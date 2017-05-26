package at.tiam.bolt.gui.components.scrolling;

import at.tiam.bolt.Bolt;
import at.tiam.bolt.gui.components.selector.EntitySelectorButton;
import at.tiam.bolt.gui.components.selector.SelectorButton;
import at.tiam.bolt.gui.components.selector.SelectorSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by quicktime on 5/26/17.
 */
public class PlayerEntityScrollPane extends ScrollPane {

    private SelectorSystem<SelectorButton> system;
    private Minecraft mc;

    public PlayerEntityScrollPane(int height, SelectorSystem<SelectorButton> system) {
        super(height);
        this.system = system;
        this.mc = Minecraft.getMinecraft();
    }

    @Override
    public void draw(int x, int y) {

        List<EntityPlayer> players = (List<EntityPlayer>) mc.world.playerEntities;

        Iterator<SelectorButton> it = system.buttons.iterator();

        while(it.hasNext()){
            if(entityNotFound(it.next().getStaticText(), players)){
                it.remove();
            }
        }

        for(EntityPlayer player : players){
            if(playerNotFound(player.getName())){
                addComponent(system.add(new EntitySelectorButton(player, system)));
            }
        }

        super.draw(x, y);
    }

    private boolean playerNotFound(String p){

        for(SelectorButton b : system.buttons){

            if(b.getStaticText().equalsIgnoreCase(p)){
                return false;
            }
        }

        return true;
    }

    private boolean entityNotFound(String playerName, List<EntityPlayer> players){

        for(EntityPlayer player : players){

            if(player.getName().equalsIgnoreCase(playerName)){
                return false;
            }
        }

        return true;
    }

    private ArrayList<String> getPlayers(){

        ArrayList<String> list = new ArrayList<String>();

        NetHandlerPlayClient net = Bolt.getBolt().getPlayer().connection;

        for(Object obj : net.getPlayerInfoMap()) {
            //func_178845_a() is the GameProfile of the player
            //getName() is the username of the player
            list.add(((NetworkPlayerInfo)obj).getGameProfile().getName());
        }

        return list;
    }
}
