package piotro15.symbiont.common.genetics;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.Registry;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import piotro15.symbiont.common.registry.ModRegistries;

import java.util.*;

public record Biocode(Map<Biotrait.BiotraitType, ResourceLocation> traits) {
    public static final Codec<Biocode> CODEC;
    public static final StreamCodec<ByteBuf, Biocode> STREAM_CODEC;

    static {
        CODEC = Codec.unboundedMap(
                Biotrait.BIOTRAIT_TYPE_CODEC,
                ResourceLocation.CODEC
        ).flatXmap(
                map -> DataResult.success(new Biocode(map)),
                biocode -> DataResult.success(biocode.traits())
        );

        STREAM_CODEC = ByteBufCodecs.STRING_UTF8.map(Biocode::parse, Biocode::toString);
    }

    public static Biocode parse(String s) {
        System.out.println(s);
        Map<Biotrait.BiotraitType, ResourceLocation> traits = new HashMap<>();

        s = s.replaceFirst("(Biocode\\[traits=\\{)", "");
        s = s.replaceFirst("}]$", "");

        String[] entries = s.split(", ");
        for (String entry : entries) {
            String[] parts = entry.split("=");
            if (parts.length == 2) {
                try {
                    Biotrait.BiotraitType type = Biotrait.BiotraitType.valueOf(parts[0]);
                    ResourceLocation traitId = ResourceLocation.parse(parts[1]);
                    traits.put(type, traitId);
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid BiotraitType: " + parts[0]);
                }
            }
        }
        System.out.println(new Biocode(traits));
        return new Biocode(traits);
    }

    public String toString(Biocode biocode) {
        StringBuilder sb = new StringBuilder();
        biocode.traits().forEach((type, traitId) -> {
            sb.append(type.toString()).append("=").append(traitId.toString()).append(";");
        });
        return sb.toString();
    }

    public static Map<Biotrait.BiotraitType, Biotrait> map(Map<Biotrait.BiotraitType, ResourceLocation> traitIds) {
        return traitIds.entrySet().stream().map(entry -> {
            ResourceLocation traitId = entry.getValue();
            ClientPacketListener connection = Minecraft.getInstance().getConnection();
            if (connection == null)
                return null;

            Registry<Biotrait> registry = connection.registryAccess().registryOrThrow(ModRegistries.BIOTRAIT);
            Biotrait trait = registry.get(traitId);
            return trait == null ? null : Map.entry(entry.getKey(), trait);
        }).filter(Objects::nonNull).collect(HashMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), HashMap::putAll);
    }
}
