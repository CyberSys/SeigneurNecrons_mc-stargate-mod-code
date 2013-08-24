package seigneurnecron.minecraftmods.stargate.item.stuff;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.StargateModConfig;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
public class ItemNaquadahPickaxe extends ItemPickaxe {
	
	public ItemNaquadahPickaxe(String name) {
		super(StargateModConfig.getItemId(name), StargateMod.naquadahToolMaterial);
		this.setUnlocalizedName(name);
		this.func_111206_d(name); // setIconName(name)
		this.setCreativeTab(StargateMod.stargateItemsTab);
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(StargateModConfig.canNaquadahPickaxeMineBedrock && y != 0 && world.getBlockId(x, y, z) == Block.bedrock.blockID) {
			world.setBlockToAir(x, y, z);
			return true;
		}
		
		return false;
	}
	
	@Override
	public float getStrVsBlock(ItemStack itemStack, Block block) {
		return block.equals(Block.obsidian) ? 100.0F : super.getStrVsBlock(itemStack, block);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon(StargateMod.ASSETS_PREFIX + this.func_111208_A()); // getIconName()
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack itemStack) {
		return EnumRarity.epic;
	}
	
}
