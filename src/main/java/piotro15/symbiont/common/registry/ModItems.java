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
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.common.genetics.CellType;
import piotro15.symbiont.common.item.CellCultureItem;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Symbiont.MOD_ID);

    public static final DeferredItem<BlockItem> RECOMBINATOR = ITEMS.register("recombinator", () -> new BlockItem(ModBlocks.RECOMBINATOR.get(), new BlockItem.Properties()));
    public static final DeferredItem<BlockItem> BIOREACTOR = ITEMS.register("bioreactor", () -> new BlockItem(ModBlocks.BIOREACTOR.get(), new BlockItem.Properties()));
    public static final DeferredItem<BlockItem> METABOLIZER = ITEMS.register("metabolizer", () -> new BlockItem(ModBlocks.METABOLIZER.get(), new BlockItem.Properties()));
    public static final DeferredItem<BlockItem> BIOFORMER = ITEMS.register("bioformer", () -> new BlockItem(ModBlocks.BIOFORMER.get(), new BlockItem.Properties()));

    public static final DeferredItem<Item> CELL_CULTURE = ITEMS.register("cell_culture", () -> new CellCultureItem(new Item.Properties()));
    public static final DeferredItem<Item> CULTURE_STARTER = ITEMS.register("culture_starter", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ORGANIC_BINDER = ITEMS.register("organic_binder", () -> new Item(new Item.Properties()));

    public static final DeferredItem<BucketItem> NUTRITIONAL_PASTE_BUCKET = ITEMS.register("nutritional_paste_bucket", () -> new BucketItem(ModFluids.NUTRITIONAL_PASTE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final DeferredItem<BucketItem> SWEET_PASTE_BUCKET = ITEMS.register("sweet_paste_bucket", () -> new BucketItem(ModFluids.SWEET_PASTE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    @SubscribeEvent
    public static void buildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == ModCreativeModeTabs.COMMON.getKey()) {
            event.accept(BIOFORMER);
            event.accept(METABOLIZER);
            event.accept(BIOREACTOR);
            event.accept(RECOMBINATOR);
            event.accept(CULTURE_STARTER);
            event.accept(ORGANIC_BINDER);
            event.accept(NUTRITIONAL_PASTE_BUCKET);
            event.accept(SWEET_PASTE_BUCKET);
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
            }
        }
    }
}
