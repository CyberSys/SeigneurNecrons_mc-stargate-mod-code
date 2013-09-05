package seigneurnecron.minecraftmods.stargate.client.gui.tools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;

@SideOnly(Side.CLIENT)
public class Button extends GuiButton {
	
	public Button(Container container, int id, int xPos, int yPos, int width, int height, String text) {
		super(id, container.getXPosInScreen(xPos), container.getYPosInScreen(yPos), width, height, text);
	}
	
	public Button(Container container, int id, int xPos, int yPos, int width, String text) {
		this(container, id, xPos, yPos, width, 20, text);
	}
	
	public Button(Container container, int id, int xPos, int yPos, String text) {
		this(container, id, xPos, yPos, 200, 20, text);
	}
	
}
