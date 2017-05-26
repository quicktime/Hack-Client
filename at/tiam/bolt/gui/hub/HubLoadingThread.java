package at.tiam.bolt.gui.hub;

import at.tiam.bolt.util.ClientUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;

/**
 * Created by quicktime on 5/26/17.
 */
public class HubLoadingThread implements Runnable {

    private GuiHub guiHub;

    public HubLoadingThread(GuiHub guiHub) {
        super();
        this.guiHub = guiHub;
    }

    @Override
    public void run() {
        try {
            Document document = Jsoup.connect("http://tiam.at/news.php").userAgent(ClientUtils.USER_AGENT).get();
            JsonArray jsonArray = (JsonArray) new JsonParser().parse(document.text());

            for (JsonElement jsonElement : jsonArray) {
                JsonObject slate = jsonElement.getAsJsonObject();

                ArrayList<SquareCell> cells = new ArrayList<SquareCell>();

                for (JsonElement cellElement : slate.get("cells").getAsJsonArray()) {
                    JsonObject cell = cellElement.getAsJsonObject();
                    cells.add(new SquareCell(cell.get("title").getAsString(), cell.get("color").getAsInt(), cell.get("contents").getAsString()));
                }
            }
            guiHub.setState(State.CONNECTED);
            guiHub.postConnection();
        } catch (Exception e) {
            e.printStackTrace();
            guiHub.setState(State.FAILED);
        }
    }
}
