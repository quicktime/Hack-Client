package at.tiam.bolt.command;

import at.tiam.bolt.Bolt;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;

/**
 * Created by quicktime on 5/26/17.
 */
public abstract class Command {

    private String name, call, desc;

    protected Minecraft mc;

    public Command(String name, String call, String desc) {
        this.name = name;
        this.call = call;
        this.desc = desc;

        mc = Minecraft.getMinecraft();
    }

    public String getName() { return name; }
    public String getCallName() { return call; }
    public String getSummary(CommandManager cm) {
        StringBuilder builder = new StringBuilder();

        Style style = new Style();
        builder.append(TextFormatting.BLUE + getCallName());

        for (Argument argument : getArguments(cm)) {
            if (argument.isOptional()) {
                builder.append(TextFormatting.AQUA + " [" + argument.getName() + "]");
            } else {
                builder.append(TextFormatting.GOLD + " [" + argument.getName() + "]");
            }
        }
        return builder.toString();
    }

    public String getDesc() { return desc; }
    protected void chat(String msg) {
        Bolt.getBolt().addChat(msg);
    }
    protected void message(String msg) {
        Bolt.getBolt().getPlayer().connection.sendPacket(new CPacketChatMessage(msg));
    }

    public abstract void runCommand(CommandManager commandManager, Arguments arguments);
    public abstract Argument[] getArguments(CommandManager commandManager);
}
