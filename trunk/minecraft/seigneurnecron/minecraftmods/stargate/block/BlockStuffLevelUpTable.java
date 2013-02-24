package seigneurnecron.minecraftmods.stargate.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.inventory.ContainerStuffLevelUpTable;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStuffLevelUpTable;

public class BlockStuffLevelUpTable extends BlockGuiContainer {
	
	public BlockStuffLevelUpTable(int id, int textureIndex, String name) {
		super(id, textureIndex, name);
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityStuffLevelUpTable();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int par6, float par7, float par8, float par9) {
		if(!world.isRemote) {
			ContainerStuffLevelUpTable.test();
		}
		return true;
	}
	
}
