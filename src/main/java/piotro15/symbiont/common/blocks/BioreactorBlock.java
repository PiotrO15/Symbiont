package piotro15.symbiont.common.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import piotro15.symbiont.common.blocks.entities.BioreactorBlockEntity;
import piotro15.symbiont.common.registries.ModBlockEntities;

public class BioreactorBlock extends BaseEntityBlock {
    public static final MapCodec<BioreactorBlock> CODEC = simpleCodec(BioreactorBlock::new);
//    public static final EnumProperty<DoubleBlockHalf> HALF = null;

    public BioreactorBlock(Properties properties) {
        super(properties);
//        this.registerDefaultState(this.stateDefinition.any().setValue(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

//    @Nullable
//    public BlockState getStateForPlacement(BlockPlaceContext context) {
//        BlockPos blockpos = context.getClickedPos();
//        Level level = context.getLevel();
//        return blockpos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(blockpos.above()).canBeReplaced(context) ? super.getStateForPlacement(context) : null;
//    }
//
//    public static void placeAt(LevelAccessor level, BlockState state, BlockPos pos, int flags) {
//        BlockPos blockpos = pos.above();
//        level.setBlock(pos, state.setValue(HALF, DoubleBlockHalf.LOWER), flags);
//        level.setBlock(blockpos, state.setValue(HALF, DoubleBlockHalf.UPPER), flags);
//    }

    @Override
    public @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hitResult) {
        if (!level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof MenuProvider menuProvider) {
                player.openMenu(menuProvider, pos);
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new BioreactorBlockEntity(blockPos, blockState);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState state, @NotNull Level level, @NotNull BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);

            if (blockEntity instanceof BioreactorBlockEntity bioreactorBlockEntity) {
                // Drop items
            }
        }

        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
        return level.isClientSide ? null : createTickerHelper(blockEntityType, ModBlockEntities.BIOREACTOR.get(),
                (level1, blockPos, blockState, blockEntity) -> blockEntity.tick(level1, blockPos, blockState, blockEntity));
    }
}
