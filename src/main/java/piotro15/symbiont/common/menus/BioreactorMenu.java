package piotro15.symbiont.common.menus;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import piotro15.symbiont.common.blocks.entities.BioreactorBlockEntity;
import piotro15.symbiont.common.registries.ModMenuTypes;

public class BioreactorMenu extends BasicMachineMenu {
    private final int slotCount = 2;
    private final BioreactorBlockEntity blockEntity;
    private final ContainerData data;

    public BioreactorMenu(int id, Inventory playerInv, BioreactorBlockEntity blockEntity, ContainerData data) {
        super(ModMenuTypes.BIOREACTOR.get(), id);
        this.blockEntity = blockEntity;
        this.data = data;

        this.addSlot(new SlotItemHandler(blockEntity.getItems(), 0, 63, 35));
        this.addSlot(new SlotItemHandler(blockEntity.getItems(), 1, 121, 35));

        this.addPlayerInventory(playerInv);
        this.addPlayerHotbar(playerInv);

        addDataSlots(data);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack rawStack = slot.getItem();
            itemStack = rawStack.copy();

            if (index == 1) {
                if (!this.moveItemStackTo(rawStack, slotCount, Inventory.INVENTORY_SIZE + slotCount, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(rawStack, itemStack);
            }
            else if (index >= slotCount && index < Inventory.INVENTORY_SIZE + slotCount) {
                if (!this.moveItemStackTo(rawStack, 0, 1, false)) {
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
    public boolean stillValid(@NotNull Player player) {
        return this.blockEntity != null &&
                this.blockEntity.getBlockPos().closerThan(player.blockPosition(), 8);
    }

    public FluidStack getInputFluid() {
        return blockEntity.getInputTank().getFluid();
    }

    public FluidTank getInputFluidTank() {
        return blockEntity.getInputTank();
    }

    public FluidTank getOutputFluidTank() {
        return blockEntity.getOutputTank();
    }

    public FluidStack getOutputFluid() {
        return blockEntity.getOutputTank().getFluid();
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getContainerData(int id) {
        return this.data.get(id);
    }

    public int getScaledArrowProgress() {
        int progress = data.get(0);
        int maxProgress = blockEntity.getMaxProgress();
        int arrowPixelSize = 24;

        return maxProgress != 0 && progress != 0 ? progress * arrowPixelSize / maxProgress : 0;
    }
}

