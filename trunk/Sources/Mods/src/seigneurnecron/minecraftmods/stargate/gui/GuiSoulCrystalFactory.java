package seigneurnecron.minecraftmods.stargate.gui;

import static seigneurnecron.minecraftmods.core.gui.GuiConstants.BACKGROUND_COLOR;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.GREEN;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.LIGHT_BLUE;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.MARGIN;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.PANEL_MARGIN;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.RED;

import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import seigneurnecron.minecraftmods.core.gui.Label;
import seigneurnecron.minecraftmods.core.gui.ListProviderSelectTwoLines;
import seigneurnecron.minecraftmods.core.gui.Panel;
import seigneurnecron.minecraftmods.stargate.gui.components.SelectionListSoul;
import seigneurnecron.minecraftmods.stargate.gui.components.StargateButton;
import seigneurnecron.minecraftmods.stargate.inventory.ContainerSoulCrystalFactory;
import seigneurnecron.minecraftmods.stargate.inventory.InventoryStuffLevelUpTable;
import seigneurnecron.minecraftmods.stargate.tools.enchant.EnchantmentTools;
import seigneurnecron.minecraftmods.stargate.tools.loadable.SoulCount;
import seigneurnecron.minecraftmods.stargate.tools.playerdata.PlayerSoulCountData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class GuiSoulCrystalFactory extends GuiContainerConsolePanel<ContainerSoulCrystalFactory> implements ListProviderSelectTwoLines<SoulCount> {
	
	// ####################################################################################################
	// Lang constants :
	// ####################################################################################################
	
	public static final String CREATE = InventoryStuffLevelUpTable.INV_NAME + ".create";
	public static final String SOULS = InventoryStuffLevelUpTable.INV_NAME + ".souls";
	public static final String INSERT_CRYSTAL = InventoryStuffLevelUpTable.INV_NAME + ".insertCrystal";
	public static final String CYSTAL_READY = InventoryStuffLevelUpTable.INV_NAME + ".crystalReady";
	
	// ####################################################################################################
	// Interface fields :
	// ####################################################################################################
	
	protected String string_insertCrystal;
	protected String string_crystalReady;
	
	protected Panel panel_list;
	protected Panel panel_controls;
	protected Panel panel_info;
	
	protected Label label_crystal;
	
	protected StargateButton button_create;
	
	protected SelectionListSoul selectionList;
	
	// ####################################################################################################
	// Data fields :
	// ####################################################################################################
	
	protected SoulCount selectedSoulCount = null;
	
	protected PlayerSoulCountData playerData;
	
	// ####################################################################################################
	// Constructors :
	// ####################################################################################################
	
	public GuiSoulCrystalFactory(ContainerSoulCrystalFactory container) {
		super(container);
		this.playerData = PlayerSoulCountData.get(this.container.player);
	}
	
	// ####################################################################################################
	// Interface definition :
	// ####################################################################################################
	
	@Override
	protected void updateComponents() {
		super.updateComponents();
		
		boolean crystalOk = this.container.inventory.isCrystalValid();
		
		this.label_crystal.setText(crystalOk ? this.string_crystalReady : this.string_insertCrystal, crystalOk ? GREEN : RED);
		this.button_create.enabled = crystalOk && EnchantmentTools.hasEnoughtSoul(this.container.player, this.selectedSoulCount);
	}
	
	@Override
	protected void drawBackground(int par1, int par2, float par3) {
		super.drawBackground(par1, par2, par3);
		this.panel_list.drawBox(LIGHT_BLUE, BACKGROUND_COLOR);
		this.panel_info.drawBox(LIGHT_BLUE, BACKGROUND_COLOR);
	}
	
	@Override
	protected void drawForeground(int par1, int par2) {
		super.drawForeground(par1, par2);
		this.selectionList.drawList(par1, par2);
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
		
		this.string_insertCrystal = I18n.getString(INSERT_CRYSTAL);
		this.string_crystalReady = I18n.getString(CYSTAL_READY);
		
		// Component sizes :
		
		int labelWidth = this.panel_info.getComponentWidth() - (2 * MARGIN);
		int listMargin = 2;
		
		// Components :
		
		this.nextYPos = MARGIN;
		this.label_crystal = this.addComponent(new Label(this.panel_info, this.fontRenderer, MARGIN, this.nextYPos, labelWidth, ""));
		this.button_create = this.addComponent(new StargateButton(this.panel_info, MARGIN, this.nextYPos, labelWidth, I18n.getString(CREATE)));
		
		// List :
		
		this.selectionList = new SelectionListSoul(this.panel_list, listMargin, listMargin, this.panel_list.getComponentWidth() - (2 * listMargin), this.panel_list.getComponentHeight() - (2 * listMargin), this.mc, this, this.mc.thePlayer);
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
	
	@Override
	public void handleMouseInput() {
		super.handleMouseInput();
		this.selectionList.handleMouseInput(getMouseXFromEvent(), getMouseYFromEvent());
	}
	
	protected void create() {
		if(this.button_create.enabled) {
			this.mc.playerController.sendEnchantPacket(this.inventorySlots.windowId, this.selectedSoulCount.id);
		}
	}
	
	// ####################################################################################################
	// ListProvider interface :
	// ####################################################################################################
	
	@Override
	public List<SoulCount> getList() {
		return this.playerData.getDataList();
	}
	
	@Override
	public int getSelectedIndex() {
		return this.getList().indexOf(this.selectedSoulCount);
	}
	
	@Override
	public void setSelectedIndex(int index) {
		if(index >= 0 && index < this.getList().size()) {
			this.setSelectedSoulCount(this.getList().get(index));
		}
		else {
			this.setSelectedSoulCount(null);
		}
	}
	
	protected void setSelectedSoulCount(SoulCount soulCount) {
		this.selectedSoulCount = soulCount;
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
