package iut.info1.sae.planning;


import java.util.ArrayList;
import java.util.List;

public class PrototypePlannificationNok {

    public static void main(String[] args) {
    	/*
        List<Tache> listeTaches = new ArrayList<>();

        listeTaches.add(new Tache(1, "Tâche 1", 5, List.of()));
        listeTaches.add(new Tache(2, "Tâche 2", 3, List.of(99))); 

        Gestionnaire gestionnaire = new Gestionnaire(listeTaches);
        System.out.println(gestionnaire.afficherOrdonnancement());
   */

		
		/*
        List<Tache> listeTaches = new ArrayList<>();

        listeTaches.add(new Tache(1, "Tâche 1", 5, List.of(3)));
        listeTaches.add(new Tache(2, "Tâche 2", 3, List.of(1)));
        listeTaches.add(new Tache(3, "Tâche 3", 4, List.of(2))); // Cycle : 1 -> 3 -> 2 -> 1

        Gestionnaire gestionnaire = new Gestionnaire(listeTaches);
        System.out.println(gestionnaire.afficherOrdonnancement());
		
		 */
        
        /*
        List<Tache> listeTaches = new ArrayList<>();

        listeTaches.add(new Tache(1, "Tâche 1", 5, List.of()));
        listeTaches.add(new Tache(1, "Tâche 2", 3, List.of())); // ID dupliqué

        Gestionnaire gestionnaire = new Gestionnaire(listeTaches);
        System.out.println(gestionnaire.afficherOrdonnancement());
   
         */
    	
    	/*
    	
        List<Tache> listeTaches = new ArrayList<>();

        listeTaches.add(new Tache(1, "Tâche 1", 0, List.of())); // Durée invalide (inférieure à 1)

        Gestionnaire gestionnaire = new Gestionnaire(listeTaches);
        System.out.println(gestionnaire.afficherOrdonnancement());
    	 */

        
        /*
        List<Tache> listeTaches = new ArrayList<>();

        listeTaches.add(new Tache(1, "", 5, List.of())); // Nom vide

        Gestionnaire gestionnaire = new Gestionnaire(listeTaches);
        System.out.println(gestionnaire.afficherOrdonnancement());
        */
        
    }
}