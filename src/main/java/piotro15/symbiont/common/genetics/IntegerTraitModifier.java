package piotro15.symbiont.common.genetics;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
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

    @Override
    public Component getDisplayComponent() {
        String sign = (value - 1) > 0 ? "+" : "";

        return statType.getDisplayName().withStyle(ChatFormatting.GRAY)
                .append(Component.literal(": ").withStyle(ChatFormatting.GRAY))
                .append(Component.literal(sign + String.format("%.0f%%", (value - 1) * 100)).withStyle(statType.getColor()));
    }

    public enum StatType {
        STABILITY(ChatFormatting.GREEN, "symbiont.stat.stability"),
        GROWTH(ChatFormatting.AQUA, "symbiont.stat.growth"),
        PRODUCTION(ChatFormatting.DARK_AQUA, "symbiont.stat.production"),
        CONSUMPTION(ChatFormatting.GOLD, "symbiont.stat.consumption");

        private final ChatFormatting color;
        private final String translationKey;

        StatType(ChatFormatting color, String translationKey) {
            this.color = color;
            this.translationKey = translationKey;
        }

        public ChatFormatting getColor() {
            return color;
        }

        public MutableComponent getDisplayName() {
            return Component.translatable(translationKey);
        }
    }
}
