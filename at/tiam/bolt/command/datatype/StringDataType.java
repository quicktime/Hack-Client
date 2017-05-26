package at.tiam.bolt.command.datatype;

import com.google.common.base.Strings;

/**
 * Created by quicktime on 5/26/17.
 */
public class StringDataType extends DataType {

    public StringDataType() { super("String"); }

    @Override
    public boolean isValid(String text) { return true; }

    @Override
    public Object getValue(String text) { return text; }

    @Override
    public Object getDefault() { return ""; }

    @Override
    public String getError() { return "%s should be a string of characters"; }
}
