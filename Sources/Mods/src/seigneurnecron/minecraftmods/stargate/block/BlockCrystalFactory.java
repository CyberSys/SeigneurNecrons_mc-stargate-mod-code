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
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityCrystalFactory;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
public class BlockCrystalFactory extends BlockGuiContainer {
	
	// Fields :
	
	protected Icon naquadaIcon;
	protected Icon[] panelIcons = new Icon[4];
	
	// Constructors :
	
	public BlockCrystalFactory(String name) {
		super(name);
	}
	
	// Methods :
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityCrystalFactory();
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLivingBase, ItemStack itemStack) {
		int angle = MathHelper.floor_double(entityLivingBase.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		int metadata = (angle == 0) ? 2 : (angle == 1) ? 5 : (angle == 2) ? 3 : 4;
		world.setBlockMetadataWithNotify(x, y, z, metadata, 3);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.naquadaIcon = iconRegister.registerIcon(StargateMod.instance.getAssetPrefix() + StargateMod.blockName_naquadahAlloy);
		
		String blockName = StargateMod.instance.getAssetPrefix() + this.getTextureName();
		
		for(int i = 0; i < 4; i++) {
			this.panelIcons[i] = iconRegister.registerIcon(blockName + "_" + i);
		}
		
		this.blockIcon = this.panelIcons[0];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side) {
		if(side == 1) {
			int metadata = world.getBlockMetadata(x, y, z);
			int index = BlockConsolePanel.panelIconIndex(metadata);
			return this.panelIcons[index];
		}
		
		return this.naquadaIcon;
	}
	
}
