package seigneurnecron.minecraftmods.stargate.tileentity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.core.mod.ModBase;
import seigneurnecron.minecraftmods.core.sound.Sound;
import seigneurnecron.minecraftmods.core.teleportation.Teleporter;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.block.BlockChevron;
import seigneurnecron.minecraftmods.stargate.block.BlockConsoleBase;
import seigneurnecron.minecraftmods.stargate.block.BlockConsolePanel;
import seigneurnecron.minecraftmods.stargate.block.BlockKawoosh;
import seigneurnecron.minecraftmods.stargate.block.BlockNaquadahAlloy;
import seigneurnecron.minecraftmods.stargate.block.BlockPortal;
import seigneurnecron.minecraftmods.stargate.block.BlockStargateControl;
import seigneurnecron.minecraftmods.stargate.block.BlockStargatePart;
import seigneurnecron.minecraftmods.stargate.entity.damagesource.CustomDamageSource;
import seigneurnecron.minecraftmods.stargate.network.packetmapping.StargateCommandPacketMapping;
import seigneurnecron.minecraftmods.stargate.tileentity.console.Console;
import seigneurnecron.minecraftmods.stargate.tileentity.console.ConsoleStargate;
import seigneurnecron.minecraftmods.stargate.tools.address.GateAddress;
import seigneurnecron.minecraftmods.stargate.tools.enums.Dimension;
import seigneurnecron.minecraftmods.stargate.tools.enums.GateActivationSequence;
import seigneurnecron.minecraftmods.stargate.tools.enums.GateActivationState;
import seigneurnecron.minecraftmods.stargate.tools.enums.GateActivationType;
import seigneurnecron.minecraftmods.stargate.tools.enums.GateKawooshState;
import seigneurnecron.minecraftmods.stargate.tools.enums.GateOrientation;
import seigneurnecron.minecraftmods.stargate.tools.enums.GateState;
import seigneurnecron.minecraftmods.stargate.tools.loadable.StargateCoordinates;
import seigneurnecron.minecraftmods.stargate.tools.worlddata.StargateChunkLoader;
import seigneurnecron.minecraftmods.stargate.tools.worlddata.WorldStargateData;

/**
 * @author Seigneur Necron
 */
public class TileEntityStargateControl extends TileEntityStargate {
	
	// NBTTags names :
	
	private static final String ADDRESS = "address";
	private static final String STATE = "state";
	private static final String ACTIVATION_TYPE = "activationType";
	private static final String ACTIVATION_SEQUENCE = "activationSequence";
	private static final String ACTIVATION_STATE = "activationState";
	private static final String KAWOOSH_STATE = "kawooshState";
	private static final String SHIELD_ACTIVATED = "shieldActivated";
	private static final String SHIELD_AUTOMATED = "shieldAutomated";
	private static final String CODE = "code";
	private static final String COUNT = "count";
	private static final String OTHER_GATE_SHIELD_ACTIVATED = "otherGateShieldActivated";
	private static final String DESTINATION = "destination";
	
	// Constants :
	
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
	 * The gate address.
	 */
	private String address = "";
	
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
	 * Indicates if the other gate shield is activated.
	 */
	private boolean otherGateShieldActivated = false;
	
	/**
	 * The destination (the coordiantes of the gate connected to this one).
	 */
	private StargateCoordinates destination = new StargateCoordinates("", 0, 0, 0, 0);
	
	/**
	 * A map containing the list of recently teleported entities and counters indicating how long ago they were teleported.
	 */
	private HashMap<Integer, Integer> teleportedEntities = new HashMap<Integer, Integer>();
	
	// Getters :
	
	/**
	 * Returns the gate address.
	 * @return the gate address.
	 */
	public String getAddress() {
		return this.address;
	}
	
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
	 * Returns the other gate shield state.
	 * @return true if the other gate shield is activated, else false.
	 */
	public boolean isOtherGateShieldActivated() {
		return this.otherGateShieldActivated;
	}
	
	/**
	 * Returns the destination (the coordiantes of the gate connected to this one).
	 * @return the destination (the coordiantes of the gate connected to this one).
	 */
	public StargateCoordinates getDestination() {
		return this.destination;
	}
	
	// Setters :
	
	private void setAddress(String address) {
		this.address = address;
		this.setChanged();
	}
	
