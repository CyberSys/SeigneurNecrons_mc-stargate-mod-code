package seigneurnecron.minecraftmods.stargate.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

import org.lwjgl.input.Keyboard;

import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityBaseShieldConsole;

/**
 * @author Seigneur Necron
 */
public class GuiShieldConsole extends GuiScreen {
	
	public static final String SHIELD_PARAMETERS = TileEntityBaseShieldConsole.INV_NAME + ".shieldParameters";
	public static final String CODE = TileEntityBaseShieldConsole.INV_NAME + ".code";
	public static final String AUTO_SHIELD_ON = TileEntityBaseShieldConsole.INV_NAME + ".autoShieldOn";
	public static final String AUTO_SHIELD_OFF = TileEntityBaseShieldConsole.INV_NAME + ".autoShieldOff";
	public static final String AUTO_SHIELD_SWITCH = TileEntityBaseShieldConsole.INV_NAME + ".autoShieldSwitch";
	
	/** The shield console tile entity. */
	private TileEntityBaseShieldConsole entityShieldConsole;
	
	private GuiIntegerField codeField;
	
	public GuiShieldConsole(TileEntityBaseShieldConsole tileEntity) {
		this.entityShieldConsole = tileEntity;
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, I18n.func_135053_a(SHIELD_PARAMETERS) + " :", this.width / 2, 40, 16777215);
		this.drawString(this.fontRenderer, I18n.func_135053_a(CODE) + " :", this.width / 2 - 40, 65, 10526880);
		this.drawString(this.fontRenderer, this.entityShieldConsole.isShieldAutomated() ? I18n.func_135053_a(AUTO_SHIELD_ON) : I18n.func_135053_a(AUTO_SHIELD_OFF), this.width / 2 - 40, 85, this.entityShieldConsole.isShieldAutomated() ? 0x44dd44 : 0xdd8844);
		this.codeField.drawTextBox();
		super.drawScreen(par1, par2, par3);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void initGui() {
		this.buttonList.clear();
		Keyboard.enableRepeatEvents(true);
		this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120, I18n.func_135053_a("gui.done")));
		this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 100, I18n.func_135053_a(AUTO_SHIELD_SWITCH)));
		this.codeField = new GuiIntegerField(this.fontRenderer, this.width / 2, 60, 50, 20);
		this.codeField.setText(String.valueOf(this.entityShieldConsole.getCode()));
		this.codeField.setFocused(true);
	}
	
	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
		
		try {
			int code = Integer.parseInt(this.codeField.getText());
			this.entityShieldConsole.setCode(code);
		}
		catch(NumberFormatException argh) {
			// If the value is not a valid integer, it is ignored.
		}
		
		StargateMod.sendPacketToServer(this.entityShieldConsole.getDescriptionPacketWhithId(StargatePacketHandler.getGuiClosedPacketIdFromClass(TileEntityBaseShieldConsole.class)));
	}
	
	@Override
	public void updateScreen() {
		this.codeField.updateCursorCounter();
	}
	
	@Override
	protected void actionPerformed(GuiButton guiButton) {
		if(guiButton.enabled) {
			if(guiButton.id == 0) {
				this.valider();
			}
			else if(guiButton.id == 1) {
				this.autoShield();
			}
		}
	}
	
	/**
	 * Closes the detector configuration window.
	 */
	private void valider() {
		this.entityShieldConsole.onInventoryChanged();
		this.mc.displayGuiScreen((GuiScreen) null);
	}
	
	/**
	 * Sets the state of the detector.
	 */
	private void autoShield() {
		this.entityShieldConsole.setShieldAutomated(!this.entityShieldConsole.isShieldAutomated());
	}
	
	@Override
	protected void keyTyped(char character, int key) {
		if(key == Keyboard.KEY_RETURN || key == Keyboard.KEY_ESCAPE || key == this.mc.gameSettings.keyBindInventory.keyCode) {
			this.valider();
		}
		else if(key == Keyboard.KEY_TAB) {
			this.autoShield();
		}
		else if(this.codeField.isFocused()) {
			this.codeField.textboxKeyTyped(character, key);
		}
	}
	
	@Override
	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
		this.codeField.mouseClicked(par1, par2, par3);
	}
	
}
