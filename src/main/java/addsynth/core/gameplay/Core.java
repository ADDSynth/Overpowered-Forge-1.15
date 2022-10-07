package addsynth.core.gameplay;

import addsynth.core.game.blocks.TestBlock;
import addsynth.core.gameplay.blocks.CautionBlock;
import addsynth.core.gameplay.items.ScytheTool;
import addsynth.core.gameplay.music_box.MusicBox;
import addsynth.core.gameplay.music_box.MusicSheet;
import addsynth.core.gameplay.registers.Names;
import addsynth.core.gameplay.team_manager.TeamManagerBlock;
import net.minecraft.block.Block;
import net.minecraft.item.ItemTier;

public final class Core {

  public static final TestBlock        test_block              = new TestBlock();

  public static final Block            caution_block           = new CautionBlock();
  public static final MusicBox         music_box               = new MusicBox();
  public static final MusicSheet       music_sheet             = new MusicSheet();
  public static final TeamManagerBlock team_manager            = new TeamManagerBlock();

  public static final ScytheTool       wooden_scythe           = new ScytheTool(Names.WOODEN_SCYTHE,  ItemTier.WOOD);
  public static final ScytheTool       stone_scythe            = new ScytheTool(Names.STONE_SCYTHE,   ItemTier.STONE);
  public static final ScytheTool       iron_scythe             = new ScytheTool(Names.IRON_SCYTHE,    ItemTier.IRON);
  public static final ScytheTool       gold_scythe             = new ScytheTool(Names.GOLD_SCYTHE,    ItemTier.GOLD);
  public static final ScytheTool       diamond_scythe          = new ScytheTool(Names.DIAMOND_SCYTHE, ItemTier.DIAMOND);

}
