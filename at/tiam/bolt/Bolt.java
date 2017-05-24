package at.tiam.bolt;

import at.tiam.bolt.event.EventManager;
import at.tiam.bolt.event.EventTarget;
import at.tiam.bolt.event.events.EventKeyboard;
import at.tiam.bolt.module.Module;
import at.tiam.bolt.module.ModuleManager;
import at.tiam.bolt.ttf.FontManager;
import at.tiam.bolt.ui.Hud;
import org.lwjgl.input.Keyboard;

/**
 * Created by quicktime on 5/22/17.
 */
public class Bolt {

    public static final Bolt INSTANCE = new Bolt();

    public final String NAME = "Bolt";

    public final String VERSION = "inDev 0.1";

    public final String MC_VERSION = "1.11.2";

    public final ModuleManager MODULE_MANAGER = new ModuleManager();

    public final FontManager FONT_MANAGER = new FontManager();

    public final Hud HUD = new Hud();

    public void startClient() {
        EventManager.register(this);
        FONT_MANAGER.loadFonts();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> endClient()));
    }

    public void endClient() {

    }

    @EventTarget
    private void eventKeyboard(EventKeyboard eventKeyboard) {
        for (Module module : Bolt.INSTANCE.MODULE_MANAGER.getAllModules()) {
            if (Keyboard.getEventKey() == module.getBind()) {
                module.toggle();
            }
        }
    }

}
