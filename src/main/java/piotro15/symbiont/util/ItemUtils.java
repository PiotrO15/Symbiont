package piotro15.symbiont.util;

import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.items.ItemStackHandler;
import piotro15.symbiont.common.Symbiont;

import java.util.List;

public class ItemUtils {
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

    public static void insertIntoInventory(ItemStackHandler handler, ItemStack stack, int outputStart, int outputEnd) {
        if (stack.isEmpty())
            return;

        ItemStack remaining = stack.copy();

        for (int i = outputStart; i < outputEnd && !remaining.isEmpty(); i++) {
            remaining = handler.insertItem(i, remaining, false);
        }

        if (!remaining.isEmpty())
            Symbiont.LOGGER.warn("Couldn't itemInput {}x {}, deleting the item!", remaining.getCount(), remaining.getItem());
    }

    public static void extractFromInventory(ItemStackHandler handler, Ingredient ingredient, int inputStart, int inputEnd) {
        for (int i = inputStart; i < inputEnd; i++) {
            ItemStack stackInSlot = handler.getStackInSlot(i);
            if (ingredient.test(stackInSlot)) {
                handler.extractItem(i, 1, false);
                return;
            }
        }
    }

    public static int randomCount(int count, double multiplier, RandomSource random) {
        if (multiplier == 1.0) {
            return count;
        }

        int newCount = (int) Math.floor(count * multiplier);
        if (random.nextDouble() < (count * multiplier - newCount)) {
            newCount++;
        }
        return newCount;
    }
}
