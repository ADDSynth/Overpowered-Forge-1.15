package addsynth.core.util.math.block;

import javax.annotation.Nonnegative;
import addsynth.core.ADDSynthCore;
import addsynth.core.util.constants.DirectionConstant;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public final class DirectionUtil {

  public static final int getOppositeDirection(@Nonnegative final int direction){
    if(direction == DirectionConstant.DOWN ){ return DirectionConstant.UP;   }
    if(direction == DirectionConstant.UP   ){ return DirectionConstant.DOWN; }
    if(direction == DirectionConstant.NORTH){ return DirectionConstant.SOUTH;}
    if(direction == DirectionConstant.SOUTH){ return DirectionConstant.NORTH;}
    if(direction == DirectionConstant.WEST ){ return DirectionConstant.EAST; }
    if(direction == DirectionConstant.EAST ){ return DirectionConstant.WEST; }
    return Direction.byIndex(direction).getOpposite().getIndex();
  }

  public static final Direction getDirection(final BlockPos current_position, final BlockPos adjacent_position){
    if(current_position.equals(adjacent_position)){
      ADDSynthCore.log.error(DirectionUtil.class.getName()+".getDirection(BlockPos, BlockPos) cannot determine direction because both BlockPositions are equal!");
    }
    final int x = MathHelper.clamp(adjacent_position.getX() - current_position.getX(), -1, 1);
    final int y = MathHelper.clamp(adjacent_position.getY() - current_position.getY(), -1, 1);
    final int z = MathHelper.clamp(adjacent_position.getZ() - current_position.getZ(), -1, 1);
    return Direction.byLong(x, y, z);
  }

}
