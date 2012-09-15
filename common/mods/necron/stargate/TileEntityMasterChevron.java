package mods.necron.stargate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;

import net.minecraft.src.Block;
import net.minecraft.src.BlockFluid;
import net.minecraft.src.ChunkPosition;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;

public class TileEntityMasterChevron extends TileEntityStargate {
	
	// Constantes :
	
	/**
	 * Le temps d'activation de la porte : 9sec (1sec = 22tick).
	 */
	private static final int ACTIVATION_TIME = 198;
	
	/**
	 * Le temps maximal d'ouverture de la porte : 3min (1sec = 22tick).
	 */
	private static final int MAX_ACTIVATED_TIME = 3960;
	
	/**
	 * La durree pendant laquelle une entite ne peux etre teleportee une deuxieme fois : 1sec (1sec = 22tick).
	 */
	private static final int TELEPORT_LOCK_TIME = 22;
	
	/**
	 * L'index où est stocke le nombre de cases vides dans chaque ligne du patern.
	 */
	private static final int INDEX_NB_CASES_VIDES = 0;
	
	/**
	 * Un raccourci vers l'id du block de naquada.
	 */
	private static final int N = StargateMod.naquadaAlliage.blockID;
	
	/**
	 * Un raccourci vers l'id du block chevron.
	 */
	private static final int C = StargateMod.chevron.blockID;
	
	/**
	 * Un raccourci vers l'id du block chevron maitre.
	 */
	private static final int M = StargateMod.masterChevron.blockID;
	
	/**
	 * Le patern servant a verifier si une porte des etoile est bien construite.
	 */
	private static final int[][] PATERN = { {0, M, N}, {0, N, N, N, N}, {2, N, N, C}, {4, N, N}, {5, N, N}, {5, N, N}, {6, N, C}, {6, N, N}, {6, N, N}, {5, N, N}, {5, N, C}, {4, N, N}, {2, N, N, N}, {0, N, N, N, C}, {0, N, N}};
	
	/**
	 * Un tableau de valeurs servant a calculer les coordonnees de chaque chevron.
	 */
	private static final int[][] CHEVRON = { {-2, 4}, {-6, 7}, {-10, 6}, {-10, -6}, {-6, -7}, {-2, -4}, {0, 0}, {-13, 3}, {-13, -3}};
	
	/**
	 * Liste des id des blocks pouvant etre remplaces par un vortex.
	 */
	private static final LinkedList<Integer> blocksRemplassables = new LinkedList<Integer>();
	
	static {
		blocksRemplassables.add(0);
		blocksRemplassables.add(Block.fire.blockID);
		for(Block block : Block.blocksList) {
			if(block instanceof BlockFluid) {
				blocksRemplassables.add(block.blockID);
			}
		}
	}
	
	// Champs :
	
	/**
	 * L'etat de la porte : Broken/Off/Activating/Output/Input.
	 */
	private GateState state = GateState.BROKEN;
	
	/**
	 * Le type d'activation de la porte : Failed/Output/Input.
	 */
	private GateActivationType activationType = GateActivationType.FAILED;
	
	/**
	 * L'etat d'activation de la porte : E0/E1/E2/E3/E4/E5/E6/E7.
	 */
	private GateActivationState activationState = GateActivationState.E0;
	
	/**
	 * Compteur servant a l'activation et la desactivation automatique de la porte.
	 */
	private int count = 0;
	
	/**
	 * Coordonnee en X de la destination.
	 */
	private int xDest = 0;
	
	/**
	 * Coordonnee en Y de la destination.
	 */
	private int yDest = 0;
	
	/**
	 * Coordonnee en Z de la destination.
	 */
	private int zDest = 0;
	
	/**
	 * Une map contenant la liste des entites recement transportees, ainsi qu'un compteur indiquand il y a combien de temps elle ont ete teleportees.
	 */
	private HashMap<Integer, Integer> teleportedEntities = new HashMap<Integer, Integer>();
	
	// Methodes :
	
	/**
	 * Retourne l'etat de la porte : Broken/Off/Activating/Output/Input.
	 * @return l'etat de la porte : Broken/Off/Activating/Output/Input.
	 */
	public GateState getState() {
		return this.state;
	}
	
	/**
	 * Retourne le type d'activation de la porte : Failed/Output/Input.
	 * @return le type d'activation de la porte : Failed/Output/Input.
	 */
	public GateActivationType getActivationType() {
		return this.activationType;
	}
	
	/**
	 * Retourne l'etat d'activation de la porte : E0/E1/E2/E3/E4/E5/E6/E7.
	 * @return l'etat d'activation de la porte : E0/E1/E2/E3/E4/E5/E6/E7.
	 */
	public GateActivationState getActivationState() {
		return this.activationState;
	}
	
