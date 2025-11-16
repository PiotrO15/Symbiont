package piotro15.symbiont.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;
import piotro15.symbiont.client.screen.*;
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.common.genetics.Biotrait;
import piotro15.symbiont.common.genetics.CellType;
import piotro15.symbiont.common.registry.*;

import java.util.Map;

@Mod(value = Symbiont.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Symbiont.MOD_ID, value = Dist.CLIENT)
public class SymbiontClient {
    public static final Map<DeferredHolder<Fluid, FlowingFluid>, IClientFluidTypeExtensions> fluidTypeExtensions = Map.ofEntries(
            Map.entry(ModFluids.NUTRITIONAL_PASTE, createFluidType(Symbiont.id("block/thick_fluid_still"), Symbiont.id("block/thick_fluid_flow"), 0xffff8fab)),
            Map.entry(ModFluids.SWEET_PASTE, createFluidType(Symbiont.id("block/thick_fluid_still"), Symbiont.id("block/thick_fluid_flow"), 0xFFA1C5FF)),
            Map.entry(ModFluids.PROTEIN_PASTE, createFluidType(Symbiont.id("block/thick_fluid_still"), Symbiont.id("block/thick_fluid_flow"), 0xFFB565A7)),
            Map.entry(ModFluids.MYOGENIC_BIOMASS, createFluidType(Symbiont.id("block/thick_fluid_still"), Symbiont.id("block/thick_fluid_flow"), 0xFFc96363)),
            Map.entry(ModFluids.STICKY_PASTE, createFluidType(Symbiont.id("block/thick_fluid_still"), Symbiont.id("block/thick_fluid_flow"), 0xFFD2B48C)),
            Map.entry(ModFluids.BIOPOLYMER_SOLUTION, createFluidType(Symbiont.id("block/thick_fluid_still"), Symbiont.id("block/thick_fluid_flow"), 0xFF7FFFD4)),
            Map.entry(ModFluids.CUPRIC_PASTE, createFluidType(Symbiont.id("block/thick_fluid_still"), Symbiont.id("block/thick_fluid_flow"), 0xFFDA7B5C)),
            Map.entry(ModFluids.FERRIC_PASTE, createFluidType(Symbiont.id("block/thick_fluid_still"), Symbiont.id("block/thick_fluid_flow"), 0xFF8B0000)),
            Map.entry(ModFluids.ENRICHED_CUPRIC_SOLUTION, createFluidType(Symbiont.id("block/thick_fluid_still"), Symbiont.id("block/thick_fluid_flow"), 0xFFFF7F50)),
            Map.entry(ModFluids.FERRIC_SOLUTION, createFluidType(Symbiont.id("block/thick_fluid_still"), Symbiont.id("block/thick_fluid_flow"), 0xFFB7410E)),
            Map.entry(ModFluids.CUPRIC_SOLUTION, createFluidType(Symbiont.id("block/thick_fluid_still"), Symbiont.id("block/thick_fluid_flow"), 0xFFCE702B)),
            Map.entry(ModFluids.AURIC_SOLUTION, createFluidType(Symbiont.id("block/thick_fluid_still"), Symbiont.id("block/thick_fluid_flow"), 0xFFFFD700)),
            Map.entry(ModFluids.MARINE_EXTRACT, createFluidType(Symbiont.id("block/thick_fluid_still"), Symbiont.id("block/thick_fluid_flow"), 0xFF298860))
    );

    public SymbiontClient(IEventBus modEventBus) {
        modEventBus.addListener(ModCreativeModeTabContents::buildContents);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {

    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.BIOREACTOR.get(), BioreactorScreen::new);
        event.register(ModMenuTypes.RECOMBINATOR.get(), RecombinatorScreen::new);
        event.register(ModMenuTypes.METABOLIZER.get(), MetabolizerScreen::new);
        event.register(ModMenuTypes.CENTRIFUGE.get(), CentrifugeScreen::new);
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

        event.register((stack, tintIndex) -> {
            ResourceLocation traitId = stack.getComponents().get(ModDataComponents.BIOTRAIT.get());
            if (traitId == null)
                return -1;

            if (Minecraft.getInstance().getConnection() == null)
                return -1;

            Registry<Biotrait> registry = Minecraft.getInstance().getConnection()
                    .registryAccess().registryOrThrow(ModRegistries.BIOTRAIT);

            Biotrait biotrait = registry.get(traitId);
            if (biotrait == null)
                return -1;

            int color = brightenColor(biotrait.type().getColor(), 1.5f);
            return (color & 0xFF000000) == 0 ? color | 0xFF000000 : color;
        }, ModItems.BIOTRAIT_EXTRACT.get());

        fluidTypeExtensions.forEach((fluid, extension) -> event.register((stack, tintIndex) -> tintIndex == 1 ? extension.getTintColor() : -1, fluid.get().getBucket()));
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
        fluidTypeExtensions.forEach((fluid, extension) -> event.registerFluidType(extension, fluid.get().getFluidType()));
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

    private static int brightenColor(int color, float factor) {
        int a = (color >>> 24) & 0xFF;
        int r = (color >>> 16) & 0xFF;
        int g = (color >>> 8) & 0xFF;
        int b = color & 0xFF;

        r = Math.min(255, (int)(r * factor));
        g = Math.min(255, (int)(g * factor));
        b = Math.min(255, (int)(b * factor));

        if (a == 0) a = 0xFF;
        return (a << 24) | (r << 16) | (g << 8) | b;
    }
}
