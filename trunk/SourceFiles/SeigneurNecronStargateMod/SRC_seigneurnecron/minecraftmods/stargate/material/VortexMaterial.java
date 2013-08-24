package seigneurnecron.minecraftmods.stargate.material;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialPortal;

public class VortexMaterial extends MaterialPortal {
	
	public VortexMaterial() {
		super(MapColor.airColor);
		this.setNoPushMobility();
		this.setReplaceable();
	}
	
}
