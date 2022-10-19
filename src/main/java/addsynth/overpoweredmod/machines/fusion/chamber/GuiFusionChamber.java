package addsynth.overpoweredmod.machines.fusion.chamber;

import addsynth.core.gui.GuiContainerBase;
import addsynth.overpoweredmod.game.reference.GuiReference;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public final class GuiFusionChamber extends GuiContainerBase<ContainerFusionChamber> {

  public GuiFusionChamber(final ContainerFusionChamber container, final PlayerInventory player_inventory, final ITextComponent title){
    super(container, player_inventory, title, GuiReference.fusion_chamber);
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY){
    guiUtil.draw_title(this.title);
  }

}
