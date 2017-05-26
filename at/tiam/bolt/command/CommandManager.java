package at.tiam.bolt.command;

import at.tiam.bolt.command.datatype.BooleanDataType;
import at.tiam.bolt.command.datatype.IntegerDataType;
import at.tiam.bolt.command.datatype.ModuleDataType;
import at.tiam.bolt.command.datatype.StringDataType;
import at.tiam.bolt.util.Chars;
import at.tiam.bolt.util.ReflectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by quicktime on 5/24/17.
 */
public class CommandManager {

    public List<Command> commandList;
    public static String PREFIX = ".";

    public BooleanDataType BOOLEAN = new BooleanDataType();
    public IntegerDataType INTEGER = new IntegerDataType();
    public StringDataType STRING = new StringDataType();
    public StringDataType MULTISPACE_STRING = new StringDataType();
    public ModuleDataType MODULE = new ModuleDataType();

    public CommandManager() {
        commandList = new ArrayList<Command>();
        this.addClassesFromPackage("at.tiam.bolt.command.commands");
    }

    public void addClassesFromPackage(String packageName) {
        for (Class<?> clazz : ReflectionUtils.getClasses(packageName)) {
            try {
                Object obj = clazz.newInstance();
                if (obj instanceof Command) {
                    commandList.add((Command) obj);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<Command> getCommandList() { return commandList; }

    public String[] run(String[] parts) {
        Argument[] arguments = null;
        Map<String, Object> values = new HashMap<String, Object>();

        Command command = getCommandByName(parts[0]);

        if (command == null) {
            return new String[] { "Command not found. Type " + Chars.QUOTE + PREFIX + "help" + Chars.QUOTE + " for a list of all commands" };
        } else {
            arguments = command.getArguments(this);
        }

        if (arguments != null) {
            if (parts.length - 1 < getRequiredArguments(arguments).size()) {
                return new String[] { "Not enough arguments specified!",
                        "Should be: " + command.getSummary(this)};
            }

            for (int i = 0; i < arguments.length; i++) {
                Argument argument = arguments[i];
                if (i < parts.length - 1) {
                    String input = null;

                    if (argument.getType() == MULTISPACE_STRING) {
                        input = getStringAfterIndex(parts, i + 1);
                    } else {
                        input = parts[i + 1];
                    }

                    if (argument.getType().isValid(input)) {
                        values.put(argument.getName(), argument.getType().getValue(input));
                    } else {
                        return new String[] {
                                String.format("Error: " + argument.getType().getError(), Chars.QUOTE + input + Chars.QUOTE)
                        };
                    }
                } else {
                    values.put(argument.getName(), argument.getType().getDefault());
                }
            }
        }
        command.runCommand(this, new Arguments(this, values));
        return null;
    }

    private String getStringAfterIndex(String[] array, int index) {
        StringBuilder builder = new StringBuilder();

        for (int i = index; i < array.length; i++) {
            builder.append(array[i]);
            builder.append(" ");
        }
        return builder.toString();
    }

    private List<Argument> getRequiredArguments(Argument[] arguments) {
        List<Argument> required = new ArrayList<Argument>();

        for (Argument argument : arguments) {
            if (argument.isOptional()) {
                return required;
            }
            required.add(argument);
        }
        return required;
    }

    private Command getCommandByName(String name) {
        for (Command command : commandList) {
            if (command.getCallName().equalsIgnoreCase(name)) {
                return command;
            }
        }
        return  null;
    }

}
