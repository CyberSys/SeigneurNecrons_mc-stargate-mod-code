package seigneurnecron.minecraftmods.stargate.gui.components;

import static seigneurnecron.minecraftmods.core.gui.GuiConstants.GREEN;
import static seigneurnecron.minecraftmods.core.gui.GuiConstants.RED;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import seigneurnecron.minecraftmods.core.gui.ListProviderGui;
import seigneurnecron.minecraftmods.core.gui.SelectionListInventory;
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
	
	// Constructors :
	
	public SelectionListSoul(ListProviderGui<SoulCount> gui, int xPos, int yPos, int width, int height, EntityPlayer player) {
		super(gui, xPos, yPos, width, height, player);
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
		
		return soulCount.count + " / " + crystal.neededSouls;
	}
	
	@Override
	protected int getInfoColor(SoulCount element) {
		return EnchantmentTools.hasEnoughtSoul(this.player, element) ? GREEN : RED;
	}
	
}
