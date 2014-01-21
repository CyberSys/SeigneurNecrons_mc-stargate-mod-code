package seigneurnecron.minecraftmods.stargate.gui;

import static seigneurnecron.minecraftmods.core.gui.GuiConstants.BACKGROUND_COLOR;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.GREEN;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.LIGHT_BLUE;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.MARGIN;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.PANEL_MARGIN;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.RED;
import static seigneurnecron.minecraftmods.stargate.tools.enchant.EnchantmentTools.MIN_NB_BOOKS;

import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import seigneurnecron.minecraftmods.core.gui.Label;
import seigneurnecron.minecraftmods.core.gui.ListProviderSelectTwoLines;
import seigneurnecron.minecraftmods.core.gui.Panel;
import seigneurnecron.minecraftmods.stargate.gui.components.SelectionListPowerUp;
import seigneurnecron.minecraftmods.stargate.gui.components.StargateButton;
import seigneurnecron.minecraftmods.stargate.inventory.ContainerStuffLevelUpTable;
import seigneurnecron.minecraftmods.stargate.inventory.InventoryStuffLevelUpTable;
import seigneurnecron.minecraftmods.stargate.tools.enchant.EnchantmentTools;
import seigneurnecron.minecraftmods.stargate.tools.loadable.PowerUp;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class GuiStuffLevelUpTable extends GuiContainerConsolePanel<ContainerStuffLevelUpTable> implements ListProviderSelectTwoLines<PowerUp> {
	
	// ####################################################################################################
	// Lang constants :
	// ####################################################################################################
	
	public static final String MAGIC_POWER = InventoryStuffLevelUpTable.INV_NAME + ".magicPower";
	public static final String COST = InventoryStuffLevelUpTable.INV_NAME + ".cost";
	public static final String LEVELS = InventoryStuffLevelUpTable.INV_NAME + ".levels";
	public static final String ENCHANT = InventoryStuffLevelUpTable.INV_NAME + ".enchant";
	
	// ####################################################################################################
	// Interface fields :
	// ####################################################################################################
	
	protected String string_magicPower;
	
	protected Panel panel_list;
	protected Panel panel_controls;
	protected Panel panel_info;
	
	protected Label label_magicPower;
	
	protected StargateButton button_enchant;
	
	protected SelectionListPowerUp selectionList;
	
	// ####################################################################################################
	// Data fields :
	// ####################################################################################################
	
	protected PowerUp selectedPowerUp = null;
	
	// ####################################################################################################
	// Constructors :
	// ####################################################################################################
	
	public GuiStuffLevelUpTable(ContainerStuffLevelUpTable container) {
		super(container);
	}
	
	// ####################################################################################################
	// Interface definition :
	// ####################################################################################################
	
	@Override
	protected void updateComponents() {
		super.updateComponents();
		
		if(!this.getList().contains(this.selectedPowerUp)) {
			this.selectedPowerUp = null;
		}
		
		int nbBooks = this.container.inventory.console.getNbBooks();
		boolean booksOk = EnchantmentTools.isThereEnoughtBookCase(this.container.player, nbBooks);
		boolean xpOk = EnchantmentTools.canPayEnchantCost(this.container.player, this.selectedPowerUp);
		
		this.label_magicPower.setText(this.string_magicPower + (booksOk ? MIN_NB_BOOKS : nbBooks) + " / " + MIN_NB_BOOKS, booksOk ? GREEN : RED);
		this.button_enchant.enabled = booksOk && xpOk;
	}
	
	@Override
	protected void drawBackground(int mouseX, int mouseY, float timeSinceLastTick) {
		super.drawBackground(mouseX, mouseY, timeSinceLastTick);
		this.panel_list.drawBox(LIGHT_BLUE, BACKGROUND_COLOR);
		this.panel_info.drawBox(LIGHT_BLUE, BACKGROUND_COLOR);
	}
	
	@Override
	protected void initComponents() {
		// Panel sizes :
		
		int panelWidth_controls = this.container.mainPanelWidth();
		int panelWidth_list = (this.width - panelWidth_controls - (3 * PANEL_MARGIN));
		int panelHeight = this.height - (2 * PANEL_MARGIN);
		int panelHeight_information = panelHeight - this.container.mainPanelHeight() - PANEL_MARGIN;
		
		// Panels :
		
		this.panel_list = new Panel(this, PANEL_MARGIN, PANEL_MARGIN, panelWidth_list, panelHeight);
		this.panel_controls = new Panel(this, this.panel_list.getRight() + PANEL_MARGIN, PANEL_MARGIN, panelWidth_controls, panelHeight);
		
		this.panel_info = new Panel(this.panel_controls, 0, 0, this.panel_controls.getComponentWidth(), panelHeight_information);
		this.panel_main = new Panel(this.panel_controls, 0, this.panel_info.getBottom() + PANEL_MARGIN, this.panel_controls.getComponentWidth(), this.container.mainPanelHeight());
		
		// Strings :
		
		this.string_magicPower = I18n.getString(MAGIC_POWER) + " : ";
		
		// Component sizes :
		
		int labelWidth = this.panel_info.getComponentWidth() - (2 * MARGIN);
		int listMargin = 2;
		int listWidth = this.panel_list.getComponentWidth() - (2 * listMargin);
		int listHeight = this.panel_list.getComponentHeight() - (2 * listMargin);
		
		// Components :
		
		this.nextYPos = MARGIN;
		this.label_magicPower = this.addComponent(new Label(this.panel_info, this.fontRenderer, MARGIN, this.nextYPos, labelWidth, ""));
		this.button_enchant = this.addComponent(new StargateButton(this.panel_info, MARGIN, this.nextYPos, labelWidth, I18n.getString(ENCHANT)));
		
		// List :
		
		this.selectionList = this.addComponent(new SelectionListPowerUp(this.panel_list, listMargin, listMargin, listWidth, listHeight, this.mc, this, this.mc.thePlayer));
	}
	
	// ####################################################################################################
	// User input :
	// ####################################################################################################
	
	@Override
	protected void actionPerformed(GuiButton guiButton) {
		if(guiButton.enabled) {
			if(guiButton == this.button_enchant) {
				this.enchant();
			}
		}
	}
	
	protected void enchant() {
		if(this.button_enchant.enabled) {
			this.mc.playerController.sendEnchantPacket(this.inventorySlots.windowId, this.getSelectedIndex());
		}
	}
	
	// ####################################################################################################
	// ListProvider interface :
	// ####################################################################################################
	
	@Override
	public List<PowerUp> getList() {
		return this.container.inventory.console.getEnchantments();
	}
	
	@Override
	public int getSelectedIndex() {
		return this.getList().indexOf(this.selectedPowerUp);
	}
	
	@Override
	public void setSelectedIndex(int index) {
		if(index >= 0 && index < this.getList().size()) {
			this.setSelectedPowerUp(this.getList().get(index));
		}
		else {
			this.setSelectedPowerUp(null);
		}
	}
	
	protected void setSelectedPowerUp(PowerUp powerUp) {
		this.selectedPowerUp = powerUp;
	}
	
	@Override
	public void onElementDoubleClicked() {
		this.enchant();
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
