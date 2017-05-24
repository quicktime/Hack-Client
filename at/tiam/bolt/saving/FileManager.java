package at.tiam.bolt.saving;

import at.tiam.bolt.Bolt;
import at.tiam.bolt.util.ReflectionUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import at.tiam.bolt.util.FileUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.io.File;

/**
 * Created by quicktime on 5/24/17.
 */
public class FileManager {

    public List<DataTask> tasks; // TODO: Create DataTask class (at.tiam.bolt.saving)
    private DataTask settingsTask;
    private Bolt bolt;

    public FileManager(Bolt bolt) {
        this.bolt = bolt;
        tasks = initializeClassesFromPackage("at.tiam.bolt.saving");
        settingsTask = getTaskByName("settings");
    }

    private List<DataTask> initializeClassesFromPackage(String packageName) {

        List<DataTask> objects = new ArrayList<DataTask>();

        for (Class<?> clazz : ReflectionUtils.getClasses(packageName)) {
            try {
                if (Arrays.asList(clazz.getInterfaces()).contains(DataTask.class)) {
                    Object obj = clazz.newInstance();
                    objects.add((DataTask) obj);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return objects;
    }

    public void save() {
        for (DataTask task : tasks) {
            File file = getFile(task);

            if (file.exists()) {
                JsonObject obj = new JsonObject();
                task.write(bolt, obj);
                FileUtils.writeFile(file, obj.toString());
            } else {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Loads simple single and optional values
     */
    public void loadClientSettings() { handleTaskLoading(settingsTask); }

    /**
     * Loads complex values
     */
    public void loadSecondarySettings() {
        for (DataTask task : tasks) {
            if (task != settingsTask) {
                handleTaskLoading(task);
            }
        }
    }

    private void handleTaskLoading(DataTask task) {
        File file = getFile(task);

        if (file.exists()) {
            System.out.println("LOADING TASK: " + task.getFileName());

            JsonElement el = new JsonParser().parse(FileUtils.readFileFull(file));

            if (el.isJsonObject()) {
                task.read(bolt, (JsonObject) new JsonParser().parse(FileUtils.readFileFull(file)));
            } else {
                System.out.println("REJECTED TASK FILE: " + task.getFileName());
            }
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private File getFile(DataTask task) {
        return new File(Bolt.getFullDir(), task.getFileName() + ".json");
    }

    private DataTask getTaskByName(String name) {
        for (DataTask task : tasks) {
            if (task.getFileName().equalsIgnoreCase(name)) {
                return task;
            }
        }
        return null;
    }

}
