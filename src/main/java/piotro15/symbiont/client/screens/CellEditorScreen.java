package piotro15.symbiont.client.screens;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import piotro15.symbiont.common.menus.CellEditorMenu;
import piotro15.symbiont.common.Symbiont;

@OnlyIn(Dist.CLIENT)
public class CellEditorScreen extends AbstractContainerScreen<CellEditorMenu> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Symbiont.MOD_ID, "textures/gui/cell_editor.png");

    public CellEditorScreen(CellEditorMenu menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);
        this.imageWidth = 176;
        this.imageHeight = 222;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void renderBg(GuiGraphics gfx, float partialTicks, int mouseX, int mouseY) {
        gfx.blit(TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight);

        // Draw biomolecule icon in the center (overlay)
//        gfx.blit(ResourceLocation.fromNamespaceAndPath(Plasmid.MOD_ID, "textures/gui/biomolecule.png"),
//                leftPos + 80 - 8, topPos + 35 - 8, 0, 0, 16, 16);
    }
}

