package seigneurnecron.minecraftmods.stargate.network.packetmapping;

import seigneurnecron.minecraftmods.core.network.packetmapping.CommandPacketMapping;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.network.command.RemoteCloseCommand;
import seigneurnecron.minecraftmods.stargate.network.command.ShieldAutomatedCommand;
import seigneurnecron.minecraftmods.stargate.network.command.ShieldCodeCommand;
import seigneurnecron.minecraftmods.stargate.network.command.ShieldCommand;
import seigneurnecron.minecraftmods.stargate.network.command.ShieldRemoteCommand;
import seigneurnecron.minecraftmods.stargate.network.command.StargateCloseCommand;
import seigneurnecron.minecraftmods.stargate.network.command.StargateCreateCommand;
import seigneurnecron.minecraftmods.stargate.network.command.StargateOpenCommand;
import seigneurnecron.minecraftmods.stargate.network.command.TeleportCommand;

/**
 * @author Seigneur Necron
 */
public class StargateCommandPacketMapping extends CommandPacketMapping {
	
	// Command ids :
	
	public final int TELEPORT;
	public final int STARGATE_OPEN;
	public final int STARGATE_CLOSE;
	public final int STARGATE_CREATE;
	public final int SHIELD;
	public final int SHIELD_AUTOMATED;
	public final int SHIELD_CODE;
	public final int SHIELD_REMOTE;
	public final int REMOTE_CLOSE;
	
	// Static instance :
	
	private static StargateCommandPacketMapping instance;
	
	public static StargateCommandPacketMapping getInstance() {
		return instance;
	}
	
	// Constructors :
	
	public StargateCommandPacketMapping() {
		super(StargateMod.CHANEL_COMMANDS);
		instance = this;
		
		this.TELEPORT = this.register(new TeleportCommand());
		this.STARGATE_OPEN = this.register(new StargateOpenCommand());
		this.STARGATE_CLOSE = this.register(new StargateCloseCommand());
		this.STARGATE_CREATE = this.register(new StargateCreateCommand());
		this.SHIELD = this.register(new ShieldCommand());
		this.SHIELD_AUTOMATED = this.register(new ShieldAutomatedCommand());
		this.SHIELD_CODE = this.register(new ShieldCodeCommand());
		this.SHIELD_REMOTE = this.register(new ShieldRemoteCommand());
		this.REMOTE_CLOSE = this.register(new RemoteCloseCommand());
	}
	
}
