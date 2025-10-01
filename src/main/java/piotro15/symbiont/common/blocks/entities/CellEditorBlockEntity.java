package piotro15.symbiont.common.blocks.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import piotro15.symbiont.common.menus.CellEditorMenu;
import piotro15.symbiont.common.registries.ModBlockEntities;

public class CellEditorBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler items = new ItemStackHandler(6); // 1 cell + 5 strains

    public CellEditorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CELL_EDITOR.get(), pos, state);
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInv, Player player) {
        return new CellEditorMenu(id, playerInv, this.getBlockPos());
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("container.cell_editor");
    }

    public ItemStackHandler getItems() {
        return items;
    }
}

