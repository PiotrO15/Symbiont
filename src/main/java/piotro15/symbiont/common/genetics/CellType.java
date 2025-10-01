package piotro15.symbiont.common.genetics;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record CellType(
        CellGroup group
) {
    public static final Codec<CellType> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    CellGroup.CODEC.fieldOf("cell_group").forGetter(CellType::group)
            ).apply(instance, CellType::new)
    );
}