	/**
	 * Sets the gate state and informs clients.
	 * @param state - the new gate state.
	 */
	private void setState(GateState state) {
		if(this.state != state) {
			this.state = state;
			this.setChanged();
			
			if(state == GateState.ACTIVATING) {
				StargateChunkLoader.getInstance().registerGate(this.getStargateCoordinates());
			}
			else if(state == GateState.OFF) {
				this.setKawooshState(GateKawooshState.K0);
				this.setActivationState(GateActivationState.E0);
				StargateChunkLoader.getInstance().unregisterGate(this.getStargateCoordinates());
			}
			else if(state == GateState.BROKEN) {
				this.onShieldConsoleDestroyed();
				WorldStargateData.getInstance().removeElement(this.getStargateCoordinates());
				this.setAddress("");
			}
			
			this.update();
		}
	}
	
	/**
	 * Sets the gate activation type.
	 * @param activationType - the new activation type.
	 */
	private void setActivationType(GateActivationType activationType) {
		this.activationType = activationType;
		this.setChanged();
	}
	
	/**
	 * Sets the gate activation sequence.
	 * @param activationSequence - the new activation sequence.
	 */
	private void setActivationSequence(GateActivationSequence activationSequence) {
		this.activationSequence = activationSequence;
		this.setChanged();
	}
	
	/**
	 * Sets the gate activation state and imforms clients.
	 * @param activationState - the new activation state.
	 */
	private void setActivationState(GateActivationState activationState) {
		this.activationState = activationState;
		this.setChanged();
		
		if(activationState == GateActivationState.E0) {
			for(int i = 1; i <= 9; i++) {
				this.deactivateChevron(i);
			}
		}
		else {
			// If the gate is almost activated and the shield is in automatic mode, activates the shield.
			if(activationState == GateActivationState.E7 && this.activationType != GateActivationType.FAILED && this.shieldAutomated) {
				this.setShieldActivated(true);
			}
			
			this.activateChevron(this.activationState.getValue());
		}
		
		this.update();
	}
	
	/**
	 * Sets the gate kawoosh state.
	 * @param kawooshState - the new gate kawoosh state.
	 */
	private void setKawooshState(GateKawooshState kawooshState) {
		this.kawooshState = kawooshState;
		this.setChanged();
		
		if(this.kawooshState != GateKawooshState.K0) {
			this.proceedKawoosh();
		}
	}
	
	/**
	 * Sets the shield state.
	 * @param shieldActivated - the new shield state.
	 */
	private void setShieldActivated(boolean shieldActivated) {
		if(this.shieldActivated != shieldActivated) {
			this.shieldActivated = shieldActivated;
			this.setChanged();
			
			// Plays the sound corresponding to a shield activation/deactivation.
			this.playSoundEffect(this.shieldActivated ? StargateMod.getSounds().stargateShieldActivation : StargateMod.getSounds().stargateShieldDeactivation);
			
			// Updates the shield.
			this.updateVortex();
			
			// Informs the otherGate of the change.
			if(this.isActivated()) {
				TileEntityStargateControl otherGate = this.getOtherGate();
				
				if(otherGate != null) {
					otherGate.setOtherGateShieldActivated(shieldActivated);
				}
				else {
					StargateMod.instance.log("Error : can't transmit the shield state to the other gate. This is a bug !", Level.SEVERE);
				}
			}
			
			this.update();
		}
	}
	
	/**
	 * Sets the automatic shield activation on or off.
	 * @param shieldAutomated - true if the shield must be automatically activated, else false.
	 */
	private void setShieldAutomated(boolean shieldAutomated) {
		if(shieldAutomated != this.shieldAutomated) {
			this.shieldAutomated = shieldAutomated;
			this.setChanged();
		}
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
		if(code != this.code) {
			this.code = code;
			this.setChanged();
		}
	}
	
	/**
	 * Sets the value of the counter used for activation and deactivation of the gate.
	 * @param count - the new value for the counter.
	 */
	private void setCount(int count) {
		this.count = count;
	}
	
	/**
	 * Updates the other gate shield state.
	 * @param otherGateShieldActivated - true if the other gate shield is activated, else false.
	 */
	private void setOtherGateShieldActivated(boolean otherGateShieldActivated) {
		this.otherGateShieldActivated = otherGateShieldActivated;
		this.setChanged();
		this.update();
	}
	
