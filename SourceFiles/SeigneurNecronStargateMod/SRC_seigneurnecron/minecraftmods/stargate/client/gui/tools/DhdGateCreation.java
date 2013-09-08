package seigneurnecron.minecraftmods.stargate.client.gui.tools;

import seigneurnecron.minecraftmods.stargate.tools.address.GateAddress;

/**
 * @author Seigneur Necron
 */
public class DhdGateCreation extends Dhd {
	
	protected final char dimension;
	
	public DhdGateCreation(AddressBarGateCreation addressBar, DhdPanelGateCreation dhdPanel) {
		super(addressBar, dhdPanel);
		this.dimension = addressBar.dimension;
	}
	
	@Override
	public String getAddress() {
		String address = this.address.toString();
		
		if(address.length() == 7) {
			address += String.valueOf(this.dimension) + String.valueOf(GateAddress.SPECIAL_SYMBOL);
		}
		
		return address;
	}
	
}
