/*
 * Ecran.java                                                  06/06/2025
 * IUT Rodez Info1 2024 2025 TD1 -Pas de copyright
 */
package iut.info1.sae.planning;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsable de l'affichage et des calculs liés à l'ordonnancement
 * des tâches.
 * @author Helg.Florian/Liao.Mattieu/Petit.Valentin/Rebourg.Noé/Vaysse.Robin
 */
public class Ecran {

	/** Instance pour accéder aux tâches et à leur ordonnancement */
    private final Gestionnaire gestionnaire;
    
    /**
     * Constructeur de la classe Ecran.
     * @param gestionnaire Instance de Gestionnaire pour accéder
     *                     aux tâches et à leur ordonnancement.
     * @param tache Instance de Tache afin de pouvoir créer, modifier 
     *              et retirer les taches
     */
    public Ecran(Gestionnaire gestionnaire, Tache tache) {
        this.gestionnaire = gestionnaire;
    }

    /**
     * Calcule la date au plus tôt pour une tâche donnée.
     * @param tache La tâche pour laquelle calculer la date au plus tôt.
     * @return La date au plus tôt.
     */
    public int auPlusTotTache(Tache tache) {
        if (tache.getPredecesseurs().isEmpty()) {
            return 0;
        }

        /*Calculer la date au plus tôt en fonction des prédécesseurs*/
        int auPlusTot = 0;
        for (int idPredecesseur : tache.getPredecesseurs()) {
            Tache predecesseur = gestionnaire.trouverTacheParId(idPredecesseur);
            if (predecesseur != null) {
                int finPredecesseur = auPlusTotTache(predecesseur) 
                		              + predecesseur.getDuree();
                auPlusTot = Math.max(auPlusTot, finPredecesseur);
            }
        }
        return auPlusTot;
    }

    /**
     * Calcule la date au plus tôt pour la fin du projet.
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
     * Cette méthode n'est pas le travail de notre propre fruit,
     * puisque ne nous y sommes pas arriver
     * @param tache La tâche pour laquelle calculer la date au plus tard.
     * @return La date au plus tard.
     */
    public int auPlusTardTache(Tache tache) {

        /*
         *  On met auPlusTardMin a Integer.MAX_VALUE
         *  afin de pouvoir le comparer pour récupérer le minimum de la tache
         */
        int auPlusTardMin = Integer.MAX_VALUE;
        /*
         * Si la tâche n'a pas de successeurs,
         * sa date au plus tard est la fin du projet
         */
        List<Tache> successeurs = gestionnaire.trouverTachesSuccesseurs(tache);
        if (successeurs.isEmpty()) {
            int finProjet = auPlusTotFinProjet() - tache.getDuree();
            return finProjet;
        }
        /*
         *  On trouve toutes les tâches qui ont les mêmes prédécesseurs
         *  que cette tâche
         */
        List<Tache> tachesMemesPredecesseurs = new ArrayList<>();
        for (Tache autreTache : gestionnaire.calculerOrdre()) {
            if (autreTache.getPredecesseurs().equals(tache.getPredecesseurs())) {
                tachesMemesPredecesseurs.add(autreTache);
            }
        }
        for (Tache tacheSimilaire : tachesMemesPredecesseurs) {
            List<Tache> successeursTacheSimilaire = 
            		    gestionnaire.trouverTachesSuccesseurs(tacheSimilaire);
            /*
             *  Calculer la date au plus tard basée sur
             *  les successeurs de cette tâche similaire
             */
            for (Tache successeur : successeursTacheSimilaire) {
                int auPlusTardSuccesseur = auPlusTardTache(successeur);
                int dateDebutMax = auPlusTardSuccesseur 
                		           - tacheSimilaire.getDuree();
                auPlusTardMin = Math.min(auPlusTardMin, dateDebutMax);
            }
        }
        /*
         *  Si on n'a trouvé aucune contrainte via les tâches similaires, 
         *  utiliser les successeurs directs
         */
        if (auPlusTardMin == Integer.MAX_VALUE) {
            for (Tache successeur : successeurs) {
                int auPlusTardSuccesseur = auPlusTardTache(successeur);
                int dateDebutMax = auPlusTardSuccesseur - tache.getDuree();
                auPlusTardMin = Math.min(auPlusTardMin, dateDebutMax);
            }
        }
        return auPlusTardMin;
    }
    


