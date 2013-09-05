package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargateControl;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
public class BlockFastStargate extends BlockNaquadahMade {
	
	protected Icon naquadaIcon;
	
	public BlockFastStargate(String name) {
		super(name);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int par6, float par7, float par8, float par9) {
		if(!world.isRemote && y >= 4 && y < 244) {
			int angle = MathHelper.floor_double(entityPlayer.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
			int side = (angle == 0) ? 2 : (angle == 1) ? 5 : (angle == 2) ? 3 : 4;
			
			int xAxis = (side == 2) ? -1 : (side == 3) ? 1 : 0;
			int zAxis = (side == 4) ? -1 : (side == 5) ? 1 : 0;
			
			world.setBlockToAir(x, y, z);
			this.addDecoration(world, x, y, z, side, xAxis, zAxis);
			TileEntityStargateControl.constructGate(world, x, y + 10, z, side);
		}
		return true;
	}
	
	protected void addDecoration(World world, int xCoord, int yCoord, int zCoord, int side, int xAxis, int zAxis) {
		// Nothing to do in that version.
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		super.registerIcons(iconRegister);
		this.naquadaIcon = iconRegister.registerIcon(StargateMod.ASSETS_PREFIX + StargateMod.blockName_naquadahAlloy);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side) {
		if(side == 0 || side == 1) {
			return this.naquadaIcon;
		}
		return this.blockIcon;
	}
	
}
