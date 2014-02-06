package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public abstract class BlockContainerStargate extends BlockContainer {
	
	// Constructors :
	
	protected BlockContainerStargate(String name, Material material) {
		super(StargateMod.instance.getConfig().getBlockId(name), material);
		this.setUnlocalizedName(name);
		this.setTextureName(name);
		this.setCreativeTab(StargateMod.stargateBlocksTab);
		StargateMod.instance.registerBlock(this);
	}
	
	// Methods :
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon(StargateMod.instance.getAssetPrefix() + this.getTextureName());
	}
	
}
