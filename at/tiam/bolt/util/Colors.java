package at.tiam.bolt.util;

/**
 * Created by quicktime on 5/25/17.
 */
public class Colors {
    public static int BRIGHT_TEXT = 0xFF4DFF4C;
    public static int ALTERNATE_TEXT = 0xFFAFA4FF;
    public static int GREY = 0xFFAFAFAF;

    public static int[] options = new int[] {
            BRIGHT_TEXT,
            ALTERNATE_TEXT,
            GREY,
            0xFFFFFFFF,
            0xFF002000,
            0xFFA4C744,
            0xFFFF7AD1,
            0xFFFF2929,
            0xFF0C478A,
            0xFF595959,
            0xFF202020,
    };

    public static int[] brightColors = new int[] {
            0xFF00FF00,
            0xFF0000FF,
            0xFFFFFF00,
            0xFFFF00FF,
            0xFF00FFFF,
            BRIGHT_TEXT,
            0xFFFF5050,
            0xFF5050FF,
            ALTERNATE_TEXT
    };
}
