package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargateControl;
import seigneurnecron.minecraftmods.stargate.tools.enums.GateState;

/**
 * @author Seigneur Necron
 */
public class BlockVortex extends BlockPortal {
	
	public BlockVortex(String name) {
		super(name);
	}
	
	/**
	 * Warns the gate that one of the vortex blocks was destroyed by an explosion.
	 */
	@Override
	public void onBlockExploded(World world, int x, int y, int z, Explosion explosion) {
		if(!world.isRemote) {
			TileEntityStargateControl gate = this.getControlUnit(world, x, y, z);
			if(gate != null) {
				gate.close();
			}
		}
		
		super.onBlockExploded(world, x, y, z, explosion);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return null;
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		if(!world.isRemote) {
			TileEntityStargateControl gate = this.getControlUnit(world, x, y, z);
			
			if(gate != null && gate.getState() == GateState.INPUT && entity.ridingEntity == null && entity.riddenByEntity == null) {
				gate.teleportEntity(entity);
			}
		}
	}
	
}
