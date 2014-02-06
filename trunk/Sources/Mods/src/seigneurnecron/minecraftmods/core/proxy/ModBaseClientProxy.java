package seigneurnecron.minecraftmods.core.proxy;

import net.minecraft.world.World;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class ModBaseClientProxy extends ModBaseCommonProxy {
	
	// Methods :
	
	@Override
	public World getSideWorldForDimension(int dim) {
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
			return getServerWorldForDimension(dim);
		}
		else {
			return getClientWorldForDimension(dim);
		}
	}
	
	/**
	 * Returns the client world corresponding to the given dimension id, if it exists.
	 * @param dim - the dimension id.
	 * @return the client world corresponding to the dimension id if it exists, else null.
	 */
	protected World getClientWorldForDimension(int dim) {
		World world = FMLClientHandler.instance().getClient().theWorld;
		
		if(world != null && world.provider.dimensionId != dim) {
			return null;
		}
		
		return world;
	}
	
}
