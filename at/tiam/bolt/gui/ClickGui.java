package at.tiam.bolt.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by quicktime on 5/24/17.
 */
public class ClickGui extends GuiScreen {

    public List<Window> windows = new CopyOnWriteArrayList<Window>();

    public void onModuleManagerChange() {
        for (Window win : windows) {
            win.onModuleManagerChange();
        }
    }

    public void setDragging(Window dragging){

        for(Window win : windows){
            if(win != dragging){
                win.dragging = false;
            }
        }

        windows.remove(dragging);
        windows.add(windows.size(), dragging);
    }
}
