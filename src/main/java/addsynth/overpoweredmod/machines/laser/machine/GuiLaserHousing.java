package addsynth.overpoweredmod.machines.laser.machine;

import addsynth.core.gui.util.GuiUtil;
import addsynth.core.util.StringUtil;
import addsynth.energy.lib.gui.GuiEnergyBase;
import addsynth.energy.lib.gui.widgets.AutoShutoffCheckbox;
import addsynth.energy.lib.gui.widgets.EnergyProgressBar;
import addsynth.energy.lib.gui.widgets.OnOffSwitch;
import addsynth.overpoweredmod.game.NetworkHandler;
import addsynth.overpoweredmod.game.reference.GuiReference;
import addsynth.overpoweredmod.machines.laser.network_messages.SetLaserDistanceMessage;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public final class GuiLaserHousing extends GuiEnergyBase<TileLaserHousing, ContainerLaserHousing> {

  // TODO: The Laser machine also doesn't have any item slots in its inventory. It can derive from a Non-container
  //       GuiEnergyBase, but that means that I need to extract all the common helper functions from GuiEnergyBase
  //       into a GuiEnergyUtil. I can have that extend GuiUtil, then pass that as a reference through the Gui constructors.

  private final String required_energy_text = StringUtil.translate("gui.overpowered.laser_housing.required_energy");
  private final String current_energy_text  = StringUtil.translate("gui.overpowered.laser_housing.current_energy");
  private final String lasers_text          = StringUtil.translate("gui.overpowered.laser_housing.lasers");
  private final String distance_text        = StringUtil.translate("gui.overpowered.laser_housing.distance");

  private TextFieldWidget text_box;
  private final EnergyProgressBar energy_bar = new EnergyProgressBar(9, 79, 163, 16, 22, 162);

  private static final int gui_height = 115;
  private static final int space = 4;

  private static final int line_1 = 39;
  private static final int text_box_width = 48;
  private static final int text_box_height = 15;
  private static final int text_box_x = 151;
  private static final int text_box_y = line_1 -3;
  private static final int line_2 = text_box_y + text_box_height + space;

  private static final int line_3 = line_2 + 8 + space;
  private static final int line_4 = 84; // line_3 (67) + 8 + 13
  private static final int line_5 = gui_height - 14;

  private static final int check_box_x = 62;
  private static final int check_box_y = 19;

  public GuiLaserHousing(final ContainerLaserHousing container, final PlayerInventory player_inventory, final ITextComponent title){
    super(208, gui_height, container, player_inventory, title, GuiReference.laser_machine);
  }

  private static final class LaserDistanceTextField extends TextFieldWidget {

    private final TileLaserHousing tile;

    public LaserDistanceTextField(FontRenderer fontIn, int x, int y, int width, int height, TileLaserHousing tile){
      super(fontIn, x, y, width, height, "");
      this.tile = tile;
      final String initial_distance = Integer.toString(tile.getLaserDistance());
      setText(initial_distance);
      setMaxStringLength(4); // FEATURE: add a numbers-only textbox to ADDSynthCore.
      setTextColor(16777215);
      setResponder((String text) -> text_field_changed());
    }

    private final void text_field_changed(){
      int captured_distance = 0;
      try{
        captured_distance = Integer.parseUnsignedInt(getText());
      }
      catch(NumberFormatException e){
        captured_distance = -1;
      }
      if(captured_distance >= 0){
        if(captured_distance != tile.getLaserDistance()){
          if(captured_distance > LaserNetwork.max_laser_distance){
            captured_distance = LaserNetwork.max_laser_distance;
            setText(Integer.toString(LaserNetwork.max_laser_distance));
          }
          NetworkHandler.INSTANCE.sendToServer(new SetLaserDistanceMessage(tile.getPos(), captured_distance));
        }
      }
    }

  }

  @Override
  public final void init(){
    super.init();
    addButton(new OnOffSwitch<>(this, tile));
    addButton(new AutoShutoffCheckbox<TileLaserHousing>(this.guiLeft + check_box_x, this.guiTop + check_box_y, tile));
    
    this.text_box = new LaserDistanceTextField(this.font, this.guiLeft + text_box_x, this.guiTop + text_box_y, text_box_width, text_box_height, tile);
    this.children.add(text_box);
  }

  // NOTE: The only thing that doesn't sync with the client is when 2 people have the gui open
  //   and one of them changes the Laser Distance. The energy requirements sucessfully updates,
  //   but not the Laser Distance text field of the other player. Here is my solution, but it looked
  //   too weird and I didn't feel it was absolutely necessary to keep things THAT much in-sync.
  // final int captured_distance = get_laser_distance();
  // if(captured_distance >= 0){
  //   if(captured_distance != tile.getLaserDistance()){
  //     distance_text_field.setText(Integer.toString(tile.getLaserDistance()));
  //   }
  // }

  @Override
  public final void tick(){
    super.tick();
    if(text_box != null){
      text_box.tick();
    }
  }

  @Override
  public final void render(final int mouseX, final int mouseY, final float partialTicks){
    super.render(mouseX, mouseY, partialTicks);
    if(text_box != null){
      text_box.render(mouseX, mouseY, partialTicks);
    }
  }

  @Override
  protected final void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    guiUtil.draw_background_texture();
    energy_bar.drawHorizontal(this, energy);
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    guiUtil.draw_title(this.title);
    GuiUtil.draw_text_left(lasers_text+": "+tile.number_of_lasers, 6, line_1);
    GuiUtil.draw_text_right(distance_text+": ", text_box_x - 2, line_1);
    draw_energy_requirements();
    GuiUtil.draw_text_right(energy_bar.getEnergyPercentage(), width - 6, line_4);
    draw_energy_difference_center(line_5);
  }

  private final void draw_energy_requirements(){
    final String required_energy = Integer.toString((int)(energy.getCapacity()));
    final String word_1 = required_energy_text+": "+required_energy;
    final int word_1_width = font.getStringWidth(word_1);
    
    final String current_energy = Integer.toString((int)(energy.getEnergy()));
    final String word_2 = current_energy_text+": "+current_energy;
    final int word_2_width = font.getStringWidth(word_2);
    
    if(Math.max(word_1_width, word_2_width) == word_1_width){
      GuiUtil.draw_text_left(word_1, 6, line_2);
      GuiUtil.draw_text_left(current_energy_text+":", 6, line_3);
      GuiUtil.draw_text_right(current_energy, 6 + word_1_width, line_3);
    }
    else{
      GuiUtil.draw_text_left(required_energy_text+":", 6, line_2);
      GuiUtil.draw_text_right(required_energy, 6 + word_2_width, line_2);
      GuiUtil.draw_text_left(word_2, 6, line_3);
    }
  }

}
