package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityGuiScreen;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
public abstract class BlockGuiScreen extends BlockStargateContainer {
	
	public BlockGuiScreen(String name) {
		super(name, Material.rock);
		this.setHardness(StargateMod.RESISTANT_BLOCKS_HARDNESS);
		this.setResistance(StargateMod.RESISTANT_BLOCKS_RESISTANCE);
		this.setStepSound(soundStoneFootstep);
	}
	
	protected boolean openGui(World world, int x, int y, int z, EntityPlayer player) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		
		if(tileEntity != null && this.tileEntityOk(tileEntity)) {
			TileEntityGuiScreen tileEntityGuiScreen = (TileEntityGuiScreen) tileEntity;
			
			if(tileEntityGuiScreen.isEditable()) {
				tileEntityGuiScreen.setEditable(false);
				if(world.isRemote) {
					ModLoader.openGUI(player, this.getGuiScreen(tileEntityGuiScreen, player));
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	protected abstract boolean tileEntityOk(TileEntity tileEntity);
	
	@SideOnly(Side.CLIENT)
	protected abstract GuiScreen getGuiScreen(TileEntityGuiScreen tileEntity, EntityPlayer player);
	
}
