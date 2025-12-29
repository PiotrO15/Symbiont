package piotro15.symbiont.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.HolderSetCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.crafting.ICustomIngredient;
import net.neoforged.neoforge.common.crafting.IngredientType;
import org.jetbrains.annotations.NotNull;
import piotro15.symbiont.common.registry.ModRegistries;

import java.util.Arrays;
import java.util.stream.Stream;

public class SizedIngredient implements ICustomIngredient {
    public static final MapCodec<SizedIngredient> CODEC = RecordCodecBuilder.mapCodec((builder) -> builder.group(HolderSetCodec.create(Registries.ITEM, BuiltInRegistries.ITEM.holderByNameCodec(), false).fieldOf("items").forGetter(SizedIngredient::items), Codec.INT.fieldOf("count").forGetter(SizedIngredient::getCount)).apply(builder, SizedIngredient::new));
    private final HolderSet<Item> items;
    private final int count;
    private final ItemStack[] itemStacks;

    public SizedIngredient(HolderSet<Item> items, int count) {
        this.items = items;
        this.count = count;

        itemStacks = items.stream().map((i) -> new ItemStack(i, this.count)).toArray(ItemStack[]::new);
    }


    @Override
    public boolean test(@NotNull ItemStack stack) {
        for(ItemStack stack2 : this.itemStacks) {
            if (ItemStack.isSameItem(stack, stack2) && stack.getCount() >= stack2.getCount()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public @NotNull Stream<ItemStack> getItems() {
        return Arrays.stream(itemStacks);
    }

    public HolderSet<Item> items() {
        return this.items;
    }

    public Integer getCount() {
        return count;
    }

    @Override
    public boolean isSimple() {
        return false;
    }

    @Override
    public @NotNull IngredientType<?> getType() {
        return ModRegistries.SIZED_INGREDIENT.get();
    }

    public static Ingredient of(int count, ItemLike... items) {
        return (new SizedIngredient(HolderSet.direct(Arrays.stream(items).map(ItemLike::asItem).map(Item::builtInRegistryHolder).toList()), count)).toVanilla();
    }
}
