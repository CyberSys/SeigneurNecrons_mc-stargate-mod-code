package seigneurnecron.minecraftmods.stargate.client.gui.tools;

import static seigneurnecron.minecraftmods.stargate.client.gui.tools.Screen.GREEN;
import static seigneurnecron.minecraftmods.stargate.client.gui.tools.Screen.YELLOW;
import net.minecraft.client.gui.FontRenderer;
import seigneurnecron.minecraftmods.stargate.tools.address.GateAddress;
import seigneurnecron.minecraftmods.stargate.tools.enums.Dimension;

/**
 * @author Seigneur Necron
 */
public class AddressBarGateCreation extends AddressBar {
	
	protected final char dimension;
	
	public AddressBarGateCreation(Container parent, FontRenderer fontRenderer, int xPos, int yPos, int width, int dimension) {
		super(parent, fontRenderer, xPos, yPos, width);
		this.dimension = Dimension.valueOf(dimension).getAddress();
	}
	
	@Override
	public void drawScreen() {
		super.drawScreen();
		this.slots[7].drawCenteredText(this.fontRenderer, String.valueOf(this.dimension), 0, YELLOW);
		this.slots[8].drawCenteredText(this.fontRenderer, String.valueOf(GateAddress.SPECIAL_SYMBOL), 0, GREEN);
	}
	
}
