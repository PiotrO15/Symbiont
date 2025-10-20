package piotro15.symbiont.common.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import piotro15.symbiont.api.OutputSlotItemHandler;
import piotro15.symbiont.common.block.entity.BioformerBlockEntity;
import piotro15.symbiont.common.registry.ModMenuTypes;

public class BioformerMenu extends BasicMachineMenu {
    private final BioformerBlockEntity blockEntity;
    private final ContainerData data;

    public BioformerMenu(int id, Inventory playerInv, BioformerBlockEntity blockEntity, ContainerData data) {
        super(ModMenuTypes.BIOFORMER.get(), id, 6, 2);
        this.blockEntity = blockEntity;
        this.data = data;

        this.addSlot(new SlotItemHandler(blockEntity.getItems(), 0, 62, 26));
        this.addSlot(new SlotItemHandler(blockEntity.getItems(), 1, 62, 44));

        this.addSlot(new OutputSlotItemHandler(blockEntity.getItems(), 2, 125, 26));
        this.addSlot(new OutputSlotItemHandler(blockEntity.getItems(), 3, 143, 26));
        this.addSlot(new OutputSlotItemHandler(blockEntity.getItems(), 4, 125, 44));
        this.addSlot(new OutputSlotItemHandler(blockEntity.getItems(), 5, 143, 44));

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

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledArrowProgress() {
        int progress = data.get(0);
        int maxProgress = blockEntity.getMaxProgress();
        int arrowPixelSize = 24;

        return maxProgress != 0 && progress != 0 ? progress * arrowPixelSize / maxProgress : 0;
    }

    public int getContainerData(int id) {
        return this.data.get(id);
    }
}

