package piotro15.symbiont.api;

import net.minecraft.client.gui.GuiGraphics;

public interface BarRenderer {
    void render(GuiGraphics gfx, int x, int y);
    void renderTooltip(GuiGraphics gfx, int leftPos, int topPos, int mouseX, int mouseY);
    default void renderOverlay(GuiGraphics gfx, int leftPos, int topPos) {}
}
