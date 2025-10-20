package piotro15.symbiont.common.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import piotro15.symbiont.util.OutputSlotItemHandler;
import piotro15.symbiont.common.block.entity.MetabolizerBlockEntity;
import piotro15.symbiont.common.registry.ModMenuTypes;

public class MetabolizerMenu extends BasicMachineMenu {
    private final MetabolizerBlockEntity blockEntity;
    private final ContainerData data;

    public MetabolizerMenu(int id, Inventory playerInv, MetabolizerBlockEntity blockEntity, ContainerData data) {
        super(ModMenuTypes.METABOLIZER.get(), id, 6, 5);
        this.blockEntity = blockEntity;
        this.data = data;

        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 0, 63, 49));

        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 1, 67, 17));
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 2, 85, 17));
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 3, 103, 17));
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 4, 121, 17));

        this.addSlot(new OutputSlotItemHandler(blockEntity.getInventory(), 5, 121, 49));

        this.addPlayerInventory(playerInv);
        this.addPlayerHotbar(playerInv);

        addDataSlots(data);
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return this.blockEntity != null &&
                this.blockEntity.getBlockPos().closerThan(player.blockPosition(), 8);
    }

    public FluidTank getInputFluidTank() {
        return blockEntity.getInputTank();
    }

    public FluidTank getOutputFluidTank() {
        return blockEntity.getOutputTank();
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledArrowProgress() {
        int progress = data.get(0);
        int maxProgress = data.get(1);
        int arrowPixelSize = 24;

        return maxProgress != 0 && progress != 0 ? progress * arrowPixelSize / maxProgress : 0;
    }

    public int getContainerData(int id) {
        return this.data.get(id);
    }
}

