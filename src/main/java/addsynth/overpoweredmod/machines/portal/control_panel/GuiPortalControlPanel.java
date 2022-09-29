package addsynth.overpoweredmod.machines.portal.control_panel;

import addsynth.core.gui.widgets.buttons.AdjustableButton;
import addsynth.core.gui.widgets.item.IngredientWidgetGroup;
import addsynth.core.util.StringUtil;
import addsynth.energy.lib.gui.GuiEnergyBase;
import addsynth.energy.lib.gui.widgets.AutoShutoffCheckbox;
import addsynth.energy.lib.gui.widgets.EnergyProgressBar;
import addsynth.energy.lib.gui.widgets.OnOffSwitch;
import addsynth.energy.lib.gui.widgets.WorkProgressBar;
import addsynth.material.MaterialTag;
import addsynth.material.MaterialsUtil;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.game.NetworkHandler;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public final class GuiPortalControlPanel extends GuiEnergyBase<TilePortalControlPanel, ContainerPortalControlPanel> {

  private static final ResourceLocation portal_control_panel_gui_texture =
    new ResourceLocation(OverpoweredTechnology.MOD_ID,"textures/gui/portal_control_panel.png");

  private static final IngredientWidgetGroup gem_blocks = new IngredientWidgetGroup(8);

  private static final int checkbox_x = 80;
  private static final int checkbox_y = 19;

  private static final int energy_percentage_y = 48;
  private static final int energy_change_y = 60;

  private final EnergyProgressBar energy_bar = new EnergyProgressBar(193, 59, 17, 64, 227, 24);

  private static final ResourceLocation gui_icons = new ResourceLocation(OverpoweredTechnology.MOD_ID,"textures/gui/gui_textures.png");
  private static final int image_x = 14;
  private static final int image_y = 71;
  private static final int space_x = 44;
  private static final int space_y = 18;

  private static final int button_x = 27;
  private static final int button_y = 108;
  private static final int button_width = 136;
  private static final int button_height = 16;
  
  private static final int status_message_y = button_y + button_height + 6;

  public GuiPortalControlPanel(final ContainerPortalControlPanel container, final PlayerInventory player_inventory, final ITextComponent title){
    super(218, 146, container, player_inventory, title, portal_control_panel_gui_texture);
  }

  private static final class GeneratePortalButton extends AdjustableButton {

    private final TilePortalControlPanel tile;

    public GeneratePortalButton(final int x, final int y, final TilePortalControlPanel tile){
      super(x, y, button_width, button_height, StringUtil.translate("gui.overpowered.portal_control_panel.generate_portal"));
      this.tile = tile;
    }

    @Override
    public void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_){
      this.active = tile.isValid();
      super.renderButton(p_renderButton_1_, p_renderButton_2_, p_renderButton_3_);
    }

    @Override
    public void onPress(){
      NetworkHandler.INSTANCE.sendToServer(new GeneratePortalMessage(tile.getPos()));
    }

  }

  @Override
  public final void init(){
    super.init();
    addButton(new OnOffSwitch<>(this, tile));
    addButton(new AutoShutoffCheckbox<TilePortalControlPanel>(this.guiLeft + checkbox_x, this.guiTop + checkbox_y, tile));
    addButton(new GeneratePortalButton(this.guiLeft + button_x, this.guiTop + button_y, tile));
    
    // Set Portal Control Panel Gui Displayed ItemStacks
    final ItemStack[][] portal_control_panel_displayed_itemstacks = {
      MaterialsUtil.getTagIngredient(MaterialTag.RUBY.BLOCKS).getMatchingStacks(),
      MaterialsUtil.getTagIngredient(MaterialTag.TOPAZ.BLOCKS).getMatchingStacks(),
      MaterialsUtil.getTagIngredient(MaterialTag.CITRINE.BLOCKS).getMatchingStacks(),
      MaterialsUtil.getTagIngredient(MaterialTag.EMERALD.BLOCKS).getMatchingStacks(),
      MaterialsUtil.getTagIngredient(MaterialTag.DIAMOND.BLOCKS).getMatchingStacks(),
      MaterialsUtil.getTagIngredient(MaterialTag.SAPPHIRE.BLOCKS).getMatchingStacks(),
      MaterialsUtil.getTagIngredient(MaterialTag.AMETHYST.BLOCKS).getMatchingStacks(),
      MaterialsUtil.getTagIngredient(MaterialTag.QUARTZ.BLOCKS).getMatchingStacks()
    };
    gem_blocks.setRecipe(portal_control_panel_displayed_itemstacks);
  }

  @Override
  public void tick(){
    gem_blocks.tick();
  }

  @Override
  protected final void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    guiUtil.draw_background_texture();
    energy_bar.drawVertical(this, energy);
    draw_portal_items();
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY){
    guiUtil.draw_title(this.title);
    draw_energy_below_switch();
    draw_status(tile.getStatus(), energy_percentage_y);
    guiUtil.draw_text_right(WorkProgressBar.getWorkTimeProgress(tile), energy_percentage_y);
    draw_energy_difference(energy_change_y);
    guiUtil.draw_text_center(tile.getMessage(), status_message_y);
  }
  
  /**
   * This is used to show non-interactable slots on the Gui showing each of the 8 gem blocks, and shows a
   * check mark or X by each of them indicating if the portal frame has it or not.
   */
  private final void draw_portal_items(){
    // GuiContainer class -> drawScreen() method -> Line 100
    // GuiContainer class -> drawSlot() method -> Line 298
    int i;
    int j;
    int index;
    int x;
    int y;
    // RenderHelper.enableGUIStandardItemLighting();
    for(j = 0; j < 2; j++){
      for(i = 0; i < 4; i++){
        index = (j*4) + i;
        x = this.guiLeft + image_x + (i * space_x);
        y = this.guiTop  + image_y + (j * space_y);
        gem_blocks.drawIngredient(index, x, y);
      }
    }
    minecraft.getTextureManager().bindTexture(gui_icons);
    for(j = 0; j < 2; j++){
      for(i = 0; i < 4; i++){
        index = (j*4) + i;
        x = this.guiLeft + image_x + (i * space_x) + 16;
        y = this.guiTop + image_y + (j * space_y);
        if(tile.getPortalItem(index)){
          blit(x, y, 64, 0, 16, 16);
        }
        else{
          blit(x, y, 80, 0, 16, 16);
        }
      }
    }
  }

  @Override
  protected void renderHoveredToolTip(int mouse_x, int mouse_y){
    super.renderHoveredToolTip(mouse_x, mouse_y);
    int i;
    int j;
    int index;
    int x;
    int y;
    for(j = 0; j < 2; j++){
      for(i = 0; i < 4; i++){
        index = (j*4) + i;
        x = this.guiLeft + image_x + (i * space_x);
        y = this.guiTop  + image_y + (j * space_y);
        gem_blocks.drawTooltip(this, index, x, y, mouse_x, mouse_y);
      }
    }
  }

}
