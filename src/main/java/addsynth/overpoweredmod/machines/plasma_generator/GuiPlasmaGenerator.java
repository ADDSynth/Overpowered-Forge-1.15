package addsynth.overpoweredmod.machines.plasma_generator;

import addsynth.core.gui.util.GuiUtil;
import addsynth.energy.lib.gui.GuiEnergyBase;
import addsynth.energy.lib.gui.widgets.OnOffSwitch;
import addsynth.energy.lib.gui.widgets.WorkProgressBar;
import addsynth.overpoweredmod.OverpoweredTechnology;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public final class GuiPlasmaGenerator extends GuiEnergyBase<TilePlasmaGenerator, ContainerPlasmaGenerator> {

  private static final ResourceLocation crystal_matter_generator_gui_texture =
    new ResourceLocation(OverpoweredTechnology.MOD_ID,"textures/gui/plasma_generator.png");

  private final WorkProgressBar work_progress_bar = new WorkProgressBar(8, 73, 166, 5, 7, 184);
  
  public GuiPlasmaGenerator(final ContainerPlasmaGenerator container, final PlayerInventory player_inventory, final ITextComponent title){
    super(183, 176, container, player_inventory, title, crystal_matter_generator_gui_texture);
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
    GuiUtil.draw_text_center(work_progress_bar.getWorkTimeProgress(), 40, 62);
    draw_time_left(82);
  }

}
