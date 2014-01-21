package seigneurnecron.minecraftmods.core.gui;

import static seigneurnecron.minecraftmods.core.gui.GuiConstants.BACKGROUND_COLOR;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.LIGHT_BLUE;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.MARGIN;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.PANEL_MARGIN;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.SLOT_BACKGROUND_COLOR;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.SLOT_BORDER_COLOR;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.TRANSPARENT;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import seigneurnecron.minecraftmods.core.inventory.ContainerBasic;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * WARNING : A LOT OF CODE IN THIS CLASS IS THE SAME AS THE CODE IN GuiScreenBasic. DON'T FORGET TO CHECK AND DUPLICATE MODIFICATIONS. <br />
 * WARNING : SOME METHODS ARE DIFFERENT. CHECK EACH LINE BEFORE DUPLICATING A MODIFICATION. <br />
 * This code duplication was necessary because factorization would have to be done in GuiScreen, which is a Minecraft class, not a mod class.
 * 
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public abstract class GuiContainerBasic<T extends ContainerBasic> extends GuiContainer implements ComponentContainer {
	
	// ####################################################################################################
	// Lang constants :
	// ####################################################################################################
	
	public static final String INV_NAME = "container.containerBasic";
	
	public static final String INVENTORY = INV_NAME + ".inventory";
	public static final String TOOL_BAR = INV_NAME + ".toolBar";
	
	// ####################################################################################################
	// Interface fields :
	// ####################################################################################################
	
	protected Panel panel_main;
	protected Panel panel_container;
	protected Panel panel_inventory;
	protected Panel panel_toolBar;
	protected List<Panel> slotPanels = new LinkedList<Panel>();
	
	protected Label label_container;
	protected Label label_inventory;
	protected Label label_toolBar;
	
	// ####################################################################################################
	// Data fields :
	// ####################################################################################################
	
	protected final T container;
	
	protected int nextYPos = 0;
	private int nextButtonId = 0;
	private boolean initialized = false;
	
	private final List<TextField> fieldList = new LinkedList<TextField>();
	private final List<Label> labelList = new LinkedList<Label>();
	private final List<ScrollableAbstractList> scrollPaneList = new LinkedList<ScrollableAbstractList>();
	
	// ####################################################################################################
	// Constructors :
	// ####################################################################################################
	
	protected GuiContainerBasic(T container) {
		super(container);
		this.container = container;
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
		// WARNING : This method is different from the GuiScreenBasic one.
		this.drawString(fontRenderer, text, x - this.guiLeft, y - this.guiTop, color);
	}
	
	@Override
	public void drawCenteredText(FontRenderer fontRenderer, String text, int x, int y, int color) {
		// WARNING : This method is different from the GuiScreenBasic one.
		this.drawCenteredString(fontRenderer, text, x - this.guiLeft, y - this.guiTop, color);
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
		// WARNING : This method is different from the GuiScreenBasic one.
		tessellator.addVertexWithUV(x - this.guiLeft, y - this.guiTop, z, u, v);
	}
	
	@Override
	public void addVertex(Tessellator tessellator, double x, double y, double z) {
		// WARNING : This method is different from the GuiScreenBasic one.
		tessellator.addVertex(x - this.guiLeft, y - this.guiTop, z);
	}
	
	@Override
	public <C extends Component> C addComponent(C component) {
		return this.addComponent(component, true);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <C extends Component> C addComponent(C component, boolean incrementYPos) {
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
		// WARNING : This method is is different from the GuiScreenBasic one.
		if(this.isGuiValid()) {
			this.updateComponents();
			super.drawScreen(mouseX, mouseY, timeSinceLastTick);
		}
		else {
			this.close();
		}
	}
	
	@Override
	protected final void drawGuiContainerBackgroundLayer(float timeSinceLastTick, int mouseX, int mouseY) {
		// WARNING : This method is not in GuiScreenBasic.
		this.drawBackground(mouseX, mouseY, timeSinceLastTick);
	}
	
	@Override
	protected final void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		// WARNING : This method is not in GuiScreenBasic.
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
		for(GuiTextField field : this.fieldList) {
			field.drawTextBox();
		}
		
		for(Label label : this.labelList) {
			label.drawScreen();
		}
		
		for(ScrollableAbstractList scrollPane : this.scrollPaneList) {
			scrollPane.drawList(mouseX, mouseY);
		}
		
		this.drawForeground(mouseX, mouseY);
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
		this.panel_container.drawBox(LIGHT_BLUE, BACKGROUND_COLOR);
		this.panel_inventory.drawBox(LIGHT_BLUE, BACKGROUND_COLOR);
		this.panel_toolBar.drawBox(LIGHT_BLUE, BACKGROUND_COLOR);
		
		for(Panel panel : this.slotPanels) {
			panel.drawBox(SLOT_BORDER_COLOR, SLOT_BACKGROUND_COLOR);
		}
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
		// WARNING : This method is different from the GuiScreenBasic one.
		super.initGui();
		
		Keyboard.enableRepeatEvents(true);
		this.buttonList.clear();
		this.fieldList.clear();
		this.labelList.clear();
		this.scrollPaneList.clear();
		
		this.xSize = this.container.mainPanelWidth();
		this.ySize = this.container.mainPanelHeight();
		
		this.initComponents();
		this.initInventory();
		
		this.guiLeft = this.panel_main.getXPosInScreen(0);
		this.guiTop = this.panel_main.getYPosInScreen(0);
		
		this.initialized = true;
		
		if(this.fieldList.size() > 0) {
			this.fieldList.get(0).setFocused(true);
		}
		
		this.onGuiInitialized();
	}
	
	/**
	 * Adds the inventory components to the screen. The mainPanel has to be initialized before.
	 */
	protected void initInventory() {
		// Component sizes :
		
		int slotSizeWithBorder = this.container.slotSizeWithBorder();
		int slotSizePlusMargin = this.container.slotSizePlusMargin();
		int titleMargin = this.container.titleMargin() + this.container.borderSize();
		int firstSlotXPos = this.container.firstSlotXPos();
		int firstSlotYPos = this.container.firstSlotYPos();
		
		int panelWidth = this.panel_main.width;
		int labelWidth = panelWidth - (2 * titleMargin);
		
		// Panels :
		
		this.panel_container = new Panel(this.panel_main, 0, 0, panelWidth, this.container.containerHeight());
		this.panel_inventory = new Panel(this.panel_main, 0, this.panel_container.getBottom() + PANEL_MARGIN, panelWidth, this.container.inventoryHeight());
		this.panel_toolBar = new Panel(this.panel_main, 0, this.panel_inventory.getBottom() + PANEL_MARGIN, panelWidth, this.container.toolBarHeight());
		
		this.slotPanels.clear();
		
		for(int i = 0; i < 9; i++) {
			this.slotPanels.add(new Panel(this.panel_toolBar, firstSlotXPos + i * slotSizePlusMargin, firstSlotYPos, slotSizeWithBorder, slotSizeWithBorder));
			
			for(int j = 0; j < 3; j++) {
				this.slotPanels.add(new Panel(this.panel_inventory, firstSlotXPos + i * slotSizePlusMargin, firstSlotYPos + j * slotSizePlusMargin, slotSizeWithBorder, slotSizeWithBorder));
			}
		}
		
		// Labels :
		
		String invName = this.fontRenderer.trimStringToWidth(I18n.getString(this.container.inventory.getInvName()), labelWidth);
		
		this.label_container = this.addComponent(new Label(this.panel_container, this.fontRenderer, titleMargin, titleMargin, labelWidth, invName));
		this.label_inventory = this.addComponent(new Label(this.panel_inventory, this.fontRenderer, titleMargin, titleMargin, labelWidth, I18n.getString(INVENTORY)));
		this.label_toolBar = this.addComponent(new Label(this.panel_toolBar, this.fontRenderer, titleMargin, titleMargin, labelWidth, I18n.getString(TOOL_BAR)));
	}
	
	/**
	 * Adds panels, buttons, fields, labels and other components to the screen. <br />
	 * WARNING : The mainPanel has to be be initialized in this method.
	 */
	protected abstract void initComponents();
	
	// ####################################################################################################
	// User input :
	// ####################################################################################################
	
	@Override
	protected void mouseClicked(int x, int y, int button) {
		// WARNING : This method is different from the GuiScreenBasic one.
		super.mouseClicked(x, y, button);
		
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
		// WARNING : This method is different from the GuiScreenBasic one.
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
			boolean fieldFocused = false;
			
			for(GuiTextField field : this.fieldList) {
				if(field.isFocused()) {
					fieldFocused = true;
					boolean fieldChanged = field.textboxKeyTyped(character, key);
					
					if(fieldChanged) {
						this.onFieldChanged();
					}
				}
			}
			
			if(!fieldFocused && key != this.mc.gameSettings.keyBindInventory.keyCode) {
				super.keyTyped(character, key);
			}
		}
	}
	
	/**
	 * Close the gui.
	 */
	protected void close() {
		// WARNING : This method is different from the GuiScreenBasic one.
		this.mc.thePlayer.closeScreen();
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
