package seigneurnecron.minecraftmods.stargate.client.gui.tools;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

import org.lwjgl.input.Keyboard;

import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.client.sound.StargateSounds;
import seigneurnecron.minecraftmods.stargate.tools.reflection.Reflection;
import seigneurnecron.minecraftmods.stargate.tools.reflection.ReflectionException;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public abstract class Screen extends GuiScreen implements Container {
	
	// ####################################################################################################
	// Reflection :
	// ####################################################################################################
	
	// Fields obfuscated names :
	
	private static String SELECTED_BUTTON = StargateMod.obfuscated ? "field_73883_a" : "selectedButton";
	
	// Setters :
	
	private void setSelectedButton(GuiButton selectedButton) throws ReflectionException {
		try {
			Reflection.set(GuiScreen.class, this, SELECTED_BUTTON, selectedButton);
		}
		catch(Exception argh) {
			throw new ReflectionException(argh);
		}
	}
	
	// ####################################################################################################
	// Interface fields :
	// ####################################################################################################
	
	public static final int TRANSPARENT = 0x00000000;
	public static final int WHITE = 0xFFFFFFFF;
	public static final int GRAY = 0xFFA0A0A0;
	public static final int BLACK = 0xFF000000;
	public static final int BLUE = 0xFF0077EE;
	public static final int GREEN = 0xFF44DD44;
	public static final int YELLOW = 0xFFDDCC44;
	public static final int RED = 0xFFDD8844;
	public static final int LIGHT_BLUE = 0xFFAACCEE;
	public static final int LIGHT_GREEN = 0xFF80FF20;
	public static final int LIGHT_YELLOW = 0xFFFFDD00;
	public static final int LIGHT_RED = 0xFFFF0000;
	
	public static final int PANEL_MARGIN = 5;
	public static final int MARGIN = 5;
	public static final int BONUS_MARGIN = 10;
	public static final int DHD_MARGIN = 1;
	public static final int ADDRESS_MARGIN = 3;
	public static final int FIELD_HEIGHT = 10;
	public static final int BUTTON_HEIGHT = 20;
	
	// ####################################################################################################
	// Data fields :
	// ####################################################################################################
	
	protected int nextYPos = 0;
	
	private int nextButtonId = 0;
	private final List<TextField> fieldList = new LinkedList<TextField>();
	private boolean initialized = false;
	
	// ####################################################################################################
	// Builder :
	// ####################################################################################################
	
	protected Screen() {
		super();
	}
	
	// ####################################################################################################
	// Container interface :
	// ####################################################################################################
	
	@Override
	public int getComponentWidth() {
		return this.width;
	}
	
	@Override
	public int getComponentHeight() {
		return this.height;
	}
	
	@Override
	public int getXPosInScreen(int xPos) {
		return xPos;
	}
	
	@Override
	public int getYPosInScreen(int yPos) {
		return yPos;
	}
	
	@Override
	public void drawText(FontRenderer fontRenderer, String text, int x, int y, int color) {
		this.drawString(fontRenderer, text, x, y + (FIELD_HEIGHT - fontRenderer.FONT_HEIGHT), color);
		this.nextYPos += FIELD_HEIGHT + MARGIN;
	}
	
	@Override
	public void drawCenteredText(FontRenderer fontRenderer, String text, int x, int y, int color) {
		this.drawCenteredString(fontRenderer, text, x, y + (FIELD_HEIGHT - fontRenderer.FONT_HEIGHT), color);
		this.nextYPos += FIELD_HEIGHT + MARGIN;
	}
	
	@Override
	public void drawCenteredText(FontRenderer fontRenderer, String text, int y, int color) {
		this.drawCenteredText(fontRenderer, text, this.width / 2, y, color);
	}
	
	@Override
	public void drawBox(int borderColor) {
		this.drawBox(0, 0, this.width, this.height, borderColor, TRANSPARENT, false);
	}
	
	@Override
	public void drawBox(int borderColor, int boxColor) {
		this.drawBox(0, 0, this.width, this.height, borderColor, boxColor, false);
	}
	
	@Override
	public void drawBox(int borderColor, boolean extendedLikeFields) {
		this.drawBox(0, 0, this.width, this.height, borderColor, TRANSPARENT, extendedLikeFields);
	}
	
	@Override
	public void drawBox(int borderColor, int boxColor, boolean extendedLikeFields) {
		this.drawBox(0, 0, this.width, this.height, borderColor, boxColor, extendedLikeFields);
	}
	
	@Override
	public void drawBox(int xPos, int yPos, int width, int height, int borderColor) {
		this.drawBox(xPos, yPos, width, height, borderColor, TRANSPARENT, false);
	}
	
	@Override
	public void drawBox(int xPos, int yPos, int width, int height, int borderColor, int boxColor) {
		this.drawBox(xPos, yPos, width, height, borderColor, boxColor, false);
	}
	
	@Override
	public void drawBox(int xPos, int yPos, int width, int height, int borderColor, boolean extendedLikeFields) {
		this.drawBox(xPos, yPos, width, height, borderColor, TRANSPARENT, extendedLikeFields);
	}
	
	@Override
	public void drawBox(int xPos, int yPos, int width, int height, int borderColor, int boxColor, boolean extendedLikeFields) {
		if(extendedLikeFields) {
			xPos -= 1;
			yPos -= 1;
			width += 2;
			height += 2;
		}
		
		if(boxColor == TRANSPARENT) {
			int x1 = xPos;
			int x2 = xPos + width;
			int y1 = yPos;
			int y2 = yPos + height;
			
			drawRect(x1, y1, x1 + 1, y2, borderColor);
			drawRect(x2 - 1, y1, x2, y2, borderColor);
			drawRect(x1, y1, x2, y1 + 1, borderColor);
			drawRect(x1, y2 - 1, x2, y2, borderColor);
		}
		else {
			drawRect(xPos, yPos, xPos + width, yPos + height, borderColor);
			drawRect(xPos + 1, yPos + 1, xPos + width - 1, yPos + height - 1, boxColor);
		}
	}
	
	// ####################################################################################################
	// Interface definition :
	// ####################################################################################################
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		this.drawDefaultBackground();
		
		for(GuiTextField field : this.fieldList) {
			field.drawTextBox();
		}
		
		super.drawScreen(par1, par2, par3);
	}
	
	/**
	 * This method is final, override initComponents instead.
	 */
	@Override
	public final void initGui() {
		Keyboard.enableRepeatEvents(true);
		this.buttonList.clear();
		this.fieldList.clear();
		
		this.initComponents();
		this.initialized = true;
		
		if(this.fieldList.size() > 0) {
			this.fieldList.get(0).setFocused(true);
		}
		
		this.onGuiInitialized();
	}
	
	/**
	 * Adds the buttons, the fields and other controls to the screen.
	 */
	protected void initComponents() {
		// Nothing here.
	}
	
	// ####################################################################################################
	// User input :
	// ####################################################################################################
	
	@Override
	protected void mouseClicked(int x, int y, int button) {
		try {
			if(button == 0) {
				for(int l = 0; l < this.buttonList.size(); ++l) {
					GuiButton guibutton = (GuiButton) this.buttonList.get(l);
					
					if(guibutton.mousePressed(this.mc, x, y)) {
						this.setSelectedButton(guibutton);
						String soundName = "random.click";
						
						if(guibutton instanceof Button) {
							soundName = StargateSounds.button.toString();
						}
						
						this.mc.sndManager.playSoundFX(soundName, 1.0F, 1.0F);
						this.actionPerformed(guibutton);
					}
				}
			}
		}
		catch(ReflectionException argh) {
			StargateMod.debug(argh.getMessage(), Level.WARNING, true);
			super.mouseClicked(x, y, button);
		}
		
		for(GuiTextField field : this.fieldList) {
			field.mouseClicked(x, y, button);
		}
	}
	
	@Override
	protected void keyTyped(char character, int key) {
		if(key == Keyboard.KEY_ESCAPE || (key == this.mc.gameSettings.keyBindInventory.keyCode && this.closeLikeInventory())) {
			this.close();
		}
		else if(key == Keyboard.KEY_RETURN) {
			this.onEnterPressed();
		}
		else if(key == Keyboard.KEY_TAB) {
			if(this.fieldList.size() > 1) {
				for(int i = 0; i < this.fieldList.size(); i++) {
					if(this.fieldList.get(i).isFocused()) {
						this.fieldList.get(i).setFocused(false);
						
						if(GuiScreen.isShiftKeyDown()) {
							i = (i == 0) ? (this.fieldList.size() - 1) : (i - 1);
						}
						else {
							i = (i == this.fieldList.size() - 1) ? (0) : (i + 1);
						}
						
						this.fieldList.get(i).setFocused(true);
						break;
					}
				}
			}
			else {
				this.specialTabAction();
			}
		}
		else {
			for(GuiTextField field : this.fieldList) {
				if(field.isFocused()) {
					boolean fieldChanged = field.textboxKeyTyped(character, key);
					
					if(fieldChanged) {
						this.onFieldChanged();
					}
				}
			}
		}
	}
	
	/**
	 * Close the gui.
	 */
	protected void close() {
		this.mc.displayGuiScreen(null);
	}
	
	/**
	 * Defines what happens when TAB is pressed, if there is at most 1 field.
	 */
	protected void specialTabAction() {
		// Nothing here.
	}
	
	/**
	 * Defines what happnens when ENTER is pressed.
	 */
	protected void onEnterPressed() {
		this.close();
	}
	
	protected boolean closeLikeInventory() {
		return true;
	}
	
	// ####################################################################################################
	// Utility :
	// ####################################################################################################
	
	/**
	 * Adds a component to the gui and increment the next Y position.
	 * @param component - the component to add to the gui.
	 * @return the added component.
	 */
	protected <T extends Component> T addComponent(T component) {
		return this.addComponent(component, true);
	}
	
	/**
	 * Adds a component to the gui and optionally increment the next Y position.
	 * @param component - the component to add to the gui.
	 * @param incrementYPos - indicates whether the next Y position have to be incremented.
	 * @return the added component.
	 */
	protected <T extends Component> T addComponent(T component, boolean incrementYPos) {
		if(component instanceof Button) {
			this.buttonList.add(component);
		}
		else if(component instanceof TextField) {
			this.fieldList.add((TextField) component);
		}
		
		if(incrementYPos) {
			this.nextYPos += component.getComponentHeight() + MARGIN;
		}
		
		return component;
	}
	
	/**
	 * Returns and increments the next button id.
	 * @return the next button id.
	 */
	protected int getNextButtonId() {
		return this.nextButtonId++;
	}
	
	/**
	 * Indicates if the components have been initialized.
	 * @return - true if the components have been initialized, else false.
	 */
	public boolean isInitialized() {
		return this.initialized;
	}
	
	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		Keyboard.enableRepeatEvents(false);
	}
	
	@Override
	public void updateScreen() {
		super.updateScreen();
		for(GuiTextField field : this.fieldList) {
			field.updateCursorCounter();
		}
	}
	
	/**
	 * Called just affter the gui is initialized.
	 */
	protected void onGuiInitialized() {
		this.onFieldChanged();
	}
	
	/**
	 * Called each time the content of a field changes.
	 */
	protected void onFieldChanged() {
		// Nothing here.
	}
	
}
