package seigneurnecron.minecraftmods.stargate.gui.components;

import static seigneurnecron.minecraftmods.core.gui.GuiConstants.BUTTON_HEIGHT;
import static seigneurnecron.minecraftmods.stargate.gui.GuiDhd.DHD_MARGIN;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import seigneurnecron.minecraftmods.core.gui.ComponentContainer;
import seigneurnecron.minecraftmods.core.gui.Panel;
import seigneurnecron.minecraftmods.stargate.gui.GuiDhd;
import seigneurnecron.minecraftmods.stargate.tools.address.GateAddress;
import seigneurnecron.minecraftmods.stargate.tools.enums.Dimension;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class DhdPanel extends Panel {
	
	// Constants :
	
	public static final String RESET = GuiDhd.INV_NAME + ".reset";
	
	// Fields :
	
	protected final FontRenderer fontRenderer;
	
	protected Dhd dhd;
	protected DhdButton[] xButtons = new DhdButton[GateAddress.X_SYMBOLS.size()];
	protected DhdButton[] zButtons = new DhdButton[GateAddress.X_SYMBOLS.size()];
	protected DhdButton[] yButtons = new DhdButton[GateAddress.Y_SYMBOLS.size()];
	protected DhdButton[] dimButtons = new DhdButton[Dimension.values().length];
	protected DhdButton specialButton;
	protected StargateButton resetButton;
	
	// Constructors :
	
	public DhdPanel(ComponentContainer parent, FontRenderer fontRenderer, int xPos, int yPos, int width, int height) {
		super(parent, xPos, yPos, width, height);
		this.fontRenderer = fontRenderer;
		
		int nbXButtons = this.xButtons.length;
		int nbYButtons = this.yButtons.length;
		int nbDimButtons = this.dimButtons.length;
		
		int y0 = 0;
		int y1 = y0 + BUTTON_HEIGHT + DHD_MARGIN;
		int y2 = y1 + BUTTON_HEIGHT + DHD_MARGIN;
		int dimSlotOffset = nbXButtons - (nbDimButtons + 2);
		int resetButtonOffset = nbYButtons + 1;
		
		int buttonSize = (width - (14 * DHD_MARGIN)) / nbXButtons;
		int resetButtonSize = (3 * buttonSize) + (2 * DHD_MARGIN);
		
		int xOffset = (width - (15 * buttonSize) - (14 * DHD_MARGIN)) / 2;
		
		for(int i = 0; i < nbXButtons; i++) {
			this.xButtons[i] = this.addComponent(new DhdButton(this, this.fontRenderer, xOffset + i * (buttonSize + DHD_MARGIN), y0, buttonSize, GateAddress.X_SYMBOLS.get(i)), false);
			this.zButtons[i] = this.addComponent(new DhdButton(this, this.fontRenderer, xOffset + i * (buttonSize + DHD_MARGIN), y1, buttonSize, GateAddress.Z_SYMBOLS.get(i)), false);
		}
		
		for(int i = 0; i < nbYButtons; i++) {
			this.yButtons[i] = this.addComponent(new DhdButton(this, this.fontRenderer, xOffset + i * (buttonSize + DHD_MARGIN), y2, buttonSize, GateAddress.Y_SYMBOLS.get(i)), false);
		}
		
		for(int i = 0; i < nbDimButtons; i++) {
			this.dimButtons[i] = this.addComponent(new DhdButton(this, this.fontRenderer, xOffset + ((i + dimSlotOffset) * (buttonSize + DHD_MARGIN)), y2, buttonSize, Dimension.values()[i].getAddress()), false);
		}
		
		this.specialButton = this.addComponent(new DhdButton(this, this.fontRenderer, xOffset + ((nbXButtons - 1) * (buttonSize + DHD_MARGIN)), y2, buttonSize, GateAddress.SPECIAL_SYMBOL), false);
		this.resetButton = this.addComponent(new StargateButton(this, xOffset + (resetButtonOffset * (buttonSize + DHD_MARGIN)), y2, resetButtonSize, I18n.getString(RESET)), false);
	}
	
	public DhdPanel(ComponentContainer parent, FontRenderer fontRenderer, int xPos, int yPos, int width) {
		this(parent, fontRenderer, xPos, yPos, width, (3 * BUTTON_HEIGHT) + (2 * DHD_MARGIN));
	}
	
	// Setters :
	
	public void setDhd(Dhd dhd) {
		this.dhd = dhd;
		this.update();
	}
	
	// Methods :
	
	public boolean actionPerformed(GuiButton guiButton) {
		if(guiButton instanceof DhdButton) {
			this.dhd.addSymbol(((DhdButton) guiButton).symbol);
			return true;
		}
		else if(guiButton == this.resetButton) {
			this.dhd.reset();
			return true;
		}
		
		return false;
	}
	
	public void update() {
		int nbChevrons = this.dhd.getNbChevrons();
		
		this.resetButton.enabled = (nbChevrons != 0);
		this.specialButton.enabled = (nbChevrons == 8);
		
		for(DhdButton button : this.xButtons) {
			button.enabled = (nbChevrons < 3) && !this.dhd.isSymbolUsed(button.symbol);
		}
		
		for(DhdButton button : this.zButtons) {
			button.enabled = (nbChevrons < 6 && nbChevrons >= 3) && !this.dhd.isSymbolUsed(button.symbol);
		}
		
		for(DhdButton button : this.yButtons) {
			button.enabled = (nbChevrons == 6);
		}
		
		for(DhdButton button : this.dimButtons) {
			button.enabled = (nbChevrons == 7);
		}
	}
	
}
