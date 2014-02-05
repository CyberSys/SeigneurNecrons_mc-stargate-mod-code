package seigneurnecron.minecraftmods.stargate.gui;

import static seigneurnecron.minecraftmods.core.gui.GuiConstants.BACKGROUND_COLOR;
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
import net.minecraft.item.Item;
import seigneurnecron.minecraftmods.core.gui.Label;
import seigneurnecron.minecraftmods.core.gui.ListProviderSelectTwoLines;
import seigneurnecron.minecraftmods.core.gui.Panel;
import seigneurnecron.minecraftmods.stargate.gui.components.SelectionListFireball;
import seigneurnecron.minecraftmods.stargate.gui.components.StargateButton;
import seigneurnecron.minecraftmods.stargate.inventory.ContainerFireballFactory;
import seigneurnecron.minecraftmods.stargate.inventory.InventoryFireballFactory;
import seigneurnecron.minecraftmods.stargate.item.fireball.ItemFireballBasic;

/**
 * @author Seigneur Necron
 */
public class GuiFireballFactory extends GuiContainerConsolePanel<ContainerFireballFactory> implements ListProviderSelectTwoLines<Item> {
	
	// ####################################################################################################
	// Lang constants :
	// ####################################################################################################
	
	public static final String TRANFORM = InventoryFireballFactory.INV_NAME + ".transform";
	public static final String INSERT_FIREBALL = InventoryFireballFactory.INV_NAME + ".insertFireball";
	public static final String FIREBALL_READY = InventoryFireballFactory.INV_NAME + ".fireballReady";
	
	// ####################################################################################################
	// Interface fields :
	// ####################################################################################################
	
	protected String string_insertFireball;
	protected String string_fireballReady;
	
	protected Panel panel_list;
	protected Panel panel_controls;
	protected Panel panel_info;
	
	protected Label label_fireball;
	
	protected StargateButton button_transform;
	
	protected SelectionListFireball selectionList;
	
	// ####################################################################################################
	// Data fields :
	// ####################################################################################################
	
	protected List<Item> fireballs = new ArrayList<Item>();
	protected Item selectedFireball = null;
	
	// ####################################################################################################
	// Constructors :
	// ####################################################################################################
	
	public GuiFireballFactory(ContainerFireballFactory container) {
		super(container);
	}
	
	// ####################################################################################################
	// Interface definition :
	// ####################################################################################################
	
	@Override
	protected void updateComponents() {
		super.updateComponents();
		
		this.fireballs = ItemFireballBasic.getCraftableFireballs();
		boolean fireballOk = this.container.inventory.isFireballValid();
		
		if(fireballOk) {
			Item insertedFireball = this.container.inventory.getFireball().getItem();
			this.fireballs.remove(insertedFireball);
			
			if(this.selectedFireball == insertedFireball) {
				this.setSelectedFireball(null);
			}
		}
		
		this.label_fireball.setText(fireballOk ? this.string_fireballReady : this.string_insertFireball, fireballOk ? GREEN : RED);
		this.button_transform.enabled = fireballOk && (this.selectedFireball != null);
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
		
		this.string_insertFireball = I18n.getString(INSERT_FIREBALL);
		this.string_fireballReady = I18n.getString(FIREBALL_READY);
		
		// Component sizes :
		
		int labelWidth = this.panel_info.getComponentWidth() - (2 * MARGIN);
		int listMargin = 2;
		int listWidth = this.panel_list.getComponentWidth() - (2 * listMargin);
		int listHeight = this.panel_list.getComponentHeight() - (2 * listMargin);
		
		// Components :
		
		this.nextYPos = MARGIN;
		this.label_fireball = this.addComponent(new Label(this.panel_info, this.fontRenderer, MARGIN, this.nextYPos, labelWidth, ""));
		this.button_transform = this.addComponent(new StargateButton(this.panel_info, MARGIN, this.nextYPos, labelWidth, I18n.getString(TRANFORM)));
		
		// List :
		
		this.selectionList = this.addComponent(new SelectionListFireball(this.panel_list, listMargin, listMargin, listWidth, listHeight, this.mc, this, this.mc.thePlayer));
	}
	
	// ####################################################################################################
	// User input :
	// ####################################################################################################
	
	@Override
	protected void actionPerformed(GuiButton guiButton) {
		if(guiButton.enabled) {
			if(guiButton == this.button_transform) {
				this.transform();
			}
		}
	}
	
	protected void transform() {
		if(this.button_transform.enabled) {
			this.mc.playerController.sendEnchantPacket(this.inventorySlots.windowId, ItemFireballBasic.getCraftableFireballs().indexOf(this.selectedFireball));
		}
	}
	
	// ####################################################################################################
	// ListProvider interface :
	// ####################################################################################################
	
	@Override
	public List<Item> getList() {
		return this.fireballs;
	}
	
	@Override
	public int getSelectedIndex() {
		return this.getList().indexOf(this.selectedFireball);
	}
	
	@Override
	public void setSelectedIndex(int index) {
		if(index >= 0 && index < this.getList().size()) {
			this.setSelectedFireball(this.getList().get(index));
		}
		else {
			this.setSelectedFireball(null);
		}
	}
	
	protected void setSelectedFireball(Item fireball) {
		this.selectedFireball = fireball;
	}
	
	@Override
	public void onElementDoubleClicked() {
		this.transform();
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
