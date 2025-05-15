/*
 * Tache.java                                      13/05/2025
 * Iut de Rodez info1, aucun copyright
 */


package planning;


/**
 * TODO
 * @author Liao Mattieu
 * @author Petit Valentin
 * @author Helg Florian
 */
public class Tache {
	

	private static Tache[] tachesStockees = new Tache[4];
	
	private String nom;
	
	private int duree;
	
	public final static int DUREE_MAX = 100;
	public final static int DUREE_MIN = 1;
	
	private int id;
	
	private int predecesseur;
	
	/**
	 * Création de tache en entrant le nom de la tache, la duree, l'identifient de la tache et son prédecesseur,
	 * @param nom
	 * @param duree
	 * @param id
	 * @param predecesseur
	 */
	public void creationTache(String nom, int duree, int id, int predecesseur) {
		this.nom= nom;
		this.duree=duree;
		this.id=id;
		this.predecesseur=predecesseur;
		
	}
	/**
	 * 
	 * @param nom
	 * @param duree
	 * @param id
	 * @param predecesseur
	 * @return
	 */
	public static boolean isValide(String nom, int duree, int id, int predecesseur) {
	    if (nom == null || nom.trim().isEmpty()) {
	        return false;
	    }
	    
	    if (duree < DUREE_MIN || duree > DUREE_MAX || id <= 0 || predecesseur < 0) {
	        return false;
	    }

	    // Vérifie que l'id est unique
	    for (Tache identifiant : tachesStockees) {
	        if (identifiant != null && identifiant.getId() == id) {
	            return false; 
	        }
	    }

	    return true;
	}

	
	/**
	 * @return nom
	 */
	public String getNom() {
		return this.nom;
	}
	
	/**
	 * @return duree
	 */
	public int getDuree() {
		return this.duree;
	}
	
	/**
	 * @return id
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * @return predecesseur
	 */
	public int getPredecesseur() {
		return this.predecesseur;
	}
	
	/* non-javadoc @see java.lang.Object#String() */
	 @Override
	public String toString() {
		return  getNom() + getDuree()
		        + getId() + getPredecesseur();
	}

	public void ajouter() {
		
	    if (!isValide(this.nom, this.duree, this.id, this.predecesseur)) {
	        throw new IllegalArgumentException("Identifiant déja utilisé.");
	    }
	    for (int i = 0; i < tachesStockees.length; i++) {
	        if (tachesStockees[i] == null) {
	            tachesStockees[i] = this;
	            return;
	        }
	    }

	    
	    Tache[] nouveauStock = new Tache[tachesStockees.length * 2];
	    for (int i = 0; i < tachesStockees.length; i++) {
	        nouveauStock[i] = tachesStockees[i];
	    }
	    nouveauStock[tachesStockees.length] = this;
	    tachesStockees = nouveauStock;
	}
	

		
	public void modifier(String nouveauNom, int nouvelleDuree, int nouvelId, int nouveauPredecesseur) {
	    // Vérifie si les nouvelles valeurs sont valides
	    if (!isValide(nouveauNom, nouvelleDuree, nouvelId, nouveauPredecesseur)) {
	        throw new IllegalArgumentException("Les nouvelles valeurs ne sont pas valides.");
	    }

	    // Vérifie si l'identifiant est déjà utilisé par une autre tâche
	    for (Tache tache : tachesStockees) {
	        if (tache != null && tache.getId() == nouvelId && tache != this) {
	            throw new IllegalArgumentException("L'identifiant est déjà utilisé par une autre tâche.");
	        }
	    }

	    // Met à jour les attributs de la tâche
	    this.nom = nouveauNom;
	    this.duree = nouvelleDuree;
	    this.id = nouvelId;
	    this.predecesseur = nouveauPredecesseur;
	}

	
	public void retirer() {
	    boolean existe = false;

	    // Vérifie si la tâche existe dans le tableau
	    for (Tache tache : tachesStockees) {
	        if (tache == this) {
	            existe = true;
	            break;
	        }
	    }

	    if (!existe) {
	        throw new IllegalArgumentException("La tâche n'existe pas dans le stockage.");
	    }
		
		
		
	    for (int i = 0; i < tachesStockees.length; i++) {
	        if (tachesStockees[i] == this) {
	            // Remove the task by setting the index to null
	            tachesStockees[i] = null;

	            // Shift remaining tasks to fill the gap
	            for (int j = i; j < tachesStockees.length - 1; j++) {
	                tachesStockees[j] = tachesStockees[j + 1];
	            }

	            // Set the last position to null after shifting
	            tachesStockees[tachesStockees.length - 1] = null;
	            return;
	        }
	    }
	    throw new IllegalArgumentException("La tâche n'existe pas dans le stockage.");
	}
	
	
	public static Tache[] getTachesStockees() {
	    return tachesStockees;
	}

	
	

}
