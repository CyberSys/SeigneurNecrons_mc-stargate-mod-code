package seigneurnecron.minecraftmods.stargate.block;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.block.Block;
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
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityConsoleBase;
import seigneurnecron.minecraftmods.stargate.tileentity.console.Console;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
public class BlockConsolePanel extends BlockNaquadahMade {
	
	protected Icon naquadaIcon;
	protected Icon[] defaultIcons = new Icon[4];
	protected Map<Class<? extends Console>, Icon[][]> panelIcons = new HashMap<Class<? extends Console>, Icon[][]>();
	
	public BlockConsolePanel(String name) {
		super(name);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.2F, 1.0F);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xOffset, float yOffset, float zOffset) {
		Block block = Block.blocksList[world.getBlockId(x, y - 1, z)];
		
		if(block instanceof BlockConsoleBase) {
			return ((BlockConsoleBase) block).openConsoleGui(world, x, y - 1, z, player);
		}
		
		return false;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		Block block = Block.blocksList[world.getBlockId(x, y - 1, z)];
		
		if(block != null && block instanceof BlockBase) {
			((BlockBase) block).onConsoleDestroyed(world, x, y - 1, z);
		}
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
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.naquadaIcon = iconRegister.registerIcon(StargateMod.instance.getAssetPrefix() + StargateMod.blockName_naquadahAlloy);
		
		String blockName = StargateMod.instance.getAssetPrefix() + this.func_111023_E();
		
		for(int i = 0; i < 4; i++) {
			this.defaultIcons[i] = iconRegister.registerIcon(blockName + "_Default" + i);
		}
		
		for(Entry<Class<? extends Console>, String> entry : Console.getConsoles()) {
			Icon[][] icons = new Icon[2][4];
			this.panelIcons.put(entry.getKey(), icons);
			String consoleName = entry.getValue();
			
			for(int i = 0; i < 4; i++) {
				icons[0][i] = iconRegister.registerIcon(blockName + "_" + consoleName + "_Off" + i);
				icons[1][i] = iconRegister.registerIcon(blockName + "_" + consoleName + "_On" + i);
			}
		}
		
		this.blockIcon = this.defaultIcons[0];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side) {
		if(side == 1) {
			int metadata = world.getBlockMetadata(x, y, z);
			int index = panelIconIndex(metadata);
			TileEntity tileEntity = world.getBlockTileEntity(x, y - 1, z);
			
			if(tileEntity instanceof TileEntityConsoleBase) {
				TileEntityConsoleBase tileEntityConsoleBase = (TileEntityConsoleBase) tileEntity;
				Console console = tileEntityConsoleBase.getConsole();
				
				if(console != null) {
					Icon[][] icons = this.panelIcons.get(console.getClass());
					
					if(icons != null) {
						int active = console.isActive() ? 1 : 0;
						return icons[active][index];
					}
				}
			}
			
			return this.defaultIcons[index];
		}
		
		return this.naquadaIcon;
	}
	
	@SideOnly(Side.CLIENT)
	public static int panelIconIndex(int metadata) {
		return (metadata == 4) ? 1 : (metadata == 2) ? 2 : (metadata == 5) ? 3 : 0;
	}
	
	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		Block block = blocksList[world.getBlockId(x, y, z)];
		Block baseBlock = blocksList[world.getBlockId(x, y - 1, z)];
		
		if(block != null) {
			if(block == this) {
				if(baseBlock != null && baseBlock instanceof BlockBase) {
					return 9;
				}
			}
			else {
				return block.getLightValue(world, x, y, z);
			}
		}
		
		return lightValue[this.blockID];
	}
	
}
