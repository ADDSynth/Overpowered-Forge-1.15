package addsynth.core.game.blocks;

import addsynth.core.ADDSynthCore;
import addsynth.core.game.RegistryUtil;
import addsynth.core.gameplay.reference.Names;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public final class TestBlock extends Block {

  public TestBlock(){
    super(Block.Properties.create(Material.ROCK, MaterialColor.SNOW).hardnessAndResistance(0.2f, 6.0f));
    RegistryUtil.register_block(this, Names.TEST_BLOCK, ADDSynthCore.creative_tab);
  }

}
