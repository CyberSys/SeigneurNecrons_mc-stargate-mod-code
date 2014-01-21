package seigneurnecron.minecraftmods.stargate.gui;

import static seigneurnecron.minecraftmods.core.gui.GuiConstants.BACKGROUND_COLOR;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.GRAY;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.GREEN;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.LIGHT_BLUE;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.MARGIN;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.PANEL_MARGIN;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.RED;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import seigneurnecron.minecraftmods.core.gui.Label;
import seigneurnecron.minecraftmods.core.gui.Panel;
import seigneurnecron.minecraftmods.core.gui.ScrollableText;
import seigneurnecron.minecraftmods.core.gui.TextProvider;
import seigneurnecron.minecraftmods.stargate.gui.components.StargateButton;
import seigneurnecron.minecraftmods.stargate.inventory.ContainerStargateFactory;
import seigneurnecron.minecraftmods.stargate.inventory.InventoryStargateFactory;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class GuiStargateFactory extends GuiContainerConsolePanel<ContainerStargateFactory> {
	
	// ####################################################################################################
	// Lang constants :
	// ####################################################################################################
	
	public static final String INFO = InventoryStargateFactory.INV_NAME + ".info";
	public static final String CREATE = InventoryStargateFactory.INV_NAME + ".create";
	public static final String MATERIALS_MISSING = InventoryStargateFactory.INV_NAME + ".materialsMissing";
	public static final String MATERIALS_READY = InventoryStargateFactory.INV_NAME + ".materialsReady";
	
	// ####################################################################################################
	// Interface fields :
	// ####################################################################################################
	
	protected String string_materialsMissing;
	protected String string_materialsReady;
	
	protected Panel panel_text;
	protected Panel panel_controls;
	protected Panel panel_info;
	
	protected Label label_materials;
	protected Label label_arrow;
	
	protected StargateButton button_create;
	
	protected ScrollableText scrollableText;
	
	// ####################################################################################################
	// Data fields :
	// ####################################################################################################
	
	protected TextProvider textProvider;
	
	// ####################################################################################################
	// Constructors :
	// ####################################################################################################
	
	public GuiStargateFactory(ContainerStargateFactory container) {
		super(container);
	}
	
	// ####################################################################################################
	// Interface definition :
	// ####################################################################################################
	
	@Override
	protected void updateComponents() {
		super.updateComponents();
		
		boolean ready = this.container.inventory.isReadyToCraft();
		
		this.label_materials.setText(ready ? this.string_materialsReady : this.string_materialsMissing, ready ? GREEN : RED);
		this.button_create.enabled = ready;
	}
	
	@Override
	protected void drawBackground(int mouseX, int mouseY, float timeSinceLastTick) {
		super.drawBackground(mouseX, mouseY, timeSinceLastTick);
		this.panel_text.drawBox(LIGHT_BLUE, BACKGROUND_COLOR);
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
		
		this.panel_text = new Panel(this, PANEL_MARGIN, PANEL_MARGIN, panelWidth_list, panelHeight);
		this.panel_controls = new Panel(this, this.panel_text.getRight() + PANEL_MARGIN, PANEL_MARGIN, panelWidth_controls, panelHeight);
		
		this.panel_info = new Panel(this.panel_controls, 0, 0, this.panel_controls.getComponentWidth(), panelHeight_information);
		this.panel_main = new Panel(this.panel_controls, 0, this.panel_info.getBottom() + PANEL_MARGIN, this.panel_controls.getComponentWidth(), this.container.mainPanelHeight());
		
		// Strings :
		
		this.string_materialsMissing = I18n.getString(MATERIALS_MISSING);
		this.string_materialsReady = I18n.getString(MATERIALS_READY);
		
		// Component sizes :
		
		int labelWidth = this.panel_info.getComponentWidth() - (2 * MARGIN);
		
		int listMargin = 2;
		int scrollableTextWidth = this.panel_text.getComponentWidth() - (2 * listMargin);
		int scrollableTextHeight = this.panel_text.getComponentHeight() - (2 * listMargin);
		
		// Components :
		
		this.nextYPos = MARGIN;
		this.label_materials = this.addComponent(new Label(this.panel_info, this.fontRenderer, MARGIN, this.nextYPos, labelWidth, ""));
		this.button_create = this.addComponent(new StargateButton(this.panel_info, MARGIN, this.nextYPos, labelWidth, I18n.getString(CREATE)));
		
		// Scrollable text :
		
		this.textProvider = new TextProvider(this.fontRenderer);
		this.scrollableText = this.addComponent(new ScrollableText(this.panel_text, listMargin, listMargin, scrollableTextWidth, scrollableTextHeight, this.mc, this.textProvider, GRAY));
		this.textProvider.update(I18n.getString(INFO), this.scrollableText.getContentWidth() - (2 * MARGIN));
	}
	
	@Override
	protected void initContainerInventory() {
		super.initContainerInventory();
		
		int inventorySize = this.container.inventory.getSafeSizeInventory();
		int nbNormalSlots = this.container.inventory.getSafeNbNormalSlots();
		int slotSizePlusMargin = this.container.slotSizePlusMargin();
		int nbSlots = 9 - inventorySize;
		int labelHeight = this.container.slotSizeWithBorder();
		int labelWidth = (nbSlots * slotSizePlusMargin) - this.container.slotMargin();
		int labelXPos = this.container.firstSlotXPos() + (nbNormalSlots * slotSizePlusMargin);
		int labelYPos = this.container.firstSlotYPos();
		
		this.label_arrow = this.addComponent(new Label(this.panel_container, this.fontRenderer, labelXPos, labelYPos, labelWidth, labelHeight, "->", true));
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
			this.mc.playerController.sendEnchantPacket(this.inventorySlots.windowId, 0);
		}
	}
	
}
