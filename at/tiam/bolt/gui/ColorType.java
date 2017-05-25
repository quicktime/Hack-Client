package at.tiam.bolt.gui;

import at.tiam.bolt.util.Colors;

/**
 * Created by quicktime on 5/25/17.
 */
public enum ColorType {

    TEXT("Text", 2, 0x00C000C0, false), HIGHLIGHT("Highlight", 0, 0x00C000C0, false), BODY("Body", 0, 0x00C000C0, false), BORDER("Border", 2, 0x8F000000, false), TITLE("Title", 0), TITLE_TEXT("Title Text", 0);

    private String name;
    private int color;
    private int modifiedColor;
    private int modifier;
    private int index;
    private int defaultIndex;
    private boolean combine;

    ColorType(String name, int index) { this(name, index, 0, false); }

    ColorType(String name, int index, int modifier, boolean combine) {
        this.name = name;
        this.index = index;
        this.modifier = modifier;
        this.combine = combine;
        this.defaultIndex = index;
        setIndex(index);
    }

    public String getName() { return name; }
    public int getColor() { return color; }
    public int getModifiedColor() { return modifiedColor; }
    public int getOverlayColor() { return color - 0x2F000000; }
    public int getIndex() { return index; }
    public int getDefaultIndex() { return defaultIndex; }

    public void setColor(int color) {
        this.color = color;
        if (combine) {
            this.modifiedColor = color + modifier;
        } else {
            this.modifiedColor = color - modifier;
        }
    }

    public void setIndex(int index) {
        this.index = index;
        setColor(Colors.options[index]);
    }

    public void cycleIndex() {
        index++;
        if (index >= Colors.options.length) {
            index = 0;
        }
        setColor(Colors.options[index]);
    }

    public static ColorType getTypeByName(String name) {
        for (ColorType colorType : values()) {
            if (colorType.getName().equalsIgnoreCase(name)) {
                return colorType;
            }
        }
        return null;
    }



}
