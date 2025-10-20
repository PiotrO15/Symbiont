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
import piotro15.symbiont.common.genetics.Biocode;
import piotro15.symbiont.common.item.CellCultureItem;
import piotro15.symbiont.common.registry.ModMenuTypes;

import java.util.List;

public class RecombinatorMenu extends BasicMachineMenu {
    private final RecombinatorBlockEntity blockEntity;

    public RecombinatorMenu(int id, Inventory playerInv, BlockPos pos) {
        super(ModMenuTypes.RECOMBINATOR.get(), id, 6, 6);
        RecombinatorBlockEntity be = playerInv.player.level().getBlockEntity(pos) instanceof RecombinatorBlockEntity ? (RecombinatorBlockEntity) playerInv.player.level().getBlockEntity(pos) : null;

        this.blockEntity = be;

        if (be == null) {
            return;
        }

        ItemStackHandler handler = be.getItems();

        this.addSlot(new SlotItemHandler(handler, 0, 80, 86));

        this.addSlot(new SlotItemHandler(handler, 1, 35, 28));
        this.addSlot(new SlotItemHandler(handler, 2, 35, 50));
        this.addSlot(new SlotItemHandler(handler, 3, 125, 17));
        this.addSlot(new SlotItemHandler(handler, 4, 125, 39));
        this.addSlot(new SlotItemHandler(handler, 5, 125, 61));

        for (int row = 0; row < 3; ++row)
            for (int col = 0; col < 9; ++col)
                this.addSlot(new Slot(playerInv, col + row * 9 + 9, 8 + col * 18, 117 + row * 18));

        for (int col = 0; col < 9; ++col)
            this.addSlot(new Slot(playerInv, col, 8 + col * 18, 175));
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return this.blockEntity != null &&
                this.blockEntity.getBlockPos().closerThan(player.blockPosition(), 8);
    }

    @Override
    public boolean isCrafting() {
        return false;
    }

    @Override
    public int getScaledArrowProgress() {
        return 0;
    }

    public boolean hasCell() {
        ItemStack cellStack = blockEntity.getItems().getStackInSlot(0);
        return !cellStack.isEmpty();
    }

    public List<CellCultureItem.AppliedBiotrait> getResultingBiocode() {
        return blockEntity.getResultingBiocode();
    }
}

