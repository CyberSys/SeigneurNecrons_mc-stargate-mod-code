package mods.necron.custom;

import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import cpw.mods.fml.common.network.Player;

@SideOnly(Side.CLIENT)
public class CustomClientPacketHandler extends CustomPacketHandler {
	
	@Override
	public void onPacketData(NetworkManager manager, Packet250CustomPayload packet, Player player) {
		// vide pour l'instant...
	}
	
}
