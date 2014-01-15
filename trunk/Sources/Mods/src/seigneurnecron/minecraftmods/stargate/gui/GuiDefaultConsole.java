package seigneurnecron.minecraftmods.stargate.gui;

import static seigneurnecron.minecraftmods.core.gui.GuiConstants.BACKGROUND_COLOR;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.BLUE;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.FIELD_HEIGHT;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.GRAY;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.LIGHT_BLUE;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.MARGIN;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.PANEL_MARGIN;
import static seigneurnecron.minecraftmods.stargate.inventory.InventoryConsoleBase.NB_CRYSTALS;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import seigneurnecron.minecraftmods.core.gui.Label;
import seigneurnecron.minecraftmods.core.gui.ListProviderSelectTwoLines;
import seigneurnecron.minecraftmods.core.gui.Panel;
import seigneurnecron.minecraftmods.core.gui.ScrollableText;
import seigneurnecron.minecraftmods.core.gui.TextProvider;
import seigneurnecron.minecraftmods.stargate.gui.components.SelectionListConsole;
import seigneurnecron.minecraftmods.stargate.item.ItemCrystal;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityConsoleBase;
import seigneurnecron.minecraftmods.stargate.tileentity.console.Console;
import seigneurnecron.minecraftmods.stargate.tileentity.console.ConsoleDefault;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class GuiDefaultConsole extends GuiScreenConsolePanel<ConsoleDefault> implements ListProviderSelectTwoLines<Entry<ArrayList<ItemCrystal>, Class<? extends Console>>> {
	
	// ####################################################################################################
	// Lang constants :
	// ####################################################################################################
	
	public static final String INV_NAME = "container.defaultConsole";
	
	// ####################################################################################################
	// Interface fields :
	// ####################################################################################################
	
	protected Panel panel_list;
	protected Panel panel_info;
	protected Panel panel_information;
	protected Panel panel_description;
	
	protected Label label_console;
	protected Label[] crystalsLabels = new Label[NB_CRYSTALS];
	
	protected SelectionListConsole selectionList;
	protected ScrollableText scrollableText;
	
	// ####################################################################################################
	// Data fields :
	// ####################################################################################################
	
	protected List<Entry<ArrayList<ItemCrystal>, Class<? extends Console>>> consoles = Console.getValidCristalSets();
	protected Entry<ArrayList<ItemCrystal>, Class<? extends Console>> selectedConsole = null;
	
	protected TextProvider textProvider;
	
	// ####################################################################################################
	// Constructors :
	// ####################################################################################################
	
	public GuiDefaultConsole(TileEntityConsoleBase tileEntity, EntityPlayer player, ConsoleDefault console) {
		super(tileEntity, player, console);
	}
	
	// ####################################################################################################
	// Interface definition :
	// ####################################################################################################
	
	@Override
	protected void drawBackground(int par1, int par2, float par3) {
		super.drawBackground(par1, par2, par3);
		this.panel_list.drawBox(LIGHT_BLUE, BACKGROUND_COLOR);
		this.panel_information.drawBox(LIGHT_BLUE, BACKGROUND_COLOR);
		this.panel_description.drawBox(LIGHT_BLUE, BACKGROUND_COLOR);
	}
	
	@Override
	protected void drawForeground(int par1, int par2) {
		super.drawForeground(par1, par2);
		this.selectionList.drawList(par1, par2);
		this.scrollableText.drawList(par1, par2);
	}
	
	@Override
	protected void initComponents() {
		super.initComponents();
		
		// Panel sizes :
		
		int panelWidth = (this.width - (3 * PANEL_MARGIN)) / 2;
		int panelHeight = this.height - (2 * PANEL_MARGIN);
		int panelHeight_information = (7 * MARGIN) + (6 * FIELD_HEIGHT);
		
		// Panels :
		
		this.panel_list = new Panel(this, PANEL_MARGIN, PANEL_MARGIN, panelWidth, panelHeight);
		this.panel_info = new Panel(this, this.panel_list.getRight() + PANEL_MARGIN, PANEL_MARGIN, panelWidth, panelHeight);
		
		this.panel_information = new Panel(this.panel_info, 0, 0, this.panel_info.getComponentWidth(), panelHeight_information);
		this.panel_description = new Panel(this.panel_info, 0, this.panel_information.getBottom() + PANEL_MARGIN, this.panel_info.getComponentWidth(), this.panel_info.getComponentHeight() - this.panel_information.getBottom() - PANEL_MARGIN);
		
		// Component sizes :
		
		int labelWidth = this.panel_information.getComponentWidth() - (2 * MARGIN);
		
		int listMargin = 2;
		int scrollableTextWidth = this.panel_description.getComponentWidth() - (2 * listMargin);
		int scrollableTextHeight = this.panel_description.getComponentHeight() - (2 * listMargin);
		
		// Components :
		
		this.nextYPos = MARGIN;
		this.label_console = this.addComponent(new Label(this.panel_information, this.fontRenderer, MARGIN, this.nextYPos, labelWidth, ""));
		
		for(int i = 0; i < NB_CRYSTALS; i++) {
			this.crystalsLabels[i] = this.addComponent(new Label(this.panel_information, this.fontRenderer, MARGIN, this.nextYPos, labelWidth, "", BLUE));
		}
		
		// List :
		
		this.selectionList = new SelectionListConsole(this.panel_list, listMargin, listMargin, this.panel_list.getComponentWidth() - (2 * listMargin), this.panel_list.getComponentHeight() - (2 * listMargin), this.mc, this, this.mc.thePlayer);
		
		// Scrollable text :
		
		this.textProvider = new TextProvider(this.fontRenderer);
		this.scrollableText = new ScrollableText(this.panel_description, listMargin, listMargin, scrollableTextWidth, scrollableTextHeight, this.mc, this.textProvider, GRAY);
		this.textProvider.setWidth(this.scrollableText.getContentWidth() - (2 * MARGIN));
	}
	
	// ####################################################################################################
	// User input :
	// ####################################################################################################
	
	@Override
	public void handleMouseInput() {
		super.handleMouseInput();
		
		int mouseX = getMouseXFromEvent();
		int mouseY = getMouseYFromEvent();
		
		this.selectionList.handleMouseInput(mouseX, mouseY);
		this.scrollableText.handleMouseInput(mouseX, mouseY);
	}
	
	// ####################################################################################################
	// Utility :
	// ####################################################################################################
	
	protected void updateInfo() {
		if(this.selectedConsole != null) {
			List<ItemCrystal> crystals = this.selectedConsole.getKey();
			Class<? extends Console> console = this.selectedConsole.getValue();
			
			this.label_console.setText(Console.getTranslatedConsoleName(console));
			
			for(int i = 0; i < NB_CRYSTALS; i++) {
				this.crystalsLabels[i].setText((i < crystals.size()) ? crystals.get(i).getStatName() : "-");
			}
			
			this.textProvider.setText(Console.getTranslatedConsoleInfo(console));
		}
		else {
			this.label_console.setText("");
			
			for(int i = 0; i < NB_CRYSTALS; i++) {
				this.crystalsLabels[i].setText("");
			}
			
			this.textProvider.setText("");
		}
	}
	
	// ####################################################################################################
	// ListProvider interface :
	// ####################################################################################################
	
	@Override
	public List<Entry<ArrayList<ItemCrystal>, Class<? extends Console>>> getList() {
		return this.consoles;
	}
	
	@Override
	public int getSelectedIndex() {
		return this.consoles.indexOf(this.selectedConsole);
	}
	
	@Override
	public void setSelectedIndex(int index) {
		if(index >= 0 && index < this.consoles.size()) {
			this.setSelectedConsole(this.consoles.get(index));
		}
		else {
			this.setSelectedConsole(null);
		}
	}
	
	protected void setSelectedConsole(Entry<ArrayList<ItemCrystal>, Class<? extends Console>> console) {
		this.selectedConsole = console;
		this.updateInfo();
	}
	
	@Override
	public void onElementDoubleClicked() {
		// Nothing here.
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
