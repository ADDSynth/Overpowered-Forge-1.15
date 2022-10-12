package addsynth.overpoweredmod.items.basic;

import addsynth.overpoweredmod.game.reference.Names;
import addsynth.overpoweredmod.items.OverpoweredItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

public final class FusionCore extends OverpoweredItem {

  public FusionCore(){
    super(Names.FUSION_CORE);
  }

  @Override
  public ITextComponent getDisplayName(ItemStack stack){
    return super.getDisplayName(stack).applyTextStyle(TextFormatting.GOLD);
  }

}
