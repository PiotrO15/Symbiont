package piotro15.symbiont.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.common.block.entity.*;

public class ModBlockEntities {
    public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Symbiont.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MachineWorkerBlockEntity>> MACHINE_WORKER = BLOCK_ENTITY_TYPE.register("machine_worker", () ->
            BlockEntityType.Builder.of(MachineWorkerBlockEntity::new, ModBlocks.BIOREACTOR.get(), ModBlocks.RECOMBINATOR.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BioreactorBlockEntity>> BIOREACTOR = BLOCK_ENTITY_TYPE.register("bioreactor", () ->
            BlockEntityType.Builder.of(BioreactorBlockEntity::new, ModBlocks.BIOREACTOR.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MetabolizerBlockEntity>> METABOLIZER = BLOCK_ENTITY_TYPE.register("metabolizer", () ->
            BlockEntityType.Builder.of(MetabolizerBlockEntity::new, ModBlocks.METABOLIZER.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BioformerBlockEntity>> BIOFORMER = BLOCK_ENTITY_TYPE.register("bioformer", () ->
            BlockEntityType.Builder.of(BioformerBlockEntity::new, ModBlocks.BIOFORMER.get()).build(null));


    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RecombinatorBlockEntity>> RECOMBINATOR = BLOCK_ENTITY_TYPE.register("recombinator", () ->
            BlockEntityType.Builder.of(RecombinatorBlockEntity::new, ModBlocks.RECOMBINATOR.get()).build(null));
}
