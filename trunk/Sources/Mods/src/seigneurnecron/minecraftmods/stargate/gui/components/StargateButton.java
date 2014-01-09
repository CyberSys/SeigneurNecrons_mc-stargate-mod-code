package seigneurnecron.minecraftmods.stargate.gui.components;

import seigneurnecron.minecraftmods.core.gui.Button;
import seigneurnecron.minecraftmods.core.gui.ComponentContainer;
import seigneurnecron.minecraftmods.stargate.StargateMod;

/**
 * @author Seigneur Necron
 */
public class StargateButton extends Button {
	
	// Constructors :
	
	public StargateButton(ComponentContainer container, int xPos, int yPos, int width, int height, String text) {
		super(container, xPos, yPos, width, height, text);
	}
	
	public StargateButton(ComponentContainer container, int xPos, int yPos, int width, String text) {
		super(container, xPos, yPos, width, text);
	}
	
	// Methods :
	
	@Override
	public String getSoundName() {
		return StargateMod.getSounds().button.toString();
	}
	
}
