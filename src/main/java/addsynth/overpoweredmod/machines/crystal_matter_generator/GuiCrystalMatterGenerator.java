package addsynth.overpoweredmod.machines.crystal_matter_generator;

import addsynth.energy.lib.gui.GuiEnergyBase;
import addsynth.energy.lib.gui.widgets.OnOffSwitch;
import addsynth.energy.lib.gui.widgets.WorkProgressBar;
import addsynth.overpoweredmod.game.reference.GuiReference;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public final class GuiCrystalMatterGenerator extends GuiEnergyBase<TileCrystalMatterGenerator, ContainerCrystalGenerator> {

  private final WorkProgressBar work_progress_bar = new WorkProgressBar(8, 89, 160, 5, 11, 194);
  
  public GuiCrystalMatterGenerator(final ContainerCrystalGenerator container, final PlayerInventory player_inventory, final ITextComponent title){
    super(176, 192, container, player_inventory, title, GuiReference.crystal_matter_generator);
  }

  @Override
  public final void init(){
    super.init();
    addButton(new OnOffSwitch<>(this, tile));
  }

  @Override
  protected final void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    guiUtil.draw_background_texture();
    work_progress_bar.draw(this, tile);
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    guiUtil.draw_title(this.title);
    draw_status_after_switch(tile.getStatus());
    draw_energy_usage(6, 38);
    guiUtil.draw_text_center(work_progress_bar.getWorkTimeProgress(), 77);
    draw_time_left(98);
  }

}
