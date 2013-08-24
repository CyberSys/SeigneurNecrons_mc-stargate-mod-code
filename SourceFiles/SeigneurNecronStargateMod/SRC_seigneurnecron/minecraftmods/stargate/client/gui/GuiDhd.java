package seigneurnecron.minecraftmods.stargate.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

import org.lwjgl.input.Keyboard;

import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityBaseDhd;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class GuiDhd extends GuiScreen {
	
	public static final String CONNECTED_GATE = TileEntityBaseDhd.INV_NAME + ".connectedGate";
	public static final String GATE_NONE = TileEntityBaseDhd.INV_NAME + ".gateNone";
	public static final String DESTINATION_COORDINATES = TileEntityBaseDhd.INV_NAME + ".destinationCoordinates";
	
	/** The dhd tile entity. */
	private TileEntityBaseDhd entityDhd;
	
	private GuiIntegerField xField;
	private GuiIntegerField yField;
	private GuiIntegerField zField;
	
	public GuiDhd(TileEntityBaseDhd tileEntity) {
		this.entityDhd = tileEntity;
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		String gate = I18n.func_135053_a(GATE_NONE);
		
		if(this.entityDhd != null && this.entityDhd.isLinkedToGate()) {
			gate = "(" + this.entityDhd.getXGate() + ", " + this.entityDhd.getYGate() + ", " + this.entityDhd.getZGate() + ")";
		}
		
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, I18n.func_135053_a(CONNECTED_GATE) + " : " + gate, this.width / 2, 45, 16777215);
		this.drawCenteredString(this.fontRenderer, I18n.func_135053_a(DESTINATION_COORDINATES) + " :", this.width / 2, 60, 16777215);
		this.drawString(this.fontRenderer, "x :", this.width / 2 - 40, 85, 10526880);
		this.drawString(this.fontRenderer, "y :", this.width / 2 - 40, 105, 10526880);
		this.drawString(this.fontRenderer, "z :", this.width / 2 - 40, 125, 10526880);
		this.xField.drawTextBox();
		this.yField.drawTextBox();
		this.zField.drawTextBox();
		super.drawScreen(par1, par2, par3);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void initGui() {
		this.buttonList.clear();
		Keyboard.enableRepeatEvents(true);
		this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120, I18n.func_135053_a("gui.done")));
		this.xField = new GuiIntegerField(this.fontRenderer, this.width / 2 - 20, 80, 50, 20);
		this.yField = new GuiIntegerField(this.fontRenderer, this.width / 2 - 20, 100, 50, 20);
		this.zField = new GuiIntegerField(this.fontRenderer, this.width / 2 - 20, 120, 50, 20);
		this.xField.setText(String.valueOf(this.entityDhd.getXDest()));
		this.yField.setText(String.valueOf(this.entityDhd.getYDest()));
		this.zField.setText(String.valueOf(this.entityDhd.getZDest()));
		this.xField.setFocused(true);
	}
	
	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
		
		try {
			this.entityDhd.setXDest(Integer.parseInt(this.xField.getText()));
		}
		catch(NumberFormatException argh) {
			// If the value is not a valid integer, it is ignored.
		}
		
		try {
			this.entityDhd.setYDest(Integer.parseInt(this.yField.getText()));
		}
		catch(NumberFormatException argh) {
			// If the value is not a valid integer, it is ignored.
		}
		
		try {
			this.entityDhd.setZDest(Integer.parseInt(this.zField.getText()));
		}
		catch(NumberFormatException argh) {
			// If the value is not a valid integer, it is ignored.
		}
		
		StargateMod.sendPacketToServer(this.entityDhd.getDescriptionPacketWhithId(StargatePacketHandler.getGuiClosedPacketIdFromClass(TileEntityBaseDhd.class)));
	}
	
	@Override
	public void updateScreen() {
		this.xField.updateCursorCounter();
		this.yField.updateCursorCounter();
		this.zField.updateCursorCounter();
	}
	
	@Override
	protected void actionPerformed(GuiButton guiButton) {
		if(guiButton.enabled) {
			if(guiButton.id == 0) {
				this.valider();
			}
		}
	}
	
	/**
	 * Closes the dhd configuration window.
	 */
	private void valider() {
		this.entityDhd.onInventoryChanged();
		this.mc.displayGuiScreen((GuiScreen) null);
	}
	
	@Override
	protected void keyTyped(char character, int key) {
		if(key == Keyboard.KEY_RETURN || key == Keyboard.KEY_ESCAPE || key == this.mc.gameSettings.keyBindInventory.keyCode) {
			this.valider();
		}
		else if(key == Keyboard.KEY_TAB) {
			if(GuiScreen.isShiftKeyDown()) {
				if(this.xField.isFocused()) {
					this.xField.setFocused(false);
					this.zField.setFocused(true);
				}
				else if(this.yField.isFocused()) {
					this.yField.setFocused(false);
					this.xField.setFocused(true);
				}
				else if(this.zField.isFocused()) {
					this.zField.setFocused(false);
					this.yField.setFocused(true);
				}
			}
			else {
				if(this.xField.isFocused()) {
					this.xField.setFocused(false);
					this.yField.setFocused(true);
				}
				else if(this.yField.isFocused()) {
					this.yField.setFocused(false);
					this.zField.setFocused(true);
				}
				else if(this.zField.isFocused()) {
					this.zField.setFocused(false);
					this.xField.setFocused(true);
				}
			}
		}
		else if(this.xField.isFocused()) {
			this.xField.textboxKeyTyped(character, key);
		}
		else if(this.yField.isFocused()) {
			this.yField.textboxKeyTyped(character, key);
		}
		else if(this.zField.isFocused()) {
			this.zField.textboxKeyTyped(character, key);
		}
	}
	
	@Override
	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
		this.xField.mouseClicked(par1, par2, par3);
		this.yField.mouseClicked(par1, par2, par3);
		this.zField.mouseClicked(par1, par2, par3);
	}
	
}
