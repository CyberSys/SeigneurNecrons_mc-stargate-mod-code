package seigneurnecron.minecraftmods.stargate.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelSign;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import seigneurnecron.minecraftmods.stargate.StargateMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * ########## Class copied from TileEntitySignRenderer and modified (1 added code part, 1 modified code part, 2 added lines) ##########<br />
 * Can display different textures depending on the text of the sign.
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class TileEntitySignCustomRenderer extends TileEntitySpecialRenderer {
	
	private static final ResourceLocation DEFAULT_TEXTURE = new ResourceLocation("textures/entity/sign.png");
	
	/* ########## Start of added code part ########## */
	
	public static final String SIGN_FOLDER = StargateMod.ASSETS_PREFIX + "textures/entity/sign/";
	public static final String EXTENSION = ".png";
	
	private static final String[] MESSAGES = {"CAUTION", "TROLL"};
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[MESSAGES.length];
	
	static {
		for(int i = 0; i < MESSAGES.length; i++) {
			TEXTURES[i] = new ResourceLocation(SIGN_FOLDER + MESSAGES[i] + EXTENSION);
		}
	}
	
	public static ResourceLocation getTextureFile(String[] signText) {
		if(signText.length == 4 && signText[1].isEmpty() && signText[2].isEmpty() && signText[3].isEmpty()) {
			for(int i = 0; i < MESSAGES.length; i++) {
				if(signText[0].equals(MESSAGES[i])) {
					return TEXTURES[i];
				}
			}
		}
		return null;
	}
	
	/* ########## End of added code part ########## */
	
	/** The ModelSign instance used by the TileEntitySignRenderer */
	private final ModelSign modelSign = new ModelSign();
	
	public void renderTileEntitySignAt(TileEntitySign tileEntitySign, double par2, double par4, double par6, float par8) {
		Block block = tileEntitySign.getBlockType();
		GL11.glPushMatrix();
		float f1 = 0.6666667F;
		float f2;
		
		if(block == Block.signPost) {
			GL11.glTranslatef((float) par2 + 0.5F, (float) par4 + 0.75F * f1, (float) par6 + 0.5F);
			float f3 = tileEntitySign.getBlockMetadata() * 360 / 16.0F;
			GL11.glRotatef(-f3, 0.0F, 1.0F, 0.0F);
			this.modelSign.signStick.showModel = true;
		}
		else {
			int i = tileEntitySign.getBlockMetadata();
			f2 = 0.0F;
			
			if(i == 2) {
				f2 = 180.0F;
			}
			
			if(i == 4) {
				f2 = 90.0F;
			}
			
			if(i == 5) {
				f2 = -90.0F;
			}
			
			GL11.glTranslatef((float) par2 + 0.5F, (float) par4 + 0.75F * f1, (float) par6 + 0.5F);
			GL11.glRotatef(-f2, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(0.0F, -0.3125F, -0.4375F);
			this.modelSign.signStick.showModel = false;
		}
		
		/* ########## Start of modified code part ########## */
		
		ResourceLocation texture = getTextureFile(tileEntitySign.signText);
		boolean shouldRenderText = texture == null;
		
		if(shouldRenderText) {
			texture = DEFAULT_TEXTURE;
		}
		
		this.func_110628_a(texture);
		
		/* ########## End of modified code part ########## */
		
		GL11.glPushMatrix();
		GL11.glScalef(f1, -f1, -f1);
		this.modelSign.renderSign();
		GL11.glPopMatrix();
		FontRenderer fontrenderer = this.getFontRenderer();
		f2 = 0.016666668F * f1;
		GL11.glTranslatef(0.0F, 0.5F * f1, 0.07F * f1);
		GL11.glScalef(f2, -f2, f2);
		GL11.glNormal3f(0.0F, 0.0F, -1.0F * f2);
		GL11.glDepthMask(false);
		byte b0 = 0;
		
		if(shouldRenderText) { /* ########## Added line ########## */
			for(int j = 0; j < tileEntitySign.signText.length; ++j) {
				String s = tileEntitySign.signText[j];
				
				if(j == tileEntitySign.lineBeingEdited) {
					s = "> " + s + " <";
					fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, j * 10 - tileEntitySign.signText.length * 5, b0);
				}
				else {
					fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, j * 10 - tileEntitySign.signText.length * 5, b0);
				}
			}
		} /* ########## Added line ########## */
		
		GL11.glDepthMask(true);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8) {
		this.renderTileEntitySignAt((TileEntitySign) par1TileEntity, par2, par4, par6, par8);
	}
	
}
