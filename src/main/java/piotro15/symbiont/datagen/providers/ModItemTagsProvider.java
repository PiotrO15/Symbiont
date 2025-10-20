package piotro15.symbiont.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.common.registry.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper, CompletableFuture<TagLookup<Block>> blockTags) {
        super(output, lookupProvider, blockTags, Symbiont.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(ModTags.Items.STABILITY_CATALYSTS)
                .add(Items.REDSTONE);

        tag(ModTags.Items.METABOLISM_CATALYSTS)
                .add(Items.GLOW_BERRIES);

        tag(ModTags.Items.REPLICATION_CATALYSTS)
                .add(Items.AMETHYST_SHARD);

        tag(ModTags.Items.ADAPTABILITY_CATALYSTS)
                .add(Items.GLOWSTONE_DUST);

        tag(ModTags.Items.SPECIAL_CATALYSTS)
                .add(Items.DRAGON_BREATH);

        tag(ModTags.Items.CENTRIFUGE_CATALYSTS)
                .addTag(ModTags.Items.STABILITY_CATALYSTS)
                .addTag(ModTags.Items.METABOLISM_CATALYSTS)
                .addTag(ModTags.Items.REPLICATION_CATALYSTS)
                .addTag(ModTags.Items.ADAPTABILITY_CATALYSTS)
                .addTag(ModTags.Items.SPECIAL_CATALYSTS);
    }
}
