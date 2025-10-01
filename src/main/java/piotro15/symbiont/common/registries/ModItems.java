package piotro15.symbiont.common.registries;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import piotro15.symbiont.common.Symbiont;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Symbiont.MOD_ID);

    public static final DeferredItem<BlockItem> CELL_EDITOR = ITEMS.register("cell_editor", () -> new BlockItem(ModBlocks.CELL_EDITOR.get(), new BlockItem.Properties()));

    public static final DeferredItem<Item> CELL_CULTURE = ITEMS.register("cell_culture", () -> new Item(new Item.Properties()));
}
