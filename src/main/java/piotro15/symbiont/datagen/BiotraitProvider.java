package piotro15.symbiont.datagen;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.common.genetics.Biotrait;
import piotro15.symbiont.common.genetics.IntegerTraitModifier;
import piotro15.symbiont.common.registries.ModRegistries;

import java.util.List;

public class BiotraitProvider {
    public static void registerBiotraits(BootstrapContext<Biotrait> bootstrapContext) {
        register(bootstrapContext, "instability", new Biotrait(Biotrait.BiotraitType.STABILITY, List.of(new IntegerTraitModifier(IntegerTraitModifier.StatType.STABILITY, 0.9))));
        register(bootstrapContext, "stable", new Biotrait(Biotrait.BiotraitType.STABILITY, List.of(new IntegerTraitModifier(IntegerTraitModifier.StatType.STABILITY, 1.3))));
        register(bootstrapContext, "metabolic", new Biotrait(Biotrait.BiotraitType.METABOLISM, List.of(new IntegerTraitModifier(IntegerTraitModifier.StatType.METABOLISM, 1.5))));
    }

    private static void register(BootstrapContext<Biotrait> bootstrapContext, String name, Biotrait biotrait) {
        bootstrapContext.register(
                ResourceKey.create(ModRegistries.BIOTRAIT, ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, name)), biotrait
        );
    }
}
