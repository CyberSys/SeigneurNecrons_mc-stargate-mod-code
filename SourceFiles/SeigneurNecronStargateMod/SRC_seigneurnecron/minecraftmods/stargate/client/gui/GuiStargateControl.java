package seigneurnecron.minecraftmods.stargate.client.gui;

import static seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargateControl.INV_NAME;

import java.util.logging.Level;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.client.gui.tools.AddressBar;
import seigneurnecron.minecraftmods.stargate.client.gui.tools.AddressBarGateCreation;
import seigneurnecron.minecraftmods.stargate.client.gui.tools.Button;
import seigneurnecron.minecraftmods.stargate.client.gui.tools.DhdGateCreation;
import seigneurnecron.minecraftmods.stargate.client.gui.tools.DhdPanelGateCreation;
import seigneurnecron.minecraftmods.stargate.client.gui.tools.Panel;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargateControl;
import seigneurnecron.minecraftmods.stargate.tools.address.GateAddress;
import seigneurnecron.minecraftmods.stargate.tools.address.MalformedCoordinatesException;
import seigneurnecron.minecraftmods.stargate.tools.loadable.BlockCoordinates;

/**
 * @author Seigneur Necron
 */
public class GuiStargateControl extends GuiScreen<TileEntityStargateControl> {
	
	// ####################################################################################################
	// Lang constants :
	// ####################################################################################################
	
	public static final String DEFAULT_ADDRESS = INV_NAME + ".defaultAddress";
	public static final String CUSTOM_ADDRESS = INV_NAME + ".customAddress";
	public static final String CREATE_WITH_DEFAULT_ADDRESS = INV_NAME + ".createWithdefaultAddress";
	public static final String CREATE_WITH_CUSTOM_ADDRESS = INV_NAME + ".createWithcustomAddress";
	public static final String ADDRESS_SELECTION = INV_NAME + ".addressSelection";
	
	// ####################################################################################################
	// Interface fields :
	// ####################################################################################################
	
	protected String string_invName;
	protected String string_defaultAddress;
	protected String string_customAddress;
	protected String string_address;
	
	protected Panel panel_main;
	
	protected Button button_cancel;
	protected Button button_default;
	protected Button button_custom;
	
	protected AddressBar addressBar1;
	protected AddressBarGateCreation addressBar2;
	protected DhdPanelGateCreation dhdPanel;
	protected DhdGateCreation dhd;
	
	protected final FontRenderer stargateFontRenderer;
	
	// ####################################################################################################
	// Data fields :
	// ####################################################################################################
	
	protected String defaultAddress = "";
	
	// ####################################################################################################
	// Builder :
	// ####################################################################################################
	
	public GuiStargateControl(TileEntityStargateControl tileEntity) {
		super(tileEntity);
		this.stargateFontRenderer = StargateMod.proxy.getStargateFontRender();
	}
	
	// ####################################################################################################
	// Interface definition :
	// ####################################################################################################
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
		
		this.panel_main.drawBox(GRAY);
		
		this.nextYPos = MARGIN;
		this.panel_main.drawCenteredText(this.fontRenderer, this.string_invName, this.nextYPos, WHITE);
		this.panel_main.drawText(this.fontRenderer, this.string_defaultAddress, MARGIN, this.nextYPos, WHITE);
		this.nextYPos += BUTTON_HEIGHT + MARGIN;
		this.panel_main.drawText(this.fontRenderer, this.string_customAddress, MARGIN, this.nextYPos, WHITE);
		this.nextYPos += (3 * BUTTON_HEIGHT) + (2 * DHD_MARGIN) + MARGIN;
		this.panel_main.drawText(this.fontRenderer, this.string_address, MARGIN, this.nextYPos, WHITE);
		
