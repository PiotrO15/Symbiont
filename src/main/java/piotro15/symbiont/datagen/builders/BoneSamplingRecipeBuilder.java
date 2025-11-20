package piotro15.symbiont.datagen.builders;

import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.conditions.ICondition;
import piotro15.symbiont.common.recipe.BoneSamplingRecipe;

import java.util.ArrayList;

public record BoneSamplingRecipeBuilder(
        EntityType<?> entityType,
        ItemStack output
) {
    public void build(RecipeOutput consumer, ResourceLocation id) {
        consumer.accept(id, new BoneSamplingRecipe(entityType, output), null, new ArrayList<ICondition>().toArray(ICondition[]::new));
    }

    public static BoneSamplingRecipeBuilder newRecipe(EntityType<?> entityType, ItemStack itemOutput) {
        return new BoneSamplingRecipeBuilder(entityType, itemOutput);
    }
}
