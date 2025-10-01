package piotro15.symbiont.common.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.common.recipe.BioreactorRecipe;

public class ModRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, Symbiont.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> BIOREACTOR = RECIPE_SERIALIZERS.register("bioreactor", BioreactorRecipe.Serializer::new);
}
