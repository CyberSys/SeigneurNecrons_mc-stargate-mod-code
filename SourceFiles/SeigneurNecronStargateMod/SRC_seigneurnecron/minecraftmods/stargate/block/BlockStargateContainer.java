package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.tools.config.StargateModConfig;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
public abstract class BlockStargateContainer extends BlockContainer {
	
	protected BlockStargateContainer(String name, Material material) {
		super(StargateModConfig.getBlockId(name), material);
		this.setUnlocalizedName(name);
		this.func_111022_d(name); // setIconName()
		this.setCreativeTab(StargateMod.stargateBlocksTab);
		StargateMod.registerBlock(this);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon(StargateMod.ASSETS_PREFIX + this.func_111023_E()); // getIconName()
	}
	
}
