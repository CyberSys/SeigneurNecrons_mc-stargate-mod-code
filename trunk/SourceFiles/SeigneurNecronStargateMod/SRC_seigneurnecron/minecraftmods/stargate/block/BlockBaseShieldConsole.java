package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.client.gui.GuiShieldConsole;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityBaseShieldConsole;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityGuiScreen;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
public class BlockBaseShieldConsole extends BlockBaseStargateConsole {
	
	public BlockBaseShieldConsole(String name) {
		super(name);
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityBaseShieldConsole();
	}
	
	@Override
	protected boolean tileEntityOk(TileEntity tileEntity) {
		return(tileEntity instanceof TileEntityBaseShieldConsole);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	protected GuiScreen getGuiScreen(TileEntityGuiScreen tileEntity, EntityPlayer player) {
		return new GuiShieldConsole((TileEntityBaseShieldConsole) tileEntity, player);
	}
	
}
