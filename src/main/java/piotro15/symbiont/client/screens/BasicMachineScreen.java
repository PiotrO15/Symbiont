package piotro15.symbiont.client.screens;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import piotro15.symbiont.api.BarRenderer;
import piotro15.symbiont.common.menus.BasicMachineMenu;

import java.util.ArrayList;
import java.util.List;

public abstract class BasicMachineScreen<M extends BasicMachineMenu> extends AbstractContainerScreen<M> {
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
}
