package seigneurnecron.minecraftmods.stargate.tileentity;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.inventory.InventoryConsoleBase;
import seigneurnecron.minecraftmods.stargate.item.ItemCrystal;
import seigneurnecron.minecraftmods.stargate.tileentity.console.Console;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class TileEntityConsoleBase extends TileEntityContainerStargate<InventoryConsoleBase> {
	
	// NBTTags names :
	
	private static final String CONSOLE = "console";
	
	// Fields :
	
	protected Console console;
	
	// Getters :
	
	public Console getConsole() {
		return this.console;
	}
	
	// Methods :
	
	@Override
	protected InventoryConsoleBase getNewInventory() {
		return new InventoryConsoleBase(this);
	}
	
	/**
	 * Returns the list of the crystals inserted in this console.
	 * @return the list of the crystals inserted in this console.
	 */
	public ArrayList<ItemCrystal> getCrystals() {
		return this.getInventory().getCrystals();
	}
	
	/**
	 * Triger a tile entity update.
	 */
	public void onConsoleDataChanged() {
		if(this.worldObj != null && !this.worldObj.isRemote) {
			this.setChanged();
			this.update();
		}
	}
	
	/**
	 * Indicates whether the console is intact.
	 * @return true if the console is intact, else false.
	 */
	public boolean isIntact() {
		return this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord) == StargateMod.block_consoleBase.blockID && this.worldObj.getBlockId(this.xCoord, this.yCoord + 1, this.zCoord) == StargateMod.block_consolePanel.blockID;
	}
	
	/**
	 * Indicates whether the console interface is matching the cristals.
	 * @return true if the console interface is matching the cristals, else false.
	 */
	public boolean checkConsole() {
		Class<? extends Console> clazz = Console.getConsoleClass(this.getCrystals());
		
		if(this.console == null || this.console.getClass() != clazz) {
			this.console = Console.getConsole(clazz, this);
		}
		
		return this.console != null;
	}
	
	/**
	 * Open the gui corresponding to the cristal set.
	 * @return true if the gui was succesfully opened, else false.
	 */
	public boolean openGui(World world, int x, int y, int z, EntityPlayer player) {
		if(this.checkConsole()) {
			return this.console.openGui(world, x, y, z, player);
		}
		
		return false;
	}
	
	/**
	 * Do the stuff that has to be done when the console is destroyed.
	 */
	public void onConsoleDestroyed() {
		if(this.checkConsole()) {
			this.console.onConsoleDestroyed();
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		if(this.checkConsole()) {
			NBTTagCompound tag = compound.getCompoundTag(CONSOLE);
			this.console.readFromNBT(tag);
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		if(this.checkConsole()) {
			NBTTagCompound tag = new NBTTagCompound();
			this.console.writeToNBT(tag);
			compound.setCompoundTag(CONSOLE, tag);
		}
	}
	
}
