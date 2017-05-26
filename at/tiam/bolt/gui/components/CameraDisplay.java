package at.tiam.bolt.gui.components;

import at.tiam.bolt.Bolt;
import at.tiam.bolt.camera.Camera;
import at.tiam.bolt.gui.AbstractComponent;
import at.tiam.bolt.gui.ClickGui;
import at.tiam.bolt.util.GuiUtils;
import net.minecraft.client.gui.FontRenderer;

/**
 * Created by quicktime on 5/26/17.
 */
public class CameraDisplay extends AbstractComponent {

    private Camera camera;
    private boolean draggingHeight, draggingWidth;
    FontRenderer fontRenderer = Bolt.getBolt().getFontRenderer();

    public CameraDisplay(Camera camera) {
        super();
        this.camera = camera;
    }

    @Override
    public void draw(int x, int y) {
        if (draggingWidth) {
            camera.setWidth((x - getX()) * 2);

            if (camera.getWidth() > 800) {
                camera.setWidth(800);
            }

            if (camera.getWidth() < 120) {
                camera.setWidth(120);
            }
            rect.updateSize();
        }

        if (draggingHeight) {
            camera.setHeight((y - getY()) * 2);

            if (camera.getHeight() > 600) {
                camera.setHeight(600);
            }

            if (camera.getHeight() < 60) {
                camera.setHeight(60);
            }
            rect.updateSize();
        }

        if (camera.isFrameBufferUpdated()) {
            camera.draw(getX(), getY(), getX() + camera.getWidth() / 2, getY() + camera.getHeight() / 2);
        } else {
            String closeMessage = "Close GUI";
            String updateMessage = "to update";

            fontRenderer.drawString(closeMessage,
                    getX() + camera.getWidth() / 4 - fontRenderer.getStringWidth(closeMessage) / 2,
                    getY() + camera.getHeight() / 4 - fontRenderer.FONT_HEIGHT / 2 - 6,
                    0xFFFFFFFF);

            fontRenderer.drawStringWithShadow(updateMessage,
                    getX() + camera.getWidth() / 4 - fontRenderer.getStringWidth(updateMessage) / 2,
                    getY() + camera.getHeight() / 4 - fontRenderer.FONT_HEIGHT / 2 - 6,
                    0xFFFFFFFF);
        }

        if (Bolt.getBolt().getMc().currentScreen instanceof ClickGui) {
            GuiUtils.drawRect(
                    getX() + camera.getWidth() / 4 - 15,
                    getY() + camera.getHeight() / 2,
                    getX() + camera.getWidth() / 4 + 15,
                    getY() + camera.getHeight() / 2 + 4,
                    0x5FFFFFFF);

            GuiUtils.drawRect(
                    getX() + camera.getWidth() / 2,
                    getY() + camera.getHeight() / 4 - 15,
                    getX() + camera.getWidth() / 2 + 4,
                    getY() + camera.getHeight() / 4 + 15,
                    0x5FFFFFFF);
        }
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (mouseOverWidthSlider(x, y, getX(), getY())) {
            draggingWidth = true;
        }

        if (mouseOverHeightSlider(x, y, getX(), getY())) {
            draggingHeight = true;
        }
    }

    @Override
    public void mouseReleased(int mouesX, int mouseY, int state) {
        if (draggingWidth || draggingHeight) {
            camera.makeNewFrameBuffer();
            rect.updateSize();
        }

        draggingHeight = false;
        draggingWidth = false;
    }

    private boolean mouseOverWidthSlider(int mouseX, int mouseY, int cx, int cy) {
        return (mouseX > cx + camera.getWidth() / 4 - 15) &&
                (mouseX <= cx + camera.getWidth() / 4 + 15) &&
                (mouseY > cy + camera.getHeight() / 2) &&
                (mouseY <= cy + camera.getHeight() / 2 + 5);
    }

    private boolean mouseOverHeightSlider(int mouseX, int mouseY, int cx, int cy) {
        return (mouseX > cx + camera.getWidth() / 2) &&
                (mouseX <= cx + camera.getWidth() / 2 + 5) &&
                (mouseY > cy + camera.getHeight() / 4 - 15) &&
                (mouseY <= cy + camera.getHeight() / 4 + 15);

    }

    @Override
    public int getWidth() { return camera.getWidth() / 2; }

    @Override
    public int getHeight() { return camera.getHeight() /2; }
}
