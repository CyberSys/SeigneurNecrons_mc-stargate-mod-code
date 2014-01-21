package seigneurnecron.minecraftmods.stargate.gui;

import static seigneurnecron.minecraftmods.core.gui.GuiConstants.BACKGROUND_COLOR;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.BUTTON_HEIGHT;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.FIELD_HEIGHT;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.LIGHT_BLUE;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.MARGIN;
import static seigneurnecron.minecraftmods.stargate.gui.GuiDhd.DHD_MARGIN;

import java.util.logging.Level;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import seigneurnecron.minecraftmods.core.gui.GuiScreenTileEntity;
import seigneurnecron.minecraftmods.core.gui.Label;
import seigneurnecron.minecraftmods.core.gui.Panel;
import seigneurnecron.minecraftmods.core.mod.ModBase;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.gui.components.AddressBar;
import seigneurnecron.minecraftmods.stargate.gui.components.AddressBarGateCreation;
import seigneurnecron.minecraftmods.stargate.gui.components.DhdGateCreation;
import seigneurnecron.minecraftmods.stargate.gui.components.DhdPanelGateCreation;
import seigneurnecron.minecraftmods.stargate.gui.components.StargateButton;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargateControl;
import seigneurnecron.minecraftmods.stargate.tools.address.GateAddress;
import seigneurnecron.minecraftmods.stargate.tools.address.MalformedCoordinatesException;
import seigneurnecron.minecraftmods.stargate.tools.loadable.BlockCoordinates;

/**
 * @author Seigneur Necron
 */
public class GuiStargateControl extends GuiScreenTileEntity<TileEntityStargateControl> {
	
	// ####################################################################################################
	// Lang constants :
	// ####################################################################################################
	
	public static final String INV_NAME = "container.stargateControl";
	
	public static final String DEFAULT_ADDRESS = INV_NAME + ".defaultAddress";
	public static final String CUSTOM_ADDRESS = INV_NAME + ".customAddress";
	public static final String CREATE_WITH_DEFAULT_ADDRESS = INV_NAME + ".createWithdefaultAddress";
	public static final String CREATE_WITH_CUSTOM_ADDRESS = INV_NAME + ".createWithcustomAddress";
	public static final String ADDRESS_SELECTION = INV_NAME + ".addressSelection";
	
	// ####################################################################################################
	// Interface fields :
	// ####################################################################################################
	
	protected Panel panel_main;
	
	protected Label label_invName;
	protected Label label_defaultAddress;
	protected Label label_customAddress;
	protected Label label_address;
	
	protected StargateButton button_cancel;
	protected StargateButton button_default;
	protected StargateButton button_custom;
	
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
	// Constructors :
	// ####################################################################################################
	
	public GuiStargateControl(TileEntityStargateControl tileEntity) {
		super(tileEntity);
		this.stargateFontRenderer = StargateMod.proxy.getStargateFontRender();
	}
	
	// ####################################################################################################
	// Interface definition :
	// ####################################################################################################
	
	@Override
	protected void drawBackground(int mouseX, int mouseY, float timeSinceLastTick) {
		super.drawBackground(mouseX, mouseY, timeSinceLastTick);
		this.panel_main.drawBox(LIGHT_BLUE, BACKGROUND_COLOR);
	}
	
	@Override
	protected void drawForeground(int mouseX, int mouseY) {
		super.drawForeground(mouseX, mouseY);
		this.addressBar1.drawScreen();
		this.addressBar2.drawScreen();
	}
	
	@Override
	protected void initComponents() {
		super.initComponents();
		
		// Panel sizes :
		
		int panelWidth = this.width / 2;
		int panelHeight = (4 * FIELD_HEIGHT) + (6 * BUTTON_HEIGHT) + (9 * MARGIN) + (2 * DHD_MARGIN);
		
		// Panels :
		
		this.panel_main = new Panel(this, (this.width - panelWidth) / 2, (this.height - panelHeight) / 2, panelWidth, panelHeight);
		
		// Strings :
		
		String string_defaultAddress = I18n.getString(DEFAULT_ADDRESS) + " : ";
		String string_address = I18n.getString(GuiDhd.ADDRESS) + " : ";
		
		// Component sizes :
		
		int stringSize_defaultAddress = this.fontRenderer.getStringWidth(string_defaultAddress);
		int stringSize_address = this.fontRenderer.getStringWidth(string_address);
		int fieldOffset_defaultAddress = stringSize_defaultAddress + MARGIN;
		int fieldOffset_address = stringSize_address + MARGIN;
		int fieldSize_defaultAddress = this.panel_main.getComponentWidth() - (fieldOffset_defaultAddress + MARGIN);
		int fieldSize_address = this.panel_main.getComponentWidth() - (fieldOffset_address + MARGIN);
		int buttonSize = this.panel_main.getComponentWidth() - (2 * MARGIN);
		
		// Fields and buttons :
		
		this.nextYPos = MARGIN;
		this.label_invName = this.addComponent(new Label(this.panel_main, this.fontRenderer, MARGIN, this.nextYPos, buttonSize, I18n.getString(INV_NAME) + " - " + I18n.getString(ADDRESS_SELECTION), true));
		this.label_defaultAddress = this.addComponent(new Label(this.panel_main, this.fontRenderer, MARGIN, this.nextYPos, stringSize_defaultAddress, string_defaultAddress), false);
		this.addressBar1 = this.addComponent(new AddressBar(this.panel_main, this.stargateFontRenderer, fieldOffset_defaultAddress, this.nextYPos, fieldSize_defaultAddress, false));
		this.button_default = this.addComponent(new StargateButton(this.panel_main, MARGIN, this.nextYPos, buttonSize, I18n.getString(CREATE_WITH_DEFAULT_ADDRESS)));
		this.label_customAddress = this.addComponent(new Label(this.panel_main, this.fontRenderer, MARGIN, this.nextYPos, buttonSize, I18n.getString(CUSTOM_ADDRESS) + " : "));
		this.dhdPanel = this.addComponent(new DhdPanelGateCreation(this.panel_main, this.stargateFontRenderer, MARGIN, this.nextYPos, buttonSize));
		this.label_address = this.addComponent(new Label(this.panel_main, this.fontRenderer, MARGIN, this.nextYPos, stringSize_address, string_address), false);
		this.addressBar2 = this.addComponent(new AddressBarGateCreation(this.panel_main, this.stargateFontRenderer, fieldOffset_address, this.nextYPos, fieldSize_address, this.tileEntity.getDimension()));
		this.button_custom = this.addComponent(new StargateButton(this.panel_main, MARGIN, this.nextYPos, buttonSize, I18n.getString(CREATE_WITH_CUSTOM_ADDRESS)));
		this.button_cancel = this.addComponent(new StargateButton(this.panel_main, MARGIN, this.nextYPos, buttonSize, I18n.getString(GUI_CANCEL) + I18n.getString(KEY_ESC)));
		
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
		ModBase.sendPacketToServer(this.tileEntity.getStargateCreatePacket(address));
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
			StargateMod.instance.log("The default address for a gate is'nt valid ! This is not normal.", Level.WARNING);
			StargateMod.instance.log(argh.getMessage(), Level.WARNING);
		}
	}
	
}
