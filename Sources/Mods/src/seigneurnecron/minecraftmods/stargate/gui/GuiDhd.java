package seigneurnecron.minecraftmods.stargate.gui;

import static seigneurnecron.minecraftmods.core.gui.GuiConstants.BACKGROUND_COLOR;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.BUTTON_HEIGHT;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.FIELD_HEIGHT;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.GREEN;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.LIGHT_BLUE;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.MARGIN;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.PANEL_MARGIN;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.YELLOW;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import seigneurnecron.minecraftmods.core.gui.Label;
import seigneurnecron.minecraftmods.core.gui.ListProviderSelectTwoLines;
import seigneurnecron.minecraftmods.core.gui.Panel;
import seigneurnecron.minecraftmods.core.gui.TextField;
import seigneurnecron.minecraftmods.core.mod.ModBase;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.gui.components.AddressBar;
import seigneurnecron.minecraftmods.stargate.gui.components.Dhd;
import seigneurnecron.minecraftmods.stargate.gui.components.DhdPanel;
import seigneurnecron.minecraftmods.stargate.gui.components.SelectionListStargate;
import seigneurnecron.minecraftmods.stargate.gui.components.StargateButton;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityConsoleBase;
import seigneurnecron.minecraftmods.stargate.tileentity.console.ConsoleStargateDhd;
import seigneurnecron.minecraftmods.stargate.tools.address.GateAddress;
import seigneurnecron.minecraftmods.stargate.tools.address.MalformedGateAddressException;
import seigneurnecron.minecraftmods.stargate.tools.enums.Dimension;
import seigneurnecron.minecraftmods.stargate.tools.loadable.Stargate;
import seigneurnecron.minecraftmods.stargate.tools.loadable.StargateZoneCoordinates;
import seigneurnecron.minecraftmods.stargate.tools.playerdata.PlayerStargateData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class GuiDhd extends GuiStargateConsole<ConsoleStargateDhd> implements ListProviderSelectTwoLines<Stargate> {
	
	// ####################################################################################################
	// Lang constants :
	// ####################################################################################################
	
	public static final String INV_NAME = "container.dhd";
	
	public static final String ADDRESS = INV_NAME + ".address";
	public static final String ACTIVATE = INV_NAME + ".activate";
	public static final String CLOSE = INV_NAME + ".close";
	public static final String EARTH = INV_NAME + ".earth";
	public static final String HELL = INV_NAME + ".hell";
	public static final String END = INV_NAME + ".end";
	
	// ####################################################################################################
	// Gui constants :
	// ####################################################################################################
	
	public static final int DHD_MARGIN = 1;
	
	// ####################################################################################################
	// Interface fields :
	// ####################################################################################################
	
	protected int offset;
	
	protected String string_activate;
	protected String string_close;
	protected String string_tab;
	protected String string_earth;
	protected String string_hell;
	protected String string_end;
	protected String string_all;
	
	protected Panel panel_list;
	protected Panel panel_controls;
	protected Panel panel_listSelect;
	protected Panel panel_listButtons;
	protected Panel panel_information;
	protected Panel panel_main;
	
	protected Label label_invName;
	protected Label label_address1;
	protected Label label_name1;
	protected Label label_address2;
	protected Label label_name2;
	protected Label label_tab;
	
	protected TextField field_name1;
	protected TextField field_name2;
	
	protected StargateButton button_cancel;
	protected StargateButton button_activate;
	protected StargateButton button_addThis;
	protected StargateButton button_earth;
	protected StargateButton button_hell;
	protected StargateButton button_end;
	protected StargateButton button_all;
	protected StargateButton button_add;
	protected StargateButton button_delete;
	protected StargateButton button_overwrite;
	
	protected SelectionListStargate selectionList;
	
	protected AddressBar addressBar1;
	protected AddressBar addressBar2;
	protected DhdPanel dhdPanel;
	protected Dhd dhd;
	
	protected final FontRenderer stargateFontRenderer;
	
	// ####################################################################################################
	// Data fields :
	// ####################################################################################################
	
	protected Dimension selectedDimension;
	
	protected PlayerStargateData playerData;
	protected List<Stargate> stargates = new LinkedList<Stargate>();
	protected Stargate selectedStargate = null;
	
	// ####################################################################################################
	// Constructors :
	// ####################################################################################################
	
	public GuiDhd(TileEntityConsoleBase tileEntity, EntityPlayer player, ConsoleStargateDhd console) {
		super(tileEntity, player, console);
		this.selectedDimension = this.getDimension();
		this.stargateFontRenderer = StargateMod.proxy.getStargateFontRender();
		this.playerData = PlayerStargateData.get(player);
		this.updateList();
	}
	
	// ####################################################################################################
	// Interface definition :
	// ####################################################################################################
	
	@Override
	protected void updateComponents() {
		super.updateComponents();
		this.updateActivateButton();
	}
	
	@Override
	protected void drawBackground(int mouseX, int mouseY, float timeSinceLastTick) {
		super.drawBackground(mouseX, mouseY, timeSinceLastTick);
		this.panel_listSelect.drawBox(LIGHT_BLUE, BACKGROUND_COLOR);
		this.panel_listButtons.drawBox(LIGHT_BLUE, BACKGROUND_COLOR);
		this.panel_information.drawBox(LIGHT_BLUE, BACKGROUND_COLOR);
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
		
		int panelWidth = (this.width - (3 * PANEL_MARGIN)) / 2;
		int panelHeight = this.height - (2 * PANEL_MARGIN);
		int panelHeight_information = (5 * MARGIN) + (3 * FIELD_HEIGHT) + BUTTON_HEIGHT;
		int panelHeight_listButtons = (4 * MARGIN) + FIELD_HEIGHT + (2 * BUTTON_HEIGHT);
		
		// Panels :
		
		this.panel_list = new Panel(this, PANEL_MARGIN, PANEL_MARGIN, panelWidth, panelHeight);
		this.panel_controls = new Panel(this, this.panel_list.getRight() + PANEL_MARGIN, PANEL_MARGIN, panelWidth, panelHeight);
		
		this.panel_listButtons = new Panel(this.panel_list, 0, this.panel_list.getComponentHeight() - panelHeight_listButtons, this.panel_list.getComponentWidth(), panelHeight_listButtons);
		this.panel_listSelect = new Panel(this.panel_list, 0, 0, this.panel_list.getComponentWidth(), this.panel_list.getComponentHeight() - panelHeight_listButtons - PANEL_MARGIN);
		
		this.panel_information = new Panel(this.panel_controls, 0, 0, this.panel_controls.getComponentWidth(), panelHeight_information);
		this.panel_main = new Panel(this.panel_controls, 0, this.panel_information.getBottom() + PANEL_MARGIN, this.panel_controls.getComponentWidth(), this.panel_controls.getComponentHeight() - this.panel_information.getBottom() - PANEL_MARGIN);
		
		// Strings :
		
		String string_name = I18n.getString(NAME) + " : ";
		String string_address = I18n.getString(ADDRESS) + " : ";
		
		this.string_activate = I18n.getString(ACTIVATE) + I18n.getString(KEY_ENTER);
		this.string_close = I18n.getString(CLOSE) + I18n.getString(KEY_ENTER);
		this.string_tab = I18n.getString(TAB);
		this.string_earth = I18n.getString(EARTH);
		this.string_hell = I18n.getString(HELL);
		this.string_end = I18n.getString(END);
		this.string_all = I18n.getString(ALL);
		
		// Component sizes :
		
		this.offset = this.panel_main.getComponentHeight() - ((5 * BUTTON_HEIGHT) + (2 * FIELD_HEIGHT) + (6 * MARGIN) + (2 * DHD_MARGIN));
		
		if(this.offset < 0) {
			this.offset = 0;
		}
		
		int stringSize = Math.max(this.fontRenderer.getStringWidth(string_name), this.fontRenderer.getStringWidth(string_address));
		int fieldOffset = stringSize + MARGIN;
		int fieldSize = this.panel_main.getComponentWidth() - (fieldOffset + MARGIN);
		
		int buttonSize_main = this.panel_main.getComponentWidth() - (2 * MARGIN);
		int buttonSize_listButtons = this.panel_listButtons.getComponentWidth() - (2 * MARGIN);
		int buttonSize_listButtons_1 = (this.panel_listButtons.getComponentWidth() - (5 * MARGIN)) / 4;
		int buttonSize_listButtons_2 = (this.panel_listButtons.getComponentWidth() - (4 * MARGIN)) / 3;
		
		int listMargin = 2;
		int listWidth = this.panel_listSelect.getComponentWidth() - (2 * listMargin);
		int listHeight = this.panel_listSelect.getComponentHeight() - (2 * listMargin);
		
		// Fields and buttons :
		
		this.nextYPos = MARGIN;
		this.label_invName = this.addComponent(new Label(this.panel_information, this.fontRenderer, MARGIN, this.nextYPos, buttonSize_main, I18n.getString(INV_NAME), true));
		this.label_address1 = this.addComponent(new Label(this.panel_information, this.fontRenderer, MARGIN, this.nextYPos, buttonSize_main, string_address), false);
		this.addressBar1 = this.addComponent(new AddressBar(this.panel_information, this.stargateFontRenderer, fieldOffset, this.nextYPos, fieldSize));
		this.label_name1 = this.addComponent(new Label(this.panel_information, this.fontRenderer, MARGIN, this.nextYPos, buttonSize_main, string_name), false);
		this.field_name1 = this.addComponent(new TextField(this.panel_information, this.fontRenderer, fieldOffset, this.nextYPos, fieldSize));
		this.button_addThis = this.addComponent(new StargateButton(this.panel_information, MARGIN, this.nextYPos, buttonSize_main, I18n.getString(ADD_THIS)));
		
		this.nextYPos = MARGIN;
		this.dhdPanel = this.addComponent(new DhdPanel(this.panel_main, this.stargateFontRenderer, MARGIN, this.nextYPos, buttonSize_main, (3 * BUTTON_HEIGHT) + (2 * DHD_MARGIN) + this.offset));
		this.label_address2 = this.addComponent(new Label(this.panel_main, this.fontRenderer, MARGIN, this.nextYPos, buttonSize_main, string_address), false);
		this.addressBar2 = this.addComponent(new AddressBar(this.panel_main, this.stargateFontRenderer, fieldOffset, this.nextYPos, fieldSize));
		this.label_name2 = this.addComponent(new Label(this.panel_main, this.fontRenderer, MARGIN, this.nextYPos, buttonSize_main, string_name), false);
		this.field_name2 = this.addComponent(new TextField(this.panel_main, this.fontRenderer, fieldOffset, this.nextYPos, fieldSize));
		this.button_activate = this.addComponent(new StargateButton(this.panel_main, MARGIN, this.nextYPos, buttonSize_main, I18n.getString(this.string_activate)));
		this.button_cancel = this.addComponent(new StargateButton(this.panel_main, MARGIN, this.nextYPos, buttonSize_main, I18n.getString(GUI_CANCEL) + I18n.getString(KEY_ESC)));
		
		this.nextYPos = MARGIN;
		this.label_tab = this.addComponent(new Label(this.panel_listButtons, this.fontRenderer, MARGIN, this.nextYPos, buttonSize_listButtons, "", true));
		this.button_earth = this.addComponent(new StargateButton(this.panel_listButtons, MARGIN, this.nextYPos, buttonSize_listButtons_1, this.string_earth), false);
		this.button_hell = this.addComponent(new StargateButton(this.panel_listButtons, buttonSize_listButtons_1 + (2 * MARGIN), this.nextYPos, buttonSize_listButtons_1, this.string_hell), false);
		this.button_end = this.addComponent(new StargateButton(this.panel_listButtons, (2 * buttonSize_listButtons_1) + (3 * MARGIN), this.nextYPos, buttonSize_listButtons_1, this.string_end), false);
		this.button_all = this.addComponent(new StargateButton(this.panel_listButtons, (3 * buttonSize_listButtons_1) + (4 * MARGIN), this.nextYPos, buttonSize_listButtons_1, this.string_all));
		this.button_delete = this.addComponent(new StargateButton(this.panel_listButtons, MARGIN, this.nextYPos, buttonSize_listButtons_2, I18n.getString(DELETE)), false);
		this.button_overwrite = this.addComponent(new StargateButton(this.panel_listButtons, buttonSize_listButtons_2 + (2 * MARGIN), this.nextYPos, buttonSize_listButtons_2, I18n.getString(OVERWRITE)), false);
		this.button_add = this.addComponent(new StargateButton(this.panel_listButtons, (2 * buttonSize_listButtons_2) + (3 * MARGIN), this.nextYPos, buttonSize_listButtons_2, I18n.getString(ADD)));
		
		// List :
		
		this.selectionList = this.addComponent(new SelectionListStargate(this.panel_listSelect, listMargin, listMargin, listWidth, listHeight, this.mc, this, this.mc.thePlayer));
		
		// Dhd :
		
		this.dhd = new Dhd(this.addressBar2, this.dhdPanel);
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
			else if(guiButton == this.button_activate) {
				this.activate();
			}
			else if(guiButton == this.button_addThis) {
				this.addThis();
			}
			else if(guiButton == this.button_earth) {
				this.switchTab(Dimension.EARTH);
			}
			else if(guiButton == this.button_hell) {
				this.switchTab(Dimension.HELL);
			}
			else if(guiButton == this.button_end) {
				this.switchTab(Dimension.END);
			}
			else if(guiButton == this.button_all) {
				this.switchTab(null);
			}
			else if(guiButton == this.button_add) {
				this.add();
			}
			else if(guiButton == this.button_delete) {
				this.remove();
			}
			else if(guiButton == this.button_overwrite) {
				this.overwite();
			}
			else {
				boolean fieldChanged = this.dhdPanel.actionPerformed(guiButton);
				
				if(fieldChanged) {
					this.onFieldChanged();
				}
			}
		}
	}
	
	@Override
	protected void onEnterPressed() {
		this.activate();
	}
	
	protected void activate() {
		if(this.button_activate.enabled && this.stargateConnected) {
			if(this.stargate.isActivated()) {
				ModBase.sendPacketToServer(this.console.getStargateClosePacket());
			}
			else {
				Stargate stargate = this.getStargateFromFields();
				
				if(stargate != null) {
					this.close();
					ModBase.sendPacketToServer(this.console.getStargateOpenPacket(stargate.address));
				}
			}
		}
	}
	
	protected void addThis() {
		Stargate stargate = this.getThisStargate();
		
		if(stargate != null) {
			stargate.name = this.field_name1.getText();
			this.playerData.addElementAndSync(stargate);
			this.updateList();
		}
	}
	
	protected void switchTab(Dimension dimension) {
		this.selectedDimension = dimension;
		this.updateList();
		this.updateTabs();
	}
	
	protected void add() {
		Stargate stargate = this.getStargateFromFields();
		
		if(stargate != null) {
			this.playerData.addElementAndSync(stargate);
			this.setSelectedStargate(stargate);
			this.updateList();
		}
	}
	
	protected void remove() {
		if(this.selectedStargate != null) {
			this.playerData.removeElementAndSync(this.selectedStargate);
			this.setSelectedStargate(null);
			this.updateList();
		}
	}
	
	protected void overwite() {
		Stargate stargate = this.getStargateFromFields();
		
		if(stargate != null && this.selectedStargate != null) {
			stargate.code = this.selectedStargate.code;
			this.playerData.overwriteElementAndSync(this.selectedStargate, stargate);
			this.setSelectedStargate(stargate);
			this.updateList();
		}
	}
	
	// ####################################################################################################
	// Utility :
	// ####################################################################################################
	
	protected Dimension getDimension() {
		return Dimension.valueOf(this.tileEntity.getDimension());
	}
	
	protected Stargate getThisStargate() {
		if(this.stargateConnected) {
			String address = this.stargate.getAddress();
			int code = this.stargate.getCode();
			return new Stargate(address, "", code);
		}
		
		return null;
	}
	
	protected Stargate getStargateFromFields() {
		String address = this.dhd.getAddress();
		
		if(address.length() == 7) {
			address += this.getDimension().getAddress();
		}
		
		if(GateAddress.isValidAddress(address)) {
			String name = this.field_name2.getText();
			return new Stargate(address, name, 0);
		}
		
		return null;
	}
	
	private boolean isStargateInRange(Stargate stargate) {
		if(this.selectedDimension == null) {
			return true;
		}
		
		if(!(this.stargateConnected && this.stargate.getAddress().equals(stargate.address))) {
			try {
				StargateZoneCoordinates coords = GateAddress.toCoordinates(stargate.address);
				return coords.dim == this.selectedDimension.getValue();
			}
			catch(MalformedGateAddressException argh) {
				return false;
			}
		}
		
		return false;
	}
	
	protected void updateList() {
		List<Stargate> stargatesInDimension = new LinkedList<Stargate>();
		
		for(Stargate stargate : this.playerData.getDataList()) {
			if(this.isStargateInRange(stargate)) {
				stargatesInDimension.add(stargate);
			}
		}
		
		this.stargates = stargatesInDimension;
	}
	
	@Override
	protected void onFieldChanged() {
		this.updateInterface();
	}
	
	protected void updateInterface() {
		Stargate stargate = this.getStargateFromFields();
		boolean canSave = stargate != null && GateAddress.isValidAddress(stargate.address);
		boolean stargateSelected = this.selectedStargate != null;
		
		this.button_add.enabled = canSave;
		this.button_overwrite.enabled = canSave && stargateSelected;
		this.button_delete.enabled = stargateSelected;
	}
	
	@Override
	protected void updateStargateInterface() {
		Stargate thisStargate = this.getThisStargate();
		
		String stargateName = "";
		String stargateAddress = "";
		if(thisStargate != null) {
			stargateAddress = thisStargate.address;
			int index = this.playerData.getDataList().indexOf(thisStargate);
			
			if(index >= 0) {
				stargateName = this.playerData.getDataList().get(index).name;
			}
		}
		
		this.field_name1.setText(stargateName);
		this.addressBar1.setAddress(stargateAddress);
		this.button_addThis.enabled = thisStargate != null;
	}
	
	protected void updateActivateButton() {
		Stargate stargate = this.getStargateFromFields();
		boolean canSave = stargate != null && GateAddress.isValidAddress(stargate.address);
		boolean canActivate = canSave && this.stargateConnected && this.stargate.isActivable();
		boolean canClose = this.stargateConnected && this.stargate.isActivated();
		
		this.button_activate.displayString = canClose ? this.string_close : this.string_activate;
		this.button_activate.enabled = (canActivate || canClose);
	}
	
	protected void updateTabs() {
		this.button_earth.enabled = this.selectedDimension != Dimension.EARTH;
		this.button_hell.enabled = this.selectedDimension != Dimension.HELL;
		this.button_end.enabled = this.selectedDimension != Dimension.END;
		this.button_all.enabled = this.selectedDimension != null;
		
		String selectedTab = (this.selectedDimension == Dimension.EARTH) ? this.string_earth : (this.selectedDimension == Dimension.HELL) ? this.string_hell : (this.selectedDimension == Dimension.END) ? this.string_end : this.string_all;
		int selectedTabColor = (this.selectedDimension == null) ? YELLOW : GREEN;
		
		this.label_tab.setText(this.string_tab + " : " + selectedTab, selectedTabColor);
	}
	
	@Override
	protected void onGuiInitialized() {
		super.onGuiInitialized();
		this.updateTabs();
		this.updateList();
		this.dhdPanel.update();
	}
	
	// ####################################################################################################
	// ListProvider interface :
	// ####################################################################################################
	
	@Override
	public List<Stargate> getList() {
		return this.stargates;
	}
	
	@Override
	public int getSelectedIndex() {
		return this.stargates.indexOf(this.selectedStargate);
	}
	
	@Override
	public void setSelectedIndex(int index) {
		if(index >= 0 && index < this.stargates.size()) {
			this.setSelectedStargate(this.stargates.get(index));
		}
		else {
			this.setSelectedStargate(null);
		}
	}
	
	protected void setSelectedStargate(Stargate stargate) {
		this.selectedStargate = stargate;
		
		if(stargate != null) {
			String address = stargate.address;
			
			if(address.length() == 8 && address.charAt(address.length() - 1) == this.getDimension().getAddress()) {
				address = address.substring(0, 7);
			}
			
			this.dhd.setAddress(address);
			this.field_name2.setText(stargate.name);
		}
		else {
			this.dhd.setAddress("");
			this.field_name2.setText("");
		}
		
		this.onFieldChanged();
	}
	
	@Override
	public void onElementDoubleClicked() {
		this.activate();
	}
	
	@Override
	public FontRenderer getFirstFontRenderer() {
		return this.fontRenderer;
	}
	
	@Override
	public FontRenderer getSecondFontRenderer() {
		return this.stargateFontRenderer;
	}
	
}
