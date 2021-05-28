package addsynth.energy.lib.energy_network;

import javax.annotation.Nonnull;
import addsynth.core.block_network.Node;
import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.lib.main.Energy;
import addsynth.energy.lib.main.IEnergyUser;
import net.minecraft.tileentity.TileEntity;

public final class EnergyNode<E extends TileEntity & IEnergyUser> extends Node<E> {

  public final Energy energy;

  public EnergyNode(@Nonnull final E tileEntity){
    super(tileEntity.getPos(), tileEntity.getBlockState().getBlock(), tileEntity);
    this.energy = tileEntity.getEnergy();
  }

  @SuppressWarnings("unchecked")
  public static final <R extends TileEntity & IEnergyUser> EnergyNode createEnergyNode(@Nonnull final TileEntity tile){
    if(tile instanceof IEnergyUser){
      return new EnergyNode<R>((R)tile);
    }
    ADDSynthEnergy.log.error(new ClassCastException(
      "TileEntity input for "+EnergyNode.class.getName()+" does not implement the "+IEnergyUser.class.getSimpleName()+" interface!"
    ));
    return null;
  }

  @Override
  public boolean isInvalid(){
    if(block == null || position == null || tile == null || energy == null){
      return true;
    }
    return tile.isRemoved() || !tile.getPos().equals(position);
  }

  @Override
  public String toString(){
    return "Node{TileEntity: "+(tile == null ? "null" : tile.getClass().getSimpleName())+", "+
                "Position: "+(position == null ? "null" : position.toString())+", "+
                (energy == null ? "Energy: null" : energy.toString())+"}";
  }

}
