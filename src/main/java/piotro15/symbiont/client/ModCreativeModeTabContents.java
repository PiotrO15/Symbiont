package piotro15.symbiont.client;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import piotro15.symbiont.common.genetics.Biotrait;
import piotro15.symbiont.common.genetics.CellType;
import piotro15.symbiont.common.registry.ModCreativeModeTabs;
import piotro15.symbiont.common.registry.ModDataComponents;
import piotro15.symbiont.common.registry.ModItems;
import piotro15.symbiont.common.registry.ModRegistries;

public class ModCreativeModeTabContents {
    @SubscribeEvent
    public static void buildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == ModCreativeModeTabs.COMMON.getKey()) {
            event.accept(ModItems.BIOFORMER);
            event.accept(ModItems.METABOLIZER);
            event.accept(ModItems.BIOREACTOR);
            event.accept(ModItems.CENTRIFUGE);
            event.accept(ModItems.RECOMBINATOR);
            event.accept(ModItems.ORGANIC_BINDER);
            event.accept(ModItems.BIOPLASTIC_SHEET);
            event.accept(ModItems.BONE_SAMPLER);

            SymbiontClient.fluidTypeExtensions.forEach(((fluid, extension) -> event.accept(fluid.get().getBucket())));
        }
        if (event.getTabKey() == ModCreativeModeTabs.CELLS.getKey()) {
            if (Minecraft.getInstance().level != null) {
                Registry<CellType> blendTypeRegistry = Minecraft.getInstance().level.registryAccess().registryOrThrow(ModRegistries.CELL_TYPE);

                for (ResourceKey<CellType> blendKey : blendTypeRegistry.registryKeySet()) {
                    ItemStack stack = new ItemStack(ModItems.CELL_CULTURE.get());
                    stack.set(ModDataComponents.CELL_TYPE.get(), blendKey.location());
                    event.accept(stack);
                }

                Registry<Biotrait> biotraitRegistry = Minecraft.getInstance().level.registryAccess().registryOrThrow(ModRegistries.BIOTRAIT);
                for (ResourceKey<Biotrait> traitKey : biotraitRegistry.registryKeySet()) {
                    ItemStack stack = new ItemStack(ModItems.BIOTRAIT_EXTRACT.get());
                    stack.set(ModDataComponents.BIOTRAIT.get(), traitKey.location());
                    event.accept(stack);
                }
            }
        }
    }
}
