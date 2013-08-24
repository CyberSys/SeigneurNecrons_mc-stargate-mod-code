package seigneurnecron.minecraftmods.stargate.tileentity;

import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.readBoolean;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.readInt;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.writeBoolean;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.writeInt;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet28EntityVelocity;
import net.minecraft.network.packet.Packet34EntityTeleport;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.block.BlockBaseStargateConsole;
import seigneurnecron.minecraftmods.stargate.block.BlockChevron;
import seigneurnecron.minecraftmods.stargate.block.BlockKawoosh;
import seigneurnecron.minecraftmods.stargate.block.BlockNaquadahAlloy;
import seigneurnecron.minecraftmods.stargate.block.BlockPanelStargateConsole;
import seigneurnecron.minecraftmods.stargate.block.BlockPortal;
import seigneurnecron.minecraftmods.stargate.block.BlockStargateControl;
import seigneurnecron.minecraftmods.stargate.block.BlockStargatePart;
import seigneurnecron.minecraftmods.stargate.client.sound.Sound;
import seigneurnecron.minecraftmods.stargate.client.sound.StargateSounds;
import seigneurnecron.minecraftmods.stargate.damageSource.CustomDamageSource;
import seigneurnecron.minecraftmods.stargate.enums.GateActivationSequence;
import seigneurnecron.minecraftmods.stargate.enums.GateActivationState;
import seigneurnecron.minecraftmods.stargate.enums.GateActivationType;
import seigneurnecron.minecraftmods.stargate.enums.GateKawooshState;
import seigneurnecron.minecraftmods.stargate.enums.GateOrientation;
import seigneurnecron.minecraftmods.stargate.enums.GateState;

/**
 * @author Seigneur Necron
 */
public class TileEntityStargateControl extends TileEntityStargate {
	
	// Constants :
	
	public static final String INV_NAME = "container.stargateControl";
	
	/**
	 * The number of ticks in one second.
	 */
	private static final int ONE_SECOND = 20;
	
	/**
	 * The delay before the first chevron is activated : 1 second.
	 */
	private static final int DELAY_BEFORE_FIRST_CHEVRON_ACTIVATION = ONE_SECOND;
	
	/**
	 * The delay between chevrons activation : 1 second.
	 */
	private static final int DELAY_BETWEEN_CHEVRON_ACTIVATIONS = ONE_SECOND;
	
	/**
	 * The delay before the kawoosh : 2 seconds.
	 */
	private static final int DELAY_BEFORE_KAWOOSH = 2 * ONE_SECOND;
	
	/**
	 * The delay between kawoosh updates : 1/10 second.
	 */
	private static final int DELAY_BETWEEN_KAWOOSH_UPDATES = ONE_SECOND / 10;
	
	/**
	 * The maximum activation duration of the gate : 3 minutes.
	 */
	private static final int MAX_ACTIVATED_TIME = 3 * 60 * ONE_SECOND;
	
	/**
	 * The period during which an entity can't be teleported a second time : 1 second.
	 */
	private static final int TELEPORT_LOCK_TIME = ONE_SECOND;
	
	/**
	 * The vortex update period : 1/2 second.
	 */
	private static final int VORTEX_UPDATE_PERIOD = ONE_SECOND / 2;
	
	/**
	 * The vortex update period : 3 second.
	 */
	private static final int DELAY_BEFORE_SHIELD_DEACTIVATION = 3 * ONE_SECOND;
	
	/**
	 * The max width of the kawoosh.
	 */
	public static final int MAX_KAWOOSH_WIDTH = 4;
	
	/**
	 * The max lenght of the kawoosh.
	 */
	public static final int MAX_KAWOOSH_LENGHT = 8;
	
	/**
	 * A shortcut to the BlockNaquadahAlloy class.
	 */
	private static final Class N = BlockNaquadahAlloy.class;
	
	/**
	 * A shortcut to the BlockChevron class.
	 */
	private static final Class C = BlockChevron.class;
	
	/**
	 * A shortcut to the BlockStargateControl class.
	 */
	private static final Class S = BlockStargateControl.class;
	
	/**
	 * The pattern used to check if a stargate is well contructed.
	 */
	private static Class[][] PATTERN_BLOCKS = { {C, N}, {S, N, N, N}, {N, N, C}, {N, N}, {N, N}, {N, N}, {N, C}, {N, N}, {N, N}, {N, N}, {N, C}, {N, N}, {N, N, N}, {N, N, N, C}, {N, N}};
	
	/**
	 * A table containing the number of empty blocks in each row of the stargate pattern.
	 */
	private static int[] PATTERN_AIR = {0, 0, 2, 4, 5, 5, 6, 6, 6, 5, 5, 4, 2, 0, 0};
	
	/**
	 * The parttern offset on the Y axis relative to the gate control block.
	 */
	private static int PATTERN_OFFSET = 1;
	
	/**
	 * A table containing values used to calculate the chevrons coordinates.
	 */
	private static final int[][] CHEVRONS = { {-1, 4}, {-5, 7}, {-9, 6}, {-9, -6}, {-5, -7}, {-1, -4}, {1, 0}, {-12, 3}, {-12, -3}};
	
	// Fields :
	
	/**
	 * The gate state : Broken/Off/Activating/Output/Input/Kawoosh.
	 */
	private GateState state = GateState.BROKEN;
	
	/**
	 * The gate activation type : Failed/Output/Input.
	 */
	private GateActivationType activationType = GateActivationType.FAILED;
	
	/**
	 * The gate activation sequence : S7/S8/S9.
	 */
	private GateActivationSequence activationSequence = GateActivationSequence.S7;
	
	/**
	 * The gate activation state : E0/E1/E2/E3/E4/E5/E6/E7.
	 */
	private GateActivationState activationState = GateActivationState.E0;
	
	/**
	 * The gate kawoosh state : K0/K1...
	 */
	private GateKawooshState kawooshState = GateKawooshState.K0;
	
	/**
	 * The shield state.
	 */
	private boolean shieldActivated = false;
	
	/**
	 * Indicates whether the shield must be automatically activated.
	 */
	private boolean shieldAutomated = false;
	
	/**
	 * Used to automatically deactivated the shield when the gate is closed and the shield is in automatic mode.
	 */
	private boolean shieldWaitingDeativation = false;
	
	/**
	 * The code which deactivates the shield.
	 */
	private int code = 0;
	
	/**
	 * The counter used for activation and deactivation of the gate.
	 */
	private int count = 0;
	
	/**
	 * The X coordinate of the destination (another gate).
	 */
	private int xDest = 0;
	
	/**
	 * The Y coordinate of the destination (another gate).
	 */
	private int yDest = 0;
	
	/**
	 * The Z coordinate of the destination (another gate).
	 */
	private int zDest = 0;
	
	/**
	 * A map containing the list of recently teleported entities and counters indicating how long ago they were teleported.
	 */
	private HashMap<Integer, Integer> teleportedEntities = new HashMap<Integer, Integer>();
	
	// Methods :
	
	/**
	 * Returns the gate state : Broken/Off/Activating/Output/Input/Kawoosh.
	 * @return the gate state : Broken/Off/Activating/Output/Input/Kawoosh.
	 */
	public GateState getState() {
		return this.state;
	}
	
	/**
	 * Returns the gate activation type : Failed/Output/Input.
	 * @return the gate activation type : Failed/Output/Input.
	 */
	public GateActivationType getActivationType() {
		return this.activationType;
	}
	
	/**
	 * Returns the gate activation sequence : S7/S8/S9.
	 * @return the gate activation sequence : S7/S8/S9.
	 */
	public GateActivationSequence getActivationSequence() {
		return this.activationSequence;
	}
	
	/**
	 * Returns gate activation state : E0/E1/E2/E3/E4/E5/E6/E7.
	 * @return gate activation state : E0/E1/E2/E3/E4/E5/E6/E7.
	 */
	public GateActivationState getActivationState() {
		return this.activationState;
	}
	
	/**
	 * Returns the gate kawoosh state : K0/K1...
	 * @return the gate kawoosh state : K0/K1...
	 */
	public GateKawooshState getKawooshState() {
		return this.kawooshState;
	}
	
	/**
	 * Returns the shield state.
	 * @return the shield state.
	 */
	public boolean isShieldActivated() {
		return this.shieldActivated;
	}
	
	/**
	 * Indicates whether the shield must be automatically activated.
	 * @return true if the shield must be automatically activated, else false.
	 */
	public boolean isShieldAutomated() {
		return this.shieldAutomated;
	}
	
