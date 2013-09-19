package seigneurnecron.minecraftmods.stargate.gui.components;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class Dhd {
	
	protected final StringBuilder address = new StringBuilder(9);
	protected final AddressBar addressBar;
	protected final DhdPanel dhdPanel;
	
	public Dhd(AddressBar addressBar, DhdPanel dhdPanel) {
		this.addressBar = addressBar;
		this.dhdPanel = dhdPanel;
		this.dhdPanel.setDhd(this);
	}
	
	public int getNbChevrons() {
		return this.address.length();
	}
	
	public String getAddress() {
		return this.address.toString();
	}
	
	public void reset() {
		this.init();
		this.updateInterface();
	}
	
	public void addSymbol(char symbol) {
		this.address.append(symbol);
		this.updateInterface();
	}
	
	public void setAddress(String address) {
		this.init();
		this.address.append(address);
		this.updateInterface();
	}
	
	public boolean isSymbolUsed(char symbol) {
		return this.address.toString().indexOf(symbol) >= 0;
	}
	
	protected void init() {
		this.address.delete(0, this.address.length());
	}
	
	protected void updateInterface() {
		this.addressBar.setAddress(this.getAddress());
		this.dhdPanel.update();
	}
	
}
