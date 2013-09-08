package seigneurnecron.minecraftmods.stargate.client.gui.tools;

import static seigneurnecron.minecraftmods.stargate.client.gui.tools.Screen.BUTTON_HEIGHT;
import static seigneurnecron.minecraftmods.stargate.client.gui.tools.Screen.DHD_MARGIN;
import static seigneurnecron.minecraftmods.stargate.tileentity.TileEntityBaseDhd.INV_NAME;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import seigneurnecron.minecraftmods.stargate.tools.address.GateAddress;
import seigneurnecron.minecraftmods.stargate.tools.enums.Dimension;

/**
 * @author Seigneur Necron
 */
public class DhdPanel extends Panel {
	
	public static final String RESET = INV_NAME + ".reset";
	
	protected final FontRenderer fontRenderer;
	
	protected Dhd dhd;
	protected DhdButton[] xButtons = new DhdButton[GateAddress.X_SYMBOLS.size()];
	protected DhdButton[] zButtons = new DhdButton[GateAddress.X_SYMBOLS.size()];
	protected DhdButton[] yButtons = new DhdButton[GateAddress.Y_SYMBOLS.size()];
	protected DhdButton[] dimButtons = new DhdButton[Dimension.values().length];
	protected DhdButton specialButton;
	protected Button resetButton;
	
	public DhdPanel(Screen gui, Container parent, FontRenderer fontRenderer, int xPos, int yPos, int width, int height) {
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
		
		for(int i = 0; i < nbXButtons; i++) {
			this.xButtons[i] = gui.addComponent(new DhdButton(this, this.fontRenderer, gui.getNextButtonId(), i * (buttonSize + DHD_MARGIN), y0, buttonSize, GateAddress.X_SYMBOLS.get(i)), false);
			this.zButtons[i] = gui.addComponent(new DhdButton(this, this.fontRenderer, gui.getNextButtonId(), i * (buttonSize + DHD_MARGIN), y1, buttonSize, GateAddress.Z_SYMBOLS.get(i)), false);
		}
		
		for(int i = 0; i < nbYButtons; i++) {
			this.yButtons[i] = gui.addComponent(new DhdButton(this, this.fontRenderer, gui.getNextButtonId(), i * (buttonSize + DHD_MARGIN), y2, buttonSize, GateAddress.Y_SYMBOLS.get(i)), false);
		}
		
		for(int i = 0; i < nbDimButtons; i++) {
			this.dimButtons[i] = gui.addComponent(new DhdButton(this, this.fontRenderer, gui.getNextButtonId(), (i + dimSlotOffset) * (buttonSize + DHD_MARGIN), y2, buttonSize, Dimension.values()[i].getAddress()), false);
		}
		
		this.specialButton = gui.addComponent(new DhdButton(this, this.fontRenderer, gui.getNextButtonId(), (nbXButtons - 1) * (buttonSize + DHD_MARGIN), y2, buttonSize, GateAddress.SPECIAL_SYMBOL), false);
		this.resetButton = gui.addComponent(new Button(this, gui.getNextButtonId(), resetButtonOffset * (buttonSize + DHD_MARGIN), y2, resetButtonSize, I18n.func_135053_a(RESET)), false);
	}
	
	public DhdPanel(Screen gui, Container parent, FontRenderer fontRenderer, int xPos, int yPos, int width) {
		this(gui, parent, fontRenderer, xPos, yPos, width, (3 * BUTTON_HEIGHT) + (2 * DHD_MARGIN));
	}
	
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
	
	public void setDhd(Dhd dhd) {
		this.dhd = dhd;
		this.update();
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
