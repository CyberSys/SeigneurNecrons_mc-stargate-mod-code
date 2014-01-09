package seigneurnecron.minecraftmods.stargate.block;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
public class BlockConsoleBase extends BlockGuiContainer {
	
	// Fields :
	
	protected Icon naquadaIcon;
	protected Icon[] defaultIcons = new Icon[4];
	protected Map<Class<? extends Console>, Icon[][]> baseIcons = new HashMap<Class<? extends Console>, Icon[][]>();
	
	// Constructors :
	
	public BlockConsoleBase(String name) {
		super(name);
		this.setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 1.0F, 0.9F);
	}
	
	// Methods :
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityConsoleBase();
	}
	
	public boolean openConsoleGui(World world, int x, int y, int z, EntityPlayer player) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		
		if(tileEntity instanceof TileEntityConsoleBase) {
			return ((TileEntityConsoleBase) tileEntity).openGui(world, x, y, z, player);
		}
		
		return false;
	}
	
	public void onConsoleDestroyed(World world, int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		
		if(tileEntity instanceof TileEntityConsoleBase) {
			((TileEntityConsoleBase) tileEntity).onConsoleDestroyed();
		}
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		this.onConsoleDestroyed(world, x, y, z);
		super.breakBlock(world, x, y, z, par5, par6);
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLivingBase, ItemStack itemStack) {
		int angle = MathHelper.floor_double(entityLivingBase.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		int metadata = (angle == 0) ? 2 : (angle == 1) ? 5 : (angle == 2) ? 3 : 4;
		world.setBlockMetadataWithNotify(x, y, z, metadata, 3);
	}
	
	@Override
	public boolean isOpaqueCube() {
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
			this.baseIcons.put(entry.getKey(), icons);
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
	public Icon getBlockTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side) {
		if(side != 0 && side != 1) {
			int metadata = iBlockAccess.getBlockMetadata(x, y, z);
			int index = baseIconIndex(side, metadata);
			TileEntity tileEntity = iBlockAccess.getBlockTileEntity(x, y, z);
			
			if(tileEntity instanceof TileEntityConsoleBase) {
				TileEntityConsoleBase tileEntityConsoleBase = (TileEntityConsoleBase) tileEntity;
				Console console = tileEntityConsoleBase.getConsole();
				
				if(console != null) {
					Icon[][] icons = this.baseIcons.get(console.getClass());
					
					if(icons != null) {
						int active = tileEntityConsoleBase.isIntact() ? 1 : 0;
						return icons[active][index];
					}
				}
			}
			
			return this.defaultIcons[index];
		}
		
		return this.naquadaIcon;
	}
	
	@SideOnly(Side.CLIENT)
	public static int baseIconIndex(int side, int metadata) {
		int convertedSide = BlockConsolePanel.panelIconIndex(side);
		int convertedMetadata = BlockConsolePanel.panelIconIndex(metadata);
		
		return (convertedSide - convertedMetadata + 4) % 4;
	}
	
}
