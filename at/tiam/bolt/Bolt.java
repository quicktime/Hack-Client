package at.tiam.bolt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.io.File;

import at.tiam.bolt.api.PluginManager;
import at.tiam.bolt.camera.Camera;
import at.tiam.bolt.command.CommandManager;
import at.tiam.bolt.event.events.EventKeyboard;
import at.tiam.bolt.friend.FriendManager;
import at.tiam.bolt.hook.EntityRendererHook;
import at.tiam.bolt.hook.ItemRendererHook;
import at.tiam.bolt.hook.RenderGlobalHook;
import at.tiam.bolt.module.Module;
import at.tiam.bolt.module.ModuleManager;
import at.tiam.bolt.module.modules.player.NoHitFlinch;
import at.tiam.bolt.module.modules.world.NearbyRespawn;
import at.tiam.bolt.module.value.ValueRegistry;
import at.tiam.bolt.saving.FileManager;
import at.tiam.bolt.ttf.FontManager;
import at.tiam.bolt.gui.ClickGui;
import at.tiam.bolt.gui.hub.GuiHub;
import at.tiam.bolt.util.ClientSession;
import at.tiam.bolt.module.modules.gui.HideClient;

import com.darkmagician6.eventapi.EventManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.TextComponentTranslation;
import org.lwjgl.input.Keyboard;
import sun.awt.SunHints;
// import net.minecraft.util.ChatComponentTranslation;

/**
 * Created by quicktime on 5/22/17.
 */
public class Bolt {

    public static String VERSION = "inDev 0.01";
    public static String NAME = "Bolt";
    public static String AUTHOR = "quicktime";

    private static Bolt bolt;

    private Minecraft mc;
    private FontRenderer fontRenderer;

    private Logger logger;



    public GuiHub guiHub;
    public ClickGui clickGui;
    private List<Camera> cameras;
    private ClientSession session;

    private ModuleManager moduleManager;
    private FontManager fontManager;
    private FileManager fileManager;
    private PluginManager pluginManager;
    private FriendManager friendManager;
    private CommandManager commandManager;
    private ValueRegistry valueRegistry;


    public Bolt(Minecraft mc) {
        this.mc = mc;
        cameras = new ArrayList<Camera>();
    }

    public void init() {
        logger = Logger.getGlobal();

        RenderGlobalHook renderGlobalHook = new RenderGlobalHook(mc);
        mc.renderGlobal = renderGlobalHook;

        // This NEEDS to go before entityRenderer because entityRenderer caches the value of Minecraft.getItemRenderer
        mc.itemRenderer = new ItemRendererHook(mc);

        EntityRendererHook entityRendererHook = new EntityRendererHook(mc, mc.getResourceManager());
        mc.entityRenderer = entityRendererHook;

        authenticateUser();
        loadPrimaryFiles();
        createClient();
        loadFiles();
        addShutdownHook();

        entityRendererHook.noHitFlinch = moduleManager.getModuleByClass(NoHitFlinch.class);
        renderGlobalHook.nearbyRespawn = (NearbyRespawn) Bolt.getBolt().getModuleManager().getModuleByClass(NearbyRespawn.class);
    }

    private void authenticateUser() {
        // TODO: AUTHENTICATION
    }

    private void loadPrimaryFiles() {
        valueRegistry = new ValueRegistry();
        fileManager = new FileManager(this);
        fileManager.loadClientSettings();
    }

    private void loadFiles() {
        (new Thread(){

            public void run(){

                try {
                    pluginManager.load();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                fileManager.loadSecondarySettings();

            }

        }).start();
    }

    private void createClient() {
        fontRenderer = mc.fontRendererObj;
        moduleManager = new ModuleManager();
        guiHub = new GuiHub(this);
        clickGui = new ClickGui(ModuleManager);
        pluginManager = new PluginManager();
        commandManager = new CommandManager();
        friendManager = new FriendManager();
    }

    private void addShutdownHook() {

    }

    public void log(String print) { logger.info(print); }
    public GuiHub getGuiHub() { return guiHub; }
    public Module getHideClientMod() { return Bolt.getBolt().getModuleManager().getModuleByClass(HideClient.class); }
    public PluginManager getPluginManager() { return pluginManager; }
    public ModuleManager getModuleManager() { return  moduleManager; }
    public static Bolt getBolt() { return bolt; }
    public static void setClient(Bolt bolt) { Bolt.bolt = bolt; }
    public EntityPlayerSP getPlayer() { return mc.player; }
    public ClickGui getClickGui() { return clickGui; }
    public FontRenderer getFontRenderer() { return fontRenderer; }
    public Minecraft getMc() { return mc; }
    public ClientSession getSession() { return session; }
    public FileManager getFileManager() { return fileManager; }
    public FriendManager getFriendManager() { return friendManager; }

    public void addChat(String text) {
        Bolt.getBolt().getPlayer().addChatMessage(new TextComponentTranslation ("[Bolt]" + text));
    }

    public void keyPressed(int key) {
        if (key == Keyboard.KEY_RSHIFT) {
            mc.displayGuiScreen(clickGui);
        }

        if (key == Keyboard.KEY_Y) {
            mc.displayGuiScreen(guiHub);
        }

        if (mc.currentScreen == null) {
            for (Module m : ModuleManager.modules) {
                if (m.getBind() == key) {
                    moduleManager.toggle(m);
                }
            }
            EventManager.call(new EventKeyboard(key));
        }
    }

    public static String getDir() { return Minecraft.getMinecraft().mcDataDir.getAbsolutePath(); }

    public static String getFullDir() {
        try {
            File file = new File(getDir(), "Bolt");
            if (!file.exists()) {
                file.mkdirs();
            }
            return Minecraft.getMinecraft().mcDataDir.getCanonicalPath() + "/Bolt";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ValueRegistry getValueRegistry() { return valueRegistry; }
    public List<Camera> getCameras() { return cameras; }
    public CommandManager getCommandManager() { return commandManager; }
    public void registerCamer(Camera camera) { cameras.add(camera); }

}
