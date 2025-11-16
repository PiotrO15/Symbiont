package piotro15.symbiont.common.registry;

import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import piotro15.symbiont.common.Symbiont;
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
}
