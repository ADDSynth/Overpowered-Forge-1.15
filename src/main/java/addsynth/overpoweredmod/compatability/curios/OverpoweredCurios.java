package addsynth.overpoweredmod.compatability.curios;

import addsynth.core.compat.Compatibility;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.config.Features;
import net.minecraftforge.fml.InterModComms;
import top.theillusivec4.curios.api.CuriosAPI;
import top.theillusivec4.curios.api.imc.CurioIMCMessage;

public final class OverpoweredCurios {

  public static final void register_slots(){
    final String sender  = OverpoweredTechnology.MOD_ID;
    final String mod     = Compatibility.CURIOS.modid;
    final String message = CuriosAPI.IMC.REGISTER_TYPE;
    // Register Curios Slots. Slot Type Identifier should be as generic as possible.
    // Mods which register the same identifier will be merged together.
    // Size determines the default number of slots. Final number will equal the mod that
    // registers the biggest size.
    // Common Slot Type Identifiers: https://github.com/TheIllusiveC4/Curios/wiki/Frequently-Used-Slots
    if(Features.rings.get()){
      InterModComms.sendTo(sender, mod, message, () -> new CurioIMCMessage("ring").setSize(2));
    }
    InterModComms.sendTo(sender, mod, message, () -> new CurioIMCMessage("charm"));
  }

}
