package piotro15.symbiont.common.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import piotro15.symbiont.common.recipe.BoneSamplingRecipe;
import piotro15.symbiont.common.registry.ModRecipeTypes;

public class BoneSamplerItem extends Item {
    public BoneSamplerItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        ItemStack product = null;
        Level level = interactionTarget.level();
        for (RecipeHolder<BoneSamplingRecipe> holder : level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.BONE_SAMPLING.get())) {
            BoneSamplingRecipe recipe = holder.value();
            if (recipe.matches(interactionTarget)) {
                product = recipe.getResultItem(level.registryAccess());
                break;
            }
        }

        if (product == null) {
            return InteractionResult.PASS;
        }

        stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
        interactionTarget.hurt(player.damageSources().generic(), 1.0F);
        if (!player.getInventory().add(product)) {
            player.drop(product, false);
        }

        return InteractionResult.SUCCESS;
    }
}
