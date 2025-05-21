
package iut.info1.sae.planning;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Tache {

	private static List<Tache> tachesStockees = new ArrayList<>();

    private String nom;
    private int duree;

    public final static int DUREE_MAX = 100;
    public final static int DUREE_MIN = 1;

    private int id;
    private List<Integer> predecesseur;

    
	public Tache(String nom, int duree, int id,
			            List<Integer> predecesseur) {
		if (!isValide(nom, duree, id, predecesseur)) {
			throw new IllegalArgumentException("Les valeurs fournies ne sont pas valides.");
		}

		// Vérifie que l'id est unique
		for (Tache identifiant : tachesStockees) {
			if (identifiant != null && identifiant.getId() == id) {
				throw new IllegalArgumentException("L'identifiant est déjà utilisé par une autre tâche.");
			}
		}
    	this.nom = nom;
        this.duree = duree;
        this.id = id;
        this.predecesseur = predecesseur;
    }

    public static boolean isValide(String nom, int duree, int id, List<Integer> predecesseur) {
        if (nom == null || nom.trim().isEmpty()) {
            return false;
        }
        if (duree < DUREE_MIN || duree > DUREE_MAX || id <= 0) {
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

    public String getNom() {
        return this.nom;
    }

    public int getDuree() {
        return this.duree;
    }

    public int getId() {
        return this.id;
    }

    public List<Integer> getPredecesseur() {
        return this.predecesseur;
    }

    @Override
    public String toString() {
        return "Nom de la tache " + getNom() + "Durée : " + getDuree() 
                + "Identifient : " + getId() + "Predecesseur : " +getPredecesseur();
    }

    public void ajouter() {
        if (!tachesStockees.contains(this)) {
            tachesStockees.add(this);
        }
    }


	public void modifier(String nouveauNom, int nouvelleDuree, int nouvelId, List<Integer> nouveauPredecesseur) {
	    if (!isValide(nouveauNom, nouvelleDuree, nouvelId, nouveauPredecesseur)) {
	        throw new IllegalArgumentException("Les nouvelles valeurs ne sont pas valides.");
	    }
	    this.nom = nouveauNom;
	    this.duree = nouvelleDuree;
	    this.id = nouvelId;
	    this.predecesseur = new ArrayList<>(nouveauPredecesseur);
	}

    public void retirer() {
        if (!tachesStockees.remove(this)) {
            throw new IllegalArgumentException("La tâche n'existe pas dans le stockage.");
        }
    }

    public static List<Tache> getTachesStockees() {
        return tachesStockees;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Tache tache = (Tache) obj;
        return id == tache.id &&
               duree == tache.duree &&
               nom.equals(tache.nom) &&
               predecesseur.equals(tache.predecesseur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom, duree, id, predecesseur);
    }
}