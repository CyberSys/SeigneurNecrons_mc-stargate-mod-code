package seigneurnecron.minecraftmods.stargate.client.gui;

import static seigneurnecron.minecraftmods.stargate.tileentity.TileEntityBaseShieldConsole.INV_NAME;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.client.gui.tools.Button;
import seigneurnecron.minecraftmods.stargate.client.gui.tools.IntegerField;
import seigneurnecron.minecraftmods.stargate.client.gui.tools.Panel;
import seigneurnecron.minecraftmods.stargate.client.gui.tools.TextField;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityBaseShieldConsole;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class GuiShieldConsole extends GuiStargateConsole<TileEntityBaseShieldConsole> {
	
	// ####################################################################################################
	// Lang constants :
	// ####################################################################################################
	
	public static final String CURRENT_CODE = INV_NAME + ".currentCode";
	public static final String SHIELD_ON = INV_NAME + ".shieldOn";
	public static final String SHIELD_OFF = INV_NAME + ".shieldOff";
	public static final String SHIELD_DISCONNECTED = INV_NAME + ".shieldDisconnected";
	public static final String SHIELD_SWITCH = INV_NAME + ".shieldSwitch";
	public static final String AUTO_SHIELD_ON = INV_NAME + ".autoShieldOn";
	public static final String AUTO_SHIELD_OFF = INV_NAME + ".autoShieldOff";
	public static final String AUTO_SHIELD_DISCONNECTED = INV_NAME + ".autoShieldDisconnected";
	public static final String AUTO_SHIELD_SWITCH = INV_NAME + ".autoShieldSwitch";
	public static final String CHANGE_CODE = INV_NAME + ".changeCode";
	
	// ####################################################################################################
	// Interface fields :
	// ####################################################################################################
	
	protected String string_invName;
	protected String string_currentCode;
	protected String string_shieldOn;
	protected String string_shieldOff;
	protected String string_shieldDisconnected;
	protected String string_autoShieldOn;
	protected String string_autoShieldOff;
	protected String string_autoShieldDisconnected;
	
	protected Panel panel_main;
	
	protected TextField field_code;
	
	protected Button button_done;
	protected Button button_shield;
	protected Button button_autoShield;
	protected Button button_code;
	
	// ####################################################################################################
	// Builder :
	// ####################################################################################################
	
	public GuiShieldConsole(TileEntityBaseShieldConsole tileEntity, EntityPlayer player) {
		super(tileEntity, player);
	}
	
	// ####################################################################################################
	// Interface definition :
	// ####################################################################################################
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
		
		this.panel_main.drawBox(GRAY);
		
		String code;
		String shieldMessage;
		int shieldMessageColor;
		String autoShieldMessage;
		int autoShieldMessageColor;
		
		if(this.stargateConnected) {
			code = String.valueOf(this.stargate.getCode());
			
			if(this.stargate.isShieldActivated()) {
				shieldMessage = this.string_shieldOn;
				shieldMessageColor = GREEN;
			}
			else {
				shieldMessage = this.string_shieldOff;
				shieldMessageColor = RED;
			}
			
			if(this.stargate.isShieldAutomated()) {
				autoShieldMessage = this.string_autoShieldOn;
				autoShieldMessageColor = GREEN;
			}
			else {
				autoShieldMessage = this.string_autoShieldOff;
				autoShieldMessageColor = RED;
			}
		}
		else {
			code = "-";
			shieldMessage = this.string_shieldDisconnected;
			shieldMessageColor = GRAY;
			autoShieldMessage = this.string_autoShieldDisconnected;
			autoShieldMessageColor = GRAY;
		}
		
		this.nextYPos = MARGIN;
		this.panel_main.drawCenteredText(this.fontRenderer, this.string_invName, this.nextYPos, WHITE);
		this.panel_main.drawText(this.fontRenderer, this.string_currentCode + code, MARGIN, this.nextYPos, WHITE);
		this.nextYPos += FIELD_HEIGHT + (2 * MARGIN) + BONUS_MARGIN;
		this.panel_main.drawText(this.fontRenderer, autoShieldMessage, MARGIN + 2, this.nextYPos, autoShieldMessageColor);
		this.nextYPos += BUTTON_HEIGHT + MARGIN + BONUS_MARGIN;
		this.panel_main.drawText(this.fontRenderer, shieldMessage, MARGIN + 2, this.nextYPos, shieldMessageColor);
	}
	
	@Override
	public void initComponents() {
		super.initComponents();
		
		// Panel sizes :
		
		int panelWidth = this.width / 2;
		int panelHeight = (5 * FIELD_HEIGHT) + (3 * BUTTON_HEIGHT) + (10 * MARGIN) + (3 * BONUS_MARGIN);
		
		// Panels :
		
		this.panel_main = new Panel(this, (this.width - panelWidth) / 2, (this.height - panelHeight) / 2, panelWidth, panelHeight);
		
		// Strings :
		
		this.string_invName = I18n.func_135053_a(INV_NAME);
		this.string_currentCode = I18n.func_135053_a(CURRENT_CODE) + " : ";
		this.string_shieldOn = I18n.func_135053_a(SHIELD_ON);
		this.string_shieldOff = I18n.func_135053_a(SHIELD_OFF);
		this.string_shieldDisconnected = I18n.func_135053_a(SHIELD_DISCONNECTED);
		this.string_autoShieldOn = I18n.func_135053_a(AUTO_SHIELD_ON);
		this.string_autoShieldOff = I18n.func_135053_a(AUTO_SHIELD_OFF);
		this.string_autoShieldDisconnected = I18n.func_135053_a(AUTO_SHIELD_DISCONNECTED);
		
		int code = (this.stargateConnected) ? this.stargate.getCode() : 0;
		
		// Component sizes :
		
		int fieldSize = (this.panel_main.getComponentWidth() - (3 * MARGIN)) / 2;
		int buttonSize = this.panel_main.getComponentWidth() - (2 * MARGIN);
		
		// Fields and buttons :
		
		this.nextYPos = (2 * FIELD_HEIGHT) + (3 * MARGIN);
		this.field_code = this.addComponent(new IntegerField(this.panel_main, this.fontRenderer, MARGIN, this.nextYPos, fieldSize, code), false);
		this.nextYPos -= MARGIN;
		this.button_code = this.addComponent(new Button(this.panel_main, this.getNextButtonId(), fieldSize + (2 * MARGIN), this.nextYPos, fieldSize, I18n.func_135053_a(CHANGE_CODE)));
		
		this.nextYPos += FIELD_HEIGHT + MARGIN + BONUS_MARGIN;
		this.button_autoShield = this.addComponent(new Button(this.panel_main, this.getNextButtonId(), MARGIN, this.nextYPos, buttonSize, I18n.func_135053_a(AUTO_SHIELD_SWITCH)));
		
		this.nextYPos += FIELD_HEIGHT + MARGIN + BONUS_MARGIN;
		this.button_shield = this.addComponent(new Button(this.panel_main, this.getNextButtonId(), MARGIN, this.nextYPos, buttonSize, I18n.func_135053_a(SHIELD_SWITCH)));
		
		this.nextYPos += BONUS_MARGIN;
		this.button_done = this.addComponent(new Button(this.panel_main, this.getNextButtonId(), MARGIN, this.nextYPos, buttonSize, I18n.func_135053_a("gui.done")));
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
			else if(guiButton == this.button_shield) {
				this.shield();
			}
			else if(guiButton == this.button_autoShield) {
				this.autoShield();
			}
			else if(guiButton == this.button_code) {
				this.changeCode();
			}
		}
	}
	
	protected void shield() {
		if(this.stargateConnected) {
			StargateMod.sendPacketToServer(this.tileEntity.getShieldPacket(!this.stargate.isShieldActivated()));
		}
	}
	
	protected void autoShield() {
		if(this.stargateConnected) {
			StargateMod.sendPacketToServer(this.tileEntity.getShieldAutomatedPacket(!this.stargate.isShieldAutomated()));
		}
	}
	
	protected void changeCode() {
		if(this.stargateConnected) {
			try {
				int code = Integer.parseInt(this.field_code.getText());
				StargateMod.sendPacketToServer(this.tileEntity.getShieldCodePacket(code));
			}
			catch(NumberFormatException argh) {
				// If the value is not a valid integer, it is ignored.
			}
		}
	}
	
	@Override
	protected void onEnterPressed() {
		this.shield();
	}
	
	@Override
	protected void specialTabAction() {
		this.autoShield();
	}
	
	// ####################################################################################################
	// Utility :
	// ####################################################################################################
	
	@Override
	protected void updateStargateInterface() {
		this.button_shield.enabled = this.stargateConnected;
		this.button_autoShield.enabled = this.stargateConnected;
		this.button_code.enabled = this.stargateConnected;
		this.field_code.setEnabled(this.stargateConnected);
	}
	
}