	/**
	 * Used to automatically deactivated the shield when the gate is closed and the shield is in automatic mode.
	 * @return true if the shield must be deativated during the next vortex update, else false.
	 */
	public boolean isShieldWaitingDeativation() {
		return this.shieldWaitingDeativation;
	}
	
	/**
	 * Returns the code which deactivates the shield.
	 * @return the code which deactivates the shield.
	 */
	public int getCode() {
		return this.code;
	}
	
	/**
	 * Returns the value of the counter used for activation and deactivation of the gate.
	 * @return the value of the counter used for activation and deactivation of the gate.
	 */
	public int getCount() {
		return this.count;
	}
	
	/**
	 * Returns the X coordinate of the destination (another gate).
	 * @return the X coordinate of the destination (another gate).
	 */
	public int getXDest() {
		return this.xDest;
	}
	
	/**
	 * Returns the Y coordinate of the destination (another gate).
	 * @return the Y coordinate of the destination (another gate).
	 */
	public int getYDest() {
		return this.yDest;
	}
	
	/**
	 * Returns the Z coordinate of the destination (another gate).
	 * @return the Z coordinate of the destination (another gate).
	 */
	public int getZDest() {
		return this.zDest;
	}
	
	/**
	 * Sets the gate state and informs clients.
	 * @param state - the new gate state.
	 */
	private void setState(GateState state) {
		this.state = state;
		
		if(state == GateState.OFF) {
			this.setKawooshState(GateKawooshState.K0);
			this.setActivationState(GateActivationState.E0);
		}
		else if(state == GateState.BROKEN) {
			this.onShieldConsoleDestroyed();
		}
		else {
			this.updateClients();
		}
	}
	
	/**
	 * Sets the gate activation type.
	 * @param activationType - the new activation type.
	 */
	private void setActivationType(GateActivationType activationType) {
		this.activationType = activationType;
	}
	
	/**
	 * Sets the gate activation sequence.
	 * @param activationSequence - the new activation sequence.
	 */
	private void setActivationSequence(GateActivationSequence activationSequence) {
		this.activationSequence = activationSequence;
	}
	
	/**
	 * Sets the gate activation state and imforms clients.
	 * @param activationState - the new activation state.
	 */
	private void setActivationState(GateActivationState activationState) {
		this.activationState = activationState;
		
		if(activationState == GateActivationState.E0) {
			for(int i = 1; i <= 9; i++) {
				this.deactivateChevron(i);
			}
		}
		else {
			// If the gate is almost activated and the shield is in automatic mode, activates the shield.
			if(activationState == GateActivationState.E7 && this.shieldAutomated) {
				this.setShieldActivated(true);
			}
			
			this.activateChevron(this.activationState.getValue());
		}
		
		this.updateClients();
	}
	
	/**
	 * Sets the gate kawoosh state.
	 * @param kawooshState - the new gate kawoosh state.
	 */
	private void setKawooshState(GateKawooshState kawooshState) {
		this.kawooshState = kawooshState;
		
		if(this.kawooshState != GateKawooshState.K0) {
			this.proceedKawoosh();
		}
	}
	
	/**
	 * Sets the shield state.
	 * @param shieldActivated - the new shield state.
	 */
	private void setShieldActivated(boolean shieldActivated) {
		boolean needVortexUpdate = this.shieldActivated != shieldActivated;
		this.shieldActivated = shieldActivated;
		this.updateClients();
		
		if(needVortexUpdate) {
			// Plays the sound corresponding to a shield activation/deactivation.
			this.playSoundEffect(this.shieldActivated ? StargateSounds.stargateShieldActivation : StargateSounds.stargateShieldDeactivation);
			
			// Updates the shield.
			this.updateVortex();
		}
	}
	
	/**
	 * Sets the automatic shield activation on or off.
	 * @param shieldAutomated - true if the shield must be automatically activated, else false.
	 */
	private void setShieldAutomated(boolean shieldAutomated) {
		this.shieldAutomated = shieldAutomated;
	}
	
	/**
	 * Used to automatically deactivated the shield when the gate is closed and the shield is in automatic mode.
	 * @param shieldWaitingDeativation - true if the shield must be deativated during the next vortex update, else false.
	 */
	public void setShieldWaitingDeativation(boolean shieldWaitingDeativation) {
		this.shieldWaitingDeativation = shieldWaitingDeativation;
	}
	
	/**
	 * Sets the code which deactivates the shield.
	 * @param code - the code which deactivates the shield.
	 */
	private void setCode(int code) {
		this.code = code;
	}
	
	/**
	 * Sets the value of the counter used for activation and deactivation of the gate.
	 * @param count - the new value for the counter.
	 */
	private void setCount(int count) {
		this.count = count;
	}
	
	/**
	 * Registers the destination coordinates.
	 * @param x - the X coordinate of the destination.
	 * @param y - the Y coordinate of the destination.
	 * @param z - the Z coordinate of the destination.
	 */
	private void setDestination(int x, int y, int z) {
		this.xDest = x;
		this.yDest = y;
		this.zDest = z;
	}
	
	/**
	 * Checks that the gate can be activated. <br />
	 * The gate state must be OFF.
	 * @return true id the gate can be activated, else false.
	 */
	public boolean isActivable() {
		return this.state == GateState.OFF;
	}
	
	/**
	 * Checks that the gate is complete/intact. <br />
	 * If there was a gate which has been damaged, it will be deactivated.
	 * @return true if there is a valid gate at this position, else false.
	 */
	public boolean checkPattern() {
		// Initialization.
		StargateMod.debug("Checking gate pattern...", true);
		int x = this.xCoord;
		int y = this.yCoord;
		int z = this.zCoord;
		
		// If the gate is situated too low on the Y axis.
		if(y < PATTERN_BLOCKS.length) {
			// The pattern can't be matched.
			StargateMod.debug("The gate is situated too low on the Y axis !\n", true);
			return false;
		}
		
		// The gate is oriented on the X or Z axis.
		int metadata = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
		int xAxis = (metadata == 2 || metadata == 3) ? 1 : 0;
		int zAxis = (metadata == 4 || metadata == 5) ? 1 : 0;
		
		// Goes through the pattern, line by line on the Y axis.
		for(int i = 0; i < PATTERN_BLOCKS.length; ++i) {
			int nbAirBlocks = PATTERN_AIR[i];
			int borderWidth = PATTERN_BLOCKS[i].length;
			int halfedWidth = nbAirBlocks + borderWidth;
			StargateMod.debug("     ", false, 9 - halfedWidth);
			
			// Goes through the line (X ou Z axis).
			for(int j = -halfedWidth + 1; j < halfedWidth; ++j) {
				// If on the gate.
				if(Math.abs(j) >= nbAirBlocks) {
					y = this.yCoord - i + PATTERN_OFFSET;
					x = this.xCoord + (xAxis * j);
					z = this.zCoord + (zAxis * j);
					
					// If not on the position of the control unit.
					if(j != 0 || i != PATTERN_OFFSET) {
						int blockId = this.worldObj.getBlockId(x, y, z);
						int blockIndex = Math.abs(j) - nbAirBlocks;
						StargateMod.debug(blockId + " ", false);
						
						// If the block doesn't match the pattern.
						if(!isBlockIdMatchingPattern(blockId, i, blockIndex)) {
							// The gate is broken.
							StargateMod.debug("", true);
							StargateMod.debug("Error in gate pattern !\n", true);
							this.setBroken();
							return false;
						}
						// If the gate is not created yet.
						else if(this.state == GateState.BROKEN) {
							TileEntityStargatePart tileEntity = (TileEntityStargatePart) this.worldObj.getBlockTileEntity(x, y, z);
							// If the block already belongs to another gate.
							if(tileEntity != null && tileEntity.isPartOfGate()) {
								// It can't belong to this gate.
								StargateMod.debug("", true);
								StargateMod.debug("This block already belongs to another gate !\n", true);
								return false;
							}
						}
					}
					else {
						StargateMod.debug("---- ", false);
					}
				}
				else {
					// Else, goes to the other side of the gate.
					StargateMod.debug("     ", false, nbAirBlocks * 2 - 1);
					j = nbAirBlocks - 1;
				}
			}
			StargateMod.debug("", true);
		}
		
		// The pattern is checked.
		StargateMod.debug("Gate pattern OK !\n", true);
		return true;
	}
	
