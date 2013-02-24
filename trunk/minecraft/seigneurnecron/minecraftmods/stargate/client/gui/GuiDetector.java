package seigneurnecron.minecraftmods.stargate.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.input.Keyboard;

import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityDetector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiDetector extends GuiScreen {
	
	/** The title string that is displayed in the top-center of the screen. */
	protected String screenTitle = "Detector parmeters :";
	
	/** La tile entity du detecteur. */
	private TileEntityDetector entityDetector;
	
	private GuiIntegerField rangeField;
	
	public GuiDetector(TileEntityDetector tileEntity) {
		this.entityDetector = tileEntity;
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 40, 16777215);
		this.drawString(this.fontRenderer, "portee :", this.width / 2 - 40, 65, 10526880);
		this.drawString(this.fontRenderer, this.entityDetector.isInverted() ? "normal output." : "inverted output.", this.width / 2 - 40, 85, this.entityDetector.isInverted() ? 0xdd8844 : 0x44dd44);
		this.rangeField.drawTextBox();
		super.drawScreen(par1, par2, par3);
	}
	
	@Override
	public void initGui() {
		this.controlList.clear();
		Keyboard.enableRepeatEvents(true);
		this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120, "Ok"));
		this.controlList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 100, "Invert output"));
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
			// Si la valeur n'est pas un entier valide, on l'ignore.
		}
		
		StargateMod.sendPacketToServer(this.entityDetector.getDescriptionPacketWhithId(StargatePacketHandler.packetId_CloseGuiDetector));
	}
	
	@Override
	public void updateScreen() {
		this.rangeField.updateCursorCounter();
	}
	
	/**
	 * Cette fonction est appelee quand on clic sur le bonton "Ok" ou le bouton "Invert output".
	 */
	@Override
	protected void actionPerformed(GuiButton guiButton) {
		if(guiButton.enabled) {
			if(guiButton.id == 0) {
				this.valider();
			}
			else if(guiButton.id == 1) {
				this.entityDetector.setInverted(!this.entityDetector.isInverted());
			}
		}
	}
	
	/**
	 * Ferme la fenetre de configuration du detecteur.
	 */
	private void valider() {
		this.entityDetector.onInventoryChanged();
		this.mc.displayGuiScreen((GuiScreen) null);
	}
	
	@Override
	protected void keyTyped(char character, int key) {
		if(key == Keyboard.KEY_RETURN || key == Keyboard.KEY_ESCAPE || key == this.mc.gameSettings.keyBindInventory.keyCode) {
			this.valider();
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
