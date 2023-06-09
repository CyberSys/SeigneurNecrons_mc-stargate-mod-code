package seigneurnecron.minecraftmods.stargate.gui;

import static seigneurnecron.minecraftmods.core.gui.GuiConstants.BACKGROUND_COLOR;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.BUTTON_HEIGHT;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.FIELD_HEIGHT;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.GREEN;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.LIGHT_BLUE;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.MARGIN;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.RED;
import static seigneurnecron.minecraftmods.stargate.gui.GuiDhd.CLOSE;
import static seigneurnecron.minecraftmods.stargate.gui.GuiShieldConsole.SHIELD_OFF;
import static seigneurnecron.minecraftmods.stargate.gui.GuiShieldConsole.SHIELD_ON;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import seigneurnecron.minecraftmods.core.gui.GuiScreenTileEntity;
import seigneurnecron.minecraftmods.core.gui.IntegerField;
import seigneurnecron.minecraftmods.core.gui.Label;
import seigneurnecron.minecraftmods.core.gui.Panel;
import seigneurnecron.minecraftmods.core.gui.TextField;
import seigneurnecron.minecraftmods.core.mod.ModBase;
import seigneurnecron.minecraftmods.stargate.gui.components.StargateButton;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargateControl;
import seigneurnecron.minecraftmods.stargate.tools.loadable.Stargate;
import seigneurnecron.minecraftmods.stargate.tools.playerdata.PlayerStargateData;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class GuiShieldRemote extends GuiScreenTileEntity<TileEntityStargateControl> {
	
	// ####################################################################################################
	// Lang constants :
	// ####################################################################################################
	
	public static final String INV_NAME = "shieldRemote";
	public static final String CODE = INV_NAME + ".code";
	public static final String SEND_CODE = INV_NAME + ".changeCode";
	
	// ####################################################################################################
	// Interface fields :
	// ####################################################################################################
	
	protected String string_shieldOn;
	protected String string_shieldOff;
	
	protected Panel panel_main;
	
	protected TextField field_code;
	
	protected Label label_invName;
	protected Label label_shield;
	protected Label label_code;
	
	protected StargateButton button_done;
	protected StargateButton button_code;
	protected StargateButton button_close;
	
	// ####################################################################################################
	// Data fields :
	// ####################################################################################################
	
	protected EntityPlayer player;
	protected PlayerStargateData playerData;
	
	// ####################################################################################################
	// Constructors :
	// ####################################################################################################
	
	public GuiShieldRemote(TileEntityStargateControl tileEntity, EntityPlayer player) {
		super(tileEntity);
		this.player = player;
		this.playerData = PlayerStargateData.get(player);
	}
	
	// ####################################################################################################
	// Interface definition :
	// ####################################################################################################
	
	@Override
	protected boolean isGuiValid() {
		return super.isGuiValid() && this.tileEntity.isActivated();
	}
	
	@Override
	protected void updateComponents() {
		super.updateComponents();
		
		String shieldMessage;
		int shieldMessageColor;
		
		if(this.tileEntity.isOtherGateShieldActivated()) {
			shieldMessage = this.string_shieldOn;
			shieldMessageColor = RED;
		}
		else {
			shieldMessage = this.string_shieldOff;
			shieldMessageColor = GREEN;
		}
		
		this.label_shield.setText(shieldMessage, shieldMessageColor);
	}
	
	@Override
	protected void drawBackground(int mouseX, int mouseY, float timeSinceLastTick) {
		super.drawBackground(mouseX, mouseY, timeSinceLastTick);
		this.panel_main.drawBox(LIGHT_BLUE, BACKGROUND_COLOR);
	}
	
	@Override
	protected void initComponents() {
		super.initComponents();
		
		// Panel sizes :
		
		int panelWidth = this.width / 2;
		int panelHeight = (3 * FIELD_HEIGHT) + (3 * BUTTON_HEIGHT) + (7 * MARGIN);
		
		// Panels :
		
		this.panel_main = new Panel(this, (this.width - panelWidth) / 2, (this.height - panelHeight) / 2, panelWidth, panelHeight);
		
		// Strings :
		
		String string_code = I18n.getString(CODE) + " : ";
		
		this.string_shieldOn = I18n.getString(SHIELD_ON);
		this.string_shieldOff = I18n.getString(SHIELD_OFF);
		
		int code = 0;
		String address = this.tileEntity.getDestination().address;
		int index = this.playerData.getDataList().indexOf(new Stargate(address, "", code));
		
		if(index >= 0) {
			code = this.playerData.getDataList().get(index).code;
		}
		
		// Component sizes :
		
		int stringSize = this.fontRenderer.getStringWidth(string_code);
		int fieldOffset = stringSize + MARGIN;
		int fieldSize = this.panel_main.getComponentWidth() - (fieldOffset + MARGIN);
		int buttonSize = this.panel_main.getComponentWidth() - (2 * MARGIN);
		
		// Fields and buttons :
		
		this.nextYPos = MARGIN;
		this.label_invName = this.addComponent(new Label(this.panel_main, this.fontRenderer, MARGIN, this.nextYPos, buttonSize, I18n.getString(INV_NAME), true));
		this.label_shield = this.addComponent(new Label(this.panel_main, this.fontRenderer, MARGIN, this.nextYPos, buttonSize, ""));
		this.label_code = this.addComponent(new Label(this.panel_main, this.fontRenderer, MARGIN, this.nextYPos, stringSize, string_code), false);
		this.field_code = this.addComponent(new IntegerField(this.panel_main, this.fontRenderer, fieldOffset, this.nextYPos, fieldSize, code));
		
		this.button_code = this.addComponent(new StargateButton(this.panel_main, MARGIN, this.nextYPos, buttonSize, I18n.getString(SEND_CODE) + I18n.getString(KEY_ENTER)));
		this.button_close = this.addComponent(new StargateButton(this.panel_main, MARGIN, this.nextYPos, buttonSize, I18n.getString(CLOSE) + I18n.getString(KEY_TAB)));
		this.button_done = this.addComponent(new StargateButton(this.panel_main, MARGIN, this.nextYPos, buttonSize, I18n.getString(GUI_DONE) + I18n.getString(KEY_ESC)));
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
			else if(guiButton == this.button_code) {
				this.sendCode();
			}
			else if(guiButton == this.button_close) {
				this.closeGate();
			}
		}
	}
	
	protected void sendCode() {
		try {
			int code = Integer.parseInt(this.field_code.getText());
			ModBase.sendPacketToServer(this.tileEntity.getShieldRemotePacket(code));
		}
		catch(NumberFormatException argh) {
			// If the value is not a valid integer, it is ignored.
		}
	}
	
	protected void closeGate() {
		ModBase.sendPacketToServer(this.tileEntity.getRemoteClosePacket());
	}
	
	@Override
	protected void onEnterPressed() {
		this.sendCode();
	}
	
	@Override
	protected void specialTabAction() {
		this.closeGate();
	}
	
}
