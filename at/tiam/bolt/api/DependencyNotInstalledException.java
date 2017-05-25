package at.tiam.bolt.api;

/**
 * Created by quicktime on 5/25/17.
 */
public class DependencyNotInstalledException extends Exception {

    private PluginData pluginData;
    private PluginDependency dependency;

    public DependencyNotInstalledException(PluginData pluginData, PluginDependency dependency) {
        super();
        this.pluginData = pluginData;
        this.dependency = dependency;
    }

    public PluginData getPluginData() {
        return pluginData;
    }

    public PluginDependency getDependency() { return dependency; }
}
