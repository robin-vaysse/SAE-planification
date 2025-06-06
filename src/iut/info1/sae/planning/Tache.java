/*
 * Tache.java                                              06/06/2025
 * IUT Rodez Info1 2024 2025 TD1 -Pas de copyright
 */
package iut.info1.sae.planning;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe représentant une tâche dans un projet.
 * Une tâche est définie par un identifiant unique, un nom, une durée, 
 * et une liste de prédécesseurs
 * (les tâches qui doivent être terminées avant celle-ci).
 * @author Helg.Florian/Liao.Mattieu/Petit.Valentin/Rebourg.Noé/Vaysse.Robin
 */
public class Tache {

    /** Liste statique contenant toutes les tâches créées. */
    private static List<Tache> tachesStockees = new ArrayList<>();

    /** Identifiant unique de la tâche. */
    private int id;

    /** Nom de la tâche. */
    private String nom;

    /** Durée de la tâche en jours. */
    private int duree;

    /** Liste des identifiants des tâches prédécesseurs. */
    private List<Integer> predecesseur;

    /** Durée maximale autorisée pour une tâche. */
    public final static int DUREE_MAX = 100;

    /** Durée minimale autorisée pour une tâche. */
    public final static int DUREE_MIN = 0;

    /**
     * Constructeur de la classe Tache.
     * Initialise une tâche avec un identifiant, un nom, une durée
     * et une liste de prédécesseurs.
     * @param id Identifiant unique de la tâche.
     * @param nom Nom de la tâche.
     * @param duree Durée de la tâche en jours.
     * @param predecesseur Liste des identifiants des tâches prédécesseurs.
     * @throws IllegalArgumentException Si les valeurs fournies 
     *                                  ne sont pas valides.
     */
    public Tache(int id, String nom, int duree, List<Integer> predecesseur) {
        if (!isValide(id, nom, duree, predecesseur)) {
            throw new IllegalArgumentException("Les valeurs fournies "
                                              + "ne sont pas valides.");
        }

        this.id = id;
        this.nom = nom;
        this.duree = duree;
        this.predecesseur = new ArrayList<>(predecesseur);
        
        /* Ajoute automatiquement la tâche à la liste des tâches stockées */
        ajouter();
    }

    /**
     * Vérifie si les paramètres fournis pour une tâche sont valides.
     * @param nom Nom de la tâche.
     * @param duree Durée de la tâche.
     * @param id Identifiant unique de la tâche.
     * @param predecesseur Liste des identifiants des tâches prédécesseurs.
     * @return true si les paramètres sont valides, false sinon.
     */
    public static boolean isValide(int id, String nom, int duree, 
    		                      List<Integer> predecesseur) {
        if (nom == null || nom.trim().isEmpty()) {
            return false;
        }
        if (duree < DUREE_MIN || duree > DUREE_MAX || id <= 0) {
            return false;
        }

        /* Vérifie que l'identifiant est unique */
        for (Tache tache : tachesStockees) {
            if (tache != null && tache.getId() == id) {
                return false;
            }
        }

        return true;
    }

    /**
     * Retourne l'identifiant de la tâche.
     * @return Identifiant de la tâche.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Retourne le nom de la tâche.
     * @return Nom de la tâche.
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Retourne la durée de la tâche.
     * @return Durée de la tâche en jours.
     */
    public int getDuree() {
        return this.duree;
    }

    /**
     * Retourne la liste des identifiants des tâches prédécesseurs.
     * @return Liste des identifiants des tâches prédécesseurs.
     */
    public List<Integer> getPredecesseurs() {
        return this.predecesseur;
    }

    /* non-javadoc @see java.lang.Object#String() */
    @Override
    public String toString() {
        return "Identifiant : " + getId() + " Nom de la tâche : " + getNom() +
               " Durée : " + getDuree() + " Prédécesseurs : " +
        		 getPredecesseurs();
    }

    /** Ajoute la tâche à la liste statique des tâches stockées. */
    public void ajouter() {
        if (!tachesStockees.contains(this)) {
            tachesStockees.add(this);
        }
    }

    /**
     * Modifie les attributs de la tâche.
     * @param nouvelId Nouvel identifiant de la tâche.
     * @param nouveauNom Nouveau nom de la tâche.
     * @param nouvelleDuree Nouvelle durée de la tâche.
     * @param nouveauPredecesseur Nouvelle liste des identifiants 
     *        des tâches prédécesseurs.
     * @throws IllegalArgumentException Si les nouvelles valeurs 
     *         ne sont pas valides.
     */
    public void modifier(int nouvelId, String nouveauNom, int nouvelleDuree,
    		             List<Integer> nouveauPredecesseur) {
        retirer();

        if(!isValide(nouvelId, nouveauNom, nouvelleDuree, nouveauPredecesseur)){
            ajouter();
            throw new IllegalArgumentException("Les nouvelles valeurs" 
                                              + " ne sont pas valides.");
        }

        this.id = nouvelId;
        this.nom = nouveauNom;
        this.duree = nouvelleDuree;
        this.predecesseur = new ArrayList<>(nouveauPredecesseur);

        ajouter();
    }

    /** Retire la tâche de la liste statique des tâches stockées. */
    public void retirer() {
        tachesStockees.remove(this);
    }

    /**
     * Retourne la liste statique des tâches stockées.
     * @return Liste des tâches stockées.
     */
    public static List<Tache> getTachesStockees() {
        return tachesStockees;
    }

    /* non-javadoc @see java.lang.Object#equals(java.lang.Object) */
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

    /* non-javadoc @see java.lang.Object#hashCode() */
    @Override
    public int hashCode() {
        return Objects.hash(id, nom, duree, predecesseur);
    }
}
