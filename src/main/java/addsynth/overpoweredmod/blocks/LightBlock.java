package addsynth.overpoweredmod.blocks;

import java.text.NumberFormat;
import java.util.List;
import javax.annotation.Nullable;
import addsynth.core.game.RegistryUtil;
import addsynth.overpoweredmod.assets.CreativeTabs;
import addsynth.overpoweredmod.config.MachineValues;
import addsynth.overpoweredmod.game.reference.Names;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

public final class LightBlock extends Block {

  public LightBlock(){
    super(Block.Properties.create(Material.ROCK, MaterialColor.QUARTZ).lightValue(15).hardnessAndResistance(5.0f, 6.0f).harvestTool(ToolType.PICKAXE).harvestLevel(2));
    RegistryUtil.register_block(this, Names.LIGHT_BLOCK, CreativeTabs.creative_tab);
  }

  @Override
  public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn){
    final String energy = NumberFormat.getIntegerInstance().format(MachineValues.light_block_energy.get());
    tooltip.add(new TranslationTextComponent("gui.addsynth_energy.tooltip.energy", energy).applyTextStyle(TextFormatting.AQUA));
  }

}
