package addsynth.energy.lib.gui;

import addsynth.core.container.TileEntityContainer;
import addsynth.core.game.inventory.machine.IMachineInventory;
import addsynth.core.gui.GuiContainerBase;
import addsynth.core.gui.util.GuiUtil;
import addsynth.core.util.StringUtil;
import addsynth.energy.lib.main.Energy;
import addsynth.energy.lib.main.IEnergyUser;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

/** Base Gui class for machines that use {@link Energy}. Contains helper
 *  functions for drawing energy variables and machine status.
 * @author ADDSynth
 * @param <T> TileEntity that implements the {@link IEnergyUser} interface
 * @param <C> The Container class object for the machine
 */
public abstract class GuiEnergyBase<T extends TileEntity & IEnergyUser, C extends TileEntityContainer<T>> extends GuiContainerBase<C> {

  protected final T tile;
  protected final Energy energy;

  private final String energy_text           = StringUtil.translate("gui.addsynth_energy.common.energy");
  private final String energy_usage_text     = StringUtil.translate("gui.addsynth_energy.common.energy_usage");
  private final String tick_text             = StringUtil.translate("gui.addsynth_energy.common.tick");
  private final String efficiency_text       = StringUtil.translate("gui.addsynth_energy.common.efficiency");
  protected final String max_extract_text    = StringUtil.translate("gui.addsynth_energy.common.max_extract");
  private final String extraction_text       = StringUtil.translate("gui.addsynth_energy.common.extraction");
  /** The word 'Status' translated. */
  private final String status_text           = StringUtil.translate("gui.addsynth_energy.common.status");
  private final String time_left_text        = StringUtil.translate("gui.addsynth_energy.common.time_remaining");
  private final String charge_remaining_text = StringUtil.translate("gui.addsynth_energy.common.charge_time_remaining");
  private final String full_charge_time_text = StringUtil.translate("gui.addsynth_energy.common.time_to_full_charge");
  private final String no_energy_change_text = StringUtil.translate("gui.addsynth_energy.common.no_energy_change");
  private final String null_energy_reference = "[Error: Null Energy Reference]";

  public GuiEnergyBase(final C container, final PlayerInventory player_inventory, final ITextComponent title, final ResourceLocation gui_texture_location){
    super(container, player_inventory, title, gui_texture_location);
    this.tile = (T)container.getTileEntity();
    this.energy = tile.getEnergy();
  }

  public GuiEnergyBase(int width, int height, C container, PlayerInventory player_inventory, ITextComponent title, ResourceLocation gui_texture_location){
    super(width, height, container, player_inventory, title, gui_texture_location);
    this.tile = (T)container.getTileEntity();
    this.energy = tile.getEnergy();
  }

  /** Draws Energy: Level / Capacity in the standard location, just below the title, at y = 17 pixels. */
  protected final void draw_energy(){
    this.draw_energy(6, 17);
  }

  protected final void draw_energy_after_switch(){
    this.draw_energy(50, 21);
  }

  protected final void draw_energy_below_switch(){
    this.draw_energy(6, 37);
  }

  protected final void draw_energy(final int draw_x, final int draw_y){
    if(energy != null){
      GuiUtil.draw_text_left(energy_text+":", draw_x, draw_y);
      GuiUtil.draw_text_right(String.format("%.2f", energy.getEnergy()) + " / " + energy.getCapacity(), guiUtil.right_edge, draw_y);
    }
    else{
      GuiUtil.draw_text_center(null_energy_reference, (draw_x + guiUtil.right_edge) / 2, draw_y);
    }
  }

  /** Draws Energy Extraction for Generators. */
  protected final void draw_energy_extraction(final int draw_y){
    GuiUtil.draw_text_left(extraction_text+":", 6, draw_y);
    GuiUtil.draw_text_right(String.format("%.2f", energy.get_energy_out())+" / "+energy.getMaxExtract(), guiUtil.right_edge, draw_y);
  }

  /** Draws the energy usage after the title. */
  protected final void draw_energy_usage(){
    this.draw_energy_usage(6, 17);
  }
  
  /** Draws the Energy Usage to the right of the Power Switch. */
  protected final void draw_energy_usage_after_switch(){
    this.draw_energy_usage(50, 21);
  }
  
