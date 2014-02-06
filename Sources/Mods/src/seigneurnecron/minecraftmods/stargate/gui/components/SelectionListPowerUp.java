package seigneurnecron.minecraftmods.stargate.gui.components;

import static seigneurnecron.minecraftmods.core.gui.GuiConstants.GREEN;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.RED;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import seigneurnecron.minecraftmods.core.gui.ComponentContainer;
import seigneurnecron.minecraftmods.core.gui.ListProviderSelectTwoLines;
import seigneurnecron.minecraftmods.core.gui.SelectionListInventory;
import seigneurnecron.minecraftmods.stargate.gui.GuiStuffLevelUpTable;
import seigneurnecron.minecraftmods.stargate.tools.enchant.EnchantmentTools;
import seigneurnecron.minecraftmods.stargate.tools.loadable.PowerUp;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class SelectionListPowerUp extends SelectionListInventory<PowerUp> {
	
	// Fields :
	
	private final String levels;
	private final String cost;
	
	// Constructors :
	
	public SelectionListPowerUp(ComponentContainer parent, int xPos, int yPos, int width, int height, Minecraft minecraft, ListProviderSelectTwoLines<PowerUp> listProvider, EntityPlayer player) {
		super(parent, xPos, yPos, width, height, minecraft, listProvider, player);
		this.cost = I18n.getString(GuiStuffLevelUpTable.COST);
		this.levels = I18n.getString(GuiStuffLevelUpTable.LEVELS);
	}
	
	// Methods :
	
	@Override
	protected String getName(PowerUp element) {
		return I18n.getString(element.enchant.getName()) + " " + element.level + "/" + element.enchant.getMaxLevel();
	}
	
	@Override
	protected String getInfo(PowerUp element) {
		return this.cost + " : " + element.cost + " " + this.levels;
	}
	
	@Override
	protected int getInfoColor(PowerUp element) {
		return EnchantmentTools.canPayEnchantCost(this.player, element) ? GREEN : RED;
	}
	
}
