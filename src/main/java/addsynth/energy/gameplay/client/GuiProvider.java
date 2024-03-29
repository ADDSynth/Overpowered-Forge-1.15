package addsynth.energy.gameplay.client;

import addsynth.energy.gameplay.machines.energy_diagnostics.GuiEnergyDiagnostics;
import addsynth.energy.gameplay.machines.energy_diagnostics.TileEnergyDiagnostics;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TranslationTextComponent;

public final class GuiProvider {

  @SuppressWarnings("resource")
  public static final void openEnergyDiagnostics(final TileEnergyDiagnostics tile, final String title){
    Minecraft.getInstance().displayGuiScreen(new GuiEnergyDiagnostics(tile, new TranslationTextComponent(title)));
  }

}
