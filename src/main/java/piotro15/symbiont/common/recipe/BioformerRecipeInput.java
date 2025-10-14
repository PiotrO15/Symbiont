package piotro15.symbiont.common.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.fluids.FluidStack;

public record BioformerRecipeInput(
        ItemStack input,
        ItemStack catalyst,
        FluidStack fluidInput
) implements RecipeInput {

    @Override
    public ItemStack getItem(int slot) {
        if (slot < 0 || slot > 1) throw new IllegalArgumentException("No item for index " + slot);
        return slot == 0 ? input : catalyst;
    }

    @Override
    public int size() {
        int size = 0;
        if (input != null)
            size++;

        if (catalyst != null)
            size++;

        return size;
    }
}
