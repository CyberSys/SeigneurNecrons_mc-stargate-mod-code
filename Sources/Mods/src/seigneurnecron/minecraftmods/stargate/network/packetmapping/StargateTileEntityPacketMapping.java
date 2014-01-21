package seigneurnecron.minecraftmods.stargate.network.packetmapping;

import seigneurnecron.minecraftmods.core.network.packetmapping.TileEntityPacketMapping;
import seigneurnecron.minecraftmods.core.tileentity.TileEntityBasic;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityChevron;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityConsoleBase;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityCrystalFactory;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityDetector;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityMobGenerator;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargateControl;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargatePart;

/**
 * @author Seigneur Necron
 */
public class StargateTileEntityPacketMapping extends TileEntityPacketMapping<TileEntityBasic> {
	
	// Static instance :
	
	private static StargateTileEntityPacketMapping instance;
	
	public static StargateTileEntityPacketMapping getInstance() {
		return instance;
	}
	
	// Constructors :
	
	public StargateTileEntityPacketMapping() {
		super(StargateMod.CHANEL_TILE_ENTITY);
		instance = this;
		
		this.register(TileEntityChevron.class);
		this.register(TileEntityConsoleBase.class);
		this.register(TileEntityDetector.class);
		this.register(TileEntityMobGenerator.class);
		this.register(TileEntityStargateControl.class);
		this.register(TileEntityCrystalFactory.class);
		this.register(TileEntityStargatePart.class);
	}
	
}
