package piotro15.symbiont.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import piotro15.symbiont.common.Symbiont;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, Symbiont.MOD_ID);

    public static final DeferredHolder<Fluid, FlowingFluid> NUTRITIONAL_PASTE =
            FLUIDS.register("nutritional_paste",
                    () -> new BaseFlowingFluid.Source(ModFluids.NUTRITIONAL_PASTE_PROPERTIES));
    public static final DeferredHolder<Fluid, FlowingFluid> FLOWING_NUTRITIONAL_PASTE =
            FLUIDS.register("flowing_nutritional_paste",
                    () -> new BaseFlowingFluid.Flowing(ModFluids.NUTRITIONAL_PASTE_PROPERTIES));

    private static final BaseFlowingFluid.Properties NUTRITIONAL_PASTE_PROPERTIES =
            new BaseFlowingFluid.Properties(
                    ModFluidTypes.NUTRITIONAL_PASTE,
                    NUTRITIONAL_PASTE,
                    FLOWING_NUTRITIONAL_PASTE
            )
                    .block(ModBlocks.NUTRITIONAL_PASTE)
                    .bucket(ModItems.NUTRITIONAL_PASTE_BUCKET)
                    .slopeFindDistance(4)
                    .levelDecreasePerBlock(2)
                    .explosionResistance(100f)
                    .tickRate(30);

    public static final DeferredHolder<Fluid, FlowingFluid> SWEET_PASTE =
            FLUIDS.register("sweet_paste",
                    () -> new BaseFlowingFluid.Source(ModFluids.SWEET_PASTE_PROPERTIES));
    public static final DeferredHolder<Fluid, FlowingFluid> FLOWING_SWEET_PASTE =
            FLUIDS.register("flowing_sweet_paste",
                    () -> new BaseFlowingFluid.Flowing(ModFluids.SWEET_PASTE_PROPERTIES));

    private static final BaseFlowingFluid.Properties SWEET_PASTE_PROPERTIES =
            new BaseFlowingFluid.Properties(
                    ModFluidTypes.SWEET_PASTE,
                    SWEET_PASTE,
                    FLOWING_SWEET_PASTE
            )
                    .block(ModBlocks.SWEET_PASTE)
                    .bucket(ModItems.SWEET_PASTE_BUCKET)
                    .slopeFindDistance(4)
                    .levelDecreasePerBlock(2)
                    .explosionResistance(100f)
                    .tickRate(30);

    public static final DeferredHolder<Fluid, FlowingFluid> PROTEIN_PASTE =
            FLUIDS.register("protein_paste",
                    () -> new BaseFlowingFluid.Source(ModFluids.PROTEIN_PASTE_PROPERTIES));
    public static final DeferredHolder<Fluid, FlowingFluid> FLOWING_PROTEIN_PASTE =
            FLUIDS.register("flowing_protein_paste",
                    () -> new BaseFlowingFluid.Flowing(ModFluids.PROTEIN_PASTE_PROPERTIES));

    private static final BaseFlowingFluid.Properties PROTEIN_PASTE_PROPERTIES =
            new BaseFlowingFluid.Properties(
                    ModFluidTypes.PROTEIN_PASTE,
                    PROTEIN_PASTE,
                    FLOWING_PROTEIN_PASTE
            )
                    .block(ModBlocks.PROTEIN_PASTE)
                    .bucket(ModItems.PROTEIN_PASTE_BUCKET)
                    .slopeFindDistance(4)
                    .levelDecreasePerBlock(2)
                    .explosionResistance(100f)
                    .tickRate(30);

    public static final DeferredHolder<Fluid, FlowingFluid> MYOGENIC_BIOMASS =
            FLUIDS.register("myogenic_biomass",
                    () -> new BaseFlowingFluid.Source(ModFluids.MYOGENIC_BIOMASS_PROPERTIES));
    public static final DeferredHolder<Fluid, FlowingFluid> FLOWING_MYOGENIC_BIOMASS =
            FLUIDS.register("flowing_myogenic_biomass",
                    () -> new BaseFlowingFluid.Flowing(ModFluids.MYOGENIC_BIOMASS_PROPERTIES));

    private static final BaseFlowingFluid.Properties MYOGENIC_BIOMASS_PROPERTIES =
            new BaseFlowingFluid.Properties(
                    ModFluidTypes.MYOGENIC_BIOMASS,
                    MYOGENIC_BIOMASS,
                    FLOWING_MYOGENIC_BIOMASS
            )
                    .block(ModBlocks.MYOGENIC_BIOMASS)
                    .bucket(ModItems.MYOGENIC_BIOMASS_BUCKET)
                    .slopeFindDistance(4)
                    .levelDecreasePerBlock(2)
                    .explosionResistance(100f)
                    .tickRate(30);

    public static final DeferredHolder<Fluid, FlowingFluid> BIOPOLYMER_SOLUTION =
            FLUIDS.register("biopolymer_solution",
                    () -> new BaseFlowingFluid.Source(ModFluids.BIOPOLYMER_SOLUTION_PROPERTIES));
    public static final DeferredHolder<Fluid, FlowingFluid> FLOWING_BIOPOLYMER_SOLUTION =
            FLUIDS.register("flowing_biopolymer_solution",
                    () -> new BaseFlowingFluid.Flowing(ModFluids.BIOPOLYMER_SOLUTION_PROPERTIES));

    private static final BaseFlowingFluid.Properties BIOPOLYMER_SOLUTION_PROPERTIES =
            new BaseFlowingFluid.Properties(
                    ModFluidTypes.BIOPOLYMER_SOLUTION,
                    BIOPOLYMER_SOLUTION,
                    FLOWING_BIOPOLYMER_SOLUTION
            )
                    .block(ModBlocks.BIOPOLYMER_SOLUTION)
                    .bucket(ModItems.BIOPOLYMER_SOLUTION_BUCKET)
                    .slopeFindDistance(4)
                    .levelDecreasePerBlock(2)
                    .explosionResistance(100f)
                    .tickRate(30);

    public static final DeferredHolder<Fluid, FlowingFluid> STICKY_PASTE =
            FLUIDS.register("sticky_paste",
                    () -> new BaseFlowingFluid.Source(ModFluids.STICKY_PASTE_PROPERTIES));
    public static final DeferredHolder<Fluid, FlowingFluid> FLOWING_STICKY_PASTE =
            FLUIDS.register("flowing_sticky_paste",
                    () -> new BaseFlowingFluid.Flowing(ModFluids.STICKY_PASTE_PROPERTIES));

    private static final BaseFlowingFluid.Properties STICKY_PASTE_PROPERTIES =
            new BaseFlowingFluid.Properties(
                    ModFluidTypes.STICKY_PASTE,
                    STICKY_PASTE,
                    FLOWING_STICKY_PASTE
            )
                    .block(ModBlocks.STICKY_PASTE)
                    .bucket(ModItems.STICKY_PASTE_BUCKET)
                    .slopeFindDistance(4)
                    .levelDecreasePerBlock(2)
                    .explosionResistance(100f)
                    .tickRate(30);
}
