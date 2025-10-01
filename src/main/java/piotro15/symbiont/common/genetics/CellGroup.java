package piotro15.symbiont.common.genetics;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

public record CellGroup(
        ResourceLocation id
) {
    private static final BiMap<ResourceLocation, MapCodec<? extends CellGroup>> groupCodecs = HashBiMap.create();

    public static final Codec<CellGroup> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    ResourceLocation.CODEC.fieldOf("id").forGetter(CellGroup::id)
            ).apply(instance, CellGroup::new)
    );

    public static void registerGroups() {
        // groupCodecs.put(ResourceLocation.fromNamespaceAndPath("symbiont", "metabolic"));
    }
}
