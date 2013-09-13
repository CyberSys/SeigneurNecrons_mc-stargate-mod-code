package seigneurnecron.minecraftmods.stargate.gui.components;

import seigneurnecron.minecraftmods.core.gui.Button;
import seigneurnecron.minecraftmods.core.gui.Container;
import seigneurnecron.minecraftmods.stargate.StargateMod;

/**
 * @author Seigneur Necron
 */
public class StargateButton extends Button {
	
	public StargateButton(Container container, int xPos, int yPos, int width, int height, String text) {
		super(container, xPos, yPos, width, height, text);
	}
	
	public StargateButton(Container container, int xPos, int yPos, int width, String text) {
		super(container, xPos, yPos, width, text);
	}
	
	@Override
	public String getSoundName() {
		return StargateMod.getSounds().button.toString();
	}
	
}
