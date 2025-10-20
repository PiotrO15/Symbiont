package piotro15.symbiont.common.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record MetabolizerRecipeInput(
        List<ItemStack> stacks,
        FluidStack fluidInput
) implements RecipeInput {
    @Override
    public @NotNull ItemStack getItem(int slot) {
        if (slot < 0 || slot > 4) throw new IllegalArgumentException("No item for index " + slot);
        return this.stacks().get(slot);
    }

    @Override
    public int size() {
        return stacks.size();
    }
}
