package seigneurnecron.minecraftmods.stargate.world;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import cpw.mods.fml.common.IWorldGenerator;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class NaquadahGenerator implements IWorldGenerator {
	
	// Methods :
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if(world.provider.dimensionId == 0) {
			this.generateSurface(world, random, chunkX * 16, chunkZ * 16);
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
	
}
