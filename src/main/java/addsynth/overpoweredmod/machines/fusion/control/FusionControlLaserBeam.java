package addsynth.overpoweredmod.machines.fusion.control;

import addsynth.overpoweredmod.game.Names;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public final class FusionControlLaserBeam extends Block {

  public FusionControlLaserBeam(){
    super(Block.Properties.create(Material.FIRE).lightValue(15).variableOpacity().doesNotBlockMovement().notSolid());
    setRegistryName(Names.FUSION_CONTROL_LASER_BEAM);
  }

  @Override
  @SuppressWarnings("deprecation")
  public final VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context){
    return VoxelShapes.empty();
  }

  @Override
  @SuppressWarnings("deprecation")
  public final boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side){
    return adjacentBlockState.getBlock() == this ? true : super.isSideInvisible(state, adjacentBlockState, side);
  }

}
