package seigneurnecron.minecraftmods.stargate.gui.components;

import static seigneurnecron.minecraftmods.core.gui.GuiConstants.GREEN;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.YELLOW;
import net.minecraft.client.gui.FontRenderer;
import seigneurnecron.minecraftmods.core.gui.ComponentContainer;
import seigneurnecron.minecraftmods.stargate.tools.address.GateAddress;
import seigneurnecron.minecraftmods.stargate.tools.enums.Dimension;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class AddressBarGateCreation extends AddressBar {
	
	// Fields :
	
	protected final char dimension;
	
	// Constructors :
	
	public AddressBarGateCreation(ComponentContainer parent, FontRenderer fontRenderer, int xPos, int yPos, int width, int dimension) {
		super(parent, fontRenderer, xPos, yPos, width);
		this.dimension = Dimension.valueOf(dimension).getAddress();
	}
	
	// Methods :
	
	@Override
	public void drawScreen() {
		super.drawScreen();
		
		this.slots[7].drawCenteredText(this.fontRenderer, String.valueOf(this.dimension), this.textOffset, YELLOW);
		this.slots[8].drawCenteredText(this.fontRenderer, String.valueOf(GateAddress.SPECIAL_SYMBOL), this.textOffset, GREEN);
	}
	
}
