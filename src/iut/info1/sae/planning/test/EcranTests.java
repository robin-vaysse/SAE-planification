/*
 * EcranTests.java                           06/06/2025
 * IUT de Rodez, Info 1 2024 - 2025 TP1, pas de copyright
 */
package iut.info1.sae.planning.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import iut.info1.sae.planning.Ecran;
import iut.info1.sae.planning.Gestionnaire;
import iut.info1.sae.planning.Tache;

/**
 * Cette classe a pour but de réaliser les tests JUnit de la classe Ecran afin
 * de savoir si les méthodes de cette classe marche
 * @author Helg.Florian/Liao.Mattieu/Petit.Valentin/Rebourg.Noé/Vaysse.Robin
 */
class EcranTests {

	/** Instance pour accéder au calcul de cette classe */
	private Ecran ecran;
	
	/** Instance pour accéder aux tâches et à leur ordonnancement */
	private Gestionnaire gestionnaire;
	
	/** durée maximum du projet attendu */
	private int dateAuPlusTotFinAttendue = 34;

	/** Fixture de test pour les tests unitaires.*/
	@BeforeEach
	void setUp() {
		
		Tache.getTachesStockees().clear();
	    Tache tache1 = new Tache(1, "Tâche 1", 1, Collections.emptyList());
	    Tache tache2 = new Tache(2, "Tâche 2", 4, List.of(1));
	    Tache tache3 = new Tache(3, "Tâche 3", 10, List.of(1));
	    Tache tache4 = new Tache(4, "Tâche 4", 6, List.of(2));
	    Tache tache5 = new Tache(5, "Tâche 5", 2, List.of(2));
	    Tache tache6 = new Tache(6, "Tâche 6", 11, List.of(2));
	    Tache tache7 = new Tache(7, "Tâche 7", 22, List.of(4, 11));
	    Tache tache8 = new Tache(8, "Tâche 8", 3, List.of(5));
	    Tache tache9 = new Tache(9, "Tâche 9", 17, List.of(3,6,8));
	    Tache tache10 = new Tache(10, "Tâche 10", 1, List.of(7, 9));
	    Tache tache11 = new Tache(11, "Tâche Fictive", 0, List.of(5));
	    

	    gestionnaire = new Gestionnaire(List.of(tache1, tache2, tache3, tache4,
	    		                                tache5, tache6, tache7, tache8,
	    		                                tache9, tache10, tache11));
	    ecran = new Ecran(gestionnaire, tache1);
	}

	
	/**
	 * Test method for {@link iut.info1sae.planning.Ecran#auPlusTotTache(Tache)}
	 */
	@Test
	void testAuPlusTotTache() {
	    assertEquals(0, ecran.auPlusTotTache(gestionnaire.trouverTacheParId(1)),
	    		      "La tâche 1 devrait commencer à 0.");
	    assertEquals(1, ecran.auPlusTotTache(gestionnaire.trouverTacheParId(2)),
	    		      "La tâche 2 devrait commencer à 1.");
	    assertEquals(1, ecran.auPlusTotTache(gestionnaire.trouverTacheParId(3)),
	    		      "La tâche 3 devrait commencer à 1.");
	    assertEquals(5, ecran.auPlusTotTache(gestionnaire.trouverTacheParId(4)), 
	    		      "La tâche 4 devrait commencer à 5.");
	    assertEquals(5,ecran.auPlusTotTache(gestionnaire.trouverTacheParId(5)), 
	    		      "La tâche 5 devrait commencer à 5.");
	    assertEquals(5, ecran.auPlusTotTache(gestionnaire.trouverTacheParId(6)),
	    		      "La tâche 6 devrait commencer à 5.");
	    assertEquals(11,ecran.auPlusTotTache(gestionnaire.trouverTacheParId(7)),
	    		      "La tâche 7 devrait commencer à 11.");
	    assertEquals(7,ecran.auPlusTotTache(gestionnaire.trouverTacheParId(8)),
  		      		  "La tâche 8 devrait commencer à 7.");
	    assertEquals(16,ecran.auPlusTotTache(gestionnaire.trouverTacheParId(9)),
  		              "La tâche 9 devrait commencer à 16.");
	    assertEquals(33,ecran.auPlusTotTache(gestionnaire.trouverTacheParId(10)),
  		              "La tâche 10 devrait commencer à 33.");
	    assertEquals(7,ecran.auPlusTotTache(gestionnaire.trouverTacheParId(11)),
	              "La tâche Fictive devrait commencer à 7.");
	}
	
