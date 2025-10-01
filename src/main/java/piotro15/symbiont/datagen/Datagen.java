package piotro15.symbiont.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import piotro15.symbiont.common.Symbiont;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Symbiont.MOD_ID)
public class Datagen {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator dataGenerator = event.getGenerator();
        PackOutput packOutput = dataGenerator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        dataGenerator.addProvider(event.includeClient(), new RecipeDatagen(packOutput, lookupProvider));
    }
}
