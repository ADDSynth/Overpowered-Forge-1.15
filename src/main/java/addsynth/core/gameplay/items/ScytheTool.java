package addsynth.core.gameplay.items;

import java.util.HashSet;
import addsynth.core.ADDSynthCore;
import net.minecraft.block.Block;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ToolItem;
import net.minecraft.util.ResourceLocation;

public class ScytheTool extends ToolItem {

  public ScytheTool(final ResourceLocation name, final IItemTier material){
    super(1.5f, -3.0f, material, new HashSet<Block>(), new Item.Properties().group(ADDSynthCore.creative_tab));
    setRegistryName(name);
  }

  public ScytheTool(final ResourceLocation name, final IItemTier material, final Item.Properties properties){
    super(1.5f, -3.0f, material, new HashSet<Block>(), properties);
    setRegistryName(name);
  }

}
