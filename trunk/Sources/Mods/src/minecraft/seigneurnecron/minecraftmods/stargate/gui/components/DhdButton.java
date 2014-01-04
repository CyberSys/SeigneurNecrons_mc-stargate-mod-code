package seigneurnecron.minecraftmods.stargate.gui.components;

import net.minecraft.client.gui.FontRenderer;
import seigneurnecron.minecraftmods.core.gui.ComponentContainer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class DhdButton extends StargateButton {
	
	protected final FontRenderer fontRenderer;
	public final char symbol;
	
	public DhdButton(ComponentContainer container, FontRenderer fontRenderer, int xPos, int yPos, int width, char symbol) {
		super(container, xPos, yPos, width, String.valueOf(symbol));
		this.fontRenderer = fontRenderer;
		this.symbol = symbol;
	}
	
	@Override
	public void drawCenteredString(FontRenderer fontRenderer, String string, int x, int y, int color) {
		super.drawCenteredString(this.fontRenderer, string, x, y, color);
	}
	
}
