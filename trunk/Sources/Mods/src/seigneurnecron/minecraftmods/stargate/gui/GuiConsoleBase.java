package seigneurnecron.minecraftmods.stargate.gui;

import static seigneurnecron.minecraftmods.core.gui.GuiConstants.BACKGROUND_COLOR;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.LIGHT_BLUE;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.MARGIN;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.PANEL_MARGIN;

import java.util.List;

import net.minecraft.client.resources.I18n;
import seigneurnecron.minecraftmods.core.gui.GuiContainerOneLine;
import seigneurnecron.minecraftmods.core.gui.Label;
import seigneurnecron.minecraftmods.core.gui.Panel;
import seigneurnecron.minecraftmods.stargate.inventory.ContainerConsoleBase;
import seigneurnecron.minecraftmods.stargate.inventory.InventoryConsoleBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
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
	protected void drawBackground(int par1, int par2, float par3) {
		super.drawBackground(par1, par2, par3);
		this.panel_info.drawBox(LIGHT_BLUE, BACKGROUND_COLOR);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	protected void initComponents() {
		// Component sizes :
		
		int panelWidth_main = this.container.mainPanelWidth();
		int panelHeight = this.container.mainPanelHeight();
		
		int totalWidth = (int) (this.width * 0.9);
		
		int panelWidth_info = totalWidth - panelWidth_main - PANEL_MARGIN;
		int labelWidth = panelWidth_info - (2 * MARGIN);
		
		List<String> strings = this.fontRenderer.listFormattedStringToWidth(I18n.func_135053_a(INFO), labelWidth);
		
		int panelYPos = (this.height - panelHeight) / 2;
		int panelXPos_info = (this.width - totalWidth) / 2;
		
		// Panels :
		
		this.panel_info = new Panel(this, panelXPos_info, panelYPos, panelWidth_info, panelHeight);
		this.panel_main = new Panel(this, this.panel_info.getRight() + PANEL_MARGIN, panelYPos, panelWidth_main, panelHeight);
		
		// Labels :
		
		this.nextYPos = MARGIN;
		
		for(String string : strings) {
			this.addComponent(new Label(this.panel_info, this.fontRenderer, MARGIN, this.nextYPos, labelWidth, string));
		}
	}
	
}
