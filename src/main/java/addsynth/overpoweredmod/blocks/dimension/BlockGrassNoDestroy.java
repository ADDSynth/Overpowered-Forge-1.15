package addsynth.overpoweredmod.blocks.dimension;

import addsynth.core.util.game.MinecraftUtility;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

@Deprecated
public final class BlockGrassNoDestroy extends Block {

  public BlockGrassNoDestroy(){
    super(MinecraftUtility.setUnbreakable(Block.Properties.create(Material.ORGANIC)));
    // MAYBE: setRegistryName(new ResourceLocation(OverpoweredTechnology.MOD_ID, "grass_block"));
  }

  /*
  @Override
  public void onBlockDestroyedByPlayer(World world, BlockPos pos, IBlockState state) {
    if(world.isRemote == false){
      world.setBlockState(pos, Init.custom_grass_block.getDefaultState(), 2);
    }
  }
  */

}
