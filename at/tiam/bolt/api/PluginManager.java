package at.tiam.bolt.api;

import at.tiam.bolt.Bolt;
import at.tiam.bolt.gui.Window;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import at.tiam.bolt.module.Module;
import at.tiam.bolt.util.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.darkmagician6.eventapi.EventManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import sun.plugin2.main.server.Plugin;

/**
 * Handles all plugin data and plugin loading
 * @author 10askinsw
 * Created by quicktime on 5/24/17.
 */
public class PluginManager {
    public File plugins = new File(Bolt.getFullDir(), "plugins");
    public File icons = new File(plugins, "icons");

    public List<PluginData> pluginData = new ArrayList<PluginData>();
    private Queue<PluginData> downloadQueue = new ConcurrentLinkedQueue<PluginData>();
    public Map<PluginData, Window> pluginDataWindowMap = new HashMap<PluginData, Window>();

    private Thread downloadThread;

    /**
     * Loads the plugins in the /plugins/ folder at startup
     * @throws ClassNotFoundException
     * @throws MalformedURLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public void load() throws ClassNotFoundException, MalformedURLException, InstantiationException, IllegalAccessException {
        // Create the plugins directory
        if (!plugins.exists()) {
            plugins.mkdir();
        }

        // Create the icons directory
        if (!icons.exists()) {
            icons.mkdir();
        }

        // Get plugins from the website
        try {
            Document document = Jsoup.connect("http://tiam.at/mods/list.php").userAgent(ClientUtils.USER_AGENT).get();

            JsonArray arr = new JsonParser().parse(document.text()).getAsJsonArray();

            for (JsonElement el : arr) {
                JsonObject jsonObject = el.getAsJsonObject();
                pluginData.add(new PluginData(jsonObject.get("name").getAsString(), jsonObject.get("description").getAsString(), jsonObject.get("file").getAsString(), jsonObject.get("filename").getAsString(), jsonObject.get("icon").getAsString(), jsonObject.get("version").getAsInt(), false, false));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Downloads any plugins we don't have yet
        for (PluginData available : pluginData) {
            File pluginFile = new File(plugins, available.getJarName());

            if (!pluginFile.exists()) {
                this.downloadPlugin(available);
            }
        }
    }

    /**
     * Finds the Plugin object with a particular name
     * @param name
     * @return the data of the plugin with specified name
     */
    public PluginData getPluginDataByName(String name) {
        for (PluginData data : pluginData) {
            if (data.getName().equals(name)) {
                return data;
            }
        }
        return null;
    }

    /**
     * Finds the Plugin object which is for a certain file
     * @param jar
     * @return the data of the plugin from the specified jar
     */
    public PluginData getPluginDataFromFile(File jar) {
        String name = ClientUtils.stripExtension(jar.getName());

        for (PluginData data : pluginData) {
            if (data.getName().equals(name)) {
                return data;
            }
        }
        return null;
    }

    /**
     * Checks if a plugin is enabled
     * @param plugin
     * @return boolean
     */
    public boolean pluginEnabled(PluginData plugin) {
        return pluginDataWindowMap.containsKey(plugin);
    }

    /** Initialises a plugin from it's file and then adds the plugin to the mod system and window system
     * @param plugin
     * @throws MalformedURLException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws DependencyNotInstalledException
     * @throws DependencyNotFoundException
     */
    public void addPlugin(PluginData plugin) throws MalformedURLException, ClassNotFoundException, IllegalAccessException, IllegalAccessException, DepencyNotInstalledException, DependencyNoFoundException {
        File jar = new File(plugins, plugin.getJarName());

        Bolt.getBolt().log("ADDING PLUGIN: " + jar.getPath());

        if (jar.isDirectory()) {
            return;
        }

        if (isValid(jar)) {
            Bolt.getBolt().log("LOADING VALID JAR: " + jar.getName());
            String mainClass = "";
            JarFile jarFile = null;

            try {
                jarFile = new JarFile(jar);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (jarFile.equals(null)) {
                Bolt.getBolt().log("Error - jar file null");
            }

            JarEntry entry = jarFile.getJarEntry("plugin.json");

            if (entry != null) {
                InputStream stream = null;

                try {
                    stream = jarFile.getInputStream(entry);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String pluginText = FileUtils.getStringFromInputStream(stream);
                JsonObject jsonObject = new JsonParser().parse(pluginText).getAsJsonObject();

                mainClass = jsonObject.get("main").getAsString();
            } else {
                mainClass = "at.tiam.bolt.module.modules.plugin.Plugin";
                Bolt.getBolt().log("Error! Plugin manifest not found: " + plugin.getPluginIdentifier());
            }

            Bolt.getBolt().log("Loading: " + plugin.getPluginIdentifier() + " using main class " + mainClass);

            URI uri = jar.toURI();
            URL url = jar.toURL();
            URL[] urls = new URL[] { url };

            ClassLoader classLoader = new URLClassLoader(urls);
            Class register = Class.forName(mainClass, true, classLoader);

            if (register.isAnnotationPresent(PluginDependency.class)) {
                PluginDependency dependency = (PluginDependency) register.getAnnotation(PluginDependency.class);
                Bolt.getBolt().log("Plgin dependency requested: " + dependency.name() + " version " + dependency.version());

                for (PluginData pl : pluginData) {
                    if (pl.getName().equals(dependency.name()) && (pl.getVersion() == dependency.version() || dependency.version() == 1)) {
                        if (pluginEnabled(pl)) {
                            Bolt.getBolt().log("Dependency found and installed");
                            break;
                        } else {
                            Bolt.getBolt().log("Dependency not found");
                            throw new DependecyNotInstalledException(pl, dependency);
                        }
                    }
                }

                Bolt.getBolt().log("Dependency not found");
                throw new DependencyNotFoundException(dependency);
            }

            PluginRegister reg = (PluginRegister) register.newInstance();
            classLoader.clearAssertionStatus();

            ArrayList<Module> modules = new ArrayList<Module>();

            // This allows the plugin to add hacks
            reg.init(Bolt.getBolt(), modules);

            // This adds the window that will contain the plugin gui
            Window w;
            pluginDataWindowMap.put(plugin, w = new Window(plugin.getName(), Bolt.getBolt().getModuleManager(), reg.getWidth()));
            Bolt.getBolt().getClickGui().windows.add(w);
        }
    }
}
