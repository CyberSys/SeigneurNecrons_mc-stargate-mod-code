package seigneurnecron.minecraftmods.stargate.client.gui;

import static seigneurnecron.minecraftmods.stargate.tileentity.TileEntityBaseTeleporter.INV_NAME;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.client.gui.tools.Button;
import seigneurnecron.minecraftmods.stargate.client.gui.tools.IntegerField;
import seigneurnecron.minecraftmods.stargate.client.gui.tools.ListProviderGui;
import seigneurnecron.minecraftmods.stargate.client.gui.tools.Panel;
import seigneurnecron.minecraftmods.stargate.client.gui.tools.TeleporterSelectionList;
import seigneurnecron.minecraftmods.stargate.client.gui.tools.TextField;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityBaseTeleporter;
import seigneurnecron.minecraftmods.stargate.tools.loadable.Teleporter;
import seigneurnecron.minecraftmods.stargate.tools.playerData.PlayerTeleporterData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class GuiTeleporter extends GuiBase<TileEntityBaseTeleporter> implements ListProviderGui<Teleporter> {
	
	// ####################################################################################################
	// Lang constants :
	// ####################################################################################################
	
	public static final String COORDINATES = INV_NAME + ".coordinates";
	public static final String TELEPORT = INV_NAME + ".teleport";
	public static final String IN_RANGE = INV_NAME + ".inRange";
	public static final String MESSAGE_OK = INV_NAME + ".messageOk";
	public static final String MESSAGE_OUT_OF_RANGE = INV_NAME + ".messageNotInRange";
	public static final String MESSAGE_INVALID = INV_NAME + ".messageInvalid";
	
	// ####################################################################################################
	// Interface fields :
	// ####################################################################################################
	
	protected int offset;
	
	protected String string_invName;
	protected String string_coordinates;
	protected String string_name;
	protected String string_destination;
	protected String string_xCoordinate;
	protected String string_yCoordinate;
	protected String string_zCoordinate;
	protected String string_tab;
	protected String string_all;
	protected String string_inRange;
	protected String string_messageOk;
	protected String string_messageOutOfRange;
	protected String string_messageInvalid;
	
	protected Panel panel_list;
	protected Panel panel_controls;
	protected Panel panel_listSelect;
	protected Panel panel_listButtons;
	protected Panel panel_information;
	protected Panel panel_main;
	
	protected TextField field_x;
	protected TextField field_y;
	protected TextField field_z;
	protected TextField field_name1;
	protected TextField field_name2;
	
	protected Button button_cancel;
	protected Button button_teleport;
	protected Button button_addThis;
	protected Button button_inRange;
	protected Button button_all;
	protected Button button_add;
	protected Button button_delete;
	protected Button button_overwrite;
	
	protected TeleporterSelectionList selectionList;
	
	// ####################################################################################################
	// Data fields :
	// ####################################################################################################
	
	protected String message = "";
	protected int messageColor = WHITE;
	protected boolean displayAll = false;
	
	protected List<Teleporter> teleporters = new LinkedList<Teleporter>();
	protected Teleporter selectedTeleporter = null;
	
	protected PlayerTeleporterData playerData;
	
	// ####################################################################################################
	// Builder :
	// ####################################################################################################
	
	public GuiTeleporter(TileEntityBaseTeleporter tileEntity, EntityPlayer player) {
		super(tileEntity, player);
		this.playerData = PlayerTeleporterData.get(player);
		this.updateList();
	}
	
	// ####################################################################################################
	// Interface definition :
	// ####################################################################################################
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
		
		this.panel_listSelect.drawBox(GRAY);
		this.panel_listButtons.drawBox(GRAY);
		this.panel_information.drawBox(GRAY);
		this.panel_main.drawBox(GRAY);
		
		this.nextYPos = MARGIN;
		this.panel_information.drawCenteredText(this.fontRenderer, this.string_invName, this.nextYPos, WHITE);
		this.panel_information.drawCenteredText(this.fontRenderer, this.string_coordinates, this.nextYPos, WHITE);
		this.panel_information.drawText(this.fontRenderer, this.string_name, MARGIN, this.nextYPos, WHITE);
		
		this.nextYPos = MARGIN;
		this.panel_main.drawCenteredText(this.fontRenderer, this.string_destination, this.nextYPos, WHITE);
		this.panel_main.drawText(this.fontRenderer, this.string_xCoordinate, MARGIN, this.nextYPos, WHITE);
		this.panel_main.drawText(this.fontRenderer, this.string_yCoordinate, MARGIN, this.nextYPos, WHITE);
		this.panel_main.drawText(this.fontRenderer, this.string_zCoordinate, MARGIN, this.nextYPos, WHITE);
		this.panel_main.drawText(this.fontRenderer, this.string_name, MARGIN, this.nextYPos, WHITE);
		this.nextYPos += this.offset;
		this.panel_main.drawCenteredText(this.fontRenderer, this.message, this.nextYPos, this.messageColor);
		
		this.nextYPos = MARGIN;
		this.panel_listButtons.drawCenteredText(this.fontRenderer, this.string_tab + " : " + (this.displayAll ? this.string_all : this.string_inRange), this.nextYPos, (this.displayAll ? YELLOW : GREEN));
		
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
		
		this.panel_listButtons = new Panel(this.panel_list, 0, this.panel_list.getComponentHeight() - panelHeight_listButtons, this.panel_list.getComponentWidth(), panelHeight_listButtons);
		this.panel_listSelect = new Panel(this.panel_list, 0, 0, this.panel_list.getComponentWidth(), this.panel_list.getComponentHeight() - panelHeight_listButtons - PANEL_MARGIN);
		
		this.panel_information = new Panel(this.panel_controls, 0, 0, this.panel_controls.getComponentWidth(), panelHeight_information);
		this.panel_main = new Panel(this.panel_controls, 0, this.panel_information.getBottom() + PANEL_MARGIN, this.panel_controls.getComponentWidth(), this.panel_controls.getComponentHeight() - this.panel_information.getBottom() - PANEL_MARGIN);
		
		// Strings :
		
		String coords = "-";
		String teleporterName = "";
		if(this.tileEntity != null) {
			coords = "(" + this.tileEntity.xCoord + ", " + this.tileEntity.yCoord + ", " + this.tileEntity.zCoord + ")";
			
			Teleporter teleporter = this.getThisTeleporter();
			int index = this.playerData.getDataList().indexOf(teleporter);
			
			if(index >= 0) {
				teleporterName = this.playerData.getDataList().get(index).name;
			}
		}
		
		this.string_invName = I18n.func_135053_a(INV_NAME);
		this.string_coordinates = I18n.func_135053_a(COORDINATES) + " : " + coords;
		this.string_name = I18n.func_135053_a(NAME) + " : ";
		this.string_destination = I18n.func_135053_a(DESTINATION);
		this.string_xCoordinate = "x : ";
		this.string_yCoordinate = "y : ";
		this.string_zCoordinate = "z : ";
		this.string_tab = I18n.func_135053_a(TAB);
		this.string_all = I18n.func_135053_a(ALL);
		this.string_inRange = I18n.func_135053_a(IN_RANGE);
		this.string_messageOk = I18n.func_135053_a(MESSAGE_OK);
		this.string_messageOutOfRange = I18n.func_135053_a(MESSAGE_OUT_OF_RANGE);
		this.string_messageInvalid = I18n.func_135053_a(MESSAGE_INVALID);
		
		// Component sizes :
		
		this.offset = this.panel_main.getComponentHeight() - ((2 * BUTTON_HEIGHT) + (6 * FIELD_HEIGHT) + (9 * MARGIN));
		
		if(this.offset < 0) {
			this.offset = 0;
		}
		
		int stringSize_main = Math.max(Math.max(this.fontRenderer.getStringWidth(this.string_name), this.fontRenderer.getStringWidth(this.string_xCoordinate)), Math.max(this.fontRenderer.getStringWidth(this.string_yCoordinate), this.fontRenderer.getStringWidth(this.string_zCoordinate)));
		int stringSize_information = this.fontRenderer.getStringWidth(this.string_name);
		
		int fieldOffset_main = stringSize_main + MARGIN;
		int fieldOffset_information = stringSize_information + MARGIN;
		
		int fieldSize_main = this.panel_main.getComponentWidth() - (fieldOffset_main + MARGIN);
		int fieldSize_information = this.panel_information.getComponentWidth() - (fieldOffset_information + MARGIN);
		
		int buttonSize_main = this.panel_main.getComponentWidth() - (2 * MARGIN);
		int buttonSize_information = this.panel_information.getComponentWidth() - (2 * MARGIN);
		int buttonSize_listButtons_1 = (this.panel_listButtons.getComponentWidth() - (3 * MARGIN)) / 2;
		int buttonSize_listButtons_2 = (this.panel_listButtons.getComponentWidth() - (4 * MARGIN)) / 3;
		
		int listMargin = 2;
		
		// Fields and buttons :
		
		this.nextYPos = FIELD_HEIGHT + (2 * MARGIN);
		this.field_x = this.addComponent(new IntegerField(this.panel_main, this.fontRenderer, fieldOffset_main, this.nextYPos, fieldSize_main));
		this.field_y = this.addComponent(new IntegerField(this.panel_main, this.fontRenderer, fieldOffset_main, this.nextYPos, fieldSize_main));
		this.field_z = this.addComponent(new IntegerField(this.panel_main, this.fontRenderer, fieldOffset_main, this.nextYPos, fieldSize_main));
		this.field_name2 = this.addComponent(new TextField(this.panel_main, this.fontRenderer, fieldOffset_main, this.nextYPos, fieldSize_main));
		this.nextYPos += FIELD_HEIGHT + MARGIN + this.offset;
		this.button_teleport = this.addComponent(new Button(this.panel_main, this.getNextButtonId(), MARGIN, this.nextYPos, buttonSize_main, I18n.func_135053_a(TELEPORT)));
		this.button_cancel = this.addComponent(new Button(this.panel_main, this.getNextButtonId(), MARGIN, this.nextYPos, buttonSize_main, I18n.func_135053_a("gui.cancel")));
		
		this.nextYPos = (2 * FIELD_HEIGHT) + (3 * MARGIN);
		this.field_name1 = this.addComponent(new TextField(this.panel_information, this.fontRenderer, fieldOffset_information, this.nextYPos, fieldSize_information, teleporterName));
		this.button_addThis = this.addComponent(new Button(this.panel_information, this.getNextButtonId(), MARGIN, this.nextYPos, buttonSize_information, I18n.func_135053_a(ADD_THIS)));
		
		this.nextYPos = FIELD_HEIGHT + (2 * MARGIN);
		this.button_inRange = this.addComponent(new Button(this.panel_listButtons, this.getNextButtonId(), MARGIN, this.nextYPos, buttonSize_listButtons_1, this.string_inRange), false);
		this.button_all = this.addComponent(new Button(this.panel_listButtons, this.getNextButtonId(), buttonSize_listButtons_1 + (2 * MARGIN), this.nextYPos, buttonSize_listButtons_1, this.string_all));
		this.button_delete = this.addComponent(new Button(this.panel_listButtons, this.getNextButtonId(), MARGIN, this.nextYPos, buttonSize_listButtons_2, I18n.func_135053_a(DELETE)), false);
		this.button_overwrite = this.addComponent(new Button(this.panel_listButtons, this.getNextButtonId(), buttonSize_listButtons_2 + (2 * MARGIN), this.nextYPos, buttonSize_listButtons_2, I18n.func_135053_a(OVERWRITE)), false);
		this.button_add = this.addComponent(new Button(this.panel_listButtons, this.getNextButtonId(), (2 * buttonSize_listButtons_2) + (3 * MARGIN), this.nextYPos, buttonSize_listButtons_2, I18n.func_135053_a(ADD)));
		
		// List :
		
		this.selectionList = new TeleporterSelectionList(this, this.panel_listSelect.getXPosInScreen(0) + listMargin, this.panel_listSelect.getYPosInScreen(0) + listMargin, this.panel_listSelect.getComponentWidth() - (2 * listMargin), this.panel_listSelect.getComponentHeight() - (2 * listMargin));
		this.selectionList.registerScrollButtons(this.buttonList, this.getNextButtonId(), this.getNextButtonId());
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
			else if(guiButton == this.button_teleport) {
				this.teleport();
			}
			else if(guiButton == this.button_addThis) {
				this.addThis();
			}
			else if(guiButton == this.button_inRange) {
				this.switchTab(false);
			}
			else if(guiButton == this.button_all) {
				this.switchTab(true);
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
		this.teleport();
	}
	
	protected void teleport() {
		if(this.button_teleport.enabled) {
			Teleporter teleporter = this.getTeleporterFromFields();
			
			if(teleporter != null) {
				this.close();
				StargateMod.sendPacketToServer(this.tileEntity.getTeleportPacket(teleporter.x, teleporter.y, teleporter.z));
			}
		}
	}
	
	protected void addThis() {
		Teleporter teleporter = this.getThisTeleporter();
		teleporter.name = this.field_name1.getText();
		this.playerData.addElementAndSync(teleporter);
		this.updateList();
	}
	
	protected void switchTab(boolean displayAll) {
		this.displayAll = displayAll;
		this.updateList();
		this.updateTabs();
	}
	
	protected void add() {
		Teleporter teleporter = this.getTeleporterFromFields();
		
		if(teleporter != null) {
			this.playerData.addElementAndSync(teleporter);
			this.setSelectedTeleporter(teleporter);
			this.updateList();
		}
	}
	
	protected void remove() {
		if(this.selectedTeleporter != null) {
			this.playerData.removeElementAndSync(this.selectedTeleporter);
			this.setSelectedTeleporter(null);
			this.updateList();
		}
	}
	
	protected void overwite() {
		Teleporter teleporter = this.getTeleporterFromFields();
		
		if(teleporter != null && this.selectedTeleporter != null) {
			this.playerData.overwriteElementAndSync(this.selectedTeleporter, teleporter);
			this.setSelectedTeleporter(teleporter);
			this.updateList();
		}
	}
	
	// ####################################################################################################
	// Utility :
	// ####################################################################################################
	
	protected Teleporter getThisTeleporter() {
		int dim = this.tileEntity.getDimension();
		int x = this.tileEntity.xCoord;
		int y = this.tileEntity.yCoord;
		int z = this.tileEntity.zCoord;
		return new Teleporter(dim, x, y, z, "");
	}
	
	protected Teleporter getTeleporterFromFields() {
		try {
			int dim = this.tileEntity.getDimension();
			int x = Integer.parseInt(this.field_x.getText());
			int y = Integer.parseInt(this.field_y.getText());
			int z = Integer.parseInt(this.field_z.getText());
			String name = this.field_name2.getText();
			return new Teleporter(dim, x, y, z, name);
		}
		catch(NumberFormatException argh) {
			return null;
		}
	}
	
	protected void updateList() {
		List<Teleporter> teleportersInRange = new LinkedList<Teleporter>();
		
		for(Teleporter teleporter : this.playerData.getDataList()) {
			if(teleporter.dim == this.tileEntity.getDimension() && (this.displayAll || this.tileEntity.isValid(teleporter.x, teleporter.y, teleporter.z) && this.tileEntity.isInRange(teleporter.x, teleporter.y, teleporter.z))) {
				teleportersInRange.add(teleporter);
			}
		}
		
		this.teleporters = teleportersInRange;
	}
	
	@Override
	protected void onFieldChanged() {
		try {
			int x = Integer.parseInt(this.field_x.getText());
			int y = Integer.parseInt(this.field_y.getText());
			int z = Integer.parseInt(this.field_z.getText());
			
			if(!this.tileEntity.isValid(x, y, z)) {
				this.updateInterface(false, false);
			}
			else if(!this.tileEntity.isInRange(x, y, z)) {
				this.updateInterface(false, true);
			}
			else {
				this.updateInterface(true, true);
			}
		}
		catch(NumberFormatException argh) {
			this.updateInterface(false, false);
		}
	}
	
	protected void updateInterface(boolean canTeleport, boolean canSave) {
		boolean teleporterSelected = this.selectedTeleporter != null;
		
		this.message = canTeleport ? this.string_messageOk : canSave ? this.string_messageOutOfRange : this.string_messageInvalid;
		this.messageColor = canTeleport ? GREEN : canSave ? YELLOW : RED;
		
		this.button_teleport.enabled = canTeleport;
		this.button_add.enabled = canSave;
		this.button_overwrite.enabled = canSave && teleporterSelected;
		this.button_delete.enabled = teleporterSelected;
	}
	
	protected void updateTabs() {
		this.button_inRange.enabled = this.displayAll;
		this.button_all.enabled = !this.displayAll;
	}
	
	@Override
	protected void onGuiInitialized() {
		super.onGuiInitialized();
		this.updateTabs();
	}
	
	// ####################################################################################################
	// ListProvider interface :
	// ####################################################################################################
	
	@Override
	public List<Teleporter> getList() {
		return this.teleporters;
	}
	
	@Override
	public int getSelectedIndex() {
		return this.teleporters.indexOf(this.selectedTeleporter);
	}
	
	@Override
	public void setSelectedIndex(int index) {
		if(index >= 0 && index < this.teleporters.size()) {
			this.setSelectedTeleporter(this.teleporters.get(index));
		}
		else {
			this.setSelectedTeleporter(null);
		}
	}
	
	protected void setSelectedTeleporter(Teleporter teleporter) {
		this.selectedTeleporter = teleporter;
		
		if(teleporter != null) {
			this.field_x.setText(String.valueOf(teleporter.x));
			this.field_y.setText(String.valueOf(teleporter.y));
			this.field_z.setText(String.valueOf(teleporter.z));
			this.field_name2.setText(String.valueOf(teleporter.name));
		}
		else {
			this.field_x.setText("0");
			this.field_y.setText("0");
			this.field_z.setText("0");
			this.field_name2.setText("");
		}
		
		this.onFieldChanged();
	}
	
	@Override
	public void onElementDoubleClicked() {
		this.teleport();
	}
	
	@Override
	public FontRenderer getFirstFontRenderer() {
		return this.fontRenderer;
	}
	
	@Override
	public FontRenderer getSecondFontRenderer() {
		return this.fontRenderer;
	}
	
}
