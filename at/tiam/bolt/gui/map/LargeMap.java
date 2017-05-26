package at.tiam.bolt.gui.map;

import at.tiam.bolt.Bolt;
import at.tiam.bolt.gui.hub.ColorModifier;
import at.tiam.bolt.util.ClientUtils;
import at.tiam.bolt.util.Colors;
import at.tiam.bolt.util.GuiUtils;
import at.tiam.bolt.event.oldevent.EventRecievePacket;
import at.tiam.bolt.module.EventListener;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.play.server.SPacketChat;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import org.lwjgl.BufferUtils;

import com.darkmagician6.eventapi.EventTarget;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.Map;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by quicktime on 5/26/17.
 */
public class LargeMap {

    private int width = 170, height = 170;
    private double lastX, lastZ;
    private Minecraft minecraft;
    private ColorModifier colorModifier;
    private EventListener listener;
    private Map<String, Integer> factionColors;
    private BiMap<String, List<ChunkData>> factionChunkMap;
    private int vID = -1;
    private boolean showChunks, caveFinder, factions, changed;

    public LargeMap(ColorModifier colourModifier){

        this.minecraft = Bolt.getBolt().getMc();
        this.colorModifier = colourModifier;
        factionColors = new HashMap<String,Integer>();
        factionChunkMap = HashBiMap.create();
        createFactionListener();
    }

    public void draw(int x, int y, double scale){

        if(changed || minecraft.player.getDistance(lastX, minecraft.player.posY, lastZ) > 1){

            if(vID != -1){
                glDeleteTextures(vID);
            }

            vID = getTexture();

            lastX = minecraft.player.posX;
            lastZ = minecraft.player.posZ;

            changed = false;
        }

        float rot = minecraft.player.rotationYaw+90;

        glTranslated(x, y, 0);
        glScaled(scale, scale, scale);

        bind();

        glColor4f(1, 1, 1, 1);
        glClear(256);

        GuiUtils.drawTextureRect(-50, -50, 50, 50);

        unbind();

        glScaled(1/scale, 1/scale, 1/scale);

        GuiUtils.drawSmallTriangle(0, 0, rot, 0xFFFF0000);

        glTranslated(-x, -y, 0);

        GuiUtils.rebindFontRenderer();
    }

    private int getTexture(){

        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 3);

        int playerX = (int) minecraft.player.posX-width/2;
        int playerZ = (int) minecraft.player.posZ-height/2;

        for(int x = 0; x < width; x++){
            for(int z = 0; z < height; z++){

                int pixel = 0;

                int xPos = playerX+x;
                int zPos = playerZ+z;

                BlockPos pos = getTopBlock(playerX+x, playerZ+z);

                if(pos != null){

                    IBlockState top = minecraft.world.getBlockState(pos);
                    pixel = top.getMapColor().colorValue;

                    if(showChunks && (xPos % 16 == 0 || zPos % 16 == 0)){
                        pixel = colorModifier.getDarkColour(pixel);
                    }else if (factions){

                        String faction = getChunkOwner(xPos, zPos);

                        if(faction != null){
                            pixel = colorModifier.getMergedColour(pixel, factionColors.get(faction));
                        }
                    }

                }else{
                    pixel = 0xFFB0B0B0;
                }

                buffer.put((byte) (pixel >> 16 & 0xFF)); //r
                buffer.put((byte) (pixel >> 8 & 0xFF)); //g
                buffer.put((byte) (pixel & 0xFF)); //b
            }
        }

        buffer.flip();

        int id = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, id);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, buffer);
        return id;
    }

    private void createFactionListener(){

        listener = new EventListener(){

            @EventTarget
            public void onPacketRecieve(EventRecievePacket e){

                if(e.packet instanceof SPacketChat){

                    String msg = ((SPacketChat)e.packet).getChatComponent().getUnformattedText();

                    if(msg.startsWith(" ~ ")){

                        try{

                            String faction = msg.split(" ~ ")[1].split(" ")[0];

                            int chunkX = ClientUtils.roundTo16((int)Bolt.getBolt().getPlayer().posX);
                            int chunkZ = ClientUtils.roundTo16((int)Bolt.getBolt().getPlayer().posZ);

                            ChunkData chunk = new ChunkData(chunkX, chunkZ);

                            if(!factionColors.containsKey(faction)){
                                factionColors.put(faction, getNewFactionColour());
                            }

                            List<ChunkData> chunks = factionChunkMap.get(faction);

                            if(chunks == null){
                                chunks = new ArrayList<ChunkData>();
                                chunks.add(chunk);
                                factionChunkMap.put(faction, chunks);
                            }

                            if(!chunks.contains(chunk)){
                                chunks.add(chunk);
                            }

                        }catch(Exception ex){
                            ex.printStackTrace();
                        }
                    }
                }
            }
        };
    }

    private int getNewFactionColour(){
        return Colors.brightColors[factionColors.size() % 10];
    }

    private String getChunkOwner(int x, int z){

        for(String faction : factionChunkMap.keySet()){

            List<ChunkData> chunks = factionChunkMap.get(faction);

            for(ChunkData chunk : chunks){
                if(chunk.containsPoint(x, z)){
                    return faction;
                }
            }
        }

        return null;
    }

    public void bind(){

        glEnable(GL_TEXTURE_2D);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

        glBindTexture(GL_TEXTURE_2D, vID);
    }

    public void unbind(){
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    private BlockPos getTopBlock(int x, int z){
        if(caveFinder){
            return getCaveFinderTopBlock(x, z);
        }else{
            return getNormalTopBlock(x, z);
        }
    }

    private BlockPos getNormalTopBlock(int x, int z){

        int playerY = (int)minecraft.player.posY;

        BlockPos pos = null;

        for(int y = 80; y > 0; y--){

            pos = new BlockPos(x, playerY+y-40, z);
            IBlockState state = minecraft.world.getBlockState(pos);

            if(state.getBlock() != Blocks.AIR){
                return pos;
            }
        }

        return null;
    }

    private BlockPos getCaveFinderTopBlock(int x, int z){

        int playerY = (int)minecraft.player.posY;
        BlockPos pos = null;
        boolean wasBlock = false;

        for(int y = -30; y < 30; y++){

            pos = new BlockPos(x, playerY+y, z);
            IBlockState state = minecraft.world.getBlockState(pos);

            if(state.getBlock() != Blocks.AIR){
                wasBlock = true;
            }else if(wasBlock){
                return pos.down(1);
            }else{
                wasBlock = false;
            }
        }

        return null;
    }

    public void registerFactionsListener(){
        listener.register();
    }

    public void unregisterFactionsListener(){
        listener.unregister();
    }

    public void setShowFactions(boolean factions){

        if(factions != this.factions){
            this.changed = true;
            this.factions = factions;

            if(factions){
                registerFactionsListener();
            }else{
                unregisterFactionsListener();
            }
        }
    }

    public boolean isShowChunks() {
        return showChunks;
    }

    public void setShowChunks(boolean showChunks) {

        if(showChunks != this.showChunks){
            this.changed = true;
            this.showChunks = showChunks;
        }

    }

    public boolean isCavefinder() {
        return caveFinder;
    }

    public void setCavefinder(boolean caveFinder) {

        if(caveFinder != this.caveFinder){
            this.changed = true;
            this.caveFinder = caveFinder;
        }

    }

}