	/**
	 * Test method for {@link iut.info1sae.planning.Ecran#auPlusTotFinProjet()}.
	 */
	@Test
	void testAuPlusTotFinProjet() {
	    int dateAuPlusTotFinCalculee = ecran.auPlusTotFinProjet();
	    assertEquals(dateAuPlusTotFinAttendue, dateAuPlusTotFinCalculee, 
	    		   "La date au plus tôt pour la fin du projet est incorrecte.");
	}

	/**
	 * Test method for{@link iut.info1sae.planning.Ecran#auPlusTardTache(Tache)}
	 */
	@Test
	void testAuPlusTardTache() {
	   
		assertEquals(0,ecran.auPlusTardTache(gestionnaire.trouverTacheParId(1)), 
	    		      "La tâche 1 devrait commencer au plus tard à 0.");
	    assertEquals(1,ecran.auPlusTardTache(gestionnaire.trouverTacheParId(2)), 
	    		      "La tâche 2 devrait commencer au plus tard à 1.");
	    assertEquals(1,ecran.auPlusTardTache(gestionnaire.trouverTacheParId(3)), 
	    		      "La tâche 3 devrait commencer au plus tard à 1.");
	    assertEquals(5,ecran.auPlusTardTache(gestionnaire.trouverTacheParId(4)), 
	    		      "La tâche 4 devrait commencer au plus tard à 5.");
	    assertEquals(5, ecran.auPlusTardTache(gestionnaire.trouverTacheParId(5)), 
	    		      "La tâche 5 devrait commencer au plus tard à 5.");
	    assertEquals(5,ecran.auPlusTardTache(gestionnaire.trouverTacheParId(6)), 
	    		      "La tâche 6 devrait commencer au plus tard à 5.");
	    assertEquals(11,ecran.auPlusTardTache(gestionnaire.trouverTacheParId(7)), 
	    		      "La tâche 7 devrait commencer au plus tard à 11.");
	   assertEquals(11,ecran.auPlusTardTache(gestionnaire.trouverTacheParId(8)), 
  		              "La tâche 8 devrait commencer au plus tard à 11.");
	    assertEquals(16,ecran.auPlusTardTache(gestionnaire.trouverTacheParId(9)), 
  		      "La tâche 9 devrait commencer au plus tard à 16.");
	    assertEquals(33,ecran.auPlusTardTache(gestionnaire.trouverTacheParId(10)), 
  		      "La tâche 10 devrait commencer au plus tard à 33.");
	    assertEquals(11,ecran.auPlusTardTache(gestionnaire.trouverTacheParId(11)), 
	  		      "La tâche 10 devrait commencer au plus tard à 11.");
	    
	}
	
