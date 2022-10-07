package addsynth.overpoweredmod.items.tools;

import addsynth.core.game.items.ToolConstants;
import addsynth.overpoweredmod.assets.CreativeTabs;
import addsynth.overpoweredmod.game.Names;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.Rarity;

public class NullPickaxe extends PickaxeItem {

  public NullPickaxe(){
    super(OverpoweredTiers.VOID, ToolConstants.pickaxe_damage, ToolConstants.pickaxe_speed, new Item.Properties().group(CreativeTabs.tools_creative_tab));
    setRegistryName(Names.VOID_PICKAXE);
  }

  @Override
  public boolean isDamageable(){
    return false;
  }

  @Override
  public boolean hasEffect(ItemStack stack){
    return true;
  }
  
  @Override
  public boolean isEnchantable(ItemStack stack){
    return false;
  }

  @Override
  public Rarity getRarity(ItemStack stack){
    return Rarity.EPIC;
  }

}
