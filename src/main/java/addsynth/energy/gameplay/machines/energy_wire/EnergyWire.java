package addsynth.energy.gameplay.machines.energy_wire;

import javax.annotation.Nullable;
import addsynth.core.block_network.BlockNetworkUtil;
import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.gameplay.machines.energy_diagnostics.TileEnergyDiagnostics;
import addsynth.energy.lib.blocks.Wire;
import addsynth.energy.lib.energy_network.tiles.AbstractEnergyNetworkTile;
import addsynth.energy.lib.main.IEnergyUser;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public final class EnergyWire extends Wire {

  public EnergyWire(final String name){
    super(Block.Properties.create(Material.WOOL, MaterialColor.GRAY).hardnessAndResistance(0.1f, 0.0f));
    ADDSynthEnergy.registry.register_block(this, name, new Item.Properties().group(ADDSynthEnergy.creative_tab));
  }

  @Override
  protected final boolean[] get_valid_sides(final IBlockReader world, final BlockPos pos){
    final boolean[] valid_sides = new boolean[6];
    for(Direction side : Direction.values()){
      valid_sides[side.ordinal()] = false;
      final TileEntity tile = world.getTileEntity(pos.offset(side));
      if(tile != null){
        if(tile instanceof AbstractEnergyNetworkTile || tile instanceof IEnergyUser || tile instanceof TileEnergyDiagnostics){
          valid_sides[side.ordinal()] = true;
        }
      }
    }
    return valid_sides;
  }

  @Override
  @Nullable
  public final TileEntity createTileEntity(BlockState state, IBlockReader world){
    return new TileEnergyWire();
  }

  /** Starting in Minecraft 1.11, {@link World#addTileEntity(TileEntity)} no longer calls
   *  {@link World#updateComparatorOutputLevel(BlockPos, Block)} at the end of the function.
   *  For this reason we have to use {@link #neighborChanged(BlockState, World, BlockPos, Block, BlockPos, boolean)}
   *  instead of {@link #onNeighborChange(BlockState, IWorldReader, BlockPos, BlockPos)} like we do in Minecraft 1.10.
   *  As it turns out, not even Vanilla Minecraft uses the <code>onNeighborChange()</code> function a whole lot.
   */
  @Override
  @SuppressWarnings("deprecation")
  public final void neighborChanged(BlockState state, World world, BlockPos pos, Block blockIn, BlockPos neighbor, boolean isMoving){
    BlockNetworkUtil.neighbor_changed(world, pos, neighbor);
  }

}
