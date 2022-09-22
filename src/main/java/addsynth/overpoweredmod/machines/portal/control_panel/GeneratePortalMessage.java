package addsynth.overpoweredmod.machines.portal.control_panel;

import java.util.function.Supplier;
import addsynth.core.util.game.MinecraftUtility;
import addsynth.core.util.game.data.AdvancementUtil;
import addsynth.overpoweredmod.assets.CustomAdvancements;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

public final class GeneratePortalMessage {

  private final BlockPos position;

  public GeneratePortalMessage(final BlockPos position){
    this.position = position;
  }

  public static final void encode(final GeneratePortalMessage message, final PacketBuffer buf){
    buf.writeInt(message.position.getX());
    buf.writeInt(message.position.getY());
    buf.writeInt(message.position.getZ());
  }

  public static final GeneratePortalMessage decode(final PacketBuffer buf){
    return new GeneratePortalMessage(new BlockPos(buf.readInt(),buf.readInt(),buf.readInt()));
  }

  public static void handle(final GeneratePortalMessage message, final Supplier<NetworkEvent.Context> context_supplier){
    final NetworkEvent.Context context = context_supplier.get();
    final ServerPlayerEntity player = context.getSender();
    if(player != null){
      context.enqueueWork(() -> {
        @SuppressWarnings("resource")
        final ServerWorld world = player.getServerWorld();
        if(world.isAreaLoaded(message.position, 0)){
          final TilePortalControlPanel tile = MinecraftUtility.getTileEntity(message.position, world, TilePortalControlPanel.class);
          if(tile != null){
            tile.generate_portal();
            AdvancementUtil.grantAdvancement(player, CustomAdvancements.PORTAL);
          }
        }
      });
      context.setPacketHandled(true);
    }
  }

}
