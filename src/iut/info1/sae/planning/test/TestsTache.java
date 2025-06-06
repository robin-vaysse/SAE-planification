/*
 * TestsTache.java                           06/06/2025
 * IUT de Rodez, Info 1 2024 - 2025 TP1, pas de copyright
 */
package iut.info1.sae.planning.test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import iut.info1.sae.planning.Tache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Cette classe a pour but de réaliser les tests JUnit de la classe Tache
 *  afin de savoir si les méthodes de cette classe marche
 * @author Helg.Florian/Liao.Mattieu/Petit.Valentin/Rebourg.Noé/Vaysse.Robin
 */
class TestsTache {

	/** Initialisation des tâches pour les tests unitaires. */
    private Tache tache1;
    private Tache tache2;
    private Tache tache3;

    /** Fixture de test pour les tests unitaires.*/
    @BeforeEach
    void setUp() {
        Tache.getTachesStockees().clear();
        tache1 = new Tache(1, "Tache1", 5, List.of(0));
        tache2 = new Tache(2, "Tache2", 3, List.of(1));
        tache3 = new Tache(3, "Tache3", 4, List.of(1, 2));
        tache1.ajouter();
        tache2.ajouter();
        tache3.ajouter();
    }

    /**
	 * Test method for 
	 *{@link iut.info1sae.planning.Tache#Tache(int, String, int, List<Integer>)}
	 */
    @Test
    void testCreationTache() {
        assertEquals(1, tache1.getId());
        assertEquals("Tache1", tache1.getNom());
        assertEquals(5, tache1.getDuree());
        assertEquals(List.of(0), tache1.getPredecesseurs());
    }

    /**
	 * Test method for 
	 * {@link iut.info1sae.planning.Tache#isValide(int, String, int, 
	 *                                             List<Integer>)}
	 */
    @Test
    void testIsValide() {
        assertTrue(Tache.isValide(4, "Tache4", 3, List.of(3)));
        assertFalse(Tache.isValide(1, "Tache1", 5, List.of(0)));
        assertFalse(Tache.isValide(5, "", 5, List.of(0)));
        assertFalse(Tache.isValide(6, "Tache5", -1, List.of(0)));
    }

    /**
	 * Test method for {@link iut.info1sae.planning.Tache#getPredecesseur()}
	 */
    @Test
    void testGetPredecesseurs() {
        assertEquals(List.of(0), tache1.getPredecesseurs());
        assertEquals(List.of(1), tache2.getPredecesseurs());
        assertEquals(List.of(1, 2), tache3.getPredecesseurs());
    }

    /**
	 * Test method for {@link iut.info1sae.planning.Tache#toString()}
	 */
    @Test
    void testToString() {
        String expected = "Identifiant : 1 Nom de la tâche : Tache1 Durée :"
        		         + " 5 Prédécesseurs : [0]";
        assertEquals(expected, tache1.toString());
    }

    /**
	 * Test method for {@link iut.info1sae.planning.Tache#ajouter()}
	 */
    @Test
    void testAjouter() {
        assertTrue(Tache.getTachesStockees().contains(tache1));
        assertTrue(Tache.getTachesStockees().contains(tache2));
        assertTrue(Tache.getTachesStockees().contains(tache3));
    }

    /**
	 * Test method for 
	 * {@link iut.info1sae.planning.Tache#modifier(int, String, int,
	 *                                              List<Integer>)}
	 */
    @Test
    void testModifier() {
        tache1.modifier(1, "TacheModifiee", 10, List.of(2, 3));
        assertEquals(1, tache1.getId());
        assertEquals("TacheModifiee", tache1.getNom());
        assertEquals(10, tache1.getDuree());
        assertEquals(List.of(2, 3), tache1.getPredecesseurs());
    }

    /**
	 * Test method for {@link iut.info1sae.planning.Tache#retirer()}
	 */
    @Test
    void testRetirer() {
        tache1.retirer();
        assertFalse(Tache.getTachesStockees().contains(tache1));
    }

    /**
	 * Test method for {@link iut.info1sae.planning.Tache#getTachesStockees()}
	 */
    @Test
    void testGetTachesStockees() {
        List<Tache> taches = Tache.getTachesStockees();
        assertEquals(3, taches.size());
        assertTrue(taches.contains(tache1));
        assertTrue(taches.contains(tache2));
        assertTrue(taches.contains(tache3));
    }
}