	/**
	 * Retourne le compteur servant a l'activation et la desactivation automatique de la porte.
	 * @return le compteur servant a l'activation et la desactivation automatique de la porte.
	 */
	public int getCount() {
		return this.count;
	}
	
	/**
	 * Retourne la coordonnee en X de la destination.
	 * @return la coordonnee en X de la destination.
	 */
	public int getXDest() {
		return this.xDest;
	}
	
	/**
	 * Retourne la coordonnee en Y de la destination.
	 * @return la coordonnee en Y de la destination.
	 */
	public int getYDest() {
		return this.yDest;
	}
	
	/**
	 * Retourne la coordonnee en Z de la destination.
	 * @return la coordonnee en Z de la destination.
	 */
	public int getZDest() {
		return this.zDest;
	}
	
	/**
	 * Indique si le chevron 7 est active.
	 * @return true si le chevron 7 est active, false sinon.
	 */
	public boolean isChevronActivated() {
		return (this.state == GateState.INPUT || this.state == GateState.OUTPUT || (this.state == GateState.ACTIVATING && this.activationState == GateActivationState.E7));
	}
	
	/**
	 * Change l'etat de la porte et previent les client du changement.
	 * @param state - le nouvel etat de la porte.
	 */
	private void setState(GateState state) {
		this.state = state;
		this.onInventoryChanged();
		this.updateClients();
	}
	
	/**
	 * Change l'etat d'activation de la porte et previent les client du changement.
	 * @param activationState - le nouvel etat d'activation de la porte.
	 */
	private void setActivationState(GateActivationState activationState) {
		this.activationState = activationState;
		if(this.activationState != GateActivationState.E0) {
			this.onInventoryChanged();
			this.updateClients();
		}
	}
	
	/**
	 * Change le type d'activation de la porte.
	 * @param activationType - le nouvel etat d'activation de la porte.
	 */
	private void setActivationType(GateActivationType activationType) {
		this.activationType = activationType;
	}
	
	/**
	 * Enregistre les coordonnees de la destination.
	 * @param x - la coordonnee en X de la destination.
	 * @param y - la coordonnee en Y de la destination.
	 * @param z - la coordonnee en Z de la destination.
	 */
	private void setDestination(int x, int y, int z) {
		this.xDest = x;
		this.yDest = y;
		this.zDest = z;
	}
	
	/**
	 * Verifie que la porte peut etre activee.
	 * @return true si la porte peut etre activee, false sinon.
	 */
	public boolean isActivable() {
		// Le patern doit etre verifie, la porte ne doit pas etre deja activee et il doit y avoir au moins un block a remplacer par du vortex.
		return this.checkPatern() && this.state == GateState.OFF && this.getBlocksARemplacer().size() > 0;
	}
	
	/**
	 * Verifie que la une porte des etoiles est complete/intacte.<br />
	 * S'il y avait une porte avant, mais qu'elle a ete endomagee, elle est desactivee.
	 * @return true s'il y a bien une porte a cette position, false sinon.
	 */
	public boolean checkPatern() {
		// Intialisation.
		StargateMod.debug("Checking gate patern...", true);
		int x = this.xCoord;
		int y = this.yCoord;
		int z = this.zCoord;
		
		// Si la porte est situee trop bas sur l'axe Y.
		if(y < PATERN.length) {
			// Le patern ne peut pas etre respecte.
			StargateMod.debug("La porte est situee trop bas sur l'axe Y !\n", true);
			return false;
		}
		
		// On oriente la porte sur l'axe X ou Z.
		int metadata = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
		int axeX = (metadata == 2 || metadata == 3) ? 1 : 0;
		int axeZ = (metadata == 4 || metadata == 5) ? 1 : 0;
		
		// Parcours par lignes sur Y.
		for(int i = 0; i < PATERN.length; ++i) {
			int nbCasesAir = PATERN[i][INDEX_NB_CASES_VIDES];
			int epesseurBordure = PATERN[i].length - 1;
			int demiLargeur = nbCasesAir + epesseurBordure;
			StargateMod.debug("    ", false, 9 - demiLargeur);
			
			// Parcours des cases de la ligne (X ou Z).
			for(int j = -demiLargeur + 1; j < demiLargeur; ++j) {
				// Si on est sur le tour de la porte.
				if(Math.abs(j) >= nbCasesAir) {
					y = this.yCoord - i;
					x = this.xCoord + (axeX * j);
					z = this.zCoord + (axeZ * j);
					
					// Si la position n'est pas celle du chevron maitre.
					if(j != 0 || i != 0) {
						int blockId = this.worldObj.getBlockId(x, y, z);
						int indexBlock = Math.abs(j) - nbCasesAir + 1;
						StargateMod.debug(blockId + " ", false);
						
						// Si le block n'est pas conforme au patern.
						if(blockId != PATERN[i][indexBlock]) {
							// On casse la porte.
							StargateMod.debug("", true);
							StargateMod.debug("Error in gate patern !\n", true);
							this.setBroken();
							return false;
						}
						// Si la porte n'est pas encore cree.
						else if(this.state == GateState.BROKEN) {
							TileEntityStargatePart tileEntity = (TileEntityStargatePart) this.worldObj.getBlockTileEntity(x, y, z);
							// Si le block appartient deja a une autre porte.
							if(tileEntity != null && tileEntity.isPartOfGate()) {
								// Il ne peut pas appartenir a cette porte.
								StargateMod.debug("", true);
								StargateMod.debug("Ce block appartient deja a une autre porte !\n", true);
								return false;
							}
						}
					}
				}
				else {
					// Sinon, on zap jusqu'a l'autre cote de la porte.
					StargateMod.debug("    ", false, nbCasesAir * 2 - 1);
					j = nbCasesAir - 1;
				}
			}
			StargateMod.debug("", true);
		}
		
		// Si on est arrive jusqu'ici, c'est que le patern est verifie.
		StargateMod.debug("Gate patern OK !\n", true);
		return true;
	}
	