	/**
	 * Creates a stargate, if possible. <br />
	 * Informs each block of the gate that they now belong to a gate, and give numbers to chevrons.
	 * @return true if the creation was successfull, else false.
	 */
	public boolean createGate() {
		// If the gate is not created yet, and the pattern is checked.
		if(this.state == GateState.BROKEN && this.checkPattern()) {
			// Initialization.
			StargateMod.debug("Creation of the gate...", true);
			int x = this.xCoord;
			int y = this.yCoord;
			int z = this.zCoord;
			
			// The gate is oriented on the X or Z axis.
			int metadata = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
			int xAxis = (metadata == 2 || metadata == 3) ? 1 : 0;
			int zAxis = (metadata == 4 || metadata == 5) ? 1 : 0;
			
			// Goes through the pattern, line by line on the Y axis.
			for(int i = 0; i < PATTERN_BLOCKS.length; ++i) {
				int nbAirBlocks = PATTERN_AIR[i];
				int borderWidth = PATTERN_BLOCKS[i].length;
				int halfedWidth = nbAirBlocks + borderWidth;
				StargateMod.debug("    ", false, 9 - halfedWidth);
				
				// Goes through the line (X ou Z axis).
				for(int j = -halfedWidth + 1; j < halfedWidth; ++j) {
					// If on the gate.
					if(Math.abs(j) >= nbAirBlocks) {
						y = this.yCoord - i + PATTERN_OFFSET;
						x = this.xCoord + (xAxis * j);
						z = this.zCoord + (zAxis * j);
						
						// If not on the position of the control unit.
						if(j != 0 || i != PATTERN_OFFSET) {
							// Links the block to the gate.
							StargateMod.debug("OOO ", false);
							((TileEntityStargatePart) this.worldObj.getBlockTileEntity(x, y, z)).setGate(this.xCoord, this.yCoord, this.zCoord);
						}
						else {
							StargateMod.debug("--- ", false);
						}
					}
					else {
						// Else, goes to the other side of the gate.
						StargateMod.debug("    ", false, nbAirBlocks * 2 - 1);
						j = nbAirBlocks - 1;
					}
				}
				StargateMod.debug("", true);
			}
			
			// Gives a number to each chevron.
			for(int i = 1; i <= 9; ++i) {
				TileEntityChevron chevron = this.getChevron(i);
				if(chevron != null) {
					chevron.setNo(i);
				}
			}
			
			// Updates the gate state.
			StargateMod.debug("Creation complete.\n", true);
			this.setState(GateState.OFF);
			return true;
		}
		
		// The creation has failed.
		return false;
	}
	
	/**
	 * Closes the gate and break the link with all the gate blocks.
	 */
	public void setBroken() {
		// First, closes the gate if it is open.
		this.close();
		
		// If the gate is already broken, there is nothing to do.
		if(this.state != GateState.BROKEN) {
			// Initialization.
			StargateMod.debug("Deactivating the gate...", true);
			int x = this.xCoord;
			int y = this.yCoord;
			int z = this.zCoord;
			
			// The gate is oriented on the X or Z axis.
			int metadata = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
			int xAxis = (metadata == 2 || metadata == 3) ? 1 : 0;
			int zAxis = (metadata == 4 || metadata == 5) ? 1 : 0;
			
			// Goes through the pattern, line by line on the Y axis.
			for(int i = 0; i < PATTERN_BLOCKS.length; ++i) {
				int nbAirBlocks = PATTERN_AIR[i];
				int borderWidth = PATTERN_BLOCKS[i].length;
				int halfedWidth = nbAirBlocks + borderWidth;
				StargateMod.debug("    ", false, 9 - halfedWidth);
				
				// Goes through the line (X ou Z axis).
				for(int j = -halfedWidth + 1; j < halfedWidth; ++j) {
					// If on the gate.
					if(Math.abs(j) >= nbAirBlocks) {
						y = this.yCoord - i + PATTERN_OFFSET;
						x = this.xCoord + (xAxis * j);
						z = this.zCoord + (zAxis * j);
						
						// If not on the position of the control unit.
						if(j != 0 || i != PATTERN_OFFSET) {
							TileEntity tileEntity = this.worldObj.getBlockTileEntity(x, y, z);
							
							// If the block is still there.
							if(tileEntity != null && tileEntity instanceof TileEntityStargatePart) {
								// Breaks the link with the gate.
								StargateMod.debug("XXX ", false);
								((TileEntityStargatePart) tileEntity).breakGate();
							}
							else {
								StargateMod.debug("--- ", false);
							}
						}
						else {
							StargateMod.debug("--- ", false);
						}
					}
					else {
						// Else, goes to the other side of the gate.
						StargateMod.debug("    ", false, nbAirBlocks * 2 - 1);
						j = nbAirBlocks - 1;
					}
				}
				StargateMod.debug("", true);
			}
			
			// Updates the gate state.
			StargateMod.debug("Deactivation complete.\n", true);
			this.setState(GateState.BROKEN);
		}
	}
	
	/**
	 * Builds a gate at the given coordinates.
	 * @param world - the world in which the gate must be built.
	 * @param x - the X coordinate.
	 * @param y - the Y coordinate.
	 * @param z - the Z coordinate.
	 * @param side - the gate orientation.
	 * @return true if the gate was successfully built, else false.
	 */
	public static boolean constructGate(World world, int xCoord, int yCoord, int zCoord, int side) {
		// Initialization.
		StargateMod.debug("Building a gate for testing...", true);
		
		// If the gate is situated too low on the Y axis.
		if(yCoord < PATTERN_BLOCKS.length) {
			// The pattern can't be matched.
			StargateMod.debug("The gate is situated too low on the Y axis !\n", true);
			return false;
		}
		
		// If the gate orientation is impossible.
		if(side < 2 && side > 5) {
			// The pattern can't be matched.
			StargateMod.debug("This gate orientation is impossible !\n", true);
			return false;
		}
		
		// The gate is oriented on the X or Z axis.
		int xAxis = (side == 2 || side == 3) ? 1 : 0;
		int zAxis = (side == 4 || side == 5) ? 1 : 0;
		
		// Goes through the pattern, line by line on the Y axis.
		for(int i = 0; i < PATTERN_BLOCKS.length; ++i) {
			int nbAirBlocks = PATTERN_AIR[i];
			int borderWidth = PATTERN_BLOCKS[i].length;
			int halfedWidth = nbAirBlocks + borderWidth;
			StargateMod.debug("     ", false, 9 - halfedWidth);
			
			// Goes through the line (X ou Z axis).
			for(int j = -halfedWidth + 1; j < halfedWidth; ++j) {
				// If on the gate.
				if(Math.abs(j) >= nbAirBlocks) {
					int y = yCoord - i + PATTERN_OFFSET;
					int x = xCoord + (xAxis * j);
					int z = zCoord + (zAxis * j);
					int blockIndex = Math.abs(j) - nbAirBlocks;
					int blockId = getBlockIdFromPattern(i, blockIndex);
					
					// Builds the gate.
					world.setBlock(x, y, z, blockId);
					StargateMod.debug(blockId + " ", false);
				}
				else {
					// Else, goes to the other side of the gate.
					StargateMod.debug("     ", false, nbAirBlocks * 2 - 1);
					j = nbAirBlocks - 1;
				}
			}
			StargateMod.debug("", true);
		}
		
		// Sets the control unit in the right direction.
		world.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, side, 3);
		
