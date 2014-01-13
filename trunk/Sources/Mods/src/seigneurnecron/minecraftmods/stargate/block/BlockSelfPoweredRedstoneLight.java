package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
public class BlockSelfPoweredRedstoneLight extends BlockStargate {
	
	// Constructors :
	
	public BlockSelfPoweredRedstoneLight(String name) {
		super(name, Material.redstoneLight);
		this.setLightValue(1.0F);
		this.setHardness(0.3F);
		this.setStepSound(soundGlassFootstep);
		this.setTextureName("redstone_lamp_on");
	}
	
	// Methods :
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.blockIcon = par1IconRegister.registerIcon(this.getTextureName());
	}
	
}
