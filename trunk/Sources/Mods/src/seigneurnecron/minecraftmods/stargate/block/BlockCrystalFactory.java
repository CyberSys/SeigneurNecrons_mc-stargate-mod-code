package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
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
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		super.registerIcons(iconRegister);
		this.naquadaIcon = iconRegister.registerIcon(StargateMod.instance.getAssetPrefix() + StargateMod.blockName_naquadahAlloy);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side) {
		if(side == 1) {
			return this.blockIcon;
		}
		
		return this.naquadaIcon;
	}
	
}
