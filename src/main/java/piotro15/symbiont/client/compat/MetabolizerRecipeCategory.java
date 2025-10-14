package piotro15.symbiont.client.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
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
        background = helper.createDrawable(ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, "textures/gui/metabolizer.png"), 34, 16, 135, 54);
        tankOverlay = helper.drawableBuilder(ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, "textures/gui/fluid_tank_overlay.png"), 0, 0, 16, 52).setTextureSize(16, 52).build();
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, MetabolizerRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.addInputSlot(1, 1).addFluidStack(recipe.fluidInput().getStacks()[0].getFluid(), recipe.fluidInput().getStacks()[0].getAmount()).setFluidRenderer(1, false, 16, 52).setOverlay(tankOverlay, 0, 0);

        if (recipe.ingredients() != null && !recipe.ingredients().isEmpty() && recipe.ingredients().getFirst().getItems()[0] != null) {
            builder.addInputSlot(29, 33).addIngredient(VanillaTypes.ITEM_STACK, recipe.ingredients().getFirst().getItems()[0]);
        } else {
            builder.addInputSlot(29, 33);
        }

        for (int i = 0; i < 4; i++) {
            ItemStack stack = (recipe.ingredients() != null && recipe.ingredients().size() > i + 1) ? recipe.ingredients().get(i + 1).getItems()[0] : null;
            int x = 33 + (i * 18);

            if (stack != null) {
                builder.addOutputSlot(x, 1).addItemStack(stack);
            } else {
                builder.addOutputSlot(x, 1);
            }
        }

        if (recipe.output() != null) {
            builder.addInputSlot(87, 33).addIngredient(VanillaTypes.ITEM_STACK, recipe.output());
        } else {
            builder.addInputSlot(87, 33);
        }

        builder.addInputSlot(118, 1).addFluidStack(recipe.fluidOutput().getFluid(), recipe.fluidOutput().getAmount()).setFluidRenderer(1, false, 16, 52).setOverlay(tankOverlay, 0, 0);
    }

    @Override
    public void draw(@NotNull MetabolizerRecipe recipe, @NotNull IRecipeSlotsView slots, @NotNull GuiGraphics gfx, double mouseX, double mouseY) {
        background.draw(gfx);
        arrow.draw(gfx,53, 32);
    }
}
