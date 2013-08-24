package seigneurnecron.minecraftmods.stargate.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

import org.lwjgl.input.Keyboard;

import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityBaseShieldConsole;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityDetector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class GuiDetector extends GuiScreen {
	
	public static final String DETECTOR_PARAMETERS = TileEntityBaseShieldConsole.INV_NAME + ".detectorParameters";
	public static final String RANGE = TileEntityBaseShieldConsole.INV_NAME + ".range";
	public static final String RANGE_LIMITS = TileEntityBaseShieldConsole.INV_NAME + ".rangeLimits";
	public static final String INVERTED_OUTPUT = TileEntityBaseShieldConsole.INV_NAME + ".invertedOutput";
	public static final String NORMAL_OUTPUT = TileEntityBaseShieldConsole.INV_NAME + ".normalOutput";
	public static final String INVERT_BUTTON = TileEntityBaseShieldConsole.INV_NAME + ".invertButton";
	
	/** The title string which is displayed in the top-center of the screen. */
	protected String screenTitle = "Detector parmeters :";
	
	/** The detector tile entity. */
	private TileEntityDetector entityDetector;
	
	private GuiIntegerField rangeField;
	
	public GuiDetector(TileEntityDetector tileEntity) {
		this.entityDetector = tileEntity;
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, I18n.func_135053_a(DETECTOR_PARAMETERS) + " :", this.width / 2, 40, 16777215);
		this.drawString(this.fontRenderer, I18n.func_135053_a(RANGE) + " :", this.width / 2 - 40, 65, 10526880);
		this.drawString(this.fontRenderer, I18n.func_135053_a(RANGE_LIMITS), this.width / 2 - 40, 85, 10526880);
		this.drawString(this.fontRenderer, this.entityDetector.isInverted() ? I18n.func_135053_a(INVERTED_OUTPUT) : I18n.func_135053_a(NORMAL_OUTPUT), this.width / 2 - 40, 100, this.entityDetector.isInverted() ? 0xdd8844 : 0x44dd44);
		this.rangeField.drawTextBox();
		super.drawScreen(par1, par2, par3);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void initGui() {
		this.buttonList.clear();
		Keyboard.enableRepeatEvents(true);
		this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120, I18n.func_135053_a("gui.done")));
		this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 100, I18n.func_135053_a(INVERT_BUTTON)));
		this.rangeField = new GuiIntegerField(this.fontRenderer, this.width / 2, 60, 50, 20);
		this.rangeField.setText(String.valueOf(this.entityDetector.getRange()));
		this.rangeField.setFocused(true);
	}
	
	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
		
		try {
			int range = Integer.parseInt(this.rangeField.getText());
			this.entityDetector.setRange(range);
		}
		catch(NumberFormatException argh) {
			// If the value is not a valid integer, it is ignored.
		}
		
		StargateMod.sendPacketToServer(this.entityDetector.getDescriptionPacketWhithId(StargatePacketHandler.getGuiClosedPacketIdFromClass(TileEntityDetector.class)));
	}
	
	@Override
	public void updateScreen() {
		this.rangeField.updateCursorCounter();
	}
	
	@Override
	protected void actionPerformed(GuiButton guiButton) {
		if(guiButton.enabled) {
			if(guiButton.id == 0) {
				this.valider();
			}
			else if(guiButton.id == 1) {
				this.invertOutput();
			}
		}
	}
	
	/**
	 * Closes the detector configuration window.
	 */
	private void valider() {
		this.entityDetector.onInventoryChanged();
		this.mc.displayGuiScreen((GuiScreen) null);
	}
	
	/**
	 * Sets the state of the detector.
	 */
	private void invertOutput() {
		this.entityDetector.setInverted(!this.entityDetector.isInverted());
	}
	
	@Override
	protected void keyTyped(char character, int key) {
		if(key == Keyboard.KEY_RETURN || key == Keyboard.KEY_ESCAPE || key == this.mc.gameSettings.keyBindInventory.keyCode) {
			this.valider();
		}
		else if(key == Keyboard.KEY_TAB) {
			this.invertOutput();
		}
		else if(this.rangeField.isFocused()) {
			this.rangeField.textboxKeyTyped(character, key);
		}
	}
	
	@Override
	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
		this.rangeField.mouseClicked(par1, par2, par3);
	}
	
}
