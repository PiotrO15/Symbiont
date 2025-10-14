package piotro15.symbiont.common.registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.common.item.CellCultureItem;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Symbiont.MOD_ID);

    public static final DeferredItem<BlockItem> RECOMBINATOR = ITEMS.register("recombinator", () -> new BlockItem(ModBlocks.RECOMBINATOR.get(), new BlockItem.Properties()));
    public static final DeferredItem<BlockItem> BIOREACTOR = ITEMS.register("bioreactor", () -> new BlockItem(ModBlocks.BIOREACTOR.get(), new BlockItem.Properties()));
    public static final DeferredItem<BlockItem> METABOLIZER = ITEMS.register("metabolizer", () -> new BlockItem(ModBlocks.METABOLIZER.get(), new BlockItem.Properties()));
    public static final DeferredItem<BlockItem> BIOFORMER = ITEMS.register("bioformer", () -> new BlockItem(ModBlocks.BIOFORMER.get(), new BlockItem.Properties()));

    public static final DeferredItem<Item> CELL_CULTURE = ITEMS.register("cell_culture", () -> new CellCultureItem(new Item.Properties()));
    public static final DeferredItem<Item> CULTURE_STARTER = ITEMS.register("culture_starter", () -> new Item(new Item.Properties()));
}
