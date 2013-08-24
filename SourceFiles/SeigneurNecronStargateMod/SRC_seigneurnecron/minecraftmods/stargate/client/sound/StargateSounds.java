package seigneurnecron.minecraftmods.stargate.client.sound;

import java.lang.reflect.Field;
import java.util.logging.Level;

import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;
import seigneurnecron.minecraftmods.stargate.StargateMod;

/**
 * @author Seigneur Necron
 */
public class StargateSounds {
	
	public static final Sound stargateChevron = new Sound("stargateChevron", true);
	public static final Sound stargateMasterChevron = new Sound("stargateMasterChevron", true);
	public static final Sound stargateOpen = new Sound("stargateOpen");
	public static final Sound stargateClose = new Sound("stargateClose");
	public static final Sound stargateFail = new Sound("stargateFail", true);
	public static final Sound stargateEnterVortex = new Sound("stargateEnterVortex");
	public static final Sound stargateEventHorizon = new Sound("stargateEventHorizon");
	public static final Sound stargateShieldActivation = new Sound("stargateShieldActivation");
	public static final Sound stargateShieldDeactivation = new Sound("stargateShieldDeactivation");
	
	@ForgeSubscribe
	public void loadCustomSounds(SoundLoadEvent event) {
		try {
			Field[] fields = StargateSounds.class.getFields();
			
			for(Field field : fields) {
				Object object = field.get(null);
				if(object != null && object instanceof Sound) {
					event.manager.soundPoolSounds.addSound(((Sound) object).getFileName());
				}
			}
		}
		catch(Exception argh) {
			StargateMod.debug(argh.getMessage(), Level.WARNING, true);
		}
	}
	
}
