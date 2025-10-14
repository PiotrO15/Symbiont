package piotro15.symbiont.client.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.common.recipe.BioformerRecipe;
import piotro15.symbiont.common.registry.ModItems;

public class BioformerRecipeCategory extends AbstractRecipeCategory<BioformerRecipe> {
    private final IDrawableAnimated arrow;
    private final IDrawableStatic background;
    private final IDrawableStatic tankOverlay;

    public BioformerRecipeCategory(IGuiHelper helper) {
        super(
                ModJeiRecipeTypes.BIOFORMER,
                Component.translatable("container.bioformer"),
                helper.createDrawableItemLike(ModItems.BIOFORMER.get()),
                126,
                54
        );

        arrow = helper.createAnimatedRecipeArrow(120);
        background = helper.createDrawable(ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, "textures/gui/bioformer.png"), 34, 16, 126, 54);
        tankOverlay = helper.drawableBuilder(ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, "textures/gui/fluid_tank_overlay.png"), 0, 0, 16, 52).setTextureSize(16, 52).build();
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BioformerRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.addInputSlot(1, 1).addFluidStack(recipe.fluidInput().getStacks()[0].getFluid(), recipe.fluidInput().getStacks()[0].getAmount()).setFluidRenderer(1, false, 16, 52).setOverlay(tankOverlay, 0, 0);

        if (recipe.itemInput() != null && recipe.itemInput().getItems().length > 0 && recipe.itemInput().getItems()[0] != null) {
            builder.addInputSlot(28, 10).addIngredient(VanillaTypes.ITEM_STACK, recipe.itemInput().getItems()[0]);
        } else {
            builder.addInputSlot(28, 10);
        }

        if (recipe.catalyst() != null && recipe.catalyst().getItems().length > 0 && recipe.catalyst().getItems()[0] != null) {
            builder.addSlot(RecipeIngredientRole.CATALYST, 28, 28).addIngredient(VanillaTypes.ITEM_STACK, recipe.catalyst().getItems()[0]);
        } else {
            builder.addSlot(RecipeIngredientRole.CATALYST, 28, 28);
        }

        for (int i = 0; i < 4; i++) {
            ItemStack stack = (recipe.output() != null && recipe.output().size() > i) ? recipe.output().get(i) : null;
            int x = (i % 2 == 0) ? 91 : 109;
            int y = (i < 2) ? 10 : 28;

            if (stack != null) {
                builder.addOutputSlot(x, y).addItemStack(stack);
            } else {
                builder.addOutputSlot(x, y);
            }
        }
    }

    @Override
    public void draw(@NotNull BioformerRecipe recipe, @NotNull IRecipeSlotsView slots, @NotNull GuiGraphics gfx, double mouseX, double mouseY) {
        background.draw(gfx);
        arrow.draw(gfx,56, 19);
    }
}
