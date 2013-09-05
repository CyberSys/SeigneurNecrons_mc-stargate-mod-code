package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.block.Block;
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
public abstract class BlockPanel extends BlockNaquadahMade {
	
	protected Icon naquadaIcon;
	protected Icon[][] panelIcons = new Icon[2][4];
	
	protected BlockPanel(String name) {
		super(name);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.2F, 1.0F);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xOffset, float yOffset, float zOffset) {
		Block baseBlock = this.baseBlock();
		
		if(world.getBlockId(x, y - 1, z) == baseBlock.blockID && baseBlock instanceof BlockBase) {
			((BlockBase) this.baseBlock()).activate(world, x, y - 1, z, player);
		}
		
		return true;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		Block baseBlock = this.baseBlock();
		
		if(!world.isRemote && world.getBlockId(x, y - 1, z) == baseBlock.blockID && baseBlock instanceof BlockBase) {
			((BlockBase) this.baseBlock()).onConsoleDestroyed(world, x, y - 1, z);
		}
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
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.naquadaIcon = iconRegister.registerIcon(StargateMod.ASSETS_PREFIX + StargateMod.blockName_naquadahAlloy);
		
		for(int i = 0; i < 4; i++) {
			this.panelIcons[0][i] = iconRegister.registerIcon(StargateMod.ASSETS_PREFIX + this.func_111023_E() + "_off" + i);
			this.panelIcons[1][i] = iconRegister.registerIcon(StargateMod.ASSETS_PREFIX + this.func_111023_E() + "_on" + i);
		}
		
		this.blockIcon = this.panelIcons[1][0];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side) {
		if(side == 1) {
			int metadata = iBlockAccess.getBlockMetadata(x, y, z);
			int index = panelIconIndex(metadata);
			
			TileEntity tileEntity = iBlockAccess.getBlockTileEntity(x, y - 1, z);
			int active = (tileEntity != null && tileEntity instanceof TileEntityBase && ((TileEntityBase) tileEntity).isActive()) ? 1 : 0;
			
			return this.panelIcons[active][index];
		}
		return this.naquadaIcon;
	}
	
	@SideOnly(Side.CLIENT)
	public static int panelIconIndex(int metadata) {
		return (metadata == 4) ? 1 : (metadata == 2) ? 2 : (metadata == 5) ? 3 : 0;
	}
	
	protected abstract Block baseBlock();
	
}
