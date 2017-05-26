package at.tiam.bolt.command.datatype;

/**
 * Created by quicktime on 5/26/17.
 */
public abstract class DataType<T> {

    private String name;

    public DataType(String name) { this.name = name; }
    public String getName() { return name; }
    public abstract boolean isValid(String text);
    public abstract T getValue(String text);
    public abstract T getDefault();
    public abstract String getError();
}
