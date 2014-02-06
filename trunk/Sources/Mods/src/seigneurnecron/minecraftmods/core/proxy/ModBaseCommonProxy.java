package seigneurnecron.minecraftmods.core.proxy;

import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class ModBaseCommonProxy {
	
	// Methods :
	
	/**
	 * Returns the world corresponding to that side and the given dimension id, if it exists.
	 * @param dim - the dimension id.
	 * @return the world corresponding to the side and the dimension id if it exists, else null.
	 */
	public World getSideWorldForDimension(int dim) {
		return this.getServerWorldForDimension(dim);
	}
	
	/**
	 * Returns the server world corresponding to the given dimension id, if it exists.
	 * @param dim - the dimension id.
	 * @return the server world corresponding to the dimension id if it exists, else null.
	 */
	protected World getServerWorldForDimension(int dim) {
		return FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(dim);
	}
	
}
