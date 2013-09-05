package seigneurnecron.minecraftmods.stargate.client.network;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class StargateClientPacketHandler extends StargatePacketHandler {
	
	@Override
	protected World getWorldForDimension(int dim) {
		WorldClient world = ModLoader.getMinecraftInstance().theWorld;
		
		if(world != null && world.provider.dimensionId != dim) {
			return null;
		}
		
		return world;
	}
	
}
