package piotro15.symbiont.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import org.jetbrains.annotations.NotNull;
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.common.registries.ModDataComponents;
import piotro15.symbiont.common.registries.ModItems;
import piotro15.symbiont.datagen.builders.BioreactorRecipeBuilder;
import piotro15.symbiont.datagen.builders.MetabolizerRecipeBuilder;

import java.util.concurrent.CompletableFuture;

public class RecipeDatagen extends RecipeProvider {

    public RecipeDatagen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput consumer) {
        ItemStack protoCellStack = new ItemStack(ModItems.CELL_CULTURE.get());
        protoCellStack.applyComponents(DataComponentMap.builder().set(ModDataComponents.CELL_TYPE, ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, "proto_cell")).build());

        BioreactorRecipeBuilder.newBioreactorRecipe
                (Ingredient.of(ModItems.CULTURE_STARTER.get()),
                        FluidIngredient.of(new FluidStack(Fluids.WATER, 1000)),
                        protoCellStack,
                        new FluidStack(Fluids.WATER, 500))
                .build(consumer, ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, "bioreactor/proto_cell"));

        NonNullList<Ingredient> ingredients = NonNullList.create();
        ingredients.add(Ingredient.of(ModItems.CELL_CULTURE.get()));
        ingredients.add(Ingredient.of(Items.SUGAR));

        MetabolizerRecipeBuilder.newRecipe(
                        ingredients,
                        FluidIngredient.of(new FluidStack(Fluids.WATER, 1000)),
                        new ItemStack(ModItems.CELL_CULTURE.get(), 2),
                        new FluidStack(Fluids.WATER, 500)).build(consumer, ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, "metabolizer/glucose"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CULTURE_STARTER)
                .requires(Items.ROTTEN_FLESH)
                .requires(Items.SPIDER_EYE)
                .requires(Items.BONE_MEAL)
                .unlockedBy("has_ingredients", has(Items.ROTTEN_FLESH))
                .save(consumer);
    }

}
