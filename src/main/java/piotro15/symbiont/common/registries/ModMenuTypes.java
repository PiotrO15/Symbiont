package piotro15.symbiont.common.registries;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import piotro15.symbiont.common.blocks.entities.BioreactorBlockEntity;
import piotro15.symbiont.common.blocks.entities.MetabolizerBlockEntity;
import piotro15.symbiont.common.menus.BioreactorMenu;
import piotro15.symbiont.common.menus.CellEditorMenu;
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.common.blocks.entities.CellEditorBlockEntity;
import piotro15.symbiont.common.menus.MetabolizerMenu;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, Symbiont.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<BioreactorMenu>> BIOREACTOR = MENU_TYPES.register("bioreactor",
            () -> IMenuTypeExtension.create((id, inv, buf) ->
            {
                BlockPos pos = buf.readBlockPos();
                BlockEntity be = inv.player.level().getBlockEntity(pos);
                if (be instanceof BioreactorBlockEntity ceb)
                    return new BioreactorMenu(id, inv, ceb, new SimpleContainerData(4));
                return null;
            }));

    public static final DeferredHolder<MenuType<?>, MenuType<MetabolizerMenu>> METABOLIZER = MENU_TYPES.register("metabolizer",
            () -> IMenuTypeExtension.create((id, inv, buf) ->
            {
                BlockPos pos = buf.readBlockPos();
                BlockEntity be = inv.player.level().getBlockEntity(pos);
                if (be instanceof MetabolizerBlockEntity ceb)
                    return new MetabolizerMenu(id, inv, ceb, new SimpleContainerData(4));
                return null;
            }));

    public static final DeferredHolder<MenuType<?>, MenuType<CellEditorMenu>> CELL_EDITOR = MENU_TYPES.register("cell_editor",
            () -> IMenuTypeExtension.create((id, inv, buf) ->
            {
                BlockPos pos = buf.readBlockPos();
                BlockEntity be = inv.player.level().getBlockEntity(pos);
                if (be instanceof CellEditorBlockEntity ceb)
                    return new CellEditorMenu(id, inv, pos);
                return null;
            }));
}
