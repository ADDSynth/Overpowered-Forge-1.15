package addsynth.overpoweredmod.items.tools;

import addsynth.core.game.item.constants.ToolConstants;
import addsynth.overpoweredmod.assets.CreativeTabs;
import addsynth.overpoweredmod.game.reference.Names;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.item.ShovelItem;

public final class OverpoweredShovel extends ShovelItem {

  public OverpoweredShovel(){
    super(OverpoweredTiers.CELESTIAL, ToolConstants.axe_damage, ToolConstants.axe_speed, new Item.Properties().group(CreativeTabs.creative_tab));
    setRegistryName(Names.CELESTIAL_SHOVEL);
  }

  @Override
  public boolean isEnchantable(ItemStack stack){
    return false;
  }

  @Override
  public Rarity getRarity(ItemStack stack){
    return Rarity.RARE;
  }
}
