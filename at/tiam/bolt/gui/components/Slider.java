package at.tiam.bolt.gui.components;

import at.tiam.bolt.Bolt;
import at.tiam.bolt.gui.AbstractComponent;
import at.tiam.bolt.module.value.SliderValue;
import at.tiam.bolt.util.ClientUtils;
import at.tiam.bolt.util.GuiUtils;

/**
 * Created by quicktime on 5/26/17.
 */
public class Slider extends AbstractComponent {
    private static int BAR_WIDTH = 10;

    private SliderValue val;
    private int slide, mouseXOffset;
    private boolean dragging;

    public Slider(SliderValue val) {
        this.val = val;
    }

    @Override
    public void draw(int x, int y) {

        if(dragging){

            slide = x + mouseXOffset;

            double factor = (slide - getX())/(double)(rect.getWidth()-BAR_WIDTH);
            double range = val.getUpperBound() - val.getLowerBound();
            double addition = range*factor;

            val.setValue(addition+val.getLowerBound());
        }

        if(slide > getX()+rect.getWidth()-BAR_WIDTH){
            slide = getX()+rect.getWidth()-BAR_WIDTH;
        }

        if(slide < getX()){
            slide = getX();
        }

        //GuiUtils.drawRect(cx, cy, cx+panel.getWidth(), cy+getHeight(), 0x2FDFDFDF);
        GuiUtils.drawRect(slide, getY(), slide+BAR_WIDTH, getY()+getHeight(), 0x7F9F9F9F);

        String displayString = val.getName()+": "+(val.isRounded()? val.getValue().intValue(): ClientUtils.digitsThree.format(val.getValue()));

        Bolt.getBolt().getFontRenderer().drawString(displayString, getX() + rect.getWidth()/2 - Bolt.getBolt().getFontRenderer().getStringWidth(displayString)/2, getY() + 2, 0xFFFFFFFF);
    }

    @Override
    public void mouseClicked(int x, int y) {
        if(mouseOverSlider(x, y, getX(), getY())){
            dragging = true;
            mouseXOffset = slide - x;
        }
    }

    @Override
    public void onGuiClosed() {
        dragging = false;
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        dragging = false;
    }

    protected boolean mouseOverSlider(int x, int y, int cx, int cy){
        return x > slide && x < slide+BAR_WIDTH && y > cy && y < cy+getHeight();
    }

    @Override
    public int getHeight() {
        return 12;
    }
}
