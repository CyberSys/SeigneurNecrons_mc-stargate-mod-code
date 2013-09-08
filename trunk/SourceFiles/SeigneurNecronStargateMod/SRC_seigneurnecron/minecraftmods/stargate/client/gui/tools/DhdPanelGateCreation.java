package seigneurnecron.minecraftmods.stargate.client.gui.tools;

import net.minecraft.client.gui.FontRenderer;

/**
 * @author Seigneur Necron
 */
public class DhdPanelGateCreation extends DhdPanel {
	
	public DhdPanelGateCreation(Screen gui, Container parent, FontRenderer fontRenderer, int xPos, int yPos, int width, int height) {
		super(gui, parent, fontRenderer, xPos, yPos, width, height);
	}
	
	public DhdPanelGateCreation(Screen gui, Container parent, FontRenderer fontRenderer, int xPos, int yPos, int width) {
		super(gui, parent, fontRenderer, xPos, yPos, width);
	}
	
	@Override
	public void update() {
		int nbChevrons = this.dhd.getNbChevrons();
		
		this.resetButton.enabled = (nbChevrons != 0);
		this.specialButton.enabled = false;
		
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
			button.enabled = false;
		}
	}
	
}
