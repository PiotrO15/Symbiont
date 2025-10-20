package piotro15.symbiont.common.registry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.common.block.*;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Symbiont.MOD_ID);

    public static final DeferredBlock<BioreactorBlock> BIOREACTOR = BLOCKS.register("bioreactor", () -> new BioreactorBlock(Block.Properties.of().strength(3.5f).noOcclusion()));
    public static final DeferredBlock<MetabolizerBlock> METABOLIZER = BLOCKS.register("metabolizer", () -> new MetabolizerBlock(Block.Properties.of().strength(3.5f).noOcclusion()));
    public static final DeferredBlock<BioformerBlock> BIOFORMER = BLOCKS.register("bioformer", () -> new BioformerBlock(Block.Properties.of().strength(3.5f).noOcclusion()));
    public static final DeferredBlock<CentrifugeBlock> CENTRIFUGE = BLOCKS.register("centrifuge", () -> new CentrifugeBlock(Block.Properties.of().strength(3.5f).noOcclusion()));
    public static final DeferredBlock<RecombinatorBlock> RECOMBINATOR = BLOCKS.register("recombinator", () -> new RecombinatorBlock(Block.Properties.of().strength(3.5f).noOcclusion()));

    public static final DeferredBlock<LiquidBlock> NUTRITIONAL_PASTE = BLOCKS.register("nutritional_paste", () -> new LiquidBlock(ModFluids.NUTRITIONAL_PASTE.get(), BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).replaceable().noCollission().randomTicks().strength(100.0F).pushReaction(PushReaction.DESTROY).noLootTable().liquid().sound(SoundType.EMPTY)));
    public static final DeferredBlock<LiquidBlock> SWEET_PASTE = BLOCKS.register("sweet_paste", () -> new LiquidBlock(ModFluids.SWEET_PASTE.get(), BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).replaceable().noCollission().randomTicks().strength(100.0F).pushReaction(PushReaction.DESTROY).noLootTable().liquid().sound(SoundType.EMPTY)));
    public static final DeferredBlock<LiquidBlock> PROTEIN_PASTE = BLOCKS.register("protein_paste", () -> new LiquidBlock(ModFluids.PROTEIN_PASTE.get(), BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).replaceable().noCollission().randomTicks().strength(100.0F).pushReaction(PushReaction.DESTROY).noLootTable().liquid().sound(SoundType.EMPTY)));
    public static final DeferredBlock<LiquidBlock> MYOGENIC_BIOMASS = BLOCKS.register("myogenic_biomass", () -> new LiquidBlock(ModFluids.MYOGENIC_BIOMASS.get(), BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).replaceable().noCollission().randomTicks().strength(100.0F).pushReaction(PushReaction.DESTROY).noLootTable().liquid().sound(SoundType.EMPTY)));
    public static final DeferredBlock<LiquidBlock> STICKY_PASTE = BLOCKS.register("sticky_paste", () -> new LiquidBlock(ModFluids.STICKY_PASTE.get(), BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).replaceable().noCollission().randomTicks().strength(100.0F).pushReaction(PushReaction.DESTROY).noLootTable().liquid().sound(SoundType.EMPTY)));
    public static final DeferredBlock<LiquidBlock> BIOPOLYMER_SOLUTION = BLOCKS.register("biopolymer_solution", () -> new LiquidBlock(ModFluids.BIOPOLYMER_SOLUTION.get(), BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).replaceable().noCollission().randomTicks().strength(100.0F).pushReaction(PushReaction.DESTROY).noLootTable().liquid().sound(SoundType.EMPTY)));
}
