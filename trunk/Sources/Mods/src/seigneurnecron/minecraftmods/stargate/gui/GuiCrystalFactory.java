package seigneurnecron.minecraftmods.stargate.gui;

import static seigneurnecron.minecraftmods.core.gui.GuiConstants.BACKGROUND_COLOR;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.GRAY;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.GREEN;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.LIGHT_BLUE;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.MARGIN;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.PANEL_MARGIN;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.RED;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import seigneurnecron.minecraftmods.core.gui.GuiContainerOneLine;
import seigneurnecron.minecraftmods.core.gui.Label;
import seigneurnecron.minecraftmods.core.gui.ListProviderSelectTwoLines;
import seigneurnecron.minecraftmods.core.gui.Panel;
import seigneurnecron.minecraftmods.core.gui.ScrollableText;
import seigneurnecron.minecraftmods.core.gui.TextProvider;
import seigneurnecron.minecraftmods.stargate.gui.components.SelectionListItemCrystal;
import seigneurnecron.minecraftmods.stargate.gui.components.StargateButton;
import seigneurnecron.minecraftmods.stargate.inventory.ContainerCrystalFactory;
import seigneurnecron.minecraftmods.stargate.inventory.InventoryCrystalFactory;
import seigneurnecron.minecraftmods.stargate.item.ItemCrystal;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class GuiCrystalFactory extends GuiContainerOneLine<ContainerCrystalFactory> implements ListProviderSelectTwoLines<ItemCrystal> {
	
	// ####################################################################################################
	// Lang constants :
	// ####################################################################################################
	
	public static final String CREATE = InventoryCrystalFactory.INV_NAME + ".create";
	public static final String INSERT_CRYSTAL = InventoryCrystalFactory.INV_NAME + ".insertCrystal";
	public static final String CRYSTAL_READY = InventoryCrystalFactory.INV_NAME + ".crystalReady";
	
	// ####################################################################################################
	// Interface fields :
	// ####################################################################################################
	
	protected String string_insertCrystal;
	protected String string_crystalReady;
	
	protected Panel panel_list;
	protected Panel panel_controls;
	protected Panel panel_listSelect;
	protected Panel panel_info;
	protected Panel panel_button;
	
	protected Label label_crystal;
	
	protected StargateButton button_create;
	
	protected SelectionListItemCrystal selectionList;
	protected ScrollableText scrollableText;
	
	// ####################################################################################################
	// Data fields :
	// ####################################################################################################
	
	protected List<ItemCrystal> crystals = new ArrayList<ItemCrystal>();
	protected ItemCrystal selectedCrystal = null;
	
	protected TextProvider textProvider;
	
	// ####################################################################################################
	// Constructors :
	// ####################################################################################################
	
	public GuiCrystalFactory(ContainerCrystalFactory container) {
		super(container);
	}
	
	// ####################################################################################################
	// Interface definition :
	// ####################################################################################################
	
	@Override
	protected void updateComponents() {
		super.updateComponents();
		
		this.crystals = ItemCrystal.getCraftableCristals();
		boolean crystalValid = this.container.inventory.isCrystalValid();
		
		if(crystalValid) {
			ItemCrystal insertedCrystal = (ItemCrystal) this.container.inventory.getCrystal().getItem();
			this.crystals.remove(insertedCrystal);
			
			if(this.selectedCrystal == insertedCrystal) {
				this.setSelectedCrystal(null);
			}
		}
		
		this.label_crystal.setText(crystalValid ? this.string_crystalReady : this.string_insertCrystal, crystalValid ? GREEN : RED);
		this.button_create.enabled = crystalValid && (this.selectedCrystal != null);
	}
	
	@Override
	protected void drawBackground(int mouseX, int mouseY, float timeSinceLastTick) {
		super.drawBackground(mouseX, mouseY, timeSinceLastTick);
		this.panel_listSelect.drawBox(LIGHT_BLUE, BACKGROUND_COLOR);
		this.panel_info.drawBox(LIGHT_BLUE, BACKGROUND_COLOR);
		this.panel_button.drawBox(LIGHT_BLUE, BACKGROUND_COLOR);
	}
	
	@Override
	protected void initComponents() {
		// Panel sizes :
		
		int panelWidth_controls = this.container.mainPanelWidth();
		int panelWidth_list = (this.width - panelWidth_controls - (3 * PANEL_MARGIN));
		int panelHeight = this.height - (2 * PANEL_MARGIN);
		int panelHeight_button = panelHeight - this.container.mainPanelHeight() - PANEL_MARGIN;
		int panelHeight_listSelect = (int) (panelHeight * 0.75);
		int panelHeight_info = panelHeight - panelHeight_listSelect - PANEL_MARGIN;
		
		// Panels :
		
		this.panel_list = new Panel(this, PANEL_MARGIN, PANEL_MARGIN, panelWidth_list, panelHeight);
		this.panel_controls = new Panel(this, this.panel_list.getRight() + PANEL_MARGIN, PANEL_MARGIN, panelWidth_controls, panelHeight);
		
		this.panel_listSelect = new Panel(this.panel_list, 0, 0, panelWidth_list, panelHeight_listSelect);
		this.panel_info = new Panel(this.panel_list, 0, this.panel_listSelect.getBottom() + PANEL_MARGIN, panelWidth_list, panelHeight_info);
		
		this.panel_button = new Panel(this.panel_controls, 0, 0, panelWidth_controls, panelHeight_button);
		this.panel_main = new Panel(this.panel_controls, 0, this.panel_button.getBottom() + PANEL_MARGIN, panelWidth_controls, this.container.mainPanelHeight());
		
		// Strings :
		
		this.string_insertCrystal = I18n.getString(INSERT_CRYSTAL);
		this.string_crystalReady = I18n.getString(CRYSTAL_READY);
		
		// Component sizes :
		
		int labelWidth = this.panel_button.getComponentWidth() - (2 * MARGIN);
		int listMargin = 2;
		int listWidth = this.panel_listSelect.getComponentWidth() - (2 * listMargin);
		int listHeight = this.panel_listSelect.getComponentHeight() - (2 * listMargin);
		int scrollableTextWidth = this.panel_info.getComponentWidth() - (2 * listMargin);
		int scrollableTextHeight = this.panel_info.getComponentHeight() - (2 * listMargin);
		
		// Components :
		
		this.nextYPos = MARGIN;
		this.label_crystal = this.addComponent(new Label(this.panel_button, this.fontRenderer, MARGIN, this.nextYPos, labelWidth, ""));
		this.button_create = this.addComponent(new StargateButton(this.panel_button, MARGIN, this.nextYPos, labelWidth, I18n.getString(CREATE)));
		
		// List :
		
		this.selectionList = this.addComponent(new SelectionListItemCrystal(this.panel_listSelect, listMargin, listMargin, listWidth, listHeight, this.mc, this, this.mc.thePlayer));
		
		// Scrollable text :
		
		this.textProvider = new TextProvider(this.fontRenderer);
		this.scrollableText = this.addComponent(new ScrollableText(this.panel_info, listMargin, listMargin, scrollableTextWidth, scrollableTextHeight, this.mc, this.textProvider, GRAY));
		this.textProvider.setWidth(this.scrollableText.getContentWidth() - (2 * MARGIN));
		
		this.updateInfo();
	}
	
	// ####################################################################################################
	// User input :
	// ####################################################################################################
	
	@Override
	protected void actionPerformed(GuiButton guiButton) {
		if(guiButton.enabled) {
			if(guiButton == this.button_create) {
				this.create();
			}
		}
	}
	
	protected void create() {
		if(this.button_create.enabled) {
			this.mc.playerController.sendEnchantPacket(this.inventorySlots.windowId, ItemCrystal.getCraftableCristals().indexOf(this.selectedCrystal));
		}
	}
	
	// ####################################################################################################
	// Utility :
	// ####################################################################################################
	
	protected void updateInfo() {
		this.textProvider.setText((this.selectedCrystal != null) ? this.selectedCrystal.getTranslatedCrystalInfo() : "");
	}
	
	// ####################################################################################################
	// ListProvider interface :
	// ####################################################################################################
	
	@Override
	public List<ItemCrystal> getList() {
		return this.crystals;
	}
	
	@Override
	public int getSelectedIndex() {
		return this.getList().indexOf(this.selectedCrystal);
	}
	
	@Override
	public void setSelectedIndex(int index) {
		if(index >= 0 && index < this.getList().size()) {
			this.setSelectedCrystal(this.getList().get(index));
		}
		else {
			this.setSelectedCrystal(null);
		}
	}
	
	protected void setSelectedCrystal(ItemCrystal crystal) {
		this.selectedCrystal = crystal;
		this.updateInfo();
	}
	
	@Override
	public void onElementDoubleClicked() {
		this.create();
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
