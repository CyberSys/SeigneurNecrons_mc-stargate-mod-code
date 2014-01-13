package seigneurnecron.minecraftmods.core.sound;

import java.lang.reflect.Field;
import java.util.logging.Level;

import net.minecraftforge.client.event.sound.SoundLoadEvent;
import seigneurnecron.minecraftmods.core.SeigneurNecronMod;

/**
 * @author Seigneur Necron
 */
public abstract class Sounds {
	
	// Methods :
	
	public void loadCustomSounds(SoundLoadEvent event) {
		try {
			Field[] fields = this.getClass().getFields();
			
			for(Field field : fields) {
				Object object = field.get(this);
				if(object != null && object instanceof Sound) {
					event.manager.soundPoolSounds.addSound(((Sound) object).getFileName());
				}
			}
		}
		catch(Exception argh) {
			SeigneurNecronMod.instance.log("Error while loading sounds. Sound system may not work.", Level.SEVERE);
			argh.printStackTrace();
		}
	}
	
}
