package piotro15.symbiont.api;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.energy.EnergyStorage;
import piotro15.symbiont.common.Symbiont;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record EnergyBarRenderer(EnergyStorage storage, int x, int y, int width, int height) implements BarRenderer {
    private static final ResourceLocation ENERGY_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, "textures/gui/energy_storage_16_52.png");

    @Override
    public void render(GuiGraphics gfx, int leftPos, int topPos) {
        int scaled = (int) ((float) storage.getEnergyStored() / storage.getMaxEnergyStored() * height);
        gfx.blit(ENERGY_TEXTURE, leftPos + x, topPos + y + (height - scaled), 0, 0, width, scaled, width, height);
    }

    @Override
    public void renderTooltip(GuiGraphics gfx, int leftPos, int topPos, int mouseX, int mouseY) {
        if (mouseX >= leftPos + x && mouseX < leftPos + x + width &&
                mouseY >= topPos + y && mouseY < topPos + y + height) {

            List<Component> tooltip = new ArrayList<>();
            tooltip.add(Component.literal(storage.getEnergyStored() + " / " + storage.getMaxEnergyStored() + " RF"));

            gfx.renderTooltip(Minecraft.getInstance().font, tooltip, Optional.empty(), mouseX, mouseY);
        }
    }
}
