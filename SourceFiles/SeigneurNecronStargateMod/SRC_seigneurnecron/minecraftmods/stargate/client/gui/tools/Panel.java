package seigneurnecron.minecraftmods.stargate.client.gui.tools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.FontRenderer;

@SideOnly(Side.CLIENT)
public class Panel implements Container {
	
	// Fields :
	
	protected Container parent;
	protected int xPos;
	protected int yPos;
	protected int width;
	protected int height;
	
	// Builders :
	
	public Panel(Container parent, int xPos, int yPos, int width, int height) {
		this.parent = parent;
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = width;
		this.height = height;
	}
	
	// Getters :
	
	public int getxPos() {
		return this.xPos;
	}
	
	public int getyPos() {
		return this.yPos;
	}
	
	@Override
	public int getXPosInScreen(int xPos) {
		return this.parent.getXPosInScreen(xPos + this.xPos);
	}
	
	@Override
	public int getYPosInScreen(int yPos) {
		return this.parent.getYPosInScreen(yPos + this.yPos);
	}
	
	@Override
	public int getWidth() {
		return this.width;
	}
	
	@Override
	public int getHeight() {
		return this.height;
	}
	
	// Methods :
	
	@Override
	public void drawText(FontRenderer fontRenderer, String text, int x, int y, int color) {
		this.parent.drawText(fontRenderer, text, x + this.xPos, y + this.yPos, color);
	}
	
	@Override
	public void drawCenteredText(FontRenderer fontRenderer, String text, int x, int y, int color) {
		this.parent.drawCenteredText(fontRenderer, text, x + this.xPos, y + this.yPos, color);
	}
	
	@Override
	public void drawCenteredText(FontRenderer fontRenderer, String text, int y, int color) {
		this.drawCenteredText(fontRenderer, text, this.width / 2, y, color);
	}
	
	@Override
	public void drawBorder(int color) {
		this.drawBorder(0, 0, this.width, this.height, color);
	}
	
	@Override
	public void drawBorder(int xPos, int yPos, int width, int height, int color) {
		this.parent.drawBorder(xPos + this.xPos, yPos + this.yPos, width, height, color);
	}
	
	public int getBottom() {
		return this.xPos + this.height;
	}
	
	public int getRight() {
		return this.yPos + this.width;
	}
	
}