	/**
	 * Cree une porte des etoiles, si c'est possible.<br />
	 * Associe des TileEntity aux blocks de la porte ainsi que des numero aux chevrons.
	 * @return true si l'operation a reussi, false sinon.
	 */
	public boolean createGate() {
		// Si la porte n'est pas deja cree, et si le patern est respecte.
		if(this.state == GateState.BROKEN && this.checkPatern()) {
			// Intialisation.
			StargateMod.debug("Creation de la porte...", true);
			int x = this.xCoord;
			int y = this.yCoord;
			int z = this.zCoord;
			
			// On oriente la porte sur l'axe X ou Z.
			int metadata = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
			int axeX = (metadata == 2 || metadata == 3) ? 1 : 0;
			int axeZ = (metadata == 4 || metadata == 5) ? 1 : 0;
			
			// Parcours par lignes sur Y.
			for(int i = 0; i < PATERN.length; ++i) {
				int nbCasesAir = PATERN[i][INDEX_NB_CASES_VIDES];
				int epesseurBordure = PATERN[i].length - 1;
				int demiLargeur = nbCasesAir + epesseurBordure;
				StargateMod.debug("    ", false, 9 - demiLargeur);
				
				// Parcours des cases de la ligne (X ou Z).
				for(int j = -demiLargeur + 1; j < demiLargeur; ++j) {
					// Si on est sur le tour de la porte.
					if(Math.abs(j) >= nbCasesAir) {
						y = this.yCoord - i;
						x = this.xCoord + (axeX * j);
						z = this.zCoord + (axeZ * j);
						
						// Si la position n'est pas celle du chevron maitre.
						if(j != 0 || i != 0) {
							// On lie le block a la porte.
							StargateMod.debug("OOO ", false);
							((TileEntityStargatePart) this.worldObj.getBlockTileEntity(x, y, z)).setGate(this.xCoord, this.yCoord, this.zCoord);
						}
					}
					else {
						// Sinon, on zap jusqu'a l'autre cote de la porte.
						StargateMod.debug("    ", false, nbCasesAir * 2 - 1);
						j = nbCasesAir - 1;
					}
				}
				StargateMod.debug("", true);
			}
			
			// On associe un numero a chaque chevron.
			for(int i = 1; i <= 9; ++i) {
				TileEntityChevron chevron = this.getChevron(i);
				if(chevron != null) {
					chevron.setNo(i);
				}
			}
			
			// On met a jour l'etat de la porte.
			StargateMod.debug("Creation terminee.\n", true);
			this.setState(GateState.OFF);
			return true;
		}
		
		// On signal que la creation a echoue.
		return false;
	}
	
	/**
	 * Retourne la liste des blocks qui peuvent etre remplaces par le vortex.
	 * @return la liste des blocks qui peuvent etre remplaces par le vortex.
	 */
	public LinkedList<ChunkPosition> getBlocksARemplacer() {
		// Intialisation.
		StargateMod.debug("Recuperation des block a remplacer par du vortex...", true);
		LinkedList<ChunkPosition> blocksARemplacer = new LinkedList<ChunkPosition>();
		int x = this.xCoord;
		int y = this.yCoord;
		int z = this.zCoord;
		
		// On oriente la porte sur l'axe X ou Z.
		int metadata = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
		int axeX = (metadata == 2 || metadata == 3) ? 1 : 0;
		int axeZ = (metadata == 4 || metadata == 5) ? 1 : 0;
		
		// Parcours par lignes sur Y.
		for(int i = 0; i < PATERN.length; ++i) {
			int nbCasesAir = PATERN[i][INDEX_NB_CASES_VIDES];
			
			// Parcours des cases de la ligne (X ou Z).
			for(int j = -nbCasesAir + 1; j < nbCasesAir; ++j) {
				y = this.yCoord - i;
				x = this.xCoord + (axeX * j);
				z = this.zCoord + (axeZ * j);
				int blockId = this.worldObj.getBlockId(x, y, z);
				StargateMod.debug(blockId + " ", false);
				
				// Si le block est remplassable par le vortex.
				if(this.blocksRemplassables.contains(blockId)) {
					// On l'ajoute a la liste des blocks a remplacer.
					blocksARemplacer.add(new ChunkPosition(x, y, z));
				}
			}
			StargateMod.debug("", true);
		}
		
		StargateMod.debug("Recuperation terminee.\n", true);
		return blocksARemplacer;
	}
	
