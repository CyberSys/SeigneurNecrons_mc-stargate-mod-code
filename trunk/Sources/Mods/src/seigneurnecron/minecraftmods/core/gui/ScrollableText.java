package seigneurnecron.minecraftmods.core.gui;

import static seigneurnecron.minecraftmods.core.gui.GuiConstants.FIELD_HEIGHT;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.GRAY;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.MARGIN;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

/**
 * @author Seigneur Necron
 */
public class ScrollableText extends ScrollableList<ListProviderText> {
	
	// Fields :
	
	protected int color;
	
	// Constructors :
	
	protected ScrollableText(ComponentContainer parent, int xPos, int yPos, int width, int height, Minecraft minecraft, ListProviderText listProvider, int color) {
		super(parent, xPos, yPos, width, height, minecraft, listProvider);
		this.color = GRAY;
	}
	
	// Methods :
	
	@Override
	protected int getSlotHeight() {
		return FIELD_HEIGHT + MARGIN;
	}
	
	@Override
	protected void drawSlots(int mouseX, int mouseY) {
		int listLeft = 0;
		int shiftedTop = 2 - (int) this.scrollDistance;
		
		for(int index = 0; index < this.getSize(); ++index) {
			int top = shiftedTop + index * this.slotHeight - 2;
			
			if(top < this.height && top + this.slotHeight > 0) {
				this.drawSlotContent(index, listLeft, top);
			}
		}
		
	}
	
	protected void drawSlotContent(int index, int left, int top) {
		String element = this.listProvider.getList().get(index);
		FontRenderer fontRenderer = this.listProvider.getFontRenderer();
		
		int yPos = top + (MARGIN / 2) + FIELD_HEIGHT - fontRenderer.FONT_HEIGHT;
		
		if(yPos > 0 && yPos + FIELD_HEIGHT - 1 < this.height) {
			this.drawText(fontRenderer, fontRenderer.trimStringToWidth(element, this.width - (2 * MARGIN)), MARGIN, yPos, this.color);
		}
	}
	
}
