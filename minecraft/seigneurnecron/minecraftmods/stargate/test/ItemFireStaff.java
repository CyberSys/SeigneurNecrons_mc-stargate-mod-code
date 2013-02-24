package seigneurnecron.minecraftmods.stargate.test;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.item.ItemStargate;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFireStaff extends ItemStargate {
	
	public ItemFireStaff(int id, int iconIdex, String name) {
		super(id, iconIdex, name, CreativeTabs.tabMisc);
		this.maxStackSize = 1;
	}
	
	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
		world.playSoundAtEntity(entityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		
		if(!world.isRemote) {
			world.spawnEntityInWorld(new EntityCustomFireBall(world, entityPlayer));
		}
		
		return itemStack;
	}
	
	/**
	 * Returns True is the item is renderer in full 3D when hold.
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public boolean isFull3D() {
		return true;
	}
	
	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.block;
	}
	
}
