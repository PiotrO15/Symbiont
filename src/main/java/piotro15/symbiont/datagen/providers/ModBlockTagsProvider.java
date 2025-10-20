package piotro15.symbiont.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.common.registry.ModBlocks;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends IntrinsicHolderTagsProvider<Block> {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, Registries.BLOCK, lookupProvider, block -> block.builtInRegistryHolder().key(), Symbiont.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.BIOREACTOR.get())
                .add(ModBlocks.METABOLIZER.get())
                .add(ModBlocks.BIOFORMER.get())
                .add(ModBlocks.CENTRIFUGE.get())
                .add(ModBlocks.RECOMBINATOR.get());
    }
}
