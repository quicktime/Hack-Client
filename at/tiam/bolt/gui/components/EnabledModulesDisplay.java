package at.tiam.bolt.gui.components;

import at.tiam.bolt.Bolt;
import at.tiam.bolt.gui.AbstractComponent;
import at.tiam.bolt.gui.ColorType;
import at.tiam.bolt.module.Module;

/**
 * Created by quicktime on 5/26/17.
 */
public class EnabledModulesDisplay extends AbstractComponent {

    @Override
    public int getHeight() {
        int enabledModules = 0;

        for (Module module : Bolt.getBolt().getModuleManager().modules) {
            if (module.isEnabled()) {
                enabledModules++;
            }
        }
        return enabledModules * 12;
    }

    @Override
    public void draw(int x, int y) {
        int enabledModules = 0;

        for (Module module : Bolt.getBolt().getModuleManager().modules) {
            if (module.isEnabled()) {
                Bolt.getBolt().getFontRenderer().drawStringWithShadow(module.getName(), getX() + 3, getY() + enabledModules * 12 + 2, ColorType.TEXT.getColor());
                enabledModules++;
            }
        }
    }

    @Override
    public void onModuleManagerChange() { rect.updateSize(); }
}
