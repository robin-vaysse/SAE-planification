
package iut.info1.sae.planning;

import java.util.ArrayList;
import java.util.List;

public class Tache {

    private static List<Tache> tachesStockees = new ArrayList<>();

    private String nom;
    private int duree;

    public final static int DUREE_MAX = 100;
    public final static int DUREE_MIN = 1;

    private int id;
    private int predecesseur;

    public void creationTache(String nom, int duree, int id, int predecesseur) {
        this.nom = nom;
        this.duree = duree;
        this.id = id;
        this.predecesseur = predecesseur;
    }

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

    public String getNom() {
        return this.nom;
    }

    public int getDuree() {
        return this.duree;
    }

    public int getId() {
        return this.id;
    }

    public int getPredecesseur() {
        return this.predecesseur;
    }

    @Override
    public String toString() {
        return getNom() + getDuree() + getId() + getPredecesseur();
    }

    public void ajouter() {
        if (!isValide(this.nom, this.duree, this.id, this.predecesseur)) {
            throw new IllegalArgumentException("Identifiant déjà utilisé.");
        }
        tachesStockees.add(this);
    }

    public void modifier(String nouveauNom, int nouvelleDuree, int nouvelId, int nouveauPredecesseur) {
        if (!isValide(nouveauNom, nouvelleDuree, nouvelId, nouveauPredecesseur)) {
            throw new IllegalArgumentException("Les nouvelles valeurs ne sont pas valides.");
        }

        for (Tache tache : tachesStockees) {
            if (tache != null && tache.getId() == nouvelId && tache != this) {
                throw new IllegalArgumentException("L'identifiant est déjà utilisé par une autre tâche.");
            }
        }

        this.nom = nouveauNom;
        this.duree = nouvelleDuree;
        this.id = nouvelId;
        this.predecesseur = nouveauPredecesseur;
    }

    public void retirer() {
        if (!tachesStockees.remove(this)) {
            throw new IllegalArgumentException("La tâche n'existe pas dans le stockage.");
        }
    }

    public static List<Tache> getTachesStockees() {
        return tachesStockees;
    }
}