package addsynth.overpoweredmod.machines.identifier;

import addsynth.core.gui.util.GuiUtil;
import addsynth.energy.lib.gui.GuiEnergyBase;
import addsynth.energy.lib.gui.widgets.WorkProgressBar;
import addsynth.overpoweredmod.game.reference.GuiReference;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public final class GuiIdentifier extends GuiEnergyBase<TileIdentifier, ContainerIdentifier> {

  private final WorkProgressBar work_progress_bar = new WorkProgressBar(8, 75, 160, 5, 11, 184);

  public GuiIdentifier(final ContainerIdentifier container, final PlayerInventory player_inventory, final ITextComponent title){
    super(176, 169, container, player_inventory, title, GuiReference.identifier);
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
    GuiUtil.drawItemStack(tile.getWorkingInventory().getStackInSlot(0), 76, 41);
    GuiUtil.draw_text_center(work_progress_bar.getWorkTimeProgress(), guiUtil.center_x, 63);
  }

}
