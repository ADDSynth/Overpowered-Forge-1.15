package addsynth.overpoweredmod.machines.suspension_bridge;

import java.util.function.Supplier;
import addsynth.core.util.game.MinecraftUtility;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

public final class RotateBridgeMessage {

  private final BlockPos position;
  
  public RotateBridgeMessage(final BlockPos position){
    this.position = position;
  }
  
  public static final void encode(final RotateBridgeMessage message, final PacketBuffer buf){
    buf.writeInt(message.position.getX());
    buf.writeInt(message.position.getY());
    buf.writeInt(message.position.getZ());
  }
  
  public static final RotateBridgeMessage decode(final PacketBuffer buf){
    return new RotateBridgeMessage(new BlockPos(buf.readInt(), buf.readInt(), buf.readInt()));
  }

  public static final void handle(final RotateBridgeMessage message, final Supplier<NetworkEvent.Context> context_supplier){
    final NetworkEvent.Context context = context_supplier.get();
    final ServerPlayerEntity player = context.getSender();
    if(player != null){
      context.enqueueWork(() -> {
        @SuppressWarnings("resource")
        final ServerWorld world = player.getServerWorld();
        if(world.isAreaLoaded(message.position, 0)){
          final TileSuspensionBridge tile = MinecraftUtility.getTileEntity(message.position, world, TileSuspensionBridge.class);
          if(tile != null){
            final BridgeNetwork bridge_network = tile.getBlockNetwork();
            if(bridge_network != null){
              bridge_network.rotate();
            }
          }
        }
      });
      context.setPacketHandled(true);
    }
  }

}
