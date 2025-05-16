package iut.info1.sae.planning;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class TestsTache {

    @Test
    void testCreationTache() {
        Tache tache = new Tache();
        tache.Tache("Tache1", 5, 1, List.of(0));
        assertEquals("Tache1", tache.getNom());
        assertEquals(5, tache.getDuree());
        assertEquals(1, tache.getId());
        assertEquals(List.of(0), tache.getPredecesseur());
    }

    @Test
    void testIsValide() {
        Tache tache1 = new Tache();
        tache1.Tache("Tache1", 5, 1, List.of(0));
        tache1.ajouter();

        assertTrue(Tache.isValide("Tache2", 3, 2, List.of(1))); // Valide
        assertFalse(Tache.isValide("Tache1", 5, 1, List.of(0))); // Identifiant déjà utilisé
        assertFalse(Tache.isValide("", 5, 3, List.of(0))); // Nom vide
        assertFalse(Tache.isValide("Tache3", -1, 4, List.of(0))); // Durée invalide
    }

    @Test
    void testGetPredecesseur() {
        Tache tache = new Tache();
        tache.Tache("Tache1", 5, 1, List.of(0, 2));
        assertEquals(List.of(0, 2), tache.getPredecesseur());
    }

    @Test
    void testToString() {
        Tache tache = new Tache();
        tache.Tache("Tache1", 5, 1, List.of(0));
        String expected = "Tache15" + "10";
        assertEquals(expected, tache.toString());
    }

    @Test
    void testAjouter() {
        Tache tache = new Tache();
        tache.Tache("Tache1", 5, 3, List.of(0));
        tache.ajouter();
        assertTrue(Tache.getTachesStockees().contains(tache));
    }

    @Test
    void testModifier() {
        Tache tache = new Tache();
        tache.Tache("Tache1", 5, 8, List.of(0));
        tache.ajouter();

        tache.modifier("TacheModifiee", 10, 8, List.of(1, 2));

        assertEquals("TacheModifiee", tache.getNom());
        assertEquals(10, tache.getDuree());
        assertEquals(8, tache.getId());
        assertEquals(List.of(1, 2), tache.getPredecesseur());
    }

    @Test
    void testRetirer() {
        Tache tache = new Tache();
        tache.Tache("Tache1", 5, 1, List.of(2));
        tache.ajouter();

        tache.retirer();

        assertFalse(Tache.getTachesStockees().contains(tache));
    }

    @Test
    void testGetTachesStockees() {
        Tache tache1 = new Tache();
        tache1.Tache("Tache1", 5, 1, List.of(0));
        Tache tache2 = new Tache();
        tache2.Tache("Tache2", 3, 2, List.of(1));
        tache1.ajouter();
        tache2.ajouter();

        List<Tache> taches = Tache.getTachesStockees();
        assertEquals(2, taches.size());
        assertTrue(taches.contains(tache1));
        assertTrue(taches.contains(tache2));
    }
}