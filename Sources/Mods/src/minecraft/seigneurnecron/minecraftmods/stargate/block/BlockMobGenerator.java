package seigneurnecron.minecraftmods.stargate.block;

import java.util.Random;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityMobGenerator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
public class BlockMobGenerator extends BlockGuiContainer {
	
	protected Random rand = new Random();
	protected Icon crystalInsertedIcon;
	protected Icon blockActiveIcon;
	
	public BlockMobGenerator(String name) {
		super(name);
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityMobGenerator();
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int blockId) {
		if(!world.isRemote) {
			TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
			if(tileEntity != null && tileEntity instanceof TileEntityMobGenerator) {
				((TileEntityMobGenerator) tileEntity).setPowered(world.isBlockIndirectlyGettingPowered(x, y, z));
			}
		}
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		TileEntity tileentity = world.getBlockTileEntity(x, y, z);
		
		if(tileentity instanceof TileEntityMobGenerator) {
			TileEntityMobGenerator tileentityMobGenerator = (TileEntityMobGenerator) tileentity;
			
			for(int i = 0; i < tileentityMobGenerator.getInventory().getSizeInventory(); ++i) {
				ItemStack itemstack = tileentityMobGenerator.getInventory().getStackInSlot(i);
				
				if(itemstack != null) {
					float x1 = this.rand.nextFloat() * 0.8F + 0.1F;
					float y1 = this.rand.nextFloat() * 0.8F + 0.1F;
					float z1 = this.rand.nextFloat() * 0.8F + 0.1F;
					
					while(itemstack.stackSize > 0) {
						int k1 = this.rand.nextInt(21) + 10;
						
						if(k1 > itemstack.stackSize) {
							k1 = itemstack.stackSize;
						}
						
						itemstack.stackSize -= k1;
						EntityItem entityitem = new EntityItem(world, x + x1, y + y1, z + z1, new ItemStack(itemstack.itemID, k1, itemstack.getItemDamage()));
						float f3 = 0.05F;
						entityitem.motionX = (float) this.rand.nextGaussian() * f3;
						entityitem.motionY = (float) this.rand.nextGaussian() * f3 + 0.2F;
						entityitem.motionZ = (float) this.rand.nextGaussian() * f3;
						world.spawnEntityInWorld(entityitem);
					}
				}
			}
		}
		
		super.breakBlock(world, x, y, z, par5, par6);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		super.registerIcons(iconRegister);
		this.crystalInsertedIcon = iconRegister.registerIcon(StargateMod.instance.getAssetPrefix() + StargateMod.blockName_mobGenerator + "_crystal");
		this.blockActiveIcon = iconRegister.registerIcon(StargateMod.instance.getAssetPrefix() + StargateMod.blockName_mobGenerator + "_active");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side) {
		TileEntity tileEntity = iBlockAccess.getBlockTileEntity(x, y, z);
		if(tileEntity != null && tileEntity instanceof TileEntityMobGenerator) {
			TileEntityMobGenerator tileEntityMobGenerator = (TileEntityMobGenerator) tileEntity;
			if(tileEntityMobGenerator.getInventory().isCrystalInserted()) {
				if(tileEntityMobGenerator.isPowered()) {
					return this.blockActiveIcon;
				}
				else {
					return this.crystalInsertedIcon;
				}
			}
		}
		return this.blockIcon;
	}
	
}
