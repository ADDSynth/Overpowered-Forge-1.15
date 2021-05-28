package addsynth.core.block_network;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import addsynth.core.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** A Node neatly stores a Block Position, Block Type, and a TileEntity in the same object.
 *  The TileEntity can be null if no TileEntity exists at that location. */
public class Node <T extends TileEntity> {

  public final BlockPos position;
  public final Block block;
  protected final T tile;

  public static final Node<TileEntity> getNode(final BlockPos position, final World world){
    return new Node<TileEntity>(position, world.getBlockState(position).getBlock(), world.getTileEntity(position));
  }

  public Node(@Nonnull final T tile){
    this.position = tile.getPos();
    this.block = tile.getBlockState().getBlock();
    this.tile = tile;
  }

  public Node(final BlockPos position, @Nonnull final T tile){
    this.position = position;
    this.block = tile.getBlockState().getBlock();
    this.tile = tile;
  }

  public Node(final BlockPos position, final Block block, final T tile){
    this.position = position;
    this.block = block;
    this.tile = tile;
  }

  public boolean isInvalid(){
    if(block == null || position == null){
      return true;
    }
    return tile != null ? (tile.isRemoved() || !tile.getPos().equals(position)) : false;
  }

  @Nullable
  public T getTile(){
    return tile;
  }

  @Override
  public String toString(){
    if(tile == null){
      return "Node{Position: "+position.toString()+", Block: "+StringUtil.getName(block)+"}";
    }
    return "Node{TileEntity: "+tile.getClass().getSimpleName()+", Position: "+position.toString()+"}";
  }

  @Override
  public boolean equals(final Object obj){
    return obj instanceof Node ? position.equals(((Node)obj).position) : false;
  }

}
