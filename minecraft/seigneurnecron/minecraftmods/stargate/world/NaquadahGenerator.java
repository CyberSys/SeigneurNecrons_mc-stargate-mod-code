package seigneurnecron.minecraftmods.stargate.world;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import cpw.mods.fml.common.IWorldGenerator;

public class NaquadahGenerator implements IWorldGenerator {
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		switch(world.provider.dimensionId) {
			case -1:
				generateNether(world, random, chunkX * 16, chunkZ * 16);
				break;
			case 0:
				generateSurface(world, random, chunkX * 16, chunkZ * 16);
				break;
		}
	}
	
	public void generateSurface(World world, Random random, int blockX, int blockZ) {
		// On fait 2 tentatives de generation (meme nombre de tentatives que pour l'or).
		for(int i = 0; i < 2; i++) {
			int x = blockX + random.nextInt(16);
			int z = blockZ + random.nextInt(16);
			// Entre la couche 0 et la couche 15 (meme profondeur que le diamant).
			int y = random.nextInt(16);
			// On demande a generer 10 blocks (c'est 16 pour le charbon).
			(new WorldGenMinable(StargateMod.naquadahOre.blockID, 10)).generate(world, random, x, y, z);
		}
	}
	
	public void generateNether(World world, Random random, int blockX, int blockZ) {
		// Rien a faire.
	}
	
}
