package io.github.koxx12dev.scc.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

/**
 * :trollage:
 */
public class SkyClientMainMenu extends GuiMainMenu {
    private static final String MOJANG_COPYRIGHT_TEXT = "Copyright Mojang AB. Do not distribute!";
    private static final ResourceLocation[] titlePanoramaPaths = new ResourceLocation[]{new ResourceLocation("scc", "panorama_0.png"), new ResourceLocation("scc", "panorama_1.png"), new ResourceLocation("scc", "panorama_2.png"), new ResourceLocation("scc", "panorama_3.png"), new ResourceLocation("scc", "panorama_4.png"), new ResourceLocation("scc", "panorama_5.png")};
    private static final ResourceLocation skyclientLogo = new ResourceLocation("scc", "skyclient_logo.png");
    private static final Color barColor = new Color(0, 0, 0, 128);
    private int panoramaTimer;
    private ResourceLocation backgroundTexture;
    private GuiButton selectedButton;
    private static Class<?> cosmeticGui;

    static {
        try {
            cosmeticGui = Class.forName("gg.essential.gui.studio.CosmeticStudio");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public SkyClientMainMenu() {

    }

    @Override
    public void updateScreen() {
        ++this.panoramaTimer;
    }

    @Override
    public void initGui() {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.shadeModel(7425);
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();

        this.backgroundTexture = this.mc.getTextureManager().getDynamicTextureLocation("background", new DynamicTexture(256, 256));
        buttonList.add(new GuiButton(1, width / 20, (height / 2) - 20, 100, 20, "Singleplayer"));
        buttonList.add(new GuiButton(2, width / 20, (height / 2) + 10, 100, 20, "§6Play Hypixel§r"));
        buttonList.add(new GuiButton(3, width / 20, (height / 2) + 40, 100, 20, "Servers"));
        buttonList.add(new GuiButton(4, width / 20, (height / 2) + 70, 100, 20, "Settings"));
        buttonList.add(new GuiButton(5, width / 20, (height / 2) + 100, 100, 20, "Quit Game"));
        buttonList.add(new ImageButton(new ResourceLocation("scc", "cosmetics.png"), 6, width - 22, height - 35, 20, 20));
        //buttonList.add(new ImageButton(new ResourceLocation("scc", "essential4.png"), 7, width - 22, height - 35, 20, 20));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 1:
                this.mc.displayGuiScreen(new GuiSelectWorld(this));
                break;
            case 2:
                FMLClientHandler.instance().connectToServer(new GuiMultiplayer(Minecraft.getMinecraft().currentScreen), new ServerData("hypixel", "mc.hypixel.net", false));
                break;
            case 3:
                this.mc.displayGuiScreen(new GuiMultiplayer(this));
                break;
            case 4:
                this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                break;
            case 5:
                this.mc.shutdown();
                break;
            case 6:
                try {
                    this.mc.displayGuiScreen((GuiScreen) cosmeticGui.newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void drawPanorama(float partialTicks) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.matrixMode(5889);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        Project.gluPerspective(120.0f, 1.0f, 0.05f, 10.0f);
        GlStateManager.matrixMode(5888);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.rotate(180.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.disableCull();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        int i = 8;
        for (int j = 0; j < i * i; ++j) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(((float) (j % i) / (float) i - 0.5f) / 64.0f, ((float) (j / i) / (float) i - 0.5f) / 64.0f, 0.0f);
            GlStateManager.rotate(MathHelper.sin(((float) this.panoramaTimer + partialTicks) / 400.0f) * 25.0f + 20.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(-((float) this.panoramaTimer + partialTicks) * 0.1f, 0.0f, 1.0f, 0.0f);
            for (int k = 0; k < 6; ++k) {
                GlStateManager.pushMatrix();
                if (k == 1) {
                    GlStateManager.rotate(90.0f, 0.0f, 1.0f, 0.0f);
                }
                if (k == 2) {
                    GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
                }
                if (k == 3) {
                    GlStateManager.rotate(-90.0f, 0.0f, 1.0f, 0.0f);
                }
                if (k == 4) {
                    GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
                }
                if (k == 5) {
                    GlStateManager.rotate(-90.0f, 1.0f, 0.0f, 0.0f);
                }
                this.mc.getTextureManager().bindTexture(titlePanoramaPaths[k]);
                worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                int l = 255 / (j + 1);
                worldrenderer.pos(-1.0, -1.0, 1.0).tex(0.0, 0.0).color(255, 255, 255, l).endVertex();
                worldrenderer.pos(1.0, -1.0, 1.0).tex(1.0, 0.0).color(255, 255, 255, l).endVertex();
                worldrenderer.pos(1.0, 1.0, 1.0).tex(1.0, 1.0).color(255, 255, 255, l).endVertex();
                worldrenderer.pos(-1.0, 1.0, 1.0).tex(0.0, 1.0).color(255, 255, 255, l).endVertex();
                tessellator.draw();
                GlStateManager.popMatrix();
            }
            GlStateManager.popMatrix();
            GlStateManager.colorMask(true, true, true, false);
        }
        worldrenderer.setTranslation(0.0, 0.0, 0.0);
        GlStateManager.colorMask(true, true, true, true);
        GlStateManager.matrixMode(5889);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.popMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.enableCull();
        GlStateManager.enableDepth();
    }

    /**
     * Rotate and blurs the skybox view in the main menu
     */
    private void rotateAndBlurSkybox() {
        this.mc.getTextureManager().bindTexture(this.backgroundTexture);
        GL11.glTexParameteri(3553, 10241, 9729);
        GL11.glTexParameteri(3553, 10240, 9729);
        GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, 256, 256);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.colorMask(true, true, true, false);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        GlStateManager.disableAlpha();
        int i = 3;
        for (int j = 0; j < i; ++j) {
            float f = 1.0f / (float) (j + 1);
            int k = this.width;
            int l = this.height;
            float f1 = (float) (j - i / 2) / 256.0f;
            worldrenderer.pos(k, l, this.zLevel).tex(0.0f + f1, 1.0).color(1.0f, 1.0f, 1.0f, f).endVertex();
            worldrenderer.pos(k, 0.0, this.zLevel).tex(1.0f + f1, 1.0).color(1.0f, 1.0f, 1.0f, f).endVertex();
            worldrenderer.pos(0.0, 0.0, this.zLevel).tex(1.0f + f1, 0.0).color(1.0f, 1.0f, 1.0f, f).endVertex();
            worldrenderer.pos(0.0, l, this.zLevel).tex(0.0f + f1, 0.0).color(1.0f, 1.0f, 1.0f, f).endVertex();
        }
        tessellator.draw();
        GlStateManager.enableAlpha();
        GlStateManager.colorMask(true, true, true, true);
    }

    /**
     * Renders the skybox in the main menu
     */
    private void renderSkybox(float partialTicks) {
        this.mc.getFramebuffer().unbindFramebuffer();
        GlStateManager.viewport(0, 0, 256, 256);
        this.drawPanorama(partialTicks);
        this.rotateAndBlurSkybox();
        this.rotateAndBlurSkybox();
        this.rotateAndBlurSkybox();
        this.rotateAndBlurSkybox();
        this.rotateAndBlurSkybox();
        this.rotateAndBlurSkybox();
        this.rotateAndBlurSkybox();
        this.mc.getFramebuffer().bindFramebuffer(true);
        GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        float f = this.width > this.height ? 120.0f / (float) this.width : 120.0f / (float) this.height;
        float f1 = (float) this.height * f / 256.0f;
        float f2 = (float) this.width * f / 256.0f;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldrenderer.pos(0.0, height, this.zLevel).tex(0.5f - f1, 0.5f + f2).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        worldrenderer.pos(width, height, this.zLevel).tex(0.5f - f1, 0.5f - f2).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        worldrenderer.pos(width, 0.0, this.zLevel).tex(0.5f + f1, 0.5f - f2).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        worldrenderer.pos(0.0, 0.0, this.zLevel).tex(0.5f + f1, 0.5f + f2).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        tessellator.draw();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        //background
        GlStateManager.disableAlpha();
        renderSkybox(partialTicks);
        GlStateManager.enableAlpha();
        GlStateManager.color(1f, 1f, 1f);

        drawRect(width / 20 - 10, 0, width / 20 + 110, height, barColor.getRGB());

        // image
        GlStateManager.color(1f, 1f, 1f);
        Minecraft.getMinecraft().getTextureManager().bindTexture(skyclientLogo);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        glPushMatrix();
        glTranslatef((float) width / 20, 0, 0);
        glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex3f(0, 0, 0);
        glTexCoord2f(0, 1);
        glVertex3f(0, 100, 0);
        glTexCoord2f(1, 1);
        glVertex3f(100, 100, 0);
        glTexCoord2f(1, 0);
        glVertex3f(100, 0, 0);
        glEnd();
        glPopMatrix();
        GlStateManager.disableBlend();

        this.drawString(this.fontRendererObj, MOJANG_COPYRIGHT_TEXT, this.width - this.fontRendererObj.getStringWidth(MOJANG_COPYRIGHT_TEXT) - 2, this.height - 10, -1);

        for (GuiButton guiButton : this.buttonList) {
            guiButton.drawButton(this.mc, mouseX, mouseY);
        }
        for (GuiLabel guiLabel : this.labelList) {
            guiLabel.drawLabel(this.mc, mouseX, mouseY);
        }
    }

    @Override
    public void confirmClicked(boolean result, int id) {

    }

    @Override
    public void onGuiClosed() {

    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            for (int i = 0; i < this.buttonList.size(); ++i) {
                GuiButton guibutton = this.buttonList.get(i);
                if (!guibutton.mousePressed(this.mc, mouseX, mouseY)) continue;
                GuiScreenEvent.ActionPerformedEvent.Pre event = new GuiScreenEvent.ActionPerformedEvent.Pre(this, guibutton, this.buttonList);
                if (MinecraftForge.EVENT_BUS.post(event)) break;
                this.selectedButton = guibutton = event.button;
                guibutton.playPressSound(this.mc.getSoundHandler());
                this.actionPerformed(guibutton);
                if (!this.equals(this.mc.currentScreen)) continue;
                MinecraftForge.EVENT_BUS.post(new GuiScreenEvent.ActionPerformedEvent.Post(this, event.button, this.buttonList));
            }
        }
    }

    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if (this.selectedButton != null && state == 0) {
            this.selectedButton.mouseReleased(mouseX, mouseY);
            this.selectedButton = null;
        }
    }

    private static class ImageButton extends GuiButton {

        private final ResourceLocation image;

        public ImageButton(ResourceLocation image, int buttonId, int x, int y, int widthIn, int heightIn) {
            super(buttonId, x, y, widthIn, heightIn, "");
            this.image = image;
        }

        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY) {
            super.drawButton(mc, mouseX, mouseY);
            if (visible) {
                mc.getTextureManager().bindTexture(image);
                GlStateManager.color(1, 1, 1, 1);
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                GlStateManager.blendFunc(770, 771);
                drawModalRectWithCustomSizedTexture(xPosition, yPosition, 0, 0, width - 2, height - 2, 20, 20);
            }
        }
    }
}