	/**
	 * Cree un passage entre cette porte est la porte situee aux coordonnees fournies, si c'est possible.
	 * @param x - la coordonnee en X de la porte de destination.
	 * @param y - la coordonnee en Y de la porte de destination.
	 * @param z - la coordonnee en Z de la porte de destination.
	 */
	public void activate(int x, int y, int z) {
		// Si cette porte n'est pas encore initialisee, on essaie de l'initialiser; Si ça marche pas, on quitte.
		if(this.state == GateState.BROKEN && !this.createGate()) {
			StargateMod.debug("La porte n'a pas pu etre initialisee !", true);
			return;
		}
		
		// Si les coordonnees fournies sont celles de la porte de depart, on quitte.
		if(x == this.xCoord && y == this.yCoord && z == this.zCoord) {
			StargateMod.debug("La porte de depart ne peut pas etre la porte d'arrivee ! >.<", true);
			return;
		}
		
		// Si la porte de depart ne peut pas etre activee, on quitte.
		if(!this.isActivable()) {
			StargateMod.debug("La porte de depart ne peut pas etre activee !", true);
			return;
		}
		
		// Si le block aux coordonees indiquees n'est pas un chevron maitre, on lance une fausse procedure d'activation.
		if(this.worldObj.getBlockId(x, y, z) != StargateMod.masterChevron.blockID) {
			StargateMod.debug("Faux numero ! try again ! XD", true);
			this.setActivating(GateActivationType.FAILED);
			return;
		}
		
		// On recupere la tile entity du chevron maitre de l'autre porte.
		TileEntityMasterChevron otherGate = (TileEntityMasterChevron) this.worldObj.getBlockTileEntity(x, y, z);
		
		// Si la porte d'arrivee ne peut pas etre activee, on lance une fausse procedure d'activation.
		if(!otherGate.isActivable()) {
			StargateMod.debug("La porte que vous essayez de joindre est actuellement occupee, veuillez reessayer ulterieurement... XD", true);
			this.setActivating(GateActivationType.FAILED);
			return;
		}
		
		// On enregistre la destination.
		this.setDestination(x, y, z);
		otherGate.setDestination(this.xCoord, this.yCoord, this.zCoord);
		
		// On active les portes.
		this.setActivating(GateActivationType.INPUT);
		otherGate.setActivating(GateActivationType.OUTPUT);
	}
	
	/**
	 * Lance la procedure d'activation de la porte.
	 * @param activationType - le type d'activation (Failed/Output/Input).
	 */
	private void setActivating(GateActivationType activationType) {
		// On lance le compteur d'activation de la porte.
		this.count = ACTIVATION_TIME;
		// On choisit le type d'activation.
		this.setActivationType(activationType);
		// On reinitialise l'etape d'activation.
		this.setActivationState(GateActivationState.E0);
		// On met a jour l'etat de la porte.
		this.setState(GateState.ACTIVATING);
	}
	
	/**
	 * Ferme la vortex.
	 */
	public void close() {
		// Si la porte etait bien active ou en train de s'activer.
		if(this.state == GateState.INPUT || this.state == GateState.OUTPUT || this.state == GateState.ACTIVATING) {
			TileEntityMasterChevron otherGate = (TileEntityMasterChevron) this.worldObj.getBlockTileEntity(this.xDest, this.yDest, this.zDest);
			// On desactive les deux portes.
			this.deletePortal();
			otherGate.deletePortal();
		}
	}
	
