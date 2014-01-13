package seigneurnecron.minecraftmods.core.gui;

import static seigneurnecron.minecraftmods.core.gui.GuiConstants.FIELD_HEIGHT;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.GRAY;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.MARGIN;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.WHITE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public abstract class SelectionListInventory<T extends Object> extends SelectionList<ListProviderSelectTwoLines<T>> {
	
	// Constants :
	
	public static final int LIST_SLOT_MARGIN = 2;
	
	// Fields :
	
	protected final EntityPlayer player;
	
	// Constructors :
	
	protected SelectionListInventory(ComponentContainer parent, int xPos, int yPos, int width, int height, Minecraft minecraft, ListProviderSelectTwoLines<T> listProvider, EntityPlayer player) {
		super(parent, xPos, yPos, width, height, minecraft, listProvider);
		this.player = player;
	}
	
	// Methods :
	
	@Override
	protected int getSlotHeight() {
		return 2 * (FIELD_HEIGHT + LIST_SLOT_MARGIN);
	}
	
	@Override
	protected void drawSlotContent(int index, int left, int top) {
		T element = this.listProvider.getList().get(index);
		
		FontRenderer fontRenderer1 = this.listProvider.getFirstFontRenderer();
		FontRenderer fontRenderer2 = this.listProvider.getFirstFontRenderer();
		
		int xPos = MARGIN;
		int width = this.width - (2 * MARGIN);
		int nameYPos = top + LIST_SLOT_MARGIN + FIELD_HEIGHT - fontRenderer1.FONT_HEIGHT;
		int infoYPos = top + LIST_SLOT_MARGIN + (2 * FIELD_HEIGHT) - fontRenderer2.FONT_HEIGHT;
		
		if(nameYPos > 0 && nameYPos + FIELD_HEIGHT - 1 < this.height) {
			this.drawText(fontRenderer1, fontRenderer1.trimStringToWidth(this.getName(element), width), xPos, nameYPos, this.getNameColor(element));
		}
		
		if(infoYPos > 0 && infoYPos + FIELD_HEIGHT - 1 < this.height) {
			this.drawText(fontRenderer2, fontRenderer2.trimStringToWidth(this.getInfo(element), width), xPos, infoYPos, this.getInfoColor(element));
		}
	}
	
	protected abstract String getName(T element);
	
	protected abstract String getInfo(T element);
	
	protected int getNameColor(T element) {
		return WHITE;
	}
	
	protected int getInfoColor(T element) {
		return GRAY;
	}
	
}
