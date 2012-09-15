package mods.necron.stargate;

import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemStargatePlaceable extends ItemStargate {
	
	/**
	 * L'id du block a placer.
	 */
	protected int BlockId;
	
	/**
	 * Indique si la terniere tentative de plaçage a reussi.
	 */
	protected boolean succes;
	
	/**
	 * Indique la position en X où le bloque doit etre place.
	 */
	protected int xPlaced;
	
	/**
	 * Indique la position en Y où le bloque doit etre place.
	 */
	protected int yPlaced;
	
	/**
	 * Indique la position en Z où le bloque doit etre place.
	 */
	protected int zPlaced;
	
	public ItemStargatePlaceable(int id, int iconIdex, String name, CreativeTabs tab, Block block) {
		super(id, iconIdex, name, tab);
		this.BlockId = block.blockID;
		this.succes = false;
	}
	
	public ItemStargatePlaceable(int id, int iconIdex, String name, Block block) {
		this(id, iconIdex, name, CreativeTabs.tabRedstone, block);
	}
	
	/**
	 * Definie la position où le block doit etre place.
	 * @param x - la position en X où le bloque doit etre place.
	 * @param y - la position en Y où le bloque doit etre place.
	 * @param z - la position en Z où le bloque doit etre place.
	 */
	private void setPlacedPosition(int x, int y, int z) {
		this.xPlaced = x;
		this.yPlaced = y;
		this.zPlaced = z;
	}
	
	/**
	 * Tente de placer l'objet correspondant a cet item dans le monde.
	 */
	@Override
	public boolean tryPlaceIntoWorld(ItemStack itemStack, EntityPlayer entityPlayer, World world, int x, int y, int z, int side, float par8, float par9, float par10) {
		this.succes = false;
		
		this.setPlacedPosition(x, y, z);
		
		int blockId = world.getBlockId(this.xPlaced, this.yPlaced, this.zPlaced);
		
		if(blockId == Block.snow.blockID) {
			side = 1;
		}
		else if(blockId != Block.vine.blockID && blockId != Block.tallGrass.blockID && blockId != Block.deadBush.blockID) {
			if(side == 0) {
				--this.yPlaced;
			}
			
			if(side == 1) {
				++this.yPlaced;
			}
			
			if(side == 2) {
				--this.zPlaced;
			}
			
			if(side == 3) {
				++this.zPlaced;
			}
			
			if(side == 4) {
				--this.xPlaced;
			}
			
			if(side == 5) {
				++this.xPlaced;
			}
		}
		
		if(!entityPlayer.canPlayerEdit(this.xPlaced, this.yPlaced, this.zPlaced)) {
			return false;
		}
		else if(itemStack.stackSize == 0) {
			return false;
		}
		else if(world.canPlaceEntityOnSide(this.BlockId, this.xPlaced, this.yPlaced, this.zPlaced, false, side, entityPlayer)) {
			Block block = Block.blocksList[this.BlockId];
			
			if(world.setBlockWithNotify(this.xPlaced, this.yPlaced, this.zPlaced, this.BlockId)) {
				if(world.getBlockId(this.xPlaced, this.yPlaced, this.zPlaced) == this.BlockId) {
					block.updateBlockMetadata(world, this.xPlaced, this.yPlaced, this.zPlaced, side, par8, par9, par10);
					block.onBlockPlacedBy(world, this.xPlaced, this.yPlaced, this.zPlaced, entityPlayer);
					this.succes = true;
				}
				world.playSoundEffect((double) ((float) this.xPlaced + 0.5F), (double) ((float) this.yPlaced + 0.5F), (double) ((float) this.zPlaced + 0.5F), block.stepSound.getStepSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
				--itemStack.stackSize;
			}
			
			return true;
		}
		return false;
	}
	
}
