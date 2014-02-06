package seigneurnecron.minecraftmods.stargate.item.staff;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.entity.fireball.EntityFireballBasic;
import seigneurnecron.minecraftmods.stargate.item.ItemStargate;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class ItemFireStaffBasic extends ItemStargate {
	
	// Constructors :
	
	public ItemFireStaffBasic(String name) {
		super(name);
		this.maxStackSize = 1;
	}
	
	// Methods :
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
		world.playSoundAtEntity(entityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		
		if(!world.isRemote) {
			world.spawnEntityInWorld(this.getProjectile(itemStack, world, entityPlayer));
		}
		
		return itemStack;
	}
	
	protected Entity getProjectile(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
		return new EntityFireballBasic(world, entityPlayer);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isFull3D() {
		return true;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.block;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack itemStack) {
		return EnumRarity.epic;
	}
	
}
