package at.tiam.bolt.command.datatype;

/**
 * Created by quicktime on 5/26/17.
 */
public class BooleanDataType extends DataType {

    public BooleanDataType() { super("Boolean"); }

    @Override
    public boolean isValid(String text) { return text.equalsIgnoreCase("true") || text.equalsIgnoreCase("false"); }

    @Override
    public Boolean getValue(String text) { return Boolean.parseBoolean(text); }

    @Override
    public Boolean getDefault() { return false; }

    @Override
    public String getError() { return "%s should be a boolean"; }
}
