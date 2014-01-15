package seigneurnecron.minecraftmods.stargate.tileentity.console;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityConsoleBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
public abstract class ConsoleScreen extends Console {
	
	// Constructors :
	
	protected ConsoleScreen(TileEntityConsoleBase tileEntity) {
		super(tileEntity);
	}
	
	// Methods :
	
	@Override
	public boolean openGui(World world, int x, int y, int z, EntityPlayer player) {
		StargateMod.proxy.openGui(this, player);
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	public abstract GuiScreen getGui(EntityPlayer player);
	
}
