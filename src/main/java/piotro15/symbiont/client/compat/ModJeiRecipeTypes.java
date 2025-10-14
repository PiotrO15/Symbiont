package piotro15.symbiont.client.compat;

import mezz.jei.api.recipe.RecipeType;
import net.minecraft.resources.ResourceLocation;
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.common.recipe.BioformerRecipe;

public class ModJeiRecipeTypes {
    public static final RecipeType<BioformerRecipe> BIOFORMER = new RecipeType<>(ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, "bioformer"), BioformerRecipe.class);
}
