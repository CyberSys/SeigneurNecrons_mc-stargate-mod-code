package seigneurnecron.minecraftmods.stargate.gui.components;

import static seigneurnecron.minecraftmods.core.gui.GuiConstants.BLACK;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.BLUE;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.FIELD_HEIGHT;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.GRAY;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.GREEN;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.YELLOW;
import net.minecraft.client.gui.FontRenderer;
import seigneurnecron.minecraftmods.core.gui.ComponentContainer;
import seigneurnecron.minecraftmods.core.gui.Panel;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class AddressBar extends Panel {
	
	public static final int ADDRESS_MARGIN = 3;
	
	protected final FontRenderer fontRenderer;
	protected final int textOffset;
	protected final Panel[] slots;
	protected char[] symboles = {};
	
	public AddressBar(ComponentContainer parent, FontRenderer fontRenderer, int xPos, int yPos, int width) {
		this(parent, fontRenderer, xPos, yPos, width, true);
	}
	
	public AddressBar(ComponentContainer parent, FontRenderer fontRenderer, int xPos, int yPos, int width, boolean displaySpecialSymbol) {
		super(parent, xPos, yPos, width, FIELD_HEIGHT);
		this.fontRenderer = fontRenderer;
		this.textOffset = FIELD_HEIGHT - this.fontRenderer.FONT_HEIGHT;
		
		this.slots = new Panel[displaySpecialSymbol ? 9 : 8];
		
		int size = (this.width - ((this.slots.length - 1) * ADDRESS_MARGIN)) / this.slots.length;
		int bonus = this.width - ((this.slots.length * size) + ((this.slots.length - 1) * ADDRESS_MARGIN));
		
		for(int i = 0; i < this.slots.length; i++) {
			int x = i * (size + ADDRESS_MARGIN);
			
			if(i > 6) {
				x += bonus;
			}
			
			this.slots[i] = new Panel(this, x, 0, size, this.height);
		}
	}
	
	public void drawScreen() {
		for(int i = 0; i < this.slots.length; i++) {
			this.slots[i].drawBox(GRAY, BLACK, true);
			
			if(i < this.symboles.length) {
				this.slots[i].drawCenteredText(this.fontRenderer, String.valueOf(this.symboles[i]), this.textOffset, (i < 7) ? BLUE : (i == 7) ? YELLOW : GREEN);
			}
		}
	}
	
	public void setAddress(String address) {
		this.symboles = address.toCharArray();
	}
	
}
