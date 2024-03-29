package addsynth.overpoweredmod.items.tools;

import addsynth.overpoweredmod.assets.CreativeTabs;
import addsynth.overpoweredmod.game.reference.Names;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;

public class OverpoweredHoe extends HoeItem {

  public OverpoweredHoe(){
    super(OverpoweredTiers.CELESTIAL, 0.0f, new Item.Properties().group(CreativeTabs.creative_tab));
    setRegistryName(Names.CELESTIAL_HOE);
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
