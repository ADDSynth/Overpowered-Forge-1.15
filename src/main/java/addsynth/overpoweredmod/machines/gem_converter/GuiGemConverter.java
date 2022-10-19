package addsynth.overpoweredmod.machines.gem_converter;

import addsynth.core.gui.util.GuiUtil;
import addsynth.core.gui.widgets.buttons.AdjustableButton;
import addsynth.energy.lib.gui.GuiEnergyBase;
import addsynth.energy.lib.gui.widgets.WorkProgressBar;
import addsynth.material.Material;
import addsynth.overpoweredmod.config.Config;
import addsynth.overpoweredmod.game.NetworkHandler;
import addsynth.overpoweredmod.game.reference.GuiReference;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

public final class GuiGemConverter extends GuiEnergyBase<TileGemConverter, ContainerGemConverter> {

  private static final ItemStack[] gem = new ItemStack[] {
    new ItemStack(Material.RUBY.gem, 1),
    new ItemStack(Material.TOPAZ.gem, 1),
    new ItemStack(Material.CITRINE.gem, 1),
    new ItemStack(Material.EMERALD.gem, 1),
    new ItemStack(Material.DIAMOND.gem, 1),
    new ItemStack(Material.SAPPHIRE.gem, 1),
    new ItemStack(Material.AMETHYST.gem, 1),
    new ItemStack(Material.QUARTZ.gem, 1)
  };

  private static final int work_percentage_x = 25;
  private static final int work_percentage_y = 88;
  private static final int time_left_y = 99;

  private static final int left_button_x = 64;
  private static final int cycle_button_y = 64;
  private static final int cycle_button_width = 10;
  private static final int cycle_button_buffer = 2;
  private static final int right_button_x = left_button_x + cycle_button_width + 16 + (cycle_button_buffer*2);
  private static final int cycle_button_height = 16;
  
  private final WorkProgressBar work_progress_bar = new WorkProgressBar(43, 89, 122, 5, 40, 199);
  
  public GuiGemConverter(final ContainerGemConverter container, final PlayerInventory player_inventory, final ITextComponent title){
    super(176, 194, container, player_inventory, title, GuiReference.gem_converter);
  }

  private static final class CycleGemButton extends AdjustableButton {

    private final TileGemConverter tile;
    private final boolean direction;

    public CycleGemButton(int xIn, int yIn, boolean direction, TileGemConverter tile){
      super(xIn, yIn, cycle_button_width, cycle_button_height, direction ? ">" : "<"); // true goes right
      this.tile = tile;
      this.direction = direction;
    }

    @Override
    public void onPress(){
      NetworkHandler.INSTANCE.sendToServer(new CycleGemConverterMessage(tile.getPos(),direction));
    }

  }

  @Override
  public final void init(){
    super.init();
    addButton(new CycleGemButton(this.guiLeft + left_button_x, this.guiTop + cycle_button_y,false, tile));
    addButton(new CycleGemButton(this.guiLeft + right_button_x, this.guiTop + cycle_button_y,true, tile));
  }

  @Override
  protected final void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY){
    guiUtil.draw_background_texture();
    work_progress_bar.draw(this, tile);
    GuiUtil.drawItemStack(gem[tile.get_gem_selected()], this.guiLeft + left_button_x + 12, this.guiTop + cycle_button_y);
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY){
    guiUtil.draw_title(this.title);
    draw_energy_usage();
    draw_status(tile.getStatus());
    
    final ItemStack s1 = tile.getWorkingInventory().getStackInSlot(0);
    if(Config.blend_working_item.get()){
      final ItemStack s2 = gem[tile.getConvertingStack()];
      GuiUtil.blendItemStacks(s1, s2, 76, 45, work_progress_bar.getWorkTime());
    }
    else{
      GuiUtil.drawItemStack(s1, 76, 45);
    }
    
    GuiUtil.draw_text_center(work_progress_bar.getWorkTimeProgress(), work_percentage_x, work_percentage_y);
    draw_time_left(time_left_y);
  }

}
