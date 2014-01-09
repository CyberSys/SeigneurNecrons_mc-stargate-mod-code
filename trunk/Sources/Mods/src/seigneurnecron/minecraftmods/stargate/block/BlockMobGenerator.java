package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityMobGenerator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
public class BlockMobGenerator extends BlockGuiContainer {
	
	// Fields :
	
	protected Icon crystalInsertedIcon;
	protected Icon blockActiveIcon;
	
	// Constructors :
	
	public BlockMobGenerator(String name) {
		super(name);
	}
	
	// Methods :
	
	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityMobGenerator();
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int blockId) {
		if(!world.isRemote) {
			TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
			if(tileEntity != null && tileEntity instanceof TileEntityMobGenerator) {
				((TileEntityMobGenerator) tileEntity).setPowered(world.isBlockIndirectlyGettingPowered(x, y, z));
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		super.registerIcons(iconRegister);
		this.crystalInsertedIcon = iconRegister.registerIcon(StargateMod.instance.getAssetPrefix() + StargateMod.blockName_mobGenerator + "_crystal");
		this.blockActiveIcon = iconRegister.registerIcon(StargateMod.instance.getAssetPrefix() + StargateMod.blockName_mobGenerator + "_active");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side) {
		TileEntity tileEntity = iBlockAccess.getBlockTileEntity(x, y, z);
		if(tileEntity != null && tileEntity instanceof TileEntityMobGenerator) {
			TileEntityMobGenerator tileEntityMobGenerator = (TileEntityMobGenerator) tileEntity;
			if(tileEntityMobGenerator.getInventory().isCrystalInserted()) {
				if(tileEntityMobGenerator.isPowered()) {
					return this.blockActiveIcon;
				}
				else {
					return this.crystalInsertedIcon;
				}
			}
		}
		return this.blockIcon;
	}
	
}
