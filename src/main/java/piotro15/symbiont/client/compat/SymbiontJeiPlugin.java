package piotro15.symbiont.client.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.common.registry.ModDataComponents;
import piotro15.symbiont.common.registry.ModItems;
import piotro15.symbiont.common.registry.ModRecipeTypes;

@JeiPlugin
@SuppressWarnings("unused")
public class SymbiontJeiPlugin implements IModPlugin {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, "jei_plugin");

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(ModItems.CELL_CULTURE.get(), new ISubtypeInterpreter<>() {
            @Override
            public @Nullable Object getSubtypeData(@NotNull ItemStack ingredient, @NotNull UidContext context) {
                return ingredient.get(ModDataComponents.CELL_TYPE.get());
            }

            @Override
            public @NotNull String getLegacyStringSubtypeInfo(@NotNull ItemStack ingredient, @NotNull UidContext context) {
                ResourceLocation blendId = ingredient.get(ModDataComponents.CELL_TYPE.get());

                if (blendId != null) {
                    return blendId.toString();
                }
                return "";
            }
        });
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IJeiHelpers helpers = registration.getJeiHelpers();
        IGuiHelper guiHelper = helpers.getGuiHelper();

        registration.addRecipeCategories(new BioreactorRecipeCategory(guiHelper));
        registration.addRecipeCategories(new MetabolizerRecipeCategory(guiHelper));
        registration.addRecipeCategories(new BioformerRecipeCategory(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(ModJeiRecipeTypes.BIOREACTOR, Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.BIOREACTOR.get()).stream().map(RecipeHolder::value).toList());
        registration.addRecipes(ModJeiRecipeTypes.METABOLIZER, Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.METABOLIZER.get()).stream().map(RecipeHolder::value).toList());
        registration.addRecipes(ModJeiRecipeTypes.BIOFORMER, Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.BIOFORMER.get()).stream().map(RecipeHolder::value).toList());
    }
}
