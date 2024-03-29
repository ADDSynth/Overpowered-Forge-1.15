package addsynth.core.gui.widgets.scrollbar;

import addsynth.core.util.StringUtil;
import addsynth.core.util.color.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;

public final class ItemListEntry extends AbstractListEntry<ItemStack> {

  private ItemStack item;

  public ItemListEntry(int x, int y, int width, int height){
    super(x, y, width, height);
  }

  @Override
  @SuppressWarnings("resource")
  public void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_){
    Minecraft minecraft = Minecraft.getInstance();
    ItemRenderer itemRenderer = minecraft.getItemRenderer();
    FontRenderer fontrenderer = minecraft.fontRenderer;
    drawListEntryHighlight(minecraft);
    if(item != null){
      itemRenderer.renderItemIntoGUI(item, this.x + 1, this.y + 1);
    }
    drawString(fontrenderer, getMessage(), this.x + 18, this.y + 5, Color.WHITE.get());
  }

  @Override
  public void set(final int entry_id, final ItemStack item){
    this.entry_id = entry_id;
    this.item = item;
    setMessage(item != null ? StringUtil.translate(item.getTranslationKey()) : "");
  }

  public void set(final int entry_id, final ItemStack item, final String message){
    this.entry_id = entry_id;
    this.item = item;
    // this.text = message;
    setMessage(message);
  }

  @Override
  public void setNull(){
    this.entry_id = -1;
    this.item = null;
    setMessage("");
    this.selected = false;
  }
}
