package seigneurnecron.minecraftmods.stargate.tileentity;

import java.util.LinkedList;

import net.minecraft.entity.player.EntityPlayer;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler;

public class TileEntityCoordTeleporter extends TileEntityCoord {
	
	/**
	 * Verifie que les coordonnees enregistrees sont valides.
	 */
	public boolean destinationValide() {
		// On verifie que la destination n'est pas trop eloignee du point de depart
		if(Math.abs(this.xDest - this.xCoord) > 256 || Math.abs(this.yDest - this.yCoord) > 256 || Math.abs(this.zDest - this.zCoord) > 256) {
			return false;
		}
		// On verifie qu'il y a assez d'espace au point d'arrivee
		if(this.worldObj.getBlockId(this.xDest, this.yDest, this.zDest) != 0 || this.worldObj.getBlockId(this.xDest, this.yDest + 1, this.zDest) != 0) {
			return false;
		}
		// On verifie qu'il y a un teleporteur a cote du point d'arrivee
		return (this.teleporteurValide(this.xDest + 1, this.yDest, this.zDest) || this.teleporteurValide(this.xDest - 1, this.yDest, this.zDest) || this.teleporteurValide(this.xDest, this.yDest, this.zDest + 1) || this.teleporteurValide(this.xDest, this.yDest, this.zDest - 1));
	}
	
	/**
	 * Verifie qu'il y a un teleporter a la position specifiee.
	 */
	private boolean teleporteurValide(int x, int y, int z) {
		return (this.worldObj.getBlockId(x, y, z) == StargateMod.teleporterCoord.blockID && this.worldObj.getBlockId(x, y + 1, z) == StargateMod.teleporter.blockID);
	}
	
	/**
	 * Teleporte le joueur aux coordonnees enregistrees, si elles sont valides.
	 */
	public void teleportPlayer(EntityPlayer player) {
		if(this.destinationValide()) {
			player.setPositionAndUpdate(this.xDest + 0.5F, this.yDest, this.zDest + 0.5F);
		}
	}
	
	@Override
	protected boolean loadEntityData(LinkedList<Byte> list) {
		if(super.loadEntityData(list)) {
			this.updateBlockTexture();
			return true;
		}
		return false;
	}
	
	/**
	 * Signal au renderer que le block situe au dessus du block lie a cette tile entity a besoin d'etre mit a jour.
	 */
	@Override
	protected void updateBlockTexture() {
		if(this.worldObj.isRemote) {
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord + 1, this.zCoord);
		}
	}
	
	@Override
	protected boolean isCorrectId(int id) {
		return (super.isCorrectId(id) || id == StargatePacketHandler.packetId_CloseGuiTeleporter);
	}
	
	@Override
	public String toString() {
		return ("TileEntityTeleporterCoord[xDest: " + this.xDest + ",yDest " + this.yDest + ",zDest: " + this.zDest + "]");
	}
	
}
