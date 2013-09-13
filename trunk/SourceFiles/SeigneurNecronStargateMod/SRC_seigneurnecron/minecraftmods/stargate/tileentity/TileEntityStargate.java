package seigneurnecron.minecraftmods.stargate.tileentity;

import seigneurnecron.minecraftmods.core.tileentity.TileEntityCommand;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.network.packetmapping.StargateTileEntityPacketMapping;

/**
 * @author Seigneur Necron
 */
public abstract class TileEntityStargate extends TileEntityCommand {
	
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
