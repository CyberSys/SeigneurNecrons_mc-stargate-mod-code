package seigneurnecron.minecraftmods.stargate.gui;

import static seigneurnecron.minecraftmods.core.gui.GuiConstants.BACKGROUND_COLOR;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.BUTTON_HEIGHT;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.FIELD_HEIGHT;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.GREEN;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.LIGHT_BLUE;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.MARGIN;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.PANEL_MARGIN;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.RED;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.YELLOW;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import seigneurnecron.minecraftmods.core.gui.GuiScreenTileEntity;
import seigneurnecron.minecraftmods.core.gui.IntegerField;
import seigneurnecron.minecraftmods.core.gui.Label;
import seigneurnecron.minecraftmods.core.gui.ListProviderGui;
import seigneurnecron.minecraftmods.core.gui.Panel;
import seigneurnecron.minecraftmods.core.gui.TextField;
import seigneurnecron.minecraftmods.core.mod.ModBase;
import seigneurnecron.minecraftmods.stargate.gui.components.SelectionListTeleporter;
import seigneurnecron.minecraftmods.stargate.gui.components.StargateButton;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityConsoleBase;
import seigneurnecron.minecraftmods.stargate.tileentity.console.ConsoleTeleporter;
import seigneurnecron.minecraftmods.stargate.tools.loadable.Teleporter;
import seigneurnecron.minecraftmods.stargate.tools.playerdata.PlayerTeleporterData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class GuiTeleporter extends GuiScreenConsolePanel<ConsoleTeleporter> implements ListProviderGui<Teleporter> {
	
	// ####################################################################################################
	// Lang constants :
	// ####################################################################################################
	
	public static final String INV_NAME = "container.teleporter";
	
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
	
	protected Label label_invName;
	protected Label label_coordinates;
	protected Label label_name1;
	protected Label label_destination;
	protected Label label_x;
	protected Label label_y;
	protected Label label_z;
	protected Label label_name2;
	protected Label label_message;
	protected Label label_tab;
	
	protected TextField field_x;
	protected TextField field_y;
	protected TextField field_z;
	protected TextField field_name1;
	protected TextField field_name2;
	
	protected StargateButton button_cancel;
	protected StargateButton button_teleport;
	protected StargateButton button_addThis;
	protected StargateButton button_inRange;
	protected StargateButton button_all;
	protected StargateButton button_add;
	protected StargateButton button_delete;
	protected StargateButton button_overwrite;
	
	protected SelectionListTeleporter selectionList;
	
	// ####################################################################################################
	// Data fields :
	// ####################################################################################################
	
	protected boolean displayAll = false;
	
	protected List<Teleporter> teleporters = new LinkedList<Teleporter>();
	protected Teleporter selectedTeleporter = null;
	
	protected PlayerTeleporterData playerData;
	
	// ####################################################################################################
	// Constructors :
	// ####################################################################################################
	
	public GuiTeleporter(TileEntityConsoleBase tileEntity, EntityPlayer player, ConsoleTeleporter console) {
		super(tileEntity, player, console);
		this.playerData = PlayerTeleporterData.get(player);
		this.updateList();
	}
	
	// ####################################################################################################
	// Interface definition :
	// ####################################################################################################
	
	@Override
	protected void drawBackground(int par1, int par2, float par3) {
		super.drawBackground(par1, par2, par3);
		this.panel_listSelect.drawBox(LIGHT_BLUE, BACKGROUND_COLOR);
		this.panel_listButtons.drawBox(LIGHT_BLUE, BACKGROUND_COLOR);
		this.panel_information.drawBox(LIGHT_BLUE, BACKGROUND_COLOR);
		this.panel_main.drawBox(LIGHT_BLUE, BACKGROUND_COLOR);
	}
	
	@Override
	protected void drawForeground(int par1, int par2) {
		super.drawForeground(par1, par2);
		this.selectionList.drawScreen(par1, par2);
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
		
		String string_name = I18n.func_135053_a(NAME) + " : ";
		
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
		
		int stringSize = this.fontRenderer.getStringWidth(string_name);
		int fieldOffset = stringSize + MARGIN;
		int fieldSize = this.panel_main.getComponentWidth() - (fieldOffset + MARGIN);
		
		int buttonSize_main = this.panel_main.getComponentWidth() - (2 * MARGIN);
		int buttonSize_listButtons = this.panel_listButtons.getComponentWidth() - (2 * MARGIN);
		int buttonSize_listButtons_1 = (this.panel_listButtons.getComponentWidth() - (3 * MARGIN)) / 2;
		int buttonSize_listButtons_2 = (this.panel_listButtons.getComponentWidth() - (4 * MARGIN)) / 3;
		
		int listMargin = 2;
		
		// Components :
		
		this.nextYPos = MARGIN;
		this.label_invName = this.addComponent(new Label(this.panel_information, this.fontRenderer, MARGIN, this.nextYPos, buttonSize_main, I18n.func_135053_a(INV_NAME), true));
		this.label_coordinates = this.addComponent(new Label(this.panel_information, this.fontRenderer, MARGIN, this.nextYPos, buttonSize_main, I18n.func_135053_a(COORDINATES) + " : " + coords, true));
		this.label_name1 = this.addComponent(new Label(this.panel_information, this.fontRenderer, MARGIN, this.nextYPos, stringSize, string_name), false);
		this.field_name1 = this.addComponent(new TextField(this.panel_information, this.fontRenderer, fieldOffset, this.nextYPos, fieldSize, teleporterName));
		this.button_addThis = this.addComponent(new StargateButton(this.panel_information, MARGIN, this.nextYPos, buttonSize_main, I18n.func_135053_a(ADD_THIS)));
		
		this.nextYPos = MARGIN;
		this.label_destination = this.addComponent(new Label(this.panel_main, this.fontRenderer, MARGIN, this.nextYPos, buttonSize_main, I18n.func_135053_a(DESTINATION), true));
		this.label_x = this.addComponent(new Label(this.panel_main, this.fontRenderer, MARGIN, this.nextYPos, stringSize, "x : "), false);
		this.field_x = this.addComponent(new IntegerField(this.panel_main, this.fontRenderer, fieldOffset, this.nextYPos, fieldSize));
		this.label_y = this.addComponent(new Label(this.panel_main, this.fontRenderer, MARGIN, this.nextYPos, stringSize, "y : "), false);
		this.field_y = this.addComponent(new IntegerField(this.panel_main, this.fontRenderer, fieldOffset, this.nextYPos, fieldSize));
		this.label_z = this.addComponent(new Label(this.panel_main, this.fontRenderer, MARGIN, this.nextYPos, stringSize, "z : "), false);
		this.field_z = this.addComponent(new IntegerField(this.panel_main, this.fontRenderer, fieldOffset, this.nextYPos, fieldSize));
		this.label_name2 = this.addComponent(new Label(this.panel_main, this.fontRenderer, MARGIN, this.nextYPos, stringSize, string_name), false);
		this.field_name2 = this.addComponent(new TextField(this.panel_main, this.fontRenderer, fieldOffset, this.nextYPos, fieldSize));
		this.nextYPos += this.offset;
		this.label_message = this.addComponent(new Label(this.panel_main, this.fontRenderer, MARGIN, this.nextYPos, buttonSize_main, "", true));
		this.button_teleport = this.addComponent(new StargateButton(this.panel_main, MARGIN, this.nextYPos, buttonSize_main, I18n.func_135053_a(TELEPORT) + I18n.func_135053_a(GuiScreenTileEntity.ENTER)));
		this.button_cancel = this.addComponent(new StargateButton(this.panel_main, MARGIN, this.nextYPos, buttonSize_main, I18n.func_135053_a("gui.cancel") + I18n.func_135053_a(GuiScreenTileEntity.ESC)));
		
		this.nextYPos = MARGIN;
		this.label_tab = this.addComponent(new Label(this.panel_listButtons, this.fontRenderer, MARGIN, this.nextYPos, buttonSize_listButtons, "", true));
		this.button_inRange = this.addComponent(new StargateButton(this.panel_listButtons, MARGIN, this.nextYPos, buttonSize_listButtons_1, this.string_inRange), false);
		this.button_all = this.addComponent(new StargateButton(this.panel_listButtons, buttonSize_listButtons_1 + (2 * MARGIN), this.nextYPos, buttonSize_listButtons_1, this.string_all));
		this.button_delete = this.addComponent(new StargateButton(this.panel_listButtons, MARGIN, this.nextYPos, buttonSize_listButtons_2, I18n.func_135053_a(DELETE)), false);
		this.button_overwrite = this.addComponent(new StargateButton(this.panel_listButtons, buttonSize_listButtons_2 + (2 * MARGIN), this.nextYPos, buttonSize_listButtons_2, I18n.func_135053_a(OVERWRITE)), false);
		this.button_add = this.addComponent(new StargateButton(this.panel_listButtons, (2 * buttonSize_listButtons_2) + (3 * MARGIN), this.nextYPos, buttonSize_listButtons_2, I18n.func_135053_a(ADD)));
		
		// List :
		
		this.selectionList = new SelectionListTeleporter(this, this.panel_listSelect.getXPosInScreen(0) + listMargin, this.panel_listSelect.getYPosInScreen(0) + listMargin, this.panel_listSelect.getComponentWidth() - (2 * listMargin), this.panel_listSelect.getComponentHeight() - (2 * listMargin), this.mc.thePlayer);
		this.selectionList.registerScrollButtons(this.getNextButtonId(), this.getNextButtonId());
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
			else {
				this.selectionList.actionPerformed(guiButton);
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
				ModBase.sendPacketToServer(this.console.getTeleportPacket(teleporter.x, teleporter.y, teleporter.z));
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
			if(teleporter.dim == this.tileEntity.getDimension() && (this.displayAll || this.console.isValidDestination(teleporter.x, teleporter.y, teleporter.z) && this.console.isInRange(teleporter.x, teleporter.y, teleporter.z))) {
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
			
			if(!this.console.isValidDestination(x, y, z)) {
				this.updateInterface(false, false);
			}
			else if(!this.console.isInRange(x, y, z)) {
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
		
		this.button_teleport.enabled = canTeleport;
		this.button_add.enabled = canSave;
		this.button_overwrite.enabled = canSave && teleporterSelected;
		this.button_delete.enabled = teleporterSelected;
		
		String message = canTeleport ? this.string_messageOk : canSave ? this.string_messageOutOfRange : this.string_messageInvalid;
		int messageColor = canTeleport ? GREEN : canSave ? YELLOW : RED;
		this.label_message.setText(message, messageColor);
	}
	
	protected void updateTabs() {
		this.button_inRange.enabled = this.displayAll;
		this.button_all.enabled = !this.displayAll;
		
		String tab = this.string_tab + " : " + (this.displayAll ? this.string_all : this.string_inRange);
		int tabColor = this.displayAll ? YELLOW : GREEN;
		this.label_tab.setText(tab, tabColor);
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
