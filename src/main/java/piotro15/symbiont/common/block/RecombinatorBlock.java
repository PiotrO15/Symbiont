package piotro15.symbiont.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import piotro15.symbiont.common.block.entity.RecombinatorBlockEntity;

public class RecombinatorBlock extends Block implements EntityBlock {
    public RecombinatorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof MenuProvider menuProvider) {
                player.openMenu(menuProvider, pos);
                player.sendSystemMessage(Component.literal("Opened Cell Editor"));
            } else {
                player.sendSystemMessage(Component.literal("No menu provider"));
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new RecombinatorBlockEntity(blockPos, blockState);
    }
}
