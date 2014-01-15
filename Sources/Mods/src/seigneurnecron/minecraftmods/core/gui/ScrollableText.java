package seigneurnecron.minecraftmods.core.gui;

import static seigneurnecron.minecraftmods.core.gui.GuiConstants.FIELD_HEIGHT;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.MARGIN;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class ScrollableText extends ScrollableList<ListProviderText> {
	
	// Fields :
	
	protected int color;
	
	// Constructors :
	
	public ScrollableText(ComponentContainer parent, int xPos, int yPos, int width, int height, Minecraft minecraft, ListProviderText listProvider, int color) {
		super(parent, xPos, yPos, width, height, minecraft, listProvider);
		this.color = color;
	}
	
	// Methods :
	
	@Override
	public int getSlotHeight() {
		return FIELD_HEIGHT + MARGIN;
	}
	
	@Override
	protected void drawSlots(int mouseX, int mouseY) {
		int shiftedTop = 2 - (int) this.scrollDistance;
		
		for(int index = 0; index < this.getSize(); ++index) {
			int top = shiftedTop + index * this.slotHeight - 2;
			
			if(top < this.height && top + this.slotHeight > 0) {
				this.drawSlotContent(index, 0, top);
			}
		}
		
	}
	
	/**
	 * Draws the content of a slot.
	 * @param index - the index of the slot.
	 * @param left - the X position of the slot.
	 * @param top - the Y position of the slot.
	 */
	protected void drawSlotContent(int index, int left, int top) {
		String element = this.listProvider.getList().get(index);
		FontRenderer fontRenderer = this.listProvider.getFontRenderer();
		
		int yPos = top + (MARGIN / 2) + FIELD_HEIGHT - fontRenderer.FONT_HEIGHT;
		
		if(yPos > 0 && yPos + FIELD_HEIGHT - 1 < this.height) {
			this.drawText(fontRenderer, fontRenderer.trimStringToWidth(element, this.width - (2 * MARGIN)), MARGIN, yPos, this.color);
		}
	}
	
}
