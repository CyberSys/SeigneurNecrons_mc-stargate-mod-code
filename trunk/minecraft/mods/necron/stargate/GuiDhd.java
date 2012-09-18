package mods.necron.stargate;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ModLoader;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiDhd extends GuiScreen {
	
	/** The title string that is displayed in the top-center of the screen. */
	protected String screenTitle = "Coordonnees de destination:";
	
	/** La tile entity du teleporteur. */
	private TileEntityCoordDhd entityDhd;
	
	/** Counts the number of screen updates. */
	private int updateCounter;
	
	private GuiIntegerField xField;
	private GuiIntegerField yField;
	private GuiIntegerField zField;
	
	public GuiDhd(TileEntityCoordDhd tileEntity) {
		this.entityDhd = tileEntity;
	}
	
	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		this.controlList.clear();
		Keyboard.enableRepeatEvents(true);
		this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120, "Valider"));
		this.xField = new GuiIntegerField(this.fontRenderer, this.width / 2 - 20, 60, 50, 20);
		this.yField = new GuiIntegerField(this.fontRenderer, this.width / 2 - 20, 80, 50, 20);
		this.zField = new GuiIntegerField(this.fontRenderer, this.width / 2 - 20, 100, 50, 20);
		this.xField.setText(String.valueOf(this.entityDhd.getXDest()));
		this.yField.setText(String.valueOf(this.entityDhd.getYDest()));
		this.zField.setText(String.valueOf(this.entityDhd.getZDest()));
		this.xField.setFocused(true);
	}
	
	/**
	 * Called when the screen is unloaded. Used to disable keyboard repeat events
	 */
	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
		
		try {
			this.entityDhd.setXDest(Integer.parseInt(this.xField.getText()));
		}
		catch(NumberFormatException argh) {
		}
		
		try {
			this.entityDhd.setYDest(Integer.parseInt(this.yField.getText()));
		}
		catch(NumberFormatException argh) {
		}
		
		try {
			this.entityDhd.setZDest(Integer.parseInt(this.zField.getText()));
		}
		catch(NumberFormatException argh) {
		}
		
		ModLoader.sendPacket(entityDhd.getAuxillaryInfoPacketWhithId(TileEntityStargate.packetId_CloseGuiDhd));
	}
	
	/**
	 * Called from the main game loop to update the screen.
	 */
	@Override
	public void updateScreen() {
		this.xField.updateCursorCounter();
		this.yField.updateCursorCounter();
		this.zField.updateCursorCounter();
		++this.updateCounter;
	}
	
	/**
	 * Cette fonction est appelee quand on clic sur le bonton "Valider".
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
	
	/**
	 * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
	 */
	@Override
	protected void keyTyped(char character, int key) {
		if(key == Keyboard.KEY_RETURN || key == Keyboard.KEY_ESCAPE) {
			this.valider();
		}
		else if(key == Keyboard.KEY_TAB) {
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
	
	/**
	 * Draws the screen and all the components in it.
	 */
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 40, 16777215);
		this.drawString(this.fontRenderer, "x :", this.width / 2 - 40, 65, 10526880);
		this.drawString(this.fontRenderer, "y :", this.width / 2 - 40, 85, 10526880);
		this.drawString(this.fontRenderer, "z :", this.width / 2 - 40, 105, 10526880);
		this.xField.drawTextBox();
		this.yField.drawTextBox();
		this.zField.drawTextBox();
		super.drawScreen(par1, par2, par3);
	}
	
	/**
	 * Called when the mouse is clicked.
	 */
	@Override
	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
		this.xField.mouseClicked(par1, par2, par3);
		this.yField.mouseClicked(par1, par2, par3);
		this.zField.mouseClicked(par1, par2, par3);
	}
	
}
