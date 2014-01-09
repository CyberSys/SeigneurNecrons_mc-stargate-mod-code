package seigneurnecron.minecraftmods.stargate.gui.components;

import static seigneurnecron.minecraftmods.core.gui.GuiConstants.GREEN;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.RED;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import seigneurnecron.minecraftmods.core.gui.ListProviderGui;
import seigneurnecron.minecraftmods.core.gui.SelectionListInventory;
import seigneurnecron.minecraftmods.stargate.gui.GuiStuffLevelUpTable;
import seigneurnecron.minecraftmods.stargate.tools.enchant.EnchantmentTools;
import seigneurnecron.minecraftmods.stargate.tools.loadable.PowerUp;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class SelectionListPowerUp extends SelectionListInventory<PowerUp> {
	
	// Fields :
	
	private final String levels;
	private final String cost;
	
	// Constructors :
	
	public SelectionListPowerUp(ListProviderGui<PowerUp> gui, int xPos, int yPos, int width, int height, EntityPlayer player) {
		super(gui, xPos, yPos, width, height, player);
		this.cost = I18n.func_135053_a(GuiStuffLevelUpTable.COST);
		this.levels = I18n.func_135053_a(GuiStuffLevelUpTable.LEVELS);
	}
	
	// Methods :
	
	@Override
	protected String getName(PowerUp element) {
		return I18n.func_135053_a(element.enchant.getName()) + " " + element.level;
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
