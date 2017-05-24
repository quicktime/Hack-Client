package at.tiam.bolt.module;

import at.tiam.bolt.Bolt;
import at.tiam.bolt.module.modules.Step;
import at.tiam.bolt.module.modules.*;
import at.tiam.bolt.event.events.EventDisabled;
import at.tiam.bolt.event.events.EventEnabled;
import at.tiam.bolt.util.ReflectionUtils;

import com.darkmagician6.eventapi.EventManager;

import java.util.Collection;
import java.util.List;
import java.lang.reflect.Field;
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
            m.setBind(details.defaultKey());
            m.setName(details.name());
            m.setDescription(details.desc());
            m.setCategory(details.category());
        } else {
            Bolt.getBolt().log("Error - RegisterMod class no found: " + m.getClass().getName());
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
        if (m.getState()) {
            m.setState(true);
            EventManager.call(new EventDisabled(m));
            m.onDisable();
        } else {
            m.setState(false);
            EventManager.call(new EventEnabled(m));
            m.onEnable();
        }

        Bolt.getBolt().getClickGui().onModuleManagerChange();
    }

}
