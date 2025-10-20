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
import piotro15.symbiont.common.registry.ModRegistries;
import piotro15.symbiont.datagen.providers.*;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Symbiont.MOD_ID)
public class ModDataGenerators {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator dataGenerator = event.getGenerator();
        PackOutput packOutput = dataGenerator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        dataGenerator.addProvider(event.includeClient(), new RecipeProvider(packOutput, lookupProvider));

        dataGenerator.addProvider(event.includeClient(), (DataProvider.Factory<DatapackBuiltinEntriesProvider>) output -> new DatapackBuiltinEntriesProvider(
                output, lookupProvider, new RegistrySetBuilder().add(ModRegistries.CELL_TYPE, CellTypeProvider::registerCellTypes).add(ModRegistries.BIOTRAIT, BiotraitProvider::registerBiotraits), Collections.singleton(Symbiont.MOD_ID))
        );

        dataGenerator.addProvider(event.includeClient(), new LanguageProvider(packOutput, Symbiont.MOD_ID, "en_us"));

        dataGenerator.addProvider(event.includeClient(), new ItemModelProvider(packOutput, Symbiont.MOD_ID, event.getExistingFileHelper()));

        ModBlockTagsProvider blockTagsProvider = dataGenerator.addProvider(true, new ModBlockTagsProvider(packOutput, lookupProvider, event.getExistingFileHelper()));
        dataGenerator.addProvider(event.includeServer(), new ModItemTagsProvider(packOutput, lookupProvider, event.getExistingFileHelper(), blockTagsProvider.contentsGetter()));
    }
}