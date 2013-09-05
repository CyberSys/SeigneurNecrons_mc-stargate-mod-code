package seigneurnecron.minecraftmods.stargate.client.gui;

import static seigneurnecron.minecraftmods.stargate.tileentity.TileEntityBaseDhd.INV_NAME;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.client.gui.tools.Button;
import seigneurnecron.minecraftmods.stargate.client.gui.tools.ListProviderGui;
import seigneurnecron.minecraftmods.stargate.client.gui.tools.Panel;
import seigneurnecron.minecraftmods.stargate.client.gui.tools.StargateSelectionList;
import seigneurnecron.minecraftmods.stargate.client.gui.tools.TextField;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityBaseDhd;
import seigneurnecron.minecraftmods.stargate.tools.address.GateAddress;
import seigneurnecron.minecraftmods.stargate.tools.address.MalformedGateAddressException;
import seigneurnecron.minecraftmods.stargate.tools.enums.Dimension;
import seigneurnecron.minecraftmods.stargate.tools.loadable.Stargate;
import seigneurnecron.minecraftmods.stargate.tools.loadable.StargateZoneCoordinates;
import seigneurnecron.minecraftmods.stargate.tools.playerData.PlayerStargateData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class GuiDhd extends GuiStargateConsole<TileEntityBaseDhd> implements ListProviderGui<Stargate> {
	
	// ####################################################################################################
	// Lang constants :
	// ####################################################################################################
	
	public static final String ADDRESS = INV_NAME + ".address";
	public static final String ACTIVATE = INV_NAME + ".activate";
	public static final String EARTH = INV_NAME + ".earth";
	public static final String HELL = INV_NAME + ".hell";
	public static final String END = INV_NAME + ".end";
	
	// ####################################################################################################
	// Interface fields :
	// ####################################################################################################
	
	protected int offset;
	
	protected String string_invName;
	protected String string_address;
	protected String string_name;
	protected String string_destination;
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
	
	protected TextField field_name1;
	protected TextField field_name2;
	
	protected Button button_cancel;
	protected Button button_activate;
	protected Button button_addThis;
	protected Button button_earth;
	protected Button button_hell;
	protected Button button_end;
	protected Button button_all;
	protected Button button_add;
	protected Button button_delete;
	protected Button button_overwrite;
	
	protected StargateSelectionList selectionList;
	
	// ####################################################################################################
	// Data fields :
	// ####################################################################################################
	
	protected Dimension selectedDimension = null;
	
	protected List<Stargate> stargates = new LinkedList<Stargate>();
	protected Stargate selectedStargate = null;
	
	protected PlayerStargateData playerData;
	
	// ####################################################################################################
	// Builder :
	// ####################################################################################################
	
	public GuiDhd(TileEntityBaseDhd tileEntity, EntityPlayer player) {
		super(tileEntity, player);
		this.playerData = PlayerStargateData.get(player);
		this.updateList();
	}
	
	// ####################################################################################################
	// Interface definition :
	// ####################################################################################################
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
		
		String selectedTab = (this.selectedDimension == Dimension.EARTH) ? this.string_earth : (this.selectedDimension == Dimension.HELL) ? this.string_hell : (this.selectedDimension == Dimension.END) ? this.string_end : this.string_all;
		int selectedTabColor = (this.selectedDimension == null) ? YELLOW : GREEN;
		
		this.panel_listSelect.drawBorder(GRAY);
		this.panel_listButtons.drawBorder(GRAY);
		this.panel_information.drawBorder(GRAY);
		this.panel_main.drawBorder(GRAY);
		
		this.nextYPos = MARGIN;
		this.panel_information.drawCenteredText(this.fontRenderer, this.string_invName, this.nextYPos, WHITE);
		this.panel_information.drawText(this.fontRenderer, this.string_address, MARGIN, this.nextYPos, WHITE);
		this.panel_information.drawText(this.fontRenderer, this.string_name, MARGIN, this.nextYPos, WHITE);
		
		this.nextYPos = (3 * BUTTON_HEIGHT) + (2 * CHEVRON_MARGIN) + (2 * MARGIN) + this.offset;
		this.panel_main.drawText(this.fontRenderer, this.string_address, MARGIN, this.nextYPos, WHITE);
		this.panel_main.drawText(this.fontRenderer, this.string_name, MARGIN, this.nextYPos, WHITE);
		
		this.nextYPos = MARGIN;
		this.panel_listButtons.drawCenteredText(this.fontRenderer, this.string_tab + " : " + selectedTab, this.nextYPos, selectedTabColor);
		
		this.selectionList.drawScreen(par1, par2, par3);
	}
	
	@Override
	public void initComponents() {
		super.initComponents();
		
		// Panel sizes :
		
		int panelWidth = (this.width - (3 * PANEL_MARGIN)) / 2;
		int panelHeight = this.height - (2 * PANEL_MARGIN);
		int panelHeight_information = (5 * MARGIN) + (3 * FIELD_HEIGHT) + BUTTON_HEIGHT;
		int panelHeight_listButtons = (4 * MARGIN) + FIELD_HEIGHT + (2 * BUTTON_HEIGHT);
		
		// Panels :
		
		this.panel_list = new Panel(this, PANEL_MARGIN, PANEL_MARGIN, panelWidth, panelHeight);
		this.panel_controls = new Panel(this, this.panel_list.getRight() + PANEL_MARGIN, PANEL_MARGIN, panelWidth, panelHeight);
		
		this.panel_listButtons = new Panel(this.panel_list, 0, this.panel_list.getHeight() - panelHeight_listButtons, this.panel_list.getWidth(), panelHeight_listButtons);
		this.panel_listSelect = new Panel(this.panel_list, 0, 0, this.panel_list.getWidth(), this.panel_list.getHeight() - panelHeight_listButtons - PANEL_MARGIN);
		
		this.panel_information = new Panel(this.panel_controls, 0, 0, this.panel_controls.getWidth(), panelHeight_information);
		this.panel_main = new Panel(this.panel_controls, 0, this.panel_information.getBottom() + PANEL_MARGIN, this.panel_controls.getWidth(), this.panel_controls.getHeight() - this.panel_information.getBottom() - PANEL_MARGIN);
		
		// Strings :
		
		String coords = "-";
		String stargateName = "";
		if(this.tileEntity != null) {
			coords = "(" + this.tileEntity.xCoord + ", " + this.tileEntity.yCoord + ", " + this.tileEntity.zCoord + ")";
			
			Stargate stargate = this.getThisStargate();
			int index = this.playerData.getDataList().indexOf(stargate);
			
			if(index >= 0) {
				stargateName = this.playerData.getDataList().get(index).name;
			}
		}
		
		this.string_invName = I18n.func_135053_a(INV_NAME);
		this.string_address = I18n.func_135053_a(ADDRESS) + " : ";
		this.string_name = I18n.func_135053_a(NAME) + " : ";
		this.string_destination = I18n.func_135053_a(DESTINATION);
		this.string_tab = I18n.func_135053_a(TAB);
		this.string_earth = I18n.func_135053_a(EARTH);
		this.string_hell = I18n.func_135053_a(HELL);
		this.string_end = I18n.func_135053_a(END);
		this.string_all = I18n.func_135053_a(ALL);
		
		// Component sizes :
		
		this.offset = this.panel_main.getHeight() - ((5 * BUTTON_HEIGHT) + (2 * FIELD_HEIGHT) + (6 * MARGIN) + (2 * CHEVRON_MARGIN));
		
		if(this.offset < 0) {
			this.offset = 0;
		}
		
		int stringSize_main = Math.max(this.fontRenderer.getStringWidth(this.string_name), this.fontRenderer.getStringWidth(this.string_address));
		int stringSize_information = stringSize_main;
		
		int fieldOffset_main = stringSize_main + MARGIN;
		int fieldOffset_information = stringSize_information + MARGIN;
		
		int fieldSize_main = this.panel_main.getWidth() - (fieldOffset_main + MARGIN);
		int fieldSize_information = this.panel_information.getWidth() - (fieldOffset_information + MARGIN);
		
		int buttonSize_main = this.panel_main.getWidth() - (2 * MARGIN);
		int buttonSize_chevron15 = (this.panel_main.getWidth() - (2 * MARGIN + 14 * CHEVRON_MARGIN)) / 15;
		int buttonSize_information = this.panel_information.getWidth() - (2 * MARGIN);
		int buttonSize_listButtons_1 = (this.panel_listButtons.getWidth() - (5 * MARGIN)) / 4;
		int buttonSize_listButtons_2 = (this.panel_listButtons.getWidth() - (4 * MARGIN)) / 3;
		
		int listMargin = 2;
		
		// Fields and buttons :
		
		this.nextYPos = MARGIN;
		
		this.addButton(new Button(this.panel_main, this.getNextButtonId(), MARGIN, this.nextYPos, buttonSize_chevron15, "a"), false);
		this.nextYPos += BUTTON_HEIGHT + CHEVRON_MARGIN;
		this.addButton(new Button(this.panel_main, this.getNextButtonId(), MARGIN, this.nextYPos, buttonSize_chevron15, "A"), false);
		this.nextYPos += BUTTON_HEIGHT + CHEVRON_MARGIN;
		this.addButton(new Button(this.panel_main, this.getNextButtonId(), MARGIN, this.nextYPos, buttonSize_chevron15, "z"), false);
		this.nextYPos += BUTTON_HEIGHT + MARGIN;
		
		// FIXME - mettre les boutons des chevrons.
		this.nextYPos += FIELD_HEIGHT + MARGIN + FIELD_OFFSET + this.offset;
		this.field_name2 = this.addField(new TextField(this.panel_main, this.fontRenderer, fieldOffset_main, this.nextYPos, fieldSize_main, FIELD_HEIGHT));
		this.nextYPos -= FIELD_OFFSET;
		this.button_activate = this.addButton(new Button(this.panel_main, this.getNextButtonId(), MARGIN, this.nextYPos, buttonSize_main, I18n.func_135053_a(ACTIVATE)));
		this.button_cancel = this.addButton(new Button(this.panel_main, this.getNextButtonId(), MARGIN, this.nextYPos, buttonSize_main, I18n.func_135053_a("gui.cancel")));
		
		this.nextYPos = (2 * FIELD_HEIGHT) + (3 * MARGIN) + FIELD_OFFSET;
		this.field_name1 = this.addField(new TextField(this.panel_information, this.fontRenderer, fieldOffset_information, this.nextYPos, fieldSize_information, FIELD_HEIGHT, stargateName));
		this.nextYPos -= FIELD_OFFSET;
		this.button_addThis = this.addButton(new Button(this.panel_information, this.getNextButtonId(), MARGIN, this.nextYPos, buttonSize_information, I18n.func_135053_a(ADD_THIS)));
		
		this.nextYPos = FIELD_HEIGHT + (2 * MARGIN);
		this.button_earth = this.addButton(new Button(this.panel_listButtons, this.getNextButtonId(), MARGIN, this.nextYPos, buttonSize_listButtons_1, this.string_earth), false);
		this.button_hell = this.addButton(new Button(this.panel_listButtons, this.getNextButtonId(), buttonSize_listButtons_1 + (2 * MARGIN), this.nextYPos, buttonSize_listButtons_1, this.string_hell), false);
		this.button_end = this.addButton(new Button(this.panel_listButtons, this.getNextButtonId(), (2 * buttonSize_listButtons_1) + (3 * MARGIN), this.nextYPos, buttonSize_listButtons_1, this.string_end), false);
		this.button_all = this.addButton(new Button(this.panel_listButtons, this.getNextButtonId(), (3 * buttonSize_listButtons_1) + (4 * MARGIN), this.nextYPos, buttonSize_listButtons_1, this.string_all));
		this.button_delete = this.addButton(new Button(this.panel_listButtons, this.getNextButtonId(), MARGIN, this.nextYPos, buttonSize_listButtons_2, I18n.func_135053_a(DELETE)), false);
		this.button_overwrite = this.addButton(new Button(this.panel_listButtons, this.getNextButtonId(), buttonSize_listButtons_2 + (2 * MARGIN), this.nextYPos, buttonSize_listButtons_2, I18n.func_135053_a(OVERWRITE)), false);
		this.button_add = this.addButton(new Button(this.panel_listButtons, this.getNextButtonId(), (2 * buttonSize_listButtons_2) + (3 * MARGIN), this.nextYPos, buttonSize_listButtons_2, I18n.func_135053_a(ADD)));
		
		// List :
		
		this.selectionList = new StargateSelectionList(this, this.panel_listSelect.getXPosInScreen(0) + listMargin, this.panel_listSelect.getYPosInScreen(0) + listMargin, this.panel_listSelect.getWidth() - (2 * listMargin), this.panel_listSelect.getHeight() - (2 * listMargin));
		this.selectionList.registerScrollButtons(this.buttonList, this.getNextButtonId(), this.getNextButtonId());
		
		this.updateInterface();
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
		}
	}
	
	@Override
	protected void onEnterPressed() {
		this.activate();
	}
	
	protected void activate() {
		if(this.button_activate.enabled) {
			Stargate stargate = this.getStargateFromFields();
			
			if(stargate != null) {
				this.close();
				StargateMod.sendPacketToServer(this.tileEntity.getStargateOpenPacket(stargate.address));
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
			this.playerData.deleteElementAndSync(this.selectedStargate);
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
	
	protected Stargate getThisStargate() {
		if(this.stargateConnected) {
			String address = this.stargate.getAddress();
			int code = this.stargate.getCode();
			return new Stargate(address, "", code);
		}
		
		return null;
	}
	
	protected Stargate getStargateFromFields() {
		String name = this.field_name2.getText();
		String address = "TODO"; // FIXME - recuperer l'adresse depuis les champs.
		// FIXME - tester la validite de l'adresse.
		return new Stargate(address, name, 0);
	}
	
	private boolean isStargateInRange(Stargate stargate) {
		if(this.selectedDimension == null) {
			return true;
		}
		
		if(!this.stargateConnected || this.stargate.getAddress() != stargate.address) {
			try {
				StargateZoneCoordinates coords = GateAddress.toCoordinates(stargate.address);
				return coords.dim == this.tileEntity.worldObj.provider.dimensionId;
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
		Stargate stargate = this.getStargateFromFields();
		this.updateInterface(stargate != null && GateAddress.isValidAddress(stargate.address));
	}
	
	protected void updateInterface(boolean canActivate) {
		boolean stargateSelected = this.selectedStargate != null;
		
		this.button_activate.enabled = canActivate;
		this.button_add.enabled = canActivate;
		this.button_overwrite.enabled = canActivate && stargateSelected;
		this.button_delete.enabled = stargateSelected;
		
		this.updateTabs();
	}
	
	protected void updateTabs() {
		this.button_earth.enabled = this.selectedDimension != Dimension.EARTH;
		this.button_hell.enabled = this.selectedDimension != Dimension.HELL;
		this.button_end.enabled = this.selectedDimension != Dimension.END;
		this.button_all.enabled = this.selectedDimension != null;
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
			// FIXME - address.
			this.field_name2.setText(String.valueOf(stargate.name));
		}
		else {
			// FIXME - address.
			this.field_name2.setText("");
		}
		
		this.onFieldChanged();
	}
	
	@Override
	public void onElementDoubleClicked() {
		this.activate();
	}
	
	@Override
	public FontRenderer getFontRenderer() {
		return this.fontRenderer;
	}
	
}
