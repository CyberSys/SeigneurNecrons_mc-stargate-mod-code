package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
public class BlockSelfPoweredRedstoneLight extends BlockStargate {
	
	public BlockSelfPoweredRedstoneLight(String name) {
		super(name, Material.redstoneLight);
		this.setLightValue(1.0F);
		this.setHardness(0.3F);
		this.setStepSound(soundGlassFootstep);
		this.func_111022_d("redstone_lamp_on");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.blockIcon = par1IconRegister.registerIcon(this.func_111023_E());
	}
	
}