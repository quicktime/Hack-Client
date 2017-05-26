package at.tiam.bolt.gui.components.buttons;

import at.tiam.bolt.Bolt;
import at.tiam.bolt.module.Module;

/**
 * Created by quicktime on 5/26/17.
 */
public class ModuleButton extends Button {

    protected Module module;

    public ModuleButton(Module module) {
        super();
        this.module = module;
    }

    @Override
    public boolean isEnabled() {
        return module.isEnabled();
    }

    @Override
    public void toggle() {
        Bolt.getBolt().getModuleManager().toggle(module);
    }

    @Override
    public String getText() {
        return module.getName();
    }

    @Override
    public boolean isCentered() {
        return false;
    }

    public Module getMod() {
        return module;
    }

}
