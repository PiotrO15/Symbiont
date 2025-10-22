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

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CENTRIFUGE)
                .pattern("BBB")
                .pattern("OIO")
                .pattern(" I ")
                .define('I', Items.IRON_INGOT)
                .define('O', Items.GLASS_BOTTLE)
                .define('B', ModItems.BIOPLASTIC_SHEET)
                .unlockedBy("has_bioplastic_sheet", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.BIOPLASTIC_SHEET)
                ).save(consumer, Symbiont.id("centrifuge"));

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
                NonNullList.of(Ingredient.EMPTY, CellCultureItem.asIngredient(Symbiont.id("proto")), Ingredient.of(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "seeds")))),
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
                NonNullList.of(Ingredient.EMPTY, Ingredient.of(Items.BOWL), Ingredient.of(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "foods/raw_meat")))),
                SizedFluidIngredient.of(new FluidStack(ModFluids.NUTRITIONAL_PASTE, 1000)),
                CellCultureItem.withCellType(Symbiont.id("myoblast")),
                new FluidStack(ModFluids.MYOGENIC_BIOMASS, 500)).build(consumer, Symbiont.id("metabolizer/myoblast_cell")
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

        MetabolizerRecipeBuilder.newRecipe(
                NonNullList.of(Ingredient.EMPTY, Ingredient.of(Items.BOWL), Ingredient.of(Items.SUGAR), Ingredient.of(Items.SUGAR), Ingredient.of(Items.SUGAR), Ingredient.of(Items.SUGAR)),
                SizedFluidIngredient.of(new FluidStack(ModFluids.NUTRITIONAL_PASTE, 1000)),
                CellCultureItem.withCellType(Symbiont.id("glucose")),
                new FluidStack(ModFluids.SWEET_PASTE, 500)).build(consumer, Symbiont.id("metabolizer/sweet_cell")
        );

        MetabolizerRecipeBuilder.newRecipe(
                NonNullList.of(Ingredient.EMPTY, Ingredient.of(Items.BOWL), Ingredient.of(Items.SLIME_BALL), Ingredient.of(Items.SUGAR), Ingredient.of(Items.HONEYCOMB)),
                SizedFluidIngredient.of(new FluidStack(ModFluids.STICKY_PASTE, 2000)),
                CellCultureItem.withCellType(Symbiont.id("poly")),
                new FluidStack(ModFluids.STICKY_PASTE, 500)).build(consumer, Symbiont.id("metabolizer/polycell")
        );

        BioformerRecipeBuilder.newRecipe(
                CellCultureItem.asIngredient(Symbiont.id("mossling")),
                Ingredient.of(Items.COBBLESTONE),
                SizedFluidIngredient.of(new FluidStack(Fluids.WATER, 1000)),
                NonNullList.of(ItemStack.EMPTY, new ItemStack(Items.MOSS_BLOCK))
        ).build(consumer, Symbiont.id("bioformer/moss_block"));

        MetabolizerRecipeBuilder.newRecipe(
                NonNullList.of(Ingredient.EMPTY, CellCultureItem.asIngredient(Symbiont.id("glucose")), Ingredient.of(Items.MELON_SLICE)),
                SizedFluidIngredient.of(new FluidStack(ModFluids.SWEET_PASTE, 1000)),
                CellCultureItem.withCellType(Symbiont.id("glucose")),
                new FluidStack(ModFluids.FERRIC_PASTE, 1000)
        ).build(consumer, Symbiont.id("metabolizer/ferric_paste"));

        MetabolizerRecipeBuilder.newRecipe(
                NonNullList.of(Ingredient.EMPTY, CellCultureItem.asIngredient(Symbiont.id("glucose")), Ingredient.of(Items.CARROT)),
                SizedFluidIngredient.of(new FluidStack(ModFluids.SWEET_PASTE, 1000)),
                CellCultureItem.withCellType(Symbiont.id("glucose")),
                new FluidStack(ModFluids.CUPRIC_PASTE, 1000)
        ).build(consumer, Symbiont.id("metabolizer/cupric_paste"));

        MetabolizerRecipeBuilder.newRecipe(
                NonNullList.of(Ingredient.EMPTY, CellCultureItem.asIngredient(Symbiont.id("glucose")), Ingredient.of(Items.PUMPKIN)),
                SizedFluidIngredient.of(new FluidStack(ModFluids.CUPRIC_SOLUTION, 1000)),
                CellCultureItem.withCellType(Symbiont.id("glucose")),
                new FluidStack(ModFluids.ENRICHED_CUPRIC_SOLUTION, 500)
        ).build(consumer, Symbiont.id("metabolizer/enriched_cupric_solution"));

        BioreactorRecipeBuilder.newBioreactorRecipe(
                CellCultureItem.asIngredient(Symbiont.id("ferric")),
                SizedFluidIngredient.of(new FluidStack(ModFluids.FERRIC_PASTE, 250)),
                CellCultureItem.withCellType(Symbiont.id("ferric")),
                new FluidStack(ModFluids.FERRIC_SOLUTION, 250)
        ).build(consumer, Symbiont.id("bioreactor/ferric_solution"));

        BioreactorRecipeBuilder.newBioreactorRecipe(
                CellCultureItem.asIngredient(Symbiont.id("auric")),
                SizedFluidIngredient.of(new FluidStack(ModFluids.CUPRIC_PASTE, 250)),
                CellCultureItem.withCellType(Symbiont.id("auric")),
                new FluidStack(ModFluids.CUPRIC_SOLUTION, 250)
        ).build(consumer, Symbiont.id("bioreactor/cupric_solution"));

        BioreactorRecipeBuilder.newBioreactorRecipe(
                CellCultureItem.asIngredient(Symbiont.id("auric")),
                SizedFluidIngredient.of(new FluidStack(ModFluids.ENRICHED_CUPRIC_SOLUTION, 250)),
                CellCultureItem.withCellType(Symbiont.id("auric")),
                new FluidStack(ModFluids.AURIC_SOLUTION, 250)
        ).build(consumer, Symbiont.id("bioreactor/auric_solution"));

        BioformerRecipeBuilder.newRecipe(
                Ingredient.EMPTY,
                Ingredient.EMPTY,
                SizedFluidIngredient.of(new FluidStack(ModFluids.FERRIC_SOLUTION, 1000)),
                NonNullList.of(ItemStack.EMPTY, new ItemStack(Items.RAW_IRON))
        ).build(consumer, Symbiont.id("bioformer/raw_iron"));

        BioformerRecipeBuilder.newRecipe(
                Ingredient.EMPTY,
                Ingredient.EMPTY,
                SizedFluidIngredient.of(new FluidStack(ModFluids.CUPRIC_SOLUTION, 1000)),
                NonNullList.of(ItemStack.EMPTY, new ItemStack(Items.RAW_COPPER))
        ).build(consumer, Symbiont.id("bioformer/raw_copper"));

        BioformerRecipeBuilder.newRecipe(
                Ingredient.EMPTY,
                Ingredient.EMPTY,
                SizedFluidIngredient.of(new FluidStack(ModFluids.AURIC_SOLUTION, 1000)),
                NonNullList.of(ItemStack.EMPTY, new ItemStack(Items.RAW_GOLD))
        ).build(consumer, Symbiont.id("bioformer/raw_gold"));

        MetabolizerRecipeBuilder.newRecipe(
                NonNullList.of(Ingredient.EMPTY, Ingredient.of(Items.BOWL), Ingredient.of(Items.IRON_INGOT)),
                SizedFluidIngredient.of(new FluidStack(ModFluids.FERRIC_PASTE, 1000)),
                CellCultureItem.withCellType(Symbiont.id("ferric")),
                new FluidStack(ModFluids.FERRIC_PASTE, 500)
        ).build(consumer, Symbiont.id("metabolizer/ferric_cell"));

        MetabolizerRecipeBuilder.newRecipe(
                NonNullList.of(Ingredient.EMPTY, Ingredient.of(Items.BOWL), Ingredient.of(Items.COPPER_INGOT), Ingredient.of(Items.GOLD_INGOT)),
                SizedFluidIngredient.of(new FluidStack(ModFluids.CUPRIC_PASTE, 1000)),
                CellCultureItem.withCellType(Symbiont.id("auric")),
                new FluidStack(ModFluids.CUPRIC_SOLUTION, 500)
        ).build(consumer, Symbiont.id("metabolizer/auric_cell"));

        MetabolizerRecipeBuilder.newRecipe(
                NonNullList.of(Ingredient.EMPTY, Ingredient.of(Items.BOWL), Ingredient.of(Items.SEAGRASS), Ingredient.of(Items.KELP)),
                SizedFluidIngredient.of(new FluidStack(Fluids.WATER, 1000)),
                CellCultureItem.withCellType(Symbiont.id("marine")),
                new FluidStack(ModFluids.MARINE_EXTRACT, 500)
        ).build(consumer, Symbiont.id("metabolizer/marine_cell"));

        MetabolizerRecipeBuilder.newRecipe(
                NonNullList.of(Ingredient.EMPTY, CellCultureItem.asIngredient(Symbiont.id("marine")), Ingredient.of(Items.KELP)),
                SizedFluidIngredient.of(new FluidStack(Fluids.WATER, 1000)),
                CellCultureItem.withCellType(Symbiont.id("marine")),
                new FluidStack(ModFluids.MARINE_EXTRACT, 1000)
        ).build(consumer, Symbiont.id("metabolizer/marine_extract"));

        MetabolizerRecipeBuilder.newRecipe(
                NonNullList.of(Ingredient.EMPTY, Ingredient.of(Items.BOWL), Ingredient.of(Items.NAUTILUS_SHELL)),
                SizedFluidIngredient.of(new FluidStack(ModFluids.MARINE_EXTRACT, 1000)),
                CellCultureItem.withCellType(Symbiont.id("coral")),
                new FluidStack(ModFluids.MARINE_EXTRACT, 500)
        ).build(consumer, Symbiont.id("metabolizer/coral_cell"));

        BioformerRecipeBuilder.newRecipe(
                CellCultureItem.asIngredient(Symbiont.id("coral")),
                Ingredient.of(Items.PINK_DYE),
                SizedFluidIngredient.of(new FluidStack(ModFluids.MARINE_EXTRACT, 500)),
                NonNullList.of(ItemStack.EMPTY, CellCultureItem.withCellType(Symbiont.id("coral")), new ItemStack(Items.BRAIN_CORAL))
        ).build(consumer, Symbiont.id("bioformer/brain_coral"));

        BioformerRecipeBuilder.newRecipe(
                CellCultureItem.asIngredient(Symbiont.id("coral")),
                Ingredient.of(Items.PURPLE_DYE),
                SizedFluidIngredient.of(new FluidStack(ModFluids.MARINE_EXTRACT, 500)),
                NonNullList.of(ItemStack.EMPTY, CellCultureItem.withCellType(Symbiont.id("coral")), new ItemStack(Items.BUBBLE_CORAL))
        ).build(consumer, Symbiont.id("bioformer/bubble_coral"));

        BioformerRecipeBuilder.newRecipe(
                CellCultureItem.asIngredient(Symbiont.id("coral")),
                Ingredient.of(Items.RED_DYE),
                SizedFluidIngredient.of(new FluidStack(ModFluids.MARINE_EXTRACT, 500)),
                NonNullList.of(ItemStack.EMPTY, CellCultureItem.withCellType(Symbiont.id("coral")), new ItemStack(Items.FIRE_CORAL))
        ).build(consumer, Symbiont.id("bioformer/fire_coral"));

        BioformerRecipeBuilder.newRecipe(
                CellCultureItem.asIngredient(Symbiont.id("coral")),
                Ingredient.of(Items.YELLOW_DYE),
                SizedFluidIngredient.of(new FluidStack(ModFluids.MARINE_EXTRACT, 500)),
                NonNullList.of(ItemStack.EMPTY, CellCultureItem.withCellType(Symbiont.id("coral")), new ItemStack(Items.HORN_CORAL))
        ).build(consumer, Symbiont.id("bioformer/horn_coral"));

        BioformerRecipeBuilder.newRecipe(
                CellCultureItem.asIngredient(Symbiont.id("coral")),
                Ingredient.of(Items.BLUE_DYE),
                SizedFluidIngredient.of(new FluidStack(ModFluids.MARINE_EXTRACT, 500)),
                NonNullList.of(ItemStack.EMPTY, CellCultureItem.withCellType(Symbiont.id("coral")), new ItemStack(Items.TUBE_CORAL))
        ).build(consumer, Symbiont.id("bioformer/tube_coral"));
    }
}