	/**
	 * Test method for {@link iut.info1sae.planning.Ecran#tacheCritique(Tache)}.
	 */
	@Test
	void testTacheCritique() {
	    assertTrue(ecran.tacheCritique(gestionnaire.trouverTacheParId(1)), 
	    		    "La tâche 1 devrait être critique.");
	    assertTrue(ecran.tacheCritique(gestionnaire.trouverTacheParId(2)), 
	    		    "La tâche 2 devrait être critique.");
	    assertFalse(ecran.tacheCritique(gestionnaire.trouverTacheParId(3)), 
	    		    "La tâche 3 devrait être critique.");
	    assertTrue(ecran.tacheCritique(gestionnaire.trouverTacheParId(4)), 
	    		    "La tâche 4 devrait être critique.");
	    assertFalse(ecran.tacheCritique(gestionnaire.trouverTacheParId(5)), 
	    		    "La tâche 5 devrait être critique.");
	    assertTrue(ecran.tacheCritique(gestionnaire.trouverTacheParId(6)), 
	    		     "La tâche 6 devrait être critique.");
	    assertTrue(ecran.tacheCritique(gestionnaire.trouverTacheParId(7)), 
	    		     "La tâche 7 devrait être critique.");
	    assertFalse(ecran.tacheCritique(gestionnaire.trouverTacheParId(8)), 
   		     "La tâche 8 ne devrait pas être critique.");
	    assertTrue(ecran.tacheCritique(gestionnaire.trouverTacheParId(9)), 
   		     "La tâche 9 devrait être critique.");
	    assertTrue(ecran.tacheCritique(gestionnaire.trouverTacheParId(10)), 
   		     "La tâche 10 devrait être critique.");
	    assertFalse(ecran.tacheCritique(gestionnaire.trouverTacheParId(11)), 
   		     "La tâche fictive ne devrait pas être critique.");
	}
	
	/**
	 * Test method for {@link iut.info1sae.planning.Ecran#cheminCritique()}.
	 */
	@Test
	void testCheminCritique() {
	    List<Tache> cheminCritiqueAttendu = List.of(
	            gestionnaire.trouverTacheParId(1),
	            gestionnaire.trouverTacheParId(2),
	            gestionnaire.trouverTacheParId(4),
	            gestionnaire.trouverTacheParId(7),
	            gestionnaire.trouverTacheParId(10)
	        );
	        List<Tache> cheminCritiqueCalcule = ecran.cheminCritique();
	        assertEquals(cheminCritiqueAttendu, cheminCritiqueCalcule,
	        		     "Le chemin critique calculé est incorrect.");
	    }

	/**
	 * Test method for{@link iut.info1sae.planning.Ecran#margeLibreTache(Tache)}
	 */
	@Test
	void testMargeLibreTache() {
	    assertEquals(0,ecran.margeLibreTache(gestionnaire.trouverTacheParId(1)),
	            "La marge libre de la tâche 1 devrait être 0.");
	    assertEquals(0,ecran.margeLibreTache(gestionnaire.trouverTacheParId(2)),
	            "La marge libre de la tâche 2 devrait être 0.");
	    assertEquals(5,ecran.margeLibreTache(gestionnaire.trouverTacheParId(3)),
	            "La marge libre de la tâche 3 devrait être 6.");
	    assertEquals(0,ecran.margeLibreTache(gestionnaire.trouverTacheParId(4)),
	            "La marge libre de la tâche 4 devrait être 0.");
	    assertEquals(0,ecran.margeLibreTache(gestionnaire.trouverTacheParId(5)),
	            "La marge libre de la tâche 5 devrait être 0.");
	    assertEquals(0,ecran.margeLibreTache(gestionnaire.trouverTacheParId(6)),
	            "La marge libre de la tâche 6 devrait être 6.");
	    assertEquals(0,ecran.margeLibreTache(gestionnaire.trouverTacheParId(7)),
	            "La marge libre de la tâche 7 devrait être 0.");
	    assertEquals(6,ecran.margeLibreTache(gestionnaire.trouverTacheParId(8)),
	            "La marge libre de la tâche 8 devrait être 4.");
	    assertEquals(0,ecran.margeLibreTache(gestionnaire.trouverTacheParId(9)),
	            "La marge libre de la tâche 9 devrait être 0.");
	    assertEquals(0,ecran.margeLibreTache(gestionnaire.trouverTacheParId(10)),
	            "La marge libre de la tâche 10 devrait être 0.");
	    assertEquals(4,ecran.margeLibreTache(gestionnaire.trouverTacheParId(11)),
	            "La marge libre de la tâche 11 devrait être 4.");
	}

