package at.tiam.bolt.gui.hub;

import at.tiam.bolt.Bolt;
import at.tiam.bolt.gui.Canvas;
import at.tiam.bolt.gui.IScalerPosition;
import at.tiam.bolt.gui.components.Slider;
import at.tiam.bolt.gui.components.buttons.BooleanButton;
import at.tiam.bolt.gui.map.LargeMap;
import at.tiam.bolt.module.value.BooleanValue;
import at.tiam.bolt.module.value.SliderValue;
import at.tiam.bolt.module.value.ValueRegistry;
import net.minecraft.client.gui.Gui;

/**
 * Created by quicktime on 5/26/17.
 */
public class MapSlate extends Slate {

    private LargeMap map;
    private Canvas options;
    private SliderValue scale;
    private BooleanValue chunks, caveFinder, factions;

    public MapSlate(final GuiHub guiHub, Bolt bolt) {
        super("Map", null, guiHub);

        IScalerPosition position = new IScalerPosition() {
            @Override
            public int getX() {
                return guiHub.width / 2 - 50;
            }

            @Override
            public int getY() {
                return 3;
            }
        };

        options = new Canvas(position, 100);

        ValueRegistry valueRegistry = bolt.getValueRegistry();

        scale = new SliderValue("Scale", "map.scale", valueRegistry, 2, 1, 8, false);
        options.addComponent(new Slider(scale));

        chunks = new BooleanValue("Show Chunks", "map.chunks", valueRegistry, false);
        options.addComponent(new BooleanButton(chunks));

        caveFinder = new BooleanValue("Cavefinder Mode", "map.cavefinder", valueRegistry, false);
        options.addComponent(new BooleanButton(caveFinder));

        factions = new BooleanValue("Record Factions", "map.factions", valueRegistry, false);
        options.addComponent(new BooleanButton(factions));

        map = new LargeMap(guiHub.colorModifier);
    }

    @Override
    public void renderSlate(int x, int y) {

        map.setShowFactions(factions.getValue());
        map.setCavefinder(caveFinder.getValue());
        map.setShowChunks(chunks.getValue());

        map.draw(guiHub.width/2, guiHub.height/2, scale.getValue());

        options.draw(x, y);
    }

    @Override
    public void init() {
        map.registerFactionsListener();
    }

    @Override
    public void close() {
        map.unregisterFactionsListener();
    }

    @Override
    public void mouseClicked(int x, int y) {
        options.mouseClicked(x, y);
    }

    @Override
    public void mouseReleased(int x, int y, int state) {
        options.mouseReleased(x, y, state);
    }
}
