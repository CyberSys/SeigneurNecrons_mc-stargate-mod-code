package seigneurnecron.minecraftmods.core.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public interface ComponentContainer extends Component {
	
	/**
	 * Returns the absolute position in the screen corresponding to the given X position relative to the container.
	 * @param xPos - the X position relative to the container.
	 * @return the absolute position in the screen.
	 */
	public int getXPosInScreen(int xPos);
	
	/**
	 * Returns the absolute position in the screen corresponding to the given Y position relative to the container.
	 * @param xPos - the Y position relative to the container.
	 * @return the absolute position in the screen.
	 */
	public int getYPosInScreen(int yPos);
	
	/**
	 * Draws a text at the given position, with the given fontRenderer and color.
	 * @param fontRenderer - the font renderer.
	 * @param text - the text.
	 * @param x - the X position.
	 * @param y - the y position.
	 * @param color - the color.
	 */
	public void drawText(FontRenderer fontRenderer, String text, int x, int y, int color);
	
	/**
	 * Draws a text centered at the given position, with the given fontRenderer and color.
	 * @param fontRenderer - the font renderer.
	 * @param text - the text.
	 * @param x - the X position.
	 * @param y - the y position.
	 * @param color - the color.
	 */
	public void drawCenteredText(FontRenderer fontRenderer, String text, int x, int y, int color);
	
	/**
	 * Draws a text centered at the given Y position, at the center (on X) of this container, with the given fontRenderer and color.
	 * @param fontRenderer - the font renderer.
	 * @param text - the text.
	 * @param y - the y position.
	 * @param color - the color.
	 */
	public void drawCenteredText(FontRenderer fontRenderer, String text, int y, int color);
	
	/**
	 * Draws a border of the given color to this component.
	 * @param borderColor - the color of the border.
	 */
	public void drawBox(int borderColor);
	
	/**
	 * Draws a border and a background of the given colors to this container.
	 * @param borderColor - the color of the border.
	 * @param boxColor - the color of the background.
	 */
	public void drawBox(int borderColor, int boxColor);
	
	/**
	 * Draws a border of the given color to this component, with a size optionnaly exteded by one pixel.
	 * @param borderColor - the color of the border.
	 * @param extendedLikeFields
	 */
	public void drawBox(int borderColor, boolean extendedLikeFields);
	
	/**
	 * Draws a border and a background of the given colors to this container, with a size optionnaly exteded by one pixel.
	 * @param borderColor - the color of the border.
	 * @param boxColor - the color of the background.
	 * @param extendedLikeFields - whether the box must be extended by one pixel.
	 */
	public void drawBox(int borderColor, int boxColor, boolean extendedLikeFields);
	
	/**
	 * Draws a border of the given color, at the given position, with the given size.
	 * @param xPos - the X position of the box.
	 * @param yPos - the Y position of the box.
	 * @param width - the width of the box.
	 * @param height - the height of the box.
	 * @param borderColor - the color of the border.
	 */
	public void drawBox(int xPos, int yPos, int width, int height, int borderColor);
	
	/**
	 * Draws a border and a background of the given colors, at the given position, with the given size.
	 * @param xPos - the X position of the box.
	 * @param yPos - the Y position of the box.
	 * @param width - the width of the box.
	 * @param height - the height of the box.
	 * @param borderColor - the color of the border.
	 * @param boxColor - the color of the background.
	 */
	public void drawBox(int xPos, int yPos, int width, int height, int borderColor, int boxColor);
	
	/**
	 * Draws a border of the given color, at the given position, with the given size, optionnaly exteded by one pixel.
	 * @param xPos - the X position of the box.
	 * @param yPos - the Y position of the box.
	 * @param width - the width of the box.
	 * @param height - the height of the box.
	 * @param borderColor - the color of the border.
	 * @param extendedLikeFields - whether the box must be extended by one pixel.
	 */
	public void drawBox(int xPos, int yPos, int width, int height, int borderColor, boolean extendedLikeFields);
	
	/**
	 * Draws a border and a background of the given colors, at the given position, with the given size, optionnaly exteded by one pixel.
	 * @param xPos - the X position of the box.
	 * @param yPos - the Y position of the box.
	 * @param width - the width of the box.
	 * @param height - the height of the box.
	 * @param borderColor - the color of the border.
	 * @param boxColor - the color of the background.
	 * @param extendedLikeFields - whether the box must be extended by one pixel.
	 */
	public void drawBox(int xPos, int yPos, int width, int height, int borderColor, int boxColor, boolean extendedLikeFields);
	
	/**
	 * Adds a vertex to a Tessellator buffer.
	 * @param tessellator - the tessellator.
	 * @param x - the X position.
	 * @param y - the Y position.
	 * @param z - the Z position.
	 * @param u - the texture U.
	 * @param v - the texture V.
	 */
	public void addVertexWithUV(Tessellator tessellator, double x, double y, double z, double u, double v);
	
	/**
	 * Adds a vertex to a tessellator buffer.
	 * @param tessellator - the tessellator.
	 * @param x - the X position.
	 * @param y - the Y position.
	 * @param z - the Z position.
	 */
	public void addVertex(Tessellator tessellator, double x, double y, double z);
	
	/**
	 * Adds a component to this container and increment the next Y position.
	 * @param component - the component to add to the gui.
	 * @return the added component.
	 */
	public <T extends Component> T addComponent(T component);
	
	/**
	 * Adds a component to this container and optionally increment the next Y position.
	 * @param component - the component to add to the gui.
	 * @param incrementYPos - indicates whether the next Y position have to be incremented.
	 * @return the added component.
	 */
	public <T extends Component> T addComponent(T component, boolean incrementYPos);
	
}
