
package iut.info1.sae.planning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Classe permettant de gérer une liste de tâches, en vérifiant l'absence de cycles
 * et en calculant l'ordre des tâches ainsi que la durée maximale du projet.
 */
public class Gestionnaire {
    private final List<Tache> taches;

    /**
     * Constructeur de la classe Gestionnaire.
     * Initialise la liste des tâches et vérifie qu'il n'y a pas de cycles.
     *
     * @param taches Liste initiale des tâches à gérer.
     * @throws IllegalArgumentException si un cycle est détecté dans les tâches.
     */
    public Gestionnaire(List<Tache> taches) {
        this.taches = new ArrayList<>(taches);
        if (contientCycle()) {
            throw new IllegalArgumentException("Cycle détecté dans les tâches.");
        }
    }

    /**
     * Ajoute une nouvelle tâche au gestionnaire après vérification.
     *
     * @param nouvelleTache La tâche à ajouter.
     * @throws IllegalArgumentException si l'ID de la tâche existe déjà,
     *                                  si une dépendance est manquante,
     *                                  ou si l'ajout crée un cycle.
     */
    public void ajouterTache(Tache nouvelleTache) {
        // Vérifie si l'ID de la tâche existe déjà
        for (Tache tache : taches) {
            if (tache.getId() == nouvelleTache.getId()) {
                throw new IllegalArgumentException("Une tâche avec cet "
                		                           + "ID existe déjà.");
            }
        }

        // Vérifie si les prédécesseurs existent
        for (int idPredecesseur : nouvelleTache.getPredecesseurs()) {
            boolean existe = false;
            for (Tache tache : taches) {
                if (tache.getId() == idPredecesseur) {
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                throw new IllegalArgumentException("Le prédécesseur avec l'ID " 
                                                   + idPredecesseur 
                                                   + " n'existe pas.");
            }
        }

        // Ajoute la tâche et vérifie les cycles
        taches.add(nouvelleTache);
        if (contientCycle()) {
            taches.remove(nouvelleTache);
            throw new IllegalArgumentException("Ajout de la tâche "
            		                            + "entraînerait un cycle.");
        }
    }

    /**
     * Affiche l'ordre des tâches et la durée maximale du projet.
     */
    public void afficherOrdreEtDureeMax() {
        List<Tache> ordre = calculerOrdreTopologique();
        int dureeMax = calculerDureeMax(ordre);

        System.out.println("Ordre des tâches :");
        for (Tache tache : ordre) {
            System.out.println("Tâche " + tache.getId() + " : " + tache.getNom());
        }
        System.out.println("Durée maximale du projet : " + dureeMax + " unités de temps.");
    }

    /**
     * Calcule l'ordre topologique des tâches.
     *
     * @return Liste des tâches dans un ordre valide (sans cycle).
     * @throws IllegalArgumentException si un cycle est détecté.
     */
    public List<Tache> calculerOrdreTopologique() {
        List<Tache> ordre = new ArrayList<>();
        Set<Integer> visites = new HashSet<>();
        Set<Integer> enCours = new HashSet<>();

        for (Tache tache : taches) {
            if (!visites.contains(tache.getId())) {
                effectuerDFS(tache, visites, enCours, ordre);
            }
        }

        return ordre;
    }

    /**
     * Effectue une recherche en profondeur (DFS) pour détecter les cycles
     * et déterminer l'ordre des tâches.
     *
     * @param tache   Tâche actuelle.
     * @param visites Ensemble des tâches déjà visitées.
     * @param enCours Ensemble des tâches en cours de visite.
     * @param ordre   Liste des tâches dans l'ordre topologique.
     * @throws IllegalArgumentException si un cycle est détecté.
     */
    private void effectuerDFS(Tache tache, Set<Integer> visites, 
    		                  Set<Integer> enCours, List<Tache> ordre) {
    	
        if (enCours.contains(tache.getId())) {
            throw new IllegalArgumentException("Cycle détecté dans les tâches.");
        }

        if (!visites.contains(tache.getId())) {
            enCours.add(tache.getId());

            for (int idPredecesseur : tache.getPredecesseurs()) {
                Tache predecesseur = trouverTacheParId(idPredecesseur);
                if (predecesseur != null) {
                    effectuerDFS(predecesseur, visites, enCours, ordre);
                }
            }

            enCours.remove(tache.getId());
            visites.add(tache.getId());
            ordre.add(tache);
        }
    }

    /**
     * Calcule la durée maximale du projet en fonction des dépendances.
     *
     * @param ordre Liste des tâches dans l'ordre topologique.
     * @return Durée maximale du projet.
     */
    public int calculerDureeMax(List<Tache> ordre) {
        /** Map pour stocker la durée maximale pour chaque tâche */
        Map<Integer, Integer> dureeMaxParTache = new HashMap<>();

        for (Tache tache : ordre) {
            int dureeMax = 0;

            /** Calculer la durée maximale en fonction des prédécesseurs */
            for (int idPredecesseur : tache.getPredecesseurs()) {
                dureeMax = Math.max(dureeMax, dureeMaxParTache.getOrDefault(idPredecesseur, 0));
            }

            /** Ajouter la durée de la tâche actuelle */
            dureeMaxParTache.put(tache.getId(), dureeMax + tache.getDuree());
        }

        /** Retourner la durée maximale du projet */
        return dureeMaxParTache.values().stream().max(Integer::compare).orElse(0);
    }

    /**
     * Vérifie si la liste des tâches contient un cycle.
     *
     * @return true si un cycle est détecté, false sinon.
     */
    private boolean contientCycle() {
        try {
            calculerOrdreTopologique();
            return false;
        } catch (IllegalArgumentException e) {
            return true;
        }
    }

    /**
     * Trouve une tâche par son ID.
     *
     * @param id ID de la tâche à trouver.
     * @return La tâche correspondante ou null si elle n'existe pas.
     */
    private Tache trouverTacheParId(int id) {
        for (Tache tache : taches) {
            if (tache.getId() == id) {
                return tache;
            }
        }
        return null;
    }
}
