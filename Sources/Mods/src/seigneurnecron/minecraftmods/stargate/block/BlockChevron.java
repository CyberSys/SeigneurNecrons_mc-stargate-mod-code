package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityChevron;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class BlockChevron extends BlockStargateSolidPart {
	
	// Fields :
	
	protected Icon naquadaIcon;
	protected Icon chevronIcon;
	protected Icon[] chevronIcons = new Icon[9];
	
	// Constructors :
	
	public BlockChevron(String name) {
		super(name);
	}
	
	// Methods :
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityChevron();
	}
	
	@Override
	protected boolean breakGate(World world, int x, int y, int z) {
		return !((TileEntityChevron) world.getBlockTileEntity(x, y, z)).isActivating();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		for(int i = 1; i <= 9; i++) {
			this.naquadaIcon = iconRegister.registerIcon(StargateMod.instance.getAssetPrefix() + StargateMod.blockName_naquadahAlloy);
			this.chevronIcon = iconRegister.registerIcon(StargateMod.instance.getAssetPrefix() + StargateMod.blockName_chevron);
			this.chevronIcons[i - 1] = iconRegister.registerIcon(StargateMod.instance.getAssetPrefix() + this.getTextureName() + "_" + i);
		}
		
		this.blockIcon = this.chevronIcons[6];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side) {
		TileEntity tileEntity = iBlockAccess.getBlockTileEntity(x, y, z);
		
		if(tileEntity != null && tileEntity instanceof TileEntityChevron) {
			TileEntityChevron tileEntityChevron = ((TileEntityChevron) tileEntity);
			
			int gateMetadata = tileEntityChevron.getGateMetadata();
			int chevronNo = tileEntityChevron.getNo();
			
			if(gateMetadata == 0 || chevronNo <= 0) {
				return this.chevronIcon;
			}
			
			if(side == gateMetadata) {
				int index = chevronNo - 1;
				
				return this.chevronIcons[index];
			}
			
			return this.naquadaIcon;
		}
		
		return this.chevronIcon;
	}
	
}
