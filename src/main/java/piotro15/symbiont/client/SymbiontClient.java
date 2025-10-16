package piotro15.symbiont.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.jetbrains.annotations.NotNull;
import piotro15.symbiont.client.screen.BioformerScreen;
import piotro15.symbiont.client.screen.BioreactorScreen;
import piotro15.symbiont.client.screen.MetabolizerScreen;
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.client.screen.RecombinatorScreen;
import piotro15.symbiont.common.genetics.CellType;
import piotro15.symbiont.common.registry.*;

import java.util.Map;

@EventBusSubscriber(modid = Symbiont.MOD_ID, value = Dist.CLIENT)
public class SymbiontClient {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {

    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.BIOREACTOR.get(), BioreactorScreen::new);
        event.register(ModMenuTypes.RECOMBINATOR.get(), RecombinatorScreen::new);
        event.register(ModMenuTypes.METABOLIZER.get(), MetabolizerScreen::new);
        event.register(ModMenuTypes.BIOFORMER.get(), BioformerScreen::new);
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

    @SubscribeEvent
    public static void registerModels(ModelEvent.RegisterAdditional event) {
        for (Map.Entry<ResourceLocation, Resource> entry : FileToIdConverter.json("models/cell_type").listMatchingResources(Minecraft.getInstance().getResourceManager()).entrySet()) {
            ResourceLocation cellType = ResourceLocation.parse(entry.getKey().toString().replace("models/cell_type", "cell_type").replace(".json", ""));
            event.register(ModelResourceLocation.standalone(cellType));
        }
    }

    @SubscribeEvent
    public static void modifyBakingResults(ModelEvent.ModifyBakingResult event) {
        event.getModels().computeIfPresent(
                ModelResourceLocation.inventory(ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, "cell_culture")),
                (location, model) -> new CellCultureModelWrapper(model)
        );
    }

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerFluidType(createFluidType(Symbiont.id("block/thick_fluid_still"), Symbiont.id("block/thick_fluid_flow"), 0xffff8fab), ModFluidTypes.NUTRITIONAL_PASTE);
        event.registerFluidType(createFluidType(Symbiont.id("block/thick_fluid_still"), Symbiont.id("block/thick_fluid_flow"), 0xFFB565A7), ModFluidTypes.SWEET_PASTE);
    }

    private static IClientFluidTypeExtensions createFluidType(ResourceLocation stillTexture, ResourceLocation flowingTexture, int color) {
        return new IClientFluidTypeExtensions() {
            @Override
            public int getTintColor() {
                return color;
            }

            @Override
            public @NotNull ResourceLocation getStillTexture() {
                return stillTexture;
            }

            @Override
            public @NotNull ResourceLocation getFlowingTexture() {
                return flowingTexture;
            }
        };
    }
}
