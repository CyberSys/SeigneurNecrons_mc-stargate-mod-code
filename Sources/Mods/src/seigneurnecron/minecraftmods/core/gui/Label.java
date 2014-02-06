package seigneurnecron.minecraftmods.core.gui;

import static seigneurnecron.minecraftmods.core.gui.GuiConstants.FIELD_HEIGHT;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.WHITE;
import net.minecraft.client.gui.FontRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class Label extends Panel {
	
	// Fields :
	
	protected final FontRenderer fontRenderer;
	
	protected String text;
	protected int color;
	protected boolean centered;
	
	// Constructors :
	
	public Label(ComponentContainer parent, FontRenderer fontRenderer, int xPos, int yPos, int width, int height, String text, int color, boolean centered) {
		super(parent, xPos, yPos, width, height);
		this.fontRenderer = fontRenderer;
		this.centered = centered;
		this.setText(text);
		this.setColor(color);
	}
	
	public Label(ComponentContainer parent, FontRenderer fontRenderer, int xPos, int yPos, int width, int height, String text, boolean centered) {
		this(parent, fontRenderer, xPos, yPos, width, height, text, WHITE, centered);
	}
	
	public Label(ComponentContainer parent, FontRenderer fontRenderer, int xPos, int yPos, int width, int height, String text, int color) {
		this(parent, fontRenderer, xPos, yPos, width, height, text, color, false);
	}
	
	public Label(ComponentContainer parent, FontRenderer fontRenderer, int xPos, int yPos, int width, int height, String text) {
		this(parent, fontRenderer, xPos, yPos, width, height, text, WHITE, false);
	}
	
	public Label(ComponentContainer parent, FontRenderer fontRenderer, int xPos, int yPos, int width, String text, int color, boolean centered) {
		this(parent, fontRenderer, xPos, yPos, width, FIELD_HEIGHT, text, color, centered);
	}
	
	public Label(ComponentContainer parent, FontRenderer fontRenderer, int xPos, int yPos, int width, String text, boolean centered) {
		this(parent, fontRenderer, xPos, yPos, width, FIELD_HEIGHT, text, WHITE, centered);
	}
	
	public Label(ComponentContainer parent, FontRenderer fontRenderer, int xPos, int yPos, int width, String text, int color) {
		this(parent, fontRenderer, xPos, yPos, width, FIELD_HEIGHT, text, color, false);
	}
	
	public Label(ComponentContainer parent, FontRenderer fontRenderer, int xPos, int yPos, int width, String text) {
		this(parent, fontRenderer, xPos, yPos, width, FIELD_HEIGHT, text, WHITE, false);
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
	
	// Methods :
	
	public void drawScreen() {
		if(this.centered) {
			this.drawCenteredText(this.fontRenderer, this.text, (this.height - this.fontRenderer.FONT_HEIGHT) / 2 + 1, this.color);
		}
		else {
			this.drawText(this.fontRenderer, this.text, 0, (this.height - this.fontRenderer.FONT_HEIGHT) / 2 + 1, this.color);
		}
	}
}
