package piotro15.symbiont.datagen.builders;

import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import piotro15.symbiont.common.recipe.BioformerRecipe;
import piotro15.symbiont.common.recipe.MetabolizerRecipe;

import java.util.ArrayList;

public record BioformerRecipeBuilder(
        Ingredient itemInput,
        Ingredient catalyst,
        FluidIngredient fluidInput,
        NonNullList<ItemStack> itemOutput
) {

    public void build(RecipeOutput consumer, ResourceLocation id) {
        consumer.accept(id, new BioformerRecipe(itemInput, catalyst, fluidInput, itemOutput), null, new ArrayList<ICondition>().toArray(ICondition[]::new));
    }

    public static BioformerRecipeBuilder newRecipe(Ingredient itemInput, Ingredient catalyst, FluidIngredient fluidInput, NonNullList<ItemStack> itemOutput) {
        return new BioformerRecipeBuilder(itemInput, catalyst, fluidInput, itemOutput);
    }
}

