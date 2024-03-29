package addsynth.energy.lib.blocks;

import addsynth.core.game.inventory.IInventoryUser;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** This is your typical machine-type block with a silver appearance and metallic properties.
 * @author ADDSynth
 */
public abstract class MachineBlock extends Block {

  /** Specify your own Block Properties. Required if block is transparent! */
  public MachineBlock(final Block.Properties properties){
    super(properties);
  }

  /** Standard constructor. SoundType = Metal, and standard block hardness. */
  public MachineBlock(final MaterialColor color){
    super(Block.Properties.create(Material.IRON, color).sound(SoundType.METAL).hardnessAndResistance(3.5f, 6.0f));
  }

  @Override
  public boolean hasTileEntity(BlockState state){
    return true;
  }

  @Override
  @SuppressWarnings("deprecation")
   public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving){
    final TileEntity tile = world.getTileEntity(pos);
    if(tile != null){
      if(tile instanceof IInventoryUser){
        ((IInventoryUser)tile).drop_inventory();
      }
    }
    super.onReplaced(state, world, pos, newState, isMoving);
  }
  
}
