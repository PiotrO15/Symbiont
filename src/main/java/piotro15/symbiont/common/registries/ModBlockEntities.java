package piotro15.symbiont.common.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.common.blocks.entities.BioreactorBlockEntity;
import piotro15.symbiont.common.blocks.entities.CellEditorBlockEntity;

public class ModBlockEntities {
    public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Symbiont.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BioreactorBlockEntity>> BIOREACTOR = BLOCK_ENTITY_TYPE.register("bioreactor", () ->
            BlockEntityType.Builder.of(BioreactorBlockEntity::new, ModBlocks.BIOREACTOR.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CellEditorBlockEntity>> CELL_EDITOR = BLOCK_ENTITY_TYPE.register("cell_editor", () ->
            BlockEntityType.Builder.of(CellEditorBlockEntity::new, ModBlocks.CELL_EDITOR.get()).build(null));
}
