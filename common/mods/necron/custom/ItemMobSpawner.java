package mods.necron.custom;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityMobSpawner;
import net.minecraft.src.World;

public class ItemMobSpawner extends ItemCustomPlaceable {
	
	/**
	 * Une map permettant d'obtenir l'id de l'ItemMobSpawner a partir du nom du mob.
	 */
	private static final Map<String, Integer> stringToIdMapping = new HashMap<String, Integer>();
	
	/**
	 * Le nom du mob a spawner.
	 */
	private String mobName;
	
	public ItemMobSpawner(int id, String mobName) {
		super(id, CustomMod.itemMobSpawnerTextureIndex, "itemSpawner" + mobName, CreativeTabs.tabDeco, Block.mobSpawner);
		this.mobName = mobName;
		ItemMobSpawner.addMapping(this.mobName, this.shiftedIndex);
	}
	
	/**
	 * Permet de recuperer l'id du mobSpawner a partir du nom du monstre.
	 * @param mob - le nom du mob dont on cherche l'id du mobSpawner.
	 */
	public static int getIdFromString(String mob) {
		return stringToIdMapping.get(mob);
	}
	
	/**
	 * Ajoute le couple (nom du mob - id du mobSpawner) a la map.
	 */
	private static void addMapping(String mob, int id) {
		stringToIdMapping.put(mob, id);
	}
	
	/**
	 * Tente de placer un mobSpawner du mob correspondant a cet item.
	 */
	@Override
	public boolean tryPlaceIntoWorld(ItemStack itemStack, EntityPlayer entityPlayer, World world, int x, int y, int z, int side, float par8, float par9, float par10) {
		super.tryPlaceIntoWorld(itemStack, entityPlayer, world, x, y, z, side, par8, par9, par10);
		if(this.succes) {
			TileEntity tileEntity = world.getBlockTileEntity(this.xPlaced, this.yPlaced, this.zPlaced);
			if(tileEntity != null && tileEntity instanceof TileEntityMobSpawner) {
				((TileEntityMobSpawner) tileEntity).setMobID(this.mobName);
			}
			return true;
		}
		return false;
	}
	
}
