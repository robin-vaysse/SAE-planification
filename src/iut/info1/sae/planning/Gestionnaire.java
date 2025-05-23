
package iut.info1.sae.planning;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe permettant de gérer une liste de tâches et de calculer l'ordre des tâches
 * ainsi que la durée maximale du projet.
 */
public class Gestionnaire {
    private final List<Tache> taches;

    /**
     * Constructeur de la classe Gestionnaire.
     * Initialise la liste des tâches.
     *
     * @param taches Liste initiale des tâches à gérer.
     */
	public Gestionnaire(List<Tache> taches) {
	    this.taches = new ArrayList<>(taches);
	
	    /* Vérifier que toutes les dépendances existent */
	    for (Tache tache : taches) {
	        for (int idPredecesseur : tache.getPredecesseurs()) {
	            if (trouverTacheParId(idPredecesseur) == null) {
	                throw new IllegalArgumentException("La tâche avec l'ID " + idPredecesseur + " n'existe pas.");
	            }
	        }
	    }
	
	    /* Vérifier l'absence de cycles complexes */
	    if (contientCycle()) {
	        throw new IllegalArgumentException("Le graphe des dépendances contient un cycle.");
	    }
	}



    /**
     * Calcule l'ordre des tâches en respectant les dépendances.
     * @return Liste des tâches dans un ordre valide.
     */
    public List<Tache> calculerOrdre() {
        List<Tache> ordre = new ArrayList<>();
        List<Integer> visites = new ArrayList<>();

        for (Tache tache : taches) {
            if (!visites.contains(tache.getId())) {
                ajouterDansOrdre(tache, visites, ordre);
            }
        }

        return ordre;
    }

    /**
     * Ajoute une tâche et ses prédécesseurs dans l'ordre.
     *
     * @param tache   Tâche actuelle.
     * @param visites Liste des tâches déjà visitées.
     * @param ordre   Liste des tâches dans l'ordre.
     */
    private void ajouterDansOrdre(Tache tache, List<Integer> visites, List<Tache> ordre) {
        for (int idPredecesseur : tache.getPredecesseurs()) {
            if (!visites.contains(idPredecesseur)) {
            	
                Tache predecesseur = trouverTacheParId(idPredecesseur);
                if (predecesseur != null) {
                	
                    ajouterDansOrdre(predecesseur, visites, ordre);
                }
            }
        }

        visites.add(tache.getId());
        ordre.add(tache);
    }

    /**
     * Calcule la durée maximale du projet.
     *
     * @return Durée maximale du projet.
     */
	public int calculerDureeMax() {
	    /** Calculer l'ordre des tâches en respectant les dépendances */
	    List<Tache> ordre = calculerOrdre();
	
	    /**
	     * Trouver le plus grand ID parmi les tâches pour déterminer la taille nécessaire de la liste.
	     */
	    int maxId = 0;
	    for (Tache tache : taches) {
	        if (tache.getId() > maxId) {
	            maxId = tache.getId();
	        }
	    }
	
	    /**
	     * Initialiser une liste pour stocker les durées maximales, 
	     * avec une taille suffisante.
	     */
	    List<Integer> durees = new ArrayList<>();
	    for (int i = 0; i <= maxId; i++) {
	        durees.add(0); // Initialiser chaque élément à 0
	    }
	
	    /** Parcourir les tâches dans l'ordre topologique */
	    for (Tache tache : ordre) {
	        int dureeMax = 0;
	
	        /** Calculer la durée maximale en fonction des prédécesseurs */
	        for (int idPredecesseur : tache.getPredecesseurs()) {
	            Tache predecesseur = trouverTacheParId(idPredecesseur);
	            if (predecesseur != null) {
	                /** Mettre à jour la durée maximale en fonction des prédécesseurs */
	                dureeMax = Math.max(dureeMax, durees.get(predecesseur.getId()));
	            }
	        }
	
	        /** Ajouter la durée de la tâche actuelle à la durée maximale calculée */
	        durees.set(tache.getId(), dureeMax + tache.getDuree());
	    }
	
	    /** Trouver la durée maximale du projet en parcourant la liste des durées */
	    int dureeMaxProjet = 0;
	    for (int duree : durees) {
	        if (duree > dureeMaxProjet) {
	            dureeMaxProjet = duree;
	        }
	    }
	    
	    return dureeMaxProjet;
	}

    /**
     * Trouve une tâche par son ID.
     *
     * @param id ID de la tâche à trouver.
     * @return La tâche correspondante ou null si elle n'existe pas.
     */
    public Tache trouverTacheParId(int id) {
        for (Tache tache : taches) {
            if (tache.getId() == id) {
                return tache;
            }
        }
        return null;
    }
    

	/**
	 * Vérifie si le graphe des dépendances contient un cycle.
	 *
	 * @return true si un cycle est détecté, false sinon.
	 */
	private boolean contientCycle() {
	    List<Integer> visites = new ArrayList<>();
	    List<Integer> recStack = new ArrayList<>();
	
	    for (Tache tache : taches) {
	        if (!visites.contains(tache.getId())) {
	            if (detecterCycle(tache, visites, recStack)) {
	                return true;
	            }
	        }
	    }
	    return false;
	}
	
	/**
	 * Détecte un cycle dans le graphe des dépendances en utilisant DFS.
	 *
	 * @param tache    Tâche actuelle.
	 * @param visites  Liste des tâches visitées.
	 * @param recStack Pile de récursion pour détecter les cycles.
	 * @return true si un cycle est détecté, false sinon.
	 */
	private boolean detecterCycle(Tache tache, List<Integer> visites, List<Integer> recStack) {
	    visites.add(tache.getId());
	    recStack.add(tache.getId());
	
	    for (int idPredecesseur : tache.getPredecesseurs()) {
	        Tache predecesseur = trouverTacheParId(idPredecesseur);
	        if (predecesseur != null) {
	            if (!visites.contains(predecesseur.getId()) && detecterCycle(predecesseur, visites, recStack)) {
	                return true;
	            } else if (recStack.contains(predecesseur.getId())) {
	                return true;
	            }
	        }
	    }
	
	    recStack.remove((Integer) tache.getId());
	    return false;
	}

	/**
	 * Trouve les tâches successeurs d'une tâche donnée.
	 *
	 * @param t Tâche pour laquelle trouver les successeurs.
	 * @return Un tableau contenant les IDs des tâches successeurs.
	 */
	public int[] trouverTachesSuccesseurs(Tache t) {
		int[] tachesSuccesseurs = new int[t.getPredecesseurs().size()];
		for (int i = 0; i < t.getPredecesseurs().size(); i++) {
			tachesSuccesseurs[i] = t.getPredecesseurs().get(i);
		}
		return tachesSuccesseurs;
	}


}
