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
    private final BioreactorBlockEntity blockEntity;
    private final ContainerData data;

    public BioreactorMenu(int id, Inventory playerInv, BioreactorBlockEntity blockEntity, ContainerData data) {
        super(ModMenuTypes.BIOREACTOR.get(), id, 2);
        this.blockEntity = blockEntity;
        this.data = data;

        this.addSlot(new SlotItemHandler(blockEntity.getItems(), 0, 63, 35));
        this.addSlot(new SlotItemHandler(blockEntity.getItems(), 1, 121, 35));

        this.addPlayerInventory(playerInv);
        this.addPlayerHotbar(playerInv);

        addDataSlots(data);
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

