package mods.necron.stargate;

import net.minecraft.src.SoundManager;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class StargateClientEvents {
	
	@ForgeSubscribe
	public void loadCustomSounds(SoundLoadEvent event) {
		SoundManager manager = event.manager;
		String[] soundFiles = {
				"stargate/chevron.wav",
				"stargate/masterChevron.wav",
				"stargate/gateOpen.wav",
				"stargate/gateClose.wav",
				"stargate/gateFail.wav",
				"stargate/enterVortex.wav",
				"stargate/eventHorizon.wav"};
		
		for(String file : soundFiles) {
			manager.soundPoolSounds.addSound(file, this.getClass().getResource("/mods/necron/" + file));
		}
	}
	
}
