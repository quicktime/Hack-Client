package at.tiam.bolt.module;

import at.tiam.bolt.Bolt;
import at.tiam.bolt.module.value.AbstractValue;
import net.minecraft.client.entity.EntityPlayerSP;
import org.lwjgl.input.Keyboard;

/**
 * Created by quicktime on 05/22/2017.
 */
public class Module implements IToggleable {

//    private String name = getClass().getAnnotation(ModInfo.class).name();
//
//    private String description = getClass().getAnnotation(ModInfo.class).description();
//
//    private Category category = getClass().getAnnotation(ModInfo.class).category();
//
//    private int bind = getClass().getAnnotation(ModInfo.class).bind();
//
//    private boolean state;
//
//    /**
//     * Category
//     */
//    public enum Category {
//        COMBAT(0x3ABDFF), MOVEMENT(0xF8FF1F), RENDER(0x48FF1F), WORLD(0xCF1FFF), MISC(0xFFC100), PLAYER(0x00FFEC), GUI(0xE800D5), PLUGIN(0x838383), NONE(0x000000);
//
//        public int color;
//
//        Category(int color) {
//
//            this.color = color;
//        }
//
//        public String getName() { return getClass().getName(); }
//    }
//
//    /**
//     * Module information @ModInfo
//     */
//    @Retention(RetentionPolicy.RUNTIME)
//    public @interface ModInfo {
//
//        String name();
//
//        String description();
//
//        Category category();
//
//        int bind();
//
//    }
//
//    public String getName() {
//
//        return name;
//    }
//
//    public void setName(String name) {
//
//        this.name = name;
//    }
//
//    public String getDescription() {
//
//        return description;
//    }
//
//    public void setDescription(String description) {
//
//        this.description = description;
//    }
//
//    public Category getCategory() {
//
//        return category;
//    }
//
//    public void setCategory(Category category) {
//
//        this.category = category;
//    }
//
//    public int getBind() {
//
//        return bind;
//    }
//
//    public void setBind(int bind) {
//
//        this.bind = bind;
//    }
//
//    public boolean getState() {
//
//        return state;
//    }
//
//    public void setState(boolean state) {
//
//        onToggle();
//
//        if (state) {
//            onEnable();
//            this.state = true;
//            EventManagerOld.register(this);
//        } else {
//            onDisable();
//            this.state = false;
//            EventManagerOld.unregister(this);
//        }
//
//        //Save the module
//
//    }
//
//    public void onDisable() {
//
//    }
//
//    public void onEnable() {
//
//    }
//
//    public void onToggle() {
//
//    }
//
//    public void toggle() {
//
//        setState(!this.getState());
//    }
//
//    public final boolean isCategory(Category category) {
//
//        return category == this.category;
//    }
//
//    public String getKeyName() {
//
//        return getBind() == -1 ? "-1" : Keyboard.getKeyName(getBind());
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return state;
//    }

    public EntityPlayerSP player = Bolt.getBolt().getPlayer();

    protected int keybind;
    protected boolean enabled;
    protected Category category;
    protected String name;
    protected String description;
    private AbstractValue[] options;

    public Module() {
        super();
        this.keybind = -1;
    }

    public Module(AbstractValue[] options) {
        super();
        this.options = options;
        this.keybind = -1;
    }

    public boolean hasOptions(){
        return options != null;
    }

    public AbstractValue[] getOptions() {
        return options;
    }

    public void setOptions(AbstractValue[] options) {
        this.options = options;
    }

    public final String getKeyName(){
        return keybind == -1? "None":Keyboard.getKeyName(keybind);
    }

    public final int getKeybind() {
        return keybind;
    }

    public final void setKeybind(int keybind){
        this.keybind = keybind;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void onEnable(){

    }

    public void onDisable(){

    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public enum Category {

//        COMBAT(0x3ABDFF), MOVEMENT(0xF8FF1F), RENDER(0x48FF1F), WORLD(0xCF1FFF), MISC(0xFFC100), PLAYER(0x00FFEC), GUI(0xE800D5), PLUGIN(0x838383), NONE(0x000000);

        COMBAT("Combat"), MOVEMENT("Movement"), RENDER("Render"), WORLD("World"), MISC("Misc"), PLAYER("Player"), GUI("Gui"), PLUGIN("Plugin"), NONE("None");

//        public int color;
//
//        Category(int color) {
//            this.color = color;
//        }
//
//        public int getColor() {
//            return color;
//        }

        private final String name;

        Category(String name) { this.name = name; }

        public String getName() { return name; }

    }
}
