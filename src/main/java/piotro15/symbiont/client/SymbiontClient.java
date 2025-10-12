package piotro15.symbiont.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import piotro15.symbiont.client.screens.BioreactorScreen;
import piotro15.symbiont.client.screens.MetabolizerScreen;
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.client.screens.CellEditorScreen;
import piotro15.symbiont.common.genetics.CellType;
import piotro15.symbiont.common.registries.ModDataComponents;
import piotro15.symbiont.common.registries.ModItems;
import piotro15.symbiont.common.registries.ModMenuTypes;
import piotro15.symbiont.common.registries.ModRegistries;

@EventBusSubscriber(modid = Symbiont.MOD_ID, value = Dist.CLIENT)
public class SymbiontClient {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {

    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.BIOREACTOR.get(), BioreactorScreen::new);
        event.register(ModMenuTypes.CELL_EDITOR.get(), CellEditorScreen::new);
        event.register(ModMenuTypes.METABOLIZER.get(), MetabolizerScreen::new);
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> {
            ResourceLocation cellId = stack.getComponents().get(ModDataComponents.CELL_TYPE.get());
            if (cellId == null)
                return -1;

            if (Minecraft.getInstance().getConnection() == null)
                return -1;

            Registry<CellType> registry = Minecraft.getInstance().getConnection()
                    .registryAccess().registryOrThrow(ModRegistries.CELL_TYPE);

            CellType cellType = registry.get(cellId);
            if (cellType == null)
                return -1;

            int color = cellType.color();
            return (color & 0xFF000000) == 0 ? color | 0xFF000000 : color;
        }, ModItems.CELL_CULTURE.get());
    }
}
