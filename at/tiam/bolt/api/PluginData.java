package at.tiam.bolt.api;

/**
 * Created by quicktime on 5/24/17.
 */
public class PluginData {

    private String name, description, filePath, fileName, iconPath;
    private int version;
    private boolean wasEnabled, privatePlugin;

    public PluginData(String name, String description, String filePath, String fileName, String iconPath, int version, boolean wasEnabled, boolean privatePlugin) {
        this.name = name;
        this.description = description;
        this.filePath = filePath;
        this.fileName = fileName;
        this.version = version;
        this.wasEnabled = wasEnabled;
        this.privatePlugin = privatePlugin;
        this.iconPath = iconPath;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public int getVersion() {
        return version;
    }

    public final String getJarName(){
        return this.fileName + ".jar";
    }

    public final String getIconName(){
        return this.fileName + "_icon.png";
    }

    public boolean getWasEnabled() {
        return wasEnabled;
    }

    public boolean isPrivatePlugin() {
        return privatePlugin;
    }

    public String getPluginIdentifier(){
        return name + "/" + version;
    }

    public String getIconPath() {
        return iconPath;
    }
}
