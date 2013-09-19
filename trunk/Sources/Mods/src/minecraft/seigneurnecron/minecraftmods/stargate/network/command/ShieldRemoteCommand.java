package seigneurnecron.minecraftmods.stargate.network.command;

import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import seigneurnecron.minecraftmods.core.network.command.Command;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargateControl;
import seigneurnecron.minecraftmods.stargate.tools.loadable.Stargate;
import seigneurnecron.minecraftmods.stargate.tools.playerdata.PlayerStargateData;

/**
 * @author Seigneur Necron
 */
public class ShieldRemoteCommand implements Command {
	
	@Override
	public void run(TileEntity tileEntity, DataInputStream input, EntityPlayer player) throws IOException {
		if(tileEntity instanceof TileEntityStargateControl) {
			int code = input.readInt();
			TileEntityStargateControl stargate = (TileEntityStargateControl) tileEntity;
			boolean succes = stargate.onShieldRemoteUsed(code);
			
			if(succes) {
				PlayerStargateData playerData = PlayerStargateData.get(player);
				
				if(playerData != null) {
					Stargate newStargate = new Stargate(stargate.getDestination().address, "New Stargate", code);
					int index = playerData.getDataList().indexOf(newStargate);
					
					if(index >= 0) {
						Stargate oldStargate = playerData.getDataList().get(index);
						
						if(oldStargate.code != code) {
							oldStargate.code = code;
							playerData.syncProperties();
						}
					}
					else {
						playerData.addElementAndSync(newStargate);
					}
				}
			}
		}
	}
	
}
