package addsynth.energy.gameplay.machines.electric_furnace;

import addsynth.core.gui.util.GuiUtil;
import addsynth.core.gui.widgets.ProgressBar;
import addsynth.energy.gameplay.reference.GuiReference;
import addsynth.energy.lib.gui.GuiEnergyBase;
import addsynth.energy.lib.gui.widgets.WorkProgressBar;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public final class GuiElectricFurnace extends GuiEnergyBase<TileElectricFurnace, ContainerElectricFurnace> {

  private final WorkProgressBar work_progress_bar = new WorkProgressBar(80, 60, 14, 14, 200, 2);
  
  public GuiElectricFurnace(final ContainerElectricFurnace container, final PlayerInventory player_inventory, final ITextComponent title){
    super(176, 172, container, player_inventory, title, GuiReference.electric_furnace);
  }

  @Override
  protected final void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    guiUtil.draw_background_texture();
    work_progress_bar.draw(this, ProgressBar.Direction.BOTTOM_TO_TOP, tile);
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    guiUtil.draw_title(this.title);
    draw_energy_usage();
    draw_status(tile.getStatus());
    // RenderHelper.enableGUIStandardItemLighting();
    GuiUtil.drawItemStack(tile.getWorkingInventory().getStackInSlot(0), 80, 40);
    GuiUtil.draw_text_center(work_progress_bar.getWorkTimeProgress(), guiUtil.center_x + 21, 65);
    draw_time_left(78);
  }

}