	/**
	 * Cree un portail a l'emplacement des blocks libres.
	 */
	private void createPortal() {
		// Si la procedure d'activation etait une fail.
		if(this.activationType == GateActivationType.FAILED) {
			// On joue le son correspondant a une activation fail.
			this.playSoundEffect("stargate.gateFail");
			// On met a jour l'etat de la porte et on quitte.
			this.setState(GateState.OFF);
			return;
		}
		
		this.worldObj.editingBlocks = true;
		
		// A chaque emplacement libre dans la porte.
		for(ChunkPosition position : this.getBlocksARemplacer()) {
			// On cree un block de vortex.
			this.worldObj.setBlockWithNotify(position.x, position.y, position.z, StargateMod.vortex.blockID);
			// Et on le lie a la porte.
			TileEntityVortex tileEntity = (TileEntityVortex) this.worldObj.getBlockTileEntity(position.x, position.y, position.z);
			tileEntity.setGate(this.xCoord, this.yCoord, this.zCoord);
		}
		
		this.worldObj.editingBlocks = false;
		
		// On lance le compteur de desactivation automatique de la porte.
		this.count = MAX_ACTIVATED_TIME;
		
		// On met a jour l'etat de la porte.
		switch(this.activationType) {
			case INPUT:
				this.setState(GateState.INPUT);
				break;
			case OUTPUT:
				this.setState(GateState.OUTPUT);
				break;
			default:
				break;
		}
		
		// On joue le son correspondant a une activation reussie.
		this.playSoundEffect("stargate.gateOpen");
	}
	
	/**
	 * Supprime les blocks de portail.
	 */
	private void deletePortal() {
		// Si la procedure d'activation n'etait pas terminee, il n'y a aucun block a supprimer.
		if(this.state != GateState.ACTIVATING) {
			// Intialisation.
			int x = this.xCoord;
			int y = this.yCoord;
			int z = this.zCoord;
			
			// On oriente la porte sur l'axe X ou Z.
			int metadata = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
			int axeX = (metadata == 2 || metadata == 3) ? 1 : 0;
			int axeZ = (metadata == 4 || metadata == 5) ? 1 : 0;
			
			this.worldObj.editingBlocks = true;
			
			// Parcours par lignes sur Y.
			for(int i = 0; i < PATERN.length; ++i) {
				int nbCasesAir = PATERN[i][INDEX_NB_CASES_VIDES];
				
				// Parcours des cases de la ligne (X ou Z).
				for(int j = -nbCasesAir + 1; j < nbCasesAir; ++j) {
					y = this.yCoord - i;
					x = this.xCoord + (axeX * j);
					z = this.zCoord + (axeZ * j);
					int blockId = this.worldObj.getBlockId(x, y, z);
					
					// Si le block est un block de vortex
					if(blockId == StargateMod.vortex.blockID) {
						// On casse le lien avec la porte.
						TileEntityVortex tileEntity = (TileEntityVortex) this.worldObj.getBlockTileEntity(x, y, z);
						tileEntity.breakGate();
						// Et on supprime le block.
						this.worldObj.setBlockWithNotify(x, y, z, 0);
					}
				}
			}
			
			this.worldObj.editingBlocks = false;
			
			// On vide la liste des entites recement teleportees.
			this.teleportedEntities.clear();
			
			// On joue le son correspondant a la fermeture de la porte.
			this.playSoundEffect("stargate.gateClose");
		}
		else {
			// On joue le son correspondant a une activation interompue.
			this.playSoundEffect("stargate.gateFail");
		}
		
		// On met a jour l'etat de la porte.
		this.setState(GateState.OFF);
	}
	
	/**
	 * Positionne le status de la porte a "Broken" et casse le lien avec les blocks de la porte.
	 */
	public void setBroken() {
		// On ferme d'abord le vortex.
		this.close();
		
		// Si la porte est deja cassee, il n'y a rien a faire.
		if(this.state != GateState.BROKEN) {
			// Intialisation.
			StargateMod.debug("Desactivation de la porte...", true);
			int x = this.xCoord;
			int y = this.yCoord;
			int z = this.zCoord;
			
			// On oriente la porte sur l'axe X ou Z.
			int metadata = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
			int axeX = (metadata == 2 || metadata == 3) ? 1 : 0;
			int axeZ = (metadata == 4 || metadata == 5) ? 1 : 0;
			
			// Parcours par lignes sur Y.
			for(int i = 0; i < PATERN.length; ++i) {
				int nbCasesAir = PATERN[i][INDEX_NB_CASES_VIDES];
				int epesseurBordure = PATERN[i].length - 1;
				int demiLargeur = nbCasesAir + epesseurBordure;
				StargateMod.debug("    ", false, 9 - demiLargeur);
				
				// Parcours des cases de la ligne (X ou Z).
				for(int j = -demiLargeur + 1; j < demiLargeur; ++j) {
					// Si on est sur le tour de la porte.
					if(Math.abs(j) >= nbCasesAir) {
						y = this.yCoord - i;
						x = this.xCoord + (axeX * j);
						z = this.zCoord + (axeZ * j);
						
						// Si la position n'est pas celle du chevron maitre.
						if(j != 0 || i != 0) {
							TileEntity tileEntity = this.worldObj.getBlockTileEntity(x, y, z);
							
							// Si le block est toujours la.
							if(tileEntity != null && tileEntity instanceof TileEntityStargatePart) {
								// On casse la lien avec la porte.
								StargateMod.debug("XXX ", false);
								((TileEntityStargatePart) tileEntity).breakGate();
							}
						}
					}
					else {
						// Sinon, on zap jusqu'a l'autre cote de la porte.
						StargateMod.debug("    ", false, nbCasesAir * 2 - 1);
						j = nbCasesAir - 1;
					}
				}
				StargateMod.debug("", true);
			}
			
			// On met a jour l'etat de la porte.
			StargateMod.debug("Desactivation terminee.\n", true);
			this.setState(GateState.BROKEN);
		}
	}
	
