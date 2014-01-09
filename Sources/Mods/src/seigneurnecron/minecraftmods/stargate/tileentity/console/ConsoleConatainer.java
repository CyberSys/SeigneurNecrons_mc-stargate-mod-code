package seigneurnecron.minecraftmods.stargate.tileentity.console;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.core.inventory.InventoryBasic;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.proxy.StargateCommonProxy;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityConsoleBase;

/**
 * @author Seigneur Necron
 */
public abstract class ConsoleConatainer<T extends InventoryBasic> extends Console {
	
	// Fields :
	
	protected T inventory;
	
	// Constructors :
	
	protected ConsoleConatainer(TileEntityConsoleBase tileEntity) {
		super(tileEntity);
		this.inventory = this.getNewInventory();
	}
	
	// Getters :
	
	public T getInventory() {
		return this.inventory;
	}
	
	// Methods :
	
	/**
	 * Returns a new inventory used to initialize this tile entity.
	 * @return a new inventory used to initialize this tile entity.
	 */
	protected abstract T getNewInventory();
	
	@Override
	public boolean openGui(World world, int x, int y, int z, EntityPlayer player) {
		if(!world.isRemote) {
			player.openGui(StargateMod.instance, StargateCommonProxy.A_CONSOLE, world, x, y, z);
		}
		
		return true;
	}
	
	@Override
	public void onConsoleDestroyed() {
		this.inventory.dropContent();
		super.onConsoleDestroyed();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.inventory.readFromNBT(compound);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		this.inventory.writeToNBT(compound);
	}
	
}
