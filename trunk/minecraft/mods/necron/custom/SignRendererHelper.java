package mods.necron.custom;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

@SideOnly(Side.CLIENT)
public class SignRendererHelper {
	
	public static String defaultTextureFile = "/item/sign.png";
	public static String cautionThisIsSparta = "/item/caution_this_is_sparta.png";
	
	public static String getTextureFile(String[] signText) {
		if(signText.length == 4) {
			if(signText[0].equals("CAUTION") && signText[1].equals("") && signText[2].equals("") && signText[3].equals("")) {
				return cautionThisIsSparta;
			}
		}
		return defaultTextureFile;
	}
	
	public static boolean shouldRenderText(String textureFile) {
		return textureFile.equals(defaultTextureFile);
	}
	
}
