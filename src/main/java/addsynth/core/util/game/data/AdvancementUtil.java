package addsynth.core.util.game.data;

import java.util.Set;
import addsynth.core.ADDSynthCore;
import addsynth.core.util.player.PlayerUtil;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public final class AdvancementUtil {

  /** This grants the player the criteria you specify.
   * @param player
   * @param advancement_id the ResourceLocation of the advancement (location of the file)
   * @param criteria_id Is the criteria ID label in the advancement.json file
   */
  public static final void grantAdvancement(ServerPlayerEntity player, ResourceLocation advancement_id, String criteria_id){
    @SuppressWarnings("resource")
    final MinecraftServer server = player.getServer();
    if(server != null){
      final AdvancementManager manager = server.getAdvancementManager();
      final Advancement advancement = manager.getAdvancement(advancement_id);
      if(advancement != null){
        player.getAdvancements().grantCriterion(advancement, criteria_id);
      }
      else{
        ADDSynthCore.log.error(new NullPointerException("Advancement "+advancement_id.toString()+" doesn't exist."));
      }
    }
  }


  /** This grants the advancement using all of the criteria in the advancement.
   * @param player_name
   * @param world
   * @param advancement
   */
  public static final void grantAdvancement(String player_name, World world, ResourceLocation advancement){
    final ServerPlayerEntity player = PlayerUtil.getPlayer(world, player_name);
    if(player != null){
      grantAdvancement(player, advancement);
    }
  }

  /** This grants the advancement using all of the criteria in the advancement.
   * @param player
   * @param advancement_id the ResourceLocation of the advancement (location of the file)
   */
  public static final void grantAdvancement(ServerPlayerEntity player, ResourceLocation advancement_id){
    @SuppressWarnings("resource")
    final MinecraftServer server = player.getServer();
    if(server != null){
      final AdvancementManager manager = server.getAdvancementManager();
      final Advancement advancement = manager.getAdvancement(advancement_id);
      if(advancement != null){
        final Set<String> criteria_set = advancement.getCriteria().keySet();
        for(String criteria : criteria_set){
          player.getAdvancements().grantCriterion(advancement, criteria);
        }
      }
      else{
        ADDSynthCore.log.error(new NullPointerException("Advancement "+advancement_id.toString()+" doesn't exist."));
      }
    }
  }

}
