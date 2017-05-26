package at.tiam.bolt.gui;

import at.tiam.bolt.module.ModuleManager;
import at.tiam.bolt.Bolt;

import java.util.List;
import java.util.ArrayList;

import at.tiam.bolt.util.GuiUtils;
import net.minecraft.client.audio.Sound;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import org.lwjgl.opengl.GL11;

/**
 * Created by quicktime on 5/24/17.
 */
public class Window implements IRectangle {

    public String title;
    public int posX, posY, mouseXOffset, mouseYOffset;
    public boolean dragging, extended, pinned;
    protected List<AbstractComponent> components = new ArrayList<AbstractComponent>();
    private ModuleManager moduleManager;

    public static int TITLE_COMPONENT_SPACE = 2;
    public static int TITLE_SIZE = 12;

    public int minWidth;
    public int width;
    public int height;

    public Window(String title, ModuleManager moduleManager, int width){
        this.title = title;
        this.width = width;
        this.minWidth = width;
        this.moduleManager = moduleManager;
    }

    public void renderWindow(int x, int y, boolean titleVisible) {

        if(dragging){
            posX = x+mouseXOffset;
            posY = y+mouseYOffset;
        }

        if(titleVisible){

            drawHeader(posX, posY, posX+width, posY+TITLE_SIZE);

            Bolt.getBolt().getFontRenderer().drawStringWithShadow(title,
                    posX + 3,
                    posY + TITLE_SIZE/2 - Bolt.getBolt().getFontRenderer().FONT_HEIGHT/2,
                    ColorType.TITLE_TEXT.getColor());

            //Toggle extension
            if(hasExtensionButton()){
                drawButton(posX+width-10, posY+2, posX+width-2, posY+TITLE_SIZE-2, extended);
            }

            //Toggle pinned
            if(hasPinnedButton()){
                drawButton(posX+width-22, posY+2, posX+width-14, posY+TITLE_SIZE-2, pinned);
            }
        }

        if(extended){

            drawBodyRect(posX, posY+TITLE_SIZE+TITLE_COMPONENT_SPACE, posX+width, posY+TITLE_SIZE+TITLE_COMPONENT_SPACE+height, titleVisible);

            for(AbstractComponent c : components){
                c.draw(x, y);
            }
        }
    }

    protected void drawButton(int x, int y, int x1, int y1, boolean enabled) {
        GuiUtils.drawRect(x, y, x1, y1, enabled? 0x9FFFFFFF:0x4FFFFFFF);
    }

    protected void drawHeader(int x, int y, int x1, int y1){
        GuiUtils.drawTopNodusRect(posX, posY, posX+width, posY+TITLE_SIZE);
    }

    protected void drawBodyRect(int x, int y, int x1, int y1, boolean titleVisible){
        GuiUtils.drawBottomNodusRect(x, y, x1, y1, titleVisible);
    }

    public void mouseClicked(int x, int y) {

        if(hasExtensionButton() && mouseOverToggleExtension(x, y)){
            onExtensionToggle();
        }else if(hasPinnedButton() && mouseOverTogglePinned(x, y)){
            onPinnedToggle();
        }else if(mouseOverTitle(x, y)){

            dragging = true;

            mouseXOffset = posX - x;
            mouseYOffset = posY - y;

            Bolt.getBolt().clickGui.setDragging(this);
        }

        if(extended){

            for(AbstractComponent c : components){
                c.mouseClicked(x, y);
            }
        }
    }

    public void onGuiClosed(){

        this.dragging = false;

        for(AbstractComponent comp : components){
            comp.onGuiClosed();
        }
    }

    public void onModuleManagerChange(){

        for(AbstractComponent comp : components){
            comp.onModuleManagerChange();
        }
    }

    public void keyPress(char c, int key) {

        for(AbstractComponent comp : components){
            comp.keyPress(key, c);
        }
    }

    public boolean hasExtensionButton(){
        return true;
    }

    public boolean hasPinnedButton(){
        return true;
    }

    public void onExtensionToggle(){
        extended = !extended;
        doClickSound();
    }

    public void onPinnedToggle(){
        pinned = !pinned;
        doClickSound();
    }

    protected void doClickSound(){
        Bolt.getBolt().getMc().player.playSound(SoundEvents.UI_BUTTON_CLICK, 1, 1);
    }

    protected void mouseReleased(int mouseX, int mouseY, int state) {
        dragging = false;
        if(extended){
            for(AbstractComponent c : components){
                c.mouseReleased(mouseX, mouseY, state);
            }
        }
    }

    public boolean mouseOverTitle(int x, int y){
        return x >= posX && x <= posX+width && y >= posY && y <= posY+TITLE_SIZE+TITLE_COMPONENT_SPACE;
    }

    public boolean mouseOverToggleExtension(int x, int y){
        return x >= posX+width-10 && x <= posX+width-2 && y >= posY+2 && y <= posY+TITLE_SIZE-2;
    }

    public boolean mouseOverTogglePinned(int x, int y){
        return x >= posX+width-22 && x <= posX+width-14 && y >= posY+2 && y <= posY+TITLE_SIZE-2;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public void setModuleManager(ModuleManager moduleManager) {
        this.moduleManager = moduleManager;
    }

    public void addComponent(AbstractComponent c){
        this.components.add(c);
        c.setRectangle(this);
        c.init();
        updateSize();
    }

    public void addComponentRaw(AbstractComponent c){
        this.components.add(c);
        c.setRectangle(this);
        c.init();
    }

    @Override
    public void updateSize(){

        this.height = 0;
        this.width = minWidth;

        for(AbstractComponent component : components){

            component.setY(height+TITLE_SIZE);
            height += component.getHeight();

            if(component.getWidth() > this.width){
                this.width = component.getWidth();
            }
        }
    }

    @Override
    public int getRectX() {
        return posX;
    }

    @Override
    public int getRectY() {
        return posY + TITLE_COMPONENT_SPACE;
    }
}
