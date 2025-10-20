package piotro15.symbiont.common;

import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;
import piotro15.symbiont.common.block.entity.BasicMachineBlockEntity;
import piotro15.symbiont.common.block.entity.MachineWorkerBlockEntity;
import piotro15.symbiont.common.config.Config;
import piotro15.symbiont.common.genetics.TraitModifierRegistry;
import piotro15.symbiont.common.registry.*;

import java.util.List;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Symbiont.MOD_ID)
public class Symbiont {
    public static final String MOD_ID = "symbiont";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Symbiont(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(ModRegistries::registerDataRegistry);

        ModDataComponents.REGISTRAR.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModFluidTypes.FLUID_TYPES.register(modEventBus);
        ModFluids.FLUIDS.register(modEventBus);
        ModMenuTypes.MENU_TYPES.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITY_TYPE.register(modEventBus);
        ModRecipeTypes.RECIPE_TYPES.register(modEventBus);
        ModRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
        modEventBus.addListener(this::registerCapabilities);
        TraitModifierRegistry.registerConditions();
        ModCreativeModeTabs.CREATIVE_TABS.register(modEventBus);
        modEventBus.addListener(ModItems::buildContents);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (Plasmid) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, path);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock) LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    public void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ModBlockEntities.MACHINE_WORKER.get(),
                (MachineWorkerBlockEntity::getItemHandlerForSide)
        );

        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK,
                ModBlockEntities.MACHINE_WORKER.get(),
                (MachineWorkerBlockEntity::getFluidHandlerForSide)
        );


        event.registerBlockEntity(
                Capabilities.EnergyStorage.BLOCK,
                ModBlockEntities.MACHINE_WORKER.get(),
                (MachineWorkerBlockEntity::getEnergyStorageForSide)
        );

        List<BlockEntityType<?>> registeredBlockEntities = List.of(
                ModBlockEntities.BIOREACTOR.get(),
                ModBlockEntities.METABOLIZER.get(),
                ModBlockEntities.BIOFORMER.get(),
                ModBlockEntities.CENTRIFUGE.get()
        );

        for (BlockEntityType<?> type : registeredBlockEntities) {
            event.registerBlockEntity(
                    Capabilities.ItemHandler.BLOCK,
                    type,
                    ((be, direction) -> ((BasicMachineBlockEntity) be).getItemHandlerForSide(direction))
            );
            event.registerBlockEntity(
                    Capabilities.FluidHandler.BLOCK,
                    type,
                    ((be, direction) -> ((BasicMachineBlockEntity) be).getFluidHandlerForSide(direction))
            );
            event.registerBlockEntity(
                    Capabilities.EnergyStorage.BLOCK,
                    type,
                    ((be, direction) -> ((BasicMachineBlockEntity) be).getEnergyStorageForSide(direction))
            );
        }
    }
}
