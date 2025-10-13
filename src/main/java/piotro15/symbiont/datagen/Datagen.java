package piotro15.symbiont.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.common.registries.ModRegistries;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Symbiont.MOD_ID)
public class Datagen {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator dataGenerator = event.getGenerator();
        PackOutput packOutput = dataGenerator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        dataGenerator.addProvider(event.includeClient(), new RecipeDatagen(packOutput, lookupProvider));

        dataGenerator.addProvider(event.includeClient(), (DataProvider.Factory<DatapackBuiltinEntriesProvider>) output -> new DatapackBuiltinEntriesProvider(
                output, lookupProvider, new RegistrySetBuilder().add(ModRegistries.CELL_TYPE, CellTypeProvider::registerCellTypes).add(ModRegistries.BIOTRAIT, BiotraitProvider::registerBiotraits), Collections.singleton(Symbiont.MOD_ID))
        );

        dataGenerator.addProvider(event.includeClient(), new LanguageDatagen(packOutput, Symbiont.MOD_ID, "en_us"));
    }
}