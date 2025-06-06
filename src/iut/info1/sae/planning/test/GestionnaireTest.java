/*
 * GestionnaireTest.java                           06/06/2025
 * IUT de Rodez, Info 1 2024 - 2025 TP1, pas de copyright
 */
package iut.info1.sae.planning.test;

import iut.info1.sae.planning.Gestionnaire;
import iut.info1.sae.planning.Tache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Cette classe a pour but de réaliser les tests JUnit de la classe Gestionnaire
 *  afin de savoir si les méthodes de cette classe marche
 * @author Helg.Florian/Liao.Mattieu/Petit.Valentin/Rebourg.Noé/Vaysse.Robin
 */
class GestionnaireTest {

	/** Initialisation des tâches pour les tests unitaires. */
    private Tache tache1;
    private Tache tache2;
    private Tache tache3;
    private Tache tache4;
    private Tache tache5;
    private Tache tache6;
    private Tache tache7;
    private Tache tache8;
    private Tache tache9;
    private Tache tache10;

    /** Fixture de test pour les tests unitaires.*/
	@BeforeEach
	void setUp() {
	    Tache.getTachesStockees().clear();
	    tache1 = new Tache(1, "Tâche 1", 3, Collections.emptyList());
	    tache2 = new Tache(2, "Tâche 2", 5, List.of(1));
	    tache3 = new Tache(3, "Tâche 3", 2, List.of(2));
	    tache4 = new Tache(4, "Tâche 4", 4, List.of(3));
	    tache5 = new Tache(5, "Tâche 5", 20, Collections.emptyList());
	    tache6 = new Tache(6, "Tâche 6", 6, List.of(4, 5));
	    tache7 = new Tache(7, "Tâche 7", 1, Collections.emptyList());
	    tache8 = new Tache(8, "Tâche 8", 3, List.of(7));
	    tache9 = new Tache(15, "Tâche 9", 2, Collections.emptyList());
	    tache10 = new Tache(20, "Tâche 10", 5, List.of(15));
	}


	/**
	 * Test method for 
	 * {@link iut.info1sae.planning.Gestionnaire#Gestionnaire(List<Tache>)}
	 */
    @Test
    void testConstructeur() {
        Gestionnaire gestionnaire = new Gestionnaire(List.of(tache1, tache2));
        assertNotNull(gestionnaire);
    }
    

	@Test
	void testConstructeurAvecDependanceManquante() {
		Tache.getTachesStockees().clear();
	    Tache tache1 = new Tache(1, "Tâche 1", 3, List.of());
	    Tache tache2 = new Tache(2, "Tâche 2", 5, List.of(99)); 
	
	    Exception exception = assertThrows(IllegalArgumentException.class, () ->
	        new Gestionnaire(List.of(tache1, tache2))
	    );
	
	    assertEquals("La tâche avec l'ID 99 n'existe pas.",
	    		      exception.getMessage());
	}

	/**
	 * Test method for 
	 * {@link iut.info1sae.planning.Gestionnaire#Gestionnaire(List<Tache>)}
	 */
    @Test
    void testConstructeurAvecCycleSimple() {
    	Tache.getTachesStockees().clear();
    	Tache tache1 = new Tache(1, "Tâche 1", 3, List.of(2));
        Tache tache2 = new Tache(2, "Tâche 2", 5, List.of(1));

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            new Gestionnaire(List.of(tache1, tache2))
        );

