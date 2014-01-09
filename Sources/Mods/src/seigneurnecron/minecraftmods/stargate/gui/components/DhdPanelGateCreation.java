package seigneurnecron.minecraftmods.stargate.gui.components;

import net.minecraft.client.gui.FontRenderer;
import seigneurnecron.minecraftmods.core.gui.ComponentContainer;

/**
 * @author Seigneur Necron
 */
public class DhdPanelGateCreation extends DhdPanel {
	
	// Constructors :
	
	public DhdPanelGateCreation(ComponentContainer parent, FontRenderer fontRenderer, int xPos, int yPos, int width, int height) {
		super(parent, fontRenderer, xPos, yPos, width, height);
	}
	
	public DhdPanelGateCreation(ComponentContainer parent, FontRenderer fontRenderer, int xPos, int yPos, int width) {
		super(parent, fontRenderer, xPos, yPos, width);
	}
	
	// Methods :
	
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
