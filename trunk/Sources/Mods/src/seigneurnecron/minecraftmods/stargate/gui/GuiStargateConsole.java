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
public abstract class GuiStargateConsole<T extends ConsoleStargate> extends GuiScreenConsolePanel<T> {
	
	// ####################################################################################################
	// Data fields :
	// ####################################################################################################
	
	protected TileEntityStargateControl stargate = null;
	protected boolean stargateConnected = false;
	
	// ####################################################################################################
	// Constructors :
	// ####################################################################################################
	
	protected GuiStargateConsole(TileEntityConsoleBase tileEntity, EntityPlayer player, T console) {
		super(tileEntity, player, console);
	}
	
	// ####################################################################################################
	// Interface definition :
	// ####################################################################################################
	
	@Override
	protected void updateComponents() {
		super.updateComponents();
		this.updateConnectedStargate();
	}
	
	@Override
	protected void initComponents() {
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
