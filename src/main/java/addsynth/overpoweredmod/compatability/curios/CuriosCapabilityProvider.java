package addsynth.overpoweredmod.compatability.curios;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.capability.CuriosCapability;
import top.theillusivec4.curios.api.capability.ICurio;

// copied from: https://github.com/TheIllusiveC4/Curios/blob/1.15.x/src/main/java/top/theillusivec4/curios/common/capability/CapCurioItem.java#L62
public final class CuriosCapabilityProvider implements ICapabilityProvider {

  final LazyOptional<ICurio> capability;
  
  public CuriosCapabilityProvider(ICurio curio){
    this.capability = LazyOptional.of(() -> curio);
  }
  
  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side){
    return CuriosCapability.ITEM.orEmpty(cap, capability);
  }

}
