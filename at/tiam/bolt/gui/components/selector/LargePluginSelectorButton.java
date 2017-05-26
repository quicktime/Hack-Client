package at.tiam.bolt.gui.components.selector;

import java.io.File;
import java.io.IOException;

import at.tiam.bolt.Bolt;
import at.tiam.bolt.gui.ColorType;
import at.tiam.bolt.util.GuiUtils;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import at.tiam.bolt.api.PluginData;

/**
 * Created by quicktime on 5/26/17.
 */
public class LargePluginSelectorButton extends SelectorButton {

    private PluginData data;
    private Texture icon;

    public LargePluginSelectorButton(PluginData data, SelectorSystem system) {
        super(data.getName(), system);
        this.data = data;

        File iconFile = new File(Bolt.getBolt().getPluginManager().icons, data.getIconName());

        try {
            this.icon = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(iconFile.getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(int x, int y) {

        boolean mouseover = mouseOverButton(x, y, getX(), getY());

        if(mouseover){
            GuiUtils.drawRect(getX(), getY(), getX()+rect.getWidth(), getY()+getHeight(), 0x2FFFFFFF);
        }

        GL11.glColor4f(1, 1, 1, 1);
        icon.bind();
        GuiUtils.drawCorrectTexturedModalRect(getX() + 2, getY() + 2, getX() + 24, getY() + 24);

        GuiUtils.rebindFontRenderer();

        if(isEnabled()){
            Bolt.getBolt().getFontRenderer().drawStringWithShadow(getText(), getX()+29, getY()+2, mouseover? ColorType.HIGHLIGHT.getModifiedColor():ColorType.HIGHLIGHT.getColor());
            Bolt.getBolt().getFontRenderer().drawStringWithShadow(data.getDescription(), getX()+29, getY()+14, 0xFFCFCFCF);
        }else{
            Bolt.getBolt().getFontRenderer().drawStringWithShadow(getText(), getX()+29, getY()+2, mouseover? ColorType.TEXT.getModifiedColor():ColorType.TEXT.getColor());
            Bolt.getBolt().getFontRenderer().drawStringWithShadow(data.getDescription(), getX()+29, getY()+14, 0xFFCFCFCF);
        }

    }

    @Override
    public String getText() {

        if(Bolt.getBolt().getPluginManager().pluginEnabled(data)){
            return super.getText() + " (On)";
        }else{
            return super.getText();
        }
    }

    @Override
    public int getHeight() {
        return 26;
    }
}
