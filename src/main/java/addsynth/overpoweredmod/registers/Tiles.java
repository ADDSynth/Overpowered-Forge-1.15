package addsynth.overpoweredmod.registers;

import addsynth.overpoweredmod.game.reference.OverpoweredBlocks;
import addsynth.overpoweredmod.machines.advanced_ore_refinery.TileAdvancedOreRefinery;
import addsynth.overpoweredmod.machines.black_hole.TileBlackHole;
import addsynth.overpoweredmod.machines.crystal_matter_generator.TileCrystalMatterGenerator;
import addsynth.overpoweredmod.machines.data_cable.TileDataCable;
import addsynth.overpoweredmod.machines.energy_extractor.TileCrystalEnergyExtractor;
import addsynth.overpoweredmod.machines.fusion.chamber.TileFusionChamber;
import addsynth.overpoweredmod.machines.fusion.converter.TileFusionEnergyConverter;
import addsynth.overpoweredmod.machines.gem_converter.TileGemConverter;
import addsynth.overpoweredmod.machines.identifier.TileIdentifier;
import addsynth.overpoweredmod.machines.inverter.TileInverter;
import addsynth.overpoweredmod.machines.laser.machine.TileLaserHousing;
import addsynth.overpoweredmod.machines.magic_infuser.TileMagicInfuser;
import addsynth.overpoweredmod.machines.matter_compressor.TileMatterCompressor;
import addsynth.overpoweredmod.machines.plasma_generator.TilePlasmaGenerator;
import addsynth.overpoweredmod.machines.portal.control_panel.TilePortalControlPanel;
import addsynth.overpoweredmod.machines.portal.frame.TilePortalFrame;
import addsynth.overpoweredmod.machines.portal.rift.TilePortal;
import addsynth.overpoweredmod.machines.suspension_bridge.TileSuspensionBridge;
import net.minecraft.tileentity.TileEntityType;

public final class Tiles {

  public static final TileEntityType<TileCrystalEnergyExtractor> CRYSTAL_ENERGY_EXTRACTOR =
    TileEntityType.Builder.create(TileCrystalEnergyExtractor::new, OverpoweredBlocks.crystal_energy_extractor).build(null);

  public static final TileEntityType<TileGemConverter> GEM_CONVERTER =
    TileEntityType.Builder.create(TileGemConverter::new, OverpoweredBlocks.gem_converter).build(null);

  public static final TileEntityType<TileInverter> INVERTER =
    TileEntityType.Builder.create(TileInverter::new, OverpoweredBlocks.inverter).build(null);

  public static final TileEntityType<TileMagicInfuser> MAGIC_INFUSER =
    TileEntityType.Builder.create(TileMagicInfuser::new, OverpoweredBlocks.magic_infuser).build(null);

  public static final TileEntityType<TileIdentifier> IDENTIFIER =
    TileEntityType.Builder.create(TileIdentifier::new, OverpoweredBlocks.identifier).build(null);

  public static final TileEntityType<TileAdvancedOreRefinery> ADVANCED_ORE_REFINERY =
    TileEntityType.Builder.create(TileAdvancedOreRefinery::new, OverpoweredBlocks.advanced_ore_refinery).build(null);

  public static final TileEntityType<TileCrystalMatterGenerator> CRYSTAL_MATTER_REPLICATOR =
    TileEntityType.Builder.create(TileCrystalMatterGenerator::new, OverpoweredBlocks.crystal_matter_generator).build(null);

  public static final TileEntityType<TileSuspensionBridge> ENERGY_SUSPENSION_BRIDGE =
    TileEntityType.Builder.create(TileSuspensionBridge::new, OverpoweredBlocks.energy_suspension_bridge).build(null);

  public static final TileEntityType<TileDataCable> DATA_CABLE =
    TileEntityType.Builder.create(TileDataCable::new, OverpoweredBlocks.data_cable).build(null);

  public static final TileEntityType<TilePortalControlPanel> PORTAL_CONTROL_PANEL =
    TileEntityType.Builder.create(TilePortalControlPanel::new, OverpoweredBlocks.portal_control_panel).build(null);

  public static final TileEntityType<TilePortalFrame> PORTAL_FRAME =
    TileEntityType.Builder.create(TilePortalFrame::new, OverpoweredBlocks.portal_frame).build(null);

  public static final TileEntityType<TilePortal> PORTAL_BLOCK =
    TileEntityType.Builder.create(TilePortal::new, OverpoweredBlocks.portal).build(null);

  public static final TileEntityType<TileLaserHousing> LASER_MACHINE =
    TileEntityType.Builder.create(TileLaserHousing::new, OverpoweredBlocks.laser_housing).build(null);

  public static final TileEntityType<TileFusionEnergyConverter> FUSION_ENERGY_CONVERTER =
    TileEntityType.Builder.create(TileFusionEnergyConverter::new, OverpoweredBlocks.fusion_converter).build(null);

  public static final TileEntityType<TileFusionChamber> FUSION_CHAMBER =
    TileEntityType.Builder.create(TileFusionChamber::new, OverpoweredBlocks.fusion_chamber).build(null);

  public static final TileEntityType<TileBlackHole> BLACK_HOLE =
    TileEntityType.Builder.create(TileBlackHole::new, OverpoweredBlocks.black_hole).build(null);

  public static final TileEntityType<TilePlasmaGenerator> PLASMA_GENERATOR =
    TileEntityType.Builder.create(TilePlasmaGenerator::new, OverpoweredBlocks.plasma_generator).build(null);

  public static final TileEntityType<TileMatterCompressor> MATTER_COMPRESSOR =
    TileEntityType.Builder.create(TileMatterCompressor::new, OverpoweredBlocks.matter_compressor).build(null);

}
