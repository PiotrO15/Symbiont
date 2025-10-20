package piotro15.symbiont.common.registry;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredRegister;
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.common.genetics.Biocode;

import java.util.function.Supplier;

public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> REGISTRAR = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, Symbiont.MOD_ID);

    public static final Supplier<DataComponentType<ResourceLocation>> CELL_TYPE = REGISTRAR.register(
            "cell_type",
            () -> DataComponentType.<ResourceLocation>builder()
                    .persistent(ResourceLocation.CODEC)
                    .networkSynchronized(ResourceLocation.STREAM_CODEC)
                    .cacheEncoding()
                    .build()
    );

    public static final Supplier<DataComponentType<Biocode>> BIOCODE = REGISTRAR.register(
            "biocode",
            () -> DataComponentType.<Biocode>builder()
                    .persistent(Biocode.CODEC)
                    .networkSynchronized(Biocode.STREAM_CODEC)
                    .cacheEncoding()
                    .build()
    );

    public static final Supplier<DataComponentType<ResourceLocation>> BIOTRAIT = REGISTRAR.register(
            "biotrait",
            () -> DataComponentType.<ResourceLocation>builder()
                    .persistent(ResourceLocation.CODEC)
                    .networkSynchronized(ResourceLocation.STREAM_CODEC)
                    .cacheEncoding()
                    .build()
    );
}
