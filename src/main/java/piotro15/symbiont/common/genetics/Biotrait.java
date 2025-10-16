package piotro15.symbiont.common.genetics;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;

public record Biotrait(
        BiotraitType type,
//        List<CellGroup> applicableGroups,
        List<GenericTraitModifier> modifiers
) {
    public Biotrait(BiotraitType type, GenericTraitModifier... traitModifiers) {
        this(type, List.of(traitModifiers));
    }

    public static final Codec<Biotrait> CODEC;
    public static final StreamCodec<ByteBuf, Biotrait> STREAM_CODEC;

    public static final Codec<BiotraitType> BIOTRAIT_TYPE_CODEC = Codec.STRING.xmap(
            BiotraitType::valueOf,
            BiotraitType::name
    );

    public static Biotrait parse(String s) {
        return new Biotrait(BiotraitType.valueOf(s), List.of());
    }

    static {
        CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        BIOTRAIT_TYPE_CODEC.fieldOf("trait_type").forGetter(Biotrait::type),
                        TraitModifierRegistry.CODEC.listOf().fieldOf("modifiers").forGetter(Biotrait::modifiers)
                ).apply(instance, Biotrait::new));
        STREAM_CODEC = ByteBufCodecs.STRING_UTF8.map(Biotrait::parse, Biotrait::toString);
    }

    public enum BiotraitType {
        STABILITY,
        METABOLISM,
        REPLICATION,
        ADAPTABILITY,
        SPECIAL
    }
}
