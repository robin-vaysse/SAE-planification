
package iut.info1.sae.planning.test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import iut.info1.sae.planning.Tache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestsTache {

    @BeforeEach
    void setUp() {
        Tache.getTachesStockees().clear();
    }

    @Test
    void testCreationTache() {
        Tache tache = new Tache(1, "Tache1", 5, List.of(0));

        assertEquals(1, tache.getId());
        assertEquals("Tache1", tache.getNom());
        assertEquals(5, tache.getDuree());
        assertEquals(List.of(0), tache.getPredecesseurs());
    }

    @Test
    void testIsValide() {
        Tache tache1 = new Tache(1, "Tache1", 5, List.of(0));
        tache1.ajouter();

        assertTrue(Tache.isValide("Tache2", 3, 2, List.of(1)));
        assertFalse(Tache.isValide("Tache1", 5, 1, List.of(0)));
        assertFalse(Tache.isValide("", 5, 3, List.of(0)));
        assertFalse(Tache.isValide("Tache3", -1, 4, List.of(0)));
    }

    @Test
    void testgetPredecesseurs() {
        Tache tache = new Tache(1, "Tache1", 5, List.of(0, 2));
        assertEquals(List.of(0, 2), tache.getPredecesseurs());
    }

    @Test
    void testToString() {
        Tache tache = new Tache(1, "Tache1", 5, List.of(0));
        String expected = "Identifiant : 1 Nom de la tâche : Tache1 Durée : 5 Prédécesseurs : [0]";
        assertEquals(expected, tache.toString());
    }

    @Test
    void testAjouter() {
        Tache tache = new Tache(3, "Tache1", 5, List.of(0));
        tache.ajouter();
        assertTrue(Tache.getTachesStockees().contains(tache));
    }

    @Test
    void testModifier() {
        Tache tache1 = new Tache(8, "Tache1", 5, List.of(0));
        tache1.ajouter();

        tache1.modifier(8, "TacheModifiee", 10, List.of(1, 2));

        assertEquals(8, tache1.getId());
        assertEquals("TacheModifiee", tache1.getNom());
        assertEquals(10, tache1.getDuree());
        assertEquals(List.of(1, 2), tache1.getPredecesseurs());
    }

    @Test
    void testRetirer() {
        Tache tache = new Tache(1, "Tache1", 5, List.of(2));
        tache.ajouter();

        tache.retirer();

        assertFalse(Tache.getTachesStockees().contains(tache));
    }

    @Test
    void testGetTachesStockees() {
        Tache tache1 = new Tache(1, "Tache1", 5, List.of(0));
        Tache tache2 = new Tache(2, "Tache2", 3, List.of(1));
        tache1.ajouter();
        tache2.ajouter();

        List<Tache> taches = Tache.getTachesStockees();
        assertEquals(2, taches.size());
        assertTrue(taches.contains(tache1));
        assertTrue(taches.contains(tache2));
    }
}
