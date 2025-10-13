package piotro15.symbiont.common.blocks.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BasicMachineBlockEntity extends BlockEntity implements BlockEntityTicker<BasicMachineBlockEntity> {
    public int progress;
    protected ContainerData data;

    public BasicMachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    public void tick(Level level, @NotNull BlockPos blockPos, @NotNull BlockState blockState, @NotNull BasicMachineBlockEntity machineBlockEntity) {
        if (level.isClientSide()) {
            clientTick(level, blockPos, blockState, machineBlockEntity);
            return;
        }

        serverTick(level, blockPos, blockState, machineBlockEntity);
    }

    public abstract void serverTick(Level level, BlockPos pos, BlockState state, BasicMachineBlockEntity blockEntity);

    public void clientTick(Level level, BlockPos pos, BlockState state, BasicMachineBlockEntity blockEntity) {}

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider pRegistries) {
        return saveWithoutMetadata(pRegistries);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
