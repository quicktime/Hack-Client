package at.tiam.bolt.api;

import at.tiam.bolt.util.ClientUtils;
import at.tiam.bolt.util.download.DownloadCallback;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by quicktime on 5/26/17.
 */
public class PrivatePluginInformationThread implements Runnable {

    private PluginData plugin;
    private JsonObject response;
    private boolean successful, running;
    private String name, password;
    private DownloadCallback callback;

    public PrivatePluginInformationThread(String name, String password, DownloadCallback callback) {
        super();
        this.name = name;
        this.password = password;
        this.callback = callback;
    }

    @Override
    public void run() {

        try {

            running = true;

            Document doc = Jsoup.connect("http://www.yawk.net/mods/getPrivate.php?name="+name+"&password="+password).userAgent(ClientUtils.USER_AGENT).get();

            response = new JsonParser().parse(doc.text()).getAsJsonObject();

            successful = response.has("status") && response.get("status").getAsBoolean();

            if(successful){
                plugin = new PluginData(response.get("name").getAsString(),
                        response.get("description").getAsString(),
                        response.get("file").getAsString(),
                        response.get("filename").getAsString(),
                        response.get("icon").getAsString(),
                        response.get("version").getAsInt(),
                        false,
                        true);
            }

        } catch (Exception e) {
            e.printStackTrace();
            successful = false;
        }

        running = false;
        callback.finished(this);
    }

    public PluginData getPlugin() {
        return plugin;
    }

    public JsonObject getResponse() {
        return response;
    }

    public boolean isSuccessful(){
        return successful;
    }

    public boolean isRunning() {
        return running;
    }
}
