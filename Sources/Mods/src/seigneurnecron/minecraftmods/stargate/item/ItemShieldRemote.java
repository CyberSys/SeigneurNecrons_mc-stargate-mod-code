package seigneurnecron.minecraftmods.stargate.item;

import java.util.LinkedList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargateControl;
import seigneurnecron.minecraftmods.stargate.tileentity.console.ConsoleStargate;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class ItemShieldRemote extends ItemStargate {
	
	// Constructors :
	
	public ItemShieldRemote(String name) {
		super(name);
	}
	
	// Methods :
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		StargateMod.proxy.openGui(this, world, player);
		return itemStack;
	}
	
	/**
	 * Searches for stargates in the area and returns the nearest one.
	 */
	public TileEntityStargateControl getNearestActivatedGate(World world, int xCoord, int yCoord, int zCoord) {
		final int maxRange = ConsoleStargate.MAX_RANGE;
		
		// Searches all the control units within range.
		LinkedList<TileEntityStargateControl> controlUnitsList = new LinkedList<TileEntityStargateControl>();
		
		// Searches in a cube with a side length of MAX_RANGE.
		for(int i = -maxRange; i <= maxRange; ++i) {
			for(int j = -maxRange; j <= maxRange; ++j) {
				for(int k = -maxRange; k <= maxRange; ++k) {
					int x = xCoord + k;
					int y = yCoord + i;
					int z = zCoord + j;
					
					// If the block is a control unit, adds it to the list of found stargates.
					if(world.getBlockId(x, y, z) == StargateMod.block_stargateControl.blockID) {
						TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
						
						if(tileEntity != null && tileEntity instanceof TileEntityStargateControl) {
							TileEntityStargateControl controlUnit = (TileEntityStargateControl) tileEntity;
							
							if(controlUnit.isActivated()) {
								controlUnitsList.add(controlUnit);
							}
						}
					}
				}
			}
		}
		
		// If at least one control unit was found.
		if(controlUnitsList.size() > 0) {
			// Takes the first control unit.
			TileEntityStargateControl controlUnit = controlUnitsList.get(0);
			
			// Goes through the list, looking for the nearest control unit.
			for(int i = 1; i < controlUnitsList.size(); i++) {
				TileEntityStargateControl controlUnit2 = controlUnitsList.get(i);
				
				// If the new control unit is closer than the previous one, takes it.
				if(controlUnit2.squaredDistance(xCoord, yCoord, zCoord) < controlUnit.squaredDistance(xCoord, yCoord, zCoord)) {
					controlUnit = controlUnit2;
				}
			}
			
			return controlUnit;
		}
		else {
			return null;
		}
	}
	
}
