package piotro15.symbiont.common.recipe;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import org.jetbrains.annotations.NotNull;
import piotro15.symbiont.common.registry.ModRecipeSerializers;
import piotro15.symbiont.common.registry.ModRecipeTypes;

public record BioformerRecipe(
        Ingredient itemInput,
        Ingredient catalyst,
        FluidIngredient fluidInput,
        NonNullList<ItemStack> output
) implements SymbiontRecipe<BioformerRecipeInput> {
    @Override
    public boolean matches(BioformerRecipeInput input, @NotNull Level level) {
        return itemInput.test(input.input())
                && catalyst.test(input.catalyst())
                && fluidInput.test(input.fluidInput());
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.BIOFORMER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipeTypes.BIOFORMER.get();
    }

    public static class Serializer implements RecipeSerializer<BioformerRecipe> {
        public static final MapCodec<BioformerRecipe> CODEC =
                RecordCodecBuilder.mapCodec(builder ->
                        builder.group(
                                Ingredient.CODEC.fieldOf("item_input").forGetter(BioformerRecipe::itemInput),
                                Ingredient.CODEC.fieldOf("catalyst").forGetter(BioformerRecipe::catalyst),
                                FluidIngredient.CODEC.fieldOf("fluid_input").forGetter(recipe -> recipe.fluidInput),
                                ItemStack.CODEC.listOf().fieldOf("output").flatXmap((outputs -> {
                                    ItemStack[] testOutput = outputs.toArray(ItemStack[]::new);
                                    if (testOutput.length == 0) {
                                        return DataResult.error(() -> "Bioformer recipe has no outputs");
                                    } else {
                                        return DataResult.success(NonNullList.of(ItemStack.EMPTY, testOutput));
                                    }
                                }), DataResult::success).forGetter(BioformerRecipe::output)
                        ).apply(builder, BioformerRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, BioformerRecipe> STREAM_CODEC = StreamCodec.of(
                BioformerRecipe.Serializer::toNetwork, BioformerRecipe.Serializer::fromNetwork
        );

        @Override
        public @NotNull MapCodec<BioformerRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, BioformerRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static BioformerRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            Ingredient itemInput = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient catalyst = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            FluidIngredient fluidInput = FluidIngredient.STREAM_CODEC.decode(buffer);

            int i = buffer.readVarInt();
            NonNullList<ItemStack> outputs = NonNullList.withSize(i, ItemStack.EMPTY);
            outputs.replaceAll((ingredient) -> ItemStack.STREAM_CODEC.decode(buffer));

            return new BioformerRecipe(itemInput, catalyst, fluidInput, outputs);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, BioformerRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.itemInput);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.catalyst);
            FluidIngredient.STREAM_CODEC.encode(buffer, recipe.fluidInput);

            buffer.writeVarInt(recipe.output.size());
            for(ItemStack outputItem : recipe.output) {
                ItemStack.STREAM_CODEC.encode(buffer, outputItem);
            }
        }
    }
}
