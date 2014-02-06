package seigneurnecron.minecraftmods.stargate.item.stuff;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class ItemNaquadahPickaxe extends ItemPickaxe {
	
	// Constructors :
	
	public ItemNaquadahPickaxe(String name) {
		super(StargateMod.instance.getConfig().getItemId(name), StargateMod.naquadahToolMaterial);
		this.setUnlocalizedName(name);
		this.setTextureName(name);
		this.setCreativeTab(StargateMod.stargateItemsTab);
	}
	
	// Methods :
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(StargateMod.instance.getConfig().canNaquadahPickaxeMineBedrock && y != 0 && world.getBlockId(x, y, z) == Block.bedrock.blockID) {
			world.destroyBlock(x, y, z, false);
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
		this.itemIcon = iconRegister.registerIcon(StargateMod.instance.getAssetPrefix() + this.getIconString());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack itemStack) {
		return EnumRarity.epic;
	}
	
}
