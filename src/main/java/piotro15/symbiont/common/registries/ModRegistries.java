package piotro15.symbiont.common.registries;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.common.genetics.Biotrait;
import piotro15.symbiont.common.genetics.CellType;

public class ModRegistries {
    public static final ResourceKey<Registry<CellType>> CELL_TYPE =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, "cell_type"));

    public static final ResourceKey<Registry<Biotrait>> BIOTRAIT =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, "biotrait"));

    @SubscribeEvent
    public static void registerDataRegistry(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(ModRegistries.CELL_TYPE, CellType.CODEC, CellType.CODEC);
        event.dataPackRegistry(ModRegistries.BIOTRAIT, Biotrait.CODEC, Biotrait.CODEC);
    }
}
