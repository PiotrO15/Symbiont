package piotro15.symbiont.common.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BasicMachineMenu extends AbstractContainerMenu {
    protected final int slotCount;
    protected final int inputSlotCount;

    protected BasicMachineMenu(@Nullable MenuType<?> menuType, int containerId, int slotCount, int inputSlotCount) {
        super(menuType, containerId);
        this.slotCount = slotCount;
        this.inputSlotCount = inputSlotCount;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack rawStack = slot.getItem();
            itemStack = rawStack.copy();

            if (index >= inputSlotCount && index < slotCount) {
                if (!this.moveItemStackTo(rawStack, slotCount, Inventory.INVENTORY_SIZE + slotCount, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(rawStack, itemStack);
            }
            else if (index >= slotCount && index < Inventory.INVENTORY_SIZE + slotCount) {
                if (!this.moveItemStackTo(rawStack, 0, inputSlotCount, false)) {
                    if (index < 27 + slotCount) {
                        if (!this.moveItemStackTo(rawStack, 27 + slotCount, Inventory.INVENTORY_SIZE + slotCount, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                    else if (!this.moveItemStackTo(rawStack, slotCount, 27 + slotCount, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }
            else if (!this.moveItemStackTo(rawStack, slotCount, Inventory.INVENTORY_SIZE + slotCount, false)) {
                return ItemStack.EMPTY;
            }

            if (rawStack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (rawStack.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, rawStack);
        }

        return itemStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return false;
    }

    public abstract boolean isCrafting();

    public abstract int getScaledArrowProgress();

    protected void addPlayerInventory(Inventory playerInventory) {
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
    }

    protected void addPlayerHotbar(Inventory playerInventory) {
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
    }
}
