package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.client.gui.GuiDhd;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityBaseDhd;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityGuiScreen;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
public class BlockBaseDhd extends BlockBaseStargateConsole {
	
	public BlockBaseDhd(String name) {
		super(name);
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityBaseDhd();
	}
	
	@Override
	protected boolean tileEntityOk(TileEntity tileEntity) {
		return(tileEntity instanceof TileEntityBaseDhd);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	protected GuiScreen getGuiScreen(TileEntityGuiScreen tileEntity, EntityPlayer player) {
		return new GuiDhd((TileEntityBaseDhd) tileEntity, player);
	}
	
}
