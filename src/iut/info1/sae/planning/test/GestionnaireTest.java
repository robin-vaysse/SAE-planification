
package iut.info1.sae.planning.test;

import iut.info1.sae.planning.Gestionnaire;
import iut.info1.sae.planning.Tache;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GestionnaireTest {

    @Test
    void testConstructeurSansCycle() {
        Tache tache1 = new Tache(1, "Tâche 1", 3, Collections.emptyList());
        Tache tache2 = new Tache(2, "Tâche 2", 5, List.of(1));
        Gestionnaire gestionnaire = new Gestionnaire(List.of(tache1, tache2));
        assertNotNull(gestionnaire);
    }

    @Test
    void testConstructeurAvecCycle() {
        Tache tache1 = new Tache(1, "Tâche 1", 3, List.of(2));
        Tache tache2 = new Tache(2, "Tâche 2", 5, List.of(1));
        assertThrows(IllegalArgumentException.class, () -> new Gestionnaire(List.of(tache1, tache2)));
    }

    @Test
    void testAjouterTacheSansCycle() {
        Tache tache1 = new Tache(1, "Tâche 1", 3, Collections.emptyList());
        Gestionnaire gestionnaire = new Gestionnaire(List.of(tache1));

        Tache tache2 = new Tache(2, "Tâche 2", 5, List.of(1));
        assertDoesNotThrow(() -> gestionnaire.ajouterTache(tache2));
    }

    @Test
    void testAjouterTacheAvecCycle() {
        Tache tache1 = new Tache(1, "Tâche 1", 3, Collections.emptyList());
        Tache tache2 = new Tache(2, "Tâche 2", 5, List.of(1));
        Gestionnaire gestionnaire = new Gestionnaire(List.of(tache1, tache2));

        Tache tache3 = new Tache(3, "Tâche 3", 2, List.of(2, 3)); // Dépendance circulaire
        assertThrows(IllegalArgumentException.class, () -> gestionnaire.ajouterTache(tache3));
    }

    @Test
    void testAfficherOrdreEtDureeMax() {
        Tache tache1 = new Tache(1, "Tâche 1", 3, Collections.emptyList());
        Tache tache2 = new Tache(2, "Tâche 2", 5, List.of(1));
        Tache tache3 = new Tache(3, "Tâche 3", 2, List.of(2));
        Gestionnaire gestionnaire = new Gestionnaire(List.of(tache1, tache2, tache3));

        // Vérifier l'ordre des tâches
        List<Tache> ordre = gestionnaire.calculerOrdreTopologique();
        assertEquals(ordre.get(0).getId(), 1);
        assertEquals(ordre.get(1).getId(), 2);
        assertEquals(ordre.get(2).getId(), 3);

        // Vérifier la durée maximale
        int dureeMax = gestionnaire.calculerDureeMax(ordre);
        assertEquals(10, dureeMax); // 3 (Tâche 1) + 5 (Tâche 2) + 2 (Tâche 3)
    }

    @Test
    void testGrandNombreDeTachesAvecPredecesseurs() {
        Tache tache1 = new Tache(1, "Tâche 1", 2, Collections.emptyList());
        Tache tache2 = new Tache(2, "Tâche 2", 4, List.of(1));
        Tache tache3 = new Tache(3, "Tâche 3", 3, List.of(1));
        Tache tache4 = new Tache(4, "Tâche 4", 5, List.of(2, 3));
        Tache tache5 = new Tache(5, "Tâche 5", 20, Collections.emptyList());
        Tache tache6 = new Tache(6, "Tâche 6", 6, List.of(4, 5));

        Gestionnaire gestionnaire = new Gestionnaire(List.of(tache1, tache2, tache3, tache4, tache5, tache6));

        // Vérifier l'ordre des tâches
        List<Tache> ordre = gestionnaire.calculerOrdreTopologique();
        assertTrue(ordre.indexOf(tache1) < ordre.indexOf(tache2));
        assertTrue(ordre.indexOf(tache1) < ordre.indexOf(tache3));
        assertTrue(ordre.indexOf(tache2) < ordre.indexOf(tache4));
        assertTrue(ordre.indexOf(tache3) < ordre.indexOf(tache4));
        assertTrue(ordre.indexOf(tache4) < ordre.indexOf(tache6));
        assertTrue(ordre.indexOf(tache5) < ordre.indexOf(tache6));

        // Vérifier la durée maximale
        int dureeMax = gestionnaire.calculerDureeMax(ordre);
        assertEquals(26, dureeMax); // Chemin le plus long : Tâche 1 -> Tâche 2 -> Tâche 4 -> Tâche 6
    }
}
