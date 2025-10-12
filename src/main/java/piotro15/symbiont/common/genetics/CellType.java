package piotro15.symbiont.common.genetics;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Map;

public record CellType(
        Biocode biocode,
        int color
) {
    public static final Codec<CellType> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Biocode.CODEC.fieldOf("biocode").forGetter(CellType::biocode),
                    Codec.INT.optionalFieldOf("color", 0xFFFFFF).forGetter(CellType::color)
            ).apply(instance, CellType::new)
    );

    public static class CellTypeBuilder {
        private Map<Biotrait.BiotraitType, Biotrait> traits = Map.of();
        private int color = 0xFFFFFF;

        public CellTypeBuilder setTraits(Map<Biotrait.BiotraitType, Biotrait> traits) {
            this.traits = traits;
            return this;
        }

        public CellTypeBuilder setColor(int color) {
            this.color = color;
            return this;
        }

        public CellType build() {
            return new CellType(new Biocode(traits), color);
        }
    }
}