	/**
	 * Registers the destination.
	 * @param destination - the coordiantes of the gate connected to this one.
	 */
	private void setDestination(StargateCoordinates destination) {
		this.destination = destination;
		this.setChanged();
	}
	
	// Methods :
	
	/**
	 * Returns the world containing the other gate.
	 * @return the world containing the other gate.
	 */
	protected World getOtherWorld() {
		return ModBase.getSideWorldForDimension(this.destination.dim);
	}
	
	/**
	 * Returns the tileEntity of the other gate.
	 * @return the tileEntity of the other gate.
	 */
	protected TileEntityStargateControl getOtherGate() {
		World world = this.getOtherWorld();
		
		if(world != null) {
			TileEntity tileEntity = world.getBlockTileEntity(this.destination.x, this.destination.y, this.destination.z);
			
			if(tileEntity instanceof TileEntityStargateControl) {
				return (TileEntityStargateControl) tileEntity;
			}
		}
		else {
			StargateMod.instance.log("Error : can't get other gate world.", Level.SEVERE);
		}
		
		return null;
	}
	
	/**
	 * Returns a StargateCoordinates object with the address and coordinates of this stargate.
	 * @return a StargateCoordinates object with the address and coordinates of this stargate.
	 */
	private StargateCoordinates getStargateCoordinates() {
		return this.getStargateCoordinates(this.address);
	}
	
	/**
	 * Returns a StargateCoordinates object with the specified address and the coordinates of this stargate.
	 * @param address - a stargate address.
	 * @return a StargateCoordinates object with the specified address and the coordinates of this stargate.
	 */
	private StargateCoordinates getStargateCoordinates(String address) {
		return new StargateCoordinates(address, this.getDimension(), this.xCoord, this.yCoord, this.zCoord);
	}
	
	/**
	 * Checks if a stargate address is valid and available.
	 * @param address - the address.
	 * @return true if the stargate address is valid and available, else false.
	 */
	private boolean isAddressValidAndAvailable(String address) {
		if(!GateAddress.isValidAddressForDimension(address, this.getDimension())) {
			return false;
		}
		
		if(!WorldStargateData.getInstance().isAvailable(this.getStargateCoordinates(address))) {
			StargateMod.instance.log("The address \"" + address + "\" isn't available.");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Registers this gate in the world.
	 * @param address - the address of the gate.
	 */
	private void registerGate(String address) {
		this.setAddress(address);
		this.setState(GateState.OFF);
		WorldStargateData.getInstance().addElement(this.getStargateCoordinates());
	}
	
	/**
	 * Checks if this gate is well registered in the world.
	 * @return true StargateCoordinates registered object if it extists, else null.
	 */
	private StargateCoordinates checkRegistered() {
		return WorldStargateData.getInstance().checkRegistered(this.getStargateCoordinates());
	}
	
	/**
	 * Returns a stargate create command packet. This is used to create a new stargate with the given address.
	 * @param address - the address of the gate.
	 * @return a stargate create command packet.
	 */
	public Packet getStargateCreatePacket(String address) {
		return this.getCommandPacket(StargateCommandPacketMapping.getInstance().STARGATE_CREATE, address);
	}
	
	/**
	 * Checks that the gate can be activated. <br />
	 * The gate state must be OFF.
	 * @return true if the gate can be activated, else false.
	 */
	public boolean isActivable() {
		return this.state == GateState.OFF;
	}
	
	/**
	 * Checks of the gate is activated.
	 * @return true if the gate is activated, else false.
	 */
	public boolean isActivated() {
		return this.state == GateState.INPUT || this.state == GateState.OUTPUT || this.state == GateState.ACTIVATING || this.state == GateState.KAWOOSH;
	}
	
	/**
	 * Checks that the gate is complete/intact. <br />
	 * If there was a gate which has been damaged, it will be deactivated.
	 * @return true if there is a valid gate at this position, else false.
	 */
	public boolean checkPattern() {
		// Initialization.
		StargateMod.instance.log("Checking gate pattern...");
		int x = this.xCoord;
		int y = this.yCoord;
		int z = this.zCoord;
		
		// If the gate is situated too low on the Y axis.
		if(y < PATTERN_BLOCKS.length) {
			// The pattern can't be matched.
			StargateMod.instance.log("The gate is situated too low on the Y axis !");
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
						
						// If the block doesn't match the pattern.
						if(!isBlockIdMatchingPattern(blockId, i, blockIndex)) {
							// The gate is broken.
							StargateMod.instance.log("Error in gate pattern !");
							this.setBroken();
							return false;
						}
						// If the gate is not created yet.
						else if(this.state == GateState.BROKEN) {
							TileEntityStargatePart tileEntity = (TileEntityStargatePart) this.worldObj.getBlockTileEntity(x, y, z);
							// If the block already belongs to another gate.
							if(tileEntity != null && tileEntity.isPartOfGate()) {
								// It can't belong to this gate.
								StargateMod.instance.log("One of the blocks already belongs to another gate !");
								return false;
							}
						}
					}
				}
				else {
					// Else, goes to the other side of the gate.
					j = nbAirBlocks - 1;
				}
			}
		}
		
