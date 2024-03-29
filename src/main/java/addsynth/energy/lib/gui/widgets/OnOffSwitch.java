package addsynth.energy.lib.gui.widgets;

import addsynth.core.util.StringUtil;
import addsynth.energy.gameplay.NetworkHandler;
import addsynth.energy.gameplay.reference.GuiReference;
import addsynth.energy.lib.network_messages.SwitchMachineMessage;
import addsynth.energy.lib.tiles.machines.ISwitchableMachine;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.tileentity.TileEntity;

/**
 * Draws a custom button which displays an on/off switch depending on the Machine's running state.
 * Currently, we only use this to toggle the running state of an EnergyReceiver machine.
 */
public final class OnOffSwitch<T extends TileEntity & ISwitchableMachine> extends AbstractButton {

  private final T tile;
  private boolean power_state;

  private final String  on_text = StringUtil.translate("gui.addsynth_energy.switch.on");
  private final String off_text = StringUtil.translate("gui.addsynth_energy.switch.off");

  /* DELETE: Old On/Off Switch constructor. Delete in 2027
   * Call with guiLeft + standard x = 6 and guiTop + standard y = 17.
   * @param x
   * @param y
   * @param tile
   *
  public OnOffSwitch(final int x, final int y, final T tile){
    super(x, y, 34, 16, "");
    this.tile = tile;
  }
  */

  public OnOffSwitch(final ContainerScreen container, final T tile){
    super(container.getGuiLeft() + 6, container.getGuiTop() + 17, 40, 16, "");
    this.tile = tile;
    if(tile != null){
      power_state = tile.get_switch_state();
      setMessage(power_state ? on_text : off_text);
    }
  }

  /**
   * Draws the button depending on the running boolean of the TileEnergyReceiver, otherwise it contains the same code
   * as Vanilla draws a GuiButton.
   */
  @Override
  @SuppressWarnings("resource")
  public final void renderButton(final int mouseX, final int mouseY, final float partial_ticks){
    final Minecraft minecraft = Minecraft.getInstance();

    // detect state change
    if(tile != null){
      if(tile.get_switch_state() != power_state){
        power_state = tile.get_switch_state();
        setMessage(power_state ? on_text : off_text);
      }
    }
    else{
      power_state = false;
      setMessage("[null]");
    }

    // Draw Power Switch
    minecraft.getTextureManager().bindTexture(GuiReference.widgets);
    RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

    // this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
    // final int hover_state = this.getHoverState(this.hovered);
    
    RenderSystem.enableBlend();
    RenderSystem.defaultBlendFunc();
    RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

    this.blit(x, y, 0, power_state ? 0 : 16, width, height);

    // Draw text
    final FontRenderer fontrenderer = minecraft.fontRenderer;
    final int text_color = 14737632;
    drawCenteredString(fontrenderer, getMessage(), x + (power_state ? 23 : 17), y + 4, text_color);
  }

  @Override
  public final void onPress(){
    if(tile != null){
      NetworkHandler.INSTANCE.sendToServer(new SwitchMachineMessage(tile.getPos()));
    }
  }

}
