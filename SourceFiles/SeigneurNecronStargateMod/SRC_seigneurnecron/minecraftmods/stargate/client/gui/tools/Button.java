package seigneurnecron.minecraftmods.stargate.client.gui.tools;

import net.minecraft.client.gui.GuiButton;
import seigneurnecron.minecraftmods.stargate.client.sound.StargateSounds;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class Button extends GuiButton implements Component {
	
	public Button(Container container, int id, int xPos, int yPos, int width, int height, String text) {
		super(id, container.getXPosInScreen(xPos), container.getYPosInScreen(yPos), width, height, text);
	}
	
	public Button(Container container, int id, int xPos, int yPos, int width, String text) {
		this(container, id, xPos, yPos, width, 20, text);
	}
	
	@Override
	public int getComponentWidth() {
		return this.width;
	}
	
	@Override
	public int getComponentHeight() {
		return this.height;
	}
	
	public String getSoundName() {
		return StargateSounds.button.toString();
	}
	
}
