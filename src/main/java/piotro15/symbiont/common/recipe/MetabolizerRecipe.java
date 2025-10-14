package piotro15.symbiont.common.recipe;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.RecipeMatcher;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import org.jetbrains.annotations.NotNull;
import piotro15.symbiont.common.registry.ModRecipeSerializers;
import piotro15.symbiont.common.registry.ModRecipeTypes;

import java.util.ArrayList;

public record MetabolizerRecipe(
        NonNullList<Ingredient> ingredients,
        FluidIngredient fluidInput,
        ItemStack output,
        FluidStack fluidOutput
) implements Recipe<MetabolizerRecipeInput> {

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public boolean matches(MetabolizerRecipeInput input, @NotNull Level level) {
        // && fluidInput.test(bioreactorRecipeInput.fluidInput());

        if (input.size() != this.ingredients.size()) {
            return false;
        } else {
            ArrayList<ItemStack> nonEmptyItems = new ArrayList(input.ingredientCount());

            for(ItemStack item : input.getItems()) {
                if (!item.isEmpty()) {
                    nonEmptyItems.add(item);
                }
            }

            return RecipeMatcher.findMatches(nonEmptyItems, this.ingredients) != null;
        }
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull MetabolizerRecipeInput metabolizerRecipeInput, HolderLookup.@NotNull Provider provider) {
        return this.output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 1;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider provider) {
        return this.output;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.METABOLIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipeTypes.METABOLIZER.get();
    }

    public static class Serializer implements RecipeSerializer<MetabolizerRecipe> {
        public static final MapCodec<MetabolizerRecipe> CODEC =
                RecordCodecBuilder.mapCodec(builder ->
                        builder.group(
                                Ingredient.CODEC_NONEMPTY.listOf().fieldOf("item_input").flatXmap((ingredients1 -> {
                                    Ingredient[] aingredient = ingredients1.toArray(Ingredient[]::new);
                                    if (aingredient.length == 0) {
                                        return DataResult.error(() -> "No ingredients for metabolizer recipe");
                                    } else {
                                        return DataResult.success(NonNullList.of(Ingredient.EMPTY, aingredient));
                                    }
                                }), DataResult::success).forGetter(MetabolizerRecipe::getIngredients),
                                FluidIngredient.CODEC.fieldOf("fluid_input").forGetter(recipe -> recipe.fluidInput),
                                ItemStack.CODEC.fieldOf("item_output").forGetter(recipe -> recipe.output),
                                FluidStack.CODEC.fieldOf("fluid_output").forGetter(recipe -> recipe.fluidOutput)
                        ).apply(builder, MetabolizerRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, MetabolizerRecipe> STREAM_CODEC = StreamCodec.of(
                MetabolizerRecipe.Serializer::toNetwork, MetabolizerRecipe.Serializer::fromNetwork
        );

        @Override
        public @NotNull MapCodec<MetabolizerRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, MetabolizerRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static MetabolizerRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            int i = buffer.readVarInt();
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);
            nonnulllist.replaceAll((ingredient) -> Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
            FluidIngredient fluidInput = FluidIngredient.STREAM_CODEC.decode(buffer);
            ItemStack itemOutput = ItemStack.STREAM_CODEC.decode(buffer);
            FluidStack fluidOutput = FluidStack.STREAM_CODEC.decode(buffer);
            return new MetabolizerRecipe(nonnulllist, fluidInput, itemOutput, fluidOutput);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, MetabolizerRecipe recipe) {
            buffer.writeVarInt(recipe.ingredients.size());
            for(Ingredient ingredient : recipe.ingredients) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, ingredient);
            }
            FluidIngredient.STREAM_CODEC.encode(buffer, recipe.fluidInput);
            ItemStack.STREAM_CODEC.encode(buffer, recipe.output);
            FluidStack.STREAM_CODEC.encode(buffer, recipe.fluidOutput);
        }
    }
}
