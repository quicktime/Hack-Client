package at.tiam.bolt.command.datatype;

import at.tiam.bolt.util.ClientUtils;

/**
 * Created by quicktime on 5/26/17.
 */
public class IntegerDataType extends DataType<Integer> {

    public IntegerDataType() { super("Integer"); }

    @Override
    public boolean isValid(String text) { return ClientUtils.isInteger(text); }

    @Override
    public Integer getValue(String text) { return Integer.parseInt(text); }

    @Override
    public Integer getDefault() { return 0; }

    @Override
    public String getError() { return "%s should be a number with no decimal places"; }
}
