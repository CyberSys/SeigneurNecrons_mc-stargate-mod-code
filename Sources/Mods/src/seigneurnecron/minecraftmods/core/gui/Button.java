package seigneurnecron.minecraftmods.core.gui;

import static seigneurnecron.minecraftmods.core.gui.GuiConstants.BUTTON_HEIGHT;
import net.minecraft.client.gui.GuiButton;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class Button extends GuiButton implements Component {
	
	// Constructors :
	
	/**
	 * Create a new Button. <br />
	 * Warning : the button must be added to a {@link ComponentContainer} using the addComponent(...) method, else the button will have the id 0.
	 * @param container - the container which contains the button.
	 * @param xPos - the X position of the button in the container.
	 * @param yPos - the Y position of the button in the container.
	 * @param width - the button with.
	 * @param height - the button height.
	 * @param text - the text displayed on the button.
	 */
	public Button(ComponentContainer container, int xPos, int yPos, int width, int height, String text) {
		super(0, container.getXPosInScreen(xPos), container.getYPosInScreen(yPos), width, height, text);
	}
	
	/**
	 * Create a new Button with the default height. <br />
	 * Warning : the button must be added to a {@link ComponentContainer} using the addComponent(...) method, else the button will have the id 0.
	 * @param container - the container which contains the button.
	 * @param xPos - the X position of the button in the container.
	 * @param yPos - the Y position of the button in the container.
	 * @param width - the button with.
	 * @param text - the text displayed on the button.
	 */
	public Button(ComponentContainer container, int xPos, int yPos, int width, String text) {
		this(container, xPos, yPos, width, BUTTON_HEIGHT, text);
	}
	
	// Component interface :
	
	@Override
	public int getComponentWidth() {
		return this.width;
	}
	
	@Override
	public int getComponentHeight() {
		return this.height;
	}
	
	// Methods :
	
	/**
	 * Returns the name of the sound to play when the button is clicked.
	 * @return the name of the sound to play when the button is clicked.
	 */
	public String getSoundName() {
		return "random.click";
	}
	
}
