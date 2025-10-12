package piotro15.symbiont.common.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.List;
import java.util.Objects;

public final class MetabolizerRecipeInput implements RecipeInput {
    private final List<ItemStack> stacks;
    private final FluidStack fluidInput;
    private final int ingredientCount;

    public MetabolizerRecipeInput(
            List<ItemStack> stacks,
            FluidStack fluidInput
    ) {
        this.stacks = stacks;
        this.fluidInput = fluidInput;
        this.ingredientCount = stacks.size();
    }

    @Override
    public ItemStack getItem(int slot) {
        if (slot < 0 || slot > 4) throw new IllegalArgumentException("No item for index " + slot);
        return this.stacks().get(slot);
    }

    public List<ItemStack> getItems() {
        return this.stacks();
    }

    @Override
    public int size() {
        return ingredientCount;
    }

    public List<ItemStack> stacks() {
        return stacks;
    }

    public FluidStack fluidInput() {
        return fluidInput;
    }

    public int ingredientCount() {
        return ingredientCount;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (MetabolizerRecipeInput) obj;
        return Objects.equals(this.stacks, that.stacks) &&
                Objects.equals(this.fluidInput, that.fluidInput) &&
                this.ingredientCount == that.ingredientCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(stacks, fluidInput, ingredientCount);
    }

    @Override
    public String toString() {
        return "MetabolizerRecipeInput[" +
                "stacks=" + stacks + ", " +
                "fluidInput=" + fluidInput + ", " +
                "ingredientCount=" + ingredientCount + ']';
    }

}
