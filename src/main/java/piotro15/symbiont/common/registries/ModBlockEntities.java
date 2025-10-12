package piotro15.symbiont.common.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.common.blocks.entities.BioreactorBlockEntity;
import piotro15.symbiont.common.blocks.entities.CellEditorBlockEntity;
import piotro15.symbiont.common.blocks.entities.MachineWorkerBlockEntity;
import piotro15.symbiont.common.blocks.entities.MetabolizerBlockEntity;

public class ModBlockEntities {
    public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Symbiont.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MachineWorkerBlockEntity>> MACHINE_WORKER = BLOCK_ENTITY_TYPE.register("machine_worker", () ->
            BlockEntityType.Builder.of(MachineWorkerBlockEntity::new, ModBlocks.BIOREACTOR.get(), ModBlocks.CELL_EDITOR.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BioreactorBlockEntity>> BIOREACTOR = BLOCK_ENTITY_TYPE.register("bioreactor", () ->
            BlockEntityType.Builder.of(BioreactorBlockEntity::new, ModBlocks.BIOREACTOR.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MetabolizerBlockEntity>> METABOLIZER = BLOCK_ENTITY_TYPE.register("metabolizer", () ->
            BlockEntityType.Builder.of(MetabolizerBlockEntity::new, ModBlocks.METABOLIZER.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CellEditorBlockEntity>> CELL_EDITOR = BLOCK_ENTITY_TYPE.register("cell_editor", () ->
            BlockEntityType.Builder.of(CellEditorBlockEntity::new, ModBlocks.CELL_EDITOR.get()).build(null));
}
