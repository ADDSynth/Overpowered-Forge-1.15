package addsynth.overpoweredmod.items.tools;

import addsynth.core.game.item.constants.ToolConstants;
import addsynth.overpoweredmod.assets.CreativeTabs;
import addsynth.overpoweredmod.game.reference.Names;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.item.ShovelItem;

public class NullShovel extends ShovelItem {

  public NullShovel(){
    super(OverpoweredTiers.VOID, ToolConstants.shovel_damage, ToolConstants.shovel_speed, new Item.Properties().group(CreativeTabs.creative_tab));
    setRegistryName(Names.VOID_SHOVEL);
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
