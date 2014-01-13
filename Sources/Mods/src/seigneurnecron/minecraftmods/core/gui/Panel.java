package seigneurnecron.minecraftmods.core.gui;

import static seigneurnecron.minecraftmods.core.gui.GuiConstants.TRANSPARENT;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class Panel implements ComponentContainer {
	
	// Fields :
	
	protected final ComponentContainer parent;
	protected final int xPos;
	protected final int yPos;
	protected final int width;
	protected final int height;
	
	// Constructors :
	
	public Panel(ComponentContainer parent, int xPos, int yPos, int width, int height) {
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
	
	public int getXPos() {
		return this.xPos;
	}
	
	public int getYPos() {
		return this.yPos;
	}
	
	public int getRight() {
		return this.xPos + this.width;
	}
	
	public int getBottom() {
		return this.yPos + this.height;
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
	
	@Override
	public void addVertexWithUV(Tessellator tessellator, double x, double y, double z, double u, double v) {
		this.parent.addVertexWithUV(tessellator, x + this.xPos, y + this.yPos, z, u, v);
	}
	
	@Override
	public void addVertex(Tessellator tessellator, double x, double y, double z) {
		this.parent.addVertex(tessellator, x + this.xPos, y + this.yPos, z);
	}
	
	@Override
	public <T extends Component> T addComponent(T component) {
		return this.parent.addComponent(component);
	}
	
	@Override
	public <T extends Component> T addComponent(T component, boolean incrementYPos) {
		return this.parent.addComponent(component, incrementYPos);
	}
	
}
