package piotro15.symbiont.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.common.recipe.BioformerRecipe;
import piotro15.symbiont.common.recipe.BioreactorRecipe;
import piotro15.symbiont.common.recipe.BoneSamplingRecipe;
import piotro15.symbiont.common.recipe.MetabolizerRecipe;

public class ModRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, Symbiont.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> BIOREACTOR = RECIPE_SERIALIZERS.register("bioreactor", BioreactorRecipe.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> METABOLIZER = RECIPE_SERIALIZERS.register("metabolizer", MetabolizerRecipe.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> BIOFORMER = RECIPE_SERIALIZERS.register("bioformer", BioformerRecipe.Serializer::new);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> BONE_SAMPLING = RECIPE_SERIALIZERS.register("bone_sampling", BoneSamplingRecipe.Serializer::new);
}
