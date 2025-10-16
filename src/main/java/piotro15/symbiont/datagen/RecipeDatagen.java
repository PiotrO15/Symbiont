package piotro15.symbiont.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import org.jetbrains.annotations.NotNull;
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.common.item.CellCultureItem;
import piotro15.symbiont.common.registry.ModFluids;
import piotro15.symbiont.common.registry.ModItems;
import piotro15.symbiont.datagen.builders.BioformerRecipeBuilder;
import piotro15.symbiont.datagen.builders.BioreactorRecipeBuilder;
import piotro15.symbiont.datagen.builders.MetabolizerRecipeBuilder;

import java.util.concurrent.CompletableFuture;

public class RecipeDatagen extends RecipeProvider {

    public RecipeDatagen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput consumer) {
        BioreactorRecipeBuilder.newBioreactorRecipe
                (Ingredient.of(CellCultureItem.withCellType(Symbiont.id("proto"))),
                        SizedFluidIngredient.of(new FluidStack(Fluids.WATER, 1000)),
                        CellCultureItem.withCellType(Symbiont.id("proto")),
                        new FluidStack(Fluids.WATER, 500))
                .build(consumer, ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, "bioreactor/proto_cell"));

        MetabolizerRecipeBuilder.newRecipe(
                NonNullList.of(Ingredient.EMPTY, Ingredient.of(Items.BOWL), Ingredient.of(ItemTags.ANVIL), Ingredient.of(Items.ROTTEN_FLESH), Ingredient.of(Items.SPIDER_EYE), Ingredient.of(Items.BONE_MEAL)),
                SizedFluidIngredient.of(new FluidStack(Fluids.WATER, 1000)),
                CellCultureItem.withCellType(Symbiont.id("proto")),
                new FluidStack(ModFluids.NUTRITIONAL_PASTE, 500)).build(consumer, ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, "metabolizer/glucose"));

        MetabolizerRecipeBuilder.newRecipe(
                NonNullList.of(Ingredient.EMPTY, CellCultureItem.asIngredient(Symbiont.id("glucose")), Ingredient.of(Items.SUGAR)),
                SizedFluidIngredient.of(new FluidStack(ModFluids.NUTRITIONAL_PASTE, 500)),
                CellCultureItem.withCellType(Symbiont.id("glucose")),
                new FluidStack(ModFluids.SWEET_PASTE, 500)).build(consumer, ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, "metabolizer/sweet_paste_from_sugar")
        );

        MetabolizerRecipeBuilder.newRecipe(
                NonNullList.of(Ingredient.EMPTY, CellCultureItem.asIngredient(Symbiont.id("glucose")), Ingredient.of(Items.BEETROOT)),
                SizedFluidIngredient.of(new FluidStack(ModFluids.NUTRITIONAL_PASTE, 1000)),
                CellCultureItem.withCellType(Symbiont.id("glucose")),
                new FluidStack(ModFluids.SWEET_PASTE, 1000)).build(consumer, ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, "metabolizer/sweet_paste_from_beetroot")
        );

        MetabolizerRecipeBuilder.newRecipe(
                NonNullList.of(Ingredient.EMPTY, CellCultureItem.asIngredient(Symbiont.id("glucose")), Ingredient.of(Items.SWEET_BERRIES)),
                SizedFluidIngredient.of(new FluidStack(ModFluids.NUTRITIONAL_PASTE, 250)),
                CellCultureItem.withCellType(Symbiont.id("glucose")),
                new FluidStack(ModFluids.SWEET_PASTE, 250)).build(consumer, ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, "metabolizer/sweet_paste_from_sweet_berries")
        );

        BioformerRecipeBuilder.newRecipe(
                Ingredient.of(ModItems.CELL_CULTURE.get()),
                Ingredient.EMPTY,
                FluidIngredient.of(new FluidStack(Fluids.WATER, 1000)),
                NonNullList.of(ItemStack.EMPTY, new ItemStack(Items.RAW_COPPER), new ItemStack(Items.RAW_GOLD))
        ).build(consumer, ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, "bioformer/aurum"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CULTURE_STARTER)
                .requires(Items.ROTTEN_FLESH)
                .requires(Items.SPIDER_EYE)
                .requires(Items.BONE_MEAL)
                .unlockedBy("has_ingredients", has(Items.ROTTEN_FLESH))
                .save(consumer);
    }

}
