package piotro15.symbiont.common.genetics;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record Biocode(Map<Biotrait.BiotraitType, Biotrait> traits) {
    public static final Codec<Biocode> CODEC;
    public static final StreamCodec<ByteBuf, Biocode> STREAM_CODEC;

    public void addTrait(Biotrait trait) {
        traits.put(trait.type(), trait);
    }

    static {
        CODEC = Biotrait.CODEC.listOf()
                .comapFlatMap(
                        list -> {
                            Map<Biotrait.BiotraitType, Biotrait> map = new HashMap<>();
                            for (Biotrait trait : list) {
                                map.put(trait.type(), trait);
                            }
                            return DataResult.success(new Biocode(map));
                        },
                        biocode -> new ArrayList<>(biocode.traits.values())
                );
        STREAM_CODEC = Biotrait.STREAM_CODEC.apply(ByteBufCodecs.list())
                .map(
                        list -> {
                            Map<Biotrait.BiotraitType, Biotrait> map = new HashMap<>();
                            for (Biotrait trait : list) {
                                map.put(trait.type(), trait);
                            }
                            return new Biocode(map);
                        },
                        biocode -> List.copyOf(biocode.traits.values())
                );
    }
}
