package at.tiam.bolt.gui.components.selector;

import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by quicktime on 5/26/17.
 */
public class EntitySelectorButton extends SelectorButton {

    private EntityPlayer player;

    public EntitySelectorButton(EntityPlayer player, SelectorSystem system) {
        super(player.getName(), system);
        this.player = player;
    }

    @Override
    public String getText() { return player.getName(); }

    public EntityPlayer getPlayer() { return player; }
}
