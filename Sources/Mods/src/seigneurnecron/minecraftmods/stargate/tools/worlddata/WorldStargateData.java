package seigneurnecron.minecraftmods.stargate.tools.worlddata;

import java.util.logging.Level;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import seigneurnecron.minecraftmods.core.mod.ModBase;
import seigneurnecron.minecraftmods.core.worlddata.WorldDataList;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargateControl;
import seigneurnecron.minecraftmods.stargate.tools.enums.GateState;
import seigneurnecron.minecraftmods.stargate.tools.loadable.StargateCoordinates;

/**
 * @author Seigneur Necron
 */
public final class WorldStargateData extends WorldDataList<StargateCoordinates> {
	
	// Constants :
	
	private static final String IDENTIFIER = "worldStargateData";
	
	// Singleton :
	
	protected static WorldStargateData instance;
	
	private static World getWorld() {
		return ModBase.getServerWorldForDimension(0);
	}
	
	public static WorldStargateData getInstance() {
		WorldStargateData worldData = instance;
		
		if(worldData == null) {
			MapStorage storage = getWorld().perWorldStorage;
			worldData = (WorldStargateData) storage.loadData(WorldStargateData.class, IDENTIFIER);
			
			if(worldData == null) {
				worldData = new WorldStargateData();
				storage.setData(IDENTIFIER, worldData);
			}
			
			instance = worldData;
		}
		
		return worldData;
	}
	
	// Methods :
	
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
				World world = ModBase.getServerWorldForDimension(stargate.dim);
				
				if(world != null) {
					TileEntity tileEntity = world.getBlockTileEntity(stargate.x, stargate.y, stargate.z);
					
					if(tileEntity != null && tileEntity instanceof TileEntityStargateControl && ((TileEntityStargateControl) tileEntity).getState() != GateState.BROKEN) {
						return stargate;
					}
				}
			}
			
			StargateMod.instance.log("An invalid stargate was registered in the world. This is not normal.", Level.WARNING);
			this.removeElement(stargate);
		}
		
		return null;
	}
	
	public boolean isAvailable(StargateCoordinates address) {
		return this.getElementLike(address) == null;
	}
	
	public StargateCoordinates checkRegistered(StargateCoordinates address) {
		StargateCoordinates stargate = this.getElementLike(address);
		
		if(stargate == null) {
			StargateMod.instance.log("A valid stargate wasn't registered in the world. This is not normal.", Level.WARNING);
			this.addElement(address);
			
			stargate = this.getElementLike(address);
			return stargate;
		}
		else if((stargate.dim == address.dim) && (stargate.x == address.x) && (stargate.y == address.y) && (stargate.z == address.z)) {
			return stargate;
		}
		else {
			return null;
		}
	}
	
	public StargateCoordinates getCoordinates(String address) {
		return this.getElementLike(new StargateCoordinates(address, 0, 0, 0, 0));
	}
	
}
