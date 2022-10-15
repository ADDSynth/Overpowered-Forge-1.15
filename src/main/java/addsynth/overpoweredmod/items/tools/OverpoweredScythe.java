package addsynth.overpoweredmod.items.tools;

import addsynth.core.gameplay.items.ScytheTool;
import addsynth.overpoweredmod.assets.CreativeTabs;
import addsynth.overpoweredmod.game.reference.Names;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;

public final class OverpoweredScythe extends ScytheTool {

  public OverpoweredScythe(){
    super(Names.CELESTIAL_SCYTHE, OverpoweredTiers.CELESTIAL, new Item.Properties().group(CreativeTabs.creative_tab));
  }

  @Override
  public boolean isEnchantable(final ItemStack stack){
    return false;
  }
  
  @Override
  public Rarity getRarity(final ItemStack stack){
    return Rarity.RARE;
  }

}
