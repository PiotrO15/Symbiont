package piotro15.symbiont.datagen;

import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import piotro15.symbiont.common.recipe.BioreactorRecipe;

import java.util.ArrayList;

public class BioreactorRecipeBuilder {
    private final Ingredient itemInput;
    private final FluidIngredient fluidInput;
    private final ItemStack itemOutput;
    private final FluidStack fluidOutput;

    public BioreactorRecipeBuilder(Ingredient itemInput, FluidIngredient fluidInput, ItemStack itemOutput, FluidStack fluidOutput) {
        this.itemInput = itemInput;
        this.fluidInput = fluidInput;
        this.itemOutput = itemOutput;
        this.fluidOutput = fluidOutput;
    }

    public void build(RecipeOutput consumer, ResourceLocation id) {
        consumer.accept(id, new BioreactorRecipe(itemInput, fluidInput, itemOutput, fluidOutput), null, new ArrayList<ICondition>().toArray(ICondition[]::new));
    }

    public static BioreactorRecipeBuilder newBioreactorRecipe(Ingredient itemInput, FluidIngredient fluidInput, ItemStack itemOutput, FluidStack fluidOutput) {
        return new BioreactorRecipeBuilder(itemInput, fluidInput, itemOutput, fluidOutput);
    }
}

