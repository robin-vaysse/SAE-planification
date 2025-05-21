package iut.info1.sae.planning;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
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
	    Tache tache = new Tache("Tache1", 5, 1, List.of(0));
	
	    assertEquals("Tache1", tache.getNom());
	    assertEquals(5, tache.getDuree());
	    assertEquals(1, tache.getId());
	    assertEquals(List.of(0), tache.getPredecesseur());
	}


    @Test
    void testIsValide() {
        Tache tache1 = new Tache("Tache1", 5, 1, List.of(0));
        tache1.ajouter();

        assertTrue(Tache.isValide("Tache2", 3, 2, List.of(1)));
        assertFalse(Tache.isValide("Tache1", 5, 1, List.of(0))); 
        assertFalse(Tache.isValide("", 5, 3, List.of(0))); 
        assertFalse(Tache.isValide("Tache3", -1, 4, List.of(0))); 
    }

    @Test
    void testGetPredecesseur() {
        Tache tache = new Tache("Tache1", 5, 1, List.of(0, 2));
        assertEquals(List.of(0, 2), tache.getPredecesseur());
    }

    @Test
    void testToString() {
        Tache tache = new Tache("Tache1", 5, 1, List.of(0));
        String expected = "Nom de la tache Tache1Durée : 5Identifient : 1Predecesseur : [0]";
        assertEquals(expected, tache.toString());
    }

    @Test
    void testAjouter() {
        Tache tache = new Tache("Tache1", 5, 3, List.of(0));
        tache.ajouter();
        assertTrue(Tache.getTachesStockees().contains(tache));
    }

    @Test
    void testModifier() {
        Tache tache1 = new Tache("Tache1", 5, 8, List.of(0));
        tache1.ajouter(); // Add the task to tachesStockees

        tache1.modifier("TacheModifiee", 5, 9, List.of(1, 2));

        assertEquals("TacheModifiee", tache1.getNom());
        assertEquals(5, tache1.getDuree());
        assertEquals(9, tache1.getId());
        assertEquals(List.of(1, 2), tache1.getPredecesseur());
    }

    @Test
    void testRetirer() {
        Tache tache = new Tache("Tache1", 5, 1, List.of(2));
        tache.ajouter();

        tache.retirer();

        assertFalse(Tache.getTachesStockees().contains(tache));
    }

    @Test
    void testGetTachesStockees() {
        Tache tache1 = new Tache("Tache1", 5, 1, List.of(0));
        Tache tache2 = new Tache("Tache2", 3, 2, List.of(1));
        tache1.ajouter();
        tache2.ajouter();

        List<Tache> taches = Tache.getTachesStockees();
        assertEquals(2, taches.size());
        assertTrue(taches.contains(tache1));
        assertTrue(taches.contains(tache2));
    }
}