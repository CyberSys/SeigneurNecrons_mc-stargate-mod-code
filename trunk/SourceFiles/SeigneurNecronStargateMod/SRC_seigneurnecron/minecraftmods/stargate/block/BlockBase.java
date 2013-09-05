package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
public abstract class BlockBase extends BlockGuiScreen {
	
	protected Icon naquadaIcon;
	protected Icon[][] baseIcons = new Icon[2][4];
	
	public BlockBase(String name) {
		super(name);
		this.setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 1.0F, 0.9F);
	}
	
	public void activate(World world, int x, int y, int z, EntityPlayer player) {
		this.openGui(world, x, y, z, player);
	}
	
	public void onConsoleDestroyed(World world, int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		
		if(tileEntity != null && tileEntity instanceof TileEntityBase) {
			((TileEntityBase) tileEntity).onConsoleDestroyed();
		}
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		this.onConsoleDestroyed(world, x, y, z);
		super.breakBlock(world, x, y, z, par5, par6);
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLivingBase, ItemStack itemStack) {
		int angle = MathHelper.floor_double(entityLivingBase.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		
		switch(angle) {
			case 0:
				world.setBlockMetadataWithNotify(x, y, z, 2, 3);
				break;
			case 1:
				world.setBlockMetadataWithNotify(x, y, z, 5, 3);
				break;
			case 2:
				world.setBlockMetadataWithNotify(x, y, z, 3, 3);
				break;
			case 3:
				world.setBlockMetadataWithNotify(x, y, z, 4, 3);
				break;
		}
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.naquadaIcon = iconRegister.registerIcon(StargateMod.ASSETS_PREFIX + StargateMod.blockName_naquadahAlloy);
		
		for(int i = 0; i < 4; i++) {
			this.baseIcons[0][i] = iconRegister.registerIcon(StargateMod.ASSETS_PREFIX + this.func_111023_E() + "_off" + i);
			this.baseIcons[1][i] = iconRegister.registerIcon(StargateMod.ASSETS_PREFIX + this.func_111023_E() + "_on" + i);
		}
		
		this.blockIcon = this.baseIcons[1][0];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side) {
		if(side == 0 || side == 1) {
			return this.naquadaIcon;
		}
		else {
			int metadata = iBlockAccess.getBlockMetadata(x, y, z);
			int index = baseIconIndex(side, metadata);
			
			TileEntity tileEntity = iBlockAccess.getBlockTileEntity(x, y, z);
			int active = (tileEntity != null && tileEntity instanceof TileEntityBase && ((TileEntityBase) tileEntity).isActive()) ? 1 : 0;
			
			return this.baseIcons[active][index];
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static int baseIconIndex(int side, int metadata) {
		int convertedSide = BlockPanel.panelIconIndex(side);
		int convertedMetadata = BlockPanel.panelIconIndex(metadata);
		
		return (convertedSide - convertedMetadata + 4) % 4;
	}
	
}