  protected final void draw_energy_usage(final int draw_x, final int draw_y){
    if(energy != null){
      GuiUtil.draw_text_left(efficiency_text+":", draw_x, draw_y);
      final String energy_usage = StringUtil.build(
        String.format("%.2f", energy.get_energy_in()),
        " / ",
        String.format("%.2f", energy.getMaxReceive()),
        "  ",
        StringUtil.toPercentageString(energy.get_energy_in() / energy.getMaxReceive())
      );
      GuiUtil.draw_text_right(energy_usage, guiUtil.right_edge, draw_y);
    }
    else{
      GuiUtil.draw_text_left(null_energy_reference, draw_x, draw_y);
    }
  }

  /** Draws the status at the default location, below the energy capacity line. */
  protected final void draw_status(final String status){
    GuiUtil.draw_text_left(status_text+": "+status, 6, 28);
  }

  protected final void draw_status(final String status, final int y){
    GuiUtil.draw_text_left(status_text+": "+status, 6, y);
  }

  protected final void draw_status(final String status, final int x, final int y){
    GuiUtil.draw_text_left(status_text+": "+status, x, y);
  }

  protected final void draw_status_after_switch(final String status){
    GuiUtil.draw_text_left(status_text+": "+status, 50, 21);
  }

  protected final void draw_status_below_switch(final String status){
    GuiUtil.draw_text_left(status_text+": "+status, 6, 37);
  }

  /** Draws machine time left at the bottom-left corner of the gui. */
  protected final void draw_time_left(final int draw_y){
    if(energy != null){
      final double rate = energy.getDifference();
      final String time_left; // it let's me do this?
      if(tile instanceof IMachineInventory){
        time_left = StringUtil.print_time((((IMachineInventory)tile).getJobs() * energy.getCapacity()) + energy.getEnergyNeeded(), rate);
      }
      else{
        time_left = StringUtil.print_time(energy.getEnergyNeeded(), rate);
      }
      GuiUtil.draw_text_left(time_left_text+": "+time_left, 6, draw_y);
    }
    else{
      GuiUtil.draw_text_left(time_left_text+": "+null_energy_reference, 6, draw_y);
    }
  }

  /** Draws machine time left at the bottom-center of the gui. */
  protected final void draw_time_left_center(final int draw_y){
    final int draw_x = xSize/2;
    if(energy != null){
      final double rate = energy.getDifference();
      final String time_left;
      if(tile instanceof IMachineInventory){
        time_left = StringUtil.print_time((((IMachineInventory)tile).getJobs() * energy.getCapacity()) + energy.getEnergyNeeded(), rate);
      }
      else{
        time_left = StringUtil.print_time(energy.getEnergyNeeded(), rate);
      }
      GuiUtil.draw_text_center(time_left_text+": "+time_left, draw_x, draw_y);
    }
    else{
      GuiUtil.draw_text_center(time_left_text+": "+null_energy_reference, draw_x, draw_y);
    }
  }

  /** Draws charge time at bottom-left of gui. */
  protected final void draw_energy_difference(final int draw_y){
    if(energy == null){
      GuiUtil.draw_text_left(null_energy_reference, 6, draw_y);
      return;
    }
    final double difference = energy.getDifference();
    switch((int)Math.signum(difference)){
    case 1:
      GuiUtil.draw_text_left(full_charge_time_text+": "+StringUtil.print_time((int)Math.ceil(energy.getEnergyNeeded() / difference)), 6, draw_y);
      break;
    case -1:
      GuiUtil.draw_text_left(charge_remaining_text+": "+StringUtil.print_time((int)Math.ceil(energy.getEnergy() / (-difference))), 6, draw_y);
      break;
    case 0:
      GuiUtil.draw_text_left(no_energy_change_text, 6, draw_y);
      break;
    }
  }

  /** Draws charge time at bottom-center of gui. */
  protected final void draw_energy_difference_center(final int draw_y){
    final int draw_x = xSize/2;
    if(energy == null){
      GuiUtil.draw_text_center(null_energy_reference, draw_x, draw_y);
      return;
    }
    final double difference = energy.getDifference();
    switch((int)Math.signum(difference)){
    case 1:
      GuiUtil.draw_text_center(full_charge_time_text+": "+StringUtil.print_time((int)Math.ceil(energy.getEnergyNeeded() / difference)), draw_x, draw_y);
      break;
    case -1:
      GuiUtil.draw_text_center(charge_remaining_text+": "+StringUtil.print_time((int)Math.ceil(energy.getEnergy() / (-difference))), draw_x, draw_y);
      break;
    case 0:
      GuiUtil.draw_text_center(no_energy_change_text, draw_x, draw_y);
      break;
    }
  }

}
