package addsynth.core.gui.widgets.buttons;

import javax.annotation.Nonnull;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.AbstractButton;

/** Hey! This has a maximum height of 20! So setting it any higher will have no effect. */
public abstract class AdjustableButton extends AbstractButton {

  /**
   * This constructor passes an empty string to the button. Only use this if you intend to
   * change the button text on a per-frame basis, such as responding to changes to a TileEntity.
   * @param x
   * @param y
   * @param width
   * @param height
   */
  public AdjustableButton(int x, int y, int width, int height){
    super(x, y, width, height, "");
  }

  /**
   * Only use this constructor if the button text remains static.
   * @param x
   * @param y
   * @param width
   * @param height
   * @param buttonText
   */
  public AdjustableButton(int x, int y, int width, int height, @Nonnull String buttonText){
    super(x, y, width, height, buttonText);
  }

    /**
     * Draws this button to the screen. This is my own overridden method. It auto adjusts for a variable width AND height.
     * Otherwise it is exactly the same as {@link Widget#renderButton}.
     */
    @Override
    @SuppressWarnings("resource")
    public void renderButton(final int mouseX, final int mouseY, final float partialTicks){
      final int max_width = 200;
      final int max_height = 20;
      final int half_width = Math.min(this.width, max_width) / 2;
      final int half_height = Math.min(this.height, max_height) / 2;
      // I could replace this with WidgetUtil.get_half_lengths(), but this already works pretty well.
      final int second_x = max_width - half_width;
      final int second_y = max_height - half_height;
      
      final Minecraft minecraft = Minecraft.getInstance();
      minecraft.getTextureManager().bindTexture(WIDGETS_LOCATION);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
      
      final int hover_state = this.getYImage(this.isHovered);
      final int button_texture_y = 46 + (hover_state * 20);
      
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
      
      this.blit(x             , y              , 0       , button_texture_y           , half_width, half_height);
      this.blit(x + half_width, y              , second_x, button_texture_y           , half_width, half_height);
      this.blit(x             , y + half_height, 0       , button_texture_y + second_y, half_width, half_height);
      this.blit(x + half_width, y + half_height, second_x, button_texture_y + second_y, half_width, half_height);
      this.renderBg(minecraft, mouseX, mouseY);
      
      final int text_color = getFGColor(); // 14737632; TODO: Is this the text color I'm looking for? Move this to constants. But now there's one in GuiUtil as well.
      /* DELETE
      if (packedFGColour != 0){
        text_color = packedFGColour;
      }
      else{
        if (!this.active){
          text_color = 10526880;
        }
        else{
          if (this.isHovered){
            text_color = 16777120;
          }
        }
      }
      */

      this.drawCenteredString(minecraft.fontRenderer, this.getMessage(), x + half_width, y + half_height - 4, text_color);
    }

}