package seigneurnecron.minecraftmods.stargate.gui;

import static seigneurnecron.minecraftmods.core.gui.GuiConstants.BACKGROUND_COLOR;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.BUTTON_HEIGHT;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.FIELD_HEIGHT;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.GREEN;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.LIGHT_BLUE;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.MARGIN;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.RED;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import seigneurnecron.minecraftmods.core.gui.GuiScreenTileEntity;
import seigneurnecron.minecraftmods.core.gui.IntegerField;
import seigneurnecron.minecraftmods.core.gui.Label;
import seigneurnecron.minecraftmods.core.gui.Panel;
import seigneurnecron.minecraftmods.stargate.gui.components.StargateButton;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityDetector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class GuiDetector extends GuiScreenTileEntity<TileEntityDetector> {
	
	// ####################################################################################################
	// Lang constants :
	// ####################################################################################################
	
	public static final String INV_NAME = "container.detector";
	
	public static final String RANGE = INV_NAME + ".range";
	public static final String RANGE_LIMITS = INV_NAME + ".rangeLimits";
	public static final String INVERTED_OUTPUT = INV_NAME + ".invertedOutput";
	public static final String NORMAL_OUTPUT = INV_NAME + ".normalOutput";
	public static final String INVERT_BUTTON = INV_NAME + ".invertButton";
	
	// ####################################################################################################
	// Interface fields :
	// ####################################################################################################
	
	protected String string_invertedOutput;
	protected String string_normalOutput;
	
	protected Panel panel_main;
	
	protected Label label_invName;
	protected Label label_range;
	protected Label label_rangeLimits;
	protected Label label_output;
	
	private GuiTextField field_range;
	
	protected StargateButton button_done;
	protected StargateButton button_invert;
	
	// ####################################################################################################
	// Constructors :
	// ####################################################################################################
	
	public GuiDetector(TileEntityDetector tileEntity) {
		super(tileEntity);
	}
	
	// ####################################################################################################
	// Interface definition :
	// ####################################################################################################
	
	@Override
	protected void updateComponents() {
		super.updateComponents();
		this.label_output.setText(this.tileEntity.isInverted() ? this.string_invertedOutput : this.string_normalOutput, this.tileEntity.isInverted() ? RED : GREEN);
	}
	
	@Override
	protected void drawBackground(int par1, int par2, float par3) {
		super.drawBackground(par1, par2, par3);
		this.panel_main.drawBox(LIGHT_BLUE, BACKGROUND_COLOR);
	}
	
	@Override
	protected void initComponents() {
		super.initComponents();
		
		// Panel sizes :
		
		int panelWidth = this.width / 2;
		int panelHeight = (4 * FIELD_HEIGHT) + (2 * BUTTON_HEIGHT) + (7 * MARGIN);
		
		// Panels :
		
		this.panel_main = new Panel(this, (this.width - panelWidth) / 2, (this.height - panelHeight) / 2, panelWidth, panelHeight);
		
		// Strings :
		
		String string_range = I18n.func_135053_a(RANGE) + " : ";
		
		this.string_invertedOutput = I18n.func_135053_a(INVERTED_OUTPUT);
		this.string_normalOutput = I18n.func_135053_a(NORMAL_OUTPUT);
		
		// Component sizes :
		
		int stringSize = this.fontRenderer.getStringWidth(string_range);
		int fieldOffset = stringSize + MARGIN;
		int fieldSize = this.panel_main.getComponentWidth() - (fieldOffset + MARGIN);
		int buttonSize = this.panel_main.getComponentWidth() - (2 * MARGIN);
		
		// Fields and buttons :
		
		this.nextYPos = MARGIN;
		this.label_invName = this.addComponent(new Label(this.panel_main, this.fontRenderer, MARGIN, this.nextYPos, buttonSize, I18n.func_135053_a(INV_NAME), true));
		this.label_range = this.addComponent(new Label(this.panel_main, this.fontRenderer, MARGIN, this.nextYPos, stringSize, string_range), false);
		this.field_range = this.addComponent(new IntegerField(this.panel_main, this.fontRenderer, fieldOffset, this.nextYPos, fieldSize, this.tileEntity.getRange()));
		this.label_rangeLimits = this.addComponent(new Label(this.panel_main, this.fontRenderer, MARGIN, this.nextYPos, buttonSize, I18n.func_135053_a(RANGE_LIMITS), true));
		this.label_output = this.addComponent(new Label(this.panel_main, this.fontRenderer, MARGIN, this.nextYPos, buttonSize, "", true));
		this.button_invert = this.addComponent(new StargateButton(this.panel_main, MARGIN, this.nextYPos, buttonSize, I18n.func_135053_a(INVERT_BUTTON) + I18n.func_135053_a(GuiScreenTileEntity.TAB)));
		this.button_done = this.addComponent(new StargateButton(this.panel_main, MARGIN, this.nextYPos, buttonSize, I18n.func_135053_a("gui.done") + I18n.func_135053_a(GuiScreenTileEntity.ESC)));
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
