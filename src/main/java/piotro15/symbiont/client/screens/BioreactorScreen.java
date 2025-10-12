package piotro15.symbiont.client.screens;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;
import piotro15.symbiont.api.FluidTankRenderer;
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.common.menus.BioreactorMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class BioreactorScreen extends AbstractContainerScreen<BioreactorMenu> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, "textures/gui/bioreactor.png");

    private static final ResourceLocation ARROW_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID,"textures/gui/arrow_progress.png");

    private static final ResourceLocation ENERGY_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, "textures/gui/energy_storage_16_52.png");

    private static final ResourceLocation FLUID_TANK_OVERLAY =
            ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, "textures/gui/fluid_tank_overlay.png");

    public BioreactorScreen(BioreactorMenu menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void renderBg(GuiGraphics gfx, float partialTicks, int mouseX, int mouseY) {
        gfx.blit(TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;


        FluidTankRenderer.renderFluid(gfx, x + 35, y + 17, 16, 52, menu.getInputFluid(), 4000);

        FluidTankRenderer.renderFluid(gfx, x + 152, y + 17, 16, 52, menu.getOutputFluid(), 4000);

        renderProgressArrow(gfx, x, y);

        renderEnergyStorage(gfx, x, y);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(ARROW_TEXTURE, 86 + x, 34 + y, 0, 0, menu.getScaledArrowProgress(), 16, 24, 16);
        }
    }

    private void renderEnergyStorage(GuiGraphics guiGraphics, int x, int y) {
        guiGraphics.blit(ENERGY_TEXTURE, 8 + x, 17 + y + (52 - menu.getScaledPowerStorage()), 0, 0, 16, menu.getScaledPowerStorage(), 16, 52);
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);

        int tankX = leftPos + 8;
        int tankY = topPos + 17;
        int tankWidth = 16;
        int tankHeight = 52;

        EnergyStorage stack = menu.getEnergyStorage();

        if (mouseX >= tankX && mouseX < tankX + tankWidth &&
                mouseY >= tankY && mouseY < tankY + tankHeight) {

            List<Component> tooltip = new ArrayList<>();
            tooltip.add(Component.literal(stack.getEnergyStored() + " / " + stack.getMaxEnergyStored() + " RF"));

            guiGraphics.renderTooltip(font, tooltip, Optional.empty(), mouseX, mouseY);
        }

        FluidTank inputStack = menu.getInputFluidTank();

        tankX = leftPos + 35;

        if (mouseX >= tankX && mouseX < tankX + tankWidth &&
                mouseY >= tankY && mouseY < tankY + tankHeight) {

            List<Component> tooltip = new ArrayList<>();
            if (inputStack.getFluid().getAmount() > 0) {
                tooltip.add(inputStack.getFluid().getHoverName());
            }
            tooltip.add(Component.literal(inputStack.getFluid().getAmount() + " / " + inputStack.getCapacity() + " mB"));

            guiGraphics.renderTooltip(font, tooltip, Optional.empty(), mouseX, mouseY);
        }

        FluidTank outputStack = menu.getOutputFluidTank();

        tankX = leftPos + 152;

        if (mouseX >= tankX && mouseX < tankX + tankWidth &&
                mouseY >= tankY && mouseY < tankY + tankHeight) {

            List<Component> tooltip = new ArrayList<>();
            if (outputStack.getFluid().getAmount() > 0) {
                tooltip.add(outputStack.getFluid().getHoverName());
            }
            tooltip.add(Component.literal(outputStack.getFluid().getAmount() + " / " + outputStack.getCapacity() + " mB"));

            guiGraphics.renderTooltip(font, tooltip, Optional.empty(), mouseX, mouseY);
        }
    }
}

