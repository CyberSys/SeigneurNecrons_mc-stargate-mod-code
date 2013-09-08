package seigneurnecron.minecraftmods.stargate.tools.worldData;

import java.util.logging.Level;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargateControl;
import seigneurnecron.minecraftmods.stargate.tools.loadable.StargateCoordinates;

/**
 * @author Seigneur Necron
 */
public final class WorldStargateData extends WorldDataList<StargateCoordinates> {
	
	private static final String IDENTIFIER = "worldStargateData";
	
	public static WorldStargateData get(World world) {
		MapStorage storage = world.perWorldStorage;
		WorldStargateData worldData = (WorldStargateData) storage.loadData(WorldStargateData.class, IDENTIFIER);
		
		if(worldData == null) {
			worldData = new WorldStargateData();
			storage.setData(IDENTIFIER, worldData);
		}
		
		worldData.world = world;
		return worldData;
	}
	
	/**
	 * You must have a constructor of this form, else you will get a NoSuchMethodException.
	 * @param indentifier - the property indentifier.
	 */
	public WorldStargateData(String indentifier) {
		super(indentifier);
	}
	
	public WorldStargateData() {
		this(IDENTIFIER);
	}
	
	@Override
	protected StargateCoordinates getElement(NBTTagCompound tag) {
		return new StargateCoordinates(tag);
	}
	
	private StargateCoordinates getElementLike(StargateCoordinates address) {
		int index = this.dataList.indexOf(address);
		
		if(index >= 0) {
			StargateCoordinates stargate = this.dataList.get(index);
			
			if(stargate != null) {
				TileEntity tileEntity = this.world.getBlockTileEntity(stargate.x, stargate.y, stargate.z);
				
				if(tileEntity != null && tileEntity instanceof TileEntityStargateControl) {
					return stargate;
				}
			}
			
			StargateMod.debug("An invalid stargate was registered in the world. This is not normal.", Level.WARNING, true);
			this.removeElement(stargate);
		}
		
		return null;
	}
	
	public boolean isAvailable(StargateCoordinates address) {
		return getElementLike(address) == null;
	}
	
	public boolean checkRegistered(StargateCoordinates address) {
		StargateMod.debug("World ok : " + (this.world == StargateMod.getServerWorldForDimension(this.world.provider.dimensionId)), true); // FIXME - delete.
		
		StargateCoordinates stargate = this.getElementLike(address);
		
		if(stargate == null) {
			StargateMod.debug("A valid stargate wasn't registered in the world. This is not normal.", Level.WARNING, true);
			this.addElement(address);
			return true;
		}
		
		return (stargate.x == address.x) && (stargate.y == address.y) && (stargate.z == address.z);
	}
	
	public StargateCoordinates getCoordinates(String address) {
		return this.getElementLike(new StargateCoordinates(address, 0, 0, 0, 0));
	}
	
}
