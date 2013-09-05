package seigneurnecron.minecraftmods.stargate.client.gui.tools;

import net.minecraft.client.gui.FontRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class IntegerField extends TextField {
	
	public IntegerField(Container container, FontRenderer par1FontRenderer, int xPos, int yPos, int width, int height) {
		this(container, par1FontRenderer, xPos, yPos, width, height, 0);
	}
	
	public IntegerField(Container container, FontRenderer par1FontRenderer, int xPos, int yPos, int width, int height, int value) {
		super(container, par1FontRenderer, xPos, yPos, width, height, String.valueOf(value));
	}
	
	@Override
	public boolean textboxNormalKeyTyped(char character, int key) {
		if(character == '-') {
			if(this.getText().length() == 0) {
				this.writeText(Character.toString(character));
			}
			else {
				int cursorPos = this.getCursorPosition();
				boolean cursorChanged = false;
				
				if(this.getCursorPosition() != 0) {
					this.setCursorPositionZero();
					cursorChanged = true;
				}
				
				if(this.getText().charAt(0) == character) {
					this.deleteFromCursor(1);
					if(cursorChanged) {
						this.setCursorPosition(cursorPos - 1);
					}
				}
				else {
					this.writeText(Character.toString(character));
					if(cursorChanged) {
						this.setCursorPosition(cursorPos + 1);
					}
				}
			}
			
			return true;
		}
		else if(this.isAllowedCharacter(character) && (this.getCursorPosition() != 0 || this.getText().length() == 0 || this.getText().charAt(0) != '-')) {
			this.writeText(Character.toString(character));
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	protected boolean isAllowedCharacter(char character) {
		return character >= '0' && character <= '9';
	}
	
}
