package addsynth.energy.gameplay.machines.universal_energy_interface;

import java.util.function.Supplier;
import addsynth.core.util.game.MinecraftUtility;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

public final class CycleTransferModeMessage {

  private final BlockPos position;

  public CycleTransferModeMessage(final BlockPos position){
    this.position = position;
  }

  public static final void encode(final CycleTransferModeMessage message, final PacketBuffer buf){
    buf.writeInt(message.position.getX());
    buf.writeInt(message.position.getY());
    buf.writeInt(message.position.getZ());
  }

  public static final CycleTransferModeMessage decode(final PacketBuffer buf){
    return new CycleTransferModeMessage(new BlockPos(buf.readInt(),buf.readInt(),buf.readInt()));
  }

  public static void handle(final CycleTransferModeMessage message, final Supplier<NetworkEvent.Context> context_supplier){
    final NetworkEvent.Context context = context_supplier.get();
    final ServerPlayerEntity player = context.getSender();
    if(player != null){
      @SuppressWarnings("resource")
      final ServerWorld world = player.getServerWorld();
      context.enqueueWork(() -> {
        if(world.isAreaLoaded(message.position, 0)){
          final TileUniversalEnergyInterface tile = MinecraftUtility.getTileEntity(message.position, world, TileUniversalEnergyInterface.class);
          if(tile != null){
            tile.set_next_transfer_mode();
          }
        }
      });
      context.setPacketHandled(true);
    }
  }

}
