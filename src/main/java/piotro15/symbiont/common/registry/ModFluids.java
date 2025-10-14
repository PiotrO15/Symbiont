package piotro15.symbiont.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.registries.DeferredRegister;
import piotro15.symbiont.common.Symbiont;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, Symbiont.MOD_ID);

//    public static final DeferredHolder<Fluid, Fluid> SWEET_PASTE = FLUIDS.register("sweet_paste", () -> new FlowingFluid() {
//    })
}
