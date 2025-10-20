package piotro15.symbiont.datagen.providers;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.common.genetics.Biotrait;
import piotro15.symbiont.common.genetics.Biotrait.BiotraitType;
import piotro15.symbiont.common.genetics.IntegerTraitModifier;
import piotro15.symbiont.common.genetics.IntegerTraitModifier.StatType;
import piotro15.symbiont.common.registry.ModRegistries;

public class BiotraitProvider {
    public static void registerBiotraits(BootstrapContext<Biotrait> bootstrapContext) {
        register(bootstrapContext, "stable_division", new Biotrait(BiotraitType.STABILITY, new IntegerTraitModifier(StatType.STABILITY, 1.1)));
        register(bootstrapContext, "light_tissue", new Biotrait(BiotraitType.REPLICATION, new IntegerTraitModifier(StatType.GROWTH, 1.1)));
        register(bootstrapContext, "fat_layered", new Biotrait(BiotraitType.METABOLISM, new IntegerTraitModifier(StatType.CONSUMPTION, 1.05), new IntegerTraitModifier(StatType.PRODUCTION, 1.1)));

        register(bootstrapContext, "resilient_membrane", new Biotrait(BiotraitType.REPLICATION, new IntegerTraitModifier(StatType.STABILITY, 1.1), new IntegerTraitModifier(StatType.GROWTH, 0.85)));
        register(bootstrapContext, "resource_aggression", new Biotrait(BiotraitType.METABOLISM, new IntegerTraitModifier(StatType.STABILITY, 0.8), new IntegerTraitModifier(StatType.PRODUCTION, 1.4), new IntegerTraitModifier(StatType.CONSUMPTION, 1.3)));
    }

    private static void register(BootstrapContext<Biotrait> bootstrapContext, String name, Biotrait biotrait) {
        bootstrapContext.register(
                ResourceKey.create(ModRegistries.BIOTRAIT, ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, name)), biotrait
        );
    }
}
