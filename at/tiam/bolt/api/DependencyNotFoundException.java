package at.tiam.bolt.api;

/**
 * Created by quicktime on 5/25/17.
 */
public class DependencyNotFoundException extends Exception {

    private PluginDependency dependency;

    public DependencyNotFoundException(PluginDependency dependency) {
        super();
        this.dependency = dependency;
    }

    public PluginDependency getDependency() { return dependency; }
}