    /**
     * Détermine si une tâche est critique.
     * @param tache La tâche à vérifier.
     * @return margeTotalTache si la tâche est critique, false sinon.
     */
    public boolean tacheCritique(Tache tache) {
        return margeTotalTache(tache) == 0;
    }

    
    /**
     * Calcule le chemin critique du projet en suivant les dépendances
     * des tâches critiques.
     * @return Liste des tâches formant le chemin critique.
     */
    public List<Tache> cheminCritique() {
        List<Tache> cheminCritique = new ArrayList<>();
        List<Tache> tachesCritiques = new ArrayList<>();

        for (Tache tache : gestionnaire.calculerOrdre()) {
            if (tacheCritique(tache)) {
                tachesCritiques.add(tache);
            }
        }
        Tache tacheActuelle = tachesCritiques.get(0);
        while (tacheActuelle != null) {
            cheminCritique.add(tacheActuelle);
            Tache prochainSuccesseur = null;
            for (Tache successeur 
            		   : gestionnaire.trouverTachesSuccesseurs(tacheActuelle)) {
                if (tachesCritiques.contains(successeur) 
                	    && prochainSuccesseur == null) {
                    prochainSuccesseur = successeur;
                }
            }
            tacheActuelle = prochainSuccesseur;
        }
        return cheminCritique;
    }
    
    /**
     * Calcule la marge libre pour une tâche donnée.
     * @param tache La tâche pour laquelle calculer la marge libre.
     * @return La marge libre.
     */
    public int margeLibreTache(Tache tache) {
        int ti = auPlusTotTache(tache);
        int dij = tache.getDuree();
        List<Tache> successeurs = gestionnaire.trouverTachesSuccesseurs(tache);
        if (successeurs.isEmpty()) {
            return margeTotalTache(tache);
        }
        int margeLibre = Integer.MAX_VALUE;
        for (Tache successeur : successeurs) {
            int tj = auPlusTotTache(successeur);
            int margeAvecSuccesseur = tj - ti - dij;
            margeLibre = Math.min(margeLibre, margeAvecSuccesseur);
        }
        return Math.max(0, margeLibre);
    }

    /**
     * Calcule la marge totale pour une tâche donnée.
     * @param tache La tâche pour laquelle calculer la marge totale.
     * @return La marge totale.
     */
    public int margeTotalTache(Tache tache) {
        int auPlusTot = auPlusTotTache(tache);
        int margeTotale = Integer.MAX_VALUE;
        List<Tache> successeurs = gestionnaire.trouverTachesSuccesseurs(tache);
        if (successeurs.isEmpty()) {
            return auPlusTardTache(tache) - auPlusTot;
        }
        for (Tache successeur : successeurs) {
            int auPlusTardSuccesseur = auPlusTardTache(successeur);
            int margeAvecSuccesseur = auPlusTardSuccesseur - auPlusTot
            		                                       - tache.getDuree();
            margeTotale = Math.min(margeTotale, margeAvecSuccesseur);
        }

        return margeTotale;
    }

    /**
     * Convertit une durée en jours-homme.
     * @param duree La durée à convertir.
     * @return La durée en jours-homme.
     */
    public static double conversionJourHomme(int duree) {
        final int JOURS_PAR_SEMAINE = 5;
        double conversion = duree * JOURS_PAR_SEMAINE;
        return  conversion;
    }

    /**
     * Convertit une durée en mois-homme.
     * @param duree La durée à convertir.
     * @return La durée en mois-homme.
     */
    public static double conversionMoisHomme(int duree) {
        final int JOURS_PAR_MOIS = 20;
        return (double) duree / JOURS_PAR_MOIS;
    }

    /**
     * Affiche les résultats des calculs.
     * Uniquement utilisée pour le prototype
     */
    public void afficherResultats() {
        System.out.println("Résultats de l'ordonnancement des tâches :");
        for (Tache tache : gestionnaire.calculerOrdre()) {
            int auPlusTot = auPlusTotTache(tache);
            int auPlusTard = auPlusTardTache(tache);
            int margeLibre = margeLibreTache(tache);
            int margeTotale = margeTotalTache(tache);
            boolean estCritique = tacheCritique(tache);
            System.out.printf("Tâche %d (%s) :\n", tache.getId()
            		                             , tache.getNom());
            System.out.printf("  - Au plus tôt : %d\n", auPlusTot);
            System.out.printf("  - Au plus tard : %d\n", auPlusTard);
            System.out.printf("  - Marge libre : %d\n", margeLibre);
            System.out.printf("  - Marge totale : %d\n", margeTotale);
            System.out.printf("  - Critique : %s\n", estCritique ? "Oui":"Non");
        }
        System.out.println("\nChemin critique :");
        List<Tache> cheminCritique = cheminCritique();
        for (Tache tache : cheminCritique) {
            System.out.printf("Tâche %d (%s)\n", tache.getId(), tache.getNom());
        }
        int dateFinProjet = auPlusTotFinProjet();
        System.out.printf("\nDate au plus tôt pour la fin du projet : %d\n"
        		           , dateFinProjet);
    }

}