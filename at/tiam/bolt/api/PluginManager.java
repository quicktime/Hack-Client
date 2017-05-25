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
import at.tiam.bolt.util.ClientUtils;
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
     *
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
     *
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
     *
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
     *
     * @param plugin
     * @return boolean
     */
    public boolean pluginEnabled(PluginData plugin) {
        return pluginDataWindowMap.containsKey(plugin);
    }

    /**
     * Initialises a plugin from it's file and then adds the plugin to the mod system and window system
     *
     * @param plugin
     * @throws MalformedURLException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws DependencyNotInstalledException
     * @throws DependencyNotFoundException
     */
    public void addPlugin(PluginData plugin) throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException, DependencyNotInstalledException, DependencyNotFoundException {
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
            URL[] urls = new URL[]{url};

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
                            throw new DependencyNotInstalledException(pl, dependency);
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

    /**
     * Downloads a plugin from the website
     *
     * @param pluginData
     */
    public void downloadPlugin(PluginData pluginData) {
        Bolt.getBolt().log("DOWNLOADING: " + pluginData.getFileName());

        if (downloadThread != null && downloadThread.isAlive()) {
            downloadQueue.add(pluginData);
        } else {
            downloadQueue.add(pluginData);

            downloadThread = new Thread() {
                public void run() {
                    while (!downloadQueue.isEmpty()) {
                        PluginData data = downloadQueue.poll();

                        File pluginFile = new File(plugins, data.getJarName());
                        File iconFile = new File(icons, data.getIconName());

                        try {
                            downloadFile(data.getFilePath(), pluginFile);
                            downloadFile(data.getIconPath(), iconFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (data.isPrivatePlugin()) {
                            try {
                                addPlugin(data);
                            } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException | DependencyNotInstalledException | DependencyNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            };

            downloadThread.start();
        }
    }

    private void downloadFile(String location, File file) throws IOException {
        URL url = new URL(location);
        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("User-Agent", ClientUtils.USER_AGENT);
        ReadableByteChannel readableByteChannel;
        readableByteChannel = Channels.newChannel(urlConnection.getInputStream());
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        fileOutputStream.close();
        readableByteChannel.close();
    }

    /**
     * Removes a plugin from the mod system and the window system
     *
     * @param pluginData
     */
    public void removePlugin(PluginData pluginData) {
        Bolt.getBolt().log("REMOVING: " + pluginData.getFileName());
        Bolt.getBolt().getModuleManager().removePluginModules(pluginData);
        Bolt.getBolt().getClickGui().windows.remove(this.pluginDataWindowMap.get(pluginData));
        pluginDataWindowMap.remove(pluginData);
    }

    private boolean developerMode = true;

    /**
     * This method is designed to stop people adding their own plugins which aren't approved by the website yawk.net
     * In the future, plugins which aren't approved may be allowed, but this checking will stay enabled to protect client users from viruses
     * <p>
     * The hash check uses the SHA-1 algorithm to get the checksum of the plugin file we're trying to load
     * It checks whether the file hash matches the hash of the file on the website (which is approved)
     * <p>
     * This should prevent any usage of modified plugin files
     * Because any modified files will have a different checksum
     * <p>
     * The URL for hash checking is
     * http://tiam.at/mods/valid.php
     * <p>
     * The parameters for hash checking on the website are:
     * name = the name of the plugin file
     * hash = the checksum of the plugin file
     *
     * @param jar
     * @return
     */
    private boolean isValid(File jar) {
        if (developerMode) {
            return true;
        }
        try {
            String hash = getHash(jar);
            Bolt.getBolt().log("HASH: " + hash);
            Bolt.getBolt().log("NAME: " + ClientUtils.stripExtension(jar.getName()));
            Document document = Jsoup.connect("http://tiam.at/mods/valid.php?&name=" + ClientUtils.stripExtension(jar.getName()) + "&hash=" + hash).userAgent(ClientUtils.USER_AGENT).get();
            Bolt.getBolt().log("RESPONSE: " + document.text());

            return document.text().equalsIgnoreCase("true");
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Gets an SHA-512 hash from the file given. This is used to check the hash against the website's saved hash
     *
     * @param jar
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    private String getHash(File jar) throws NoSuchAlgorithmException, IOException {

        MessageDigest md = MessageDigest.getInstance("SHA-512");
        FileInputStream fis = new FileInputStream(jar);
        byte[] dataBytes = new byte[1024];

        int nRead = 0;

        while ((nRead = fis.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, nRead);
        }
        ;

        fis.close();

        byte[] mdBytes = md.digest();

        //convert the byte to hex format
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < mdBytes.length; i++) {
            sb.append(Integer.toString((mdBytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString().toUpperCase();
    }
}
