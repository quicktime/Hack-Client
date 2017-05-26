package at.tiam.bolt.gui.components.scrolling;

import at.tiam.bolt.gui.AbstractComponent;
import at.tiam.bolt.gui.IRectangle;
import at.tiam.bolt.util.GuiUtils;

import java.util.ArrayList;
import java.util.List;

import at.tiam.bolt.util.Scissor;
import org.lwjgl.opengl.GL11;

/**
 * Created by quicktime on 5/26/17.
 */
public class ScrollPane extends AbstractComponent implements IRectangle {

    protected List<AbstractComponent> components = new ArrayList<AbstractComponent>();
    protected int height, viewportHeight, dragged, mouseYOffset;
    private boolean dragging;

    private static int BAR_WIDTH = 4;

    public ScrollPane(int viewportHeight){
        this.viewportHeight = viewportHeight;
    }

    @Override
    public void draw(int x, int y) {

        int barHeight = getBarHeight();

        if(dragging){

            dragged = y - getY() + mouseYOffset;

            if(dragged > viewportHeight-barHeight){
                dragged = viewportHeight-barHeight;
            }

            if(dragged < 0){
                dragged = 0;
            }
        }

        GuiUtils.drawRect(getX() + rect.getWidth() - BAR_WIDTH - 1, getY(), getX() + rect.getWidth(), getY() + viewportHeight, 0x1FCFCFCF);

        GuiUtils.drawRect(getX() + rect.getWidth() - BAR_WIDTH - 1, getY() + dragged, getX() + rect.getWidth(), getY() + dragged + barHeight, 0x4FFFFFFF);

        int drag = getScrollHeight(barHeight);

        Scissor.enableScissoring();
        Scissor.scissor(getX(), getY(), rect.getWidth(), viewportHeight);

        GL11.glTranslatef(0, -drag, 0);

        if(isWithinScrollPane(x, y, getX(), getY())){
            for(AbstractComponent c : components){
                c.draw(x, y + drag);
            }
        }else{
            for(AbstractComponent c : components){
                c.draw(-999, -999);
            }
        }

        GL11.glTranslatef(0, drag, 0);

        Scissor.disableScissoring();
    }

    public int getScrollHeight(int barHeight){
        return (int) ((float)(dragged/(float)(viewportHeight-barHeight)) * (height-viewportHeight));
    }

    public boolean mouseOverBar(int x, int y, int cx, int cy, int barHeight){
        return x >= cx + rect.getWidth() - BAR_WIDTH - 1 && x < cx + rect.getWidth() && y > cy + dragged && y <= cy + dragged + barHeight;
    }

    public boolean mouseOverBarArea(int x, int y, int cx, int cy){
        return x >= cx + rect.getWidth() - BAR_WIDTH - 1 && x <= cx + rect.getWidth() && y >= cy && y <= cy + viewportHeight;
    }

    private boolean isWithinScrollPane(int x, int y, int cx, int cy){
        return x > cx && x < cx+rect.getWidth() - BAR_WIDTH - 1 && y > cy && y < cy + viewportHeight;
    }

    @Override
    public void mouseClicked(int x, int y) {

        int barHeight = getBarHeight();

        if(mouseOverBar(x, y, getX(), getY(), barHeight)){
            dragging = true;

            mouseYOffset = (getY() + dragged) - y;
        }else{

            int h = 0;
            int drag = getScrollHeight(barHeight);

            if(isWithinScrollPane(x, y, getX(), getY())){
                for(AbstractComponent c : components){
                    c.mouseClicked(x, y + drag);
                    h += c.getHeight();
                }
            }else{
                for(AbstractComponent c : components){
                    c.mouseClicked(-999, -999);
                    h += c.getHeight();
                }
            }
        }
    }

    private int getBarHeight(){

        if(components.size() == 0){
            return viewportHeight;
        }

        int h = 0;

        for(AbstractComponent comp : components){
            h += comp.getHeight();
        }

        float contentRatio = viewportHeight / h;

        int barHeight = (int) (viewportHeight * contentRatio);

        if(barHeight < 20){
            barHeight = 20;
        }

        if(barHeight > viewportHeight){
            barHeight = viewportHeight;
        }

        return barHeight;
    }

    @Override
    public void keyPress(int key, char c) {
        for(AbstractComponent comp : components){
            comp.keyPress(key, c);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        dragging = false;
    }

    @Override
    public int getHeight() {
        return viewportHeight;
    }

    @Override
    public int getWidth() {
        return rect.getWidth() - BAR_WIDTH - 1;
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

        height = 0;

        for(AbstractComponent component : components){
            component.setY(height);
            height += component.getHeight();
        }
    }

    @Override
    public int getRectX() {
        return getX();
    }

    @Override
    public int getRectY() {
        return getY();
    }

}
