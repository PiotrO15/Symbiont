package piotro15.symbiont.common.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.crafting.IngredientType;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.common.genetics.Biotrait;
import piotro15.symbiont.common.genetics.CellType;
import piotro15.symbiont.util.SizedIngredient;

public class ModRegistries {
    public static final ResourceKey<Registry<CellType>> CELL_TYPE =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, "cell_type"));

    public static final ResourceKey<Registry<Biotrait>> BIOTRAIT =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, "biotrait"));

    public static DeferredRegister<IngredientType<?>> INGREDIENT_TYPES = DeferredRegister.create(NeoForgeRegistries.INGREDIENT_TYPES, Symbiont.MOD_ID);
    public static DeferredHolder<IngredientType<?>, IngredientType<SizedIngredient>> SIZED_INGREDIENT = INGREDIENT_TYPES.register("sized", () -> new IngredientType<>(SizedIngredient.CODEC));

    @SubscribeEvent
    public static void registerDataRegistry(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(ModRegistries.CELL_TYPE, CellType.CODEC, CellType.CODEC);
        event.dataPackRegistry(ModRegistries.BIOTRAIT, Biotrait.CODEC, Biotrait.CODEC);
    }
}
