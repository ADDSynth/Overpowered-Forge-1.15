package addsynth.overpoweredmod.machines.energy_extractor;

import javax.annotation.Nullable;
import addsynth.core.game.RegistryUtil;
import addsynth.energy.lib.tiles.energy.TileStandardGenerator;
import addsynth.overpoweredmod.config.MachineValues;
import addsynth.overpoweredmod.game.reference.OverpoweredBlocks;
import addsynth.overpoweredmod.game.reference.OverpoweredItems;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public final class TileCrystalEnergyExtractor extends TileStandardGenerator implements INamedContainerProvider {

  public static final Item[] input_filter = new Item[] {
    OverpoweredItems.energy_crystal_shards,
    OverpoweredItems.energy_crystal,
    Item.BLOCK_TO_ITEM.get(OverpoweredBlocks.light_block)
  };

  public TileCrystalEnergyExtractor(){
    super(Tiles.CRYSTAL_ENERGY_EXTRACTOR, input_filter);
  }

  @Override
  protected final void setGeneratorData(){
    final Item item = input_inventory.extractItem(0, 1, false).getItem();
    if(item == OverpoweredItems.energy_crystal){
      energy.setEnergyAndCapacity(MachineValues.energy_crystal_energy.get());
      energy.setMaxExtract(MachineValues.energy_crystal_max_extract.get());
    }
    if(item == OverpoweredItems.energy_crystal_shards){
      energy.setEnergyAndCapacity(MachineValues.energy_crystal_shards_energy.get());
      energy.setMaxExtract(MachineValues.energy_crystal_shards_max_extract.get());
    }
    if(item == RegistryUtil.getItemBlock(OverpoweredBlocks.light_block)){
      energy.setEnergyAndCapacity(MachineValues.light_block_energy.get());
      energy.setMaxExtract(MachineValues.light_block_max_extract.get());
    }
  }

  @Override
  @Nullable
  public Container createMenu(final int windowID, final PlayerInventory player_inventory, final PlayerEntity player){
    return new ContainerCrystalEnergyExtractor(windowID, player_inventory, this);
  }

  @Override
  public ITextComponent getDisplayName(){
    return new TranslationTextComponent(getBlockState().getBlock().getTranslationKey());
  }

}
