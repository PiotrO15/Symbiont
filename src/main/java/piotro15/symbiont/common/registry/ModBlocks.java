package piotro15.symbiont.common.registry;

import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.common.block.BioformerBlock;
import piotro15.symbiont.common.block.BioreactorBlock;
import piotro15.symbiont.common.block.RecombinatorBlock;
import piotro15.symbiont.common.block.MetabolizerBlock;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Symbiont.MOD_ID);

    public static final DeferredBlock<BioreactorBlock> BIOREACTOR = BLOCKS.register("bioreactor", () -> new BioreactorBlock(Block.Properties.of().strength(3.5f).noOcclusion()));
    public static final DeferredBlock<MetabolizerBlock> METABOLIZER = BLOCKS.register("metabolizer", () -> new MetabolizerBlock(Block.Properties.of().strength(3.5f).noOcclusion()));
    public static final DeferredBlock<BioformerBlock> BIOFORMER = BLOCKS.register("bioformer", () -> new BioformerBlock(Block.Properties.of().strength(3.5f).noOcclusion()));
    public static final DeferredBlock<RecombinatorBlock> RECOMBINATOR = BLOCKS.register("recombinator", () -> new RecombinatorBlock(Block.Properties.of().strength(3.5f).noOcclusion()));
}
