package at.tiam.bolt.gui;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import at.tiam.bolt.util.GuiUtils;

/**
 * Created by quicktime on 5/25/17.
 */
public class Canvas implements IRectangle {
    private IScalerPosition pos;
    private int width, height;
    protected List<AbstractComponent> components = new CopyOnWriteArrayList<AbstractComponent>();

    public Canvas(IScalerPosition pos, int width) {
        this.pos = pos;
        this.width = width;
    }

    public void draw(int x, int y){

        int posX = pos.getX();
        int posY = pos.getY();

        GuiUtils.drawRect(posX, posY, posX+width, posY+height, 0x5F5F5F5F);

        for(AbstractComponent comp : components){
            comp.draw(x, y);
        }
    }

    public void mouseClicked(int x, int y) {

        for (AbstractComponent comp : components){
            comp.mouseClicked(x, y);
        }
    }

    public void keyPress(char c, int key) {

        for(AbstractComponent comp : components){
            comp.keyPress(key, c);
        }
    }

    public void mouseReleased(int x, int y, int state) {

        for(AbstractComponent comp : components){
            comp.mouseReleased(x, y, state);
        }
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void addComponent(AbstractComponent c){
        this.components.add(c);
        c.setRectangle(this);
        c.init();
        updateSize();
    }

    public void clearComponents(){
        this.components.clear();
        height = 0;
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
        return pos.getX();
    }

    @Override
    public int getRectY() {
        return pos.getY();
    }
}
