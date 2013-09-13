package seigneurnecron.minecraftmods.core.gui;

import net.minecraft.client.gui.FontRenderer;

/**
 * @author Seigneur Necron
 */
public class Label extends Panel {
	
	// Fields :
	
	protected final FontRenderer fontRenderer;
	
	protected String text;
	protected int color;
	protected boolean centered;
	
	// Constructors :
	
	public Label(Container parent, FontRenderer fontRenderer, int xPos, int yPos, int width, int height, String text, int color, boolean centered) {
		super(parent, xPos, yPos, width, height);
		this.fontRenderer = fontRenderer;
		this.centered = centered;
		this.setText(text);
		this.setColor(color);
	}
	
	public Label(Container parent, FontRenderer fontRenderer, int xPos, int yPos, int width, int height, String text, boolean centered) {
		this(parent, fontRenderer, xPos, yPos, width, height, text, Screen.WHITE, centered);
	}
	
	public Label(Container parent, FontRenderer fontRenderer, int xPos, int yPos, int width, int height, String text, int color) {
		this(parent, fontRenderer, xPos, yPos, width, height, text, color, false);
	}
	
	public Label(Container parent, FontRenderer fontRenderer, int xPos, int yPos, int width, int height, String text) {
		this(parent, fontRenderer, xPos, yPos, width, height, text, Screen.WHITE, false);
	}
	
	public Label(Container parent, FontRenderer fontRenderer, int xPos, int yPos, int width, String text, int color, boolean centered) {
		this(parent, fontRenderer, xPos, yPos, width, Screen.FIELD_HEIGHT, text, color, centered);
	}
	
	public Label(Container parent, FontRenderer fontRenderer, int xPos, int yPos, int width, String text, boolean centered) {
		this(parent, fontRenderer, xPos, yPos, width, Screen.FIELD_HEIGHT, text, Screen.WHITE, centered);
	}
	
	public Label(Container parent, FontRenderer fontRenderer, int xPos, int yPos, int width, String text, int color) {
		this(parent, fontRenderer, xPos, yPos, width, Screen.FIELD_HEIGHT, text, color, false);
	}
	
	public Label(Container parent, FontRenderer fontRenderer, int xPos, int yPos, int width, String text) {
		this(parent, fontRenderer, xPos, yPos, width, Screen.FIELD_HEIGHT, text, Screen.WHITE, false);
	}
	
	// Setters :
	
	public void setText(String displayedText) {
		this.text = displayedText;
	}
	
	public void setColor(int color) {
		this.color = color;
	}
	
	public void setText(String text, int color) {
		this.setText(text);
		this.setColor(color);
	}
	
	// Display :
	
	public void drawScreen() {
		if(this.centered) {
			this.drawCenteredText(this.fontRenderer, this.text, this.height - this.fontRenderer.FONT_HEIGHT, this.color);
		}
		else {
			this.drawText(this.fontRenderer, this.text, 0, this.height - this.fontRenderer.FONT_HEIGHT, this.color);
		}
	}
}
