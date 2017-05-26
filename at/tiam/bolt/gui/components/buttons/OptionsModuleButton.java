package at.tiam.bolt.gui.components.buttons;

import at.tiam.bolt.Bolt;
import at.tiam.bolt.gui.ColorType;
import at.tiam.bolt.gui.WindowSubPanel;
import at.tiam.bolt.module.Module;
import at.tiam.bolt.module.value.AbstractValue;
import at.tiam.bolt.util.GuiUtils;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;

/**
 * Created by quicktime on 5/26/17.
 */
public class OptionsModuleButton extends ModuleButton {

    private boolean extended;
    private WindowSubPanel panel;

    public OptionsModuleButton(Module module) {
        super(module);
    }

    @Override
    public void init() {

        panel = new WindowSubPanel(rect, this, 12);

        for(AbstractValue option : module.getOptions()){
            panel.addComponent(option.getComponent(panel));
        }
    }

    @Override
    public void draw(int x, int y) {

        if (module.isEnabled()) {
            Bolt.getBolt().getFontRenderer().drawStringWithShadow(getText(),
                    getX()+3,
                    getY()+2,
                    mouseOverButton(x, y, getX(), getY())? ColorType.HIGHLIGHT.getModifiedColor():ColorType.HIGHLIGHT.getColor());
        }else {
            Bolt.getBolt().getFontRenderer().drawStringWithShadow(getText(),
                    getX()+3,
                    getY()+2,
                    mouseOverButton(x, y, getX(), getY())? ColorType.TEXT.getModifiedColor():ColorType.TEXT.getColor());
        }

        GuiUtils.drawRect(getX()+rect.getWidth()-10, getY()+2, getX()+rect.getWidth()-2, getY()+10, extended? 0x4FFFFFFF:0x2FFFFFFF);

        if(extended){
            panel.draw(x, y);
        }
    }

    @Override
    public void mouseClicked(int x, int y) {

        if(mouseOverExtendButton(x, y, getX(), getY())){
            extended = !extended;
            Bolt.getBolt().getMc().player.playSound(SoundEvents.UI_BUTTON_CLICK, 1, 1);
            rect.updateSize();
        }else{
            super.mouseClicked(x, y);
        }

        if(extended){
            panel.mouseClicked(x, y);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        if(extended){
            panel.mouseReleased(mouseX, mouseY, state);
        }
    }

    public boolean mouseOverExtendButton(int x, int y, int cx, int cy){
        return x >= cx+rect.getWidth()-10 && x <= cx+rect.getWidth()-2 && y >= cy+2 && y <= cy+10;
    }

    @Override
    public boolean mouseOverButton(int x, int y, int cx, int cy){
        return x > cx && x < cx+rect.getWidth() && y > cy && y < cy+12;
    }

    @Override
    public int getHeight() {

        if(extended){
            return super.getHeight() + panel.getHeight() + 1;
        }else{
            return super.getHeight();
        }
    }
}
