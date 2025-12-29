package piotro15.symbiont.util;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import piotro15.symbiont.common.recipe.MetabolizerRecipe;
import piotro15.symbiont.common.registry.ModRecipeTypes;

import java.util.List;

public class MetabolizerCellSlotItemHandler extends SlotItemHandler {
    private final Level level;

    public MetabolizerCellSlotItemHandler(IItemHandler itemHandler, int index, int xPosition, int yPosition, Level level) {
        super(itemHandler, index, xPosition, yPosition);
        this.level = level;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        if (stack.isEmpty()) return false;
        if (level == null) return false;

        RecipeManager recipeManager = level.getRecipeManager();
        List<? extends RecipeHolder<MetabolizerRecipe>> recipes = recipeManager.getAllRecipesFor(ModRecipeTypes.METABOLIZER.get());
        for (RecipeHolder<?> recipe : recipes) {
            if (recipe.value() instanceof MetabolizerRecipe mr) {
                if (!mr.ingredients().isEmpty() && mr.ingredients().getFirst().test(stack)) {
                    return true;
                }
            }
        }
        return false;
    }
}
