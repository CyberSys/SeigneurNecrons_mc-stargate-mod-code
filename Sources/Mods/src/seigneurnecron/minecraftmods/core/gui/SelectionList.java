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
public abstract class SelectionList<T extends ListProviderSelect<? extends Object>> extends ScrollableList<T> {
	
	// Constants :
	
	protected static final long MAX_DOUBLE_CLICK_TIME = 250L;
	
	// Constructors :
	
	protected SelectionList(ComponentContainer parent, int xPos, int yPos, int width, int height, Minecraft minecraft, T listProvider) {
		super(parent, xPos, yPos, width, height, minecraft, listProvider);
	}
	
	// Methods :
	
	@Override
	protected void drawSlots(int mouseX, int mouseY) {
		Tessellator tessellator = Tessellator.instance;
		
		int borderLeft = 0;
		int borderRight = this.getContentWidth();
		int shiftedTop = 2 - (int) this.scrollDistance;
		
		for(int index = 0; index < this.getSize(); ++index) {
			int top = shiftedTop + index * this.slotHeight - 2;
			
			if(top < this.height && top + this.slotHeight > 0) {
				if(this.isSelected(index)) {
					int borderTop = top;
					int borderBottom = top + this.slotHeight;
					
					int panelLeft = borderLeft + 1;
					int panelRight = borderRight - 1;
					int panelTop = borderTop + 1;
					int panelBottom = borderBottom - 1;
					
					if(borderTop < 0) {
						borderTop = 0;
						panelTop = 0;
					}
					
					if(borderBottom > this.height) {
						borderBottom = this.height;
						panelBottom = this.height;
					}
					
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					GL11.glDisable(GL11.GL_TEXTURE_2D);
					tessellator.startDrawingQuads();
					tessellator.setColorOpaque_I(0x808080);
					this.addVertexWithUV(tessellator, borderLeft, borderBottom, 0, 0, 1);
					this.addVertexWithUV(tessellator, borderRight, borderBottom, 0, 1, 1);
					this.addVertexWithUV(tessellator, borderRight, borderTop, 0, 1, 0);
					this.addVertexWithUV(tessellator, borderLeft, borderTop, 0, 0, 0);
					tessellator.setColorOpaque_I(0x000000);
					this.addVertexWithUV(tessellator, panelLeft, panelBottom, 0, 0, 1);
					this.addVertexWithUV(tessellator, panelRight, panelBottom, 0, 1, 1);
					this.addVertexWithUV(tessellator, panelRight, panelTop, 0, 1, 0);
					this.addVertexWithUV(tessellator, panelLeft, panelTop, 0, 0, 0);
					tessellator.draw();
					GL11.glEnable(GL11.GL_TEXTURE_2D);
				}
				
				this.drawSlotContent(index, borderLeft, top);
			}
		}
	}
	
	/**
	 * Indicates whether the element at the given index is selected.
	 * @param index - the index of the element.
	 * @return true if the element is selected, else false.
	 */
	protected boolean isSelected(int index) {
		return this.listProvider.getSelectedIndex() == index;
	}
	
	/**
	 * Defines what happens when the element at the given index is clicked.
	 * @param index - the index of the element.
	 * @param doubleClick - whether the element was double clicked.
	 */
	protected void elementClicked(int index, boolean doubleClick) {
		this.listProvider.setSelectedIndex(index);
		
		if(doubleClick) {
			this.listProvider.onElementDoubleClicked();
		}
	}
	
	/**
	 * Draws the content of a slot.
	 * @param index - the index of the slot.
	 * @param left - the X position of the slot.
	 * @param top - the Y position of the slot.
	 */
	protected abstract void drawSlotContent(int index, int left, int top);
	
	@Override
	protected void handleMouseInputInner(int mouseX, int mouseY) {
		super.handleMouseInputInner(mouseX, mouseY);
		
		if(Mouse.isButtonDown(0) && Mouse.getEventDX() == 0 && Mouse.getEventDY() == 0 && Mouse.getEventDWheel() == 0) {
			int mouseYWithScroll = mouseY + (int) this.scrollDistance;
			int index = mouseYWithScroll / this.slotHeight;
			
			if(mouseX >= 0 && mouseX <= this.getContentWidth() && mouseY >= 0 && mouseY <= this.height && index >= 0 && index < this.getSize()) {
				boolean doubleClicked = index == this.selectedIndex && Minecraft.getSystemTime() - this.lastClickTime < MAX_DOUBLE_CLICK_TIME;
				this.elementClicked(index, doubleClicked);
				this.selectedIndex = index;
				this.lastClickTime = Minecraft.getSystemTime();
			}
		}
	}
	
}
