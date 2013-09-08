package seigneurnecron.minecraftmods.stargate.client.network;

import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class StargateClientPacketHandler extends StargatePacketHandler {
	
	@Override
	public World getWorldForDimension(int dim) {
		return StargateMod.getClientWorldForDimension(dim);
	}
	
}
