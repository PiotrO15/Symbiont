package piotro15.symbiont.common.genetics;

import net.minecraft.resources.ResourceLocation;

import java.util.Set;

public record Biotrait(
        ResourceLocation id,
        BiotraitType type,
        Set<CellGroup> applicableGroups,
        Set<GenericTraitModifier> modifiers
) {
    public enum BiotraitType {
        STABILITY,
        METABOLISM,
        REPLICATION,
        ADAPTABILITY,
        BIOCHEMISTRY
    }
}
