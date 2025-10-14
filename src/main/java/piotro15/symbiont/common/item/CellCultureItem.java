package piotro15.symbiont.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;
import piotro15.symbiont.common.genetics.Biocode;
import piotro15.symbiont.common.genetics.Biotrait;
import piotro15.symbiont.common.genetics.CellType;
import piotro15.symbiont.common.genetics.IntegerTraitModifier;
import piotro15.symbiont.common.registry.ModDataComponents;
import piotro15.symbiont.common.registry.ModRegistries;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class CellCultureItem extends Item {
    public CellCultureItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull Component getName(ItemStack stack) {
        ResourceLocation cellId = stack.get(ModDataComponents.CELL_TYPE.get());
        if (cellId != null) {
            return Component.translatable(Util.makeDescriptionId("cell_type", cellId));
        }
        return super.getName(stack);
    }

    private double getStat(ItemStack stack, IntegerTraitModifier.StatType statType) {
        AtomicReference<Double> stat = new AtomicReference<>(1.0);

        CellType cellType = getCellType(stack);

        if (cellType == null)
            return stat.get();

        Map<Biotrait.BiotraitType, Biotrait> traits = getBiocode(stack);

        if (traits == null)
            return stat.get();

        traits.forEach((type, trait) -> {
            trait.modifiers().forEach(modifier -> {
                if (modifier instanceof IntegerTraitModifier(IntegerTraitModifier.StatType integerStatType, double value)) {
                    if (integerStatType == statType) {
                        stat.updateAndGet(v -> v * value);
                    }
                }
            });
        });
        return stat.get();
    }

    public double getStability(ItemStack stack) {
        return getStat(stack, IntegerTraitModifier.StatType.STABILITY);
    }

    public double getGrowth(ItemStack stack) {
        return getStat(stack, IntegerTraitModifier.StatType.GROWTH);
    }

    public double getMetabolism(ItemStack stack) {
        return getStat(stack, IntegerTraitModifier.StatType.METABOLISM);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        CellType cellType = getCellType(stack);
        if (cellType == null) {
            return;
        }

        if (tooltipFlag.hasShiftDown()) {
            Map<Biotrait.BiotraitType, Biotrait> traits = getBiocode(stack);
            if (traits != null) {
                tooltipComponents.add(Component.translatable("item.symbiont.cell_culture.traits").withStyle(ChatFormatting.GRAY));
                traits.forEach((type, trait) -> {
                    tooltipComponents.add(Component.literal(type +" - " + trait).withStyle(ChatFormatting.DARK_GRAY));
                });
            }
        } else {
            tooltipComponents.add(Component.translatable("item.symbiont.cell_culture.stability").withStyle(ChatFormatting.GRAY).append(Component.literal(String.format("%.0f", getStability(stack) * 100) + "%").withStyle(ChatFormatting.GREEN)));
            tooltipComponents.add(Component.translatable("item.symbiont.cell_culture.growth").withStyle(ChatFormatting.GRAY).append(Component.literal(String.format("%.0f", getGrowth(stack) * 100) + "%").withStyle(ChatFormatting.AQUA)));
            tooltipComponents.add(Component.translatable("item.symbiont.cell_culture.metabolism").withStyle(ChatFormatting.GRAY).append(Component.literal(String.format("%.0f", getMetabolism(stack) * 100) + "%").withStyle(ChatFormatting.DARK_AQUA)));
        }
    }

    public static CellType getCellType(ItemStack stack) {
        ResourceLocation cellId = stack.get(ModDataComponents.CELL_TYPE.get());
        if (cellId == null)
            return null;

        ClientPacketListener connection = Minecraft.getInstance().getConnection();
        if (connection == null)
            return null;

        Registry<CellType> registry = connection.registryAccess().registryOrThrow(ModRegistries.CELL_TYPE);

        return registry.get(cellId);
    }

    public static Map<Biotrait.BiotraitType, Biotrait> getBiocode(ItemStack stack) {
        CellType cellType = getCellType(stack);

        if (cellType == null) {
            return null;
        }

        Biocode biocodeComponent = stack.get(ModDataComponents.BIOCODE);

        Map<Biotrait.BiotraitType, ResourceLocation> traits = new HashMap<>(cellType.biocode().traits());

        if (biocodeComponent != null) {
            traits.putAll(biocodeComponent.traits());
        }

        return Biocode.map(traits);
    }
}
