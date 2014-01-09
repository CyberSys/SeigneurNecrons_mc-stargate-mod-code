package seigneurnecron.minecraftmods.stargate.gui;

import net.minecraft.entity.player.EntityPlayer;
import seigneurnecron.minecraftmods.core.gui.GuiScreenTileEntity;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityConsoleBase;
import seigneurnecron.minecraftmods.stargate.tileentity.console.Console;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public abstract class GuiScreenConsolePanel<T extends Console> extends GuiScreenTileEntity<TileEntityConsoleBase> {
	
	// ####################################################################################################
	// Lang constants :
	// ####################################################################################################
	
	public static final String INV_NAME = "container.base";
	
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
	
	protected final EntityPlayer player;
	protected final T console;
	
	// ####################################################################################################
	// Constructors :
	// ####################################################################################################
	
	protected GuiScreenConsolePanel(TileEntityConsoleBase tileEntity, EntityPlayer player, T console) {
		super(tileEntity);
		this.player = player;
		this.console = console;
	}
	
	// ####################################################################################################
	// Interface definition :
	// ####################################################################################################
	
	@Override
	protected boolean isGuiValid() {
		return super.isGuiValid() && this.tileEntity.isIntact() && this.console.isValid();
	}
	
	// ####################################################################################################
	// User input :
	// ####################################################################################################
	
	@Override
	protected boolean closeLikeInventory() {
		return false;
	}
	
}
