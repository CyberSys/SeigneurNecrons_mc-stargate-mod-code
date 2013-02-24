package net.minecraft.client.renderer.tileentity;

import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelSign;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * ########## Classe modifiee (1 ligne modifiee, 3 lignes ajoutees, 2 constantes ajoutees, 2 methodes ajoutees) ##########<br />
 * Ajoute des affichages speciaux sur les panneaux.<br />
 * Ensemble : TileEntitySpecialRenderer.
 */
@SideOnly(Side.CLIENT)
public class TileEntitySignRenderer extends TileEntitySpecialRenderer {
	
	/* ########## debut constantes ajoutee ########## */
	public static String defaultTextureFile = "/item/sign.png";
	public static String cautionThisIsSparta = "/item/caution_this_is_sparta.png";
	/* ########## fin constantes ajoutee ########## */
	
	/** The ModelSign instance used by the TileEntitySignRenderer */
	private ModelSign modelSign = new ModelSign();
	
	public void renderTileEntitySignAt(TileEntitySign tileEntitySign, double par2, double par4, double par6, float par8) {
		Block var9 = tileEntitySign.getBlockType();
		GL11.glPushMatrix();
		float var10 = 0.6666667F;
		float var12;
		
		if(var9 == Block.signPost) {
			GL11.glTranslatef((float) par2 + 0.5F, (float) par4 + 0.75F * var10, (float) par6 + 0.5F);
			float var11 = tileEntitySign.getBlockMetadata() * 360 / 16.0F;
			GL11.glRotatef(-var11, 0.0F, 1.0F, 0.0F);
			this.modelSign.signStick.showModel = true;
		}
		else {
			int var16 = tileEntitySign.getBlockMetadata();
			var12 = 0.0F;
			
			if(var16 == 2) {
				var12 = 180.0F;
			}
			
			if(var16 == 4) {
				var12 = 90.0F;
			}
			
			if(var16 == 5) {
				var12 = -90.0F;
			}
			
			GL11.glTranslatef((float) par2 + 0.5F, (float) par4 + 0.75F * var10, (float) par6 + 0.5F);
			GL11.glRotatef(-var12, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(0.0F, -0.3125F, -0.4375F);
			this.modelSign.signStick.showModel = false;
		}
		
		String textureFile = getTextureFile(tileEntitySign.signText); /* ########## ligne ajoutee ########## */
		this.bindTextureByName(textureFile); /* ########## ligne modifiee ########## */
		
		GL11.glPushMatrix();
		GL11.glScalef(var10, -var10, -var10);
		this.modelSign.renderSign();
		GL11.glPopMatrix();
		FontRenderer var17 = this.getFontRenderer();
		var12 = 0.016666668F * var10;
		GL11.glTranslatef(0.0F, 0.5F * var10, 0.07F * var10);
		GL11.glScalef(var12, -var12, var12);
		GL11.glNormal3f(0.0F, 0.0F, -1.0F * var12);
		GL11.glDepthMask(false);
		byte var13 = 0;
		
		if(shouldRenderText(textureFile)) { /* ########## ligne ajoutee ########## */
			for(int var14 = 0; var14 < tileEntitySign.signText.length; ++var14) {
				String var15 = tileEntitySign.signText[var14];
				
				if(var14 == tileEntitySign.lineBeingEdited) {
					var15 = "> " + var15 + " <";
					var17.drawString(var15, -var17.getStringWidth(var15) / 2, var14 * 10 - tileEntitySign.signText.length * 5, var13);
				}
				else {
					var17.drawString(var15, -var17.getStringWidth(var15) / 2, var14 * 10 - tileEntitySign.signText.length * 5, var13);
				}
			}
		} /* ########## ligne ajoutee ########## */
		
		GL11.glDepthMask(true);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8) {
		this.renderTileEntitySignAt((TileEntitySign) par1TileEntity, par2, par4, par6, par8);
	}
	
	/* ########## debut methode ajoutee ########## */
	public static String getTextureFile(String[] signText) {
		if(signText.length == 4) {
			if(signText[0].equals("CAUTION") && signText[1].equals("") && signText[2].equals("") && signText[3].equals("")) {
				return cautionThisIsSparta;
			}
		}
		return defaultTextureFile;
	}/* ########## fin methode ajoutee ########## */
	
	/* ########## debut methode ajoutee ########## */
	public static boolean shouldRenderText(String textureFile) {
		return textureFile.equals(defaultTextureFile);
	}/* ########## fin methode ajoutee ########## */
	
}
