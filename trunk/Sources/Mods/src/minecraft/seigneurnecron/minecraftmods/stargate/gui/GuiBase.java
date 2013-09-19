package seigneurnecron.minecraftmods.stargate.gui;

import static seigneurnecron.minecraftmods.stargate.tileentity.TileEntityBase.INV_NAME;
import net.minecraft.entity.player.EntityPlayer;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public abstract class GuiBase<T extends TileEntityBase> extends GuiScreen<T> {
	
	// ####################################################################################################
	// Lang constants :
	// ####################################################################################################
	
	public static final String DESTINATION = INV_NAME + ".destination";
	public static final String NAME = INV_NAME + ".name";
	public static final String ADD_THIS = INV_NAME + ".addThis";
	public static final String ADD = INV_NAME + ".add";
	public static final String DELETE = INV_NAME + ".delete";
	public static final String OVERWRITE = INV_NAME + ".overwrite";
	public static final String TAB = INV_NAME + ".tab";
	public static final String ALL = INV_NAME + ".all";
	
	// ####################################################################################################
	// Data fields :
	// ####################################################################################################
	
	protected EntityPlayer player;
	
	// ####################################################################################################
	// Builder :
	// ####################################################################################################
	
	protected GuiBase(T tileEntity, EntityPlayer player) {
		super(tileEntity);
		this.player = player;
	}
	
	// ####################################################################################################
	// Interface definition :
	// ####################################################################################################
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		if(!this.tileEntity.isIntact()) {
			this.close();
		}
		
		super.drawScreen(par1, par2, par3);
	}
	
	// ####################################################################################################
	// User input :
	// ####################################################################################################
	
	@Override
	protected boolean closeLikeInventory() {
		return false;
	}
	
}
