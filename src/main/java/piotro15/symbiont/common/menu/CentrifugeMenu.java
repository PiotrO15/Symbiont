package piotro15.symbiont.common.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import piotro15.symbiont.common.block.entity.CentrifugeBlockEntity;
import piotro15.symbiont.common.registry.ModMenuTypes;
import piotro15.symbiont.util.OutputSlotItemHandler;

public class CentrifugeMenu extends BasicMachineMenu {
    private final CentrifugeBlockEntity blockEntity;
    private final ContainerData data;

    public CentrifugeMenu(int id, Inventory playerInv, CentrifugeBlockEntity blockEntity, ContainerData data) {
        super(ModMenuTypes.CENTRIFUGE.get(), id, 3, 2);
        this.blockEntity = blockEntity;
        this.data = data;

        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 0, 63, 25));
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 1, 63, 43));
        this.addSlot(new OutputSlotItemHandler(blockEntity.getInventory(), 2, 121, 35));

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

    public int getContainerData(int id) {
        return this.data.get(id);
    }

    public int getScaledArrowProgress() {
        int progress = data.get(0);
        int maxProgress = data.get(1);
        int arrowPixelSize = 24;

        return maxProgress != 0 && progress != 0 ? progress * arrowPixelSize / maxProgress : 0;
    }
}

