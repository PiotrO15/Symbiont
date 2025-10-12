package piotro15.symbiont.datagen;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.common.genetics.Biocode;
import piotro15.symbiont.common.genetics.Biotrait;
import piotro15.symbiont.common.genetics.CellType;
import piotro15.symbiont.common.registries.ModRegistries;

import java.util.Map;

public class CellTypeProvider {
    public static void registerCellTypes(BootstrapContext<CellType> bootstrapContext) {
        bootstrapContext.register(
                ResourceKey.create(ModRegistries.CELL_TYPE, ResourceLocation.fromNamespaceAndPath("symbiont", "proto_cell")),
                new CellType(new Biocode(Map.of(Biotrait.BiotraitType.STABILITY, new Biotrait(Biotrait.BiotraitType.STABILITY))), 0xFF0000)
        );

        register(bootstrapContext, "glucose", new CellType.CellTypeBuilder().setColor(0x00FF00).build());
    }

    private static void register(BootstrapContext<CellType> bootstrapContext, String name, CellType cellType) {
        bootstrapContext.register(
                ResourceKey.create(ModRegistries.CELL_TYPE, ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, name)), cellType
        );
    }
}
