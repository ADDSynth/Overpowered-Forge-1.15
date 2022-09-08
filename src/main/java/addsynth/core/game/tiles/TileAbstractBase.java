package addsynth.core.game.tiles;

import addsynth.core.ADDSynthCore;
import addsynth.core.util.game.MessageUtil;
import addsynth.core.util.world.WorldUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

/** This is our abstract BlockEntity class which only contains a few
 *  basic stuff. Do not override this directly. Either create your own
 *  BlockEntity or override {@link TileBase} or {@link TileBaseNoData}.
 * @author ADDSynth
 */
public abstract class TileAbstractBase extends TileEntity {

  public TileAbstractBase(final TileEntityType type){
    super(type);
  }

  @SuppressWarnings("null")
  protected final boolean onServerSide(){
    return !world.isRemote;
  }

  @SuppressWarnings("null")
  protected final boolean onClientSide(){
    return world.isRemote;
  }

  public abstract void update_data();

  protected final void report_ticking_error(final Throwable e){
    ADDSynthCore.log.fatal(
      "Encountered an error while ticking TileEntity: "+getClass().getSimpleName()+", at position: "+pos+". "+
      "Please report this to the developer.", e);

    WorldUtil.delete_block(world, pos);

    final TranslationTextComponent message = new TranslationTextComponent("message.addsynthcore.tileentity_error",
      getClass().getSimpleName(), pos.getX(), pos.getY(), pos.getZ());

    message.setStyle(new Style().setColor(TextFormatting.RED));
    MessageUtil.send_to_all_players_in_world(world, message);
  }

}
