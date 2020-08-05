package addsynth.energy.gameplay.universal_energy_interface;

import addsynth.core.gui.objects.AdjustableButton;
import addsynth.core.gui.objects.ProgressBar;
import addsynth.core.util.StringUtil;
import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.gui.GuiEnergyBase;
import addsynth.energy.registers.NetworkHandler;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public final class GuiUniversalEnergyInterface extends GuiEnergyBase<TileUniversalEnergyTransfer, ContainerUniversalInterface> {

  private static final ResourceLocation universal_interface_gui_texture =
    new ResourceLocation(ADDSynthEnergy.MOD_ID,"textures/gui/universal_energy_interface.png");

  private final String mode_text   = StringUtil.translate("gui.addsynth_energy.common.mode");
  private final String energy_text = StringUtil.translate("gui.addsynth_energy.common.energy");

  private static final int button_width = 90;
  private final ProgressBar energy_bar = new ProgressBar(156, 18, 12, 34, 206, 28);
  
  private static final int line_1 = 21;
  private static final int line_2 = 41;

  public GuiUniversalEnergyInterface(final ContainerUniversalInterface container, final PlayerInventory player_inventory, final ITextComponent title){
    super(-1, 60, container, player_inventory, title, universal_interface_gui_texture);
  }

  private static final class CycleTransferModeButton extends AdjustableButton {

    private final TileUniversalEnergyTransfer tile;

    public CycleTransferModeButton(int xIn, int yIn, TileUniversalEnergyTransfer tile){
      super(xIn, yIn, button_width, 16);
      this.tile = tile;
    }

    @Override
    public final void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_){
      setMessage(tile.get_transfer_mode().toString());
      super.renderButton(p_renderButton_1_, p_renderButton_2_, p_renderButton_3_);
    }

    @Override
    public void onPress(){
      NetworkHandler.INSTANCE.sendToServer(new CycleTransferModeMessage(tile.getPos()));
    }

  }

  @Override
  public final void init(){
    super.init();
    final int button_x = guiLeft + center_x - (button_width / 2) + 4;
    addButton(new CycleTransferModeButton(button_x, guiTop + 17, tile));
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    draw_background_texture();
    energy_bar.draw(this,this.guiLeft,this.guiTop,ProgressBar.Direction.BOTTOM_TO_TOP,tile.getEnergy().getEnergyPercentage(),ProgressBar.Round.NEAREST);
  }

  @Override
  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    draw_title();
    draw_text_left(mode_text+":", 6, line_1);
    draw_text_left(energy_text+":", 6, line_2);
    draw_text_right(energy.getEnergy() + " / "+energy.getCapacity(), 130, line_2);
  }

}
