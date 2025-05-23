
package iut.info1.sae.planning;

import java.util.ArrayList;
import java.util.List;
import iut.info1.sae.planning.Gestionnaire;

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
        if (tache.getPredecesseurs().isEmpty()) {
            return 0;
        }

        // Calculer la date au plus tôt en fonction des prédécesseurs
        int auPlusTot = 0;
        for (int idPredecesseur : tache.getPredecesseurs()) {
            Tache predecesseur = gestionnaire.trouverTacheParId(idPredecesseur);
            if (predecesseur != null) {
                int finPredecesseur = auPlusTotTache(predecesseur) - predecesseur.getDuree();
                auPlusTot = Math.min(auPlusTot, finPredecesseur);
            }
        }
		if (auPlusTot < 0) {
			auPlusTot = -auPlusTot;
		}

        return auPlusTot;
    }
    /**
     * Détermine si une tâche est critique.
     *
     * @param tache La tâche à vérifier.
     * @return margeTotalTache si la tâche est critique, false sinon.
     */
    public boolean tacheCritique(Tache tache) {
    	return margeTotalTache(tache) == 0;
    }

    /**
     * Calcule le chemin critique du projet.
     *
     * @return Liste des tâches formant le chemin critique.
     */
    public List<Tache> cheminCritique() {
        List<Tache> cheminCritique = new ArrayList<>();
        for (Tache tache : gestionnaire.calculerOrdre()) {
            if (margeTotalTache(tache) == 0) {
                cheminCritique.add(tache);
            }
        }
        return cheminCritique;
    }
    /**
     * Calcule la marge libre pour une tâche donnée.
     *
     * @param tache La tâche pour laquelle calculer la marge libre.
     * @return La marge libre.
     */
    public int margeLibreTache(Tache tache) {
        int dateFinTache = auPlusTotTache(tache) + tache.getDuree();
        int margeLibre = Integer.MAX_VALUE;

        for (Tache successeur : gestionnaire.calculerOrdre()) {
            if (successeur.getPredecesseurs().contains(tache.getId())) {
                int auPlusTotSuccesseur = auPlusTotTache(successeur);
                margeLibre = Math.min(margeLibre, auPlusTotSuccesseur - dateFinTache);
            }
        }

        return margeLibre == Integer.MAX_VALUE ? 0 : margeLibre;
    }

    /**
     * Calcule la marge totale pour une tâche donnée.
     *
     * @param tache La tâche pour laquelle calculer la marge totale.
     * @return La marge totale.
     */
    public int margeTotalTache(Tache tache) {
        int margeTotale = auPlusTardTache(tache) - auPlusTotTache(tache);
        return margeTotale;
    }
    /**
     * Convertit une durée en jours-homme.
     *
     * @param duree La durée à convertir.
     * @return La durée en jours-homme.
     */
    public static double conversionJourHomme(int duree) {
        final int HEURES_PAR_JOUR = 8;
        return (double) duree / HEURES_PAR_JOUR;
    }

    /**
     * Convertit une durée en mois-homme.
     *
     * @param duree La durée à convertir.
     * @return La durée en mois-homme.
     */
    public int conversionMoisHomme(int duree) {
        final int HEURES_PAR_JOUR = 8;
        final int JOURS_PAR_MOIS = 20;
        double joursHomme = conversionJourHomme(duree);
        return (int) (joursHomme / JOURS_PAR_MOIS);
    }

    /**
     * Affiche les résultats des calculs.
     */
    public void afficherResultats() {
        System.out.println("Résultats de l'ordonnancement des tâches :");
        
        for (Tache tache : gestionnaire.calculerOrdre()) {
            int auPlusTot = auPlusTotTache(tache);
            int auPlusTard = auPlusTardTache(tache);
            int margeLibre = margeLibreTache(tache);
            int margeTotale = margeTotalTache(tache);
            boolean estCritique = tacheCritique(tache);
            
            System.out.printf("Tâche %d (%s) :\n", tache.getId(), tache.getNom());
            System.out.printf("  - Au plus tôt : %d\n", auPlusTot);
            System.out.printf("  - Au plus tard : %d\n", auPlusTard);
            System.out.printf("  - Marge libre : %d\n", margeLibre);
            System.out.printf("  - Marge totale : %d\n", margeTotale);
            System.out.printf("  - Critique : %s\n", estCritique ? "Oui" : "Non");
        }

        System.out.println("\nChemin critique :");
        List<Tache> cheminCritique = cheminCritique();
        for (Tache tache : cheminCritique) {
            System.out.printf("Tâche %d (%s)\n", tache.getId(), tache.getNom());
        }

        int dateFinProjet = auPlusTotFinProjet();
        System.out.printf("\nDate au plus tôt pour la fin du projet : %d\n", dateFinProjet);
    }

}