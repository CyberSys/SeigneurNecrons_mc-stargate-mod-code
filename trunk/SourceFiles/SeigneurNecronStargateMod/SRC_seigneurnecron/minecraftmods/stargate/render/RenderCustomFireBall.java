package seigneurnecron.minecraftmods.stargate.render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import seigneurnecron.minecraftmods.stargate.entity.EntityCustomFireBall;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * ########## Class copied from RenderFireball and modified (EntityFireBall => EntityCustomFireBall) ##########
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class RenderCustomFireBall extends Render {
	
	private float size;
	
	public RenderCustomFireBall(float size) {
		this.size = size;
	}
	
	public void doRenderFireball(EntityCustomFireBall entityCustomFireBall, double par2, double par4, double par6, float par8, float par9) {
		GL11.glPushMatrix();
		this.func_110777_b(entityCustomFireBall);
		GL11.glTranslatef((float) par2, (float) par4, (float) par6);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glScalef(this.size / 1.0F, this.size / 1.0F, this.size / 1.0F);
		Icon icon = Item.fireballCharge.getIconFromDamage(0);
		Tessellator tessellator = Tessellator.instance;
		float f3 = icon.getMinU();
		float f4 = icon.getMaxU();
		float f5 = icon.getMinV();
		float f6 = icon.getMaxV();
		float f7 = 1.0F;
		float f8 = 0.5F;
		float f9 = 0.25F;
		GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		tessellator.addVertexWithUV(0.0F - f8, 0.0F - f9, 0.0D, f3, f6);
		tessellator.addVertexWithUV(f7 - f8, 0.0F - f9, 0.0D, f4, f6);
		tessellator.addVertexWithUV(f7 - f8, 1.0F - f9, 0.0D, f4, f5);
		tessellator.addVertexWithUV(0.0F - f8, 1.0F - f9, 0.0D, f3, f5);
		tessellator.draw();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}
	
	protected ResourceLocation func_110790_a(EntityCustomFireBall entityCustomFireBall) {
		return TextureMap.field_110576_c;
	}
	
	@Override
	protected ResourceLocation func_110775_a(Entity entity) {
		return this.func_110790_a((EntityCustomFireBall) entity);
	}
	
	/**
	 * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	 * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
	 * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
	 * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	 */
	@Override
	public void doRender(Entity entity, double par2, double par4, double par6, float par8, float par9) {
		this.doRenderFireball((EntityCustomFireBall) entity, par2, par4, par6, par8, par9);
	}
	
}
