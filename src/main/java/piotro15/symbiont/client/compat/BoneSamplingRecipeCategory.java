package piotro15.symbiont.client.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.common.recipe.BoneSamplingRecipe;
import piotro15.symbiont.common.registry.ModItems;

public class BoneSamplingRecipeCategory extends AbstractRecipeCategory<BoneSamplingRecipe> {
    private final IDrawableAnimated arrow;
    private final IDrawableStatic background;

    public BoneSamplingRecipeCategory(IGuiHelper helper) {
        super(
                ModJeiRecipeTypes.BONE_SAMPLING,
                Component.translatable("item.symbiont.bone_sampler"),
                helper.createDrawableItemLike(ModItems.BONE_SAMPLER.get()),
                104,
                42
        );

        arrow = helper.createAnimatedRecipeArrow(120);
        background = helper.createDrawable(ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, "textures/gui/bone_sampling.png"), 0, 0, 104, 42);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BoneSamplingRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.addOutputSlot(83, 13).addIngredient(VanillaTypes.ITEM_STACK, recipe.output());
    }

    @Override
    public void draw(@NotNull BoneSamplingRecipe recipe, @NotNull IRecipeSlotsView slots, @NotNull GuiGraphics gfx, double mouseX, double mouseY) {
        background.draw(gfx);
        arrow.draw(gfx,49, 12);

        Level level = Minecraft.getInstance().level;

        if (level == null)
            return;

        Entity entity = recipe.entityType().create(level);

        if (entity instanceof LivingEntity livingEntity)
            EntityIngredientRenderer.render(gfx, 5, 5, livingEntity, false, false);
    }
}
