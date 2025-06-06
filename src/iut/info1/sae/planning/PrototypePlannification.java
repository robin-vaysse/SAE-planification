package iut.info1.sae.planning;

import java.util.ArrayList;
import java.util.List;

public class PrototypePlannification {

    public static void main(String[] args) {
        List<Tache> listeTaches = new ArrayList<>();


		listeTaches.add(new Tache(1, "Analyse des besoins", 5, List.of()));
		listeTaches.add(new Tache(2, "Conception de l'architecture", 3, List.of(1)));
		listeTaches.add(new Tache(3, "Développement du module A", 4, List.of(1)));
		listeTaches.add(new Tache(4, "Tests unitaires module A", 2, List.of(3)));
		listeTaches.add(new Tache(5, "Développement du module B", 6, List.of(3, 4)));
		listeTaches.add(new Tache(6, "Tests unitaires module B", 3, List.of(4)));
		listeTaches.add(new Tache(7, "Intégration des modules", 7, List.of(5)));
		listeTaches.add(new Tache(8, "Validation fonctionnelle", 2, List.of(6, 5)));
		listeTaches.add(new Tache(9, "Rédaction de la documentation", 4, List.of(7)));
		listeTaches.add(new Tache(10, "Revue de la documentation", 5, List.of(8)));
		listeTaches.add(new Tache(11, "Tests d'intégration", 3, List.of(10, 9)));
		listeTaches.add(new Tache(12, "Déploiement en préproduction", 6, List.of(11)));
		listeTaches.add(new Tache(13, "Validation en préproduction", 4, List.of(11)));
		listeTaches.add(new Tache(14, "Déploiement en production", 2, List.of(12)));
		listeTaches.add(new Tache(15, "Formation des utilisateurs", 5, List.of(13)));
		listeTaches.add(new Tache(16, "Support post-déploiement", 3, List.of(15, 14)));
		listeTaches.add(new Tache(17, "Clôture du projet", 7, List.of(16)));
		listeTaches.add(new Tache(18, "Audit de fin de projet", 2, List.of(5, 8)));
		listeTaches.add(new Tache(19, "Rapport final", 4, List.of(17)));
		listeTaches.add(new Tache(20, "Réunion de bilan", 5, List.of(8, 5)));


        System.out.println("Tâches créées : " + listeTaches.size());

        Gestionnaire gestionnaire = new Gestionnaire(listeTaches);
        System.out.println("Gestionnaire créé.");
        System.out.println("Ordonnonancement des tâches : \n" + gestionnaire.afficherOrdonnancement());
        Ecran ecran = new Ecran(gestionnaire, null);

        ecran.afficherResultats();
    }
}