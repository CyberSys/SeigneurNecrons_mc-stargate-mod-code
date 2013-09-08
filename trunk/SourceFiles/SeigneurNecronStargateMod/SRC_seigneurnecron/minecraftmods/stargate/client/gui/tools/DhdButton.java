package seigneurnecron.minecraftmods.stargate.client.gui.tools;

import net.minecraft.client.gui.FontRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class DhdButton extends Button {
	
	protected final FontRenderer fontRenderer;
	public final char symbol;
	
	public DhdButton(Container container, FontRenderer fontRenderer, int id, int xPos, int yPos, int width, char symbol) {
		super(container, id, xPos, yPos, width, String.valueOf(symbol));
		this.fontRenderer = fontRenderer;
		this.symbol = symbol;
	}
	
	@Override
	public void drawCenteredString(FontRenderer fontRenderer, String string, int x, int y, int color) {
		super.drawCenteredString(this.fontRenderer, string, x, y, color);
	}
	
}
