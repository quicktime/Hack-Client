package at.tiam.bolt.util;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
//import net.minecraft.client.renderer.WorldRenderer;
//import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
// import net.yawk.client.gui.ColorType;

import at.tiam.bolt.gui.ColorType;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

/**
 * Created by quicktime on 5/25/17.
 */
public class GuiUtils {

    public static ScaleManager scaleManager = new ScaleManager();
    private static Minecraft mc = Minecraft.getMinecraft();

    public static void drawRect(double paramXStart, double paramYStart, double paramXEnd, double paramYEnd, int paramColor)
    {
        float alpha = (float)(paramColor >> 24 & 0xFF) / 255F;
        float red = (float)(paramColor >> 16 & 0xFF) / 255F;
        float green = (float)(paramColor >> 8 & 0xFF) / 255F;
        float blue = (float)(paramColor & 0xFF) / 255F;

        GL11.glClear(256);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);

        GL11.glPushMatrix();
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2d(paramXEnd, paramYStart);
        GL11.glVertex2d(paramXStart, paramYStart);
        GL11.glVertex2d(paramXStart, paramYEnd);
        GL11.glVertex2d(paramXEnd, paramYEnd);
        GL11.glEnd();
        GL11.glPopMatrix();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);

        //Added by Yaweh
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glClear(256);
    }

    public static void drawTextureRect(double x, double y, double x1, double y1)
    {
        Tessellator tes = Tessellator.getInstance();
//        WorldRenderer wr = tes.getWorldRenderer();
//
//        wr.startDrawing(GL11.GL_QUADS);
//        wr.addVertexWithUV(x, y1, 0, 0, 0);
//        wr.addVertexWithUV(x1, y1, 0, 1, 0);
//        wr.addVertexWithUV(x1, y, 0, 1, 1);
//        wr.addVertexWithUV(x, y, 0, 0, 1);
        tes.draw();
    }

    public static void drawNodusBorder(int x, int y, int x1, int y1) {
        drawBorder(x, y, x1, y1, 2, ColorType.BORDER.getModifiedColor());
    }

    public static void drawTopNodusRect(int x, int y, int x1, int y1) {
        GuiUtils.drawBorderedRect(x, y, x1, y1, 2, ColorType.BORDER.getModifiedColor(), ColorType.TITLE.getColor());
    }

    public static void drawBottomNodusRect(float x, float y, float x1, float y1, boolean titleVisible) {
        //GuiUtils.drawRect(par1 - 2.0F, par2 /*- 2.0F*/, par3 + 2.0F, par4 + 2.0F, 0x5FFFFFFF);
        GuiUtils.drawRect(x, y, x1, y1, 0x9F000000);

        int lw = 2;

        if(!titleVisible){
            //Cuts off the top border
            GuiUtils.drawRect(x - lw, y - lw, x1 + lw, y, ColorType.BORDER.getModifiedColor());
        }

        GuiUtils.drawRect(x, y1, x1, y1 + lw, ColorType.BORDER.getModifiedColor());

        GuiUtils.drawRect(x - lw, y, x, y1 + lw, ColorType.BORDER.getModifiedColor());
        GuiUtils.drawRect(x1, y, x1 + lw, y1 + lw, ColorType.BORDER.getModifiedColor());
    }

    public static void drawBorder(int x, int y, int x1, int y1, int lw, int borderC){
        GuiUtils.drawRect(x, y - lw, x1, y, ColorType.BORDER.getModifiedColor());
        GuiUtils.drawRect(x, y1, x1, y1 + lw, ColorType.BORDER.getModifiedColor());

        GuiUtils.drawRect(x - lw, y - lw, x, y1 + lw, ColorType.BORDER.getModifiedColor());
        GuiUtils.drawRect(x1, y - lw, x1 + lw, y1 + lw, ColorType.BORDER.getModifiedColor());
    }

    public static void drawBorderedRect(int x, int y, int x1, int y1, int lw, int borderC, int insideC){

        GuiUtils.drawRect(x, y - lw, x1, y, borderC);
        GuiUtils.drawRect(x, y1, x1, y1 + lw, borderC);

        GuiUtils.drawRect(x - lw, y - lw, x, y1 + lw, borderC);
        GuiUtils.drawRect(x1, y - lw, x1 + lw, y1 + lw, borderC);

        GuiUtils.drawRect(x, y, x1, y1, insideC);
    }

    public static void drawBorderedRoundedRect(float x, float y, float x1, float y1, int borderC, int insideC) {
        x *= 2; y *= 2; x1 *= 2; y1 *= 2;
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        drawRect(x + 1, y + 1, x1 - 1, y1 - 1, insideC);
        drawVLine(x, y + 1, y1 -2, borderC);
        drawVLine(x1 - 1, y + 1, y1 - 2, borderC);
        drawHLine(x + 2, x1 - 3, y, borderC);
        drawHLine(x + 2, x1 - 3, y1 -1, borderC);
        drawHLine(x + 1, x + 1, y + 1, borderC);
        drawHLine(x1 - 2, x1 - 2, y + 1, borderC);
        drawHLine(x1 - 2, x1 - 2, y1 - 2, borderC);
        drawHLine(x + 1, x + 1, y1 - 2, borderC);
        GL11.glScalef(2.0F, 2.0F, 2.0F);
    }

    public static void drawHLine(double par1, double par2, double par3, int par4)
    {
        if (par2 < par1)
        {
            double var5 = par1;
            par1 = par2;
            par2 = var5;
        }

        drawRect(par1, par3, par2 + 1, par3 + 1, par4);
    }

    public static void drawVLine(double x, double y, double y1, int colour)
    {
        if (y1 < y)
        {
            double var5 = y;
            y = y1;
            y1 = var5;
        }

        drawRect(x, y + 1, x + 1, y1, colour);
    }

    public static void drawTriangle(double x, double y, double rotation, int paramColor) {

        float alpha = (float)(paramColor >> 24 & 0xFF) / 255F;
        float red = (float)(paramColor >> 16 & 0xFF) / 255F;
        float green = (float)(paramColor >> 8 & 0xFF) / 255F;
        float blue = (float)(paramColor & 0xFF) / 255F;

        GL11.glClear(256);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);

        GL11.glTranslated(x, y, 0);
        GL11.glRotated(rotation, 0F, 0F, 1.0F);

        GL11.glPushMatrix();

        GL11.glBegin(GL11.GL_TRIANGLES);
        GL11.glVertex2d(0, -4);
        GL11.glVertex2d(-4, 2);
        GL11.glVertex2d(4, 2);
        GL11.glEnd();

        GL11.glPopMatrix();

        GL11.glRotated(-rotation, 0F, 0F, 1.0F);
        GL11.glTranslated(-x, -y, 0);

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
    }

    public static void drawSmallTriangle(double x, double y, double rotation, int paramColor) {

        float alpha = (float)(paramColor >> 24 & 0xFF) / 255F;
        float red = (float)(paramColor >> 16 & 0xFF) / 255F;
        float green = (float)(paramColor >> 8 & 0xFF) / 255F;
        float blue = (float)(paramColor & 0xFF) / 255F;

        GL11.glClear(256);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);

        GL11.glTranslated(x, y, 0);
        GL11.glRotated(rotation, 0F, 0F, 1.0F);

        GL11.glPushMatrix();

        GL11.glBegin(GL11.GL_TRIANGLES);
        GL11.glVertex2d(0, -2);
        GL11.glVertex2d(-2, 4);
        GL11.glVertex2d(2, 4);
        GL11.glEnd();

        GL11.glPopMatrix();

        GL11.glRotated(-rotation, 0F, 0F, 1.0F);
        GL11.glTranslated(-x, -y, 0);

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
    }

    public static void drawCorrectTexturedModalRect(double x, double y, double x1, double y1)
    {
        Tessellator tes = Tessellator.getInstance();
//        WorldRenderer wr = tes.getWorldRenderer();
//
//        wr.startDrawingQuads();
//        wr.addVertexWithUV(x, y1, 0, 0, 1);
//        wr.addVertexWithUV(x1, y1, 0, 1, 1);
//        wr.addVertexWithUV(x1, y, 0, 1, 0);
//        wr.addVertexWithUV(x, y, 0, 0, 0);
        tes.draw();
    }

    /**
     * Renders the specified item of the inventory slot at the specified location. Args: itemstack, x, y, partialTick
     * Taken from GuiIngame
     */
    public static void renderItemStack(ItemStack var6, int p_175184_2_, int p_175184_3_, float p_175184_4_, EntityPlayer p_175184_5_)
    {

        if (var6 != null)
        {

            float var7 = (float)var6.animationsToGo - p_175184_4_;

            if (var7 > 0.0F)
            {
                GlStateManager.pushMatrix();
                float var8 = 1.0F + var7 / 5.0F;
                GlStateManager.translate((float)(p_175184_2_ + 8), (float)(p_175184_3_ + 12), 0.0F);
                GlStateManager.scale(1.0F / var8, (var8 + 1.0F) / 2.0F, 1.0F);
                GlStateManager.translate((float)(-(p_175184_2_ + 8)), (float)(-(p_175184_3_ + 12)), 0.0F);
            }

//            mc.getRenderItem().func_180450_b(var6, p_175184_2_, p_175184_3_);
            mc.getRenderItem().renderItemIntoGUI(var6, p_175184_2_, p_175184_3_);

            if (var7 > 0.0F)
            {
                GlStateManager.popMatrix();
            }

//            mc.getRenderItem().func_175030_a(mc.fontRendererObj, var6, p_175184_2_, p_175184_3_);
            mc.getRenderItem().renderItemOverlays(mc.fontRendererObj, var6, p_175184_2_, p_175184_3_);
        }

        //Needed because of something in renderItemOverlayIntoGUI
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_CULL_FACE);
    }

    public static void drawFlippedTexturedModalRect(double x, double y, double x1, double y1) {
        Tessellator tes = Tessellator.getInstance();
//        WorldRenderer wr = tes.getWorldRenderer();
//
//        wr.startDrawing(GL11.GL_QUADS);
//        wr.addVertexWithUV(x, y1, 0, 0, 0);
//        wr.addVertexWithUV(x1, y1, 0, 1, 0);
//        wr.addVertexWithUV(x1, y, 0, 1, 1);
//        wr.addVertexWithUV(x, y, 0, 0, 1);
        tes.draw();
    }

    public static void drawReflectedTexturedRect(double x, double y, double x1, double y1) {

        Tessellator tes = Tessellator.getInstance();
//        WorldRenderer wr = tes.getWorldRenderer();
//
//        wr.startDrawing(GL11.GL_QUADS);
//        wr.addVertexWithUV(x, y1, 0, 1, 0);
//        wr.addVertexWithUV(x1, y1, 0, 0, 0);
//        wr.addVertexWithUV(x1, y, 0, 0, 1);
//        wr.addVertexWithUV(x, y, 0, 1, 1);
        tes.draw();
    }

    private static int fontRendererID = -1;

    public static void rebindFontRenderer(){

        if(fontRendererID == -1){
            fontRendererID =  mc.getTextureManager().getTexture(mc.fontRendererObj.locationFontTexture).getGlTextureId();
        }

        glBindTexture(GL_TEXTURE_2D, fontRendererID);
    }
}
