package addsynth.overpoweredmod.machines.suspension_bridge;

import addsynth.core.util.constants.Constants;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.game.core.Lens;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public final class EnergyBridge extends RotatedPillarBlock {

  private static final VoxelShape[] shapes = {
    VoxelShapes.create(7.0 / 16, 0, 0, 9.0 / 16, 1.0, 1.0), // X = flat going forward and back, missing left and right
    VoxelShapes.create(0, 14.0 / 16, 0, 1.0, 1.0, 1.0),
    VoxelShapes.create(0, 0, 7.0 / 16, 1.0, 1.0, 9.0 / 16)  // Z = flat going left and right, missing front and back
  };

  public EnergyBridge(final String name, final Lens lens){
    super(Block.Properties.create(
      new Material(lens.color, false, true, true, false, false, false, false, PushReaction.BLOCK)
    ).lightValue(11).hardnessAndResistance(-1.0f, Constants.infinite_resistance).variableOpacity().noDrops());
    OverpoweredTechnology.registry.register_block(this, name);
  }

  public final BlockState getRotated(final Direction.Axis axis){
    return getDefaultState().with(AXIS, axis);
  }

  @Override
  @SuppressWarnings("deprecation")
  public final VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context){
    return shapes[state.get(AXIS).ordinal()];
  }

  @Override
  @SuppressWarnings("deprecation")
  public final VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context){
    return shapes[state.get(AXIS).ordinal()];
  }

  @Override
  @SuppressWarnings("deprecation")
  public final boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side){
    // Minecraft 1.15 already renders the Energy Suspension Bridge correctly.
    // TODO: Still have problem with vertical bridges however.
    // If I ever need to implement this function, copy the code from the 1.14 version.
    return super.isSideInvisible(state, adjacentBlockState, side);
  }

}
