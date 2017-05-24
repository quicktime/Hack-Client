package at.tiam.bolt.module;

import at.tiam.bolt.event.EventManagerOld;
import org.lwjgl.input.Keyboard;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by quicktime on 05/22/2017.
 */
public class Module implements IToggleable {

    private String name = getClass().getAnnotation(ModInfo.class).name();

    private String description = getClass().getAnnotation(ModInfo.class).description();

    private Category category = getClass().getAnnotation(ModInfo.class).category();

    private int bind = getClass().getAnnotation(ModInfo.class).bind();

    private boolean state;

    /**
     * Category
     */
    public enum Category {
        COMBAT(0x3ABDFF), MOVEMENT(0xF8FF1F), RENDER(0x48FF1F), WORLD(0xCF1FFF), MISC(0xFFC100), PLAYER(0x00FFEC), GUI(0xE800D5), PLUGIN(0x838383);

        public int color;

        Category(int color) {

            this.color = color;
        }
    }

    /**
     * Module information @ModInfo
     */
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ModInfo {

        String name();

        String description();

        Category category();

        int bind();

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

    public Category getCategory() {

        return category;
    }

    public void setCategory(Category category) {

        this.category = category;
    }

    public int getBind() {

        return bind;
    }

    public void setBind(int bind) {

        this.bind = bind;
    }

    public boolean getState() {

        return state;
    }

    public void setState(boolean state) {

        onToggle();

        if (state) {
            onEnable();
            this.state = true;
            EventManagerOld.register(this);
        } else {
            onDisable();
            this.state = false;
            EventManagerOld.unregister(this);
        }

        //Save the module

    }

    public void onDisable() {

    }

    public void onEnable() {

    }

    public void onToggle() {

    }

    public void toggle() {

        setState(!this.getState());
    }

    public final boolean isCategory(Category category) {

        return category == this.category;
    }

    public String getKeyName() {

        return getBind() == -1 ? "-1" : Keyboard.getKeyName(getBind());
    }

    @Override
    public boolean isEnabled() {
        return state;
    }
}
