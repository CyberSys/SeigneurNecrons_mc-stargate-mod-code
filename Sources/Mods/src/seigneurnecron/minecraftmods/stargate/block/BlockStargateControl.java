package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.client.gui.GuiScreen;
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
import seigneurnecron.minecraftmods.stargate.gui.GuiStargateControl;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargateControl;
import seigneurnecron.minecraftmods.stargate.tools.enums.GateState;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class BlockStargateControl extends BlockGuiScreen {
	
	// Fields :
	
	protected Icon naquadaIcon;
	
	// Constructors :
	
	public BlockStargateControl(String name) {
		super(name);
	}
	
	// Methods :
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLivingBase, ItemStack itemStack) {
		int angle = MathHelper.floor_double(entityLivingBase.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		int metadata = (angle == 0) ? 2 : (angle == 1) ? 5 : (angle == 2) ? 3 : 4;
		world.setBlockMetadataWithNotify(x, y, z, metadata, 3);
	}
	
	/**
	 * Informs other blocks of the gate that the gate has been destroyed.
	 */
	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		
		if(tileEntity instanceof TileEntityStargateControl) {
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
		this.naquadaIcon = iconRegister.registerIcon(StargateMod.instance.getAssetPrefix() + StargateMod.blockName_naquadahAlloy);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side) {
		int metadata = world.getBlockMetadata(x, y, z);
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		
		if(side == metadata && this.tileEntityOk(tileEntity)) {
			return this.blockIcon;
		}
		
		return this.naquadaIcon;
	}
	
	@Override
	protected boolean tileEntityOk(TileEntity tileEntity) {
		return (tileEntity instanceof TileEntityStargateControl) && ((TileEntityStargateControl) tileEntity).getState() == GateState.BROKEN;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen getGuiScreen(TileEntity tileEntity, EntityPlayer player) {
		return new GuiStargateControl((TileEntityStargateControl) tileEntity);
	}
	
}
