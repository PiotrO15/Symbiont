package piotro15.symbiont.client.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.common.recipe.BioreactorRecipe;
import piotro15.symbiont.common.registry.ModItems;

public class BioreactorRecipeCategory extends AbstractRecipeCategory<BioreactorRecipe> {
    private final IDrawableAnimated arrow;
    private final IDrawableStatic background;
    private final IDrawableStatic tankOverlay;

    public BioreactorRecipeCategory(IGuiHelper helper) {
        super(
                ModJeiRecipeTypes.BIOREACTOR,
                Component.translatable("container.bioreactor"),
                helper.createDrawableItemLike(ModItems.BIOREACTOR.get()),
                135,
                54
        );

        arrow = helper.createAnimatedRecipeArrow(120);
        background = helper.createDrawable(ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, "textures/gui/bioreactor.png"), 34, 16, 135, 54);
        tankOverlay = helper.drawableBuilder(ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, "textures/gui/fluid_tank_overlay.png"), 0, 0, 16, 52).setTextureSize(16, 52).build();
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BioreactorRecipe recipe, @NotNull IFocusGroup focuses) {
        IRecipeSlotBuilder fluidInputSlot = builder.addInputSlot(1, 1).setFluidRenderer(1, false, 16, 52).setOverlay(tankOverlay, 0, 0);
        for (FluidStack fluid : recipe.fluidInput().getFluids()) {
            fluidInputSlot.addFluidStack(fluid.getFluid(), fluid.getAmount());
        }

        IRecipeSlotBuilder input = builder.addInputSlot(29, 19);
        input.addIngredients(recipe.input());

        if (recipe.output() != null) {
            builder.addOutputSlot(87, 19).addIngredient(VanillaTypes.ITEM_STACK, recipe.output());
        } else {
            builder.addOutputSlot(87, 19);
        }

        builder.addOutputSlot(118, 1).addFluidStack(recipe.fluidOutput().getFluid(), recipe.fluidOutput().getAmount()).setFluidRenderer(1, false, 16, 52).setOverlay(tankOverlay, 0, 0);
    }

    @Override
    public void draw(@NotNull BioreactorRecipe recipe, @NotNull IRecipeSlotsView slots, @NotNull GuiGraphics gfx, double mouseX, double mouseY) {
        background.draw(gfx);
        arrow.draw(gfx,53, 18);
    }
}
