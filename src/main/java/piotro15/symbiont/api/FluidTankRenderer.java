package piotro15.symbiont.api;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import org.joml.Matrix4f;

public class FluidTankRenderer {
    public static void renderFluid(GuiGraphics gfx, int x, int y, int width, int height, FluidStack stack, int capacity) {
        if (stack.isEmpty()) return;

        IClientFluidTypeExtensions attributes = IClientFluidTypeExtensions.of(stack.getFluid());
        ResourceLocation fluidTexture = attributes.getStillTexture(stack);
        // Get the sprite for the fluid
        TextureAtlasSprite sprite = Minecraft.getInstance()
                .getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
                .apply(fluidTexture);

        int color = attributes.getTintColor();

        int fluidAmount = stack.getAmount();
        int scaled = (int)((fluidAmount / (float) capacity) * height);

        RenderSystem.enableBlend();
        // Bind block atlas (fluids are in there)
        RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
        FluidTankRenderer.setShaderColor(color);
        gfx.pose().pushPose();

        // Draw fluid bottom-up
        for (int dy = 0; dy < scaled; dy += 16) {
            int drawHeight = Math.min(16, scaled - dy);
//            gfx.blit(x, y + height - dy - drawHeight, 0, width, drawHeight, sprite);
            FluidTankRenderer.drawSprite(x, y + height - dy - drawHeight, 0, width, drawHeight, sprite, gfx.pose());
//            gfx.blit(attributes.getStillTexture(), x, y + height - dy - drawHeight, 0, 0, width, drawHeight, 16, 16);
        }
        gfx.pose().popPose();
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.disableBlend();
    }

    public static void setShaderColor(int color) {
        float r = (color >> 16 & 0xFF) / 255.0F;
        float g = (color >> 8 & 0xFF) / 255.0F;
        float b = (color & 0xFF) / 255.0F;
        float a = (color >> 24 & 0xFF) / 255.0F;

        RenderSystem.setShaderColor(r, g, b, a);
    }

    public static void drawSprite(int x, int y, int z, int width, int height, TextureAtlasSprite sprite, PoseStack matrices) {
        drawTexturedQuad(sprite.atlasLocation(), matrices, x, x + width, y, y + height, z, sprite);
    }

    private static void drawTexturedQuad(ResourceLocation texture, PoseStack matrices, int x1, int x2, int y1, int y2, int z, TextureAtlasSprite textureSprite) {
        float uMin = textureSprite.getU0();
        float uMax = textureSprite.getU1();
        float vMin = textureSprite.getV0();
        float vMax = textureSprite.getV1();

//        vMax = vMax - ((16F - (y2 - y1)) / 16F * (vMax - vMin));
        vMin = vMin - (((y2 - y1) - 16F) / 16F * (vMax - vMin));

        RenderSystem.setShaderTexture(0, texture);
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.enableBlend();
        Matrix4f matrix4f = matrices.last().pose();
        BufferBuilder bufferBuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        bufferBuilder.addVertex(matrix4f, (float) x1, (float) y1, (float) z).setUv(uMin, vMin).setColor(1f, 1f, 1f, 1f);
        bufferBuilder.addVertex(matrix4f, (float) x1, (float) y2, (float) z).setUv(uMin, vMax).setColor(1f, 1f, 1f, 1f);
        bufferBuilder.addVertex(matrix4f, (float) x2, (float) y2, (float) z).setUv(uMax, vMax).setColor(1f, 1f, 1f, 1f);
        bufferBuilder.addVertex(matrix4f, (float) x2, (float) y1, (float) z).setUv(uMax, vMin).setColor(1f, 1f, 1f, 1f);
        BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
        RenderSystem.disableBlend();
    }
}
