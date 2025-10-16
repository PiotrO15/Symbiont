package piotro15.symbiont.client.compat;

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
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.common.recipe.MetabolizerRecipe;
import piotro15.symbiont.common.registry.ModItems;

public class MetabolizerRecipeCategory extends AbstractRecipeCategory<MetabolizerRecipe> {
    private final IDrawableAnimated arrow;
    private final IDrawableStatic background;
    private final IDrawableStatic tankOverlay;

    public MetabolizerRecipeCategory(IGuiHelper helper) {
        super(
                ModJeiRecipeTypes.METABOLIZER,
                Component.translatable("container.metabolizer"),
                helper.createDrawableItemLike(ModItems.METABOLIZER.get()),
                135,
                54
        );

        arrow = helper.createAnimatedRecipeArrow(120);
        background = helper.createDrawable(Symbiont.id("textures/gui/metabolizer.png"), 34, 16, 135, 54);
        tankOverlay = helper.drawableBuilder(Symbiont.id("textures/gui/fluid_tank_overlay.png"), 0, 0, 16, 52).setTextureSize(16, 52).build();
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, MetabolizerRecipe recipe, @NotNull IFocusGroup focuses) {
        IRecipeSlotBuilder fluidInputSlot = builder.addInputSlot(1, 1).setFluidRenderer(1, false, 16, 52).setOverlay(tankOverlay, 0, 0);
        for (FluidStack fluid : recipe.fluidInput().getFluids()) {
            fluidInputSlot.addFluidStack(fluid.getFluid(), fluid.getAmount());
        }

        IRecipeSlotBuilder mainInput = builder.addInputSlot(29, 33);
        mainInput.addIngredients(recipe.ingredients().getFirst());

        for (int i = 1; i < 5; i++) {
            int x = 15 + (i * 18);
            IRecipeSlotBuilder sideInput = builder.addInputSlot(x, 1);

            if (recipe.ingredients().size() > i) {
                sideInput.addIngredients(recipe.ingredients().get(i));
            }
        }

        IRecipeSlotBuilder outputSlot = builder.addOutputSlot(87, 33);
        outputSlot.addItemStack(recipe.output());

        builder.addOutputSlot(118, 1).addFluidStack(recipe.fluidOutput().getFluid(), recipe.fluidOutput().getAmount()).setFluidRenderer(1, false, 16, 52).setOverlay(tankOverlay, 0, 0);
    }

    @Override
    public void draw(@NotNull MetabolizerRecipe recipe, @NotNull IRecipeSlotsView slots, @NotNull GuiGraphics gfx, double mouseX, double mouseY) {
        background.draw(gfx);
        arrow.draw(gfx,53, 32);
    }
}
