package seigneurnecron.minecraftmods.stargate.sound;

import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;
import seigneurnecron.minecraftmods.core.sound.Sounds;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class StargateSounds extends Sounds {
	
	// Constants :
	
	public final StargateSound stargateChevron = new StargateSound("stargateChevron", true);
	public final StargateSound stargateMasterChevron = new StargateSound("stargateMasterChevron", true);
	public final StargateSound stargateOpen = new StargateSound("stargateOpen");
	public final StargateSound stargateClose = new StargateSound("stargateClose");
	public final StargateSound stargateFail = new StargateSound("stargateFail", true);
	public final StargateSound stargateEnterVortex = new StargateSound("stargateEnterVortex");
	public final StargateSound stargateEventHorizon = new StargateSound("stargateEventHorizon");
	public final StargateSound stargateShieldActivation = new StargateSound("stargateShieldActivation");
	public final StargateSound stargateShieldDeactivation = new StargateSound("stargateShieldDeactivation");
	public final StargateSound stargateShieldHit = new StargateSound("stargateShieldHit");
	public final StargateSound teleportation = new StargateSound("teleportation");
	public final StargateSound button = new StargateSound("button");
	
	// Methods :
	
	@Override
	@ForgeSubscribe
	public void loadCustomSounds(SoundLoadEvent event) {
		super.loadCustomSounds(event);
	}
	
}
