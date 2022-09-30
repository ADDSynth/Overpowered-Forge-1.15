package addsynth.core.util.game.data;

import java.util.Set;
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
   * @param criteria_id Is the criteria id label in the advancement.json file
   */
  @Deprecated
  public static final void grantAdvancement(ServerPlayerEntity player, ResourceLocation advancement_id, String criteria_id){
    @SuppressWarnings("resource")
    final MinecraftServer server = player.getServer();
    if(server != null){
      final AdvancementManager manager = server.getAdvancementManager();
      final Advancement advancement = manager.getAdvancement(advancement_id);
      player.getAdvancements().grantCriterion(advancement, criteria_id);
    }
  }


  /** This grants the advancement using the first criteria of the advancement.
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

  /** This grants the advancement using the first criteria of the advancement.
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
        if(criteria_set.size() > 0){
          final String criteria = criteria_set.toArray(new String[criteria_set.size()])[0];
          player.getAdvancements().grantCriterion(advancement, criteria);
        }
      }
    }
  }

}
