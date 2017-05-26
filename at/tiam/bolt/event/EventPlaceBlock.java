package at.tiam.bolt.event;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;

import com.darkmagician6.eventapi.events.callables.EventCancellable;

public class EventPlaceBlock extends EventCancellable{
	
	private BlockPos pos;
	private EnumFacing facing;
	private ItemStack stack;
	private Vec3d vec;
	
	public EventPlaceBlock(BlockPos pos, EnumFacing facing, ItemStack stack,
			Vec3d vec) {
		super();
		this.pos = pos;
		this.facing = facing;
		this.stack = stack;
		this.vec = vec;
	}

	public BlockPos getPos() {
		return pos;
	}

	public void setPos(BlockPos pos) {
		this.pos = pos;
	}

	public EnumFacing getFacing() {
		return facing;
	}

	public void setFacing(EnumFacing facing) {
		this.facing = facing;
	}

	public ItemStack getStack() {
		return stack;
	}

	public void setStack(ItemStack stack) {
		this.stack = stack;
	}

	public Vec3d getVec() {
		return vec;
	}

	public void setVec(Vec3d vec) {
		this.vec = vec;
	}
}
