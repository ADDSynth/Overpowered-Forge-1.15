package addsynth.energy.gameplay.machines.compressor;

import addsynth.core.gui.util.GuiUtil;
import addsynth.energy.gameplay.reference.GuiReference;
import addsynth.energy.lib.gui.GuiEnergyBase;
import addsynth.energy.lib.gui.widgets.WorkProgressBar;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public final class GuiCompressor extends GuiEnergyBase<TileCompressor, ContainerCompressor> {

  private final WorkProgressBar work_progress_bar = new WorkProgressBar(8, 79, 160, 5, 8, 194);
  
  public GuiCompressor(final ContainerCompressor container, final PlayerInventory player_inventory, final ITextComponent title){
    super(176, 182, container, player_inventory, title, GuiReference.compressor);
  }

  @Override
  protected final void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY){
    guiUtil.draw_background_texture();
    work_progress_bar.draw(this, tile);
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY){
    guiUtil.draw_title(this.title);
    draw_energy_usage();
    draw_status(tile.getStatus());
    GuiUtil.drawItemStack(tile.getWorkingInventory().getStackInSlot(0), 80, 42);
    GuiUtil.draw_text_center(work_progress_bar.getWorkTimeProgress(), guiUtil.center_x, 67);
    draw_time_left(88);
  }

}
