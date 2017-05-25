package at.tiam.bolt.module;

import at.tiam.bolt.Bolt;
import at.tiam.bolt.api.PluginData;
import at.tiam.bolt.module.modules.Step;
import at.tiam.bolt.module.modules.*;
import at.tiam.bolt.event.events.EventDisabled;
import at.tiam.bolt.event.events.EventEnabled;
import at.tiam.bolt.util.ReflectionUtils;

import com.darkmagician6.eventapi.EventManager;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by quicktime on 5/22/17.
 */
public class ModuleManager {

    public static List<Module> modules = new CopyOnWriteArrayList<Module>();

    /**
     * Modules
     */

    public final Step step = new Step();
    public final Sprint sprint = new Sprint();

    public ModuleManager() {
        addClassesFromPackage("at.tiam.client.modules");

        for (Module m : modules) {
            initMod(m);
            EventManager.register(m);
        }
    }

    private void addClassesFromPackage(String packageName){

        for (Class<?> clazz : ReflectionUtils.getClasses(packageName)) {
            try {
                Object obj = clazz.newInstance();
                if (obj instanceof Module) {
                    modules.add((Module) obj);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initMod(Module m) {
        if (m.getClass().isAnnotationPresent(RegisterMod.class)) {
            RegisterMod details = m.getClass().getAnnotation(RegisterMod.class);
            m.setKeybind(details.defaultKey());
            m.setName(details.name());
            m.setDescription(details.desc());
            m.setCategory(details.category());
        } else {
            Bolt.getBolt().log("Error - RegisterMod class no found: " + m.getClass().getName());
        }
    }

    public void removePluginModules(PluginData data) {
        Iterator<Module> iterator = modules.iterator();

        while (iterator.hasNext()) {
            Module module = iterator.next();

            if (module instanceof PluginModule && ((PluginModule) module).getPluginData() == data) {
                Bolt.getBolt().getModuleManager().modules.remove(module);
            }
        }
    }

    public Module getModuleByName(String name) {
        for (Module m : modules) {
            if (m.getName().equalsIgnoreCase(name)) {
                return m;
            }
        }
        return null;
    }

    public Module getModuleByClass(Class<? extends Module> clazz) {
        for (Module module : modules) {
            if (module.getClass().equals(clazz)) {
                return module;
            }
        }
        return null;
    }

    public void toggle(Module m) {
        if (m.isEnabled()) {
            m.setEnabled(false);
            EventManager.call(new EventDisabled(m));
            m.onDisable();
        } else {
            m.setEnabled(true);
            EventManager.call(new EventEnabled(m));
            m.onEnable();
        }

        Bolt.getBolt().getClickGui().onModuleManagerChange();
    }

}
