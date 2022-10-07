package addsynth.overpoweredmod.game.core;

import addsynth.core.game.items.ArmorMaterial;
import addsynth.core.game.items.EquipmentType;
import addsynth.core.game.items.Toolset;
import addsynth.core.gameplay.items.ScytheTool;
import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.assets.CreativeTabs;
import addsynth.overpoweredmod.compatability.CompatabilityManager;
import addsynth.overpoweredmod.game.Names;
import addsynth.overpoweredmod.items.tools.*;
import addsynth.overpoweredmod.items.Ring;
import addsynth.overpoweredmod.items.UnidentifiedItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;

public final class Tools {

  static {
    Debug.log_setup_info("Begin loading Tools class...");
  }

  public static final Toolset overpowered_tools = new Toolset(
    new OverpoweredSword(),
    new OverpoweredShovel(),
    new OverpoweredPickaxe(),
    new OverpoweredAxe(),
    new OverpoweredHoe(),
    Init.celestial_gem
  );
    
  public static final ScytheTool overpowered_scythe =
  new ScytheTool(Names.CELESTIAL_SCYTHE, OverpoweredTiers.CELESTIAL, new Item.Properties().group(CreativeTabs.tools_creative_tab)){
    @Override
    public boolean isEnchantable(final ItemStack stack){
      return false;
    }
  
    @Override
    public Rarity getRarity(final ItemStack stack){
      return Rarity.RARE;
    }
  };

  public static final Toolset void_toolset = new Toolset(
    new NullSword(),
    new NullShovel(),
    new NullPickaxe(),
    new NullAxe(),
    new NullHoe(),
    Init.void_crystal
  );

  public static final Item[][] unidentified_armor = new Item[5][4];

  static {
    for(ArmorMaterial material : ArmorMaterial.values()){
      for(EquipmentType type : EquipmentType.values()){
        unidentified_armor[material.ordinal()][type.ordinal()] = new UnidentifiedItem(material, type);
      }
    }
  }

  public static final Item[] ring = CompatabilityManager.are_rings_enabled() ? new Item[] {
    new UnidentifiedItem(0),
    new UnidentifiedItem(1),
    new UnidentifiedItem(2),
    new UnidentifiedItem(3)
  } : null;

  public static final Item[] magic_ring = CompatabilityManager.are_rings_enabled() ? new Item[] {
    new Ring(0),
    new Ring(1),
    new Ring(2),
    new Ring(3)
  } : null;

  static {
    Debug.log_setup_info("Finished loading Tools class.");
  }

}
