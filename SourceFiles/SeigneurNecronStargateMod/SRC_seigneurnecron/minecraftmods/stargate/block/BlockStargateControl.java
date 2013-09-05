package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargateControl;
import seigneurnecron.minecraftmods.stargate.tools.enums.GateState;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
public class BlockStargateControl extends BlockStargateContainer {
	
	// FIXME - extends BlockGuiScreen.
	
	protected Icon naquadaIcon;
	
	public BlockStargateControl(String name) {
		super(name, Material.rock);
		this.setHardness(StargateMod.RESISTANT_BLOCKS_HARDNESS);
		this.setResistance(StargateMod.RESISTANT_BLOCKS_RESISTANCE);
		this.setStepSound(soundStoneFootstep);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int par6, float par7, float par8, float par9) {
		TileEntityStargateControl gate = ((TileEntityStargateControl) world.getBlockTileEntity(x, y, z));
		
		if(gate != null && gate.getState() == GateState.BROKEN) {
			if(!world.isRemote) {
				gate.createGate("abcABCv0@"); // FIXME - faire une interface pour entrer l'adresse.
			}
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLivingBase, ItemStack itemStack) {
		int angle = MathHelper.floor_double(entityLivingBase.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		
		switch(angle) {
			case 0:
				world.setBlockMetadataWithNotify(x, y, z, 2, 3);
				break;
			case 1:
				world.setBlockMetadataWithNotify(x, y, z, 5, 3);
				break;
			case 2:
				world.setBlockMetadataWithNotify(x, y, z, 3, 3);
				break;
			case 3:
				world.setBlockMetadataWithNotify(x, y, z, 4, 3);
				break;
		}
	}
	
	/**
	 * Informs other blocks of the gate that the gate has been destroyed.
	 */
	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if(tileEntity != null && tileEntity instanceof TileEntityStargateControl) {
			((TileEntityStargateControl) tileEntity).setBroken();
		}
		super.breakBlock(world, x, y, z, par5, par6);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityStargateControl();
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
		int metadata = iBlockAccess.getBlockMetadata(x, y, z);
		
		if(side == metadata) {
			TileEntityStargateControl tileEntity = (TileEntityStargateControl) iBlockAccess.getBlockTileEntity(x, y, z);
			if(tileEntity != null && tileEntity.getState() == GateState.BROKEN) {
				return this.blockIcon;
			}
		}
		
		return this.naquadaIcon;
	}
	
}
