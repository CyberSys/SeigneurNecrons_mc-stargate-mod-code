package mods.necron.stargate;

import java.util.LinkedList;

import net.minecraft.src.ChunkPosition;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;

public class TileEntityCoordDhd extends TileEntityCoord {
	
	/**
	 * Portee maximale de connection avec une porte.
	 */
	private static final int maxRange = 20;
	
	/**
	 * Indique si ce DHD est connecte a une porte.
	 */
	private boolean linkedToGate = false;
	
	/**
	 * La coordonnee en X de la porte a laquelle est lie ce DHD.
	 */
	private int xGate = 0;
	
	/**
	 * La coordonnee en Y de la porte a laquelle est lie ce DHD.
	 */
	private int yGate = 0;
	
	/**
	 * La coordonnee en Z de la porte a laquelle est lie ce DHD.
	 */
	private int zGate = 0;
	
	/**
	 * Indique si ce DHD est connecte a une porte.
	 * @return true si ce DHD est connecte a une porte, false sinon.
	 */
	public boolean isLinkedToGate() {
		return linkedToGate;
	}
	
	/**
	 * Retourne la coordonnee en X de la porte a laquelle est lie ce DHD.
	 * @return la coordonnee en X de la porte a laquelle est lie ce DHD.
	 */
	public int getXGate() {
		return xGate;
	}
	
	/**
	 * Retourne la coordonnee en Y de la porte a laquelle est lie ce DHD.
	 * @return la coordonnee en Y de la porte a laquelle est lie ce DHD.
	 */
	public int getYGate() {
		return yGate;
	}
	
	/**
	 * Retourne la coordonnee en Z de la porte a laquelle est lie ce DHD.
	 * @return la coordonnee en Z de la porte a laquelle est lie ce DHD.
	 */
	public int getZGate() {
		return zGate;
	}
	
	/**
	 * Signal a ce DHD s'il est connecte a une porte et previent les clients du changement.
	 * @param linkedToGate true si ce DHD est connecte a une porte, false sinon.
	 */
	private void setLinkedToGate(boolean linkedToGate) {
		this.linkedToGate = linkedToGate;
		this.updateClients();
	}
	
	/**
	 * Cherche une porte des etoiles dans la zone, et enregistre ses coordonnees.
	 */
	private void searchGate() {
		// Pas la peine de chercher une porte si on en a deja trouve une.
		if(!this.linkedToGate) {
			// On va chercher la liste de tout les chevrons maitre a portee.
			LinkedList<ChunkPosition> listeChevronsMaitre = new LinkedList<ChunkPosition>();
			
			// On cherche dans un cube de cote : portee max.
			for(int i = -maxRange; i <= maxRange; ++i) {
				for(int j = -maxRange; j <= maxRange; ++j) {
					for(int k = -maxRange; k <= maxRange; ++k) {
						int x = this.xCoord + k;
						int y = this.yCoord + i;
						int z = this.zCoord + j;
						
						// Si le block est un chevron maitre, on l'ajoute a la liste des blocks pouvant etre lies au dhd.
						if(this.worldObj.getBlockId(x, y, z) == StargateMod.masterChevron.blockID) {
							listeChevronsMaitre.add(new ChunkPosition(x, y, z));
						}
					}
				}
			}
			
			// Si on a trouve au moins un chevron maitre.
			if(listeChevronsMaitre.size() > 0) {
				// On parcoure la liste a la recherche du chevron le plus proche.
				for(ChunkPosition pos : listeChevronsMaitre) {
					// Si le dhd n'etait pas encore lie ou si le nouveau chevron maitre est plus proche que l'ancien.
					if(!this.linkedToGate || this.distanceCarre(pos.x, pos.y, pos.z) < this.distanceCarre(this.xGate, this.yGate, this.zGate)) {
						// On lie le dhd au nouveau chevron maitre.
						this.xGate = pos.x;
						this.yGate = pos.y;
						this.zGate = pos.z;
						this.setLinkedToGate(true);
					}
				}
			}
		}
	}
	
