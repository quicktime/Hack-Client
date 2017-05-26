package at.tiam.bolt.command;

import at.tiam.bolt.module.Module;

import java.util.Map;

/**
 * Created by quicktime on 5/26/17.
 */
public class Arguments {

    private CommandManager commandManager;
    private Map<String, Object> values;

    public Arguments(CommandManager commandManager, Map<String, Object> values) {
        this.commandManager = commandManager;
        this.values = values;
    }

    public String get(String name) { return (String)values.get(name); }
    public int getInteger(String name) { return (Integer)values.get(name); }
    public boolean getBoolean(String name) { return (Boolean)values.get(name); }
    public Module module(String name) { return (Module)values.get(name); }

}
