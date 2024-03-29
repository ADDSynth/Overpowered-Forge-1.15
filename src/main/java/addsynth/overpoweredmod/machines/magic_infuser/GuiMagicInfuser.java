package addsynth.overpoweredmod.machines.magic_infuser;

import addsynth.core.gui.util.GuiUtil;
import addsynth.energy.lib.gui.GuiEnergyBase;
import addsynth.energy.lib.gui.widgets.WorkProgressBar;
import addsynth.overpoweredmod.game.reference.GuiReference;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public final class GuiMagicInfuser extends GuiEnergyBase<TileMagicInfuser, ContainerMagicInfuser> {

  private final WorkProgressBar work_progress_bar = new WorkProgressBar(8, 84, 160, 5, 8, 194);
  
  public GuiMagicInfuser(final ContainerMagicInfuser container, final PlayerInventory player_inventory, final ITextComponent title){
    super(176, 187, container, player_inventory, title, GuiReference.magic_infuser);
  }

  @Override
  protected final void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    guiUtil.draw_background_texture();
    work_progress_bar.draw(this, tile);
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    guiUtil.draw_title(this.title);
    draw_energy_usage();
    draw_status(tile.getStatus());
    GuiUtil.drawItemStack(tile.getWorkingInventory().getStackInSlot(0), 78, 44);
    GuiUtil.drawItemStack(tile.getWorkingInventory().getStackInSlot(1), 95, 44);
    GuiUtil.draw_text_center(work_progress_bar.getWorkTimeProgress(), guiUtil.center_x, 72);
    draw_time_left(93);
  }

}
