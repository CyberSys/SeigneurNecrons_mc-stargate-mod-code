package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
public abstract class BlockContainerStargate extends BlockContainer {
	
	// Constructors :
	
	protected BlockContainerStargate(String name, Material material) {
		super(StargateMod.instance.getConfig().getBlockId(name), material);
		this.setUnlocalizedName(name);
		this.func_111022_d(name); // setIconName()
		this.setCreativeTab(StargateMod.stargateBlocksTab);
		StargateMod.instance.registerBlock(this);
	}
	
	// Methods :
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon(StargateMod.instance.getAssetPrefix() + this.func_111023_E()); // getIconName()
	}
	
}
