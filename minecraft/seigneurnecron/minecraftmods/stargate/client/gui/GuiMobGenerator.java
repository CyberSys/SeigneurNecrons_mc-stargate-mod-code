package seigneurnecron.minecraftmods.stargate.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import seigneurnecron.minecraftmods.stargate.inventory.ContainerMobGenerator;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityMobGenerator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMobGenerator extends GuiContainer {
	
	public GuiMobGenerator(InventoryPlayer inventoryPlayer, TileEntityMobGenerator tileEntity) {
		super(new ContainerMobGenerator(inventoryPlayer, tileEntity));
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRenderer.drawString("Mob Generator", 8, 6, 4210752);
		this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		int texture = this.mc.renderEngine.getTexture("/seigneurnecron/minecraftmods/stargate/image/mobGeneratorGui.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(texture);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
	}
	
}
