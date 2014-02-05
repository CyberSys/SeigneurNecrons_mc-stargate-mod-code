package seigneurnecron.minecraftmods.stargate.item.fireball;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.entity.fireball.EntityFireballBasic;
import seigneurnecron.minecraftmods.stargate.item.ItemStargate;
import seigneurnecron.minecraftmods.stargate.tools.comparator.ItemComparator;

import com.google.common.collect.Lists;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
public class ItemFireballBasic extends ItemStargate {
	
	// Static part :
	
	private static final SortedSet<Item> craftableFireballs = new TreeSet<Item>(new ItemComparator());
	
	static {
		craftableFireballs.add(Item.fireballCharge);
	}
	
	public static List<Item> getCraftableFireballs() {
		return Lists.newArrayList(craftableFireballs);
	}
	
	public static void registerCraftableFireball(ItemFireballBasic fireball) {
		craftableFireballs.add(fireball);
	}
	
	// Constructors :
	
	public ItemFireballBasic(String name) {
		super(name);
		this.setTextureName("fireball");
	}
	
	// Methods :
	
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
		return new EntityFireballBasic(world, entityPlayer);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon(this.getIconString());
	}
	
}
