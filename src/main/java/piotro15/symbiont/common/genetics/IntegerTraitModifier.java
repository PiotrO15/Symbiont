package piotro15.symbiont.common.genetics;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import piotro15.symbiont.common.Symbiont;

public record IntegerTraitModifier(StatType statType, double value) implements GenericTraitModifier {
    public static final ResourceLocation id = ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, "integer_trait_modifier");

    public static final MapCodec<IntegerTraitModifier> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Codec.STRING.fieldOf("stat_type").flatXmap(type -> DataResult.success(StatType.valueOf(type)), type -> DataResult.success(type.toString())).forGetter(IntegerTraitModifier::statType),
                    Codec.DOUBLE.fieldOf("value").forGetter(IntegerTraitModifier::value)
            ).apply(instance, IntegerTraitModifier::new)
    );

    @Override
    public ResourceLocation id() {
        return id;
    }

    public enum StatType {
        STABILITY,
        GROWTH,
        METABOLISM
    }
}
