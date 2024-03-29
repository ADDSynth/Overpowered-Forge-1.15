package addsynth.overpoweredmod.machines.portal.rift;

import javax.annotation.Nullable;
import addsynth.core.game.RegistryUtil;
import addsynth.overpoweredmod.game.reference.Names;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public final class PortalEnergyBlock extends Block {

  // NOTE: well, after seeing a YouTube video, I was going to make this extend from the Vanilla PortalBlock class,
  //       but I want this to have a TileEntity, then just implement ITileProvider?

  public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;

  public PortalEnergyBlock(){
    super(Block.Properties.create(Material.PORTAL).doesNotBlockMovement().noDrops().variableOpacity());
    RegistryUtil.register_block(this, Names.PORTAL_RIFT, new Item.Properties());
    // Portal Energy Block needs an ItemBlock form to use as an icon for the Achievement.
  }

  @Override
  @SuppressWarnings("deprecation")
  public final VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context){
    return VoxelShapes.empty();
  }

  @Override
  public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player){
    return ItemStack.EMPTY;
  }

  @Override
  @SuppressWarnings("deprecation")
  public final void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity){
    if(world.isRemote == false){
      /*
      if(entity.isNonBoss()){
        if(entity instanceof ServerPlayerEntity){
          final MinecraftServer server = entity.getServer();
          if(server != null){
            server.getPlayerList().transferPlayerToDimension((ServerPlayerEntity)entity, WeirdDimension.id, new CustomTeleporter(server.getWorld(WeirdDimension.id)));
          }
        }
        else{
          entity.changeDimension(WeirdDimension.id);
        }
      }
      */
    }
  }

  @Override
  public boolean hasTileEntity(BlockState state){
    return true;
  }

  @Override
  @Nullable
  public final TileEntity createTileEntity(BlockState state, final IBlockReader world){
    return new TilePortal();
  }

  @Override
  @SuppressWarnings("deprecation")
  public final ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state){
    return ItemStack.EMPTY;
  }

  // Portal Energy block doesn't need it for some reason?
  @Override
  @SuppressWarnings("deprecation")
  public final boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side){
    return adjacentBlockState.getBlock() == this ? true : super.isSideInvisible(state, adjacentBlockState, side);
  }

}
