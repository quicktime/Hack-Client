package at.tiam.bolt.hook;

import com.darkmagician6.eventapi.EventManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.yawk.client.Client;
import net.yawk.client.events.EventClickBlock;
import net.yawk.client.events.EventDamageBlock;
import net.yawk.client.events.EventGuiRender;
import net.yawk.client.events.EventPlaceBlock;
import net.yawk.client.events.EventUpdateController;
import net.yawk.client.modmanager.Mod;
import net.yawk.client.mods.building.Reach;

public class PlayerControllerMPHook extends PlayerControllerMP{

	public PlayerControllerMPHook(Minecraft mcIn, NetHandlerPlayClient p_i45062_2_) {
		super(mcIn, p_i45062_2_);
	}
	
	private EventUpdateController tick = new EventUpdateController();
	
	@Override
	public void updateController() {
		super.updateController();
		EventManager.call(tick);
	}
	
	@Override
	public boolean onBlockPlacement(EntityPlayerSP player,
			WorldClient world, ItemStack itemstack,
			BlockPos pos, EnumFacing facing, Vec3 vec) {
		
		EventPlaceBlock e = new EventPlaceBlock(pos, facing, itemstack, vec);
		EventManager.call(e);
		
		if(e.isCancelled()){
			return false;
		}else{
			return super.onBlockPlacement(player, world, itemstack,
					pos, facing, vec);
		}
	}
	
	@Override
    public boolean onPlayerDamageBlock(BlockPos pos, EnumFacing facing)
	{
		EventDamageBlock event = new EventDamageBlock(pos, facing);
		EventManager.call(event);
		
		if(!event.isCancelled()){
			return super.onPlayerDamageBlock(pos, facing);
		}else{
			return false;
		}
	}
	
	@Override
	public boolean clickBlock(BlockPos pos, EnumFacing facing) {
		
		EventClickBlock event = new EventClickBlock(pos, facing);
		EventManager.call(event);
		
		if(!event.isCancelled()){
			return super.clickBlock(pos, facing);
		}else{
			return false;
		}
	}

	private Mod reach = Client.getClient().getModManager().getMod(Reach.class);
	
	@Override
	public float getBlockReachDistance() {
		
		if(reach.isEnabled()){
			return 20;
		}else{
			return super.getBlockReachDistance();
		}
		
	}
	
}
