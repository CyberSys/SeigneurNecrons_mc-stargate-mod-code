package seigneurnecron.minecraftmods.stargate.block.material;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialPortal;

/**
 * @author Seigneur Necron
 */
public class VortexMaterial extends MaterialPortal {
	
	// Constructors :
	
	public VortexMaterial() {
		super(MapColor.airColor);
		this.setNoPushMobility();
		this.setReplaceable();
	}
	
}
