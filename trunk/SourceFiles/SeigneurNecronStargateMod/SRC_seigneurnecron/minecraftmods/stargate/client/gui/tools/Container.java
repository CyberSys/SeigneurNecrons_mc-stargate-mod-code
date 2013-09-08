package seigneurnecron.minecraftmods.stargate.client.gui.tools;

import net.minecraft.client.gui.FontRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public interface Container extends Component {
	
	public int getXPosInScreen(int xPos);
	
	public int getYPosInScreen(int yPos);
	
	public void drawText(FontRenderer fontRenderer, String text, int x, int y, int color);
	
	public void drawCenteredText(FontRenderer fontRenderer, String text, int x, int y, int color);
	
	public void drawCenteredText(FontRenderer fontRenderer, String text, int y, int color);
	
	public void drawBox(int borderColor);
	
	public void drawBox(int borderColor, int boxColor);
	
	public void drawBox(int borderColor, boolean extendedLikeFields);
	
	public void drawBox(int borderColor, int boxColor, boolean extendedLikeFields);
	
	public void drawBox(int xPos, int yPos, int width, int height, int borderColor);
	
	public void drawBox(int xPos, int yPos, int width, int height, int borderColor, int boxColor);
	
	public void drawBox(int xPos, int yPos, int width, int height, int borderColor, boolean extendedLikeFields);
	
	public void drawBox(int xPos, int yPos, int width, int height, int borderColor, int boxColor, boolean extendedLikeFields);
	
}