	/**
	 * Test method for{@link iut.info1sae.planning.Ecran#margeTotalTache(Tache)}
	 */
	@Test
	void testMargeTotalTache() {
	    assertEquals(0,ecran.margeTotalTache(gestionnaire.trouverTacheParId(1)),
	            "La marge totale de la tâche 1 devrait être 0.");
	    assertEquals(0,ecran.margeTotalTache(gestionnaire.trouverTacheParId(2)),
	            "La marge totale de la tâche 2 devrait être 0.");
	    assertEquals(5,ecran.margeTotalTache(gestionnaire.trouverTacheParId(3)),
	            "La marge totale de la tâche 3 devrait être 0.");
	    assertEquals(0,ecran.margeTotalTache(gestionnaire.trouverTacheParId(4)),
	            "La marge totale de la tâche 4 devrait être 0.");
	    assertEquals(4,ecran.margeTotalTache(gestionnaire.trouverTacheParId(5)),
	            "La marge totale de la tâche 5 devrait être 0.");
	    assertEquals(0,ecran.margeTotalTache(gestionnaire.trouverTacheParId(6)),
	            "La marge totale de la tâche 6 devrait être 0.");
	    assertEquals(0,ecran.margeTotalTache(gestionnaire.trouverTacheParId(7)),
	            "La marge totale de la tâche 7 devrait être 0.");
	    assertEquals(6,ecran.margeTotalTache(gestionnaire.trouverTacheParId(8)),
	            "La marge totale de la tâche 8 devrait être 0.");
	    assertEquals(0,ecran.margeTotalTache(gestionnaire.trouverTacheParId(9)),
	            "La marge totale de la tâche 9 devrait être 0.");
	    assertEquals(0,ecran.margeTotalTache(gestionnaire.trouverTacheParId(10)),
	            "La marge totale de la tâche 10 devrait être 0.");
	    assertEquals(4,ecran.margeTotalTache(gestionnaire.trouverTacheParId(11)),
	            "La marge totale de la tâche 11 devrait être 0.");
	}

	/**
	 * Test method for 
	 * {@link iut.info1sae.planning.Ecran#conversionJourHomme(double)}.
	 */
	@Test
	void testConversionJourHomme() {
       
        assertEquals(25.0, Ecran.conversionJourHomme(5), 0.01, 
            "La conversion de 5 jours en jours-homme est incorrecte.");
        assertEquals(50.0, Ecran.conversionJourHomme(10), 0.01, 
            "La conversion de 10 jours en jours-homme est incorrecte.");

        assertEquals(0.0, Ecran.conversionJourHomme(0), 0.01, 
            "La conversion de 0 jour en jours-homme est incorrecte.");

        assertEquals(500.0, Ecran.conversionJourHomme(100), 0.01, 
            "La conversion de 100 jours en jours-homme est incorrecte.");
    }

	/**
	 * Test method for 
	 * {@link iut.info1sae.planning.Ecran#conversionMoisHomme(double)}.
	 */
	@Test
    void testConversionMoisHomme() {
        assertEquals(1.25, Ecran.conversionMoisHomme(25), 0.01, 
            "La conversion de 25 jours en mois-homme est incorrecte.");
        assertEquals(2.5, Ecran.conversionMoisHomme(50), 0.01, 
            "La conversion de 50 jours en mois-homme est incorrecte.");

        assertEquals(0.0, Ecran.conversionMoisHomme(0), 0.01, 
            "La conversion de 0 jour en mois-homme est incorrecte.");

        assertEquals(50.0, Ecran.conversionMoisHomme(1000), 0.01, 
            "La conversion de 1000 jours en mois-homme est incorrecte.");
    }
}
