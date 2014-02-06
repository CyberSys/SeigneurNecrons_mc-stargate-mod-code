package seigneurnecron.minecraftmods.stargate.gui;

import static seigneurnecron.minecraftmods.core.gui.GuiConstants.BACKGROUND_COLOR;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.GRAY;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.LIGHT_BLUE;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.MARGIN;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.PANEL_MARGIN;
import net.minecraft.client.resources.I18n;
import seigneurnecron.minecraftmods.core.gui.GuiContainerOneLine;
import seigneurnecron.minecraftmods.core.gui.Panel;
import seigneurnecron.minecraftmods.core.gui.ScrollableText;
import seigneurnecron.minecraftmods.core.gui.TextProvider;
import seigneurnecron.minecraftmods.stargate.inventory.ContainerConsoleBase;
import seigneurnecron.minecraftmods.stargate.inventory.InventoryConsoleBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class GuiConsoleBase extends GuiContainerOneLine<ContainerConsoleBase> {
	
	// ####################################################################################################
	// Lang constants :
	// ####################################################################################################
	
	public static final String INFO = InventoryConsoleBase.INV_NAME + ".info";
	
	// ####################################################################################################
	// Interface fields :
	// ####################################################################################################
	
	protected Panel panel_info;
	
	protected ScrollableText scrollableText;
	
	// ####################################################################################################
	// Data fields :
	// ####################################################################################################
	
	protected TextProvider textProvider;
	
	// ####################################################################################################
	// Constructors :
	// ####################################################################################################
	
	public GuiConsoleBase(ContainerConsoleBase container) {
		super(container);
	}
	
	// ####################################################################################################
	// Interface definition :
	// ####################################################################################################
	
	@Override
	protected void drawBackground(int mouseX, int mouseY, float timeSinceLastTick) {
		super.drawBackground(mouseX, mouseY, timeSinceLastTick);
		this.panel_info.drawBox(LIGHT_BLUE, BACKGROUND_COLOR);
	}
	
	@Override
	protected void initComponents() {
		// Component sizes :
		
		int panelWidth_main = this.container.mainPanelWidth();
		int panelHeight = this.container.mainPanelHeight();
		
		int totalWidth = (int) (this.width * 0.9);
		
		int panelWidth_info = totalWidth - panelWidth_main - PANEL_MARGIN;
		
		int panelYPos = (this.height - panelHeight) / 2;
		int panelXPos_info = (this.width - totalWidth) / 2;
		
		int listMargin = 2;
		int scrollableTextWidth = panelWidth_info - (2 * listMargin);
		int scrollableTextHeight = panelHeight - (2 * listMargin);
		
		// Panels :
		
		this.panel_info = new Panel(this, panelXPos_info, panelYPos, panelWidth_info, panelHeight);
		this.panel_main = new Panel(this, this.panel_info.getRight() + PANEL_MARGIN, panelYPos, panelWidth_main, panelHeight);
		
		// Scrollable text :
		
		this.textProvider = new TextProvider(this.fontRenderer);
		this.scrollableText = this.addComponent(new ScrollableText(this.panel_info, listMargin, listMargin, scrollableTextWidth, scrollableTextHeight, this.mc, this.textProvider, GRAY));
		this.textProvider.update(I18n.getString(INFO), this.scrollableText.getContentWidth() - (2 * MARGIN));
	}
	
}
