package iut.info1.sae.planning.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import iut.info1.sae.planning.Ecran;
import iut.info1.sae.planning.Gestionnaire;
import iut.info1.sae.planning.Tache;


class EcranTests {

	private Ecran ecran;
	private Gestionnaire gestionnaire;
	private int dateAuPlusTotFinAttendue = 25;

	@BeforeEach
	void setUp() {
		Tache.getTachesStockees().clear();
	    Tache tache1 = new Tache(1, "Tâche 1", 3, Collections.emptyList());
	    Tache tache2 = new Tache(2, "Tâche 2", 5, List.of(1));
	    Tache tache3 = new Tache(3, "Tâche 3", 2, List.of(1));
	    Tache tache4 = new Tache(4, "Tâche 4", 4, List.of(2, 3));
	    Tache tache5 = new Tache(5, "Tâche 5", 6, List.of(4));
	    Tache tache6 = new Tache(6, "Tâche 6", 3, List.of(2));
	    Tache tache7 = new Tache(7, "Tâche 7", 7, List.of(5, 6));

	    // Initialisation du gestionnaire et de l'écran
	    gestionnaire = new Gestionnaire(List.of(tache1, tache2, tache3, tache4, tache5, tache6, tache7));
	    ecran = new Ecran(gestionnaire, tache1);
	}


	@Test
	void testAuPlusTotTache() {
	    // Vérification des dates au plus tôt
	    assertEquals(0, ecran.auPlusTotTache(gestionnaire.trouverTacheParId(1)), "La tâche 1 devrait commencer à 0.");
	    assertEquals(3, ecran.auPlusTotTache(gestionnaire.trouverTacheParId(2)), "La tâche 2 devrait commencer à 3.");
	    assertEquals(3, ecran.auPlusTotTache(gestionnaire.trouverTacheParId(3)), "La tâche 3 devrait commencer à 3.");
	    assertEquals(8, ecran.auPlusTotTache(gestionnaire.trouverTacheParId(4)), "La tâche 4 devrait commencer à 8.");
	    assertEquals(12, ecran.auPlusTotTache(gestionnaire.trouverTacheParId(5)), "La tâche 5 devrait commencer à 12.");
	    assertEquals(8, ecran.auPlusTotTache(gestionnaire.trouverTacheParId(6)), "La tâche 6 devrait commencer à 8.");
	    assertEquals(18, ecran.auPlusTotTache(gestionnaire.trouverTacheParId(7)), "La tâche 7 devrait commencer à 18.");
	}
	@Test
	void testAuPlusTotFinProjet() {
	    // La date au plus tôt pour la fin du projet est déterminée par la tâche 7 (18 + 7 = 25)
	

	    // Calcul de la date au plus tôt pour la fin du projet
	    int dateAuPlusTotFinCalculee = ecran.auPlusTotFinProjet();

	    // Vérification
	    assertEquals(dateAuPlusTotFinAttendue, dateAuPlusTotFinCalculee, "La date au plus tôt pour la fin du projet est incorrecte.");
	}

	@Test
	void testAuPlusTardTache() {
	   
	    assertEquals(0, ecran.auPlusTardTache(gestionnaire.trouverTacheParId(1)), "La tâche 1 devrait commencer au plus tard à 0.");
	    assertEquals(3, ecran.auPlusTardTache(gestionnaire.trouverTacheParId(2)), "La tâche 2 devrait commencer au plus tard à 3.");
	    assertEquals(3, ecran.auPlusTardTache(gestionnaire.trouverTacheParId(3)), "La tâche 3 devrait commencer au plus tard à 3.");
	    assertEquals(7, ecran.auPlusTardTache(gestionnaire.trouverTacheParId(4)), "La tâche 4 devrait commencer au plus tard à 7.");
	    assertEquals(13, ecran.auPlusTardTache(gestionnaire.trouverTacheParId(5)), "La tâche 5 devrait commencer au plus tard à 13.");
	    assertEquals(10, ecran.auPlusTardTache(gestionnaire.trouverTacheParId(6)), "La tâche 6 devrait commencer au plus tard à 10.");
	    assertEquals(20, ecran.auPlusTardTache(gestionnaire.trouverTacheParId(7)), "La tâche 7 devrait commencer au plus tard à 20.");
	}
	@Test
	void testTacheCritique() {
	    assertTrue(ecran.tacheCritique(gestionnaire.trouverTacheParId(1)), "La tâche 1 devrait être critique.");
	    assertTrue(ecran.tacheCritique(gestionnaire.trouverTacheParId(2)), "La tâche 2 devrait être critique.");
	    assertFalse(ecran.tacheCritique(gestionnaire.trouverTacheParId(3)), "La tâche 3 ne devrait pas être critique.");
	    assertTrue(ecran.tacheCritique(gestionnaire.trouverTacheParId(4)), "La tâche 4 devrait être critique.");
	    assertTrue(ecran.tacheCritique(gestionnaire.trouverTacheParId(5)), "La tâche 5 devrait être critique.");
	    assertFalse(ecran.tacheCritique(gestionnaire.trouverTacheParId(6)), "La tâche 6 ne devrait pas être critique.");
	    assertTrue(ecran.tacheCritique(gestionnaire.trouverTacheParId(7)), "La tâche 7 devrait être critique.");
	}

	@Test
	void testCheminCritique() {
	    List<Tache> cheminCritiqueAttendu = List.of(
	            gestionnaire.trouverTacheParId(1),
	            gestionnaire.trouverTacheParId(2),
	            gestionnaire.trouverTacheParId(4),
	            gestionnaire.trouverTacheParId(5),
	            gestionnaire.trouverTacheParId(7)
	        );

	        // Calcul du chemin critique
	        List<Tache> cheminCritiqueCalcule = ecran.cheminCritique();

	        // Vérification
	        assertEquals(cheminCritiqueAttendu, cheminCritiqueCalcule, "Le chemin critique calculé est incorrect.");
	    }

	@Test
	void testMargeLibreTache() {
		fail("Not yet implemented");
	}

	@Test
	void testMargeTotalTache() {
		fail("Not yet implemented");
	}

	@Test
	void testConversionJourHomme() {
		fail("Not yet implemented");
	}

	@Test
	void testConversionMoisHomme() {
		fail("Not yet implemented");
	}

	@Test
	void testAfficherResultats() {
		fail("Not yet implemented");
	}

}
