package seigneurnecron.minecraftmods.stargate.tileentity;

import seigneurnecron.minecraftmods.core.inventory.InventoryBasic;
import seigneurnecron.minecraftmods.core.tileentity.TileEntityContainer;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.network.packetmapping.StargateTileEntityPacketMapping;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public abstract class TileEntityContainerStargate<T extends InventoryBasic> extends TileEntityContainer<T> {
	
	// Methods :
	
	@Override
	protected int getTileEntityPacketId() {
		return StargateTileEntityPacketMapping.getInstance().getId(this.getClass());
	}
	
	@Override
	protected String getTileEntityChanel() {
		return StargateMod.CHANEL_TILE_ENTITY;
	}
	
	@Override
	protected String getCommandChanel() {
		return StargateMod.CHANEL_COMMANDS;
	}
	
}
