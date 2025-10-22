package piotro15.symbiont.datagen.providers;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.common.genetics.Biotrait;
import piotro15.symbiont.common.genetics.CellType;
import piotro15.symbiont.common.registry.ModRegistries;

import java.util.Map;

public class CellTypeProvider {
    public static void registerCellTypes(BootstrapContext<CellType> bootstrapContext) {
        // Metabolic Cultures
        register(bootstrapContext, "proto", new CellType.CellTypeBuilder().build());
        register(bootstrapContext, "glucose", new CellType.CellTypeBuilder().build());

        // Structural Cultures
        register(bootstrapContext, "poly", new CellType.CellTypeBuilder().setColor(0xa3b18a).setTraits(Map.of(Biotrait.BiotraitType.METABOLISM, Symbiont.id("resource_aggression"))).build());
        register(bootstrapContext, "myoblast", new CellType.CellTypeBuilder().build());

        // Processor Cultures
        register(bootstrapContext, "marine", new CellType.CellTypeBuilder().setColor(0x298860).setTraits(Map.of(Biotrait.BiotraitType.METABOLISM, Symbiont.id("selective_uptake"))).build());
        register(bootstrapContext, "coral", new CellType.CellTypeBuilder().setTraits(Map.of(Biotrait.BiotraitType.METABOLISM, Symbiont.id("photosynthetic"))).build());
//        register(bootstrapContext, "oxidic", new CellType.CellTypeBuilder().build());
//        register(bootstrapContext, "silica", new CellType.CellTypeBuilder().build());

        // Animal Cultures
        register(bootstrapContext, "bovine", new CellType.CellTypeBuilder().setColor(0x6f4e37).build());
        register(bootstrapContext, "ovine", new CellType.CellTypeBuilder().setColor(0xffffff).build());
        register(bootstrapContext, "avian", new CellType.CellTypeBuilder().setColor(0xe4d00a).setTraits(Map.of(Biotrait.BiotraitType.REPLICATION, Symbiont.id("light_tissue"))).build());
        register(bootstrapContext, "porcine", new CellType.CellTypeBuilder().setColor(0xffc0cb).setTraits(Map.of(Biotrait.BiotraitType.METABOLISM, Symbiont.id("fat_layered"))).build());
        register(bootstrapContext, "leporine", new CellType.CellTypeBuilder().setColor(0xefd6b8).build());
        register(bootstrapContext, "mossling", new CellType.CellTypeBuilder().setColor(0x298860).setTraits(Map.of(Biotrait.BiotraitType.METABOLISM, Symbiont.id("old_world_metabolism"))).build());

        // Metalocell Cultures
        register(bootstrapContext, "ferric", new CellType.CellTypeBuilder().setColor(0xB7410E).setTraits(Map.of(Biotrait.BiotraitType.REPLICATION, Symbiont.id("stable_division"))).build());
        register(bootstrapContext, "auric", new CellType.CellTypeBuilder().setColor(0xFFD700).setTraits(Map.of(Biotrait.BiotraitType.REPLICATION, Symbiont.id("resilient_membrane"))).build());
    }

    private static void register(BootstrapContext<CellType> bootstrapContext, String name, CellType cellType) {
        bootstrapContext.register(
                ResourceKey.create(ModRegistries.CELL_TYPE, ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, name)), cellType
        );
    }
}
