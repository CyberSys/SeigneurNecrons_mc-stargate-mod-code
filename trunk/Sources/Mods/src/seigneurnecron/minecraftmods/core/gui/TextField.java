package seigneurnecron.minecraftmods.core.gui;

import static seigneurnecron.minecraftmods.core.gui.GuiConstants.FIELD_HEIGHT;

import java.util.logging.Level;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ChatAllowedCharacters;
import seigneurnecron.minecraftmods.core.SeigneurNecronMod;
import seigneurnecron.minecraftmods.core.reflection.Reflection;
import seigneurnecron.minecraftmods.core.reflection.ReflectionException;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class TextField extends GuiTextField implements Component {
	
	// Field :
	
	protected final int width;
	protected final int height;
	
	// Constructors :
	
	public TextField(ComponentContainer container, FontRenderer par1FontRenderer, int xPos, int yPos, int width, int height, String text) {
		super(par1FontRenderer, container.getXPosInScreen(xPos), container.getYPosInScreen(yPos), width, height);
		this.setMaxStringLength(25);
		this.setText(text);
		this.width = width;
		this.height = height;
	}
	
	public TextField(ComponentContainer container, FontRenderer par1FontRenderer, int xPos, int yPos, int width, int height) {
		this(container, par1FontRenderer, xPos, yPos, width, height, "");
	}
	
	public TextField(ComponentContainer container, FontRenderer par1FontRenderer, int xPos, int yPos, int width, String text) {
		this(container, par1FontRenderer, xPos, yPos, width, FIELD_HEIGHT, text);
	}
	
	public TextField(ComponentContainer container, FontRenderer par1FontRenderer, int xPos, int yPos, int width) {
		this(container, par1FontRenderer, xPos, yPos, width, FIELD_HEIGHT, "");
	}
	
	// Getters - use reflexion to get super class private fields :
	
	protected boolean getIsEnabled() throws ReflectionException {
		return Reflection.getBoolean(GuiTextField.class, this, SeigneurNecronMod.instance.getConfig().guiTextField_isEnabled);
	}
	
	// Methods :
	
	@Override
	public int getComponentWidth() {
		return this.width;
	}
	
	@Override
	public int getComponentHeight() {
		return this.height;
	}
	
	@Override
	public boolean textboxKeyTyped(char character, int key) {
		try {
			if(this.getIsEnabled() && this.isFocused()) {
				switch(character) {
					case 1:
						this.setCursorPositionEnd();
						this.setSelectionPos(0);
						return true;
					case 3:
						GuiScreen.setClipboardString(this.getSelectedtext());
						return true;
					case 22:
						this.writeText(GuiScreen.getClipboardString());
						return true;
					case 24:
						GuiScreen.setClipboardString(this.getSelectedtext());
						this.writeText("");
						return true;
					default:
						switch(key) {
							case 14:
								if(GuiScreen.isCtrlKeyDown()) {
									this.deleteWords(-1);
								}
								else {
									this.deleteFromCursor(-1);
								}
								
								return true;
							case 199:
								if(GuiScreen.isShiftKeyDown()) {
									this.setSelectionPos(0);
								}
								else {
									this.setCursorPositionZero();
								}
								
								return true;
							case 203:
								if(GuiScreen.isShiftKeyDown()) {
									if(GuiScreen.isCtrlKeyDown()) {
										this.setSelectionPos(this.getNthWordFromPos(-1, this.getSelectionEnd()));
									}
									else {
										this.setSelectionPos(this.getSelectionEnd() - 1);
									}
								}
								else if(GuiScreen.isCtrlKeyDown()) {
									this.setCursorPosition(this.getNthWordFromCursor(-1));
								}
								else {
									this.moveCursorBy(-1);
								}
								
								return true;
							case 205:
								if(GuiScreen.isShiftKeyDown()) {
									if(GuiScreen.isCtrlKeyDown()) {
										this.setSelectionPos(this.getNthWordFromPos(1, this.getSelectionEnd()));
									}
									else {
										this.setSelectionPos(this.getSelectionEnd() + 1);
									}
								}
								else if(GuiScreen.isCtrlKeyDown()) {
									this.setCursorPosition(this.getNthWordFromCursor(1));
								}
								else {
									this.moveCursorBy(1);
								}
								
								return true;
							case 207:
								if(GuiScreen.isShiftKeyDown()) {
									this.setSelectionPos(this.getText().length());
								}
								else {
									this.setCursorPositionEnd();
								}
								
								return true;
							case 211:
								if(GuiScreen.isCtrlKeyDown()) {
									this.deleteWords(1);
								}
								else {
									this.deleteFromCursor(1);
								}
								
								return true;
							default:
								return this.textboxNormalKeyTyped(character, key);
						}
				}
			}
			else {
				return false;
			}
		}
		catch(ReflectionException argh) {
			SeigneurNecronMod.instance.log(argh.getMessage(), Level.SEVERE);
		}
		
		return false;
	}
	
	protected boolean textboxNormalKeyTyped(char character, int key) {
		if(this.isAllowedCharacter(character)) {
			this.writeText(Character.toString(character));
			return true;
		}
		else {
			return false;
		}
	}
	
	protected boolean isAllowedCharacter(char character) {
		return ChatAllowedCharacters.isAllowedCharacter(character);
	}
	
}
