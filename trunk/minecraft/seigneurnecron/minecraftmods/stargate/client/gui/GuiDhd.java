package seigneurnecron.minecraftmods.stargate.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.input.Keyboard;

import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityCoordDhd;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiDhd extends GuiScreen {
	
	/** The title string that is displayed in the top-center of the screen. */
	protected String screenTitle = "Coordinates of destination :";
	
	/** La tile entity du teleporteur. */
	private TileEntityCoordDhd entityDhd;
	
	private GuiIntegerField xField;
	private GuiIntegerField yField;
	private GuiIntegerField zField;
	
	public GuiDhd(TileEntityCoordDhd tileEntity) {
		this.entityDhd = tileEntity;
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		String gate = "none";
		
		if(this.entityDhd != null && this.entityDhd.isLinkedToGate()) {
			gate = "(" + this.entityDhd.getXGate() + ", " + this.entityDhd.getYGate() + ", " + this.entityDhd.getZGate() + ")";
		}
		
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, "Connected gate : " + gate, this.width / 2, 45, 16777215);
		this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 60, 16777215);
		this.drawString(this.fontRenderer, "x :", this.width / 2 - 40, 85, 10526880);
		this.drawString(this.fontRenderer, "y :", this.width / 2 - 40, 105, 10526880);
		this.drawString(this.fontRenderer, "z :", this.width / 2 - 40, 125, 10526880);
		this.xField.drawTextBox();
		this.yField.drawTextBox();
		this.zField.drawTextBox();
		super.drawScreen(par1, par2, par3);
	}
	
	@Override
	public void initGui() {
		this.controlList.clear();
		Keyboard.enableRepeatEvents(true);
		this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120, "Ok"));
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
			// Si la valeur n'est pas un entier valide, on l'ignore.
		}
		
		try {
			this.entityDhd.setYDest(Integer.parseInt(this.yField.getText()));
		}
		catch(NumberFormatException argh) {
			// Si la valeur n'est pas un entier valide, on l'ignore.
		}
		
		try {
			this.entityDhd.setZDest(Integer.parseInt(this.zField.getText()));
		}
		catch(NumberFormatException argh) {
			// Si la valeur n'est pas un entier valide, on l'ignore.
		}
		
		StargateMod.sendPacketToServer(this.entityDhd.getDescriptionPacketWhithId(StargatePacketHandler.packetId_CloseGuiDhd));
	}
	
	@Override
	public void updateScreen() {
		this.xField.updateCursorCounter();
		this.yField.updateCursorCounter();
		this.zField.updateCursorCounter();
	}
	
	/**
	 * Cette fonction est appelee quand on clic sur le bonton "Ok".
	 */
	@Override
	protected void actionPerformed(GuiButton guiButton) {
		if(guiButton.enabled) {
			if(guiButton.id == 0) {
				this.valider();
			}
		}
	}
	
	/**
	 * Ferme la fenetre de configuration du dhd.
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
