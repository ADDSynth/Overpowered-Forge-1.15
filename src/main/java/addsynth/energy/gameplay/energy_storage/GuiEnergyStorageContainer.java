package addsynth.energy.gameplay.energy_storage;

import addsynth.core.gui.objects.ProgressBar;
import addsynth.core.util.StringUtil;
import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.gui.GuiEnergyBase;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public final class GuiEnergyStorageContainer extends GuiEnergyBase<TileEnergyStorage, ContainerEnergyStorage> {

  private static final ResourceLocation energy_storage_gui_texture =
    new ResourceLocation(ADDSynthEnergy.MOD_ID,"textures/gui/energy_storage.png");

  private final String energy_stored_text = StringUtil.translate("gui.addsynth_energy.common.energy_stored");

  private float energy_float;
  private static final int draw_energy_text_y  = 25;
  private static final int draw_energy_level_y = 36;
  private static final int draw_energy_x   = 88;
  private static final int draw_capacity_x = 93;
  private static final int draw_energy_percentage_y = 47;
  private final ProgressBar energy_bar = new ProgressBar(9, 59, 174, 17, 9, 106);

  public GuiEnergyStorageContainer(final ContainerEnergyStorage container, final PlayerInventory player_inventory, final ITextComponent title){
    super(190, 94, container, player_inventory, title, energy_storage_gui_texture);
  }

  @Override
  protected final void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    draw_background_texture();
    
    if(energy != null){
      energy_float = energy.getEnergyPercentage();
      energy_bar.draw(this,this.guiLeft,this.guiTop,ProgressBar.Direction.LEFT_TO_RIGHT,energy_float,ProgressBar.Round.NEAREST);
    }
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY){
    super.draw_title();
    draw_text_center(energy_stored_text+":", center_x, draw_energy_text_y);
    draw_text_right(String.format("%.2f", energy.getEnergy()), draw_energy_x, draw_energy_level_y);
    draw_text_left("/ "+energy.getCapacity(), draw_capacity_x, draw_energy_level_y);
    draw_text_center(Math.round(energy_float*100) + "%", center_x, draw_energy_percentage_y);
    draw_energy_difference(80);
  }

}
