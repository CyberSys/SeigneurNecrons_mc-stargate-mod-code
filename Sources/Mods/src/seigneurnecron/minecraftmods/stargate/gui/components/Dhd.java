package seigneurnecron.minecraftmods.stargate.gui.components;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class Dhd {
	
	// Fields :
	
	protected final StringBuilder address = new StringBuilder(9);
	protected final AddressBar addressBar;
	protected final DhdPanel dhdPanel;
	
	// Constructors :
	
	public Dhd(AddressBar addressBar, DhdPanel dhdPanel) {
		this.addressBar = addressBar;
		this.dhdPanel = dhdPanel;
		this.dhdPanel.setDhd(this);
	}
	
	// Getters :
	
	public String getAddress() {
		return this.address.toString();
	}
	
	public int getNbChevrons() {
		return this.address.length();
	}
	
	// Setters :
	
	public void setAddress(String address) {
		this.init();
		this.address.append(address);
		this.updateInterface();
	}
	
	// Methods :
	
	protected void init() {
		this.address.delete(0, this.address.length());
	}
	
	public void reset() {
		this.init();
		this.updateInterface();
	}
	
	public void addSymbol(char symbol) {
		this.address.append(symbol);
		this.updateInterface();
	}
	
	public boolean isSymbolUsed(char symbol) {
		return this.address.toString().indexOf(symbol) >= 0;
	}
	
	protected void updateInterface() {
		this.addressBar.setAddress(this.getAddress());
		this.dhdPanel.update();
	}
	
}
