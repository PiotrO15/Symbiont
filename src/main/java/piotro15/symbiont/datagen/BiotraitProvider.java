package piotro15.symbiont.datagen;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.common.genetics.Biotrait;
import piotro15.symbiont.common.genetics.Biotrait.BiotraitType;
import piotro15.symbiont.common.genetics.IntegerTraitModifier;
import piotro15.symbiont.common.genetics.IntegerTraitModifier.StatType;
import piotro15.symbiont.common.registry.ModRegistries;

import java.util.List;

public class BiotraitProvider {
    public static void registerBiotraits(BootstrapContext<Biotrait> bootstrapContext) {
        register(bootstrapContext, "instability", new Biotrait(BiotraitType.STABILITY, List.of(new IntegerTraitModifier(StatType.STABILITY, 0.9))));
        register(bootstrapContext, "stable", new Biotrait(BiotraitType.STABILITY, List.of(new IntegerTraitModifier(StatType.STABILITY, 1.3))));
        register(bootstrapContext, "metabolic", new Biotrait(BiotraitType.METABOLISM, List.of(new IntegerTraitModifier(StatType.PRODUCTION, 1.5), new IntegerTraitModifier(StatType.CONSUMPTION, 1.5))));

        register(bootstrapContext, "stable_division", new Biotrait(BiotraitType.STABILITY, new IntegerTraitModifier(StatType.STABILITY, 1.1)));


        register(bootstrapContext, "resilient_membrane", new Biotrait(BiotraitType.REPLICATION, new IntegerTraitModifier(StatType.STABILITY, 1.1), new IntegerTraitModifier(StatType.GROWTH, 0.85)));
    }

    private static void register(BootstrapContext<Biotrait> bootstrapContext, String name, Biotrait biotrait) {
        bootstrapContext.register(
                ResourceKey.create(ModRegistries.BIOTRAIT, ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, name)), biotrait
        );
    }
}
