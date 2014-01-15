package seigneurnecron.minecraftmods.core.gui;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class TextProvider implements ListProviderText {
	
	// Fields :
	
	private FontRenderer fontRenderer;
	private int width = 100;
	private String text = "";
	private List<String> lines = new LinkedList<String>();
	
	// Constructors :
	
	public TextProvider(FontRenderer fontRenderer) {
		this.fontRenderer = fontRenderer;
	}
	
	// Getters :
	
	public int getWidth() {
		return this.width;
	}
	
	public String getText() {
		return this.text;
	}
	
	// Setters :
	
	public void setWidth(int width) {
		this.width = width;
		this.update();
	}
	
	public void setText(String text) {
		this.text = text;
		this.update();
	}
	
	// Methods :
	
	public void update(String text, int width) {
		this.width = width;
		this.text = text;
		this.update();
	}
	
	@SuppressWarnings("unchecked")
	protected void update() {
		this.lines = this.fontRenderer.listFormattedStringToWidth(this.text, this.width);
	}
	
	@Override
	public List<String> getList() {
		return this.lines;
	}
	
	@Override
	public FontRenderer getFontRenderer() {
		return this.fontRenderer;
	}
	
}
