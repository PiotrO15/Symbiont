package piotro15.symbiont.common.menu;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import piotro15.symbiont.common.block.entity.RecombinatorBlockEntity;
import piotro15.symbiont.common.registry.ModMenuTypes;

public class RecombinatorMenu extends AbstractContainerMenu {
    private final RecombinatorBlockEntity blockEntity;

    public RecombinatorMenu(int id, Inventory playerInv, BlockPos pos) {
        super(ModMenuTypes.RECOMBINATOR.get(), id);
        RecombinatorBlockEntity be = playerInv.player.level().getBlockEntity(pos) instanceof RecombinatorBlockEntity ? (RecombinatorBlockEntity) playerInv.player.level().getBlockEntity(pos) : null;

        this.blockEntity = be;

        ItemStackHandler handler = be.getItems();

        // Slot 0: Cell in the center
        this.addSlot(new SlotItemHandler(handler, 0, 80, 35));

        // Slots 1–5: Genetic strains around it
        this.addSlot(new SlotItemHandler(handler, 1, 26, 35));
        this.addSlot(new SlotItemHandler(handler, 2, 53, 17));
        this.addSlot(new SlotItemHandler(handler, 3, 107, 17));
        this.addSlot(new SlotItemHandler(handler, 4, 134, 90));
        this.addSlot(new SlotItemHandler(handler, 5, 80, 53));

        // Player inventory slots
        for (int row = 0; row < 3; ++row)
            for (int col = 0; col < 9; ++col)
                this.addSlot(new Slot(playerInv, col + row * 9 + 9, 8 + col * 18, 140 + row * 18));

        for (int col = 0; col < 9; ++col)
            this.addSlot(new Slot(playerInv, col, 8 + col * 18, 198));
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.blockEntity != null &&
                this.blockEntity.getBlockPos().closerThan(player.blockPosition(), 8);
    }
}

