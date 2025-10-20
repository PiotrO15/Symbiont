package piotro15.symbiont.common.genetics;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
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
        STABILITY(0xDC267F, "symbiont.trait_type.stability"),
        METABOLISM(0xFFB000, "symbiont.trait_type.metabolism"),
        REPLICATION(0x785EF0, "symbiont.trait_type.replication"),
        ADAPTABILITY(0x648FFF, "symbiont.trait_type.adaptability"),
        SPECIAL(0xFE6100, "symbiont.trait_type.special");

        private final int color;
        private final String translationKey;

        BiotraitType(int color, String translationKey) {
            this.color = color;
            this.translationKey = translationKey;
        }

        public int getColor() {
            return color;
        }

        public Component getDisplayName() {
            return Component.translatable(translationKey).withColor(color);
        }
    }

}
