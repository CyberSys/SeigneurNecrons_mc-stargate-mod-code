package seigneurnecron.minecraftmods.core.gui;

import static seigneurnecron.minecraftmods.core.gui.GuiConstants.FIELD_HEIGHT;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.GRAY;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.MARGIN;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.WHITE;
import net.minecraft.client.renderer.Tessellator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public abstract class SelectionList<T extends Object> extends CustomGuiScrollingList {
	
	protected final ListProviderGui<T> gui;
	
	public SelectionList(ListProviderGui<T> gui, int xPos, int yPos, int width, int height) {
		super(xPos, yPos, width, height, 2 * (FIELD_HEIGHT + MARGIN - 2));
		this.gui = gui;
	}
	
	@Override
	protected int getSize() {
		return this.gui.getList().size();
	}
	
	@Override
	protected void elementClicked(int index, boolean doubleClick) {
		this.gui.setSelectedIndex(index);
		
		if(doubleClick) {
			this.gui.onElementDoubleClicked();
		}
	}
	
	@Override
	protected boolean isSelected(int index) {
		return this.gui.getSelectedIndex() == index;
	}
	
	@Override
	protected void drawSlot(int index, int left, int top, int slotHeight, Tessellator tessellator) {
		T element = this.gui.getList().get(index);
		
		int xPos = this.left + MARGIN;
		int nameYPos = top + MARGIN + (FIELD_HEIGHT - this.gui.getFirstFontRenderer().FONT_HEIGHT);
		int infoYPos = top + MARGIN + FIELD_HEIGHT + (FIELD_HEIGHT - this.gui.getSecondFontRenderer().FONT_HEIGHT);
		
		if(nameYPos > this.top && nameYPos + FIELD_HEIGHT - 3 < this.bottom) {
			this.gui.getFirstFontRenderer().drawString(this.gui.getFirstFontRenderer().trimStringToWidth(this.getName(element), this.listWidth - (2 * MARGIN)), xPos, nameYPos, WHITE);
		}
		
		if(infoYPos > this.top && infoYPos + FIELD_HEIGHT - 3 < this.bottom) {
			this.gui.getSecondFontRenderer().drawString(this.gui.getSecondFontRenderer().trimStringToWidth(this.getInfo(element), this.listWidth - (2 * MARGIN)), xPos, infoYPos, GRAY);
		}
	}
	
	protected abstract String getName(T element);
	
	protected abstract String getInfo(T element);
	
}
