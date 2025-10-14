package piotro15.symbiont.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import piotro15.symbiont.api.EnergyBarRenderer;
import piotro15.symbiont.api.FluidTankRenderer;
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.common.menu.MetabolizerMenu;

@OnlyIn(Dist.CLIENT)
public class MetabolizerScreen extends BasicMachineScreen<MetabolizerMenu> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, "textures/gui/metabolizer.png");

    public MetabolizerScreen(MetabolizerMenu menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
        this.inventoryLabelY = this.imageHeight - 94;

        bars.add(new FluidTankRenderer(menu.getInputFluidTank(), 35, 17, 16, 52));
        bars.add(new FluidTankRenderer(menu.getOutputFluidTank(), 152, 17, 16, 52));
        bars.add(new EnergyBarRenderer(() -> menu.getContainerData(2), () -> menu.getContainerData(3), 8, 17, 16, 52));
    }

    @Override
    protected void renderBg(GuiGraphics gfx, float partialTicks, int mouseX, int mouseY) {
        super.renderBg(gfx, partialTicks, mouseX, mouseY);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        renderProgressArrow(gfx, 86 + x, 34 + y);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}

