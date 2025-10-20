package piotro15.symbiont.common.registry;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import piotro15.symbiont.client.SymbiontClient;
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.common.genetics.Biotrait;
import piotro15.symbiont.common.genetics.CellType;
import piotro15.symbiont.common.item.BoneSamplerItem;
import piotro15.symbiont.common.item.BiotraitExtractItem;
import piotro15.symbiont.common.item.CellCultureItem;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Symbiont.MOD_ID);

    public static final DeferredItem<BlockItem> RECOMBINATOR = ITEMS.register("recombinator", () -> new BlockItem(ModBlocks.RECOMBINATOR.get(), new BlockItem.Properties()));
    public static final DeferredItem<BlockItem> BIOREACTOR = ITEMS.register("bioreactor", () -> new BlockItem(ModBlocks.BIOREACTOR.get(), new BlockItem.Properties()));
    public static final DeferredItem<BlockItem> METABOLIZER = ITEMS.register("metabolizer", () -> new BlockItem(ModBlocks.METABOLIZER.get(), new BlockItem.Properties()));
    public static final DeferredItem<BlockItem> CENTRIFUGE = ITEMS.register("centrifuge", () -> new BlockItem(ModBlocks.CENTRIFUGE.get(), new BlockItem.Properties()));
    public static final DeferredItem<BlockItem> BIOFORMER = ITEMS.register("bioformer", () -> new BlockItem(ModBlocks.BIOFORMER.get(), new BlockItem.Properties()));

    public static final DeferredItem<Item> CELL_CULTURE = ITEMS.register("cell_culture", () -> new CellCultureItem(new Item.Properties()));
    public static final DeferredItem<Item> BIOTRAIT_EXTRACT = ITEMS.register("biotrait_extract", () -> new BiotraitExtractItem(new Item.Properties()));
    public static final DeferredItem<Item> ORGANIC_BINDER = ITEMS.register("organic_binder", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BIOPLASTIC_SHEET = ITEMS.register("bioplastic_sheet", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> BONE_SAMPLER = ITEMS.register("bone_sampler", () -> new BoneSamplerItem(new Item.Properties().durability(16)));

    public static final DeferredItem<BucketItem> NUTRITIONAL_PASTE_BUCKET = ITEMS.register("nutritional_paste_bucket", () -> new BucketItem(ModFluids.NUTRITIONAL_PASTE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final DeferredItem<BucketItem> SWEET_PASTE_BUCKET = ITEMS.register("sweet_paste_bucket", () -> new BucketItem(ModFluids.SWEET_PASTE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final DeferredItem<BucketItem> PROTEIN_PASTE_BUCKET = ITEMS.register("protein_paste_bucket", () -> new BucketItem(ModFluids.PROTEIN_PASTE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final DeferredItem<BucketItem> MYOGENIC_BIOMASS_BUCKET = ITEMS.register("myogenic_biomass_bucket", () -> new BucketItem(ModFluids.MYOGENIC_BIOMASS.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final DeferredItem<BucketItem> STICKY_PASTE_BUCKET = ITEMS.register("sticky_paste_bucket", () -> new BucketItem(ModFluids.STICKY_PASTE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final DeferredItem<BucketItem> BIOPOLYMER_SOLUTION_BUCKET = ITEMS.register("biopolymer_solution_bucket", () -> new BucketItem(ModFluids.BIOPOLYMER_SOLUTION.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    @SubscribeEvent
    public static void buildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == ModCreativeModeTabs.COMMON.getKey()) {
            event.accept(BIOFORMER);
            event.accept(METABOLIZER);
            event.accept(BIOREACTOR);
            event.accept(CENTRIFUGE);
            event.accept(RECOMBINATOR);
            event.accept(ORGANIC_BINDER);
            event.accept(BIOPLASTIC_SHEET);
            event.accept(BONE_SAMPLER);

            SymbiontClient.fluidTypeExtensions.forEach(((fluid, extension) -> event.accept(fluid.get().getBucket())));
        }
        if (event.getTabKey() == ModCreativeModeTabs.CELLS.getKey()) {
            Level level = Minecraft.getInstance().level;
            if (level != null) {
                Registry<CellType> blendTypeRegistry = level.registryAccess().registryOrThrow(ModRegistries.CELL_TYPE);

                for (ResourceKey<CellType> blendKey : blendTypeRegistry.registryKeySet()) {
                    ItemStack stack = new ItemStack(ModItems.CELL_CULTURE.get());
                    stack.set(ModDataComponents.CELL_TYPE.get(), blendKey.location());
                    event.accept(stack);
                }

                Registry<Biotrait> biotraitRegistry = level.registryAccess().registryOrThrow(ModRegistries.BIOTRAIT);
                for (ResourceKey<Biotrait> traitKey : biotraitRegistry.registryKeySet()) {
                    ItemStack stack = new ItemStack(ModItems.BIOTRAIT_EXTRACT.get());
                    stack.set(ModDataComponents.BIOTRAIT.get(), traitKey.location());
                    event.accept(stack);
                }
            }
        }
    }
}
