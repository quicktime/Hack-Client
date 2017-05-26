package at.tiam.bolt.command;

import at.tiam.bolt.command.datatype.DataType;

/**
 * Created by quicktime on 5/26/17.
 */
public class Argument {

    private String name;
    private boolean optional;
    private DataType type;

    public Argument(String name, boolean optional, DataType type) {
        this.name = name;
        this.optional = optional;
        this.type = type;
    }

    public String getName() { return name; }
    public boolean isOptional() { return optional; }
    public DataType getType() { return type; }
}
