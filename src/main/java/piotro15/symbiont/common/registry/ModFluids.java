package piotro15.symbiont.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import piotro15.symbiont.common.Symbiont;

import java.util.function.Supplier;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, Symbiont.MOD_ID);

    public static final DeferredHolder<Fluid, FlowingFluid> NUTRITIONAL_PASTE = registerFlowingFluid("nutritional_paste");
    public static final DeferredHolder<Fluid, FlowingFluid> SWEET_PASTE = registerFlowingFluid("sweet_paste");
    public static final DeferredHolder<Fluid, FlowingFluid> PROTEIN_PASTE = registerFlowingFluid("protein_paste");
    public static final DeferredHolder<Fluid, FlowingFluid> MYOGENIC_BIOMASS = registerFlowingFluid("myogenic_biomass");
    public static final DeferredHolder<Fluid, FlowingFluid> BIOPOLYMER_SOLUTION = registerFlowingFluid("biopolymer_solution");
    public static final DeferredHolder<Fluid, FlowingFluid> STICKY_PASTE = registerFlowingFluid("sticky_paste");
    public static final DeferredHolder<Fluid, FlowingFluid> FERRIC_SOLUTION = registerFlowingFluid("ferric_solution");
    public static final DeferredHolder<Fluid, FlowingFluid> CUPRIC_SOLUTION = registerFlowingFluid("cupric_solution");
    public static final DeferredHolder<Fluid, FlowingFluid> AURIC_SOLUTION = registerFlowingFluid("auric_solution");
    public static final DeferredHolder<Fluid, FlowingFluid> FERRIC_PASTE = registerFlowingFluid("ferric_paste");
    public static final DeferredHolder<Fluid, FlowingFluid> CUPRIC_PASTE = registerFlowingFluid("cupric_paste");
    public static final DeferredHolder<Fluid, FlowingFluid> ENRICHED_CUPRIC_SOLUTION = registerFlowingFluid("enriched_cupric_solution");
    public static final DeferredHolder<Fluid, FlowingFluid> MARINE_EXTRACT = registerFlowingFluid("marine_extract");

    private static DeferredHolder<Fluid, FlowingFluid> registerFlowingFluid(String name) {
        final DeferredHolder<Fluid, FlowingFluid>[] sourceHolder = new DeferredHolder[1];
        final DeferredHolder<Fluid, FlowingFluid>[] flowingHolder = new DeferredHolder[1];
        final DeferredBlock<LiquidBlock>[] blockHolder = new DeferredBlock[1];
        final DeferredItem<BucketItem>[] bucketHolder = new DeferredItem[1];
        final DeferredHolder<FluidType, FluidType> fluidTypeHolder = ModFluidTypes.FLUID_TYPES.register(name, () -> new FluidType(FluidType.Properties.create()));

        Supplier<BaseFlowingFluid.Properties> propertiesSupplier = () ->
                new BaseFlowingFluid.Properties(fluidTypeHolder, sourceHolder[0], flowingHolder[0])
                        .block(blockHolder[0])
                        .bucket(bucketHolder[0])
                        .slopeFindDistance(4)
                        .levelDecreasePerBlock(2)
                        .explosionResistance(100f)
                        .tickRate(30);

        sourceHolder[0] = FLUIDS.register(name,
                () -> new BaseFlowingFluid.Source(propertiesSupplier.get()));
        flowingHolder[0] = FLUIDS.register("flowing_" + name,
                () -> new BaseFlowingFluid.Flowing(propertiesSupplier.get()));
        blockHolder[0] = ModBlocks.BLOCKS.register(name, () -> new LiquidBlock(sourceHolder[0].get(), BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).replaceable().noCollission().randomTicks().strength(100.0F).pushReaction(PushReaction.DESTROY).noLootTable().liquid().sound(SoundType.EMPTY)));
        bucketHolder[0] = ModItems.ITEMS.register(name + "_bucket", () -> new BucketItem(sourceHolder[0].get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
        return sourceHolder[0];
    }
}
