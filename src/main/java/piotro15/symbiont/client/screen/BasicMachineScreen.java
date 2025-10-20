package piotro15.symbiont.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import piotro15.symbiont.util.BarRenderer;
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.common.menu.BasicMachineMenu;

import java.util.ArrayList;
import java.util.List;

public abstract class BasicMachineScreen<M extends BasicMachineMenu> extends AbstractContainerScreen<M> {
    private static final ResourceLocation ARROW_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID,"textures/gui/arrow_progress.png");

    protected final List<BarRenderer> bars = new ArrayList<>();

    public BasicMachineScreen(M menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics gfx, float v, int i, int i1) {
        gfx.blit(getTexture(), leftPos, topPos, 0, 0, imageWidth, imageHeight);
        bars.forEach(bar -> bar.render(gfx, leftPos, topPos));
    }

    @Override
    public void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        super.render(gfx, mouseX, mouseY, partialTick);

        this.renderTooltip(gfx, mouseX, mouseY);

        bars.forEach(bar -> bar.renderOverlay(gfx, leftPos, topPos));
        bars.forEach(bar -> bar.renderTooltip(gfx, leftPos, topPos, mouseX, mouseY));
    }

    public abstract ResourceLocation getTexture();

    protected void renderProgressArrow(GuiGraphics gfx, int x, int y) {
        if(menu.isCrafting()) {
            gfx.blit(ARROW_TEXTURE, x, y, 0, 0, menu.getScaledArrowProgress(), 16, 24, 16);
        }
    }
}
