
package iut.info1.sae.planning;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Tache {

    private static List<Tache> tachesStockees = new ArrayList<>();

    private int id;
    private String nom;
    private int duree;
    private List<Integer> predecesseur;

    public final static int DUREE_MAX = 100;
    public final static int DUREE_MIN = 1;

    public Tache(int id, String nom, int duree, List<Integer> predecesseur) {
        if (!isValide(nom, duree, id, predecesseur)) {
            throw new IllegalArgumentException("Les valeurs fournies ne sont pas valides.");
        }

        this.id = id;
        this.nom = nom;
        this.duree = duree;
        this.predecesseur = new ArrayList<>(predecesseur);

        ajouter(); // Automatically add to tachesStockees
    }

    public static boolean isValide(String nom, int duree, int id, List<Integer> predecesseur) {
        if (nom == null || nom.trim().isEmpty()) {
            return false;
        }
        if (duree < DUREE_MIN || duree > DUREE_MAX || id <= 0) {
            return false;
        }

        // Check for unique ID
        for (Tache tache : tachesStockees) {
            if (tache != null && tache.getId() == id) {
                return false;
            }
        }

        return true;
    }

    public int getId() {
        return this.id;
    }

    public String getNom() {
        return this.nom;
    }

    public int getDuree() {
        return this.duree;
    }

    public List<Integer> getPredecesseurs() {
        return this.predecesseur;
    }

    @Override
    public String toString() {
        return "Identifiant : " + getId() + " Nom de la tâche : " + getNom() +
               " Durée : " + getDuree() + " Prédécesseurs : " + getPredecesseurs();
    }

    public void ajouter() {
        if (!tachesStockees.contains(this)) {
            tachesStockees.add(this);
        }
    }

    public void modifier(int nouvelId, String nouveauNom, int nouvelleDuree, List<Integer> nouveauPredecesseur) {
        retirer();

        if (!isValide(nouveauNom, nouvelleDuree, nouvelId, nouveauPredecesseur)) {
            ajouter();
            throw new IllegalArgumentException("Les nouvelles valeurs ne sont pas valides.");
        }

        this.id = nouvelId;
        this.nom = nouveauNom;
        this.duree = nouvelleDuree;
        this.predecesseur = new ArrayList<>(nouveauPredecesseur);

        ajouter();
    }

    public void retirer() {
        tachesStockees.remove(this);
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
        return Objects.hash(id, nom, duree, predecesseur);
    }
}
