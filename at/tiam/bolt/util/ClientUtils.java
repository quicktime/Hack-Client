package at.tiam.bolt.util;

import at.tiam.bolt.Bolt;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.network.Packet;
import net.minecraft.util.ChatAllowedCharacters;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * Created by quicktime on 5/25/17.
 */
public class ClientUtils {

    public static Random random = new Random();
    public static String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36";
    public static DecimalFormat digitsThree = new DecimalFormat("#0.00");
    public static DecimalFormat digitsTwo = new DecimalFormat("#0.0");

//    public static void sendPacket(Packet packet) {
//        Bolt.getBolt().getPlayer().sendQueue.addToSendQueue(packet); }

    /**
     * Use filterAllowedCharacters(string) in place -- (net.minecraft.util.ChatAllowedCharacters)
     */
    /*
    private static String allowedCharacters;

    static {
        allowedCharacters = ChatAllowedCharacters.allowedCharactersArray.toString() + " ";
    }

    public static String allowedCharacters() { return allowedCharacters; }
    */

    public static void openBrowserWindow(String url) {
        String os = System.getProperty("os.name").toLowerCase();
        Runtime rt = Runtime.getRuntime();

        try {
            if (os.contains("win")) {
                // This doesn't support showing urls in the form of "page.html#nameLink"
                rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } else if (os.contains("mac")) {
                rt.exec("open " + url);
            } else if (os.contains("nix") || os.contains("nux")) {
                // Do a best guess on unix until we get a platform independent way
                // Build a list of browsers to try, in this order.
                String[] browsers = { "epiphany", "firefox", "mozilla", "konqueror", "netscape", "opera", "links", "lynx"};

                //Build a command string which looks like "browser1 "url" || browser2 "url" ||..."
                StringBuffer cmd = new StringBuffer();
                for (int i = 0; i < browsers.length; i++)
                    cmd.append((i == 0 ? "" : " || " ) + browsers[i] + " \"" + url + "\" ");
                    rt.exec(new String[] { "sh", "-c", cmd.toString() });
            } else {
                return;
            }
        } catch (Exception e) {
            return;
        }
    }

    public static String stripExtension(String str) {
        int pos = str.lastIndexOf(".");

        if (pos == -1) {
            return str;
        } else {
            return str.substring(0, pos);
        }
    }

    public static int roundTo16(int i) { return i - (i % 16); }

    public static boolean isInteger(String text) {
        int length = text.length();
        int i = 0;

        if (text == null) {
            return false;
        }

        if (length == 0) {
            return false;
        }

        if (text.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }

        for (; i < length; i++) {
            char c = text.charAt(i);

            if (c <= '/' || c >= ':') {
                return false;
            }
        }

        return true;
    }

    public static int getScreenWidth() {
        ScaledResolution scaledResolution = new ScaledResolution(Bolt.getBolt().getMc());
        return scaledResolution.getScaledWidth();
    }

    public static int getScreenHeight() {
        ScaledResolution scaledResolution = new ScaledResolution(Bolt.getBolt().getMc());
        return scaledResolution.getScaledWidth();
    }
}
