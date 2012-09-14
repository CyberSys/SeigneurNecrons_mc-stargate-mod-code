package mods.necron.stargate;

import java.util.LinkedList;

import net.minecraft.src.EntityPlayer;

public class TileEntityCoordTeleporter extends TileEntityCoord {
	
	/**
	 * Verifie que les coordonnées enregistrées sont valides.
	 */
	public boolean destinationValide() {
		// On verifie que la destination n'est pas trop éloignée du point de départ
		if(Math.abs(this.xDest - this.xCoord) > 256 || Math.abs(this.yDest - this.yCoord) > 256 || Math.abs(this.zDest - this.zCoord) > 256) {
			return false;
		}
		// On verifie qu'il y a assez d'espace au point d'arrivée
		if(this.worldObj.getBlockId(xDest, yDest, zDest) != 0 || this.worldObj.getBlockId(xDest, yDest + 1, zDest) != 0) {
			return false;
		}
		// On verifie qu'il y a un téléporteur à côté du point d'arrivée
		return (this.teleporteurValide(this.xDest + 1, this.yDest, this.zDest) || this.teleporteurValide(this.xDest - 1, this.yDest, this.zDest) || this.teleporteurValide(this.xDest, this.yDest, this.zDest + 1) || this.teleporteurValide(this.xDest, this.yDest, this.zDest - 1));
	}
	
	/**
	 * Verifie qu'il y a un teleporter à la position spécifiée.
	 */
	private boolean teleporteurValide(int x, int y, int z) {
		return (this.worldObj.getBlockId(x, y, z) == StargateMod.teleporterCoord.blockID && this.worldObj.getBlockId(x, y + 1, z) == StargateMod.teleporter.blockID);
	}
	
	/**
	 * Teleporte le joueur aux coordonnées enregistrées, si elles sont valides.
	 */
	public void teleportPlayer(EntityPlayer player) {
		if(this.destinationValide()) {
			player.setPositionAndUpdate((double) ((float) this.xDest + 0.5F), (double) this.yDest, (double) ((float) this.zDest + 0.5F));
		}
	}
	
	/**
	 * Charge les données de la tileEntity depuis une LinkedList de Byte.
	 * @param list - la LinkedList de Byte contenant les données à charger.
	 * @return true si le chargement est un succes, false sinon.
	 */
	@Override
	protected boolean loadEntityData(LinkedList<Byte> list) {
		if(super.loadEntityData(list)) {
			this.updateBlockTexture();
			return true;
		}
		return false;
	}
	
	/**
	 * Signal au renderer que le block situé au dessus du block lié à cette tile entity a besoin d'être mit à jour.
	 */
	protected void updateBlockTexture() {
		if(this.worldObj.isRemote) {
			this.worldObj.markBlockNeedsUpdate(this.xCoord, this.yCoord + 1, this.zCoord);
		}
	}
	
	/**
	 * Verifie que l'id fournie est une id correcte pour un packet destiné à cette tile entity.
	 * @param id - l'id à tester.
	 * @return true si l'id est correcte, false sinon.
	 */
	@Override
	protected boolean isCorrectId(int id) {
		return (super.isCorrectId(id) || id == packetId_CloseGuiTeleporter);
	}
	
	/**
	 * Retourne une représentation textuelle de cette tile entity.
	 */
	@Override
	public String toString() {
		return ("[] TileEntityTeleporterCoord[xDest: " + this.xDest + ",yDest " + this.yDest + ",zDest: " + this.zDest + "]");
	}
	
}
