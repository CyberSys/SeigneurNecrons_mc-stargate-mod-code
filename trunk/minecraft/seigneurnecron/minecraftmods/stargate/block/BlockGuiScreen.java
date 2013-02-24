package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityGuiScreen;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockGuiScreen extends BlockStargate {
	
	public BlockGuiScreen(int id, int textureIndex, String name) {
		super(id, textureIndex, Material.rock, name);
		this.setHardness(StargateMod.resitantBlockHardness);
		this.setResistance(StargateMod.resitantBlockResistance);
		this.setStepSound(soundStoneFootstep);
		this.setCreativeTab(CreativeTabs.tabRedstone);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		
		if(tileEntity == null || !this.guiOk(tileEntity)) {
			return false;
		}
		
		TileEntityGuiScreen tileEntityGuiScreen = (TileEntityGuiScreen) tileEntity;
		
		if(tileEntityGuiScreen.isEditable()) {
			tileEntityGuiScreen.setEditable(false);
			if(world.isRemote) {
				ModLoader.openGUI(player, this.getGuiScreen(tileEntityGuiScreen));
			}
		}
		
		return true;
	}
	
	protected abstract boolean guiOk(TileEntity tileEntity);
	
	@SideOnly(Side.CLIENT)
	protected abstract GuiScreen getGuiScreen(TileEntityGuiScreen tileEntity);
	
	@Override
	public String getTextureFile() {
		return StargateMod.blockTextureFile;
	}
	
}
