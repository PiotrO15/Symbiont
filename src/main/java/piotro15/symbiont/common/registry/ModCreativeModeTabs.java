package piotro15.symbiont.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import piotro15.symbiont.common.Symbiont;

public class ModCreativeModeTabs {
    public static DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Symbiont.MOD_ID);

    public static DeferredHolder<CreativeModeTab, CreativeModeTab> COMMON = CREATIVE_TABS.register("common", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.symbiont.common")).icon(() -> new ItemStack(ModItems.BIOREACTOR.get())).build());
    public static DeferredHolder<CreativeModeTab, CreativeModeTab> CELLS = CREATIVE_TABS.register("cells", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.symbiont.cells")).icon(() -> new ItemStack(ModItems.CELL_CULTURE.get())).build());
}
