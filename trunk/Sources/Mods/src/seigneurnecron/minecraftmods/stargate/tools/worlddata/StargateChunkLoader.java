package seigneurnecron.minecraftmods.stargate.tools.worlddata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.LoadingCallback;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;
import seigneurnecron.minecraftmods.core.mod.ModBase;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.tools.loadable.ChunkCoordinates;
import seigneurnecron.minecraftmods.stargate.tools.loadable.StargateCoordinates;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public final class StargateChunkLoader implements LoadingCallback {
	
	// Singleton :
	
	private static final StargateChunkLoader INSTANCE = new StargateChunkLoader();
	
	private StargateChunkLoader() {
		// This is a singleton.
	}
	
	public static StargateChunkLoader getInstance() {
		return INSTANCE;
	}
	
	// Fields :
	
	private final Map<StargateCoordinates, Ticket> registeredGates = new HashMap<StargateCoordinates, Ticket>();
	
	// Methods :
	
	@Override
	public void ticketsLoaded(List<Ticket> tickets, World world) {
		for(Ticket ticket : tickets) {
			StargateCoordinates stargate = new StargateCoordinates(ticket.getModData());
			this.registeredGates.put(stargate, ticket);
		}
	}
	
	public void registerGate(StargateCoordinates stargate) {
		Ticket ticket = ForgeChunkManager.requestTicket(StargateMod.instance, ModBase.getSideWorldForDimension(stargate.dim), Type.NORMAL);
		
		if(ticket != null) {
			this.registeredGates.put(stargate, ticket);
			ChunkCoordinates coords = stargate.getChunkCoordinates();
			
			for(int i = -1; i <= 1; i++) {
				for(int j = -1; j <= 1; j++) {
					ForgeChunkManager.forceChunk(ticket, new ChunkCoordIntPair(coords.x + i, coords.z + j));
				}
			}
		}
		else {
			StargateMod.instance.log("Cant get a chunk loader ticket for a stargate. Stargates may not update correctly.", Level.SEVERE);
		}
	}
	
	public void unregisterGate(StargateCoordinates stargate) {
		ForgeChunkManager.releaseTicket(this.registeredGates.remove(stargate));
	}
	
}
