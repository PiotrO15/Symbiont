package piotro15.symbiont.common.genetics;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.resources.ResourceLocation;

public class TraitModifierRegistry {
    private static final BiMap<ResourceLocation, MapCodec<? extends GenericTraitModifier>> conditionCodecs = HashBiMap.create();

    public static final Codec<GenericTraitModifier> CODEC = ResourceLocation.CODEC.dispatch(GenericTraitModifier::id, conditionCodecs::get);

    public static <T extends GenericTraitModifier> void registerCondition(ResourceLocation id, MapCodec<T> codec) {
        if (conditionCodecs.containsKey(id)) {
            throw new IllegalArgumentException("Compass target condition with id " + id + " is already registered.");
        }
        conditionCodecs.put(id, codec);
    }

    public static void registerConditions() {
        registerCondition(IntegerTraitModifier.id, IntegerTraitModifier.CODEC);
    }
}
