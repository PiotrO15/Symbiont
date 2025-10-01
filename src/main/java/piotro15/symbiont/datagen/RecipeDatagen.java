package piotro15.symbiont.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import org.jetbrains.annotations.NotNull;
import piotro15.symbiont.common.Symbiont;

import java.util.concurrent.CompletableFuture;

public class RecipeDatagen extends RecipeProvider {

    public RecipeDatagen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput consumer) {
        BioreactorRecipeBuilder.newBioreactorRecipe
                (Ingredient.of(Items.ROTTEN_FLESH),
                        FluidIngredient.of(new FluidStack(Fluids.WATER, 1000)),
                        new ItemStack(Items.SLIME_BALL),
                        new FluidStack(Fluids.WATER, 500))
                .build(consumer, ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, "bioreactor/slime_from_flesh"));
    }

}
