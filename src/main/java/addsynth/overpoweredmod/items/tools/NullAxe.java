package addsynth.overpoweredmod.items.tools;

import addsynth.overpoweredmod.assets.CreativeTabs;
import addsynth.overpoweredmod.game.reference.Names;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;

public class NullAxe extends AxeItem {

  public NullAxe(){
    super(OverpoweredTiers.VOID, 14.0f, -3.0f, new Item.Properties().group(CreativeTabs.creative_tab));
    setRegistryName(Names.VOID_AXE);
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
