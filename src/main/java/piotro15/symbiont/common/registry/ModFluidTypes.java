package piotro15.symbiont.common.registry;

import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import piotro15.symbiont.common.Symbiont;

public class ModFluidTypes {
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, Symbiont.MOD_ID);

    public static final DeferredHolder<FluidType, FluidType> NUTRITIONAL_PASTE =
            FLUID_TYPES.register("nutritional_paste", () -> new FluidType(FluidType.Properties.create()));

    public static final DeferredHolder<FluidType, FluidType> SWEET_PASTE =
            FLUID_TYPES.register("sweet_paste", () -> new FluidType(FluidType.Properties.create()));
}
