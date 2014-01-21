package seigneurnecron.minecraftmods.stargate.tileentity.console;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.inventory.InventoryStuffLevelUpTable;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityConsoleBase;
import seigneurnecron.minecraftmods.stargate.tools.enchant.EnchantmentTools;
import seigneurnecron.minecraftmods.stargate.tools.loadable.PowerUp;

/**
 * @author Seigneur Necron
 */
public class ConsoleStuffLevelUpTable extends ConsoleContainer<InventoryStuffLevelUpTable> {
	
	// NBTTags names :
	
	private static final String NB_BOOKS = "nbBooks";
	private static final String ENCHANTMENTS = "enchantments";
	
	// Fields :
	
	/**
	 * The number of nearby bookcases.
	 */
	private int nbBooks = 0;
	
	/**
	 * The list of applicable enchantments, with thier levels and costs.
	 */
	private List<PowerUp> enchantments = new LinkedList<PowerUp>();
	
	// Constructors :
	
	public ConsoleStuffLevelUpTable(TileEntityConsoleBase tileEntity) {
		super(tileEntity);
	}
	
	// Getters :
	
	/**
	 * Returns the number of nearby bookcases.
	 * @return the number of nearby bookcases.
	 */
	public int getNbBooks() {
		return this.nbBooks;
	}
	
	/**
	 * Returns the list of applicable enchantments, with thier levels and costs.
	 * @return the list of applicable enchantments, with thier levels and costs.
	 */
	public List<PowerUp> getEnchantments() {
		return this.enchantments;
	}
	
	// Setters :
	
	/**
	 * Updates the number of nearby bookcases.
	 * @param nbBooks - the number of nearby bookcases.
	 */
	public void setNbBooks(int nbBooks) {
		this.nbBooks = nbBooks;
	}
	
	/**
	 * Updates the list of applicable enchantments, with thier levels and costs.
	 * @param enchantments - the list of applicable enchantments, with thier levels and costs.
	 */
	public void setEnchantments(LinkedList<PowerUp> enchantments) {
		Collections.sort(enchantments);
		this.enchantments = enchantments;
	}
	
	// Methods :
	
	public void updateEnchantInfo() {
		World world = this.tileEntity.worldObj;
		
		if(!world.isRemote) {
			int x = this.tileEntity.xCoord;
			int y = this.tileEntity.yCoord;
			int z = this.tileEntity.zCoord;
			ItemStack itemStack = this.inventory.getStackInSlot(0);
			int nbBooks = 0;
			
			for(int i = -2; i <= 2; i++) {
				for(int j = -2; j <= 2; j++) {
					if((Math.abs(i) == 2) ^ (Math.abs(j) == 2)) {
						for(int k = 0; k <= 1; k++) {
							if(world.getBlockId(x + i, y + k, z + j) == Block.bookShelf.blockID && world.isAirBlock(x + ((Math.abs(i) == 2) ? (i / 2) : i), y + k, z + ((Math.abs(j) == 2) ? (j / 2) : j))) {
								nbBooks++;
							}
						}
					}
				}
			}
			
			this.setNbBooks(nbBooks);
			this.setEnchantments(EnchantmentTools.getAvailableEnchantments(itemStack));
			this.tileEntity.onConsoleDataChanged();
		}
	}
	
	@Override
	protected InventoryStuffLevelUpTable getNewInventory() {
		return new InventoryStuffLevelUpTable(this.tileEntity, this);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.nbBooks = compound.getInteger(NB_BOOKS);
		
		NBTTagList enchantments = compound.getTagList(ENCHANTMENTS);
		this.enchantments = new LinkedList<PowerUp>();
		
		for(int i = 0; i < enchantments.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) enchantments.tagAt(i);
			PowerUp element = new PowerUp(tag);
			this.enchantments.add(element);
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger(NB_BOOKS, this.nbBooks);
		
		NBTTagList enchantments = new NBTTagList();
		compound.setTag(ENCHANTMENTS, enchantments);
		
		for(PowerUp powerUp : this.enchantments) {
			NBTTagCompound tag = new NBTTagCompound();
			powerUp.saveNBTData(tag);
			enchantments.appendTag(tag);
		}
	}
	
}
