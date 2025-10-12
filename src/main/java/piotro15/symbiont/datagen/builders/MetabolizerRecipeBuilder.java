package piotro15.symbiont.datagen.builders;

import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import piotro15.symbiont.common.recipe.MetabolizerRecipe;

import java.util.ArrayList;

public class MetabolizerRecipeBuilder {
    private final NonNullList<Ingredient> itemInput;
    private final FluidIngredient fluidInput;
    private final ItemStack itemOutput;
    private final FluidStack fluidOutput;

    public MetabolizerRecipeBuilder(NonNullList<Ingredient> itemInput, FluidIngredient fluidInput, ItemStack itemOutput, FluidStack fluidOutput) {
        this.itemInput = itemInput;
        this.fluidInput = fluidInput;
        this.itemOutput = itemOutput;
        this.fluidOutput = fluidOutput;
    }

    public void build(RecipeOutput consumer, ResourceLocation id) {
        consumer.accept(id, new MetabolizerRecipe(itemInput, fluidInput, itemOutput, fluidOutput), null, new ArrayList<ICondition>().toArray(ICondition[]::new));
    }

    public static MetabolizerRecipeBuilder newRecipe(NonNullList<Ingredient> itemInput, FluidIngredient fluidInput, ItemStack itemOutput, FluidStack fluidOutput) {
        return new MetabolizerRecipeBuilder(itemInput, fluidInput, itemOutput, fluidOutput);
    }
}

