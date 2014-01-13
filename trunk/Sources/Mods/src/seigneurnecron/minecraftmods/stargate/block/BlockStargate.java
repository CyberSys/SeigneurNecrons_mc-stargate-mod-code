package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
public abstract class BlockStargate extends Block {
	
	// Constructors :
	
	protected BlockStargate(String name, Material material) {
		super(StargateMod.instance.getConfig().getBlockId(name), material);
		this.setUnlocalizedName(name);
		this.setTextureName(name);
		this.setCreativeTab(StargateMod.stargateBlocksTab);
		StargateMod.instance.registerBlock(this);
	}
	
	protected BlockStargate(String name) {
		this(name, Material.rock);
	}
	
	// Methods :
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon(StargateMod.instance.getAssetPrefix() + this.getTextureName());
	}
	
}