		this.addressBar1.drawScreen();
		this.addressBar2.drawScreen();
	}
	
	@Override
	public void initComponents() {
		super.initComponents();
		
		// Panel sizes :
		
		int panelWidth = this.width / 2;
		int panelHeight = (4 * FIELD_HEIGHT) + (6 * BUTTON_HEIGHT) + (9 * MARGIN) + (2 * DHD_MARGIN);
		
		// Panels :
		
		this.panel_main = new Panel(this, (this.width - panelWidth) / 2, (this.height - panelHeight) / 2, panelWidth, panelHeight);
		
		// Strings :
		
		this.string_invName = I18n.func_135053_a(INV_NAME) + " - " + I18n.func_135053_a(ADDRESS_SELECTION);
		this.string_defaultAddress = I18n.func_135053_a(DEFAULT_ADDRESS) + " : ";
		this.string_customAddress = I18n.func_135053_a(CUSTOM_ADDRESS) + " : ";
		this.string_address = I18n.func_135053_a(GuiDhd.ADDRESS) + " : ";
		
		// Component sizes :
		
		int stringSize_defaultAddress = this.fontRenderer.getStringWidth(this.string_defaultAddress);
		int stringSize_address = this.fontRenderer.getStringWidth(this.string_address);
		int fieldOffset_defaultAddress = stringSize_defaultAddress + MARGIN;
		int fieldOffset_address = stringSize_address + MARGIN;
		int fieldSize_defaultAddress = this.panel_main.getComponentWidth() - (fieldOffset_defaultAddress + MARGIN);
		int fieldSize_address = this.panel_main.getComponentWidth() - (fieldOffset_address + MARGIN);
		int buttonSize = this.panel_main.getComponentWidth() - (2 * MARGIN);
		
		// Fields and buttons :
		
		this.nextYPos = FIELD_HEIGHT + (2 * MARGIN);
		this.addressBar1 = this.addComponent(new AddressBar(this.panel_main, this.stargateFontRenderer, fieldOffset_defaultAddress, this.nextYPos, fieldSize_defaultAddress, false));
		this.button_default = this.addComponent(new Button(this.panel_main, this.getNextButtonId(), MARGIN, this.nextYPos, buttonSize, I18n.func_135053_a(CREATE_WITH_DEFAULT_ADDRESS)));
		this.nextYPos += FIELD_HEIGHT + MARGIN;
		this.dhdPanel = this.addComponent(new DhdPanelGateCreation(this, this.panel_main, this.stargateFontRenderer, MARGIN, this.nextYPos, buttonSize));
		this.addressBar2 = this.addComponent(new AddressBarGateCreation(this.panel_main, this.stargateFontRenderer, fieldOffset_address, this.nextYPos, fieldSize_address, this.tileEntity.getDimension()));
		this.button_custom = this.addComponent(new Button(this.panel_main, this.getNextButtonId(), MARGIN, this.nextYPos, buttonSize, I18n.func_135053_a(CREATE_WITH_CUSTOM_ADDRESS)));
		this.button_cancel = this.addComponent(new Button(this.panel_main, this.getNextButtonId(), MARGIN, this.nextYPos, buttonSize, I18n.func_135053_a("gui.cancel")));
		
		// Dhd :
		
		this.dhd = new DhdGateCreation(this.addressBar2, this.dhdPanel);
	}
	
	// ####################################################################################################
	// User input :
	// ####################################################################################################
	
	@Override
	protected void actionPerformed(GuiButton guiButton) {
		if(guiButton.enabled) {
			if(guiButton == this.button_cancel) {
				this.close();
			}
			else if(guiButton == this.button_default) {
				this.createDefault();
			}
			else if(guiButton == this.button_custom) {
				this.createCustom();
			}
			else {
				boolean fieldChanged = this.dhdPanel.actionPerformed(guiButton);
				
				if(fieldChanged) {
					this.onFieldChanged();
				}
			}
		}
	}
	
	protected void create(String address) {
		this.close();
		StargateMod.sendPacketToServer(this.tileEntity.getStargateCreatePacket(address));
	}
	
	protected void createDefault() {
		this.create(this.defaultAddress);
	}
	
	protected void createCustom() {
		this.create(this.dhd.getAddress());
	}
	
	// ####################################################################################################
	// Utility :
	// ####################################################################################################
	
	@Override
	protected void onFieldChanged() {
		this.button_custom.enabled = GateAddress.isValidAddress(this.dhd.getAddress());
	}
	
	@Override
	protected void onGuiInitialized() {
		super.onGuiInitialized();
		this.dhdPanel.update();
		
		try {
			this.defaultAddress = GateAddress.toAddress(new BlockCoordinates(this.tileEntity));
			this.addressBar1.setAddress(this.defaultAddress);
		}
		catch(MalformedCoordinatesException argh) {
			StargateMod.debug("The default address for a gate is'nt valid ! This is not normal.", Level.WARNING, true);
			StargateMod.debug(argh.getMessage(), Level.WARNING, true);
		}
	}
	
}
