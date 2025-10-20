package piotro15.symbiont.datagen.providers;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
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

public class RecipeProvider extends net.minecraft.data.recipes.RecipeProvider {

    public RecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ORGANIC_BINDER, 4)
                        .requires(Ingredient.of(Items.GREEN_DYE), 2)
                        .requires(Ingredient.of(Items.CLAY_BALL))
                        .unlockedBy("has_green_dye", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GREEN_DYE))
                        .save(consumer, Symbiont.id("organic_binder"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.METABOLIZER)
                        .pattern("B B")
                        .pattern("IRI")
                        .pattern("IRI")
                        .define('I', Items.IRON_INGOT)
                        .define('R', Items.IRON_BARS)
                        .define('B', ModItems.ORGANIC_BINDER)
                        .unlockedBy("has_iron_ingot", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_INGOT))
                        .save(consumer, Symbiont.id("metabolizer"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BIOREACTOR)
                .pattern("IBI")
                .pattern("R R")
                .pattern("IBI")
                .define('I', Items.IRON_INGOT)
                .define('R', Items.REDSTONE)
                .define('B', ModItems.ORGANIC_BINDER)
                .unlockedBy("has_iron_ingot", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_INGOT))
                .save(consumer, Symbiont.id("bioreactor"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BIOFORMER)
                .pattern("BPB")
                .pattern("I I")
                .pattern("III")
                .define('I', Items.IRON_INGOT)
                .define('P', Items.PISTON)
                .define('B', ModItems.ORGANIC_BINDER)
                .unlockedBy("has_iron_ingot", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_INGOT))
                .save(consumer, Symbiont.id("bioformer"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.RECOMBINATOR)
                .pattern("BIB")
                .pattern("BDB")
                .pattern("III")
                .define('I', Items.IRON_INGOT)
                .define('D', Items.DIAMOND)
                .define('B', ModItems.BIOPLASTIC_SHEET)
                .unlockedBy("has_bioplastic_sheet", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.BIOPLASTIC_SHEET))
                .save(consumer, Symbiont.id("recombinator"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BONE_SAMPLER)
                .pattern(" B")
                .pattern("S ")
                .define('S', Items.STICK)
                .define('B', Items.BONE)
                .unlockedBy("has_bone", InventoryChangeTrigger.TriggerInstance.hasItems(Items.BONE))
                .save(consumer, Symbiont.id("bone_sampler"));

        MetabolizerRecipeBuilder.newRecipe(
                NonNullList.of(Ingredient.EMPTY, Ingredient.of(Items.BOWL), Ingredient.of(Items.ROTTEN_FLESH), Ingredient.of(Items.SPIDER_EYE), Ingredient.of(Items.BONE_MEAL)),
                SizedFluidIngredient.of(new FluidStack(Fluids.WATER, 1000)),
                CellCultureItem.withCellType(Symbiont.id("proto")),
                new FluidStack(ModFluids.NUTRITIONAL_PASTE, 500)).build(consumer, Symbiont.id("metabolizer/proto_cell"));

        MetabolizerRecipeBuilder.newRecipe(
                NonNullList.of(Ingredient.EMPTY, CellCultureItem.asIngredient(Symbiont.id("proto")), Ingredient.of(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "crops")))),
                SizedFluidIngredient.of(new FluidStack(Fluids.WATER, 500)),
                CellCultureItem.withCellType(Symbiont.id("proto")),
                new FluidStack(ModFluids.NUTRITIONAL_PASTE, 500)).build(consumer, Symbiont.id("metabolizer/nutritional_paste_from_crops")
        );

        MetabolizerRecipeBuilder.newRecipe(
                NonNullList.of(Ingredient.EMPTY, CellCultureItem.asIngredient(Symbiont.id("proto")), Ingredient.of(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "crops")))),
                SizedFluidIngredient.of(new FluidStack(Fluids.WATER, 250)),
                CellCultureItem.withCellType(Symbiont.id("proto")),
                new FluidStack(ModFluids.NUTRITIONAL_PASTE, 250)).build(consumer, Symbiont.id("metabolizer/nutritional_paste_from_seeds")
        );

        MetabolizerRecipeBuilder.newRecipe(
                NonNullList.of(Ingredient.EMPTY, CellCultureItem.asIngredient(Symbiont.id("glucose")), Ingredient.of(Items.SUGAR)),
                SizedFluidIngredient.of(new FluidStack(ModFluids.NUTRITIONAL_PASTE, 500)),
                CellCultureItem.withCellType(Symbiont.id("glucose")),
                new FluidStack(ModFluids.SWEET_PASTE, 500)).build(consumer, Symbiont.id("metabolizer/sweet_paste_from_sugar")
        );

        MetabolizerRecipeBuilder.newRecipe(
                NonNullList.of(Ingredient.EMPTY, CellCultureItem.asIngredient(Symbiont.id("glucose")), Ingredient.of(Items.BEETROOT)),
                SizedFluidIngredient.of(new FluidStack(ModFluids.NUTRITIONAL_PASTE, 1000)),
                CellCultureItem.withCellType(Symbiont.id("glucose")),
                new FluidStack(ModFluids.SWEET_PASTE, 1000)).build(consumer, Symbiont.id("metabolizer/sweet_paste_from_beetroot")
        );

        MetabolizerRecipeBuilder.newRecipe(
                NonNullList.of(Ingredient.EMPTY, CellCultureItem.asIngredient(Symbiont.id("glucose")), Ingredient.of(Items.SWEET_BERRIES)),
                SizedFluidIngredient.of(new FluidStack(ModFluids.NUTRITIONAL_PASTE, 250)),
                CellCultureItem.withCellType(Symbiont.id("glucose")),
                new FluidStack(ModFluids.SWEET_PASTE, 250)).build(consumer, Symbiont.id("metabolizer/sweet_paste_from_sweet_berries")
        );

        MetabolizerRecipeBuilder.newRecipe(
                NonNullList.of(Ingredient.EMPTY, CellCultureItem.asIngredient(Symbiont.id("proto")), Ingredient.of(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "foods/raw_meat")))),
                SizedFluidIngredient.of(new FluidStack(ModFluids.NUTRITIONAL_PASTE, 1000)),
                CellCultureItem.withCellType(Symbiont.id("myoblast")),
                new FluidStack(ModFluids.MYOGENIC_BIOMASS, 500)).build(consumer, Symbiont.id("metabolizer/myoblast")
        );

        MetabolizerRecipeBuilder.newRecipe(
                NonNullList.of(Ingredient.EMPTY, CellCultureItem.asIngredient(Symbiont.id("proto")), Ingredient.of(Items.CHORUS_FRUIT), Ingredient.of(Items.WHEAT)),
                SizedFluidIngredient.of(new FluidStack(ModFluids.NUTRITIONAL_PASTE, 250)),
                CellCultureItem.withCellType(Symbiont.id("proto")),
                new FluidStack(ModFluids.PROTEIN_PASTE, 250)).build(consumer, Symbiont.id("metabolizer/protein_paste")
        );

        BioreactorRecipeBuilder.newBioreactorRecipe
                        (CellCultureItem.asIngredient(Symbiont.id("myoblast")),
                                SizedFluidIngredient.of(new FluidStack(ModFluids.PROTEIN_PASTE, 500)),
                                CellCultureItem.withCellType(Symbiont.id("myoblast")),
                                new FluidStack(ModFluids.MYOGENIC_BIOMASS, 500))
                .build(consumer, Symbiont.id("bioreactor/myogenic_biomass"));

        BioformerRecipeBuilder.newRecipe(
                CellCultureItem.asIngredient(Symbiont.id("bovine")),
                Ingredient.EMPTY,
                SizedFluidIngredient.of(new FluidStack(ModFluids.MYOGENIC_BIOMASS, 500)),
                NonNullList.of(ItemStack.EMPTY, CellCultureItem.withCellType(Symbiont.id("bovine")), new ItemStack(Items.BEEF))
        ).build(consumer, Symbiont.id("bioformer/beef"));

        BioformerRecipeBuilder.newRecipe(
                CellCultureItem.asIngredient(Symbiont.id("ovine")),
                Ingredient.EMPTY,
                SizedFluidIngredient.of(new FluidStack(ModFluids.MYOGENIC_BIOMASS, 500)),
                NonNullList.of(ItemStack.EMPTY, CellCultureItem.withCellType(Symbiont.id("ovine")), new ItemStack(Items.MUTTON))
        ).build(consumer, Symbiont.id("bioformer/mutton"));

        BioformerRecipeBuilder.newRecipe(
                CellCultureItem.asIngredient(Symbiont.id("porcine")),
                Ingredient.EMPTY,
                SizedFluidIngredient.of(new FluidStack(ModFluids.MYOGENIC_BIOMASS, 500)),
                NonNullList.of(ItemStack.EMPTY, CellCultureItem.withCellType(Symbiont.id("porcine")), new ItemStack(Items.PORKCHOP))
        ).build(consumer, Symbiont.id("bioformer/porkchop"));

        BioformerRecipeBuilder.newRecipe(
                CellCultureItem.asIngredient(Symbiont.id("avian")),
                Ingredient.EMPTY,
                SizedFluidIngredient.of(new FluidStack(ModFluids.MYOGENIC_BIOMASS, 500)),
                NonNullList.of(ItemStack.EMPTY, CellCultureItem.withCellType(Symbiont.id("avian")), new ItemStack(Items.CHICKEN))
        ).build(consumer, Symbiont.id("bioformer/chicken"));

        BioformerRecipeBuilder.newRecipe(
                CellCultureItem.asIngredient(Symbiont.id("leporine")),
                Ingredient.EMPTY,
                SizedFluidIngredient.of(new FluidStack(ModFluids.MYOGENIC_BIOMASS, 500)),
                NonNullList.of(ItemStack.EMPTY, CellCultureItem.withCellType(Symbiont.id("leporine")), new ItemStack(Items.RABBIT))
        ).build(consumer, Symbiont.id("bioformer/rabbit"));

        MetabolizerRecipeBuilder.newRecipe(
                NonNullList.of(Ingredient.EMPTY, CellCultureItem.asIngredient(Symbiont.id("proto")), Ingredient.of(Items.HONEYCOMB), Ingredient.of(Items.WHEAT), Ingredient.of(Items.SUGAR)),
                SizedFluidIngredient.of(new FluidStack(ModFluids.SWEET_PASTE, 500)),
                CellCultureItem.withCellType(Symbiont.id("proto")),
                new FluidStack(ModFluids.STICKY_PASTE, 500)
        ).build(consumer, Symbiont.id("metabolizer/sticky_paste"));

        BioreactorRecipeBuilder.newBioreactorRecipe(
                CellCultureItem.asIngredient(Symbiont.id("poly")),
                SizedFluidIngredient.of(new FluidStack(ModFluids.STICKY_PASTE, 500)),
                CellCultureItem.withCellType(Symbiont.id("poly")),
                new FluidStack(ModFluids.BIOPOLYMER_SOLUTION, 500)
        ).build(consumer, Symbiont.id("bioreactor/biopolymer_solution"));

        BioformerRecipeBuilder.newRecipe(
                Ingredient.EMPTY,
                Ingredient.EMPTY,
                SizedFluidIngredient.of(new FluidStack(ModFluids.BIOPOLYMER_SOLUTION, 1000)),
                NonNullList.of(ItemStack.EMPTY, new ItemStack(ModItems.BIOPLASTIC_SHEET.get()))
        ).build(consumer, Symbiont.id("bioformer/bioplastic_sheet"));
    }
}
