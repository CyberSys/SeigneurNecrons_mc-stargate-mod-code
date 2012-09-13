package mods.stargate;

import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemStargatePlaceable extends ItemStargate {
	
	/**
	 * L'id du block à placer.
	 */
	protected int BlockId;
	
	/**
	 * Indique si la ternière tentative de plaçage a réussi.
	 */
	protected boolean succes;
	
	public ItemStargatePlaceable(int id, int iconIdex, String name, CreativeTabs tab, Block block) {
		super(id, iconIdex, name, tab);
		this.BlockId = block.blockID;
		this.succes = false;
	}
	
	public ItemStargatePlaceable(int id, int iconIdex, String name, Block block) {
		this(id, iconIdex, name, CreativeTabs.tabRedstone, block);
	}
	
	/**
	 * Tente de placer l'objet correspondant à cet item dans le monde.
	 */
	@Override
	public boolean tryPlaceIntoWorld(ItemStack itemStack, EntityPlayer entityPlayer, World world, int x, int y, int z, int side, float par8, float par9, float par10) {
		this.succes = false;
		
		int blockId = world.getBlockId(x, y, z);
		
		if(blockId == Block.snow.blockID) {
			side = 1;
		}
		else if(blockId != Block.vine.blockID && blockId != Block.tallGrass.blockID && blockId != Block.deadBush.blockID) {
			if(side == 0) {
				--y;
			}
			
			if(side == 1) {
				++y;
			}
			
			if(side == 2) {
				--z;
			}
			
			if(side == 3) {
				++z;
			}
			
			if(side == 4) {
				--x;
			}
			
			if(side == 5) {
				++x;
			}
		}
		
		if(!entityPlayer.canPlayerEdit(x, y, z)) {
			return false;
		}
		else if(itemStack.stackSize == 0) {
			return false;
		}
		else if(world.canPlaceEntityOnSide(this.BlockId, x, y, z, false, side, entityPlayer)) {
			Block block = Block.blocksList[this.BlockId];
			
			if(world.setBlockWithNotify(x, y, z, this.BlockId)) {
				if(world.getBlockId(x, y, z) == this.BlockId) {
					block.updateBlockMetadata(world, x, y, z, side, par8, par9, par10);
					block.onBlockPlacedBy(world, x, y, z, entityPlayer);
					this.succes = true;
				}
				world.playSoundEffect((double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F), block.stepSound.getStepSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
				--itemStack.stackSize;
			}
			
			return true;
		}
		return false;
	}
	
}
