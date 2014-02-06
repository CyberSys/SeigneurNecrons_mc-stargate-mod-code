package seigneurnecron.minecraftmods.core.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public abstract class ScrollableAbstractList extends Panel {
	
	// Constants :

	protected static final float NO_CLICK = -1.0F;
	protected static final float OUT_OF_LIST = -2.0F;
	protected static final int MIN_CURSOR_HEIGHT = 32;
	protected static final int MIN_CURSOR_MARGIN = 8;
	
	// Fields :
	
	private final Minecraft minecraft;
	protected final int slotHeight;
	
	protected float initialMouseClickY = -2.0F;
	protected float scrollFactor;
	protected float scrollDistance;
	protected int selectedIndex = -1;
	protected long lastClickTime = 0L;
	
	// Constructors :
	
	protected ScrollableAbstractList(ComponentContainer parent, int xPos, int yPos, int width, int height, Minecraft minecraft) {
		super(parent, xPos, yPos, width, height);
		this.minecraft = minecraft;
		this.slotHeight = this.getSlotHeight();
	}
	
	// Methods :
	
	/**
	 * Returns the number of elements in the list.
	 * @return the number of elements in the list.
	 */
	public abstract int getSize();
	
	/**
	 * Returns the height of a slot.
	 * @return the height of a slot.
	 */
	public abstract int getSlotHeight();
	
	/**
	 * Returns the width of the scroll bar.
	 * @return the width of the scroll bar.
	 */
	public int getScrollBarWidth() {
		return 6;
	}
	
	/**
	 * Returns the margin between the content and the scroll bar.
	 * @return the margin between the content and the scroll bar.
	 */
	public int getScrollBarMargin() {
		return 1;
	}
	
	/**
	 * Returns the width of the list.
	 * @return the width of the list.
	 */
	public int getContentWidth() {
		return this.width - this.getScrollBarWidth() - this.getScrollBarMargin();
	}
	
	/**
	 * Returns the total height of the list.
	 * @return the total height of the list.
	 */
	public int getContentHeight() {
		return this.getSize() * this.slotHeight;
	}
	
	/**
	 * Returns the height of the content that can't be displayed on the screen. Negative if the content is shorter than the list.
	 * @return the height of the content that can't be displayed on the screen.
	 */
	public int getMissingHeight() {
		return this.getContentHeight() - this.height;
	}
	
	/**
	 * Draws the list.
	 * @param mouseX - the X position of the mouse, relative to the screen.
	 * @param mouseY - the Y position of the mouse, relative to the screen.
	 */
	public final void drawList(int mouseX, int mouseY) {
		this.drawListInner(mouseX - this.getXPosInScreen(0), mouseY - this.getYPosInScreen(0));
	}
	
	/**
	 * Draws the list.
	 * @param mouseX - the X position of the mouse, relative to the list.
	 * @param mouseY - the Y position of the mouse, relative to the list.
	 */
	private void drawListInner(int mouseX, int mouseY) {
		this.updateScrolling(mouseX, mouseY);
		
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_FOG);
		
		this.drawSlots(mouseX, mouseY);
		this.drawScrollBar();
	}
	
	/**
	 * Update the scrolling state.
	 * @param mouseX - the X position of the mouse, relative to the list.
	 * @param mouseY - the Y position of the mouse, relative to the list.
	 */
	private void updateScrolling(int mouseX, int mouseY) {
		if(Mouse.isButtonDown(0)) {
			if(this.initialMouseClickY == NO_CLICK) {
				if(mouseX >= 0 && mouseX <= this.width && mouseY >= 0 && mouseY <= this.height) {
					if(mouseX >= this.width - this.getScrollBarWidth() && mouseX <= this.width) {
						this.scrollFactor = -1.0F;
						int missingHeight = this.getMissingHeight();
						
						if(missingHeight < 1) {
							missingHeight = 1;
						}
						
						int cursorHeight = (int) ((float) (this.height * this.height) / (float) this.getContentHeight());
						
						if(cursorHeight < MIN_CURSOR_HEIGHT) {
							cursorHeight = MIN_CURSOR_HEIGHT;
						}
						
						if(cursorHeight > this.height - MIN_CURSOR_MARGIN) {
							cursorHeight = this.height - MIN_CURSOR_MARGIN;
						}
						
						this.scrollFactor /= (float) (this.height - cursorHeight) / (float) missingHeight;
					}
					else {
						this.scrollFactor = 1.0F;
					}
					
					this.initialMouseClickY = mouseY;
				}
				else {
					this.initialMouseClickY = OUT_OF_LIST;
				}
			}
			else if(this.initialMouseClickY >= 0.0F) {
				this.scrollDistance -= (mouseY - this.initialMouseClickY) * this.scrollFactor;
				this.initialMouseClickY = mouseY;
			}
		}
		else {
			this.initialMouseClickY = NO_CLICK;
		}
		
		this.applyScrollLimits();
	}
	
	/**
	 * Draws the slots of the list.
	 * @param mouseX - the X position of the mouse, relative to the list.
	 * @param mouseY - the Y position of the mouse, relative to the list.
	 */
	protected abstract void drawSlots(int mouseX, int mouseY);
	
	/**
	 * Draws the scroll bar.
	 */
	protected void drawScrollBar() {
		int scrollBarRight = this.width;
		int scrollBarLeft = scrollBarRight - this.getScrollBarWidth();
		
		int missingHeight = this.getMissingHeight();
		
		if(missingHeight > 0) {
			int cursorHeight = (int) ((float) (this.height * this.height) / (float) this.getContentHeight());
			
			if(cursorHeight < MIN_CURSOR_HEIGHT) {
				cursorHeight = MIN_CURSOR_HEIGHT;
			}
			
			if(cursorHeight > this.height - MIN_CURSOR_MARGIN) {
				cursorHeight = this.height - MIN_CURSOR_MARGIN;
			}
			
			int cursorTop = (int) this.scrollDistance * (this.height - cursorHeight) / missingHeight;
			
			if(cursorTop < 0) {
				cursorTop = 0;
			}
			
			Tessellator tessellator = Tessellator.instance;
			
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glShadeModel(GL11.GL_SMOOTH);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			
			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_I(0, 255);
			this.addVertexWithUV(tessellator, scrollBarLeft, this.height, 0, 0, 1);
			this.addVertexWithUV(tessellator, scrollBarRight, this.height, 0, 1, 1);
			this.addVertexWithUV(tessellator, scrollBarRight, 0, 0, 1, 0);
			this.addVertexWithUV(tessellator, scrollBarLeft, 0, 0, 0, 0);
			tessellator.draw();
			
			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_I(8421504, 255);
			this.addVertexWithUV(tessellator, scrollBarLeft, cursorTop + cursorHeight, 0, 0, 1);
			this.addVertexWithUV(tessellator, scrollBarRight, cursorTop + cursorHeight, 0, 1, 1);
			this.addVertexWithUV(tessellator, scrollBarRight, cursorTop, 0, 1, 0);
			this.addVertexWithUV(tessellator, scrollBarLeft, cursorTop, 0, 0, 0);
			tessellator.draw();
			
			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_I(12632256, 255);
			this.addVertexWithUV(tessellator, scrollBarLeft + 1, cursorTop + cursorHeight - 1, 0, 0, 1);
			this.addVertexWithUV(tessellator, scrollBarRight - 1, cursorTop + cursorHeight - 1, 0, 1, 1);
			this.addVertexWithUV(tessellator, scrollBarRight - 1, cursorTop + 1, 0, 1, 0);
			this.addVertexWithUV(tessellator, scrollBarLeft + 1, cursorTop + 1, 0, 0, 0);
			tessellator.draw();
			
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glShadeModel(GL11.GL_FLAT);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
		}
	}
	
	/**
	 * Handle the mouse input.
	 * @param mouseX - the X position of the mouse, relative to the screen.
	 * @param mouseY - the Y position of the mouse, relative to the screen.
	 */
	public final void handleMouseInput(int mouseX, int mouseY) {
		this.handleMouseInputInner(mouseX - this.getXPosInScreen(0), mouseY - this.getYPosInScreen(0));
	}
	
	/**
	 * Handle the mouse input.
	 * @param mouseX - the X position of the mouse, relative to the list.
	 * @param mouseY - the Y position of the mouse, relative to the list.
	 */
	protected void handleMouseInputInner(int mouseX, int mouseY) {
		if(!Mouse.isButtonDown(0) && !this.minecraft.gameSettings.touchscreen && mouseX >= 0 && mouseX <= this.width && mouseY >= 0 && mouseY <= this.height) {
			int eventDWheel = Mouse.getEventDWheel();
			
			if(eventDWheel != 0) {
				if(eventDWheel > 0) {
					eventDWheel = -1;
				}
				else if(eventDWheel < 0) {
					eventDWheel = 1;
				}
				
				this.scrollDistance += eventDWheel * this.slotHeight / 2;
			}
		}
	}
	
	/**
	 * Prevent from scrolling to far.
	 */
	protected void applyScrollLimits() {
		int missingHeight = this.getMissingHeight();
		
		if(missingHeight < 0) {
			missingHeight /= 2;
		}
		
		if(this.scrollDistance < 0.0F) {
			this.scrollDistance = 0.0F;
		}
		
		if(this.scrollDistance > missingHeight) {
			this.scrollDistance = missingHeight;
		}
	}
	
}
