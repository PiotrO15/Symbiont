package piotro15.symbiont.api;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import piotro15.symbiont.common.Symbiont;

import java.util.List;

public class ItemApi {
    public static boolean canFitOutputs(ItemStackHandler handler, List<ItemStack> results, int outputStart, int outputEnd) {
        ItemStackHandler simulated = new ItemStackHandler(handler.getSlots());
        for (int i = 0; i < handler.getSlots(); i++) {
            simulated.setStackInSlot(i, handler.getStackInSlot(i).copy());
        }

        for (ItemStack result : results) {
            if (result.isEmpty()) continue;

            ItemStack remaining = result.copy();
            for (int i = outputStart; i < outputEnd && !remaining.isEmpty(); i++) {
                remaining = simulated.insertItem(i, remaining, false);
            }

            if (!remaining.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public static boolean canFit(ItemStackHandler handler, ItemStack stack, int outputStart, int outputEnd) {
        if (stack.isEmpty())
            return true;

        ItemStack remaining = stack.copy();

        for (int i = outputStart; i < outputEnd && !remaining.isEmpty(); i++) {
            remaining = handler.insertItem(i, remaining, true);
        }

        return remaining.isEmpty();
    }

    public static void insertIntoInventory(ItemStackHandler handler, ItemStack stack, int outputStart, int outputEnd) {
        if (stack.isEmpty())
            return;

        ItemStack remaining = stack.copy();

        for (int i = outputStart; i < outputEnd && !remaining.isEmpty(); i++) {
            remaining = handler.insertItem(i, remaining, false);
        }

        if (!remaining.isEmpty())
            Symbiont.LOGGER.warn("Couldn't input {}x {}, deleting the item!", remaining.getCount(), remaining.getItem());
    }
}
