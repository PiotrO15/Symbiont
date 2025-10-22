package piotro15.symbiont.common.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import piotro15.symbiont.common.Symbiont;

public class BoneSamplerItem extends Item {
    public BoneSamplerItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        ItemStack product;
        if (interactionTarget.getType() == EntityType.COW) {
            product = CellCultureItem.withCellType(Symbiont.id("bovine"));
        } else if (interactionTarget.getType() == EntityType.SHEEP) {
            product = CellCultureItem.withCellType(Symbiont.id("ovine"));
        } else if (interactionTarget.getType() == EntityType.PIG) {
            product = CellCultureItem.withCellType(Symbiont.id("porcine"));
        } else if (interactionTarget.getType() == EntityType.CHICKEN) {
            product = CellCultureItem.withCellType(Symbiont.id("avian"));
        } else if (interactionTarget.getType() == EntityType.RABBIT) {
            product = CellCultureItem.withCellType(Symbiont.id("leporine"));
        } else if (interactionTarget.getType() == EntityType.SNIFFER) {
            product = CellCultureItem.withCellType(Symbiont.id("mossling"));
        } else {
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
