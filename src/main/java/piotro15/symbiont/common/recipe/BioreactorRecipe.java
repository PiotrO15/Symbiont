package piotro15.symbiont.common.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import org.jetbrains.annotations.NotNull;
import piotro15.symbiont.common.registry.ModRecipeSerializers;
import piotro15.symbiont.common.registry.ModRecipeTypes;

public record BioreactorRecipe(
        Ingredient input,
        SizedFluidIngredient fluidInput,
        ItemStack output,
        FluidStack fluidOutput
) implements SymbiontRecipe<BioreactorRecipeInput> {
    @Override
    public boolean matches(BioreactorRecipeInput bioreactorRecipeInput, @NotNull Level level) {
        return input.test(bioreactorRecipeInput.stack()) && fluidInput.test(bioreactorRecipeInput.fluidInput());
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.BIOREACTOR.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipeTypes.BIOREACTOR.get();
    }

    public static class Serializer implements RecipeSerializer<BioreactorRecipe> {
        public static final MapCodec<BioreactorRecipe> CODEC =
                RecordCodecBuilder.mapCodec(builder ->
                        builder.group(
                                Ingredient.CODEC_NONEMPTY.fieldOf("item_input").forGetter(recipe -> recipe.input),
                                SizedFluidIngredient.FLAT_CODEC.fieldOf("fluid_input").forGetter(recipe -> recipe.fluidInput),
                                ItemStack.CODEC.fieldOf("item_output").forGetter(recipe -> recipe.output),
                                FluidStack.CODEC.fieldOf("fluid_output").forGetter(recipe -> recipe.fluidOutput)
                        ).apply(builder, BioreactorRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, BioreactorRecipe> STREAM_CODEC = StreamCodec.of(
                BioreactorRecipe.Serializer::toNetwork, BioreactorRecipe.Serializer::fromNetwork
        );

        @Override
        public @NotNull MapCodec<BioreactorRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, BioreactorRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static BioreactorRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            Ingredient itemInput = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            SizedFluidIngredient fluidInput = SizedFluidIngredient.STREAM_CODEC.decode(buffer);
            ItemStack itemOutput = ItemStack.STREAM_CODEC.decode(buffer);
            FluidStack fluidOutput = FluidStack.STREAM_CODEC.decode(buffer);
            return new BioreactorRecipe(itemInput, fluidInput, itemOutput, fluidOutput);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, BioreactorRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.input);
            SizedFluidIngredient.STREAM_CODEC.encode(buffer, recipe.fluidInput);
            ItemStack.STREAM_CODEC.encode(buffer, recipe.output);
            FluidStack.STREAM_CODEC.encode(buffer, recipe.fluidOutput);
        }
    }
}
