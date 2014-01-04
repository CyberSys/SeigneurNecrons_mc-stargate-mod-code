package seigneurnecron.minecraftmods.stargate.gui;

import net.minecraft.entity.player.EntityPlayer;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityConsoleBase;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargateControl;
import seigneurnecron.minecraftmods.stargate.tileentity.console.ConsoleStargate;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public abstract class GuiStargateConsole<T extends ConsoleStargate> extends GuiConsolePanel<T> {
	
	// ####################################################################################################
	// Data fields :
	// ####################################################################################################
	
	protected TileEntityStargateControl stargate = null;
	protected boolean stargateConnected = false;
	
	// ####################################################################################################
	// Builder :
	// ####################################################################################################
	
	protected GuiStargateConsole(TileEntityConsoleBase tileEntity, EntityPlayer player, T console) {
		super(tileEntity, player, console);
	}
	
	// ####################################################################################################
	// Interface definition :
	// ####################################################################################################
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
		this.updateConnectedStargate();
	}
	
	@Override
	public void initComponents() {
		super.initComponents();
		this.updateConnectedStargate();
	}
	
	@Override
	protected void onGuiInitialized() {
		super.onGuiInitialized();
		this.updateStargateInterface();
	}
	
	// ####################################################################################################
	// Utility :
	// ####################################################################################################
	
	protected void updateConnectedStargate() {
		this.stargate = this.console.getStargateControl();
		boolean stargateConnected = this.stargate != null;
		
		if(this.stargateConnected != stargateConnected) {
			this.stargateConnected = stargateConnected;
			
			if(this.isInitialized()) {
				this.updateStargateInterface();
			}
		}
	}
	
	protected void updateStargateInterface() {
		// Nothing here.
	}
	
}
