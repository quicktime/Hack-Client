package at.tiam.bolt.command.datatype;

import at.tiam.bolt.Bolt;

/**
 * Created by quicktime on 5/26/17.
 */
public class ModuleDataType extends DataType {

    public ModuleDataType() { super("Mod"); }

    @Override
    public boolean isValid(String text) { return Bolt.getBolt().getModuleManager().getModuleByName(text) != null; }

    @Override
    public Object getValue(String text) { return Bolt.getBolt().getModuleManager().getModuleByName(text); }

    @Override
    public Object getDefault() { return null; }

    @Override
    public String getError() { return "The mod %s was not found"; }
}
