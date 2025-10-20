package piotro15.symbiont.common.registry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import piotro15.symbiont.common.block.entity.*;
import piotro15.symbiont.common.menu.*;
import piotro15.symbiont.common.Symbiont;

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

    public static final DeferredHolder<MenuType<?>, MenuType<BioformerMenu>> BIOFORMER = MENU_TYPES.register("bioformer",
            () -> IMenuTypeExtension.create((id, inv, buf) ->
            {
                BlockPos pos = buf.readBlockPos();
                BlockEntity be = inv.player.level().getBlockEntity(pos);
                if (be instanceof BioformerBlockEntity ceb)
                    return new BioformerMenu(id, inv, ceb, new SimpleContainerData(4));
                return null;
            }));

    public static final DeferredHolder<MenuType<?>, MenuType<CentrifugeMenu>> CENTRIFUGE = MENU_TYPES.register("centrifuge",
            () -> IMenuTypeExtension.create((id, inv, buf) ->
            {
                BlockPos pos = buf.readBlockPos();
                BlockEntity be = inv.player.level().getBlockEntity(pos);
                if (be instanceof CentrifugeBlockEntity ceb)
                    return new CentrifugeMenu(id, inv, ceb, new SimpleContainerData(4));
                return null;
            }));

    public static final DeferredHolder<MenuType<?>, MenuType<RecombinatorMenu>> RECOMBINATOR = MENU_TYPES.register("recombinator",
            () -> IMenuTypeExtension.create((id, inv, buf) ->
            {
                BlockPos pos = buf.readBlockPos();
                BlockEntity be = inv.player.level().getBlockEntity(pos);
                if (be instanceof RecombinatorBlockEntity)
                    return new RecombinatorMenu(id, inv, pos);
                return null;
            }));
}
