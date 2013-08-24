package seigneurnecron.minecraftmods.stargate.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.entity.EntityCustomFireBall;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCustomFireBall extends ItemStargate {
	
	public ItemCustomFireBall(String name) {
		super(name);
		this.func_111206_d("fireball");
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
		world.playSoundAtEntity(entityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		
		if(!world.isRemote) {
			world.spawnEntityInWorld(this.getProjectile(itemStack, world, entityPlayer));
			
			if(!entityPlayer.capabilities.isCreativeMode) {
				itemStack.stackSize--;
			}
		}
		
		return itemStack;
	}
	
	protected Entity getProjectile(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
		return new EntityCustomFireBall(world, entityPlayer);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon(this.func_111208_A()); // getIconName()
	}
	
}
