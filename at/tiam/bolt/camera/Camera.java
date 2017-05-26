package at.tiam.bolt.camera;

import at.tiam.bolt.util.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;

import at.tiam.bolt.Bolt;
import at.tiam.bolt.gui.Window;
import net.minecraft.entity.Entity;

/**
 * Created by quicktime on 5/24/17.
 */
public class Camera {

    private static boolean capturing;
    private static int fontRendererID;
    private int width = 360, height = 200;
    private Framebuffer framebuffer;
    private Window displayedWindow;
    public float cameraRotationYaw, cameraRotationPitch;
    public double cameraPosX, cameraPosY, cameraPosZ;
    private boolean frameBufferUpdated, reflected;

    private Minecraft mc;

    public Camera() {
        this(null, false);
    }

    public Camera(Window displayedWindow) { this(displayedWindow, false); }
    public Camera(Window displayedWindow, boolean reflected) {
        this.reflected = reflected;
        this.displayedWindow = displayedWindow;

        mc = Minecraft.getMinecraft();

        framebuffer = new Framebuffer(width, height, true);
        makeNewFrameBuffer();

        Bolt.getBolt().registerCamera(this);

        if (fontRendererID == 0) {
            fontRendererID = mc.getTextureManager().getTexture(mc.fontRendererObj.locationFontTexture).getGlTextureId();
        }
    }

    protected void setCapture(boolean capture) {
        if (capture) {
            framebuffer.bindFramebuffer(true);
        } else {
            framebuffer.unbindFramebuffer();
        }

        capturing = capture;
    }

    public void updateFramebuffer() {
        // So we don't make the loop of rendering the camera
        // TODO: make this work when the game isn't in focus

        if (capturing || !mc.inGameHasFocus || !isCameraVisible()) {
            return;
        }

        // Saves the player's current position and game settings

        double posX, posY, posZ, prevPosX, prevPosY, prevPosZ, lastTickPosX, lastTickPosY, lastTickPosZ;
        int displayWidth, displayHeight, thirdPersonView;
        float rotationYaw, rotationPitch, prevRotationYaw, prevRotationPitch;
        boolean hideGUI, viewBobbing;

        displayWidth = mc.displayWidth;
        displayHeight = mc.displayHeight;
        hideGUI = mc.gameSettings.hideGUI;
        thirdPersonView = mc.gameSettings.thirdPersonView;
        viewBobbing = mc.gameSettings.viewBobbing;

        /*
        RenderViewEntity Calls
         */
        
            rotationYaw = mc.getRenderViewEntity().rotationYaw;
            prevRotationYaw = mc.getRenderViewEntity().prevRotationYaw;
            rotationPitch = mc.getRenderViewEntity().rotationPitch;
            prevRotationPitch = mc.getRenderViewEntity().prevRotationPitch;

            posX = mc.getRenderViewEntity().posX;
            prevPosX = mc.getRenderViewEntity().prevPosX;
            lastTickPosX = mc.getRenderViewEntity().lastTickPosX;

            posY = mc.getRenderViewEntity().posY;
            prevPosY = mc.getRenderViewEntity().prevPosY;
            lastTickPosY = mc.getRenderViewEntity().lastTickPosY;

            posZ = mc.getRenderViewEntity().posZ;
            prevPosZ = mc.getRenderViewEntity().prevPosZ;
            lastTickPosZ = mc.getRenderViewEntity().lastTickPosZ;

        // Sets the player's position to the camera position
        
            mc.getRenderViewEntity().posX = cameraPosX;
            mc.getRenderViewEntity().prevPosX = cameraPosX;
            mc.getRenderViewEntity().lastTickPosX = cameraPosX;

            mc.getRenderViewEntity().posY = cameraPosY;
            mc.getRenderViewEntity().prevPosY = cameraPosY;
            mc.getRenderViewEntity().lastTickPosY = cameraPosY;

            mc.getRenderViewEntity().posZ = cameraPosZ;
            mc.getRenderViewEntity().prevPosZ = cameraPosZ;
            mc.getRenderViewEntity().lastTickPosZ = cameraPosZ;

            mc.getRenderViewEntity().rotationPitch = cameraRotationPitch;
            mc.getRenderViewEntity().prevRotationPitch = cameraRotationPitch;
            mc.getRenderViewEntity().rotationYaw = cameraRotationYaw;
            mc.getRenderViewEntity().prevRotationYaw = cameraRotationYaw;
        

        mc.displayHeight = height;
        mc.displayWidth = width;
        mc.gameSettings.thirdPersonView = thirdPersonView;
        mc.gameSettings.viewBobbing = viewBobbing;
        mc.gameSettings.hideGUI = hideGUI;

        setCapture(true);

        mc.entityRenderer.updateCameraAndRender(mc.getRenderPartialTicks(), System.nanoTime());

        setCapture(false);

        //Sets the player's position back to the saved position and reverses the game settings changes

        mc.displayWidth = displayWidth;
        mc.displayHeight = displayHeight;
        mc.getRenderViewEntity().rotationYaw = rotationYaw;
        mc.getRenderViewEntity().prevRotationYaw = prevRotationYaw;
        mc.getRenderViewEntity().rotationPitch = rotationPitch;
        mc.getRenderViewEntity().prevRotationPitch = prevRotationPitch;
        mc.gameSettings.thirdPersonView = thirdPersonView;
        mc.gameSettings.hideGUI = hideGUI;
        mc.gameSettings.viewBobbing = viewBobbing;

        mc.getRenderViewEntity().posX = posX;
        mc.getRenderViewEntity().prevPosX = prevPosX;
        mc.getRenderViewEntity().lastTickPosX = lastTickPosX;

        mc.getRenderViewEntity().posY = posY;
        mc.getRenderViewEntity().prevPosY = prevPosY;
        mc.getRenderViewEntity().lastTickPosY = lastTickPosY;

        mc.getRenderViewEntity().posZ = posZ;
        mc.getRenderViewEntity().prevPosZ = prevPosZ;
        mc.getRenderViewEntity().lastTickPosZ = lastTickPosZ;

        frameBufferUpdated = true;

    }

