package seigneurnecron.minecraftmods.stargate.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityBaseStargateConsole;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargateControl;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public abstract class GuiStargateConsole<T extends TileEntityBaseStargateConsole> extends GuiBase<T> {
	
	// ####################################################################################################
	// Data fields :
	// ####################################################################################################
	
	protected TileEntityStargateControl stargate = null;
	protected boolean stargateConnected = false;
	
	// ####################################################################################################
	// Builder :
	// ####################################################################################################
	
	protected GuiStargateConsole(T tileEntity, EntityPlayer player) {
		super(tileEntity, player);
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
		this.stargate = this.tileEntity.getStargateControl();
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
