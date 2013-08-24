package seigneurnecron.minecraftmods.stargate.client.gui;

import static seigneurnecron.minecraftmods.stargate.inventory.ContainerStuffLevelUpTable.MIN_NB_BOOKS;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.inventory.ContainerStuffLevelUpTable;
import seigneurnecron.minecraftmods.stargate.inventory.PowerUp;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStuffLevelUpTable;

/**
 * @author Seigneur Necron
 */
public class GuiStuffLevelUpTable extends GuiContainer {
	
	public static final String POWER = TileEntityStuffLevelUpTable.INV_NAME + ".power";
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(StargateMod.ASSETS_PREFIX + "textures/gui/container/stuffLevelUpGui.png");
	
	private static final int SLOT_SIZE = 18;
	private static final int SLOT_MIN_X = 61;
	private static final int SLOT_MIN_Y = 14;
	private static final int ICON_MIN_X = 0;
	private static final int ICON_MIN_Y = 166;
	private static final int NB_SLOT_BY_LINE = 6;
	private static final int NB_ICON_BY_LINE = 14;
	private static final Map<Enchantment, Integer> icons = new HashMap<Enchantment, Integer>();
	
	static {
		icons.put(Enchantment.protection, 0);
		icons.put(Enchantment.fireProtection, 1);
		icons.put(Enchantment.blastProtection, 2);
		icons.put(Enchantment.projectileProtection, 3);
		icons.put(Enchantment.thorns, 4);
		icons.put(Enchantment.unbreaking, 5);
		icons.put(Enchantment.featherFalling, 6);
		icons.put(Enchantment.respiration, 7);
		icons.put(Enchantment.aquaAffinity, 8);
		icons.put(Enchantment.sharpness, 9);
		icons.put(Enchantment.smite, 10);
		icons.put(Enchantment.baneOfArthropods, 11);
		icons.put(Enchantment.knockback, 12);
		icons.put(Enchantment.fireAspect, 13);
		icons.put(Enchantment.looting, 14);
		icons.put(Enchantment.efficiency, 15);
		icons.put(Enchantment.silkTouch, 16);
		icons.put(Enchantment.fortune, 17);
		icons.put(Enchantment.power, 18);
		icons.put(Enchantment.punch, 19);
		icons.put(Enchantment.flame, 20);
		icons.put(Enchantment.infinity, 21);
	}
	
	private static int getInconIndex(Enchantment enchantment) {
		return icons.containsKey(enchantment) ? icons.get(enchantment) : icons.size();
	}
	
	public GuiStuffLevelUpTable(InventoryPlayer inventoryPlayer, TileEntityStuffLevelUpTable tileEntity) {
		super(new ContainerStuffLevelUpTable(inventoryPlayer, tileEntity));
	}
	
	private ContainerStuffLevelUpTable getContainer() {
		return (ContainerStuffLevelUpTable) this.inventorySlots;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRenderer.drawString(I18n.func_135053_a(TileEntityStuffLevelUpTable.INV_NAME), 12, 6, 4210752);
		this.fontRenderer.drawString(I18n.func_135053_a("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void mouseClicked(int xClick, int yClick, int click) {
		super.mouseClicked(xClick, yClick, click);
		int xPos = (this.width - this.xSize) / 2;
		int yPos = (this.height - this.ySize) / 2;
		
		for(int i = 0; i < this.getContainer().getEnchantments().size(); ++i) {
			int x = xClick - (xPos + SLOT_MIN_X + (SLOT_SIZE * (i % NB_SLOT_BY_LINE)));
			int y = yClick - (yPos + SLOT_MIN_Y + (SLOT_SIZE * (i / NB_SLOT_BY_LINE)));
			
			if(x >= 0 && y >= 0 && x < SLOT_SIZE && y < SLOT_SIZE && this.inventorySlots.enchantItem(this.mc.thePlayer, i)) {
				this.mc.playerController.sendEnchantPacket(this.inventorySlots.windowId, i);
			}
		}
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		this.mc.func_110434_K().func_110577_a(TEXTURE);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int xPos = (this.width - this.xSize) / 2;
		int yPos = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(xPos, yPos, 0, 0, this.xSize, this.ySize);
		
		int nbBooks = this.getContainer().getNbBooks();
		
		if(nbBooks >= MIN_NB_BOOKS) {
			this.fontRenderer.drawString(I18n.func_135053_a(POWER) + " : " + MIN_NB_BOOKS + " / " + MIN_NB_BOOKS, xPos + 70, yPos + 72, 8453920);
		}
		else {
			this.fontRenderer.drawString(I18n.func_135053_a(POWER) + " : " + nbBooks + " / " + MIN_NB_BOOKS, xPos + 72, yPos + 72, 16711680);
		}
		
		LinkedList<PowerUp> enchants = this.getContainer().getEnchantments();
		
		if(enchants.size() > 0) {
			int baseCost = this.getContainer().getCurrentItemEnchantLevelSum();
			
			for(int i = 0; i < enchants.size(); ++i) {
				PowerUp powerUp = enchants.get(i);
				
				int x = xPos + SLOT_MIN_X + (SLOT_SIZE * (i % NB_SLOT_BY_LINE));
				int y = yPos + SLOT_MIN_Y + (SLOT_SIZE * (i / NB_SLOT_BY_LINE));
				
				int inconIndex = getInconIndex(powerUp.enchant);
				int xIcon = ICON_MIN_X + (SLOT_SIZE * (inconIndex % NB_ICON_BY_LINE));
				int yIcon = ICON_MIN_Y + (SLOT_SIZE * (inconIndex / NB_ICON_BY_LINE));
				
				this.mc.func_110434_K().func_110577_a(TEXTURE);
				this.zLevel = 0.0F;
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				this.drawTexturedModalRect(x, y, xIcon, yIcon, SLOT_SIZE, SLOT_SIZE);
				
				int cost = (5 * powerUp.level) + baseCost;
				boolean ok = this.mc.thePlayer.capabilities.isCreativeMode || this.mc.thePlayer.experienceLevel >= cost;
				int color = ok ? 8453920 : 16711680;
				String str = String.valueOf(cost);
				
				this.fontRenderer.drawString(str, x + SLOT_SIZE - 2 - this.fontRenderer.getStringWidth(str), y + SLOT_SIZE - 8, color);
			}
		}
	}
	
}
