package seigneurnecron.minecraftmods.core.gui;

import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public abstract class CustomGuiScrollingList {
	
	protected final int listWidth;
	protected final int listHeight;
	protected final int top;
	protected final int bottom;
	protected final int left;
	protected final int right;
	protected final int slotHeight;
	protected int scrollUpActionId;
	protected int scrollDownActionId;
	protected int mouseX;
	protected int mouseY;
	protected float initialMouseClickY = -2.0F;
	protected float scrollFactor;
	protected float scrollDistance;
	protected int selectedIndex = -1;
	protected long lastClickTime = 0L;
	protected boolean field_25123_p = true;
	protected boolean field_27262_q;
	protected int field_27261_r;
	
	public CustomGuiScrollingList(int left, int top, int width, int height, int entryHeight) {
		this.left = left;
		this.top = top;
		this.right = left + width;
		this.bottom = top + height;
		this.listWidth = width;
		this.listHeight = height;
		this.slotHeight = entryHeight;
	}
	
	public void func_27258_a(boolean p_27258_1_) {
		this.field_25123_p = p_27258_1_;
	}
	
	protected void func_27259_a(boolean p_27259_1_, int p_27259_2_) {
		this.field_27262_q = p_27259_1_;
		this.field_27261_r = p_27259_2_;
		
		if(!p_27259_1_) {
			this.field_27261_r = 0;
		}
	}
	
	protected abstract int getSize();
	
	protected abstract void elementClicked(int index, boolean doubleClick);
	
	protected abstract boolean isSelected(int index);
	
	protected int getContentHeight() {
		return this.getSize() * this.slotHeight + this.field_27261_r;
	}
	
	protected abstract void drawSlot(int index, int left, int top, int slotHeight, Tessellator tessellator);
	
	protected void func_27260_a(int p_27260_1_, int p_27260_2_, Tessellator p_27260_3_) {
		// Nothing here.
	}
	
	protected void func_27255_a(int p_27255_1_, int p_27255_2_) {
		// Nothing here.
	}
	
	protected void func_27257_b(int p_27257_1_, int p_27257_2_) {
		// Nothing here.
	}
	
	public int func_27256_c(int p_27256_1_, int p_27256_2_) {
		int var3 = this.left + 1;
		int var4 = this.left + this.listWidth - 7;
		int var5 = p_27256_2_ - this.top - this.field_27261_r + (int) this.scrollDistance;
		int var6 = var5 / this.slotHeight;
		return p_27256_1_ >= var3 && p_27256_1_ <= var4 && var6 >= 0 && var5 >= 0 && var6 < this.getSize() ? var6 : -1;
	}
	
	public void registerScrollButtons(List buttonList, int scrollUpActionId, int scrollDownActionId) {
		this.scrollUpActionId = scrollUpActionId;
		this.scrollDownActionId = scrollDownActionId;
	}
	
	protected void applyScrollLimits() {
		int var1 = this.getContentHeight() - (this.bottom - this.top);
		
		if(var1 < 0) {
			var1 /= 2;
		}
		
		if(this.scrollDistance < 0.0F) {
			this.scrollDistance = 0.0F;
		}
		
		if(this.scrollDistance > var1) {
			this.scrollDistance = var1;
		}
	}
	
	public void actionPerformed(GuiButton button) {
		if(button.enabled) {
			if(button.id == this.scrollUpActionId) {
				this.scrollDistance -= this.slotHeight * 2 / 3;
				this.initialMouseClickY = -2.0F;
				this.applyScrollLimits();
			}
			else if(button.id == this.scrollDownActionId) {
				this.scrollDistance += this.slotHeight * 2 / 3;
				this.initialMouseClickY = -2.0F;
				this.applyScrollLimits();
			}
		}
	}
	
	public void drawScreen(int mouseX, int mouseY, float p_22243_3_) {
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		int listLength = this.getSize();
		int scrollBarXStart = this.left + this.listWidth - 6;
		int scrollBarXEnd = scrollBarXStart + 6;
		int boxLeft = this.left;
		int boxRight = scrollBarXStart - 1;
		int var10;
		int index;
		int var13;
		int var19;
		
		if(Mouse.isButtonDown(0)) {
			if(this.initialMouseClickY == -1.0F) {
				boolean var7 = true;
				
				if(mouseY >= this.top && mouseY <= this.bottom) {
					var10 = mouseY - this.top - this.field_27261_r + (int) this.scrollDistance;
					index = var10 / this.slotHeight;
					
					if(mouseX >= boxLeft && mouseX <= boxRight && index >= 0 && var10 >= 0 && index < listLength) {
						boolean var12 = index == this.selectedIndex && System.currentTimeMillis() - this.lastClickTime < 250L;
						this.elementClicked(index, var12);
						this.selectedIndex = index;
						this.lastClickTime = System.currentTimeMillis();
					}
					else if(mouseX >= boxLeft && mouseX <= boxRight && var10 < 0) {
						this.func_27255_a(mouseX - boxLeft, mouseY - this.top + (int) this.scrollDistance);
						var7 = false;
					}
					
					if(mouseX >= scrollBarXStart && mouseX <= scrollBarXEnd) {
						this.scrollFactor = -1.0F;
						var19 = this.getContentHeight() - (this.bottom - this.top);
						
						if(var19 < 1) {
							var19 = 1;
						}
						
						var13 = (int) ((float) ((this.bottom - this.top) * (this.bottom - this.top)) / (float) this.getContentHeight());
						
						if(var13 < 32) {
							var13 = 32;
						}
						
						if(var13 > this.bottom - this.top - 8) {
							var13 = this.bottom - this.top - 8;
						}
						
						this.scrollFactor /= (float) (this.bottom - this.top - var13) / (float) var19;
					}
					else {
						this.scrollFactor = 1.0F;
					}
					
					if(var7) {
						this.initialMouseClickY = mouseY;
					}
					else {
						this.initialMouseClickY = -2.0F;
					}
				}
				else {
					this.initialMouseClickY = -2.0F;
				}
			}
			else if(this.initialMouseClickY >= 0.0F) {
				this.scrollDistance -= (mouseY - this.initialMouseClickY) * this.scrollFactor;
				this.initialMouseClickY = mouseY;
			}
		}
		else {
			while(Mouse.next()) {
				int var16 = Mouse.getEventDWheel();
				
				if(var16 != 0) {
					if(var16 > 0) {
						var16 = -1;
					}
					else if(var16 < 0) {
						var16 = 1;
					}
					
					this.scrollDistance += var16 * this.slotHeight / 2;
				}
			}
			
			this.initialMouseClickY = -1.0F;
		}
		
		this.applyScrollLimits();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_FOG);
		Tessellator tessellator = Tessellator.instance;
		int shiftedTop = this.top + 2 - (int) this.scrollDistance;
		
		if(this.field_27262_q) {
			this.func_27260_a(boxRight, shiftedTop, tessellator);
		}
		
		for(index = 0; index < listLength; ++index) {
			int top = shiftedTop + index * this.slotHeight + this.field_27261_r - 2;
			int slotHeight = this.slotHeight;
			
			if(top < this.bottom && top + slotHeight > this.top) {
				if(this.field_25123_p && this.isSelected(index)) {
					int borderLeft = boxLeft;
					int borderRight = boxRight;
					int borderTop = top;
					int borderBottom = top + slotHeight;
					
					int panelLeft = borderLeft + 1;
					int panelRight = borderRight - 1;
					int panelTop = borderTop + 1;
					int panelBottom = borderBottom - 1;
					
					if(borderTop < this.top) {
						borderTop = this.top;
						panelTop = this.top;
					}
					
					if(borderBottom > this.bottom) {
						borderBottom = this.bottom;
						panelBottom = this.bottom;
					}
					
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					GL11.glDisable(GL11.GL_TEXTURE_2D);
					tessellator.startDrawingQuads();
					tessellator.setColorOpaque_I(0x808080);
					tessellator.addVertexWithUV(borderLeft, borderBottom, 0.0D, 0.0D, 1.0D);
					tessellator.addVertexWithUV(borderRight, borderBottom, 0.0D, 1.0D, 1.0D);
					tessellator.addVertexWithUV(borderRight, borderTop, 0.0D, 1.0D, 0.0D);
					tessellator.addVertexWithUV(borderLeft, borderTop, 0.0D, 0.0D, 0.0D);
					tessellator.setColorOpaque_I(0x000000);
					tessellator.addVertexWithUV(panelLeft, panelBottom, 0.0D, 0.0D, 1.0D);
					tessellator.addVertexWithUV(panelRight, panelBottom, 0.0D, 1.0D, 1.0D);
					tessellator.addVertexWithUV(panelRight, panelTop, 0.0D, 1.0D, 0.0D);
					tessellator.addVertexWithUV(panelLeft, panelTop, 0.0D, 0.0D, 0.0D);
					tessellator.draw();
					GL11.glEnable(GL11.GL_TEXTURE_2D);
				}
				
				this.drawSlot(index, boxLeft, top, slotHeight, tessellator);
			}
		}
		
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		var19 = this.getContentHeight() - (this.bottom - this.top);
		
		if(var19 > 0) {
			var13 = (this.bottom - this.top) * (this.bottom - this.top) / this.getContentHeight();
			
			if(var13 < 32) {
				var13 = 32;
			}
			
			if(var13 > this.bottom - this.top - 8) {
				var13 = this.bottom - this.top - 8;
			}
			
			int var14 = (int) this.scrollDistance * (this.bottom - this.top - var13) / var19 + this.top;
			
			if(var14 < this.top) {
				var14 = this.top;
			}
			
			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_I(0, 255);
			tessellator.addVertexWithUV(scrollBarXStart, this.bottom, 0.0D, 0.0D, 1.0D);
			tessellator.addVertexWithUV(scrollBarXEnd, this.bottom, 0.0D, 1.0D, 1.0D);
			tessellator.addVertexWithUV(scrollBarXEnd, this.top, 0.0D, 1.0D, 0.0D);
			tessellator.addVertexWithUV(scrollBarXStart, this.top, 0.0D, 0.0D, 0.0D);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_I(8421504, 255);
			tessellator.addVertexWithUV(scrollBarXStart, var14 + var13, 0.0D, 0.0D, 1.0D);
			tessellator.addVertexWithUV(scrollBarXEnd, var14 + var13, 0.0D, 1.0D, 1.0D);
			tessellator.addVertexWithUV(scrollBarXEnd, var14, 0.0D, 1.0D, 0.0D);
			tessellator.addVertexWithUV(scrollBarXStart, var14, 0.0D, 0.0D, 0.0D);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_I(12632256, 255);
			tessellator.addVertexWithUV(scrollBarXStart, var14 + var13 - 1, 0.0D, 0.0D, 1.0D);
			tessellator.addVertexWithUV(scrollBarXEnd - 1, var14 + var13 - 1, 0.0D, 1.0D, 1.0D);
			tessellator.addVertexWithUV(scrollBarXEnd - 1, var14, 0.0D, 1.0D, 0.0D);
			tessellator.addVertexWithUV(scrollBarXStart, var14, 0.0D, 0.0D, 0.0D);
			tessellator.draw();
		}
		
		this.func_27257_b(mouseX, mouseY);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_BLEND);
	}
	
}