	/**
	 * Methode appelee quand le dhd est active. Ferme la porte si est ouverte, essaye de contacter la porte aux coordonnees indiquees sinon.
	 * @param x - la coordonnee en X de la porte a contacter.
	 * @param y - la coordonnee en Y de la porte a contacter.
	 * @param z - la coordonnee en Z de la porte a contacter.
	 */
	public void onDhdActivation(int x, int y, int z) {
		// Si la porte est activee.
		if(this.state == GateState.INPUT || this.state == GateState.OUTPUT) {
			// On ferme le vortex.
			this.close();
		}
		else if(this.state == GateState.OFF || this.state == GateState.BROKEN) {
			// Sinon, on active la porte.
			this.activate(x, y, z);
		}
	}
	
	/**
	 * Cette methode est appelee a chaque tick, elle sert a mettre a jour l'etat de
	 * la porte lors de l'activation ou de la desactivation automatique de la porte.
	 */
	@Override
	public void updateEntity() {
		// Si on est cote server.
		if(!this.worldObj.isRemote) {
			// On met a jour le compteur.
			if(this.count > 0) {
				--this.count;
			}
			
			// Selon l'etat de la porte...
			switch(this.state) {
				// Si la porte est ouverte en entree.
				case INPUT:
					// Si la duree d'activation maximale a ete atteinte, on ferme la porte.
					if(this.count <= 0) {
						this.close();
					}
					// Sinon, on met a jour la liste des entites recement teleportees.
					else {
						Iterator<Entry<Integer, Integer>> iterator = this.teleportedEntities.entrySet().iterator();
						Entry<Integer, Integer> entry;
						while(iterator.hasNext()) {
							entry = iterator.next();
							if(entry.getValue() <= 0) {
								iterator.remove();
							}
							else {
								entry.setValue(entry.getValue() - 1);
							}
						}
					}
					break;
				// Si la porte est en train de s'activer.
				case ACTIVATING:
					// Selon l'etape d'activation...
					switch(this.activationState) {
						// Si aucun chevron n'est active.
						case E0:
							// Si il s'est ecoule environ 1s.
							if(this.count <= 176) {
								// On active le chevron 1.
								this.setActivationState(GateActivationState.E1);
								this.playSoundEffect("stargate.chevron");
							}
							break;
						// Si seul le premier chevron est active.
						case E1:
							// Si il s'est ecoule environ 1s.
							if(this.count <= 154) {
								// On active le chevron 2.
								this.setActivationState(GateActivationState.E2);
								this.playSoundEffect("stargate.chevron");
							}
							break;
						// Si les 2 premiers chevrons sont actives.
						case E2:
							// Si il s'est ecoule environ 1s.
							if(this.count <= 132) {
								// On active le chevron 3.
								this.setActivationState(GateActivationState.E3);
								this.playSoundEffect("stargate.chevron");
							}
							break;
						// Si les 3 premiers chevrons sont actives.
						case E3:
							// Si il s'est ecoule environ 1s.
							if(this.count <= 110) {
								// On active le chevron 4.
								this.setActivationState(GateActivationState.E4);
								this.playSoundEffect("stargate.chevron");
							}
							break;
						// Si les 4 premiers chevrons sont actives.
						case E4:
							// Si il s'est ecoule environ 1s.
							if(this.count <= 88) {
								// On active le chevron 5.
								this.setActivationState(GateActivationState.E5);
								this.playSoundEffect("stargate.chevron");
							}
							break;
						// Si les 5 premiers chevrons sont actives.
						case E5:
							// Si il s'est ecoule environ 1s.
							if(this.count <= 66) {
								// On active le chevron 6.
								this.setActivationState(GateActivationState.E6);
								this.playSoundEffect("stargate.chevron");
							}
							break;
						// Si les 6 premiers chevrons sont actives.
						case E6:
							// Si il s'est ecoule environ 1s.
							if(this.count <= 44) {
								// On active le chevron 7.
								this.setActivationState(GateActivationState.E7);
								this.playSoundEffect("stargate.masterChevron");
							}
							break;
						// Si les 7 chevrons sont actives.
						case E7:
							// Si il s'est ecoule environ 2s.
							if(this.count <= 0) {
								// On reinitialise l'etape d'activation.
								this.setActivationState(GateActivationState.E0);
								// On active la porte.
								this.createPortal();
							}
							break;
					}
					break;
				default:
					break;
			}
		}
		super.updateEntity();
	}
	
