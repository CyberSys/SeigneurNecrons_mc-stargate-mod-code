package seigneurnecron.minecraftmods.stargate.tileentity.console;

import java.util.LinkedList;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkPosition;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityConsoleBase;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargateControl;
import seigneurnecron.minecraftmods.stargate.tools.enums.GateState;

/**
 * @author Seigneur Necron
 */
public abstract class ConsoleStargate extends Console {
	
	// NBTTags names :
	
	private static final String LINKED_TO_GATE = "linkedToGate";
	private static final String X_GATE = "xGate";
	private static final String Y_GATE = "yGate";
	private static final String Z_GATE = "zGate";
	
	// Constants :

	/**
	 * The maximum range in which a DHD can connect to a gate.
	 */
	public static final int MAX_RANGE = 20;
	
	// Fields :
	
	/**
	 * Indicates whether the DHD is connected to a gate.
	 */
	private boolean linkedToGate = false;
	
	/**
	 * The X coordinate of the gate connected to the DHD.
	 */
	private int xGate = 0;
	
	/**
	 * The Y coordinate of the gate connected to the DHD.
	 */
	private int yGate = 0;
	
	/**
	 * The Z coordinate of the gate connected to the DHD.
	 */
	private int zGate = 0;
	
	// Constructors :
	
	protected ConsoleStargate(TileEntityConsoleBase tileEntity) {
		super(tileEntity);
	}
	
	// Getters :
	
	/**
	 * Indicates whether the DHD is connected to a gate.
	 * @return true if the DHD is connected to a gate, else false.
	 */
	public boolean isLinkedToGate() {
		return this.linkedToGate;
	}
	
	/**
	 * Returns the X coordinate of the gate connected to the DHD.
	 * @return the X coordinate of the gate connected to the DHD.
	 */
	public int getXGate() {
		return this.xGate;
	}
	
	/**
	 * Returns the Y coordinate of the gate connected to the DHD.
	 * @return the Y coordinate of the gate connected to the DHD.
	 */
	public int getYGate() {
		return this.yGate;
	}
	
	/**
	 * Returns the Z coordinate of the gate connected to the DHD.
	 * @return the Z coordinate of the gate connected to the DHD.
	 */
	public int getZGate() {
		return this.zGate;
	}
	
	// Setters :
	
	/**
	 * Updates the the DHD state (inform clients).
	 * @param linkedToGate - true if the DHD is connected to a gate, else false.
	 */
	private void setLinkedToGate(boolean linkedToGate) {
		this.linkedToGate = linkedToGate;
		this.tileEntity.onConsoleDataChanged();
	}
	
	/**
	 * Updates the gate position.
	 * @param x - the gate X coordinate.
	 * @param y - the gate Y coordinate.
	 * @param z - the gate Z coordinate.
	 */
	private void setGatePos(int x, int y, int z) {
		this.xGate = x;
		this.yGate = y;
		this.zGate = z;
	}
	
	// Methods :
	
	/**
	 * Searches for stargates in the area and registers the coordinates of the nearest stargate.
	 */
	private void searchGate() {
		// No need to search if the DHD is already connected to a stargate.
		if(!this.linkedToGate && this.tileEntity.isIntact()) {
			// Searches all the control units within range.
			LinkedList<ChunkPosition> controlUnitsList = new LinkedList<ChunkPosition>();
			
			// Searches in a cube with a side length of MAX_RANGE.
			for(int i = -MAX_RANGE; i <= MAX_RANGE; ++i) {
				for(int j = -MAX_RANGE; j <= MAX_RANGE; ++j) {
					for(int k = -MAX_RANGE; k <= MAX_RANGE; ++k) {
						int x = this.tileEntity.xCoord + k;
						int y = this.tileEntity.yCoord + i;
						int z = this.tileEntity.zCoord + j;
						
						// If the block is a control unit, adds it to the list of stargates that can be linked to the DHD.
						if(this.tileEntity.worldObj.getBlockId(x, y, z) == StargateMod.block_stargateControl.blockID) {
							TileEntity tileEntity = this.tileEntity.worldObj.getBlockTileEntity(x, y, z);
							
							if(tileEntity instanceof TileEntityStargateControl && ((TileEntityStargateControl) tileEntity).getState() != GateState.BROKEN) {
								controlUnitsList.add(new ChunkPosition(x, y, z));
							}
						}
					}
				}
			}
			
			// If at least one control unit was found.
			if(controlUnitsList.size() > 0) {
				// Takes the first control unit.
				ChunkPosition pos = controlUnitsList.get(0);
				this.setGatePos(pos.x, pos.y, pos.z);
				
				// Goes through the list, looking for the nearest control unit.
				for(int i = 1; i < controlUnitsList.size(); i++) {
					pos = controlUnitsList.get(i);
					
					// If the new control unit is closer than the previous one, links the DHD with the new control unit.
					if(this.tileEntity.squaredDistance(pos.x, pos.y, pos.z) < this.tileEntity.squaredDistance(this.xGate, this.yGate, this.zGate)) {
						this.setGatePos(pos.x, pos.y, pos.z);
					}
				}
				
				// Updates the DHD state.
				this.setLinkedToGate(true);
			}
		}
	}
	
	/**
	 * Return the tile entity of the stargate linked to this console.
	 * @return the tile entity of the stargate linked to this console if it exists, else null.
	 */
	public TileEntityStargateControl getStargateControl() {
		this.searchGate();
		
		if(this.linkedToGate) {
			TileEntity tileEntity = this.tileEntity.worldObj.getBlockTileEntity(this.xGate, this.yGate, this.zGate);
			if(tileEntity != null && tileEntity instanceof TileEntityStargateControl) {
				return (TileEntityStargateControl) tileEntity;
			}
			else {
				this.setLinkedToGate(false);
				return this.getStargateControl();
			}
		}
		
		return null;
	}
	
	/**
	 * Called when the stargate linked to this console is destroyed.
	 */
	public void onStargateDestroyed(TileEntityStargateControl stargate) {
		this.setLinkedToGate(false);
	}
	
	/**
	 * Informs the stargate that the console has been destroyed.
	 * @param invent - the stargate tile entity.
	 */
	protected void onStargateConsoleDestroyed(TileEntityStargateControl stargate) {
		// Nothing here.
	}
	
	@Override
	public final void onConsoleDestroyed() {
		TileEntityStargateControl stargate = this.getStargateControl();
		
		if(stargate != null) {
			this.onStargateConsoleDestroyed(stargate);
			this.setLinkedToGate(false);
		}
	}
	
	@Override
	public boolean isActive() {
		return this.isLinkedToGate();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.linkedToGate = compound.getBoolean(LINKED_TO_GATE);
		this.xGate = compound.getInteger(X_GATE);
		this.yGate = compound.getInteger(Y_GATE);
		this.zGate = compound.getInteger(Z_GATE);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setBoolean(LINKED_TO_GATE, this.linkedToGate);
		compound.setInteger(X_GATE, this.xGate);
		compound.setInteger(Y_GATE, this.yGate);
		compound.setInteger(Z_GATE, this.zGate);
	}
	
}