		// The pattern is checked.
		StargateMod.instance.log("Gate pattern OK.");
		return true;
	}
	
	/**
	 * Creates a stargate, if possible. <br />
	 * Informs each block of the gate that they now belong to a gate, and give numbers to chevrons.
	 * @param address - the address of the gate.
	 * @return true if the creation was successfull, else false.
	 */
	public boolean createGate(String address) {
		// If the gate is not created yet, and the pattern is checked.
		if(this.state == GateState.BROKEN && this.checkPattern() && this.isAddressValidAndAvailable(address)) {
			// Initialization.
			StargateMod.instance.log("Creation of the gate...");
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
							((TileEntityStargatePart) this.worldObj.getBlockTileEntity(x, y, z)).setGate(this.xCoord, this.yCoord, this.zCoord);
						}
					}
					else {
						// Else, goes to the other side of the gate.
						j = nbAirBlocks - 1;
					}
				}
			}
			
			// Gives a number to each chevron.
			for(int i = 1; i <= 9; ++i) {
				TileEntityChevron chevron = this.getChevron(i);
				if(chevron != null) {
					chevron.setNo(i);
				}
			}
			
			// Updates the gate state.
			StargateMod.instance.log("Creation complete.");
			this.registerGate(address);
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
			StargateMod.instance.log("Deactivating the gate...");
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
							if(tileEntity instanceof TileEntityStargatePart) {
								// Breaks the link with the gate.
								((TileEntityStargatePart) tileEntity).breakGate();
							}
						}
					}
					else {
						// Else, goes to the other side of the gate.
						j = nbAirBlocks - 1;
					}
				}
			}
			
			// Updates the gate state.
			StargateMod.instance.log("Deactivation complete.");
			this.setState(GateState.BROKEN);
			
			// Deactivates all stargate consoles connected to that gate.
			this.deactivateStargateConsoles();
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
		StargateMod.instance.log("Building a gate for testing...");
		
		// If the gate is situated too low on the Y axis.
		if(yCoord < PATTERN_BLOCKS.length) {
			// The pattern can't be matched.
			StargateMod.instance.log("The gate is situated too low on the Y axis !");
			return false;
		}
		
		// If the gate orientation is impossible.
		if(side < 2 && side > 5) {
			// The pattern can't be matched.
			StargateMod.instance.log("This gate orientation is impossible !");
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
				}
				else {
					// Else, goes to the other side of the gate.
					j = nbAirBlocks - 1;
				}
			}
		}
		
		// Sets the control unit in the right direction.
		world.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, side, 3);
		
		// The construction is complete.
		StargateMod.instance.log("Construction complete !");
		return true;
	}
	
	protected void deactivateStargateConsoles() {
		final int maxRange = ConsoleStargate.MAX_RANGE;
		
		// Searches in a cube with a side length of MAX_RANGE.
		for(int i = -maxRange; i <= maxRange; ++i) {
			for(int j = -maxRange; j <= maxRange; ++j) {
				for(int k = -maxRange; k <= maxRange; ++k) {
					TileEntity tileEntity = this.worldObj.getBlockTileEntity(this.xCoord + k, this.yCoord + i, this.zCoord + j);
					
					// If the block is a control unit, adds it to the list of stargates that can be linked to the DHD.
					if(tileEntity instanceof TileEntityConsoleBase) {
						TileEntityConsoleBase tileEntityConsole = (TileEntityConsoleBase) tileEntity;
						Console console = tileEntityConsole.getConsole();
						
						if(console instanceof ConsoleStargate) {
							ConsoleStargate consoleStargate = (ConsoleStargate) console;
							
							if(consoleStargate.isLinkedToGate() && (consoleStargate.getXGate() == this.xCoord) && (consoleStargate.getYGate() == this.yCoord) && (consoleStargate.getZGate() == this.zCoord)) {
								consoleStargate.onStargateDestroyed(this);
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * This method is called when the DHD is activated. Closes the gate if possible. <br />
	 * The activation process can only be canceled from the input side.
	 */
	public void onDhdCloseCommand() {
		if(this.state == GateState.INPUT || this.state == GateState.OUTPUT || (this.state == GateState.ACTIVATING && this.activationType != GateActivationType.OUTPUT)) {
			this.close();
		}
	}
	
	/**
	 * This method is called when the DHD is activated. Opens the gate if possible.
	 * @param address - the address of the gate to contact.
	 */
	public void onDhdOpenCommand(String address) {
		if(this.state == GateState.OFF) {
			this.activate(address);
		}
	}
	
	/**
	 * This method is called when the shield console is activated. Activates/deactivates the shield if possible.
	 * @param activate - true if the shield must be activated, false if it must be deactivated.
	 */
	public void onShieldConsoleActivated(boolean activate) {
		if(activate != this.shieldActivated) {
			if(this.shieldActivated) {
				this.setShieldActivated(false);
			}
			else if(this.state == GateState.INPUT || this.state == GateState.OUTPUT || this.state == GateState.ACTIVATING || this.state == GateState.OFF) {
				this.setShieldActivated(true);
			}
		}
	}
	
	/**
	 * Changes the automated shield state.
	 * @param shieldAutomated - indicates whether the shield must be automatically activated.
	 */
	public void changeShieldAutomated(boolean shieldAutomated) {
		this.setShieldAutomated(shieldAutomated);
		this.update();
	}
	
	/**
	 * Changes the code which deactivates the shield.
	 * @param code - the code which deactivates the shield.
	 */
	public void changeShieldCode(int code) {
		this.setCode(code);
		this.update();
	}
	
	/**
	 * This method is called when the shield console is destroyed. Deactivates the shield, and the automatic activation of the shield.
	 */
	public void onShieldConsoleDestroyed() {
		this.setShieldAutomated(false);
		this.setShieldActivated(false);
		this.update();
	}
	
	/**
	 * This method is called when a shield remote is used. It deactivates the shield if the code is correct.
	 * @param code - the code sent by the shield remote.
	 */
	public boolean onShieldRemoteUsed(int code) {
		TileEntityStargateControl otherGate = this.getOtherGate();
		
		if(otherGate != null && otherGate.isShieldActivated() && code == otherGate.getCode()) {
			otherGate.setShieldActivated(false);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Returns a shield remote command packet. This is used to try deactivating the shield of the other stargate using the given code.
	 * @param code - the code.
	 * @return a shield remote command packet.
	 */
	public Packet getShieldRemotePacket(int code) {
		return this.getCommandPacket(StargateCommandPacketMapping.getInstance().SHIELD_REMOTE, code);
	}
	
	/**
	 * Returns a remote close command packet. This is used to close the gate with the shield remote.
	 * @return a remote close command packet.
	 */
	public Packet getRemoteClosePacket() {
		return this.getCommandPacket(StargateCommandPacketMapping.getInstance().REMOTE_CLOSE);
	}
	
	/**
	 * Activates the gate, creating a vortex to the other gate at the given address, if possible.
	 * @param address - the address of the destination.
	 */
	public void activate(String address) {
		// If the input gate can't be activated, exits.
		if(!this.isActivable()) {
			StargateMod.instance.log("The gate couldn't be activated !");
			return;
		}
		
		// Checks the gate is registered in the world.
		StargateCoordinates thisCoordinates = this.checkRegistered();
		
		if(thisCoordinates == null) {
			StargateMod.instance.log("The gate isn't register ! This is not normal. Can't activate.", Level.WARNING);
			this.setBroken();
			return;
		}
		
		// Chooses an activation sequence.
		GateActivationSequence activationSequence;
		
		if(address.length() == 9) {
			activationSequence = GateActivationSequence.S9;
		}
		else if(address.length() == 8 && Dimension.byAddress(address.charAt(7)).getValue() != this.getDimension()) {
			activationSequence = GateActivationSequence.S8;
		}
		else {
			activationSequence = GateActivationSequence.S7;
		}
		
		// Translates the address.
		StargateCoordinates destination = WorldStargateData.getInstance().getCoordinates(address);
		
		// If there is no gate registered with that adress, launches a false activation.
		if(destination == null) {
			StargateMod.instance.log("No gate registered with the address \"" + address + "\". Wrong number, try again ! XD");
			this.setActivating(GateActivationType.FAILED, activationSequence);
			return;
		}
		
		// Registers the destination.
		this.setDestination(destination);
		
		// If the given coordinates are those of that gate, launches a false activation.
		if(destination.dim == this.getDimension() && destination.x == this.xCoord && destination.y == this.yCoord && destination.z == this.zCoord) {
			StargateMod.instance.log("The input gate can't be the output gate ! >.<");
			this.setActivating(GateActivationType.FAILED, activationSequence);
			return;
		}
		
		// Gets the tile entity of the other gate control unit.
		TileEntityStargateControl otherGate = this.getOtherGate();
		
		// If the output gate can't be activated, launches a false activation.
		if(otherGate == null || !otherGate.isActivable()) {
			StargateMod.instance.log("The gate you are trying to call is currently busy, please try again later... XD");
			this.setActivating(GateActivationType.FAILED, activationSequence);
			return;
		}
		
		// Registers the destination in the otherGate.
		otherGate.setDestination(thisCoordinates);
		
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
		if(this.isActivated()) {
			TileEntityStargateControl otherGate = this.getOtherGate();
			
			// Deactivates this gate.
			this.deletePortal();
			
			// Deactivate the other gate, if it exists.
			if(otherGate != null) {
				otherGate.deletePortal();
			}
			else if(this.activationType != GateActivationType.FAILED) {
				StargateMod.instance.log("Error : can't deactivate the other gate ! This is a bug !", Level.SEVERE);
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
		
		StargateMod.instance.log("Vortex created.");
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
			this.playSoundEffect(StargateMod.getSounds().stargateFail);
		}
		// If the kawoosh wasn't finished.
		else if(oldState == GateState.KAWOOSH) {
			// Delete remaining kawoosh.
			this.deleteKawoosh();
		}
		// Else, delete vortex.
		else {
			this.updateVortex();
			
			StargateMod.instance.log("Vortex deleted.");
			
			// Clears the list of recently teleported entities.
			this.teleportedEntities.clear();
			
			// Plays the sound corresponding to a closed gate.
			this.playSoundEffect(StargateMod.getSounds().stargateClose);
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
		
		StargateMod.instance.log("Kawoosh deleted.");
	}
	
	/**
	 * This method is called each tick. It is used to update the state of the gate during activation and to deactivate the gate when maximum activation duration is reached.
	 */
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		// If server side.
		if(!this.worldObj.isRemote) {
			// Updates counter.
			if(this.count > 0) {
				--this.count;
			}
			
			// Depending on the state of the gate...
			switch(this.state) {
				case INPUT:
					// Updates the list of recently teleported entities.
					this.updateTeleportedEntityList();
					
					// If maximum activation duration was reached, closes the gate.
					if(this.count <= 0) {
						this.close();
						break;
					}
					
					// No "break", this is normal.
				case OUTPUT:
					// Update vortex and shield.
					if(this.count % VORTEX_UPDATE_PERIOD == 0) {
						this.updateVortex();
					}
					break;
				case OFF:
					// Update shield.
					if(this.count == 0) {
						if(this.isShieldActivated()) {
							if(this.shieldWaitingDeativation) {
								this.setShieldWaitingDeativation(false);
								this.setShieldActivated(false);
							}
							
							this.updateVortex();
							this.setCount(VORTEX_UPDATE_PERIOD);
						}
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
									this.playSoundEffect(StargateMod.getSounds().stargateFail);
									// Updates the gate state and exits.
									this.setState(GateState.OFF);
								}
								else {
									// Plays the sound corresponding to a successfull activation.
									this.playSoundEffect(StargateMod.getSounds().stargateOpen);
									
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
		return block == null || !(block instanceof BlockKawoosh || block instanceof BlockStargatePart || block instanceof BlockStargateControl || block instanceof BlockConsoleBase || block instanceof BlockConsolePanel);
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
			this.playSoundEffect(no == 7 ? StargateMod.getSounds().stargateMasterChevron : StargateMod.getSounds().stargateChevron);
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
				
				return true;
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
		World otherWorld = this.getOtherWorld();
		
		if(otherWorld != null) {
			return getAngleFromMetadata(otherWorld.getBlockMetadata(this.destination.x, this.destination.y, this.destination.z));
		}
		
		return -1;
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
			
			StargateMod.instance.log("Input (orientation = " + this.getThisAngle() + ") :");
			StargateMod.instance.log("xDiff = " + xTP + "; yDiff = " + yTP + "; zDiff = " + zTP);
			StargateMod.instance.log("xMotion = " + xMotion + "; yMotion = " + yMotion + "; zMotion = " + zMotion);
			StargateMod.instance.log("rotationYaw = " + entity.rotationYaw + "; rotationPitch = " + entity.rotationPitch);
			StargateMod.instance.log("");
			StargateMod.instance.log("rotation = " + rotation + " (" + angleRotation + ")");
			StargateMod.instance.log("");
			
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
					StargateMod.instance.log("A gate returned a strange orientation value !", Level.SEVERE);
					return;
			}
			
			StargateMod.instance.log("Output (orientation = " + this.getOtherAngle() + ") :");
			StargateMod.instance.log("xDiff = " + xTP + "; yDiff = " + yTP + "; zDiff = " + zTP);
			StargateMod.instance.log("xMotion = " + xMotion + "; yMotion = " + yMotion + "; zMotion = " + zMotion);
			StargateMod.instance.log("rotationYaw = " + rotationYaw + "; rotationPitch = " + rotationPitch);
			
			// Calculates the final teleportation coordinates.
			xTP = (this.destination.x + 0.5) + xTP;
			yTP = (this.destination.y + 0.5) + yTP;
			zTP = (this.destination.z + 0.5) + zTP;
			
			// Plays the sound corresponding to an entity entering the vortex.
			this.playSoundEffect(StargateMod.getSounds().stargateEnterVortex, entity);
			
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
			
			// FIXME - The orientation/velocity of minecarts do not seem to to be correctly controlable...
			//
			//			if(entity instanceof EntityMinecart) {
			//				int otherAngle = this.getOtherAngle();
			//				float defaultSpeed = EntityMinecart.defaultMaxSpeedRail * 0.75F;
			//				int offset = 1;
			//				
			//				yTP += 0.125;
			//				
			//				StargateMod.instance.debug("Minecart ", false);
			//				
			//				switch(otherAngle) {
			//					case 0:
			//						StargateMod.instance.debug("(0) :", true);
			//						zTP -= offset;
			//						rotationYaw = 180;
			//						xMotion = 0;
			//						zMotion = defaultSpeed;
			//						break;
			//					case 1:
			//						StargateMod.instance.debug("(1) :", true);
			//						xTP += offset;
			//						rotationYaw = 270;
			//						xMotion = -defaultSpeed;
			//						zMotion = 0;
			//						break;
			//					case 2:
			//						StargateMod.instance.debug("(2) :", true);
			//						zTP += offset;
			//						rotationYaw = 0;
			//						xMotion = 0;
			//						zMotion = defaultSpeed;
			//						break;
			//					case 3:
			//						StargateMod.instance.debug("(3) :", true);
			//						xTP -= offset;
			//						rotationYaw = 90;
			//						xMotion = defaultSpeed;
			//						zMotion = 0;
			//						break;
			//				}
			//				
			//				StargateMod.instance.log("");
			//				StargateMod.instance.log("xDiff = " + xTP + "; yDiff = " + yTP + "; zDiff = " + zTP);
			//				StargateMod.instance.log("xMotion = " + xMotion + "; yMotion = " + yMotion + "; zMotion = " + zMotion);
			//				StargateMod.instance.log("rotationYaw = " + rotationYaw + "; rotationPitch = " + rotationPitch);
			//			}
			
			entity = Teleporter.teleportEntity(entity, this.destination.dim, xTP, yTP, zTP, rotationYaw, rotationPitch, xMotion, yMotion, zMotion);
			
			if(entity == null) {
				return;
			}
			
			// Kills the entity if it is in a wall or a shield.
			if(this.isEntityInsideIris(entity)) {
				if(entity instanceof EntityLivingBase) {
					entity.attackEntityFrom(CustomDamageSource.IRIS, Integer.MAX_VALUE);
				}
				else {
					entity.setDead();
				}
				
				// Plays the sound corresponding to an entity crashing on the shield.
				this.playSoundEffect(StargateMod.getSounds().stargateShieldHit, entity);
			}
			else {
				// Plays the sound corresponding to an entity exiting the vortex.
				this.playSoundEffect(StargateMod.getSounds().stargateEnterVortex, entity);
			}
		}
	}
	
	/**
	 * Checks if an entity is inside the iris/shield.
	 * @param entity - the entity.
	 * @return true if the shield is activated or if the entity is in a block with a not null bounding box, else false.
	 */
	private boolean isEntityInsideIris(Entity entity) {
		// Gets the other gate tile entity.
		TileEntityStargateControl otherGate = this.getOtherGate();
		
		if(otherGate == null) {
			StargateMod.instance.log("Error can't get the other gate ! This is a bug !", Level.SEVERE);
			return false;
		}
		
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
			
			int x = (otherGateOrientation == GateOrientation.Z_AXIS) ? this.destination.x : MathHelper.floor_double(entity.posX + f1);
			int y = MathHelper.floor_double(entity.posY + entity.getEyeHeight() + f2);
			int z = (otherGateOrientation == GateOrientation.X_AXIS) ? this.destination.z : MathHelper.floor_double(entity.posZ + f3);
			
			Block block = Block.blocksList[otherGate.worldObj.getBlockId(x, y, z)];
			
			if(block != null && block.getCollisionBoundingBoxFromPool(otherGate.worldObj, x, y, z) != null) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Plays a sound at the center of the gate.
	 * @param sound - the sound.
	 */
	@Override
	public void playSoundEffect(Sound sound) {
		super.playSoundEffect(sound, this.xCoord + 0.5D, this.yCoord - 5.5D, this.zCoord + 0.5D);
	}
	
	@Override
	public void playSoundEffect(Sound sound, Entity entity) {
		this.playSoundEffect(sound, entity, this.worldObj.rand.nextFloat() * 0.2F + 0.8F, this.worldObj.rand.nextFloat() * 0.2F + 0.8F);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		this.address = compound.getString(ADDRESS);
		this.state = GateState.valueOf(compound.getInteger(STATE));
		this.activationType = GateActivationType.valueOf(compound.getInteger(ACTIVATION_TYPE));
		this.activationSequence = GateActivationSequence.valueOf(compound.getInteger(ACTIVATION_SEQUENCE));
		this.activationState = GateActivationState.valueOf(compound.getInteger(ACTIVATION_STATE));
		this.kawooshState = GateKawooshState.valueOf(compound.getInteger(KAWOOSH_STATE));
		this.shieldActivated = compound.getBoolean(SHIELD_ACTIVATED);
		this.shieldAutomated = compound.getBoolean(SHIELD_AUTOMATED);
		this.code = compound.getInteger(CODE);
		this.count = compound.getInteger(COUNT);
		this.otherGateShieldActivated = compound.getBoolean(OTHER_GATE_SHIELD_ACTIVATED);
		
		this.destination = new StargateCoordinates(compound.getCompoundTag(DESTINATION));
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		compound.setString(ADDRESS, this.address);
		compound.setInteger(STATE, this.state.getValue());
		compound.setInteger(ACTIVATION_TYPE, this.activationType.getValue());
		compound.setInteger(ACTIVATION_SEQUENCE, this.activationSequence.getValue());
		compound.setInteger(ACTIVATION_STATE, this.activationState.getValue());
		compound.setInteger(KAWOOSH_STATE, this.kawooshState.getValue());
		compound.setBoolean(SHIELD_ACTIVATED, this.shieldActivated);
		compound.setBoolean(SHIELD_AUTOMATED, this.shieldAutomated);
		compound.setInteger(CODE, this.code);
		compound.setInteger(COUNT, this.count);
		compound.setBoolean(OTHER_GATE_SHIELD_ACTIVATED, this.otherGateShieldActivated);
		
		compound.setCompoundTag(DESTINATION, this.destination.getCompound());
	}
	
}
