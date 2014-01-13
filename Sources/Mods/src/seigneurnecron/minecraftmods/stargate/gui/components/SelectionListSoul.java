package seigneurnecron.minecraftmods.stargate.gui.components;

import static seigneurnecron.minecraftmods.core.gui.GuiConstants.GREEN;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.RED;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import seigneurnecron.minecraftmods.core.gui.ComponentContainer;
import seigneurnecron.minecraftmods.core.gui.ListProviderSelectTwoLines;
import seigneurnecron.minecraftmods.core.gui.SelectionListInventory;
import seigneurnecron.minecraftmods.stargate.gui.GuiSoulCrystalFactory;
import seigneurnecron.minecraftmods.stargate.item.ItemSoulCrystalFull;
import seigneurnecron.minecraftmods.stargate.tools.enchant.EnchantmentTools;
import seigneurnecron.minecraftmods.stargate.tools.loadable.SoulCount;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class SelectionListSoul extends SelectionListInventory<SoulCount> {
	
	// Fields :
	
	private final String souls;
	
	// Constructors :
	
	public SelectionListSoul(ComponentContainer parent, int xPos, int yPos, int width, int height, Minecraft minecraft, ListProviderSelectTwoLines<SoulCount> listProvider, EntityPlayer player) {
		super(parent, xPos, yPos, width, height, minecraft, listProvider, player);
		this.souls = I18n.getString(GuiSoulCrystalFactory.SOULS);
	}
	
	// Methods :
	
	@Override
	protected String getName(SoulCount soulCount) {
		return StatCollector.translateToLocal("entity." + EntityList.getStringFromID(soulCount.id) + ".name");
	}
	
	@Override
	protected String getInfo(SoulCount soulCount) {
		ItemSoulCrystalFull crystal = ItemSoulCrystalFull.getCrystalFromMonsterId(soulCount.id);
		
		if(crystal == null) {
			return String.valueOf(soulCount.count);
		}
		
		return this.souls + " : " + soulCount.count + " / " + crystal.neededSouls;
	}
	
	@Override
	protected int getInfoColor(SoulCount element) {
		return EnchantmentTools.hasEnoughtSoul(this.player, element) ? GREEN : RED;
	}
	
}
