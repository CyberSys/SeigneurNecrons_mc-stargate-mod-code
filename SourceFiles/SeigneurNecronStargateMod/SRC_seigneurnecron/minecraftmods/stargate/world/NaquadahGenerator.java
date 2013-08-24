package seigneurnecron.minecraftmods.stargate.world;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import cpw.mods.fml.common.IWorldGenerator;

/**
 * @author Seigneur Necron
 */
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
		// Tries to generate naquadah twice.
		for(int i = 0; i < 2; i++) {
			int x = blockX + random.nextInt(16);
			int z = blockZ + random.nextInt(16);
			// 0 < Y < 15
			int y = random.nextInt(16);
			// Tries to generate 10 blocks.
			(new WorldGenMinable(StargateMod.block_naquadahOre.blockID, 10)).generate(world, random, x, y, z);
		}
	}
	
	public void generateNether(World world, Random random, int blockX, int blockZ) {
		// Nothing to do.
	}
	
}