        assertEquals("Le graphe des dépendances contient un cycle.",
        		      exception.getMessage());
    }

    /**
	 * Test method for 
	 * {@link iut.info1sae.planning.Gestionnaire#Gestionnaire(List<Tache>)}
	 */
    @Test
    void testConstructeurAvecCycleComplexe() {
    	Tache.getTachesStockees().clear();
        Tache tache1 = new Tache(1, "Tâche 1", 3, List.of(3));
        Tache tache2 = new Tache(2, "Tâche 2", 5, List.of(1));
        Tache tache3 = new Tache(3, "Tâche 3", 2, List.of(2));

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            new Gestionnaire(List.of(tache1, tache2, tache3))
        );

        assertEquals("Le graphe des dépendances contient un cycle.",
        		      exception.getMessage());
    } 

    /**
	 * Test method for{@link iut.info1sae.planning.Gestionnaire#calculerOrdre()}
	 */
    @Test
    void testCalculerOrdre() {
        Gestionnaire gestionnaire = new Gestionnaire(List.of(tache1,
        		                                     tache2, tache3));

        List<Tache> ordre = gestionnaire.calculerOrdre();

        assertEquals(3, ordre.size());
        assertEquals(1, ordre.get(0).getId());
        assertEquals(2, ordre.get(1).getId());
        assertEquals(3, ordre.get(2).getId());
    }

    /**
	 * Test method for
	 * {@link iut.info1sae.planning.Gestionnaire#calculerDureeMax()}
	 */
    @Test
    void testCalculerDureeMax() {
        Gestionnaire gestionnaire = new Gestionnaire(List.of(tache1,
        		                                     tache2, tache3));

        int dureeMax = gestionnaire.calculerDureeMax();

        assertEquals(10, dureeMax); // 3 (Tâche 1) + 5 (Tâche 2) + 2 (Tâche 3)
    }

    /**
	 * Test method for{@link iut.info1sae.planning.Gestionnaire#calculerOrdre()}
	 */
    @Test
    void testTachesAvecPredecesseurs() {
        Gestionnaire gestionnaire = new Gestionnaire(List.of(tache1, tache2,
        		                                             tache3, tache4,
        		                                             tache5, tache6));

        List<Tache> ordre = gestionnaire.calculerOrdre();
        assertTrue(ordre.indexOf(tache1) < ordre.indexOf(tache2));
        assertTrue(ordre.indexOf(tache1) < ordre.indexOf(tache3));
        assertTrue(ordre.indexOf(tache2) < ordre.indexOf(tache4));
        assertTrue(ordre.indexOf(tache3) < ordre.indexOf(tache4));
        assertTrue(ordre.indexOf(tache4) < ordre.indexOf(tache6));
        assertTrue(ordre.indexOf(tache5) < ordre.indexOf(tache6));

        int dureeMax = gestionnaire.calculerDureeMax();
        /* Chemin le plus long : Tâche 1 -> Tâche 2 -> Tâche 4 -> Tâche 6 */
        assertEquals(26, dureeMax);
    }

    /**
	 * Test method for{@link iut.info1sae.planning.Gestionnaire#calculerOrdre()}
	 */
    @Test
    void testTachesDansDesordre() {
        Gestionnaire gestionnaire = new Gestionnaire(List.of(tache3, tache1, 
        		                                             tache4, tache2));

        List<Tache> ordre = gestionnaire.calculerOrdre();
        assertEquals(4, ordre.size());
        assertTrue(ordre.indexOf(tache1) < ordre.indexOf(tache2));
        assertTrue(ordre.indexOf(tache2) < ordre.indexOf(tache3));
        assertTrue(ordre.indexOf(tache3) < ordre.indexOf(tache4));

        int dureeMax = gestionnaire.calculerDureeMax();
        /* Chemin le plus long: Tâche 1 -> Tâche 2 -> Tâche 3 -> Tâche 4*/
        assertEquals(14, dureeMax);
    }

    /**
	 * Test method for{@link iut.info1sae.planning.Gestionnaire#calculerOrdre()}
	 */
    @Test
    void testTachesAvecIdsNonConsecutifs() {
        Gestionnaire gestionnaire = new Gestionnaire(List.of(tache9, tache10));

        List<Tache> ordre = gestionnaire.calculerOrdre();
        assertEquals(2, ordre.size());
        assertTrue(ordre.indexOf(tache9) < ordre.indexOf(tache10));

        int dureeMax = gestionnaire.calculerDureeMax();
        assertEquals(7, dureeMax);
    }

    /**
	 * Test method for{@link iut.info1sae.planning.Gestionnaire#calculerOrdre()}
	 */
    @Test
    void testTachesAvecDependancesMultiples() {
        Gestionnaire gestionnaire = new Gestionnaire(List.of(tache1, tache2, 
        		                               tache3, tache4, tache5, tache6));

        List<Tache> ordre = gestionnaire.calculerOrdre();
        assertTrue(ordre.indexOf(tache1) < ordre.indexOf(tache2));
        assertTrue(ordre.indexOf(tache2) < ordre.indexOf(tache3));
        assertTrue(ordre.indexOf(tache3) < ordre.indexOf(tache4));
        assertTrue(ordre.indexOf(tache4) < ordre.indexOf(tache6));
        assertTrue(ordre.indexOf(tache5) < ordre.indexOf(tache6));

        int dureeMax = gestionnaire.calculerDureeMax();
        assertEquals(26, dureeMax);
    }
    

    /**
	 * Test method for
	 *{@link iut.info1sae.planning.Gestionnaire#trouverTachesSuccesseurs(Tache)}
	 */
	@Test
	void testTrouverTachesSuccesseurs() {
	    Gestionnaire gestionnaire = new Gestionnaire(List.of(tache1, tache2,
	    		                                             tache3, tache4,
	    		                                             tache5, tache6));
	    assertEquals(List.of(tache2),
	    		     gestionnaire.trouverTachesSuccesseurs(tache1));	
	    assertEquals(List.of(tache3),
	    		     gestionnaire.trouverTachesSuccesseurs(tache2));
	    assertEquals(List.of(tache4),
	    		     gestionnaire.trouverTachesSuccesseurs(tache3));	
	    assertEquals(List.of(tache6),
	    		     gestionnaire.trouverTachesSuccesseurs(tache4));	
	    assertEquals(List.of(tache6),
	    		     gestionnaire.trouverTachesSuccesseurs(tache5));
	    assertTrue(gestionnaire.trouverTachesSuccesseurs(tache6).isEmpty(),
	    		   "Successeurs de tache6 : " +
	                gestionnaire.trouverTachesSuccesseurs(tache6));
	}


	/**
	 * Test method for
	 * {@link 
	 * iut.info1sae.planning.Gestionnaire#trouverTachesPredecesseurs(Tache)}
	 */
	@Test
	void testTrouverTachesPredecesseurs() {
	    Gestionnaire gestionnaire = new Gestionnaire(List.of(tache1, tache2,
	    		                                             tache3, tache4,
	    		                                             tache5, tache6));
	
	    assertEquals(0, gestionnaire.trouverTachesPredecesseurs(tache1).length);
	    assertArrayEquals(new int[]{1},
	    		          gestionnaire.trouverTachesPredecesseurs(tache2));
	    assertArrayEquals(new int[]{2},
	    		          gestionnaire.trouverTachesPredecesseurs(tache3));
	    assertArrayEquals(new int[]{3},
	    		          gestionnaire.trouverTachesPredecesseurs(tache4));
	    assertEquals(0, gestionnaire.trouverTachesPredecesseurs(tache5).length);
	    assertArrayEquals(new int[]{4, 5},
	    		         gestionnaire.trouverTachesPredecesseurs(tache6));
	}
	
	




}
