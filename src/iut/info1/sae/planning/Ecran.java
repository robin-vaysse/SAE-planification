
package iut.info1.sae.planning;

import java.util.List;

/**
 * Classe responsable de l'affichage et des calculs liés à l'ordonnancement des tâches.
 */
public class Ecran {

    private final Gestionnaire gestionnaire;
    private final Tache tache;

    /**
     * Constructeur de la classe Ecran.
     *
     * @param gestionnaire Instance de Gestionnaire pour accéder aux tâches et à leur ordonnancement.
     */
    public Ecran(Gestionnaire gestionnaire, Tache tache) {
        this.gestionnaire = gestionnaire;
        this.tache = tache;
    }

    /**
     * Calcule la date au plus tôt pour une tâche donnée.
     *
     * @param tache La tâche pour laquelle calculer la date au plus tôt.
     * @return La date au plus tôt.
     */
    public int auPlusTotTache(Tache tache) {
        if (tache.getPredecesseurs().isEmpty()) {
            return 0;
        }

        // Calculer la date au plus tôt en fonction des prédécesseurs
        int auPlusTot = 0;
        for (int idPredecesseur : tache.getPredecesseurs()) {
            Tache predecesseur = gestionnaire.trouverTacheParId(idPredecesseur);
            if (predecesseur != null) {
                int finPredecesseur = auPlusTotTache(predecesseur) + predecesseur.getDuree();
                auPlusTot = Math.max(auPlusTot, finPredecesseur);
            }
        }

        return auPlusTot;
    }

    /**
     * Calcule la date au plus tôt pour la fin du projet.
     *
     * @return La date au plus tôt pour la fin du projet.
     */
    public int auPlusTotFinProjet() {
        int auPlusTotFin = 0;
		for (Tache tache : gestionnaire.calculerOrdre()) {
			int finTache = auPlusTotTache(tache) + tache.getDuree();
			auPlusTotFin = Math.max(auPlusTotFin, finTache);
		}
        return auPlusTotFin;
    }

    /**
     * Calcule la date au plus tard pour une tâche donnée.
     *
     * @param tache La tâche pour laquelle calculer la date au plus tard.
     * @return La date au plus tard.
     */
    public int auPlusTardTache(Tache tache) {
        int auPlusTard;
        
        auPlusTard = auPlusTotFinProjet() - tache.getDuree();

        for (Tache autreTache : gestionnaire.calculerOrdre()) {
            if (autreTache.getPredecesseurs().contains(tache.getId())) {
                int debutSuccesseur = auPlusTardTache(autreTache) - autreTache.getDuree();
                auPlusTard = Math.min(auPlusTard, debutSuccesseur);
            }
        }

        return auPlusTard;
    }

    /**
     * Détermine si une tâche est critique.
     *
     * @param tache La tâche à vérifier.
     * @return true si la tâche est critique, false sinon.
     */
    public boolean tacheCritique(Tache tache) {
        // À implémenter
        return false;
    }

    /**
     * Calcule le chemin critique du projet.
     *
     * @return Liste des tâches formant le chemin critique.
     */
    public List<Tache> cheminCritique() {
        // À implémenter
        return null;
    }

    /**
     * Calcule la marge libre pour une tâche donnée.
     *
     * @param tache La tâche pour laquelle calculer la marge libre.
     * @return La marge libre.
     */
    public int margeLibreTache(Tache tache) {
        // À implémenter
        return 0;
    }

    /**
     * Calcule la marge totale pour une tâche donnée.
     *
     * @param tache La tâche pour laquelle calculer la marge totale.
     * @return La marge totale.
     */
    public int margeTotalTache(Tache tache) {
        // À implémenter
        return 0;
    }

    /**
     * Convertit une durée en jours-homme.
     *
     * @param duree La durée à convertir.
     * @return La durée en jours-homme.
     */
    public int conversionJourHomme(int duree) {
        // À implémenter
        return 0;
    }

    /**
     * Convertit une durée en mois-homme.
     *
     * @param duree La durée à convertir.
     * @return La durée en mois-homme.
     */
    public int conversionMoisHomme(int duree) {
        // À implémenter
        return 0;
    }

    /**
     * Affiche les résultats des calculs.
     */
    public void afficherResultats() {
        // À implémenter
    }
}