		// The construction is complete.
		StargateMod.debug("Construction complete !\n", true);
		return true;
	}
	
	/**
	 * This method is called when the DHD is activated. Closes the gate if it is open, opens it if it is closed. <br />
	 * The activation process can only be canceled from the input side.
	 * @param x - the X coordinate of the destination.
	 * @param y - the Y coordinate of the destination.
	 * @param z - the Z coordinate of the destination.
	 */
	public void onDhdActivation(int x, int y, int z) {
		// If the gate is activated, or activating from that side.
		if(this.state == GateState.INPUT || this.state == GateState.OUTPUT || (this.state == GateState.ACTIVATING && this.activationType != GateActivationType.OUTPUT)) {
			// Closes the gate.
			this.close();
		}
		else if(this.state == GateState.OFF || this.state == GateState.BROKEN) {
			// Else, activates the gate.
			this.activate(x, y, z);
		}
	}
	
	/**
	 * This method is called when the shield consol is activated. Activates the shield if it is possible, deactivates it if it is activated.
	 * @param panelActivated - indicates whether the shield must be activated/deactivted when receiving the informations.
	 * @param shieldAutomated - indicates whether the shield must be automatically activated.
	 * @param code - the code which deactivates the shield.
	 */
	public void onShieldConsoleActivated(boolean panelActivated, boolean shieldAutomated, int code) {
		this.setCode(code);
		this.setShieldAutomated(shieldAutomated);
		
		if(panelActivated) {
			if(this.shieldActivated) {
				this.setShieldActivated(false);
			}
			else if(this.state == GateState.INPUT || this.state == GateState.OUTPUT || this.state == GateState.ACTIVATING || this.state == GateState.OFF) {
				this.setShieldActivated(true);
			}
		}
	}
	
	/**
	 * This method is called when the shield consol is destroyed. Deactivates the shield, and the automatic activation of the shield.
	 */
	public void onShieldConsoleDestroyed() {
		this.setCode(0);
		this.setShieldAutomated(false);
		this.setShieldActivated(false);
	}
	
	/**
	 * Activates the gate, creating a vortex to the other gate at the given coordinates, if possible.
	 * @param x - the X coordinate of the destination.
	 * @param y - the Y coordinate of the destination.
	 * @param z - the Z coordinate of the destination.
	 */
	public void activate(int x, int y, int z) {
		// If this gate isn't created yet, Tries to create it; if it don't work, exits.
		if(this.state == GateState.BROKEN && !this.createGate()) {
			StargateMod.debug("The gate couldn't be created !", true);
			return;
		}
		
		// If the given coordinates are those of that gate, exits.
		if(x == this.xCoord && y == this.yCoord && z == this.zCoord) {
			StargateMod.debug("The input gate can't be the output gate ! >.<", true);
			return;
		}
		
		// If the input gate can't be activated, exits.
		if(!this.isActivable()) {
			StargateMod.debug("The gate couldn't be activated !", true);
			return;
		}
		
		// Choose an activation sequence.
		GateActivationSequence activationSequence = GateActivationSequence.S7;
		
		// If there is no gate at the given coordinates, launches a false activation.
		if(this.worldObj.getBlockId(x, y, z) != StargateMod.block_stargateControl.blockID) {
			StargateMod.debug("Wrong number ! try again ! XD", true);
			this.setActivating(GateActivationType.FAILED, activationSequence);
			return;
		}
		
		// Gets the tile entity of the other gate control unit.
		TileEntityStargateControl otherGate = (TileEntityStargateControl) this.worldObj.getBlockTileEntity(x, y, z);
		
		// If the output gate can't be activated, launches a false activation.
		if(!otherGate.isActivable()) {
			StargateMod.debug("The gate you are trying to reach is currently busy, please try again later... XD", true);
			this.setActivating(GateActivationType.FAILED, activationSequence);
			return;
		}
		
		// Registers the destination.
		this.setDestination(x, y, z);
		otherGate.setDestination(this.xCoord, this.yCoord, this.zCoord);
		
		// Activates the gates.
		this.setActivating(GateActivationType.INPUT, activationSequence);
		otherGate.setActivating(GateActivationType.OUTPUT, activationSequence);
	}
	
	/**
	 * Starts the activation process of the gate.
	 * @param activationType - the activation type (Failed/Output/Input).
	 */
	private void setActivating(GateActivationType activationType, GateActivationSequence activationSequence) {
		this.setCount(DELAY_BEFORE_FIRST_CHEVRON_ACTIVATION);
		this.setActivationType(activationType);
		this.setActivationSequence(activationSequence);
		this.setState(GateState.ACTIVATING);
	}
	
	/**
	 * Closes the gate.
	 */
	public void close() {
		// If the gate was activated or activating.
		if(this.state == GateState.INPUT || this.state == GateState.OUTPUT || this.state == GateState.ACTIVATING || this.state == GateState.KAWOOSH) {
			TileEntityStargateControl otherGate = (TileEntityStargateControl) this.worldObj.getBlockTileEntity(this.xDest, this.yDest, this.zDest);
			
			// Deactivates this gate.
			this.deletePortal();
			
			// Deactivate the other gate, if it exists.
			if(otherGate != null) {
				otherGate.deletePortal();
			}
		}
	}
	
	/**
	 * Replaces each block that can be replaced by a votex block.
	 */
	private void updateVortex() {
		// Initialization.
		int blockId = (this.state == GateState.INPUT || this.state == GateState.OUTPUT || this.state == GateState.KAWOOSH) ? (this.shieldActivated ? StargateMod.block_shieldedVortex.blockID : StargateMod.block_vortex.blockID) : (this.shieldActivated ? StargateMod.block_shield.blockID : 0);
		int x = this.xCoord;
		int y = this.yCoord;
		int z = this.zCoord;
		
		// The gate is oriented on the X or Z axis.
		int metadata = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
		int xAxis = (metadata == 2 || metadata == 3) ? 1 : 0;
		int zAxis = (metadata == 4 || metadata == 5) ? 1 : 0;
		
		// Goes through the pattern, line by line on the Y axis.
		for(int i = 0; i < PATTERN_BLOCKS.length; ++i) {
			int nbAirBlocks = PATTERN_AIR[i];
			
			// Goes through the line (X ou Z axis).
			for(int j = -nbAirBlocks + 1; j < nbAirBlocks; ++j) {
				y = this.yCoord - i + PATTERN_OFFSET;
				x = this.xCoord + (xAxis * j);
				z = this.zCoord + (zAxis * j);
				
				// If the block can be replaced by vortex.
				if(isBlockReplaceableByVortex(this.worldObj, x, y, z, blockId)) {
					// Creates a vortex block.
					this.worldObj.setBlock(x, y, z, blockId);
					
					// And links it to the gate.
					if(blockId != 0) {
						TileEntityStargatePart tileEntity = (TileEntityStargatePart) this.worldObj.getBlockTileEntity(x, y, z);
						tileEntity.setGate(this.xCoord, this.yCoord, this.zCoord);
					}
				}
			}
		}
	}
	
	/**
	 * Creates the vortex blocks.
	 */
	private void createPortal() {
		// Updates the gate state.
		switch(this.activationType) {
			case INPUT:
				this.setState(GateState.INPUT);
				break;
			case OUTPUT:
				this.setState(GateState.OUTPUT);
				break;
			default:
		}
		
		// Launches the counter so the gate will deactivate when maximum activation duration is reached.
		this.setCount(MAX_ACTIVATED_TIME);
		
		// Creates the vortex blocks.
		this.updateVortex();
		
		StargateMod.debug("Vortex created.", true);
	}
	
	/**
	 * Deletes the vortex (and kawoosh) blocks.
	 */
	private void deletePortal() {
		GateState oldState = this.state;
		
		// Updates the gate state.
		this.setState(GateState.OFF);
		
		// If the activation process wasn't completed, there is no block to delete.
		if(oldState == GateState.ACTIVATING) {
			// Plays the sound corresponding to an interrupted activation.
			this.playSoundEffect(StargateSounds.stargateFail);
		}
		// If the kawoosh wasn't finished.
		else if(oldState == GateState.KAWOOSH) {
			// Delete remaining kawoosh.
			this.deleteKawoosh();
		}
		// Else, delete vortex.
		else {
			this.updateVortex();
			
			StargateMod.debug("Vortex deleted.", true);
			
			// Clears the list of recently teleported entities.
			this.teleportedEntities.clear();
			
			// Plays the sound corresponding to a closed gate.
			this.playSoundEffect(StargateSounds.stargateClose);
		}
		
		// Counter for shield automatic deactivation.
		if(this.shieldAutomated) {
			this.setShieldWaitingDeativation(true);
			this.setCount(DELAY_BEFORE_SHIELD_DEACTIVATION);
		}
	}
	
	private void deleteKawoosh() {
		// Initialization.
		int x = this.xCoord;
		int y = this.yCoord;
		int z = this.zCoord;
		
		// The gate is oriented on the X or Z axis.
		int metadata = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
		int xAxis = (metadata == 2) ? -1 : (metadata == 3) ? 1 : 0;
		int zAxis = (metadata == 4) ? -1 : (metadata == 5) ? 1 : 0;
		
		// Goes through the pattern, line by line on the Y axis.
		for(int i = 0; i < PATTERN_BLOCKS.length; ++i) {
			int nbAirBlocks = PATTERN_AIR[i];
			
			// Goes through the line (X ou Z axis).
			for(int j = -nbAirBlocks + 1; j < nbAirBlocks; ++j) {
				y = this.yCoord - i + PATTERN_OFFSET;
				x = this.xCoord + (xAxis * j);
				z = this.zCoord + (zAxis * j);
				
				// If the block is a kawoosh block, deletes it.
				if(this.worldObj.getBlockId(x, y, z) == StargateMod.block_kawoosh.blockID) {
					this.worldObj.setBlockToAir(x, y, z);
				}
			}
		}
		
		// Calculates gate center coordinates.
		int yCoord = this.yCoord - (PATTERN_AIR.length / 2) + PATTERN_OFFSET;
		int xCoord = this.xCoord + zAxis;
		int zCoord = this.zCoord + xAxis;
		
		for(int i = 0; i < MAX_KAWOOSH_LENGHT; i++) {
			for(int j = -MAX_KAWOOSH_WIDTH + 1; j < MAX_KAWOOSH_WIDTH; j++) {
				for(int k = -MAX_KAWOOSH_WIDTH + 1; k < MAX_KAWOOSH_WIDTH; k++) {
					// Calculates final coordinates.
					y = yCoord + j;
					x = xCoord + (zAxis * i) + (xAxis * k);
					z = zCoord + (xAxis * i) + (zAxis * k);
					
					// If the block is a kawoosh block, deletes it.
					if(this.worldObj.getBlockId(x, y, z) == StargateMod.block_kawoosh.blockID) {
						this.worldObj.setBlockToAir(x, y, z);
					}
				}
			}
		}
		
		StargateMod.debug("Kawoosh deleted.", true);
	}
	
	/**
	 * This method is called each tick. It is used to update the state of the gate during activation and to deactivate the gate when maximum activation duration is reached.
	 */
	@Override
	public void updateEntity() {
		// If server side.
		if(!this.worldObj.isRemote) {
			// Updates counter.
			if(this.count > 0) {
				--this.count;
			}
			
			// Depending on the state of the gate...
			switch(this.state) {
				case INPUT:
					// If maximum activation duration was reached, closes the gate.
					if(this.count <= 0) {
						this.close();
						break;
					}
					// Updates the list of recently teleported entities.
					this.updateTeleportedEntityList();
					
					// No "break", this is normal.
				case OUTPUT:
					// Update vortex and shield.
					if(this.count % VORTEX_UPDATE_PERIOD == 0) {
						this.updateVortex();
					}
					break;
				case OFF:
					// Update shield.
					if(this.count == 0 && this.isShieldActivated()) {
						if(this.shieldWaitingDeativation) {
							this.setShieldWaitingDeativation(false);
							this.setShieldActivated(false);
						}
						
						this.updateVortex();
						this.setCount(VORTEX_UPDATE_PERIOD);
					}
					break;
				case ACTIVATING:
					// If it is time to activate the next chevron.
					if(this.count <= 0) {
						// Depending on the activation step...
						switch(this.activationState) {
						// If 6 chevrons are activated.
							case E6:
								// Depending on the activation sequence...
								if(this.activationSequence == GateActivationSequence.S7) {
									// Activates chevron 7.
									this.setActivationState(GateActivationState.E7);
								}
								else {
									// Activates chevron 8.
									this.setActivationState(GateActivationState.E8);
								}
								break;
							// If all chevrons are activated.
							case E7:
								// If the activation was a fail.
								if(this.activationType == GateActivationType.FAILED) {
									// Plays the sound corresponding to a failed activation.
									this.playSoundEffect(StargateSounds.stargateFail);
									// Updates the gate state and exits.
									this.setState(GateState.OFF);
								}
								else {
									// Plays the sound corresponding to a successfull activation.
									this.playSoundEffect(StargateSounds.stargateOpen);
									
									// Activates the gate.
									if(this.shieldActivated) {
										this.createPortal();
									}
									else {
										this.setState(GateState.KAWOOSH);
									}
								}
								break;
							// If 8 chevrons are activated.
							case E8:
								// Depending on the activation sequence...
								if(this.activationSequence == GateActivationSequence.S8) {
									// Activates chevron 7.
									this.setActivationState(GateActivationState.E7);
								}
								else {
									// Activates chevron 9.
									this.setActivationState(GateActivationState.E9);
								}
								break;
							// If 9 chevrons are activated.
							case E9:
								// Activates chevron 7.
								this.setActivationState(GateActivationState.E7);
								// If less than 6 chevrons are activated.
								break;
							default:
								this.setActivationState(GateActivationState.valueOf(this.activationState.getValue() + 1));
						}
					}
					break;
				case KAWOOSH:
					// If it is time for the next kawoosh update.
					if(this.count <= 0) {
						int step = this.kawooshState.getValue();
						
						// If all kawoosh updates were done.
						if(step == GateKawooshState.LAST_STEP) {
							this.createPortal();
						}
						// If kawoosh updates were not all done.
						else {
							this.setKawooshState(GateKawooshState.valueOf(step + 1));
						}
					}
					break;
				default:
			}
		}
		super.updateEntity();
	}
	
	/**
	 * Updates the map of recently teleported entities. Decrement all counters by one, and deletes entries which have a counter at zero.
	 */
	private void updateTeleportedEntityList() {
		Iterator<Entry<Integer, Integer>> iterator = this.teleportedEntities.entrySet().iterator();
		Entry<Integer, Integer> entry;
		while(iterator.hasNext()) {
			entry = iterator.next();
			if(entry.getValue() <= 0) {
				iterator.remove();
			}
			else {
				entry.setValue(entry.getValue() - 1);
			}
		}
	}
	
	/**
	 * Indicates whether the given block id matches the given line and column of the pattern.
	 * @param blockId - the block id.
	 * @param y - the line of the pattern.
	 * @param x - the column of the pattern.
	 * @return true if the given block id matches the given line and column of the pattern, else false.
	 */
	private static boolean isBlockIdMatchingPattern(int blockId, int y, int x) {
		Block block = Block.blocksList[blockId];
		
		if(block != null) {
			return PATTERN_BLOCKS[y][x].isInstance(block);
		}
		
		return false;
	}
	
	/**
	 * Returns the block id for the given line and column of the pattern.
	 * @param y - the line of the pattern.
	 * @param x - the column of the pattern.
	 * @return the block id for the given line and column of the pattern.
	 */
	private static int getBlockIdFromPattern(int y, int x) {
		Class clazz = PATTERN_BLOCKS[y][x];
		
		if(clazz == N) {
			return StargateMod.block_naquadahAlloy.blockID;
		}
		else if(clazz == C) {
			return StargateMod.block_chevronOff.blockID;
		}
		else if(clazz == S) {
			return StargateMod.block_stargateControl.blockID;
		}
		else {
			throw new RuntimeException("Erreur while building a stargate : the class in the pattern isn't binded to a blockId. This is a bug !");
		}
	}
	
	/**
	 * Indicate whether the block at the given coordinates is replaceable by vortex.
	 * @param world - the world.
	 * @param x - the X coordinate.
	 * @param y - the Y coordinate.
	 * @param z - the Z coordinate.
	 * @return true if the block at the given coordinates is replaceable by vortex, else false.
	 */
	private static boolean isBlockReplaceableByVortex(World world, int x, int y, int z, int blockId) {
		int oldBlockId = world.getBlockId(x, y, z);
		Block block = Block.blocksList[oldBlockId];
		return oldBlockId != blockId && (block == null || (blockId == 0 ? block instanceof BlockPortal : block.isBlockReplaceable(world, x, y, z)));
	}
	
	/**
	 * Indicate whether the block at the given coordinates is replaceable by the flat part of the kawoosh.
	 * @param world - the world.
	 * @param x - the X coordinate.
	 * @param y - the Y coordinate.
	 * @param z - the Z coordinate.
	 * @return true if the block at the given coordinates is replaceable by the flat part of the kawoosh, else false.
	 */
	private static boolean isBlockReplaceableByFlatKawoosh(World world, int x, int y, int z) {
		Block block = Block.blocksList[world.getBlockId(x, y, z)];
		return block == null || (!(block instanceof BlockKawoosh) && block.isBlockReplaceable(world, x, y, z));
	}
	
	/**
	 * Indicate whether the block at the given coordinates is replaceable by kawoosh.
	 * @param world - the world.
	 * @param x - the X coordinate.
	 * @param y - the Y coordinate.
	 * @param z - the Z coordinate.
	 * @return true if the block at the given coordinates is replaceable by kawoosh, else false.
	 */
	private static boolean isBlockReplaceableByKawoosh(World world, int x, int y, int z) {
		Block block = Block.blocksList[world.getBlockId(x, y, z)];
		return block == null || !(block instanceof BlockKawoosh || block instanceof BlockStargatePart || block instanceof BlockStargateControl || block instanceof BlockBaseStargateConsole || block instanceof BlockPanelStargateConsole);
	}
	
	/**
	 * Returns the position of the chevron corresponding to the given number, if it exists.
	 * @param no - the number of the chevron we want to get.
	 * @return the position of chevron corresponding to the given number, or null if (no < 1 || no > 9).
	 */
	private ChunkPosition getChevronPosition(int no) {
		if(no >= 1 && no <= 9) {
			int metadata = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
			int xAxis = (metadata == 3) ? 1 : ((metadata == 2) ? -1 : 0);
			int zAxis = (metadata == 4) ? 1 : ((metadata == 5) ? -1 : 0);
			
			int y = this.yCoord + CHEVRONS[no - 1][0];
			int x = this.xCoord + (xAxis * CHEVRONS[no - 1][1]);
			int z = this.zCoord + (zAxis * CHEVRONS[no - 1][1]);
			
			return new ChunkPosition(x, y, z);
		}
		
		return null;
	}
	
	/**
	 * Returns the tile entity of the chevron at the given position, if it exists.
	 * @param pos - the position of the chevron we want to get.
	 * @return the tile entity of chevron at the given position, if it exists, else null.
	 */
	private TileEntityChevron getChevron(ChunkPosition pos) {
		return (pos != null) ? (TileEntityChevron) this.worldObj.getBlockTileEntity(pos.x, pos.y, pos.z) : null;
	}
	
	/**
	 * Returns the tile entity of the chevron corresponding to the given number, if it exists.
	 * @param no - the number of the chevron we want to get.
	 * @return the tile entity of chevron corresponding to the given number, if it exists, else null.
	 */
	private TileEntityChevron getChevron(int no) {
		return this.getChevron(this.getChevronPosition(no));
	}
	
	/**
	 * Activates the chevron corresponding to the given number.
	 * @param no - the number of the chevron.
	 */
	private void activateChevron(int no) {
		boolean updated = this.changeChevronState(no, StargateMod.block_chevronOn.blockID);
		
		if(updated) {
			this.setCount(no == 7 ? DELAY_BEFORE_KAWOOSH : DELAY_BETWEEN_CHEVRON_ACTIVATIONS);
			this.playSoundEffect(no == 7 ? StargateSounds.stargateMasterChevron : StargateSounds.stargateChevron);
		}
	}
	
	/**
	 * Deactivates the chevron corresponding to the given number.
	 * @param no - the number of the chevron.
	 */
	private void deactivateChevron(int no) {
		this.changeChevronState(no, StargateMod.block_chevronOff.blockID);
	}
	
	/**
	 * Activates/deactivates the chevron corresponding to the given number, depending on the given block id.
	 * @param no - the number of the chevron.
	 * @param blockId - the block id corresponding to a on/off chevron.
	 */
	private boolean changeChevronState(int no, int blockId) {
		ChunkPosition pos = this.getChevronPosition(no);
		
		// If the given number is a valid number for a chevron.
		if(pos != null) {
			int oldBlockId = this.worldObj.getBlockId(pos.x, pos.y, pos.z);
			
			// If the block at the chevron position is a chevron and isn't already of the right type.
			if(blockId != oldBlockId && (Block.blocksList[oldBlockId] instanceof BlockChevron)) {
				// Gets the old chevron and tells it to don't break the gate when detroyed.
				TileEntityChevron chevron = this.getChevron(pos);
				chevron.setActivating(true);
				
				// Replace the old chevron by the new one.
				this.worldObj.setBlock(pos.x, pos.y, pos.z, blockId);
				
				// Gets the new chevron, tells it that he is part of gate and give it a number.
				chevron = this.getChevron(pos);
				chevron.setGate(this.xCoord, this.yCoord, this.zCoord);
				chevron.setNo(no);
				
				StargateMod.debug("Chevron " + no + " updated.", true);
				return true;
			}
			else {
				StargateMod.debug("Chevron " + no + " : no update needed.", true);
			}
		}
		
		return false;
	}
	
	private void proceedKawoosh() {
		// Initialization.
		int x, y, z;
		
		GateKawooshState state = this.getKawooshState();
		int step = state.getValue();
		
		// The gate is oriented on the X or Z axis.
		int metadata = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
		int xAxis = (metadata == 2) ? -1 : (metadata == 3) ? 1 : 0;
		int zAxis = (metadata == 4) ? -1 : (metadata == 5) ? 1 : 0;
		
		// If before the first instable step.
		if(step < GateKawooshState.FIRST_INSTABLE_STEP) {
			// Goes through the pattern, line by line on the Y axis.
			for(int i = 2; i < PATTERN_BLOCKS.length - 2; ++i) {
				int nbAirBlocks = PATTERN_AIR[i];
				
				// Goes through the line (X ou Z axis).
				for(int j = -nbAirBlocks + 1; j < nbAirBlocks; ++j) {
					y = this.yCoord - i + PATTERN_OFFSET;
					x = this.xCoord + (xAxis * j);
					z = this.zCoord + (zAxis * j);
					
					// If the block isn't replaceable by kawoosh, goes to the next block.
					if(!isBlockReplaceableByFlatKawoosh(this.worldObj, x, y, z)) {
						continue;
					}
					
					// Calculates the horizontal distance from the ring.
					int xDistance = nbAirBlocks - Math.abs(j);
					
					// If the block is horizontally too far from the ring.
					if(xDistance > step) {
						int zDistance = 0;
						int iPattern, jPattern;
						
						// Calculates the vertical distance from the ring.
						do {
							zDistance++;
							iPattern = (i < PATTERN_AIR.length / 2) ? (i - zDistance) : (i + zDistance);
							jPattern = Math.abs(j) - PATTERN_AIR[iPattern];
						}
						while(jPattern < 0);
						
						// If the block is vertically too far from the ring, goes to the next block.
						if(zDistance > step) {
							continue;
						}
					}
					
					// Replaces the block by kawoosh.
					this.worldObj.setBlock(x, y, z, StargateMod.block_kawoosh.blockID);
				}
			}
		}
		// If after the first instable step.
		else {
			// If in the first instable step.
			if(step == GateKawooshState.FIRST_INSTABLE_STEP) {
				// Calculates gate center coordinates.
				int yCoord = this.yCoord - (PATTERN_AIR.length / 2) + PATTERN_OFFSET;
				int xCoord = this.xCoord;
				int zCoord = this.zCoord;
				
				boolean canKawooshAppear = false;
				Collection<ChunkPosition> replaceableBlocks = new LinkedList<ChunkPosition>();
				
				for(int i = -MAX_KAWOOSH_WIDTH + 1; i < MAX_KAWOOSH_WIDTH; i++) {
					for(int j = -MAX_KAWOOSH_WIDTH + 1; j < MAX_KAWOOSH_WIDTH; j++) {
						// If the coordinates match the kawoosh pattern.
						if(Math.abs(i) < MAX_KAWOOSH_WIDTH - 1 || Math.abs(j) < MAX_KAWOOSH_WIDTH - 1) {
							// Calculates final coordinates.
							y = yCoord + i;
							x = xCoord + (xAxis * j);
							z = zCoord + (zAxis * j);
							
							// If the block is a kawoosh block, the kawoosh can appear.
							if(this.worldObj.getBlockId(x, y, z) == StargateMod.block_kawoosh.blockID) {
								canKawooshAppear = true;
							}
							// Else, it may be replaced by kawoosh, if at least one block is already a kawoosh block.
							else {
								replaceableBlocks.add(new ChunkPosition(x, y, z));
							}
						}
					}
				}
				
				if(canKawooshAppear) {
					for(ChunkPosition pos : replaceableBlocks) {
						this.worldObj.setBlock(pos.x, pos.y, pos.z, StargateMod.block_kawoosh.blockID);
					}
				}
				else {
					this.createPortal();
					return;
				}
			}
			
			// Calculates gate center coordinates.
			int yCoord = this.yCoord - (PATTERN_AIR.length / 2) + PATTERN_OFFSET;
			int xCoord = this.xCoord + zAxis;
			int zCoord = this.zCoord + xAxis;
			
			for(int i = 0; i < MAX_KAWOOSH_LENGHT; i++) {
				int width = state.getSize(i);
				
				for(int j = -MAX_KAWOOSH_WIDTH + 1; j < MAX_KAWOOSH_WIDTH; j++) {
					for(int k = -MAX_KAWOOSH_WIDTH + 1; k < MAX_KAWOOSH_WIDTH; k++) {
						// Calculates final coordinates.
						y = yCoord + j;
						x = xCoord + (zAxis * i) + (xAxis * k);
						z = zCoord + (xAxis * i) + (zAxis * k);
						
						// If the coordinates match the kawoosh pattern.
						if(Math.abs(j) < width && Math.abs(k) < width && (width == 1 || Math.abs(j) < width - 1 || Math.abs(k) < width - 1)) {
							// If the block is replaceable by kawoosh, replaces it.
							if(isBlockReplaceableByKawoosh(this.worldObj, x, y, z)) {
								this.worldObj.setBlock(x, y, z, StargateMod.block_kawoosh.blockID);
							}
						}
						// If the coordinates don't match the kawoosh pattern.
						else {
							// If the block is a kawoosh block, deletes it.
							if(this.worldObj.getBlockId(x, y, z) == StargateMod.block_kawoosh.blockID) {
								this.worldObj.setBlockToAir(x, y, z);
							}
						}
					}
				}
			}
		}
		
		this.setCount(DELAY_BETWEEN_KAWOOSH_UPDATES);
	}
	
	/**
	 * Returns the orientation corresponding to the metadata of a control unit.
	 * @param metadata - the metadata of a control unit.
	 * @return the orientation corresponding to the metadata.
	 */
	private static int getAngleFromMetadata(int metadata) {
		switch(metadata) {
			case 5:
				return 1;
			case 3:
				return 2;
			case 4:
				return 3;
			case 2:
				return 0;
			default:
				return -1;
		}
	}
	
	/**
	 * Returns the orientation of this gate.
	 * @return the orientation of this gate.
	 */
	private int getThisAngle() {
		return getAngleFromMetadata(this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord));
	}
	
	/**
	 * Returns the orientation of the other gate.
	 * @return the orientation of the other gate.
	 */
	private int getOtherAngle() {
		return getAngleFromMetadata(this.worldObj.getBlockMetadata(this.xDest, this.yDest, this.zDest));
	}
	
	/**
	 * Returns the angle between this gate and the other gate.
	 * @return the angle between this gate and the other gate.
	 */
	private int getRotation() {
		int thisAngle = this.getThisAngle();
		int otherAngle = this.getOtherAngle();
		
		if(thisAngle < 0 || thisAngle > 3 || otherAngle < 0 || otherAngle > 3) {
			return -1;
		}
		
		return (otherAngle - thisAngle + 4) % 4;
	}
	
	/**
	 * Returns the axis corresponding to the given angle.
	 * @param angle - an angle (0, 1, 2, or 3).
	 * @return an axis (X_AXIS/Z_AXIS/ERROR).
	 */
	private static GateOrientation getOrientation(int angle) {
		switch(angle) {
			case 0:
			case 2:
				return GateOrientation.X_AXIS;
			case 1:
			case 3:
				return GateOrientation.Z_AXIS;
			default:
				return GateOrientation.ERROR;
		}
	}
	
	/**
	 * Returns the axis corresponding to this gate orientation.
	 * @return an axis (X_AXIS/Z_AXIS/ERROR).
	 */
	public GateOrientation getGateOrientation() {
		return getOrientation(this.getThisAngle());
	}
	
	/**
	 * Teleports the given entity to the corresponding coordinates of the other gate. <br />
	 * Also rotates the entity according to the angle between the two gates.
	 * @param entity - the entity to teleport.
	 */
	public void teleportEntity(Entity entity) {
		// Checks that the entity doesn't have just been teleported.
		if(!this.teleportedEntities.containsKey(entity.entityId)) {
			// Adds the entity to the list of recently teleported entities.
			this.teleportedEntities.put(entity.entityId, TELEPORT_LOCK_TIME);
			
			// Gets the entity position relative to the control unit of the input gate.
			double xDiff = entity.posX - (this.xCoord + 0.5);
			double yDiff = entity.posY - (this.yCoord + 0.5);
			double zDiff = entity.posZ - (this.zCoord + 0.5);
			
			// Gets the entity velocity.
			double motionX = entity.motionX;
			double motionY = entity.motionY;
			double motionZ = entity.motionZ;
			
			// Gets the orientation of the gate and the angle between this gate and the other gate.
			GateOrientation orientation = getOrientation(this.getThisAngle());
			int rotation = this.getRotation();
			float angleRotation = rotation * 90;
			
			// Performs a rotation on the coordinates of the entity relative to the control unit and on the entity orientation and velocity, according to the angle between the two gates.
			double xTP = xDiff;
			double yTP = yDiff;
			double zTP = zDiff;
			double xMotion = motionX;
			double yMotion = motionY;
			double zMotion = motionZ;
			float rotationYaw = entity.rotationYaw;
			float rotationPitch = entity.rotationPitch;
			
			StargateMod.debug("Input (orientation = " + this.getThisAngle() + ") :", true);
			StargateMod.debug("xDiff = " + xTP + "; yDiff = " + yTP + "; zDiff = " + zTP, true);
			StargateMod.debug("xMotion = " + xMotion + "; yMotion = " + yMotion + "; zMotion = " + zMotion, true);
			StargateMod.debug("rotationYaw = " + entity.rotationYaw + "; rotationPitch = " + entity.rotationPitch, true);
			StargateMod.debug("", true);
			StargateMod.debug("rotation = " + rotation + " (" + angleRotation + ")", true);
			StargateMod.debug("", true);
			
			switch(rotation) {
				case 0:
					switch(orientation) {
						case X_AXIS:
							xTP = -xDiff;
							zTP = zDiff;
							break;
						case Z_AXIS:
							xTP = xDiff;
							zTP = -zDiff;
							break;
						case ERROR:
							return;
					}
					xMotion = -motionX;
					zMotion = -motionZ;
					rotationYaw = (entity.rotationYaw + 180) % 360;
					break;
				case 1:
					switch(orientation) {
						case X_AXIS:
							xTP = -zDiff;
							zTP = -xDiff;
							break;
						case Z_AXIS:
							xTP = zDiff;
							zTP = xDiff;
							break;
						case ERROR:
							return;
					}
					xMotion = motionZ;
					zMotion = -motionX;
					rotationYaw = (entity.rotationYaw + 270) % 360;
					break;
				case 2:
					switch(orientation) {
						case X_AXIS:
							xTP = xDiff;
							zTP = -zDiff;
							break;
						case Z_AXIS:
							xTP = -xDiff;
							zTP = zDiff;
							break;
						case ERROR:
							return;
					}
					xMotion = motionX;
					zMotion = motionZ;
					rotationYaw = entity.rotationYaw;
					break;
				case 3:
					switch(orientation) {
						case X_AXIS:
							xTP = zDiff;
							zTP = xDiff;
							break;
						case Z_AXIS:
							xTP = -zDiff;
							zTP = -xDiff;
							break;
						case ERROR:
							return;
					}
					xMotion = -motionZ;
					zMotion = motionX;
					rotationYaw = (entity.rotationYaw + 90) % 360;
					break;
				default:
					StargateMod.debug("A gate returned a strange orientation value !", true);
					return;
			}
			
			StargateMod.debug("Output (orientation = " + this.getOtherAngle() + ") :", true);
			StargateMod.debug("xDiff = " + xTP + "; yDiff = " + yTP + "; zDiff = " + zTP, true);
			StargateMod.debug("xMotion = " + xMotion + "; yMotion = " + yMotion + "; zMotion = " + zMotion, true);
			StargateMod.debug("rotationYaw = " + rotationYaw + "; rotationPitch = " + rotationPitch + "\n\n", true);
			
			// Calculates the final teleportation coordinates.
			xTP = (this.xDest + 0.5) + xTP;
			yTP = (this.yDest + 0.5) + yTP;
			zTP = (this.zDest + 0.5) + zTP;
			
			// Plays the sound corresponding to an entity entering the vortex.
			this.playSoundEffect(entity, StargateSounds.stargateEnterVortex);
			
			// If the entity is a minecart, the position and the velocity must be adjusted.
			if(entity instanceof EntityMinecart) {
				int otherAngle = this.getOtherAngle();
				int offset = 2;
				
				yTP += 0.125;
				xMotion = 0;
				zMotion = 0;
				
				switch(otherAngle) {
					case 0:
						zTP -= offset;
						break;
					case 1:
						xTP += offset;
						break;
					case 2:
						zTP += offset;
						break;
					case 3:
						xTP -= offset;
						break;
				}
			}
			
			// FIXME - The orientation/velocity of minecarts do not seem to to be correctly controlled...
			//
			//			if(entity instanceof EntityMinecart) {
			//				int otherAngle = this.getOtherAngle();
			//				float defaultSpeed = EntityMinecart.defaultMaxSpeedRail * 0.75F;
			//				int offset = 1;
			//				
			//				yTP += 0.125;
			//				
			//				StargateMod.debug("Minecart ", false);
			//				
			//				switch(otherAngle) {
			//					case 0:
			//						StargateMod.debug("(0) :", true);
			//						zTP -= offset;
			//						rotationYaw = 180;
			//						xMotion = 0;
			//						zMotion = defaultSpeed;
			//						break;
			//					case 1:
			//						StargateMod.debug("(1) :", true);
			//						xTP += offset;
			//						rotationYaw = 270;
			//						xMotion = -defaultSpeed;
			//						zMotion = 0;
			//						break;
			//					case 2:
			//						StargateMod.debug("(2) :", true);
			//						zTP += offset;
			//						rotationYaw = 0;
			//						xMotion = 0;
			//						zMotion = defaultSpeed;
			//						break;
			//					case 3:
			//						StargateMod.debug("(3) :", true);
			//						xTP -= offset;
			//						rotationYaw = 90;
			//						xMotion = defaultSpeed;
			//						zMotion = 0;
			//						break;
			//				}
			//				
			//				StargateMod.debug("xDiff = " + xTP + "; yDiff = " + yTP + "; zDiff = " + zTP, true);
			//				StargateMod.debug("xMotion = " + xMotion + "; yMotion = " + yMotion + "; zMotion = " + zMotion, true);
			//				StargateMod.debug("rotationYaw = " + rotationYaw + "; rotationPitch = " + rotationPitch + "\n\n", true);
			//			}
			
			// Updates the entity velocity.
			entity.motionX = xMotion;
			entity.motionY = yMotion;
			entity.motionZ = zMotion;
			
			StargateMod.sendPacketToAllPlayers(new Packet28EntityVelocity(entity));
			
			// Teleports the entity.
			if(entity instanceof EntityPlayerMP) {
				EntityPlayerMP player = (EntityPlayerMP) entity;
				player.playerNetServerHandler.setPlayerLocation(xTP, yTP, zTP, rotationYaw, rotationPitch);
			}
			else {
				entity.setLocationAndAngles(xTP, yTP, zTP, rotationYaw, rotationPitch);
				StargateMod.sendPacketToAllPlayers(new Packet34EntityTeleport(entity));
			}
			
			// Updates the entity velocity, again.
			StargateMod.sendPacketToAllPlayers(new Packet28EntityVelocity(entity));
			
			// Kills the entity if it is in a wall or a shield.
			if(this.isEntityInsideIris(entity)) {
				if(entity instanceof EntityLivingBase) {
					entity.attackEntityFrom(CustomDamageSource.iris, Integer.MAX_VALUE);
				}
				else {
					entity.setDead();
				}
			}
			
			// Plays the sound corresponding to an entity exiting the vortex.
			this.playSoundEffect(entity, StargateSounds.stargateEnterVortex);
		}
	}
	
	/**
	 * Checks if an entity is inside the iris/shield.
	 * @param entity - the entity.
	 * @return true if the shield is activated or if the entity is in a block with a not null bounding box, else false.
	 */
	private boolean isEntityInsideIris(Entity entity) {
		// Gets the other gate tile entity.
		TileEntityStargateControl otherGate = (TileEntityStargateControl) this.worldObj.getBlockTileEntity(this.xDest, this.yDest, this.zDest);
		
		// If the shield is activated, the entity can't pass.
		if(otherGate.isShieldActivated()) {
			return true;
		}
		
		// Gets the other gate orientation.
		GateOrientation otherGateOrientation = otherGate.getGateOrientation();
		
		// Checks if the entity have to pass through a block to exit the gate.
		for(int i = 0; i < 8; ++i) {
			float f1 = ((i >> 0) % 2 - 0.5F) * entity.width * 0.8F;
			float f2 = ((i >> 1) % 2 - 0.5F) * 0.1F;
			float f3 = ((i >> 2) % 2 - 0.5F) * entity.width * 0.8F;
			
			int x = (otherGateOrientation == GateOrientation.Z_AXIS) ? this.xDest : MathHelper.floor_double(entity.posX + f1);
			int y = MathHelper.floor_double(entity.posY + entity.getEyeHeight() + f2);
			int z = (otherGateOrientation == GateOrientation.X_AXIS) ? this.zDest : MathHelper.floor_double(entity.posZ + f3);
			
			Block block = Block.blocksList[this.worldObj.getBlockId(x, y, z)];
			
			if(block != null && block.getCollisionBoundingBoxFromPool(this.worldObj, x, y, z) != null) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Plays a sound at the center of the gate.
	 * @param sound - the name of the sound.
	 */
	private void playSoundEffect(Sound sound) {
		this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord - 5.5D, this.zCoord + 0.5D, sound.toString(), 1.0F, 1.0F);
	}
	
	/**
	 * Plays a sound at the coordinates of the given entity.
	 * @param entity - the entity where the sound must be played.
	 * @param sound - the name of the sound.
	 */
	private void playSoundEffect(Entity entity, Sound sound) {
		this.worldObj.playSoundAtEntity(entity, sound.toString(), this.worldObj.rand.nextFloat() * 0.2F + 0.8F, this.worldObj.rand.nextFloat() * 0.2F + 0.8F);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		this.state = GateState.valueOf(par1NBTTagCompound.getInteger("state"));
		this.activationType = GateActivationType.valueOf(par1NBTTagCompound.getInteger("activationType"));
		this.activationSequence = GateActivationSequence.valueOf(par1NBTTagCompound.getInteger("activationSequence"));
		this.activationState = GateActivationState.valueOf(par1NBTTagCompound.getInteger("activationState"));
		this.kawooshState = GateKawooshState.valueOf(par1NBTTagCompound.getInteger("kawooshState"));
		this.shieldActivated = par1NBTTagCompound.getBoolean("shieldActivated");
		this.shieldAutomated = par1NBTTagCompound.getBoolean("shieldAutomated");
		this.code = par1NBTTagCompound.getInteger("code");
		this.count = par1NBTTagCompound.getInteger("count");
		this.xDest = par1NBTTagCompound.getInteger("xDest");
		this.yDest = par1NBTTagCompound.getInteger("yDest");
		this.zDest = par1NBTTagCompound.getInteger("zDest");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("state", this.state.getValue());
		par1NBTTagCompound.setInteger("activationType", this.activationType.getValue());
		par1NBTTagCompound.setInteger("activationSequence", this.activationSequence.getValue());
		par1NBTTagCompound.setInteger("activationState", this.activationState.getValue());
		par1NBTTagCompound.setInteger("kawooshState", this.kawooshState.getValue());
		par1NBTTagCompound.setBoolean("shieldActivated", this.shieldActivated);
		par1NBTTagCompound.setBoolean("shieldAutomated", this.shieldAutomated);
		par1NBTTagCompound.setInteger("code", this.code);
		par1NBTTagCompound.setInteger("count", this.count);
		par1NBTTagCompound.setInteger("xDest", this.xDest);
		par1NBTTagCompound.setInteger("yDest", this.yDest);
		par1NBTTagCompound.setInteger("zDest", this.zDest);
	}
	
	@Override
	protected LinkedList<Byte> getEntityData() {
		LinkedList<Byte> list = super.getEntityData();
		
		writeInt(list, this.state.getValue());
		writeInt(list, this.activationType.getValue());
		writeInt(list, this.activationSequence.getValue());
		writeInt(list, this.activationState.getValue());
		writeInt(list, this.kawooshState.getValue());
		writeBoolean(list, this.shieldActivated);
		writeBoolean(list, this.shieldAutomated);
		writeInt(list, this.code);
		writeInt(list, this.count);
		writeInt(list, this.xDest);
		writeInt(list, this.yDest);
		writeInt(list, this.zDest);
		
		return list;
	}
	
	@Override
	protected boolean loadEntityData(LinkedList<Byte> list) {
		if(super.loadEntityData(list)) {
			this.state = GateState.valueOf(readInt(list));
			this.activationType = GateActivationType.valueOf(readInt(list));
			this.activationSequence = GateActivationSequence.valueOf(readInt(list));
			this.activationState = GateActivationState.valueOf(readInt(list));
			this.kawooshState = GateKawooshState.valueOf(readInt(list));
			this.shieldActivated = readBoolean(list);
			this.shieldAutomated = readBoolean(list);
			this.code = readInt(list);
			this.count = readInt(list);
			this.xDest = readInt(list);
			this.yDest = readInt(list);
			this.zDest = readInt(list);
			this.updateBlockTexture();
			return true;
		}
		return false;
	}
	
	@Override
	protected void updateClients() {
		this.onInventoryChanged();
		super.updateClients();
	}
	
}
