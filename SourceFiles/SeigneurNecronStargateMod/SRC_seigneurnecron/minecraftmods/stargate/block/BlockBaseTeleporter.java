package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.client.gui.GuiTeleporter;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityBaseTeleporter;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityGuiScreen;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
public class BlockBaseTeleporter extends BlockBase {
	
	public BlockBaseTeleporter(String name) {
		super(name);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityBaseTeleporter();
	}
	
	@Override
	protected boolean tileEntityOk(TileEntity tileEntity) {
		return(tileEntity instanceof TileEntityBaseTeleporter);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	protected GuiScreen getGuiScreen(TileEntityGuiScreen tileEntity, EntityPlayer player) {
		return new GuiTeleporter((TileEntityBaseTeleporter) tileEntity, player);
	}
	
}
