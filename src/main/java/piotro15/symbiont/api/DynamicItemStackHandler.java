package piotro15.symbiont.api;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.ItemStackHandler;

public class DynamicItemStackHandler extends ItemStackHandler {
    private final BlockEntity blockEntity;

    public DynamicItemStackHandler(int size, BlockEntity blockEntity) {
        super(size);
        this.blockEntity = blockEntity;
    }

    @Override
    protected void onContentsChanged(int slot) {
        blockEntity.setChanged();

        Level level = blockEntity.getLevel();

        if(level != null && !level.isClientSide()) {
            level.sendBlockUpdated(blockEntity.getBlockPos(), blockEntity.getBlockState(), blockEntity.getBlockState(), 3);
        }
    }
}