package iut.info1.sae.planning.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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

	    gestionnaire = new Gestionnaire(List.of(tache1, tache2, tache3, tache4,
	    		                                tache5, tache6, tache7));
	    ecran = new Ecran(gestionnaire, tache1);
	}


	@Test
	void testAuPlusTotTache() {
	    // Vérification des dates au plus tôt
	    assertEquals(0, ecran.auPlusTotTache(gestionnaire.trouverTacheParId(1)),
	    		      "La tâche 1 devrait commencer à 0.");
	    assertEquals(3, ecran.auPlusTotTache(gestionnaire.trouverTacheParId(2)),
	    		      "La tâche 2 devrait commencer à 3.");
	    assertEquals(3, ecran.auPlusTotTache(gestionnaire.trouverTacheParId(3)),
	    		      "La tâche 3 devrait commencer à 3.");
	    assertEquals(8, ecran.auPlusTotTache(gestionnaire.trouverTacheParId(4)), 
	    		      "La tâche 4 devrait commencer à 8.");
	    assertEquals(12,ecran.auPlusTotTache(gestionnaire.trouverTacheParId(5)), 
	    		      "La tâche 5 devrait commencer à 12.");
	    assertEquals(8, ecran.auPlusTotTache(gestionnaire.trouverTacheParId(6)),
	    		      "La tâche 6 devrait commencer à 8.");
	    assertEquals(18,ecran.auPlusTotTache(gestionnaire.trouverTacheParId(7)),
	    		      "La tâche 7 devrait commencer à 18.");
	}
	@Test
	void testAuPlusTotFinProjet() {
	    int dateAuPlusTotFinCalculee = ecran.auPlusTotFinProjet();
	    assertEquals(dateAuPlusTotFinAttendue, dateAuPlusTotFinCalculee, 
	    		   "La date au plus tôt pour la fin du projet est incorrecte.");
	}

	@Test
	void testAuPlusTardTache() {
	   
	    assertEquals(0,ecran.auPlusTardTache(gestionnaire.trouverTacheParId(1)), 
	    		      "La tâche 1 devrait commencer au plus tard à 0.");
	    assertEquals(3,ecran.auPlusTardTache(gestionnaire.trouverTacheParId(2)), 
	    		      "La tâche 2 devrait commencer au plus tard à 3.");
	    assertEquals(3,ecran.auPlusTardTache(gestionnaire.trouverTacheParId(3)), 
	    		      "La tâche 3 devrait commencer au plus tard à 3.");
	    assertEquals(7,ecran.auPlusTardTache(gestionnaire.trouverTacheParId(4)), 
	    		      "La tâche 4 devrait commencer au plus tard à 7.");
	    assertEquals(13, ecran.
	    		auPlusTardTache(gestionnaire.trouverTacheParId(5)), 
	    		      "La tâche 5 devrait commencer au plus tard à 13.");
	    assertEquals(10,ecran.
	    		      auPlusTardTache(gestionnaire.trouverTacheParId(6)), 
	    		      "La tâche 6 devrait commencer au plus tard à 10.");
	    assertEquals(20,ecran.
	    		      auPlusTardTache(gestionnaire.trouverTacheParId(7)), 
	    		      "La tâche 7 devrait commencer au plus tard à 20.");
	}
	@Test
	void testTacheCritique() {
	    assertTrue(ecran.tacheCritique(gestionnaire.trouverTacheParId(1)), 
	    		    "La tâche 1 devrait être critique.");
	    assertTrue(ecran.tacheCritique(gestionnaire.trouverTacheParId(2)), 
	    		    "La tâche 2 devrait être critique.");
	    assertFalse(ecran.tacheCritique(gestionnaire.trouverTacheParId(3)), 
	    		    "La tâche 3 ne devrait pas être critique.");
	    assertTrue(ecran.tacheCritique(gestionnaire.trouverTacheParId(4)), 
	    		    "La tâche 4 devrait être critique.");
	    assertTrue(ecran.tacheCritique(gestionnaire.trouverTacheParId(5)), 
	    		    "La tâche 5 devrait être critique.");
	    assertFalse(ecran.tacheCritique(gestionnaire.trouverTacheParId(6)), 
	    		     "La tâche 6 ne devrait pas être critique.");
	    assertTrue(ecran.tacheCritique(gestionnaire.trouverTacheParId(7)), 
	    		     "La tâche 7 devrait être critique.");
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
	        List<Tache> cheminCritiqueCalcule = ecran.cheminCritique();
	        assertEquals(cheminCritiqueAttendu, cheminCritiqueCalcule,
	        		     "Le chemin critique calculé est incorrect.");
	    }

	@Test
	void testMargeLibreTache() {
	    assertEquals(0,ecran.margeLibreTache(gestionnaire.trouverTacheParId(1)),
	            "La marge libre de la tâche 1 devrait être 0.");
	    assertEquals(0,ecran.margeLibreTache(gestionnaire.trouverTacheParId(2)),
	            "La marge libre de la tâche 2 devrait être 0.");
	    assertEquals(0,ecran.margeLibreTache(gestionnaire.trouverTacheParId(3)),
	            "La marge libre de la tâche 3 devrait être 0.");
	    assertEquals(0,ecran.margeLibreTache(gestionnaire.trouverTacheParId(4)),
	            "La marge libre de la tâche 4 devrait être 0.");
	    assertEquals(0,ecran.margeLibreTache(gestionnaire.trouverTacheParId(5)),
	            "La marge libre de la tâche 5 devrait être 0.");
	    assertEquals(0,ecran.margeLibreTache(gestionnaire.trouverTacheParId(6)),
	            "La marge libre de la tâche 6 devrait être 0.");
	    assertEquals(0,ecran.margeLibreTache(gestionnaire.trouverTacheParId(7)),
	            "La marge libre de la tâche 7 devrait être 0.");
	}

	@Test
	void testMargeTotalTache() {
	    assertEquals(0,ecran.margeTotalTache(gestionnaire.trouverTacheParId(1)),
	            "La marge totale de la tâche 1 devrait être 0.");
	    assertEquals(0,ecran.margeTotalTache(gestionnaire.trouverTacheParId(2)),
	            "La marge totale de la tâche 2 devrait être 0.");
	    assertEquals(0,ecran.margeTotalTache(gestionnaire.trouverTacheParId(3)),
	            "La marge totale de la tâche 3 devrait être 0.");
	    assertEquals(0,ecran.margeTotalTache(gestionnaire.trouverTacheParId(4)),
	            "La marge totale de la tâche 4 devrait être 0.");
	    assertEquals(0,ecran.margeTotalTache(gestionnaire.trouverTacheParId(5)),
	            "La marge totale de la tâche 5 devrait être 0.");
	    assertEquals(0,ecran.margeTotalTache(gestionnaire.trouverTacheParId(6)),
	            "La marge totale de la tâche 6 devrait être 0.");
	    assertEquals(0,ecran.margeTotalTache(gestionnaire.trouverTacheParId(7)),
	            "La marge totale de la tâche 7 devrait être 0.");
	}

	@Test
	void testConversionJourHomme() {
	    assertEquals(2.0, Ecran.conversionJourHomme(16), 0.01,
	    		   "La conversion de 16 heures en jours-homme est incorrecte.");
	    assertEquals(1.0, Ecran.conversionJourHomme(8), 0.01,
	    		   "La conversion de 8 heures en jours-homme est incorrecte.");
	    assertEquals(0.0, Ecran.conversionJourHomme(0), 0.01, 
	    		    "La conversion de 0 heure en jours-homme est incorrecte.");
	}

	@Test
	void testConversionMoisHomme() {
	    assertEquals(1, ecran.conversionMoisHomme(160),
	    		   "La conversion de 160 heures en mois-homme est incorrecte.");
	    assertEquals(0, ecran.conversionMoisHomme(80), 
	    		    "La conversion de 80 heures en mois-homme est incorrecte.");
	    assertEquals(0, ecran.conversionMoisHomme(0),
	    		    "La conversion de 0 heure en mois-homme est incorrecte.");
	}
	
	@Test
	void testAfficherResultats() {
	    ByteArrayOutputStream sortieCapturee = new ByteArrayOutputStream();
	    PrintStream sortieOriginale = System.out;
	    System.setOut(new PrintStream(sortieCapturee));

	    try {
	        ecran.afficherResultats();

	        String resultatAttendu = """
	            Résultats de l'ordonnancement des tâches :
	            Tâche 1 (Tâche 1) :
	              - Au plus tôt : 0
	              - Au plus tard : 0
	              - Marge libre : 0
	              - Marge totale : 0
	              - Critique : Oui
	            Tâche 2 (Tâche 2) :
	              - Au plus tôt : 3
	              - Au plus tard : 3
	              - Marge libre : 0
	              - Marge totale : 0
	              - Critique : Oui
	            Tâche 3 (Tâche 3) :
	              - Au plus tôt : 3
	              - Au plus tard : 3
	              - Marge libre : 0
	              - Marge totale : 0
	              - Critique : Non
	            Tâche 4 (Tâche 4) :
	              - Au plus tôt : 8
	              - Au plus tard : 7
	              - Marge libre : 0
	              - Marge totale : 0
	              - Critique : Oui
	            Tâche 5 (Tâche 5) :
	              - Au plus tôt : 12
	              - Au plus tard : 13
	              - Marge libre : 0
	              - Marge totale : 0
	              - Critique : Oui
	            Tâche 6 (Tâche 6) :
	              - Au plus tôt : 8
	              - Au plus tard : 10
	              - Marge libre : 0
	              - Marge totale : 0
	              - Critique : Non
	            Tâche 7 (Tâche 7) :
	              - Au plus tôt : 18
	              - Au plus tard : 20
	              - Marge libre : 0
	              - Marge totale : 0
	              - Critique : Oui
	            """;

	        // Vérifier que la sortie correspond au résultat attendu
	        assertEquals(resultatAttendu.trim(),
	        		     sortieCapturee.toString().trim(),
	        		     "L'affichage des résultats est incorrect.");
	    } finally {
	        // Restaurer la sortie standard
	        System.setOut(sortieOriginale);
	    }
	}
}
