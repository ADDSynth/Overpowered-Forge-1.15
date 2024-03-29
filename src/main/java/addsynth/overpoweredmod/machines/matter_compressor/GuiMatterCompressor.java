package addsynth.overpoweredmod.machines.matter_compressor;

import addsynth.core.gui.util.GuiUtil;
import addsynth.core.gui.widgets.ProgressBar;
import addsynth.core.util.StringUtil;
import addsynth.core.util.math.RoundMode;
import addsynth.energy.lib.gui.GuiEnergyBase;
import addsynth.overpoweredmod.config.Config;
import addsynth.overpoweredmod.game.reference.GuiReference;
import addsynth.overpoweredmod.game.reference.OverpoweredBlocks;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public final class GuiMatterCompressor extends GuiEnergyBase<TileMatterCompressor, MatterCompressorContainer> {

  private final String black_hole_text = StringUtil.translate(OverpoweredBlocks.black_hole.getTranslationKey());
  private final String matter_text     = StringUtil.translate("gui.overpowered.matter_compressor.matter");

  private final ProgressBar progress_bar = new ProgressBar(8, 83, 166, 11, 7, 190);

  public GuiMatterCompressor(final MatterCompressorContainer container, final PlayerInventory player_inventory, final ITextComponent title){
    super(183, 182, container, player_inventory, title, GuiReference.matter_compressor);
  }

  @Override
  protected final void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    guiUtil.draw_background_texture();
    progress_bar.draw(this, ProgressBar.Direction.LEFT_TO_RIGHT, tile.getProgress(), RoundMode.Floor);
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    guiUtil.draw_title(this.title);
    GuiUtil.draw_text_right(black_hole_text+':', 76, 27);
    // final int slash = font.width("/"); // 6
    // final int space = font.width(" "); // 4
    
    GuiUtil.draw_text_left(matter_text+":", 6, 71);
    GuiUtil.draw_text_right(tile.getMatter(), guiUtil.center_x - 7, 71);
    GuiUtil.draw_text_left("/ "+Config.max_matter.get(), guiUtil.center_x - 3, 71);
    guiUtil.draw_text_right(StringUtil.toPercentageString(tile.getProgress(), RoundMode.Floor), 71);
  }

}
