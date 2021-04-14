package addsynth.energy.gui;

import addsynth.core.container.TileEntityContainer;
import addsynth.core.gui.GuiBase;
import addsynth.core.inventory.IInputInventory;
import addsynth.core.util.StringUtil;
import addsynth.energy.main.Energy;
import addsynth.energy.main.IEnergyUser;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public abstract class GuiEnergyBase<T extends TileEntity & IEnergyUser, C extends TileEntityContainer<T>> extends GuiBase<C> {

  protected final T tile;
  protected final Energy energy;

  private final String energy_text           = StringUtil.translate("gui.addsynth_energy.common.energy");
  private final String energy_usage_text     = StringUtil.translate("gui.addsynth_energy.common.energy_usage");
  private final String tick_text             = StringUtil.translate("gui.addsynth_energy.common.tick");
  /** The word 'Status' translated. */
  private final String status_text           = StringUtil.translate("gui.addsynth_energy.common.status");
  private final String time_left_text        = StringUtil.translate("gui.addsynth_energy.common.time_remaining");
  private final String charge_remaining_text = StringUtil.translate("gui.addsynth_energy.common.charge_time_remaining");
  private final String full_charge_time_text = StringUtil.translate("gui.addsynth_energy.common.time_to_full_charge");
  private final String no_energy_change_text = StringUtil.translate("gui.addsynth_energy.common.no_energy_change");
  private final String null_energy_reference = "[Error: Null Energy Reference]";

  public GuiEnergyBase(final C container, final PlayerInventory player_inventory, final ITextComponent title, final ResourceLocation gui_texture_location){
    super(-1, -1, container, player_inventory, title, gui_texture_location);
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
    this.draw_energy(44, 21);
  }

  protected final void draw_energy(final int draw_x, final int draw_y){
    if(energy != null){
      draw_text_left(energy_text+":",draw_x,draw_y);
      draw_text_right(String.format("%.2f", energy.getEnergy()) + " / " + energy.getCapacity(), right_edge, draw_y);
    }
    else{
      draw_text_center(null_energy_reference, (draw_x + right_edge) / 2, draw_y);
    }
  }

  protected final void draw_energy_usage(){
    this.draw_energy_usage(6, 17);
  }
  
  protected final void draw_energy_usage_after_switch(){
    this.draw_energy_usage(44, 21);
  }
  
  protected final void draw_energy_usage(final int draw_x, final int draw_y){
    if(energy != null){
      draw_text_left(energy_usage_text+":", draw_x, draw_y);
      draw_text_right(String.format("%.2f", energy.get_energy_in()) + " /"+tick_text, right_edge, draw_y);
    }
    else{
      draw_text_left(null_energy_reference, draw_x, draw_y);
    }
  }

  /** Draws the status at the default location, below the energy capacity line. */
  protected final void draw_status(final String status){
    draw_text_left(status_text+": "+status, 6, 28);
  }

  protected final void draw_status(final String status, final int y){
    draw_text_left(status_text+": "+status, 6, y);
  }

  protected final void draw_status_below_switch(final String status){
    draw_text_left(status_text+": "+status, 6, 37);
  }

  protected final void draw_time_left(final int draw_y){
    if(energy != null){
      final double rate = energy.getDifference();
      final String time_left; // it let's me do this?
      if(tile instanceof IInputInventory){
        // TODO: to calculate total time left, update a jobs array every time the inventories change!
        //       a Job is a list of ItemStacks. Keep the Jobs in a Queue List.
        //       When input inventory changes, copy the inventory, then check which job can be performed,
        //       add the Job to the job array and remove the job from the copy.
        //       This way we determine what jobs to perform ahead of time, and it works with more than 1 ItemStack.
        final ItemStack stack = ((IInputInventory)tile).getInputInventory().getStackInSlot(0);
        time_left = StringUtil.print_time((stack.getCount() * energy.getCapacity()) + energy.getEnergyNeeded(), rate);
      }
      else{
        time_left = StringUtil.print_time(energy.getEnergyNeeded(), rate);
      }
      draw_text_left(time_left_text+": "+time_left, 6, draw_y);
    }
    else{
      draw_text_left(time_left_text+": "+null_energy_reference, 6, draw_y);
    }
  }

  protected final void draw_energy_difference(final int draw_y){
    if(energy == null){
      draw_text_left(null_energy_reference, 6, draw_y);
      return;
    }
    final double difference = energy.getDifference();
    switch((int)Math.signum(difference)){
    case 1:
      draw_text_left(full_charge_time_text+": "+StringUtil.print_time((int)Math.ceil(energy.getEnergyNeeded() / difference)), 6, draw_y);
      break;
    case -1:
      draw_text_left(charge_remaining_text+": "+StringUtil.print_time((int)Math.ceil(energy.getEnergy() / (-difference))), 6, draw_y);
      break;
    case 0:
      draw_text_left(no_energy_change_text, 6, draw_y);
      break;
    }
  }

}
