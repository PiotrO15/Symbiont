package piotro15.symbiont.util;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

public class DynamicFluidTank extends FluidTank {
    private final BlockEntity blockEntity;

    public DynamicFluidTank(int capacity, BlockEntity blockEntity) {
        super(capacity);
        this.blockEntity = blockEntity;
    }

    @Override
    protected void onContentsChanged() {
        blockEntity.setChanged();

        Level level = blockEntity.getLevel();

        if(level != null && !level.isClientSide()) {
            blockEntity.getLevel().sendBlockUpdated(blockEntity.getBlockPos(), blockEntity.getBlockState(), blockEntity.getBlockState(), 3);
        }
    }
}