	/**
	 * Retourne la tile entity du chevron correspondant au numero passe en parametre.
	 * @param no - le numero du chevron a recuperer.
	 * @return la tile entity du chevron correspondant au numero.
	 */
	private TileEntityChevron getChevron(int no) {
		if(no < 1 || no == 7 || no > 9) {
			return null;
		}
		int metadata = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
		int axeX = (metadata == 3) ? 1 : ((metadata == 2) ? -1 : 0);
		int axeZ = (metadata == 4) ? 1 : ((metadata == 5) ? -1 : 0);
		int y = this.yCoord + CHEVRON[no - 1][0];
		int x = this.xCoord + (axeX * CHEVRON[no - 1][1]);
		int z = this.zCoord + (axeZ * CHEVRON[no - 1][1]);
		return (TileEntityChevron) this.worldObj.getBlockTileEntity(x, y, z);
	}
	
	/**
	 * Retourne l'angle correspondant aux metadata d'une tile entity de chevron maitre.
	 * @param metadata - les metadata du chevron maitre.
	 * @return l'angle correspondant aux metadata.
	 */
	private int getAngle(int metadata) {
		switch(metadata) {
			case 5:
				return 1;
			case 3:
				return 2;
			case 4:
				return 3;
			default:
				return 0;
		}
	}
	
