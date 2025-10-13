package piotro15.symbiont.datagen;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.common.genetics.CellType;
import piotro15.symbiont.common.registries.ModRegistries;

public class CellTypeProvider {
    public static void registerCellTypes(BootstrapContext<CellType> bootstrapContext) {
        register(bootstrapContext, "proto_cell", new CellType.CellTypeBuilder().build());
        register(bootstrapContext, "glucose", new CellType.CellTypeBuilder().build());
    }

    private static void register(BootstrapContext<CellType> bootstrapContext, String name, CellType cellType) {
        bootstrapContext.register(
                ResourceKey.create(ModRegistries.CELL_TYPE, ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, name)), cellType
        );
    }
}
