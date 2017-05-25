package at.tiam.bolt.gui;

import at.tiam.bolt.Bolt;
import at.tiam.bolt.module.Module;
import at.tiam.bolt.module.ModuleManager;
import at.tiam.bolt.gui.components.*;
import at.tiam.bolt.gui.components.buttons.LinkButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by quicktime on 5/24/17.
 */
public class ClickGui extends GuiScreen {

    public List<Window> windows = new CopyOnWriteArrayList<Window>();
    public boolean opened;

    public ClickGui(ModuleManager moduleManager) {
        initModuleWindows(moduleManager);
        initEnabledModulesWindows(moduleManager);
        initPluginsWindow(moduleManager);
        initColorsWindow(moduleManager);
        initFriendsWindow(moduleManager);
        initCameraWindows(moduleManager);
        moveWindows();
    }

    private void initModuleWindows(ModuleManager moduleManager) {
        for (Module.Category category : Module.Category.values()) {
            if (category != Module.Category.PLUGIN && category != Module.Category.NONE) {
                Window window;
                windows.add(window = new Window(category.getName(), moduleManager, 85));

                for (Module module : moduleManager.modules) {
                    if (module.getCategory() == category) {
                        if (module.hasOptions()) {
                            window.addComponent(new OptionsModuleButton(module)); // TODO: Create OptionsModuleButton class (at.tiam.bolt.gui.component.buttons)
                        } else {
                            window.addComponent(new ModuleButton); // TODO: Create ModuleButton class (at.tiam.bolt.gui.component.buttons)
                        }
                    }
                }
            }
        }
    }

    private void initEnabledModulesWindows(ModuleManager moduleManager) {
        Window enabledModules = new Window("Enabled", moduleManager, 85);
        enabledModules.addComponent(new EnabledModulesDisplay()); // TODO: Create EnabledModulesDisplay class (at.tiam.bolt.gui.components)
        windows.add(enabledModules);
    }

    private void initPluginsWindow(ModuleManager moduleManager) {
        Window plugins = new Window("Get Plugins", moduleManager, 120);
        windows.add(plugins);

        SelectorSytstem<SelectorButton> pluginSystem = new SelectorSystem<SelectorButton>(); // TODO: Create SelectorButton class (at.tiam.bolt.gui.components.buttons)
        plugins.addComponent(new PluginScrollPane(72, pluginSystem, false)); // TODO: Create PluginScrollPane class (at.tiam.bolt.gui.components.scrolling)
        plugins.addComponent(new PluginDownloadButton(pluginSystem)); // TODO: Create PluginDownloadButton class (at.tiam.bolt.gui.components.buttons)
    }

    private void initColorsWindow(ModuleManager moduleManager) {
        Window colors = new Window("Colors", moduleManager, 85);

        for (ColorType colorType : ColorType.values()) {
            colors.addComponent(new ColorPicker(colorType, this));
        }

        windows.add(colors);
    }

    private void initFriendsWindow(ModuleManager moduleManager) {
        Window enabledModules = new Window("Enabled", moduleManager, 85);
        enabledModules.addComponent(new EnabledModulesDisplay());
        windows.add(enabledModules);

    }

    private void initCameraWindows(ModuleManager moduleManager) {
        Window rearView = new Window("Rearview", moduleManager, 85);
        rearView.addComponent(new CameraDisplay(new RearviewCamera(rearView)));
        windows.add(rearView);

        Window arrowView = new Window("ArrowView", moduleManager, 85);
        arrowView.addComponent(new CameraDisplay(new ArrowCamera(arrowView)));
        windows.add(arrowView);

        Window periscope = new Window("Periscope", moduleManager, 85);
        periscope.addComponent(new CameraDisplay(new PeriscopeCamer(periscope)));
        windows.add(periscope);

        Window players = new Window("Players", moduleManager, 85);
        SelectorSystem<SelectorButton> playerSystem = new SelectorySystem<SelectorButton>();
        players.addComponent(new CameraDisplay(new PlayerCamer(players, playerSystem)));
        SpoilerButton spoilerButton = new SpoilerButton("Select Player");
        players.addComponent(spoilerButton);
        spoilerButton.addComponent(new PlayerEntityScrollPane(50, playerSystem));
        windows.add(players);
    }

    private void moveWindows() {
        int line = 0;
        for (Window window : windows) {
            window.posX = 3;
            window.posY = line++ * 20;
        }
    }

    public void onModuleManagerChange() {
        for (Window window : windows) {
            window.onModuleManagerChange();
        }
    }

    @Override
    protected void keyTyped(char c, int i) throws IOException {
        for (Window window : windows) {
            window.keyPress(c, i);
        }
        super.keyTyped(c, i);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (Window window : windows) {
            window.mouseClicked(mouseX, mouseY);
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        for (Window window : windows) {
            window.mouseReleased(mouseX, mouseY, state);
        }
    }

    public Window getWindowByName(String name) {
        for (Window window : windows) {
            if (window.title.equalsIgnoreCase(name)) {
                return window;
            }
        }
        return null;
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        if (!opened) {
            ScaledResolution scaledResolution = new ScaledResolution(Bolt.getBolt().getMc());

            Window popup = new WindowPopup(
                    "Welcome",
                    "Get help here:",
                    Bolt.getBolt().getModuleManager(),
                    250,
                    scaledResolution.getScaledWidth(),
                    scaledResolution.getScaledHeight());

            popup.addComponent(new LinkButton("Bolt Forums", "http://tiam.at/forums"));
            popup.addComponent(new TextDisplay("Type .help in chat to get a list of all commands"));
            popup.addComponent(new TextDisplay("Press Y to open the Bolt Hud"));

            windows.add(popup);

            opened = true;
        }
    }

    @Override
    public void onGuiClosed() {
        for (Window window : windows) {
            window.onGuiClosed();
        }
        Keyboard.enableRepeatEvents(false);
    }

    private void setDragging(Window dragging){

        for(Window win : windows){
            if(win != dragging){
                win.dragging = false;
            }
        }

        windows.remove(dragging);
        windows.add(windows.size(), dragging);
    }

    @Override
    public boolean doesGuiPauseGame() { return false; }
}
