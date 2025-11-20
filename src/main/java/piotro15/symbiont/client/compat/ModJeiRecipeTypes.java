package piotro15.symbiont.client.compat;

import mezz.jei.api.recipe.RecipeType;
import net.minecraft.resources.ResourceLocation;
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.common.recipe.BioformerRecipe;
import piotro15.symbiont.common.recipe.BioreactorRecipe;
import piotro15.symbiont.common.recipe.BoneSamplingRecipe;
import piotro15.symbiont.common.recipe.MetabolizerRecipe;

public class ModJeiRecipeTypes {
    public static final RecipeType<BioreactorRecipe> BIOREACTOR = new RecipeType<>(ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, "bioreactor"), BioreactorRecipe.class);
    public static final RecipeType<MetabolizerRecipe> METABOLIZER = new RecipeType<>(ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, "metabolizer"), MetabolizerRecipe.class);
    public static final RecipeType<BioformerRecipe> BIOFORMER = new RecipeType<>(ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, "bioformer"), BioformerRecipe.class);

    public static final RecipeType<BoneSamplingRecipe> BONE_SAMPLING = new RecipeType<>(ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, "bone_sampling"), BoneSamplingRecipe.class);
}
