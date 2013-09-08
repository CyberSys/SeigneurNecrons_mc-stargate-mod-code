package seigneurnecron.minecraftmods.stargate.client.gui.tools;

import static seigneurnecron.minecraftmods.stargate.client.gui.tools.Screen.TRANSPARENT;
import net.minecraft.client.gui.FontRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class Panel implements Container {
	
	// Fields :
	
	protected final Container parent;
	protected final int xPos;
	protected final int yPos;
	protected final int width;
	protected final int height;
	
	// Constructors :
	
	public Panel(Container parent, int xPos, int yPos, int width, int height) {
		this.parent = parent;
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = width;
		this.height = height;
	}
	
	// Getters :
	
	@Override
	public int getComponentWidth() {
		return this.width;
	}
	
	@Override
	public int getComponentHeight() {
		return this.height;
	}
	
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
	public void drawBox(int borderColor) {
		this.drawBox(0, 0, this.width, this.height, borderColor, TRANSPARENT, false);
	}
	
	@Override
	public void drawBox(int borderColor, int boxColor) {
		this.drawBox(0, 0, this.width, this.height, borderColor, boxColor, false);
	}
	
	@Override
	public void drawBox(int borderColor, boolean extendedLikeFields) {
		this.drawBox(0, 0, this.width, this.height, borderColor, TRANSPARENT, extendedLikeFields);
	}
	
	@Override
	public void drawBox(int borderColor, int boxColor, boolean extendedLikeFields) {
		this.drawBox(0, 0, this.width, this.height, borderColor, boxColor, extendedLikeFields);
	}
	
	@Override
	public void drawBox(int xPos, int yPos, int width, int height, int borderColor) {
		this.drawBox(xPos, yPos, width, height, borderColor, TRANSPARENT, false);
	}
	
	@Override
	public void drawBox(int xPos, int yPos, int width, int height, int borderColor, int boxColor) {
		this.drawBox(xPos, yPos, width, height, borderColor, boxColor, false);
	}
	
	@Override
	public void drawBox(int xPos, int yPos, int width, int height, int borderColor, boolean extendedLikeFields) {
		this.drawBox(xPos, yPos, width, height, borderColor, TRANSPARENT, extendedLikeFields);
	}
	
	@Override
	public void drawBox(int xPos, int yPos, int width, int height, int borderColor, int boxColor, boolean extendedLikeFields) {
		this.parent.drawBox(xPos + this.xPos, yPos + this.yPos, width, height, borderColor, boxColor, extendedLikeFields);
	}
	
	public int getBottom() {
		return this.xPos + this.height;
	}
	
	public int getRight() {
		return this.yPos + this.width;
	}
	
}
