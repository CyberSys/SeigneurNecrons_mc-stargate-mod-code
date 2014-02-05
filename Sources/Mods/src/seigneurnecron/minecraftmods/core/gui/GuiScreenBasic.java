package seigneurnecron.minecraftmods.core.gui;

import static seigneurnecron.minecraftmods.core.gui.GuiConstants.MARGIN;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.TRANSPARENT;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.Tessellator;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import seigneurnecron.minecraftmods.core.SeigneurNecronMod;
import seigneurnecron.minecraftmods.core.reflection.Reflection;
import seigneurnecron.minecraftmods.core.reflection.ReflectionException;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * WARNING : A LOT OF CODE IN THIS CLASS IS THE SAME AS THE CODE IN GuiContainerBasic. DON'T FORGET TO CHECK AND DUPLICATE MODIFICATIONS. <br />
 * WARNING : SOME METHODS ARE DIFFERENT. CHECK EACH LINE BEFORE DUPLICATING A MODIFICATION. <br />
 * This code duplication was necessary because factorization would have to be done in GuiScreen, which is a Minecraft class, not a mod class.
 * 
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public abstract class GuiScreenBasic extends GuiScreen implements ComponentContainer {
	
	// ####################################################################################################
	// Lang constants :
	// ####################################################################################################
	
	public static final String INV_NAME_SCREEN = "container.screenBasic";
	
	public static final String KEY_ENTER = INV_NAME_SCREEN + ".enter";
	public static final String KEY_TAB = INV_NAME_SCREEN + ".tab";
	public static final String KEY_ESC = INV_NAME_SCREEN + ".esc";
	
	public static final String GUI_CANCEL = "gui.cancel";
	public static final String GUI_DONE = "gui.done";
	
	// ####################################################################################################
	// Reflection :
	// ####################################################################################################
	
	// Setters :
	
	private void setSelectedButton(GuiButton selectedButton) throws ReflectionException {
		// WARNING : This method is not in GuiContainerBasic.
		try {
			Reflection.set(GuiScreen.class, this, SeigneurNecronMod.instance.getConfig().guiScreen_selectedButton, selectedButton);
		}
		catch(Exception argh) {
			throw new ReflectionException(argh);
		}
	}
	
	// ####################################################################################################
	// Data fields :
	// ####################################################################################################
	
	protected int nextYPos = 0;
	private int nextButtonId = 0;
	private boolean initialized = false;
	
	private final List<TextField> fieldList = new LinkedList<TextField>();
	private final List<Label> labelList = new LinkedList<Label>();
	private final List<ScrollableAbstractList> scrollPaneList = new LinkedList<ScrollableAbstractList>();
	
	// ####################################################################################################
	// Constructors :
	// ####################################################################################################
	
	protected GuiScreenBasic() {
		super();
	}
	
	// ####################################################################################################
	// ComponentContainer interface :
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
	public int getXPos() {
		return 0;
	}
	
	@Override
	public int getYPos() {
		return 0;
	}
	
	@Override
	public int getRight() {
		return this.width;
	}
	
	@Override
	public int getBottom() {
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
		// WARNING : This method is different from the GuiContainerBasic one.
		this.drawString(fontRenderer, text, x, y, color);
	}
	
	@Override
	public void drawCenteredText(FontRenderer fontRenderer, String text, int x, int y, int color) {
		// WARNING : This method is different from the GuiContainerBasic one.
		this.drawCenteredString(fontRenderer, text, x, y, color);
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
		
		int x1 = xPos;
		int x2 = xPos + width;
		int y1 = yPos;
		int y2 = yPos + height;
		
		drawRect(x1, y1, x1 + 1, y2, borderColor);
		drawRect(x2 - 1, y1, x2, y2, borderColor);
		drawRect(x1, y1, x2, y1 + 1, borderColor);
		drawRect(x1, y2 - 1, x2, y2, borderColor);
		
		if(boxColor != TRANSPARENT) {
			drawRect(xPos + 1, yPos + 1, xPos + width - 1, yPos + height - 1, boxColor);
		}
	}
	
	@Override
	public void addVertexWithUV(Tessellator tessellator, double x, double y, double z, double u, double v) {
		// WARNING : This method is different from the GuiContainerBasic one.
		tessellator.addVertexWithUV(x, y, z, u, v);
	}
	
	@Override
	public void addVertex(Tessellator tessellator, double x, double y, double z) {
		// WARNING : This method is different from the GuiContainerBasic one.
		tessellator.addVertex(x, y, z);
	}
	
	@Override
	public <T extends Component> T addComponent(T component) {
		return this.addComponent(component, true);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends Component> T addComponent(T component, boolean incrementYPos) {
		if(component instanceof Button) {
			Button button = (Button) component;
			this.buttonList.add(button);
			button.id = this.getNextButtonId();
		}
		else if(component instanceof TextField) {
			TextField textField = (TextField) component;
			this.fieldList.add(textField);
		}
		else if(component instanceof Label) {
			Label label = (Label) component;
			this.labelList.add(label);
		}
		else if(component instanceof ScrollableAbstractList) {
			ScrollableAbstractList scrollPane = (ScrollableAbstractList) component;
			this.scrollPaneList.add(scrollPane);
		}
		
		if(incrementYPos) {
			this.nextYPos += component.getComponentHeight() + MARGIN;
		}
		
		return component;
	}
	
	// ####################################################################################################
	// Interface definition :
	// ####################################################################################################
	
	/**
	 * Indicates if the gui is valid or if it should be closed.
	 * @return true if the gui is valid, false if it should be closed.
	 */
	protected boolean isGuiValid() {
		// WARNING : This method is different from the GuiContainerBasic one.
		return true;
	}
	
	/**
	 * Indicates if the default background should be drawn.
	 * @return true if the default background should be drawn, else false.
	 */
	protected boolean shouldDrawDefaultBackground() {
		return false;
	}
	
	@Override
	public final void drawDefaultBackground() {
		if(this.shouldDrawDefaultBackground()) {
			super.drawDefaultBackground();
		}
	}
	
	@Override
	public final void drawScreen(int mouseX, int mouseY, float timeSinceLastTick) {
		// WARNING : This method is is different from the GuiContainerBasic one.
		if(this.isGuiValid()) {
			this.updateComponents();
			
			this.drawDefaultBackground();
			this.drawBackground(mouseX, mouseY, timeSinceLastTick);
			
			for(GuiTextField field : this.fieldList) {
				field.drawTextBox();
			}
			
			for(Label label : this.labelList) {
				label.drawScreen();
			}
			
			for(ScrollableAbstractList scrollPane : this.scrollPaneList) {
				scrollPane.drawList(mouseX, mouseY);
			}
			
			super.drawScreen(mouseX, mouseY, timeSinceLastTick);
			this.drawForeground(mouseX, mouseY);
		}
		else {
			this.close();
		}
	}
	
	/**
	 * Update information needed to draw the background and the components.
	 */
	protected void updateComponents() {
		// Nothing here.
	}
	
	/**
	 * Draw the background.
	 */
	protected void drawBackground(int mouseX, int mouseY, float timeSinceLastTick) {
		// WARNING : This method is different from the GuiScreenBasic one.
		// Nothing here.
	}
	
	/**
	 * Draw the forground. No need to draw fields, labels and buttons, it's already done.
	 */
	protected void drawForeground(int mouseX, int mouseY) {
		// Nothing here.
	}
	
	/**
	 * This method is final, override initComponents instead.
	 */
	@Override
	public final void initGui() {
		// WARNING : This method is different from the GuiContainerBasic one.
		super.initGui();
		
		Keyboard.enableRepeatEvents(true);
		this.buttonList.clear();
		this.fieldList.clear();
		this.labelList.clear();
		this.scrollPaneList.clear();
		
		this.initComponents();
		this.initialized = true;
		
		if(this.fieldList.size() > 0) {
			this.fieldList.get(0).setFocused(true);
		}
		
		this.onGuiInitialized();
	}
	
	/**
	 * Adds panels, buttons, fields, labels and other components to the screen.
	 */
	protected void initComponents() {
		// Nothing here.
	}
	
	// ####################################################################################################
	// User input :
	// ####################################################################################################
	
	@Override
	protected void mouseClicked(int x, int y, int button) {
		// WARNING : This method is different from the GuiContainerBasic one.
		try {
			if(button == 0) {
				for(int i = 0; i < this.buttonList.size(); ++i) {
					GuiButton guibutton = (GuiButton) this.buttonList.get(i);
					
					if(guibutton.mousePressed(this.mc, x, y)) {
						this.setSelectedButton(guibutton);
						String soundName = "random.click";
						
						if(guibutton instanceof Button) {
							soundName = ((Button) guibutton).getSoundName();
						}
						
						this.mc.sndManager.playSoundFX(soundName, 1.0F, 1.0F);
						this.actionPerformed(guibutton);
					}
				}
			}
		}
		catch(ReflectionException argh) {
			SeigneurNecronMod.instance.log(argh.getMessage(), Level.WARNING);
			super.mouseClicked(x, y, button);
		}
		
		for(GuiTextField field : this.fieldList) {
			field.mouseClicked(x, y, button);
		}
	}
	
	@Override
	public void handleMouseInput() {
		super.handleMouseInput();
		
		int mouseX = getMouseXFromEvent();
		int mouseY = getMouseYFromEvent();
		
		for(ScrollableAbstractList scrollPane : this.scrollPaneList) {
			scrollPane.handleMouseInput(mouseX, mouseY);
		}
	}
	
	@Override
	protected void keyTyped(char character, int key) {
		// WARNING : This method is different from the GuiContainerBasic one.
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
		// WARNING : This method is different from the GuiContainerBasic one.
		this.mc.displayGuiScreen(null);
		this.mc.setIngameFocus();
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
	
	/**
	 * Determines if the gui have to be closed when inventory key is pressed.
	 * @return true if the gui have to be closed when inventory key is pressed, else false.
	 */
	protected boolean closeLikeInventory() {
		return true;
	}
	
	/**
	 * Returns the X position of the mouse during the last mouse event.
	 * @return the X position of the mouse during the last mouse event.
	 */
	protected final int getMouseXFromEvent() {
		return Mouse.getEventX() * this.width / this.mc.displayWidth;
	}
	
	/**
	 * Returns the Y position of the mouse during the last mouse event.
	 * @return the Y position of the mouse during the last mouse event.
	 */
	protected final int getMouseYFromEvent() {
		return this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
	}
	
	// ####################################################################################################
	// Utility :
	// ####################################################################################################
	
	/**
	 * Returns and increments the next button id.
	 * @return the next button id.
	 */
	public int getNextButtonId() {
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
