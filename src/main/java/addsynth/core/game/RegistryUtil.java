package addsynth.core.game;

import java.util.HashSet;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import addsynth.core.ADDSynthCore;
import addsynth.core.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

// Remember: The order an item shows up in the creative tab depends on
//             the order you register Items in the Item Registry Event.
public final class RegistryUtil {
// cannot be named GameRegistry because it conflicts with net.minecraftforge.fml.common.registry.GameRegistry;

  /** This is used to store a reference to your ItemBlocks so you don't have to.
   *  Just call {@link #getItemBlock(Block)} to get one. */
  private static final HashSet<BlockItem> items = new HashSet<BlockItem>(500);

  public static final <T extends IForgeRegistryEntry<T>> void register(final IForgeRegistry<T> registry, final T object, final ResourceLocation id){
    object.setRegistryName(id);
    registry.register(object);
  }

  @Nullable
  private static final BlockItem getVanillaItemBlock(@Nonnull final Block block){
    final Item vanilla_item = Item.BLOCK_TO_ITEM.get(block);
    if(vanilla_item != null){
      if(vanilla_item != Items.AIR){
        return (BlockItem)vanilla_item;
      }
    }
    return null;
  }

  @Nullable
  private static final BlockItem getModdedItemBlock(@Nonnull final Block block){
    for(final BlockItem item : items){
      if(item.getBlock() == block){
        return item;
      }
    }
    return null;
  }

//============================================================================================================

  /** This registers your block, and also creates a vanilla ItemBlock.
   *  If you're just registering the block, then set the RegistryName directly yourself!
   * @param block
   * @param name
   * @param tab
   */
  public static final void register_block(@Nonnull final Block block, final ResourceLocation name, final ItemGroup tab){
    block.setRegistryName(name);
    register_ItemBlock(new BlockItem(block, new Item.Properties().group(tab)), name);
  }

  /** This registers your block, and also creates a vanilla ItemBlock using
   *  the Item Properties that you specify.
   *  If you're just registering the block, then set the RegistryName directly yourself!
   * @param block
   * @param name
   * @param properties
   */
  public static final void register_block(@Nonnull final Block block, final ResourceLocation name, final Item.Properties properties){
    block.setRegistryName(name);
    register_ItemBlock(new BlockItem(block, properties), name);
  }

  /** Use this to create a blank ItemBlock. */
  public static final BlockItem register_ItemBlock(final Block block){
    return register_ItemBlock(new BlockItem(block, new Item.Properties()), block.getRegistryName());
  }

  /** Use this to create and register ItemBlocks. */
  public static final BlockItem register_ItemBlock(final Block block, final ItemGroup tab){
    return register_ItemBlock(new BlockItem(block, new Item.Properties().group(tab)), block.getRegistryName());
  }

  /** Use this to create and register ItemBlocks. */
  public static final BlockItem register_ItemBlock(final Block block, final ItemGroup tab, final ResourceLocation registry_name){
    return register_ItemBlock(new BlockItem(block, new Item.Properties().group(tab)), registry_name);
  }

  /** Use this to create and register ItemBlocks. */
  public static final BlockItem register_ItemBlock(final Block block, final Item.Properties properties){
    return register_ItemBlock(new BlockItem(block, properties), block.getRegistryName());
  }

  /** Use this to create and register ItemBlocks. */
  public static final BlockItem register_ItemBlock(final Block block, final Item.Properties properties, final ResourceLocation registry_name){
    return register_ItemBlock(new BlockItem(block, properties), registry_name);
  }

  /** Use this to register your ItemBlocks.
   * @param itemblock
   * @param registry_name
   * @return Returns the passed in ItemBlock if we successfully registered it, returns null otherwise.
   */
  public static final BlockItem register_ItemBlock(@Nonnull final BlockItem itemblock, final ResourceLocation registry_name){
    if(itemblock.getRegistryName() == null){
      itemblock.setRegistryName(registry_name);
      items.add(itemblock);
      return itemblock;
    }
    ADDSynthCore.log.error("Tried to register an ItemBlock for "+StringUtil.getName(itemblock.getBlock())+" after one was already registered!");
    return itemblock;
  }

  /**
   * <p>This is our version of {@link Item#getItemFromBlock(Block)}. This function returns the ItemBlock
   *    for the block you specify, if it exists.
   * <p>We prefer you use this because the vanilla method returns Blocks.AIR, if it can't find the ItemBlock
   *    for your block, and doesn't warn you.
   * @param block
   * @return the existing one or null.
   */
  public static final BlockItem getItemBlock(final Block block){
    BlockItem item_block = null;
    try{
      // safety check
      if(block == null){
        throw new NullPointerException("Block input for RegistryUtil.getItemBlock() was null reference.");
      }
      item_block = getVanillaItemBlock(block); // check 1 (already registered)
      if(item_block == null){
        item_block = getModdedItemBlock(block); // check 2 (created but not registered)
        if(item_block == null){
          ADDSynthCore.log.fatal(
            "No ItemBlock exists for "+StringUtil.getName(block)+". ItemBlocks should've been registered when you "+
            "called RegistryUtil.register_block() or register_ItemBlock() with your preferred Item.Properties!"
          );
        }
      }
    }
    catch(Throwable e){
      e.printStackTrace();
    }
    return item_block;
  }

}
