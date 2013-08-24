package seigneurnecron.minecraftmods.stargate.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

import org.lwjgl.input.Keyboard;

import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityBaseDhd;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityBaseTeleporter;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class GuiTeleporter extends GuiScreen {
	
	public static final String TELEPORTER_COORDINATES = TileEntityBaseDhd.INV_NAME + ".teleporterCoordinates";
	public static final String DESTINATION_COORDINATES = TileEntityBaseDhd.INV_NAME + ".destinationCoordinates";
	
	/** The teleporter tile entity. */
	private TileEntityBaseTeleporter entityTeleporter;
	
	private GuiIntegerField xField;
	private GuiIntegerField yField;
	private GuiIntegerField zField;
	
	public GuiTeleporter(TileEntityBaseTeleporter tileEntity) {
		this.entityTeleporter = tileEntity;
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		String coords = "-";
		
		if(this.entityTeleporter != null) {
			coords = "(" + this.entityTeleporter.xCoord + ", " + this.entityTeleporter.yCoord + ", " + this.entityTeleporter.zCoord + ")";
		}
		
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, I18n.func_135053_a(TELEPORTER_COORDINATES) + " : " + coords, this.width / 2, 45, 16777215);
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
		this.xField.setText(String.valueOf(this.entityTeleporter.getXDest()));
		this.yField.setText(String.valueOf(this.entityTeleporter.getYDest()));
		this.zField.setText(String.valueOf(this.entityTeleporter.getZDest()));
		this.xField.setFocused(true);
	}
	
	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
		
		try {
			this.entityTeleporter.setXDest(Integer.parseInt(this.xField.getText()));
		}
		catch(NumberFormatException argh) {
			// If the value is not a valid integer, it is ignored.
		}
		
		try {
			this.entityTeleporter.setYDest(Integer.parseInt(this.yField.getText()));
		}
		catch(NumberFormatException argh) {
			// If the value is not a valid integer, it is ignored.
		}
		
		try {
			this.entityTeleporter.setZDest(Integer.parseInt(this.zField.getText()));
		}
		catch(NumberFormatException argh) {
			// If the value is not a valid integer, it is ignored.
		}
		
		StargateMod.sendPacketToServer(this.entityTeleporter.getDescriptionPacketWhithId(StargatePacketHandler.getGuiClosedPacketIdFromClass(TileEntityBaseTeleporter.class)));
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
	 * Closes the teleporter configuration window.
	 */
	private void valider() {
		this.entityTeleporter.onInventoryChanged();
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
