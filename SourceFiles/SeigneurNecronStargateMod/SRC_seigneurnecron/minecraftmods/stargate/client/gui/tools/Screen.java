package seigneurnecron.minecraftmods.stargate.client.gui.tools;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class Screen extends GuiScreen implements Container {
	
	// ####################################################################################################
	// Interface fields :
	// ####################################################################################################
	
	public static final int WHITE = 0xFFFFFFFF;
	public static final int GRAY = 0xFFA0A0A0;
	public static final int GREEN = 0xFF44DD44;
	public static final int YELLOW = 0xFFDDCC44;
	public static final int RED = 0xFFDD8844;
	public static final int LIGHT_GREEN = 0xFF80FF20;
	public static final int LIGHT_YELLOW = 0xFFFFDD00;
	public static final int LIGHT_RED = 0xFFFF0000;
	
	public static final int PANEL_MARGIN = 5;
	public static final int MARGIN = 5;
	public static final int BONUS_MARGIN = 10;
	public static final int CHEVRON_MARGIN = 1;
	public static final int FIELD_HEIGHT = 10;
	public static final int BUTTON_HEIGHT = 20;
	public static final int FIELD_OFFSET = -2;
	
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
	public int getXPosInScreen(int xPos) {
		return xPos;
	}
	
	@Override
	public int getYPosInScreen(int yPos) {
		return yPos;
	}
	
	@Override
	public int getWidth() {
		return this.width;
	}
	
	@Override
	public int getHeight() {
		return this.height;
	}
	
	@Override
	public void drawText(FontRenderer fontRenderer, String text, int x, int y, int color) {
		this.drawString(fontRenderer, text, x, y, color);
		this.nextYPos += FIELD_HEIGHT + MARGIN;
	}
	
	@Override
	public void drawCenteredText(FontRenderer fontRenderer, String text, int x, int y, int color) {
		this.drawCenteredString(fontRenderer, text, x, y, color);
		this.nextYPos += FIELD_HEIGHT + MARGIN;
	}
	
	@Override
	public void drawCenteredText(FontRenderer fontRenderer, String text, int y, int color) {
		this.drawCenteredText(fontRenderer, text, this.width / 2, y, color);
		this.nextYPos += FIELD_HEIGHT + MARGIN;
	}
	
	@Override
	public void drawBorder(int color) {
		this.drawBorder(0, 0, this.width, this.height, color);
	}
	
	@Override
	public void drawBorder(int xPos, int yPos, int width, int height, int color) {
		int x1 = xPos;
		int x2 = xPos + width;
		int y1 = yPos;
		int y2 = yPos + height;
		
		drawRect(x1, y1, x1 + 1, y2, color);
		drawRect(x2 - 1, y1, x2, y2, color);
		drawRect(x1, y1, x2, y1 + 1, color);
		drawRect(x1, y2 - 1, x2, y2, color);
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
		
		this.onFieldChanged();
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
		super.mouseClicked(x, y, button);
		
		for(GuiTextField field : this.fieldList) {
			field.mouseClicked(x, y, button);
		}
	}
	
	@Override
	protected void keyTyped(char character, int key) {
		if(key == Keyboard.KEY_ESCAPE || key == this.mc.gameSettings.keyBindInventory.keyCode) {
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
	
	// ####################################################################################################
	// Utility :
	// ####################################################################################################
	
	/**
	 * Adds a button to the gui, and increment the next Y position.
	 * @param button - the button to add to the gui.
	 * @return the added button.
	 */
	protected final Button addButton(Button button) {
		return this.addButton(button, true);
	}
	
	/**
	 * Adds a field to the gui, and increment the next Y position.
	 * @param field - the field to add to the gui.
	 * @return the added field.
	 */
	protected final TextField addField(TextField field) {
		return this.addField(field, true);
	}
	
	/**
	 * Adds a button to the gui, and optionally increment the next Y position.
	 * @param button - the button to add to the gui.
	 * @param incrementYPos - indicates whether the next Y position have to be incremented.
	 * @return the added button.
	 */
	protected final Button addButton(Button button, boolean incrementYPos) {
		this.buttonList.add(button);
		
		if(incrementYPos) {
			this.nextYPos += BUTTON_HEIGHT + MARGIN;
		}
		
		return button;
	}
	
	/**
	 * Adds a field to the gui, and optionally increment the next Y position.
	 * @param field - the field to add to the gui.
	 * @param incrementYPos - indicates whether the next Y position have to be incremented.
	 * @return the added field.
	 */
	protected final TextField addField(TextField field, boolean incrementYPos) {
		this.fieldList.add(field);
		
		if(incrementYPos) {
			this.nextYPos += FIELD_HEIGHT + MARGIN;
		}
		
		return field;
	}
	
	/**
	 * Returns and increments the next button id.
	 * @return the next button id.
	 */
	protected final int getNextButtonId() {
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
	 * Called each time the content of a field changes.
	 */
	protected void onFieldChanged() {
		// Nothing here.
	}
	
}
