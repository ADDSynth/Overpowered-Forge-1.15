package addsynth.overpoweredmod.items.basic;

import addsynth.overpoweredmod.items.OverpoweredItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

public final class LensItem extends OverpoweredItem {

  public final int index;
  private final TextFormatting color_code;
  // https://minecraft.gamepedia.com/Formatting_codes

  public LensItem(final int index, final ResourceLocation name, final TextFormatting format_code){
    super(name);
    this.index = index;
    color_code = format_code;
  }

  @Override
  public ITextComponent getDisplayName(final ItemStack stack){
    return super.getDisplayName(stack).applyTextStyle(color_code);
  }

}
