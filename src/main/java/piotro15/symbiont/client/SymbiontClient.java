package piotro15.symbiont.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import piotro15.symbiont.client.screens.BioreactorScreen;
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.client.screens.CellEditorScreen;
import piotro15.symbiont.common.registries.ModMenuTypes;

@EventBusSubscriber(modid = Symbiont.MOD_ID, value = Dist.CLIENT)
public class SymbiontClient {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {

    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.BIOREACTOR.get(), BioreactorScreen::new);
        event.register(ModMenuTypes.CELL_EDITOR.get(), CellEditorScreen::new);
    }
}
