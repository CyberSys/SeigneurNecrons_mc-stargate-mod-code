package seigneurnecron.minecraftmods.stargate.client.gui;

import static seigneurnecron.minecraftmods.stargate.tileentity.TileEntityDetector.INV_NAME;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import seigneurnecron.minecraftmods.stargate.client.gui.tools.Button;
import seigneurnecron.minecraftmods.stargate.client.gui.tools.IntegerField;
import seigneurnecron.minecraftmods.stargate.client.gui.tools.Panel;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityDetector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class GuiDetector extends GuiScreen<TileEntityDetector> {
	
	// ####################################################################################################
	// Lang constants :
	// ####################################################################################################
	
	public static final String RANGE = INV_NAME + ".range";
	public static final String RANGE_LIMITS = INV_NAME + ".rangeLimits";
	public static final String INVERTED_OUTPUT = INV_NAME + ".invertedOutput";
	public static final String NORMAL_OUTPUT = INV_NAME + ".normalOutput";
	public static final String INVERT_BUTTON = INV_NAME + ".invertButton";
	
	// ####################################################################################################
	// Interface fields :
	// ####################################################################################################
	
	protected String string_invName;
	protected String string_range;
	protected String string_rangeLimits;
	protected String string_invertedOutput;
	protected String string_normalOutput;
	
	protected Panel panel_main;
	
	private GuiTextField field_range;
	
	protected Button button_done;
	protected Button button_invert;
	
	// ####################################################################################################
	// Builder :
	// ####################################################################################################
	
	public GuiDetector(TileEntityDetector tileEntity) {
		super(tileEntity);
	}
	
	// ####################################################################################################
	// Interface definition :
	// ####################################################################################################
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
		
		this.panel_main.drawBorder(GRAY);
		
		this.nextYPos = MARGIN;
		this.panel_main.drawCenteredText(this.fontRenderer, this.string_invName, this.nextYPos, WHITE);
		this.panel_main.drawText(this.fontRenderer, this.string_range, MARGIN, this.nextYPos, WHITE);
		this.panel_main.drawText(this.fontRenderer, this.string_rangeLimits, MARGIN, this.nextYPos, WHITE);
		this.panel_main.drawText(this.fontRenderer, this.tileEntity.isInverted() ? this.string_invertedOutput : this.string_normalOutput, MARGIN, this.nextYPos, this.tileEntity.isInverted() ? RED : GREEN);
	}
	
	@Override
	public void initComponents() {
		super.initComponents();
		
		// Panel sizes :
		
		int panelWidth = this.width / 2;
		int panelHeight = (4 * FIELD_HEIGHT) + (2 * BUTTON_HEIGHT) + (7 * MARGIN);
		
		// Panels :
		
		this.panel_main = new Panel(this, (this.width - panelWidth) / 2, (this.height - panelHeight) / 2, panelWidth, panelHeight);
		
		// Strings :
		
		this.string_invName = I18n.func_135053_a(INV_NAME);
		this.string_range = I18n.func_135053_a(RANGE) + " : ";
		this.string_rangeLimits = I18n.func_135053_a(RANGE_LIMITS);
		this.string_invertedOutput = I18n.func_135053_a(INVERTED_OUTPUT);
		this.string_normalOutput = I18n.func_135053_a(NORMAL_OUTPUT);
		
		// Component sizes :
		
		int stringSize = this.fontRenderer.getStringWidth(this.string_range);
		int fieldOffset = stringSize + MARGIN;
		int fieldSize = this.panel_main.getWidth() - (fieldOffset + MARGIN);
		int buttonSize = this.panel_main.getWidth() - (2 * MARGIN);
		
		// Fields and buttons :
		
		this.nextYPos = FIELD_HEIGHT + (2 * MARGIN) + FIELD_OFFSET;
		this.field_range = this.addField(new IntegerField(this.panel_main, this.fontRenderer, fieldOffset, this.nextYPos, fieldSize, FIELD_HEIGHT, this.tileEntity.getRange()));
		
		this.nextYPos += (FIELD_HEIGHT + MARGIN) * 2 - FIELD_OFFSET;
		this.button_invert = this.addButton(new Button(this.panel_main, this.getNextButtonId(), MARGIN, this.nextYPos, buttonSize, I18n.func_135053_a(INVERT_BUTTON)));
		this.button_done = this.addButton(new Button(this.panel_main, this.getNextButtonId(), MARGIN, this.nextYPos, buttonSize, I18n.func_135053_a("gui.done")));
	}
	
	// ####################################################################################################
	// User input :
	// ####################################################################################################
	
	@Override
	protected void actionPerformed(GuiButton guiButton) {
		if(guiButton.enabled) {
			if(guiButton == this.button_done) {
				this.close();
			}
			else if(guiButton == this.button_invert) {
				this.invertOutput();
			}
		}
	}
	
	@Override
	protected void close() {
		this.tileEntity.onInventoryChanged();
		super.close();
	}
	
	private void invertOutput() {
		this.tileEntity.setInverted(!this.tileEntity.isInverted());
	}
	
	@Override
	protected void specialTabAction() {
		this.invertOutput();
	}
	
	@Override
	public void onGuiClosed() {
		try {
			int range = Integer.parseInt(this.field_range.getText());
			this.tileEntity.setRange(range);
		}
		catch(NumberFormatException argh) {
			// If the value is not a valid integer, it is ignored.
		}
		
		super.onGuiClosed();
	}
	
}