	/**
	 * Reroutre l'angle entre cette porte et la porte de destination.
	 * @return l'angle entre cette porte et la porte de destination.
	 */
	private int getRotation() {
		int thisAngle = this.getAngle(this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord));
		int otherAngle = this.getAngle(this.worldObj.getBlockMetadata(this.xDest, this.yDest, this.zDest));
		return (otherAngle - thisAngle + 4) % 4;
	}
	
	/**
	 * Teleporte l'entite situee aux coordonnees fournies aux coordonnees equivalentes de l'autre porte.<br />
	 * Oriente egalement la vue en fonction de l'angle entre les deux porte.
	 * @param entity - l'entite a teleporter.
	 * @param x - la coordonnee en X de l'entite.
	 * @param y - la coordonnee en Y de l'entite.
	 * @param z - la coordonnee en Z de l'entite.
	 */
	public void teleportEntity(Entity entity) {
		// On verifie que l'entite ne viens pas deja d'etre teleportee.
		if(!this.teleportedEntities.containsKey(entity.entityId)) {
			// On ajoute l'entite a la liste des entites recement teleportees.
			this.teleportedEntities.put(entity.entityId, TELEPORT_LOCK_TIME);
			
			// On recupere la position de l'entite par rapport aux chevron maitre de la porte de depart.
			double xDiff = entity.posX - this.xCoord;
			double yDiff = entity.posY - this.yCoord;
			double zDiff = entity.posZ - this.zCoord;
			
			// On recupere la vitesse de l'entite.
			double motionX = entity.motionX;
			double motionY = entity.motionY;
			double motionZ = entity.motionZ;
			
			// On recupere l'angle entre la porte de depart et la porte d'arrivee.
			int rotation = this.getRotation();
			float angleRotation = rotation * 90;
			
			// On calcule le nouvel angle de la camera en fonction de l'angle entre les porte.
			float rotationYaw = (entity.rotationYaw - angleRotation) % 360;
			float rotationPitch = entity.rotationPitch;
			
			// On fait une rotation sur les coordonnees relatives au chevron maitre et sur les donnees de vitesse, en fonction de l'angle entre les portes.
			double xTP = xDiff;
			double yTP = yDiff;
			double zTP = zDiff;
			double xMotion = motionX;
			double yMotion = motionY;
			double zMotion = motionZ;
			switch(rotation) {
				case 1:
					xTP = -zDiff;
					zTP = xDiff;
					xMotion = motionZ;
					zMotion = -motionX;
					break;
				case 2:
					xTP = -xDiff;
					zTP = -zDiff;
					xMotion = -motionX;
					zMotion = -motionZ;
					break;
				case 3:
					xTP = zDiff;
					zTP = -xDiff;
					xMotion = -motionZ;
					zMotion = motionX;
					break;
			}
			
			// On calcule les coordonnees finales de teleportation.
			xTP = this.xDest + xTP;
			yTP = this.yDest + yTP;
			zTP = this.zDest + zTP;
			
			// On produit le son de passage dans le vortex a la position de depart.
			this.playSoundEffect(entity, "stargate.enterVortex");
			
			// On teleporte l'entite.
			if(entity instanceof EntityPlayerMP) {
				// FIXME - l'angle de la camera des joueurs n'est pas positionne correctement... en mode survie !
				((EntityPlayerMP) entity).serverForThisPlayer.setPlayerLocation(xTP, yTP, zTP, rotationYaw, rotationPitch);
			}
			else {
				entity.setLocationAndAngles(xTP, yTP, zTP, rotationYaw, rotationPitch);
			}
			
			// On met a jour la vitesse de l'entite.
			entity.setVelocity(xMotion, yMotion, zMotion);
			
			// On produit le son de passage dans le vortex a la position d'arrivee.
			this.playSoundEffect(entity, "stargate.enterVortex");
		}
	}
	
	/**
	 * Produit un son aux coordonnees du centre de la porte.
	 * @param sound - le nom du son a produire.
	 */
	private void playSoundEffect(String sound) {
		this.worldObj.playSoundEffect(this.xCoord - 7, this.yCoord - 7, this.zCoord - 7, sound, 2, 1);
	}
	
	/**
	 * Produit un son aux coordonnees de l'entite indiquee.
	 * @param entity - l'entite sur laquelle produire le son.
	 * @param sound - le nom du son a produire.
	 */
	private void playSoundEffect(Entity entity, String sound) {
		this.worldObj.playSoundAtEntity(entity, sound, this.worldObj.rand.nextFloat() * 0.2F + 0.8F, this.worldObj.rand.nextFloat() * 0.2F + 0.8F);
	}
	
	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		this.state = GateState.valueOf(par1NBTTagCompound.getInteger("status"));
		this.activationType = GateActivationType.valueOf(par1NBTTagCompound.getInteger("activationType"));
		this.activationState = GateActivationState.valueOf(par1NBTTagCompound.getInteger("activationState"));
		this.count = par1NBTTagCompound.getInteger("count");
		this.xDest = par1NBTTagCompound.getInteger("xDest");
		this.yDest = par1NBTTagCompound.getInteger("yDest");
		this.zDest = par1NBTTagCompound.getInteger("zDest");
	}
	
	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("status", this.state.getValue());
		par1NBTTagCompound.setInteger("activationType", this.activationType.getValue());
		par1NBTTagCompound.setInteger("activationState", this.activationState.getValue());
		par1NBTTagCompound.setInteger("count", this.count);
		par1NBTTagCompound.setInteger("xDest", this.xDest);
		par1NBTTagCompound.setInteger("yDest", this.yDest);
		par1NBTTagCompound.setInteger("zDest", this.zDest);
	}
	
	/**
	 * Enregistre les donnees de la tileEntity dans une List de Byte, dans le but de creer un packet.
	 * @return les donnees de la tileEntity sous la forme d'une List de Byte.
	 */
	@Override
	protected LinkedList<Byte> getEntityData() {
		LinkedList<Byte> list = super.getEntityData();
		
		writeInt(list, this.state.getValue());
		writeInt(list, this.activationType.getValue());
		writeInt(list, this.activationState.getValue());
		writeInt(list, this.count);
		writeInt(list, this.xDest);
		writeInt(list, this.yDest);
		writeInt(list, this.zDest);
		
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
			GateActivationState tmp = this.activationState;
			this.state = GateState.valueOf(readInt(list));
			this.activationType = GateActivationType.valueOf(readInt(list));
			this.activationState = GateActivationState.valueOf(readInt(list));
			this.count = readInt(list);
			this.xDest = readInt(list);
			this.yDest = readInt(list);
			this.zDest = readInt(list);
			this.updateBlockTexture(tmp != this.activationState);
			return true;
		}
		return false;
	}
	
	/**
	 * Signal au renderer que le block lie a cette tile entity a besoin d'etre mit a jour.
	 */
	protected void updateBlockTexture(boolean updateChevrons) {
		this.updateBlockTexture();
		if(this.state != GateState.BROKEN && updateChevrons) {
			if(this.activationState == GateActivationState.E0) {
				for(int i = 1; i <= 9; ++i) {
					TileEntityChevron chevron = this.getChevron(i);
					if(chevron != null) {
						chevron.updateBlockTexture();
					}
				}
			}
			else if(this.activationState != GateActivationState.E7) {
				TileEntityChevron chevron = this.getChevron(this.activationState.getValue());
				if(chevron != null) {
					chevron.updateBlockTexture();
				}
			}
		}
	}
	
	/**
	 * Retourne une representation textuelle de cette tile entity.
	 */
	@Override
	public String toString() {
		return ("[] TileEntityMasterChevron[state " + this.state.toString() + ",activationType " + this.activationType.toString() + ",activationState " + this.activationState.toString() + ",count " + this.count + ",xDest: " + this.xDest + ",yDest " + this.yDest + ",zDest: " + this.zDest + "]");
	}
	
}
