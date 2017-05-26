package at.tiam.bolt.gui.hub;

import at.tiam.bolt.gui.Canvas;
import at.tiam.bolt.gui.components.buttons.TextButton;
import at.tiam.bolt.util.GuiUtils;
import net.minecraft.client.gui.GuiScreen;

import at.tiam.bolt.Bolt;
import at.tiam.bolt.util.timing.MillisecondTimer;
import at.tiam.bolt.util.timing.FlatTimer;
import at.tiam.bolt.gui.IScalerPosition;

import java.io.IOException;
import java.security.Key;
import java.util.List;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;

/**
 * Created by quicktime on 5/24/17.
 */
public class GuiHub extends GuiScreen {

    public int buttonPaddingX = 30;
    public int buttonWidth = 16;
    public int buttonSlateSpace = 30;
    public int cellSize = 100;
    public int cellPadding = 5;

    public int slateIndex, rotation;

    private State state = State.IDLE;
    private String trail = "";
    private MillisecondTimer timer = new FlatTimer(5);

    public ColorModifier colorModifier;

    public List<Slate> slates = new ArrayList<Slate>();
    private Canvas options;
    private Bolt bolt;

    public GuiHub(Bolt bolt) {
        this.bolt = bolt;
        this.colorModifier = new ColorModifier();

        IScalerPosition pos = new IScalerPosition() {
            @Override
            public int getX() { return width / 2 - 50; }
            @Override
            public int getY() { return 3; }
        };
    }

    @Override
    public void drawScreen(int x, int y, float f) {
        if (this.state == State.LOADING) {
            drawLoadingScreen();
        } else if (this.state == State.FAILED) {
            drawFailedScreen();
        } else if (this.state == State.CONNECTED) {
            renderSlates(x, y);
        }
        options.draw(x, y);
    }

    private void updateTrail() {
        if (trail.length() >= 3) {
            trail = "";
            return;
        }
        trail += ".";
    }

    public void renderSlates(int x, int y) {
        if (needsLeft()) {
            if (mouseOverLeft(x, y)) {
                GuiUtils.drawBorderedRect(buttonPaddingX, this.height / 2 -20, buttonPaddingX + buttonWidth, this.height / 2 + 20, 1, 0xFFFFFFFF, 0x5F000000);
                GuiUtils.drawTriangle(buttonPaddingX + buttonWidth / 2, this.height / 2, 270, 0xFFFFFFFF);
            } else {
                GuiUtils.drawRect(buttonPaddingX, this.height / 2 - 20, buttonPaddingX + buttonWidth, this.height / 2 + 20, 0x5F000000);
                GuiUtils.drawTriangle(buttonPaddingX + buttonWidth / 2, this.height / 2, 270, 0xFF9F9F9F);
            }
        }

        if (needsRight()) {
            if (mouseOverRight(x, y)) {
                GuiUtils.drawBorderedRect(this.width - buttonPaddingX - buttonWidth, this.height/2 - 20, this.width - buttonPaddingX, this.height/2 + 20, 1, 0xFFFFFFFF, 0x5F000000);
                GuiUtils.drawTriangle(this.width - buttonPaddingX - buttonWidth/2, this.height/2, 90, 0xFFFFFFFF);
            } else {
                GuiUtils.drawRect(this.width - buttonPaddingX - buttonWidth, this.height/2 - 20, this.width - buttonPaddingX, this.height/2 + 20, 0x5F000000);
                GuiUtils.drawTriangle(this.width - buttonPaddingX - buttonWidth/2, this.height/2, 90, 0xFF9F9F9F);
            }
        }
        slates.get(slateIndex).renderSlate(x, y);
    }

    private void drawLoadingScreen() {
        if (timer.output()) {
            rotation += 2;

            if (rotation % 50 == 0) {
                updateTrail();
            }
        }

        int posX = width / 2;
        int posY = height / 2;
        int size = 10;

        glTranslatef(posX, posY, 0);
        glRotatef(rotation, 0, 0, 1);

        GuiUtils.drawRect(-size, -size, size, size, 0xFF58B4ED);

        glRotatef(-rotation, 0, 0, 1);
        glTranslatef(-posX, -posY, 0);

        String loading = "Loading" + trail;
        Bolt.getBolt().getFontRenderer().drawString(loading, posX - Bolt.getBolt().getFontRenderer().getStringWidth(loading) / 2, posY + 30, 0xFFFFFFFF);
    }

    private void drawFailedScreen() {

        int posX = width / 2;
        int posY = height / 2;
        int size = 10;

        glTranslatef(posX, posY, 0);
        glRotatef(45, 0, 0, 1);

        GuiUtils.drawRect(-size, -size, size, size, 0xFFFF0000);

        glRotatef(-45, 0, 0, 1);
        glTranslatef(-posX, -posY, 0);

        String failed = "Failed";
        Bolt.getBolt().getFontRenderer().drawString(failed, posX - Bolt.getBolt().getFontRenderer().getStringWidth(failed) / 2, posY + 30, 0xFFFFFFFF);
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);

        if (this.state == State.IDLE && slates.size() == 0) {
            connect();
        }
    }

    private void connect() {
        this.state = State.LOADING;
        Thread thread = new Thread(new HubLoadingThread(this));
        thread.start();
    }

    public void postConnection() {
        slates.add(new MapSlate(this, bolt));
        slates.add(new PluginSlate(this, bolt));
        slates.add(new PrivatePluginSlate(this, bolt));

        for (Slate slate : slates) {
            slate.init();
        }
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    protected void mouseClicked(int x, int y, int b) throws IOException {
        if (needsLeft() && mouseOverLeft(x, y)) {
            slateIndex++;

            if (slateIndex >= slates.size()) {
                slateIndex = slates.size() - 1;
            }
        } else if (needsRight() && mouseOverRight(x, y)) {
            slateIndex --;

            if (slateIndex < 0) {
                slateIndex = 0;
            }
        } else if (slates.size() > 0) {
            slates.get(slateIndex).mouseClicked(x, y);
        }
        options.mouseClicked(x, y);
    }

    @Override
    protected void mouseReleased(int x, int y, int keyState) {
        if (state == State.CONNECTED) {
            slates.get(slateIndex).mouseReleased(x, y, keyState);
            options.mouseReleased(x, y, keyState);
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (state == State.CONNECTED) {
            slates.get(slateIndex).keyTyped(typedChar, keyCode);
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public boolean mouseOverLeft(int x, int y) {
        return x >= buttonPaddingX && x <= buttonPaddingX+buttonWidth && y >= this.height/2 - 20 && y <= this.height/2 + 20;
    }

    public boolean mouseOverRight(int x, int y) {
        return x >= this.width - buttonPaddingX - buttonWidth && x <= this.width - buttonPaddingX && y >= this.height/2 - 20 && y <= this.height/2 + 20;
    }

    private boolean needsLeft() {
        return slateIndex < slates.size()-1;
    }

    private boolean needsRight() {
        return slateIndex > 0;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
        if (state == State.FAILED) {
            options.addComponent(new TextButton("Offline mode", true) {
                @Override
                public void toggle() {
                    setState(State.CONNECTED);
                    postConnection();
                }
            });
        } else if (state == State.CONNECTED) {

        }
    }

    public void clearOptions() {
        options.clearComponents();
    }


}

enum State {
    IDLE, CONNECTED, LOADING, FAILED;
}
