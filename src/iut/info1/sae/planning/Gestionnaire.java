/*
 * Gestionnaire.java                                          06/06/2025
 * IUT Rodez Info1 2024 2025 TD1 -Pas de copyright              
 */
package iut.info1.sae.planning;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe permettant de gérer une liste de tâches et 
 * de calculer l'ordre des tâches ainsi que la durée maximale du projet.
 * 
 * @author Helg.Florian/Liao.Mattieu/Petit.Valentin/Rebourg.Noé/Vaysse.Robin
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
	                throw new IllegalArgumentException("La tâche avec l'ID " 
	                                       + idPredecesseur + " n'existe pas.");
	            }
	        }
	    }
	
	    /* Vérifier l'absence de cycles complexes */
	    if (contientCycle()) {
	        throw new IllegalArgumentException("Le graphe des dépendances "
	        		                           + "contient un cycle.");
	    }
	}




	/**
	 * Calcule l'ordre des tâches en respectant leurs dépendances 
	 * à l'aide d'un tri topologique.
	 * Les tâches sans prédécesseurs sont traitées en premier, 
	 * et leurs successeurs voient leur 
	 * degré d'entrée réduit au fur et à mesure.
	 *
	 * @return Une liste des tâches dans un ordre valide
	 *         respectant leurs dépendances.
	 * @throws IllegalArgumentException Si le graphe 
	 *         des dépendances contient un cycle.
	 */
	public List<Tache> calculerOrdre() {
	    /* Initialiser le degré d'entrée pour chaque tâche */
	    List<Integer> degreEntree = new ArrayList<>();
	    for (Tache tache : taches) {
	        degreEntree.add(tache.getPredecesseurs().size());
	    }
	    
	    List<Tache> ordre = new ArrayList<>();
	    List<Tache> sansDependances = new ArrayList<>();
	
	    /* Ajouter les tâches avec degré d'entrée 0 à la liste sans dépendances*/
	    for (int i = 0; i < taches.size(); i++) {
	        if (degreEntree.get(i) == 0) {
	            sansDependances.add(taches.get(i));
	        }
	    }
	
	    /* Traiter les tâches sans dépendances */
	    while (!sansDependances.isEmpty()) {
	    	
	        Tache tache = sansDependances.remove(0);
	        ordre.add(tache);
	
	        /* Réduire le degré d'entrée des successeurs */
	        for (int i = 0; i < taches.size(); i++) {
	            Tache successeur = taches.get(i);
	            
	            if (successeur.getPredecesseurs().contains(tache.getId())) {
	            	
	                degreEntree.set(i, degreEntree.get(i) - 1);
	                if (degreEntree.get(i) == 0) {
	                	
	                    sansDependances.add(successeur);
	                }
	            }
	        }
	    }
	
	    /* Vérifier si toutes les tâches ont été traitées */
	    if (ordre.size() != taches.size()) {
	        throw new IllegalArgumentException("Le graphe des dépendances "
	        		                           + "contient un cycle.");
	    }
	
	    return ordre;
	}


    /**
     * Calcule la durée maximale du projet.
     * @return Durée maximale du projet.
     */
	public int calculerDureeMax() {
	    /* Calculer l'ordre des tâches en respectant les dépendances */
	    List<Tache> ordre = calculerOrdre();
	    /* Trouver l'ID maximal pour initialiser la liste des durées */
	    int maxId = 0;
	    for (Tache tache : taches) {
	        if (tache.getId() > maxId) {
	            maxId = tache.getId();
	        }
	    }
	
	    List<Integer> durees = new ArrayList<>();
	    for (int i = 0; i <= maxId; i++) {
	        durees.add(0); // Initialiser chaque élément à 0
	    }
	
	    /* Parcourir les tâches dans l'ordre topologique */
	    for (Tache tache : ordre) {
	        int dureeMax = 0;
	
	        /* Calculer la durée maximale en fonction des prédécesseurs */
	        for (int idPredecesseur : tache.getPredecesseurs()) {
	            Tache predecesseur = trouverTacheParId(idPredecesseur);
	            if (predecesseur != null) {
	                dureeMax = Math.max(dureeMax, 
	                		            durees.get(predecesseur.getId()));
	            }
	        }
	        durees.set(tache.getId(), dureeMax + tache.getDuree());
	    }
	    /* Trouver la durée maximale du projet */
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
	 * @return false si il n'y a pas de cycle
	 */
	private boolean contientCycle() {
		calculerOrdre();
		return false; 
	}

	
	
	/**
	 * Trouve les tâches successeurs d'une tâche donnée.
	 * @param t Tâche pour laquelle trouver les successeurs.
	 * @return Un tableau contenant les IDs des tâches successeurs.
	 */
	public int[] trouverTachesPredecesseurs(Tache t) {
		int[] Predecesseurs = new int[t.getPredecesseurs().size()];
		for (int i = 0; i < t.getPredecesseurs().size(); i++) {
			Predecesseurs[i] = t.getPredecesseurs().get(i);
		}
		return Predecesseurs;
	}
	

	/**
	 * Trouve les tâches successeurs d'une tâche donnée.
	 * @param tache La tâche pour laquelle trouver les successeurs.
	 * @return Une liste contenant les tâches successeurs.
	 */
	public List<Tache> trouverTachesSuccesseurs(Tache tache) {
	    List<Tache> successeurs = new ArrayList<>();
	
	    for (Tache t : taches) { // Parcourt toutes les tâches
	        if (t.getPredecesseurs().contains(tache.getId())) {
	            successeurs.add(t); 
	        }
	    }
	
	    return successeurs;
	}



	
	/**
	 * Retourne une représentation textuelle de l'ordonnancement des tâches.
	 * Chaque tâche est affichée avec son ID, son nom et 
	 * sa durée, dans un ordre d'exécution valide.
	 * @return Une chaîne listant les tâches dans l'ordre d'ordonnancement.
	 */
	public String afficherOrdonnancement() {
	    StringBuilder affichageOrdonnancement = new StringBuilder();
	    List<Tache> ordre = calculerOrdre();

	    for (Tache tache : ordre) {
	        affichageOrdonnancement
	        	.append("ID: ").append(tache.getId())
	            .append(" Nom: ").append(tache.getNom())
	            .append(", Durée: ").append(tache.getDuree())
	            .append("\n");
	    }

	    return affichageOrdonnancement.toString();
	}


}
