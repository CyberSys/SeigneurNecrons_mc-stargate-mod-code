package seigneurnecron.minecraftmods.stargate.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.inventory.ContainerMobGenerator;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityMobGenerator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class GuiMobGenerator extends GuiContainer {
	
	private static ResourceLocation texture;
	
	public GuiMobGenerator(InventoryPlayer inventoryPlayer, TileEntityMobGenerator tileEntity) {
		super(new ContainerMobGenerator(inventoryPlayer, tileEntity));
		
		if(texture == null) {
			texture = new ResourceLocation(StargateMod.instance.getAssetPrefix() + "textures/gui/container/mobGeneratorGui.png");
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRenderer.drawString(I18n.func_135053_a(TileEntityMobGenerator.INV_NAME), 8, 6, 0x404040);
		this.fontRenderer.drawString(I18n.func_135053_a("container.inventory"), 8, this.ySize - 96 + 2, 0x404040);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		this.mc.func_110434_K().func_110577_a(texture);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
	}
	
}
