package piotro15.symbiont.common.registries;

import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.common.blocks.BioreactorBlock;
import piotro15.symbiont.common.blocks.CellEditorBlock;
import piotro15.symbiont.common.blocks.MetabolizerBlock;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Symbiont.MOD_ID);

    public static final DeferredBlock<BioreactorBlock> BIOREACTOR = BLOCKS.register("bioreactor", () -> new BioreactorBlock(Block.Properties.of().strength(3.5f).noOcclusion()));
    public static final DeferredBlock<MetabolizerBlock> METABOLIZER = BLOCKS.register("metabolizer", () -> new MetabolizerBlock(Block.Properties.of().strength(3.5f).noOcclusion()));
    public static final DeferredBlock<CellEditorBlock> CELL_EDITOR = BLOCKS.register("cell_editor", () -> new CellEditorBlock(Block.Properties.of().strength(3.5f).noOcclusion()));
}
