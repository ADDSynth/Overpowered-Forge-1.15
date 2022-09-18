package addsynth.core.gui.widgets.scrollbar;

import addsynth.core.util.color.Color;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.util.ResourceLocation;

public final class ListEntry extends AbstractListEntry<String> {

  public ListEntry(int x, int y, int width, int height){
    super(x, y, width, height);
  }

  @Override
  @SuppressWarnings("resource")
  public void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_){
    Minecraft minecraft = Minecraft.getInstance();
    FontRenderer fontrenderer = minecraft.fontRenderer;
    drawListEntryHighlight(minecraft);
    drawString(fontrenderer, getMessage(), this.x + 1, this.y + 1, Color.WHITE.get());
  }

  @Override
  public void set(final int entry_id, final String message){
    this.entry_id = entry_id;
    // this.text = message;
    setMessage(message);
  }

  @Override
  public void setNull(){
    this.entry_id = -1;
    setMessage("");
    this.selected = false;
  }

}