    private boolean isCameraVisible() {
        return displayedWindow != null && displayedWindow.pinned && displayedWindow.extended;
    }

    public void makeNewFrameBuffer(){
        framebuffer.createFramebuffer(width, height);
    }

    public void draw(double x, double y, double x1, double y1) {
        // Note to self for updating
        // Taken from Framebuffer.java in method func_178038_a on line 234
        GlStateManager.enableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableAlpha();

        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        framebuffer.bindFramebufferTexture();

        if (reflected) {
            GuiUtils.drawReflectedTexturedRect(x, y, x1, y1);
        } else {
            GuiUtils.drawFlippedTexturedModalRect(x, y, x1, y1);
        }

        framebuffer.unbindFramebufferTexture();
    }

    protected void setToEntityPosition(Entity e) {
        cameraPosX = e.lastTickPosX - (e.lastTickPosX - e.posX) * mc.getRenderPartialTicks();
        cameraPosY = e.lastTickPosY - (e.lastTickPosY - e.posY) * mc.getRenderPartialTicks();
        cameraPosZ = e.lastTickPosZ - (e.lastTickPosZ - e.posZ) * mc.getRenderPartialTicks();
    }

    protected void setToEntityPositionAndRotation(Entity e) {
        setToEntityPosition(e);
        cameraRotationPitch = e.rotationPitch;
        cameraRotationYaw = e.rotationYaw;
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public void setWidth(int width) {
        this.width = width;
        frameBufferUpdated = false;
    }

    public void setHeight(int height) {
        this.height = height;
        frameBufferUpdated = false;
    }

    public boolean isFrameBufferUpdated() { return frameBufferUpdated; }

    public Camera setReflected(boolean reflected) {
        this.reflected = reflected;
        return this;
    }

    public static boolean isCapturing() { return capturing; }

    public static void setCapturing(boolean capturing) { Camera.capturing = capturing; }

}
