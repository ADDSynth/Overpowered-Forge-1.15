package addsynth.overpoweredmod.machines.inverter;

import addsynth.core.gui.util.GuiUtil;
import addsynth.energy.lib.gui.GuiEnergyBase;
import addsynth.energy.lib.gui.widgets.WorkProgressBar;
import addsynth.overpoweredmod.config.Config;
import addsynth.overpoweredmod.game.reference.GuiReference;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

public final class GuiInverter extends GuiEnergyBase<TileInverter, ContainerInverter> {

  private final WorkProgressBar work_progress_bar = new WorkProgressBar(8, 84, 160, 5, 8, 194);
  
  public GuiInverter(final ContainerInverter container, final PlayerInventory player_inventory, final ITextComponent title){
    super(176, 187, container, player_inventory, title, GuiReference.inverter);
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
    
    final ItemStack s1 = tile.getWorkingInventory().getStackInSlot(0);
    if(Config.blend_working_item.get()){
      final ItemStack s2 = TileInverter.getInverted(s1);
      GuiUtil.blendItemStacks(s1, s2, 77, 44, work_progress_bar.getWorkTime());
    }
    else{
      GuiUtil.drawItemStack(s1, 77, 44);
    }
    
    GuiUtil.draw_text_center(work_progress_bar.getWorkTimeProgress(), guiUtil.center_x, 70);
    draw_time_left(93);
  }

}
