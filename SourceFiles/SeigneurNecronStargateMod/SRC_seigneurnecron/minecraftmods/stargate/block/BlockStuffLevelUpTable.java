package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStuffLevelUpTable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
public class BlockStuffLevelUpTable extends BlockGuiContainer {
	
	protected Icon naquadaIcon;
	protected Icon[][] blockIcons = new Icon[2][4];
	
	public BlockStuffLevelUpTable(String name) {
		super(name);
		this.setLightValue(0.8F);
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
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityStuffLevelUpTable();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.naquadaIcon = iconRegister.registerIcon(StargateMod.instance.getAssetPrefix() + StargateMod.blockName_naquadahAlloy);
		
		for(int i = 0; i < 4; i++) {
			this.blockIcons[0][i] = iconRegister.registerIcon(StargateMod.instance.getAssetPrefix() + this.func_111023_E() + "_base" + i);
			this.blockIcons[1][i] = iconRegister.registerIcon(StargateMod.instance.getAssetPrefix() + this.func_111023_E() + "_panel" + i);
		}
		
		this.blockIcon = this.blockIcons[1][0];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side) {
		if(side != 0) {
			int metadata = iBlockAccess.getBlockMetadata(x, y, z);
			int index = (side == 1) ? BlockPanel.panelIconIndex(metadata) : BlockBase.baseIconIndex(side, metadata);
			int type = (side == 1) ? 1 : 0;
			
			return this.blockIcons[type][index];
		}
		
		return this.naquadaIcon;
	}
	
}
