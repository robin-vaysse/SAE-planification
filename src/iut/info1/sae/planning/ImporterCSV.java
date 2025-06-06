/**
 * ImporterCSV.java                            06/06/2025
 * IUT de Rodez, no copyright
 */

package iut.info1.sae.planning;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import iut.info1.sae.planning.Ecran;
import iut.info1.sae.planning.Tache;
import iut.info1.sae.planning.Gestionnaire;

/**
 * La classe `ImporterCSV` fournit des méthodes pour importer et exporter des 
 * tâches depuis et vers des fichiers CSV.
 * <p>
 * Elle permet de lire un fichier CSV pour créer une liste de tâches 
 * et d'exporter une liste de tâches dans un fichier CSV.
 * </p>
 * @author mattieu liao
 * @author Valentin Petit
 * @author Robin Vaysse
 * @author Florian Helg
 */

public class ImporterCSV {

	
    /**
     * Importe une liste de tâches depuis un fichier CSV.
     *
     * @param cheminFichier Le chemin du fichier CSV à importer.
     * @return Une liste de tâches importées depuis le fichier.
     * @throws NumberFormatException Si une valeur numérique
     *         dans le fichier est invalide.
     * @throws IOException  Si une erreur d'entrée/sortie se produit lors 
     *         de la lecture du fichier.
     */
    public static List<Tache> importerTachesDepuisCSV(String cheminFichier) {
        List<Tache> listeTaches = new ArrayList<>();

        try (BufferedReader br = new BufferedReader
        	   (new FileReader
        	   ("Z:\\sae_202_205_Helg_Liao_Rebourg_Petit_Vaysse\\projet.csv"))){
            String ligne;

            while ((ligne = br.readLine()) != null) {

                String[] valeurs = ligne.split(";");
                if (valeurs.length >= 4) {
                    int id = Integer.parseInt(valeurs[0].trim());
                    String nom = valeurs[1].trim();
                    int duree = Integer.parseInt(valeurs[2].trim());
                    List<Integer> predecesseur = new ArrayList<>();
                    
                    if (!valeurs[3].trim().isEmpty()) {
                        String[] predecesseursStr = 
                        		valeurs[3].trim().split(",");
                        for (String p : predecesseursStr) {
                            predecesseur.add(Integer.parseInt(p.trim()));
                        }
                    }

                    Tache tache = new Tache(id, nom, duree, predecesseur);
                    listeTaches.add(tache);
                }
            }

        } catch (Exception e) {
            System.err.println("Erreur lors de l'importation : " 
            					+ e.getMessage());
        }

        System.out.println("Tâches créées : " + listeTaches.size());
        Gestionnaire gestionnaire = new Gestionnaire(listeTaches);
        System.out.println("Ordonnonancement des tâches : \n" 
                           + gestionnaire.afficherOrdonnancement());
        Ecran ecran = new Ecran(gestionnaire, null);
        return listeTaches;
    }
    
    /**
     * Exporte une liste de tâches vers un fichier CSV.
     * 
     * @param taches        La liste des tâches à exporter.
     * @param cheminFichier Le chemin du fichier CSV où exporter les tâches.
     * @throws IOException Si une erreur d'entrée/sortie se produit 
     * lors de l'écriture dans le fichier.
     */

    public static void exporterVersCSV(List<Tache> taches,
    		                           String cheminFichier) {
        try (BufferedWriter bw = new BufferedWriter
        		(new FileWriter(cheminFichier))) {

            Gestionnaire gestionnaire = new Gestionnaire(taches);
            Ecran ecran = new Ecran(gestionnaire, null);
            
            // Calculer le chemin critique une seule fois
            String cheminCritiqueStr = ecran.cheminCritique().stream()
            	    .map(tache -> String.valueOf(tache.getId())) // Use task IDs
            	    .distinct() // Remove duplicates
            	    .collect(Collectors.joining(", "));

            
            bw.write("id;nom;duree;predecesseurs;auPlusTot;auPlusTard;"
            		+ "margeLibre;margeTotale;critique");
            bw.newLine();

            // Parcourir les tâches et écrire leurs informations dans le fichier
            for (Tache tache : gestionnaire.calculerOrdre()) {
                int auPlusTot = ecran.auPlusTotTache(tache);
                int auPlusTard = ecran.auPlusTardTache(tache);
                int margeLibre = ecran.margeLibreTache(tache);
                int margeTotale = ecran.margeTotalTache(tache);
                boolean estCritique = ecran.tacheCritique(tache);
                

                String predecesseursStr = tache.getPredecesseurs().stream()
                                              .map(String::valueOf)
                                              .collect(Collectors.joining(","));
                String ligne = String.format("%d;%s;%d;%s;%d;%d;%d;%d;%s",
                		
                                             tache.getId(),
                                             tache.getNom(),
                                             tache.getDuree(),
                                             predecesseursStr,
                                             auPlusTot,
                                             auPlusTard,
                                             margeLibre,
                                             margeTotale,
                                             estCritique ? "Oui" : "Non");
           
                bw.write(ligne);
                bw.newLine();
            }
            bw.newLine();
            bw.write("Chemin Critique:;" + cheminCritiqueStr);
            bw.newLine();
            bw.newLine();
      
            int auPlusTotFin;
            auPlusTotFin = ecran.auPlusTotFinProjet();
            bw.write("Date au plus tard fin du projet:; " + auPlusTotFin 
            		 + " Semaines");
            bw.newLine();
            bw.newLine();
            
            double conversionJourHomme = 
            		ecran.conversionJourHomme(auPlusTotFin);
            bw.write("Conversion en jours homme :; " 
                      + conversionJourHomme + " Jours.homme");
            bw.newLine();
            bw.newLine();
            
            double conversionMoisHomme = 
            		ecran.conversionMoisHomme(auPlusTotFin);
            bw.write("Conversion en mois homme :; " 
            		 + conversionMoisHomme + " Mois.homme");
            bw.newLine();
            bw.newLine();
            
            
            // Écrire l'ordonnancement des tâches
            String ordonnancement = gestionnaire.afficherOrdonnancement();
            bw.write("Ordonnancement des taches:");
            bw.newLine();
            for (String ligneOrdonnancement : ordonnancement.split(";")) {
                bw.write(ligneOrdonnancement.trim());
                bw.newLine();
            }
            
            System.out.println("Tâches et chemin critique exportés "
            		            + "avec succès dans : " + cheminFichier);
        } catch (Exception e) {
            try (BufferedWriter bw = new BufferedWriter
            		(new FileWriter(cheminFichier, true))) {
                bw.write("Erreur lors de l'exportation : " + e.getMessage());
                bw.newLine();
            } catch (IOException ioException) {
                System.err.println("Erreur lors de l'écriture de l'erreur"
                		+ " dans le fichier : " + ioException.getMessage());
            }
        }
    }


    /**
     * Point d'entrée principal de l'application.
     * <p>
     * Cette méthode importe des tâches depuis un fichier CSV, les affiche,
     * puis les exporte vers un autre fichier CSV.
     * </p>
     *
     * @param args Les arguments de la ligne de commande (non utilisés).
     */
    public static void main(String[] args) {
   
        String cheminFichier = "projet.csv"; 
        String cheminFichierExport =
        		"Z:/sae_202_205_Helg_Liao_Rebourg_Petit_Vaysse"
        		+ "/fichierExtraction/resultat.csv";
        List<Tache> taches = importerTachesDepuisCSV(cheminFichier);
   

        System.out.println("Tâches importées :");
        for (Tache t : taches) {
            System.out.println(t);
        }
        exporterVersCSV(taches, cheminFichierExport);
        
    }

	
}