package piotro15.symbiont.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;
import org.jetbrains.annotations.NotNull;
import piotro15.symbiont.common.genetics.Biocode;
import piotro15.symbiont.common.genetics.Biotrait;
import piotro15.symbiont.common.genetics.CellType;
import piotro15.symbiont.common.genetics.IntegerTraitModifier;
import piotro15.symbiont.common.registry.ModDataComponents;
import piotro15.symbiont.common.registry.ModItems;
import piotro15.symbiont.common.registry.ModRegistries;

import java.util.ArrayList;
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

    private static double getStat(ItemStack stack, IntegerTraitModifier.StatType statType) {
        AtomicReference<Double> stat = new AtomicReference<>(1.0);

        CellType cellType = getCellType(stack);

        if (cellType == null)
            return stat.get();

        List<AppliedBiotrait> traits = getBiocode(stack);

        if (traits == null)
            return stat.get();

        return getStat(traits, statType);
    }

    public static double getStat(List<AppliedBiotrait> traits, IntegerTraitModifier.StatType statType) {
        AtomicReference<Double> stat = new AtomicReference<>(1.0);

        traits.forEach((appliedBiotrait) ->
            appliedBiotrait.trait().modifiers().forEach(modifier -> {
                if (modifier instanceof IntegerTraitModifier(IntegerTraitModifier.StatType integerStatType, double value)) {
                    if (integerStatType == statType) {
                        stat.updateAndGet(v -> v * value);
                    }
                }
            })
        );
        return stat.get();
    }

    public static double getStability(ItemStack stack) {
        return getStat(stack, IntegerTraitModifier.StatType.STABILITY);
    }

    public static double getGrowth(ItemStack stack) {
        return getStat(stack, IntegerTraitModifier.StatType.GROWTH);
    }

    public static double getProduction(ItemStack stack) {
        return getStat(stack, IntegerTraitModifier.StatType.PRODUCTION);
    }

    public static double getConsumption(ItemStack stack) {
        return getStat(stack, IntegerTraitModifier.StatType.CONSUMPTION);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        tooltipComponents.addAll(getTooltipLines(stack, tooltipFlag));
    }

    public static List<Component> getTooltipLines(ItemStack stack, TooltipFlag tooltipFlag) {
        List<Component> tooltipComponents = new ArrayList<>();
        CellType cellType = getCellType(stack);
        if (cellType == null) {
            return tooltipComponents;
        }

        if (tooltipFlag.hasControlDown()) {
            List<AppliedBiotrait> traits = getBiocode(stack);
            if (traits == null || traits.isEmpty()) return tooltipComponents;

            tooltipComponents.add(Component.translatable("item.symbiont.cell_culture.traits")
                    .withStyle(ChatFormatting.GRAY, ChatFormatting.UNDERLINE));

            traits.forEach((appliedBiotrait) -> {
                tooltipComponents.add(Component.literal("• ")
                        .append(appliedBiotrait.type().getDisplayName())
                        .append(Component.literal(": "))
                        .append(Component.translatable("biotrait." + appliedBiotrait.traitId().getNamespace() + "." + appliedBiotrait.traitId().getPath())
                                .withStyle(ChatFormatting.WHITE)));

                appliedBiotrait.trait().modifiers().forEach(mod -> tooltipComponents.add(mod.getDisplayComponent()));
            });
        } else {
            tooltipComponents.add(Component.translatable("item.symbiont.cell_culture.stability").withStyle(ChatFormatting.GRAY).append(Component.literal(String.format("%.0f", getStability(stack) * 100) + "%").withStyle(ChatFormatting.GREEN)));
            tooltipComponents.add(Component.translatable("item.symbiont.cell_culture.growth").withStyle(ChatFormatting.GRAY).append(Component.literal(String.format("%.0f", getGrowth(stack) * 100) + "%").withStyle(ChatFormatting.AQUA)));
            tooltipComponents.add(Component.translatable("item.symbiont.cell_culture.production").withStyle(ChatFormatting.GRAY).append(Component.literal(String.format("%.0f", getProduction(stack) * 100) + "%").withStyle(ChatFormatting.DARK_AQUA)));
            tooltipComponents.add(Component.translatable("item.symbiont.cell_culture.consumption").withStyle(ChatFormatting.GRAY).append(Component.literal(String.format("%.0f", getConsumption(stack) * 100) + "%").withStyle(ChatFormatting.GOLD)));
        }
        return tooltipComponents;
    }

    public static List<Component> createTooltip(List<AppliedBiotrait> traits, boolean detailed) {
        List<Component> tooltipComponents = new ArrayList<>();

        if (detailed) {
            if (traits == null || traits.isEmpty()) return tooltipComponents;

            tooltipComponents.add(Component.translatable("item.symbiont.cell_culture.traits")
                    .withStyle(ChatFormatting.GRAY, ChatFormatting.UNDERLINE));

            traits.forEach((appliedBiotrait) -> {
                tooltipComponents.add(Component.literal("• ")
                        .append(appliedBiotrait.type().getDisplayName())
                        .append(Component.literal(": "))
                        .append(Component.translatable("biotrait." + appliedBiotrait.traitId().getNamespace() + "." + appliedBiotrait.traitId().getPath())
                                .withStyle(ChatFormatting.WHITE)));

                appliedBiotrait.trait().modifiers().forEach(mod -> tooltipComponents.add(mod.getDisplayComponent()));
            });
        } else {
            tooltipComponents.add(Component.translatable("item.symbiont.cell_culture.stability").withStyle(ChatFormatting.GRAY).append(Component.literal(String.format("%.0f", getStat(traits, IntegerTraitModifier.StatType.STABILITY) * 100) + "%").withStyle(ChatFormatting.GREEN)));
            tooltipComponents.add(Component.translatable("item.symbiont.cell_culture.growth").withStyle(ChatFormatting.GRAY).append(Component.literal(String.format("%.0f", getStat(traits, IntegerTraitModifier.StatType.GROWTH) * 100) + "%").withStyle(ChatFormatting.AQUA)));
            tooltipComponents.add(Component.translatable("item.symbiont.cell_culture.production").withStyle(ChatFormatting.GRAY).append(Component.literal(String.format("%.0f", getStat(traits, IntegerTraitModifier.StatType.PRODUCTION) * 100) + "%").withStyle(ChatFormatting.DARK_AQUA)));
            tooltipComponents.add(Component.translatable("item.symbiont.cell_culture.consumption").withStyle(ChatFormatting.GRAY).append(Component.literal(String.format("%.0f", getStat(traits, IntegerTraitModifier.StatType.CONSUMPTION) * 100) + "%").withStyle(ChatFormatting.GOLD)));
        }
        return tooltipComponents;
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

    public static List<AppliedBiotrait> getBiocode(ItemStack stack) {
        CellType cellType = getCellType(stack);

        if (cellType == null) {
            return null;
        }

        Biocode biocodeComponent = stack.get(ModDataComponents.BIOCODE);

        Map<Biotrait.BiotraitType, ResourceLocation> traits = new HashMap<>(cellType.biocode().traits());
        if (biocodeComponent != null) {
            traits.putAll(biocodeComponent.traits());
        }

        List<AppliedBiotrait> result = new ArrayList<>();
        for (Map.Entry<Biotrait.BiotraitType, ResourceLocation> entry : traits.entrySet()) {
            Biotrait.BiotraitType type = entry.getKey();
            ResourceLocation id = entry.getValue();

            ClientPacketListener connection = Minecraft.getInstance().getConnection();
            if (connection == null)
                return null;

            Registry<Biotrait> registry = connection.registryAccess().registryOrThrow(ModRegistries.BIOTRAIT);
            Biotrait resolved = registry.get(id);

            if (resolved != null) {
                result.add(new AppliedBiotrait(type, id, resolved));
            }
        }

        return result;
    }

    public static int getCountChange(ItemStack itemStack, RandomSource random) {
        int countChange = 0;
        if (!(itemStack.getItem() instanceof CellCultureItem))
            return countChange;

        double stability = CellCultureItem.getStability(itemStack);

        if (stability < 1.0) {
            if (random.nextDouble() > stability) {
                countChange = -1;
            }
        } else if (stability > 1.0) {
            double extra = stability - 1.0;

            int guaranteedGrowth = (int) Math.floor(extra);
            double fractionalGrowthChance = extra - guaranteedGrowth;

            countChange = guaranteedGrowth;
            if (random.nextDouble() < fractionalGrowthChance) {
                countChange += 1;
            }
        }
        return countChange;
    }

    public static ItemStack withCellType(ResourceLocation cellTypeId) {
        ItemStack stack = new ItemStack(ModItems.CELL_CULTURE.get());
        stack.set(ModDataComponents.CELL_TYPE.get(), cellTypeId);
        return stack;
    }

    public static Ingredient asIngredient(ResourceLocation cellTypeId) {
        return DataComponentIngredient.of(false, DataComponentMap.builder().set(ModDataComponents.CELL_TYPE.get(), cellTypeId).build(), ModItems.CELL_CULTURE.get());
    }

    public record AppliedBiotrait(Biotrait.BiotraitType type, ResourceLocation traitId, Biotrait trait) {}
}
