package seigneurnecron.minecraftmods.stargate.tools.worldData;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import seigneurnecron.minecraftmods.stargate.tools.loadable.StargateCoordinates;

public final class WorldStargateData extends WorldDataList<StargateCoordinates> {
	
	private static final String IDENTIFIER = "worldStargateData";
	
	public static WorldStargateData get(World world) {
		MapStorage storage = world.perWorldStorage;
		WorldStargateData worldData = (WorldStargateData) storage.loadData(WorldStargateData.class, IDENTIFIER);
		
		if(worldData == null) {
			worldData = new WorldStargateData();
			storage.setData(IDENTIFIER, worldData);
		}
		
		return worldData;
	}
	
	public WorldStargateData() {
		super(IDENTIFIER);
	}
	
	@Override
	protected StargateCoordinates getElement(NBTTagCompound tag) {
		return new StargateCoordinates(tag);
	}
	
}