	/**
	 * Retourne le carre de la distance entre ce dhd et le block aux cooradonnees indiquees.
	 * @param x - la coordonnee en X du block dont on veut connaitre la distance.
	 * @param y - la coordonnee en Y du block dont on veut connaitre la distance.
	 * @param z - la coordonnee en Z du block dont on veut connaitre la distance.
	 * @return le carre de la distance entre ce dhd et le block aux cooradonnees indiquees.
	 */
	private double distanceCarre(int x, int y, int z) {
		return Math.pow(this.xCoord - x, 2) + Math.pow(this.yCoord - y, 2) + Math.pow(this.zCoord - z, 2);
	}
	
	/**
	 * Active la porte des etoile liee a ce dhd, s'il y en a une.
	 */
	public void activateGate() {
		this.searchGate();
		
		if(this.linkedToGate) {
			TileEntity tileEntity = this.worldObj.getBlockTileEntity(this.xGate, this.yGate, this.zGate);
			if(tileEntity != null && tileEntity instanceof TileEntityMasterChevron) {
				((TileEntityMasterChevron) tileEntity).onDhdActivation(this.xDest, this.yDest, this.zDest);
			}
			else {
				this.setLinkedToGate(false);
				this.activateGate();
			}
		}
	}
	
	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		this.linkedToGate = par1NBTTagCompound.getBoolean("linkedToGate");
		this.xGate = par1NBTTagCompound.getInteger("xGate");
		this.yGate = par1NBTTagCompound.getInteger("yGate");
		this.zGate = par1NBTTagCompound.getInteger("zGate");
	}
	
	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setBoolean("linkedToGate", this.linkedToGate);
		par1NBTTagCompound.setInteger("xGate", this.xGate);
		par1NBTTagCompound.setInteger("yGate", this.yGate);
		par1NBTTagCompound.setInteger("zGate", this.zGate);
	}
	
	/**
	 * Enregistre les donnees de la tileEntity dans une List de Byte, dans le but de creer un packet.
	 * @return les donnees de la tileEntity sous la forme d'une List de Byte.
	 */
	@Override
	protected LinkedList<Byte> getEntityData() {
		LinkedList<Byte> list = super.getEntityData();
		
		writeBoolean(list, this.linkedToGate);
		writeInt(list, this.xGate);
		writeInt(list, this.yGate);
		writeInt(list, this.zGate);
		
		return list;
	}
	
	/**
	 * Charge les donnees de la tileEntity depuis une LinkedList de Byte.
	 * @param list - la LinkedList de Byte contenant les donnees a charger.
	 * @return true si le chargement est un succes, false sinon.
	 */
	@Override
	protected boolean loadEntityData(LinkedList<Byte> list) {
		if(super.loadEntityData(list)) {
			this.linkedToGate = readBoolean(list);
			this.xGate = readInt(list);
			this.yGate = readInt(list);
			this.zGate = readInt(list);
			this.updateBlockTexture();
			return true;
		}
		return false;
	}
	
	/**
	 * Verifie que l'id fournie est une id correcte pour un packet destine a cette tile entity.
	 * @param id - l'id a tester.
	 * @return true si l'id est correcte, false sinon.
	 */
	@Override
	protected boolean isCorrectId(int id) {
		return (super.isCorrectId(id) || id == packetId_CloseGuiDhd);
	}
	
	/**
	 * Retourne une representation textuelle de cette tile entity.
	 */
	@Override
	public String toString() {
		return ("[] TileEntityDhdCoord[xGate: " + this.xGate + ",yGate " + this.yGate + ",zGate: " + this.zGate + ",xDest: " + this.xDest + ",yDest " + this.yDest + ",zDest: " + this.zDest + "]");
	}
	
}
