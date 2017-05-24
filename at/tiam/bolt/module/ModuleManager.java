package at.tiam.bolt.module;

import at.tiam.bolt.module.modules.Step;

import java.util.Collection;
import java.util.TreeMap;
import java.lang.reflect.Field;

/**
 * Created by quicktime on 5/22/17.
 */
public class ModuleManager {

    private final TreeMap<String, Module> modules = new TreeMap<>();

    /**
     * Modules
     */

    public final Step step = new Step();

    public ModuleManager() {
        try {
            for (Field field : ModuleManager.class.getFields()) {
                Module module = (Module) field.get(this);
                modules.put(module.getName(), module);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Collection<Module> getAllModules() {
        return modules.values();
    }

    public Module getModuleByName(String name) {
        return modules.get(name);
    }

    public Module getModuleByClass(Class<? extends Module> clazz) {
        for (Module module : getAllModules()) {
            if (module.getClass() == clazz) {
                return module;
            }
        }
        return null;
    }

}
