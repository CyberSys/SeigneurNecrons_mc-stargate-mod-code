package seigneurnecron.minecraftmods.stargate.client.gui.tools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.FontRenderer;

@SideOnly(Side.CLIENT)
public interface Container {
	
	public int getXPosInScreen(int xPos);
	
	public int getYPosInScreen(int yPos);
	
	public int getWidth();
	
	public int getHeight();
	
    public void drawText(FontRenderer fontRenderer, String text, int x, int y, int color);
	
    public void drawCenteredText(FontRenderer fontRenderer, String text, int x, int y, int color);
	
    public void drawCenteredText(FontRenderer fontRenderer, String text, int y, int color);
	
	public void drawBorder(int color);
	
	public void drawBorder(int xPos, int yPos, int width, int height, int color);
	
}
