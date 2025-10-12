package piotro15.symbiont.common.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.common.recipe.BioreactorRecipe;
import piotro15.symbiont.common.recipe.MetabolizerRecipe;

import java.util.function.Supplier;

public class ModRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, Symbiont.MOD_ID);

    public static final Supplier<RecipeType<BioreactorRecipe>> BIOREACTOR =
            RECIPE_TYPES.register(
                    "bioreactor",
                    () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, "bioreactor"))
            );

    public static final Supplier<RecipeType<MetabolizerRecipe>> METABOLIZER =
            RECIPE_TYPES.register(
                    "metabolizer",
                    () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, "metabolizer"))
            );
}
