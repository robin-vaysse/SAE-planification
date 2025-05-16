package iut.info1.sae.planning;

import iut.info1.sae.planning.Tache;

class TacheTests {

    public static void main(String[] args) {
        testAjouter();
        testModifier();
        testRetirer();
    }

    /**
     * Test de la méthode ajouter()
     */
    public static void testAjouter() {
        try {
            Tache tache = new Tache();
            tache.creationTache("Tache Ajouter", 100, 2, 0);
            tache.ajouter();

            assert Tache.getTachesStockees().get(0) == tache;

            System.out.println("testAjouter réussi.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    /**
     * Test de la méthode modifier()
     */
    public static void testModifier() {
        try {
            Tache tache = new Tache();
            tache.creationTache("Tache Modifier", 10, 3, 0);
            tache.ajouter();

            tache.modifier("Tache Modifiée", 15, 4, 1);

            assert tache.getNom().equals("Tache Modifiée");
            assert tache.getDuree() == 15;
            assert tache.getId() == 4;
            assert tache.getPredecesseur() == 1;

            System.out.println("testModifier réussi.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    /**
     * Test de la méthode retirer()
     */
    public static void testRetirer() {
        try {
            Tache tache = new Tache();
            tache.creationTache("Tacersd", 10, 5, 0);
            tache.ajouter();

            tache.retirer();

            assert Tache.getTachesStockees().isEmpty();

            System.out.println("testRetirer réussi.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}