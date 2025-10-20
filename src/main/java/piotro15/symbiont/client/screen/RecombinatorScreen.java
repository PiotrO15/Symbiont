package piotro15.symbiont.client.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import piotro15.symbiont.common.item.CellCultureItem;
import piotro15.symbiont.common.menu.MetabolizerMenu;
import piotro15.symbiont.common.menu.RecombinatorMenu;
import piotro15.symbiont.common.Symbiont;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class RecombinatorScreen extends BasicMachineScreen<RecombinatorMenu> {

    private static final ResourceLocation TEXTURE =
            Symbiont.id("textures/gui/recombinator.png");

    public RecombinatorScreen(RecombinatorMenu menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);
        this.imageWidth = 176;
        this.imageHeight = 199;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void renderBg(GuiGraphics gfx, float partialTicks, int mouseX, int mouseY) {
        gfx.blit(TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        super.render(gfx, mouseX, mouseY, partialTick);

        if (menu.hasCell()) {
            gfx.blit(Symbiont.id("textures/gui/recombinator_cell.png"), leftPos + 61, topPos + 20, 0, 0, 54, 54, 54, 54);
        }

        if (mouseX >= leftPos + 58 && mouseX < leftPos + 117 &&
                mouseY >= topPos + 17 && mouseY < topPos + 76) {

            List<CellCultureItem.AppliedBiotrait> result = menu.getResultingBiocode();
            if (result.isEmpty()) {
                return;
            }

            List<Component> tooltip = new ArrayList<>();
            tooltip.add(Component.translatable("item.symbiont.cell_culture.prediction"));
            tooltip.addAll(CellCultureItem.createTooltip(result, hasControlDown()));

            gfx.renderTooltip(Minecraft.getInstance().font, tooltip, Optional.empty(), mouseX, mouseY);
        }
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}

