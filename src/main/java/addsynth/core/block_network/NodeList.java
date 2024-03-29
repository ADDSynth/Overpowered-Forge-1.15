package addsynth.core.block_network;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.function.Consumer;
import java.util.function.Predicate;
import addsynth.core.util.block.BlockUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class NodeList extends HashSet<Node> {

  public NodeList(){
    super(100);
  }

  public NodeList(final int size){
    super(size);
  }

  /** This is the main function that finds all blocks belonging to this BlockNetwork.
   *  This is called by {@link BlockNetwork#updateBlockNetwork(BlockPos, TileEntity)}. */
  @SuppressWarnings("unchecked") // This is the best I can do, for now.
  public final <T extends TileEntity & IBlockNetworkUser> void set(BlockNetwork<T> network, BlockPos from, World world, Class<T> class_type, Consumer<Node> custom_search){
    // clear all existing nodes
    clear();
    
    // construct predicate
    final Predicate<Node> is_valid = (Node node) -> {
      final TileEntity tile = node.getTile();
      if(tile != null){
        if(tile.isRemoved() == false && class_type.isInstance(tile)){
          return true;
        }
      }
      return false;
    };
    
    // find blocks that match the BlockEntity type we're looking for
    final HashSet<Node> blocks = BlockUtil.find_blocks(from, world, is_valid, custom_search);

    // assign BlockNetwork to all TileEntities
    T tile;
    for(Node node : blocks){
      tile = class_type.cast(node.getTile());
      tile.setBlockNetwork(network);
    }

    // assign Nodes
    addAll(blocks);
  }

  public final ArrayList<BlockPos> getBlockPositions(){
    final ArrayList<BlockPos> positions = new ArrayList<>(100);
    for(final Node node : this){
      if(node.isInvalid() == false){
        positions.add(node.position);
      }
    }
    return positions;
  }

  public final ArrayList<TileEntity> getTileEntities(){
    final ArrayList<TileEntity> tiles = new ArrayList<>(100);
    TileEntity tile;
    for(final Node node : this){
      tile = node.getTile();
      if(tile != null){
        if(node.isInvalid() == false){
          tiles.add(tile);
        }
      }
    }
    return tiles;
  }

  public final boolean contains(final TileEntity tile){
    for(final Node node : this){
      if(node.getTile() == tile){
        return true;
      }
    }
    return false;
  }

  public final boolean contains(final BlockPos position){
    for(final Node node : this){
      if(node.position.equals(position)){
        return true;
      }
    }
    return false;
  }

  public final void remove_invalid(){
    removeIf((Node n) -> n == null ? true : n.isInvalid());
  }

  public final void forAllTileEntities(final Consumer<TileEntity> action){
    TileEntity tile;
    for(Node node : this){
      tile = node.getTile();
      if(tile != null){
        action.accept(tile);
      }
    }
  }

  public final <T extends TileEntity> void forAllTileEntities(final Class<T> tileEntity_type, final Consumer<T> action){
    TileEntity tile;
    for(Node node : this){
      tile = node.getTile();
      if(tile != null){
        if(tileEntity_type.isInstance(tile)){
          action.accept(tileEntity_type.cast(tile));
        }
      }
    }
  }

  @Override
  public final Node[] toArray(){
    return toArray(new Node[size()]);
  }

}
