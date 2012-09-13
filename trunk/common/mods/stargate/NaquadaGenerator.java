package mods.stargate;

import java.util.Random;

import net.minecraft.src.IChunkProvider;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class NaquadaGenerator implements IWorldGenerator {
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		switch(world.provider.worldType) {
			case -1:
				generateNether(world, random, chunkX * 16, chunkZ * 16);
				break;
			case 0:
				generateSurface(world, random, chunkX * 16, chunkZ * 16);
				break;
		}
	}
	
	public void generateSurface(World world, Random random, int blockX, int blockZ) {
		// On fait 2 tentatives de g�n�ration (m�me nombre de tentatives que pour l'or).
		for(int i = 0; i < 2; i++) {
			int x = blockX + random.nextInt(16);
			int z = blockZ + random.nextInt(16);
			// Entre la couche 0 et la couche 15 (m�me profondeur que le diamant).
			int y = random.nextInt(16);
			// On demande � g�n�rer 16 blocks (m�me taille que les gisements de charbon).
			(new WorldGenMinable(StargateMod.naquadaOre.blockID, 16)).generate(world, random, x, y, z);
		}
	}
	
	public void generateNether(World world, Random random, int blockX, int blockZ) {
	}
	
}
