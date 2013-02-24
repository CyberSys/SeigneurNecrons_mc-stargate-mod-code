package seigneurnecron.minecraftmods.stargate.client.network;

import seigneurnecron.minecraftmods.stargate.StargateMod;
import net.minecraft.client.audio.SoundManager;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class StargateClientEvents {
	
	@ForgeSubscribe
	public void loadCustomSounds(SoundLoadEvent event) {
		SoundManager manager = event.manager;
		
		for(String file : StargateMod.soundFiles) {
			manager.soundPoolSounds.addSound(file, this.getClass().getResource(StargateMod.soundFolder + file));
		}
	}
	
}
