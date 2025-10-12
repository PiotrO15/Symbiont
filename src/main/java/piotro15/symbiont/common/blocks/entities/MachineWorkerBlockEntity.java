package piotro15.symbiont.common.blocks.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;
import piotro15.symbiont.api.FluidApi;
import piotro15.symbiont.common.registries.ModBlockEntities;

public class MachineWorkerBlockEntity extends BlockEntity {
    public MachineWorkerBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.MACHINE_WORKER.get(), pos, blockState);
    }

    @Nullable
    public IItemHandler getItemHandlerForSide(@Nullable Direction side) {
        if (level.getBlockEntity(this.getBlockPos().below()) instanceof BioreactorBlockEntity e) {
            return e.getItemHandlerForSide(side);
        }

        return null;
    }

    @Nullable
    public IFluidHandler getFluidHandlerForSide(@Nullable Direction side) {
        if (level.getBlockEntity(this.getBlockPos().below()) instanceof BioreactorBlockEntity e) {
            return e.getFluidHandlerForSide(side);
        }

        return null;
    }

    @Nullable
    public IEnergyStorage getEnergyStorageForSide(@Nullable Direction side) {
        if (level.getBlockEntity(this.getBlockPos().below()) instanceof BioreactorBlockEntity e) {
            return e.getEnergyStorage();
        }

        return null;
    }
}
