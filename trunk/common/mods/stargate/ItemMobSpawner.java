package mods.stargate;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityMobSpawner;
import net.minecraft.src.World;

public class ItemMobSpawner extends ItemStargatePlaceable {
	
	/**
	 * Une map permettant d'obtenir l'id de l'ItemMobSpawner à partir du nom du mob.
	 */
	private static final Map<String, Integer> stringToIdMapping = new HashMap<String, Integer>();
	
	/**
	 * Le nom du mob à spawner.
	 */
	private String mobName;
	
	protected ItemMobSpawner(int id, String mobName) {
		super(id, StargateMod.itemMobSpawnerTextureIndex, "itemSpawner" + mobName, CreativeTabs.tabDeco, Block.mobSpawner);
		this.mobName = mobName;
		this.addMapping(this.mobName, this.shiftedIndex);
	}
	
	/**
	 * Permet de récupérer l'id du mobSpawner à partir du nom du monstre.
	 * @param mob - le nom du mob dont on cherche l'id du mobSpawner.
	 */
	public static int getIdFromString(String mob) {
		return stringToIdMapping.get(mob);
	}
	
	/**
	 * Ajoute le couple (nom du mob - id du mobSpawner) à la map.
	 */
	private static void addMapping(String mob, int id) {
		stringToIdMapping.put(mob, id);
	}
	
	@Override
	public boolean tryPlaceIntoWorld(ItemStack itemStack, EntityPlayer entityPlayer, World world, int x, int y, int z, int side, float par8, float par9, float par10) {
		super.tryPlaceIntoWorld(itemStack, entityPlayer, world, x, y, z, side, par8, par9, par10);
		System.out.println("pwet");
		if(this.succes) {
			System.out.println("bubu");
			TileEntityMobSpawner tileEntity = (TileEntityMobSpawner) world.getBlockTileEntity(x, y, z);
			tileEntity.setMobID(this.mobName); // FIXME - NullPointerException ici !
			return true;
		}
		return false;
	}
	
}
