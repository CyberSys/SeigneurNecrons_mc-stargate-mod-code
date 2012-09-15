package mods.necron.stargate;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ModLoader;

import org.lwjgl.input.Keyboard;

public class GuiDetector extends GuiScreen {
	
	/** The title string that is displayed in the top-center of the screen. */
	protected String screenTitle = "Parametres du détecteur:";
	
	/** La tile entity du détecteur. */
	private TileEntityDetector entityDetector;
	
	/** Counts the number of screen updates. */
	private int updateCounter;
	
	private GuiIntegerField rangeField;
	
	public GuiDetector(TileEntityDetector tileEntity) {
		this.entityDetector = tileEntity;
	}
	
	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		this.controlList.clear();
		Keyboard.enableRepeatEvents(true);
		this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120, "Valider"));
		this.controlList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 100, "Inverser la sortie."));
		this.rangeField = new GuiIntegerField(this.fontRenderer, this.width / 2, 60, 50, 20);
		this.rangeField.setText(String.valueOf(this.entityDetector.getRange()));
		this.rangeField.setFocused(true);
	}
	
	/**
	 * Called when the screen is unloaded. Used to disable keyboard repeat events
	 */
	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
		
		try {
			int range = Integer.parseInt(this.rangeField.getText());
			this.entityDetector.setRange(range);
		}
		catch(NumberFormatException argh) {
		}
		
		ModLoader.sendPacket(entityDetector.getAuxillaryInfoPacketWhithId(TileEntityStargate.packetId_CloseGuiDetector));
	}
	
	/**
	 * Called from the main game loop to update the screen.
	 */
	@Override
	public void updateScreen() {
		this.rangeField.updateCursorCounter();
		++this.updateCounter;
	}
	
	/**
	 * Cette fonction est appelée quand on clic sur le bonton "Valider" ou le bouton "Inverser".
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
	 * Ferme la fenetre de configuration du téléporteur.
	 */
	private void valider() {
		this.entityDetector.onInventoryChanged();
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
		else if(this.rangeField.isFocused()) {
			this.rangeField.textboxKeyTyped(character, key);
		}
	}
	
	/**
	 * Draws the screen and all the components in it.
	 */
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 40, 16777215);
		this.drawString(this.fontRenderer, "portée :", this.width / 2 - 40, 65, 10526880);
		this.drawString(this.fontRenderer, this.entityDetector.isInverted() ? "sortie inversée." : "sortie normale.", this.width / 2 - 40, 85, this.entityDetector.isInverted() ? 0xdd8844 : 0x44dd44);
		this.rangeField.drawTextBox();
		super.drawScreen(par1, par2, par3);
	}
	
	/**
	 * Called when the mouse is clicked.
	 */
	@Override
	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
		this.rangeField.mouseClicked(par1, par2, par3);
	}
	
}
