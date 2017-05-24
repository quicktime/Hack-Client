package at.tiam.bolt.ttf;

import java.awt.*;

/**
 * Created by quicktime on 5/22/17.
 */
public class FontManager {

    public MinecraftFontRenderer hud = null;
    public MinecraftFontRenderer arraylist = null;
    public MinecraftFontRenderer mainMenu = null;
    public MinecraftFontRenderer chat = null;

    private static String fontName = "Comfortaa";

    public void loadFonts() {
        hud = new MinecraftFontRenderer(new Font(fontName, Font.PLAIN, 22), true, true);
        arraylist = new MinecraftFontRenderer(new Font(fontName, Font.PLAIN, 18), true, true);
        mainMenu = new MinecraftFontRenderer(new Font(fontName, Font.PLAIN, 50), true, true);
        chat = new MinecraftFontRenderer(new Font(fontName, Font.PLAIN, 17), true, true);
    }

    public static String getFontName() {
        return fontName;
    }

    public static void setFontName(String fontName) {
        FontManager.fontName = fontName;
    }
}
