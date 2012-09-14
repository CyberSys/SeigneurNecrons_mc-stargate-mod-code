package mods.necron.stargate;

import java.util.LinkedList;

import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;

public class TileEntityCoordDhd extends TileEntityCoord {
	
	/**
	 * Portée maximale de connection avec une porte.
	 */
	private static final int maxRange = 20;
	
	/**
	 * Indique si ce DHD est connecté à une porte.
	 */
	private boolean linkedToGate = false;
	
	/**
	 * La coordonnée en X de la porte à laquelle est lié ce DHD.
	 */
	private int xGate = 0;
	
	/**
	 * La coordonnée en Y de la porte à laquelle est lié ce DHD.
	 */
	private int yGate = 0;
	
	/**
	 * La coordonnée en Z de la porte à laquelle est lié ce DHD.
	 */
	private int zGate = 0;
	
	/**
	 * Indique si ce DHD est connecté à une porte.
	 * @return true si ce DHD est connecté à une porte, false sinon.
	 */
	public boolean isLinkedToGate() {
		return linkedToGate;
	}
	
	/**
	 * Retourne la coordonnée en X de la porte à laquelle est lié ce DHD.
	 * @return la coordonnée en X de la porte à laquelle est lié ce DHD.
	 */
	public int getXGate() {
		return xGate;
	}
	
	/**
	 * Retourne la coordonnée en Y de la porte à laquelle est lié ce DHD.
	 * @return la coordonnée en Y de la porte à laquelle est lié ce DHD.
	 */
	public int getYGate() {
		return yGate;
	}
	
	/**
	 * Retourne la coordonnée en Z de la porte à laquelle est lié ce DHD.
	 * @return la coordonnée en Z de la porte à laquelle est lié ce DHD.
	 */
	public int getZGate() {
		return zGate;
	}
	
	/**
	 * Signal à ce DHD s'il est connecté à une porte et prévient les clients du changement.
	 * @param linkedToGate true si ce DHD est connecté à une porte, false sinon.
	 */
	private void setLinkedToGate(boolean linkedToGate) {
		this.linkedToGate = linkedToGate;
		this.updateClients();
	}
	
	/**
	 * Cherche une porte des étoiles dans la zone, et enregistre ses coordonnées.
	 */
	private void searchGate() {
		// Pas la peine de chercher une porte si on en a déjà trouvé une.
		if(!this.linkedToGate) {
			// On cherche d'abord dans un rayon de 1 autour du block, puis on augmente le rayon jusqu'à ce qu'on trouve une porte ou qu'on atteigne la portée max.
			for(int rayon = 1; rayon <= maxRange; ++rayon) {
				for(int i = -rayon; i <= rayon; ++i) {
					for(int j = -rayon; j <= rayon; ++j) {
						for(int k = - rayon; k <= rayon; ++k) {
							int x = this.xCoord + k;
							int y = this.yCoord + i;
							int z = this.zCoord + j;
							
							if(this.worldObj.getBlockId(x, y, z) == StargateMod.masterChevron.blockID) {
								this.xGate = x;
								this.yGate = y;
								this.zGate = z;
								this.setLinkedToGate(true);
								return;
							}
							
							// On ne teste pas l'interieur du cube, puisqu'il a déjà été testé dans la boucle précédante (parcours sur rayon).
							if(Math.abs(i) != rayon && Math.abs(j) != rayon && k == -rayon) {
								k = rayon - 1;
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * Active la porte des étoile liée à ce dhd, s'il y en a une.
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
	 * Enregistre les données de la tileEntity dans une List de Byte, dans le but de créer un packet.
	 * @return les données de la tileEntity sous la forme d'une List de Byte.
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
	 * Charge les données de la tileEntity depuis une LinkedList de Byte.
	 * @param list - la LinkedList de Byte contenant les données à charger.
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
	 * Verifie que l'id fournie est une id correcte pour un packet destiné à cette tile entity.
	 * @param id - l'id à tester.
	 * @return true si l'id est correcte, false sinon.
	 */
	@Override
	protected boolean isCorrectId(int id) {
		return (super.isCorrectId(id) || id == packetId_CloseGuiDhd);
	}
	
	/**
	 * Retourne une représentation textuelle de cette tile entity.
	 */
	@Override
	public String toString() {
		return ("[] TileEntityDhdCoord[xGate: " + this.xGate + ",yGate " + this.yGate + ",zGate: " + this.zGate + ",xDest: " + this.xDest + ",yDest " + this.yDest + ",zDest: " + this.zDest + "]");
	}
	
}
