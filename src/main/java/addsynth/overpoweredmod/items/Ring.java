package addsynth.overpoweredmod.items;

import java.util.List;
import javax.annotation.Nullable;
import addsynth.core.game.items.ItemValue;
import addsynth.overpoweredmod.compatability.curios.CuriosCapabilityProvider;
import addsynth.overpoweredmod.compatability.curios.RingEffects;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import top.theillusivec4.curios.api.capability.ICurio;

public final class Ring extends OverpoweredItem {

  public Ring(final int ring_id){
    super("magic_ring_"+ring_id, new Item.Properties().maxStackSize(1));
  }

  @Override
  @Nullable
  public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt){
    return new CuriosCapabilityProvider(
      new ICurio(){
      
        @Override
        public void onEquipped(String identifier, LivingEntity livingEntity){
        }
        
        @Override
        public void onCurioTick(String identifier, int index, LivingEntity livingEntity){
          if(livingEntity.world.isRemote == false){
            // Example Ring in the Curios mod checks the livingEntity.ticksExisted and modulos it with 20,
            // then Re-adds the effect every second. This is far superior than checking to see if the
            // entity has the effect, and then if the effect is about to run out.
            // But we still need the special case for the Extra Health effect.
            RingEffects.checkEntityHasEffect(stack, livingEntity);
          }
        }
        
        @Override
        public void onUnequipped(String identifier, LivingEntity livingEntity){
          RingEffects.removeEffectsFromEntity(stack, livingEntity);
        }
        
      }
    );
  }

  @Override
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn){
    RingEffects.assignToolTip(stack, tooltip);
  }

  @Override
  public Rarity getRarity(ItemStack stack){
    return ItemValue.values()[RingEffects.get_ring_rarity(stack)].rarity;
  }

  @Override
  public boolean hasEffect(final ItemStack stack){
    return RingEffects.get_ring_rarity(stack) >= ItemValue.EPIC.value;
  }

}
