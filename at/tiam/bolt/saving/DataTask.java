package at.tiam.bolt.saving;

import at.tiam.bolt.Bolt;
import com.google.gson.JsonObject;

/**
 * Created by quicktime on 5/24/17.
 */
public interface DataTask {
    String getFileName();
    void read(Bolt bolt, JsonObject object);
    void write(Bolt bolt, JsonObject object);
}
