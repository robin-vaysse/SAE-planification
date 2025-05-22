/**
 * Sac.java                            13 mai 2025
 * IUT de Rodez, no copyright
 */
package iut.info1.poo;

/**
 * Sac contenant des objets divers, avec une capacité maximale et des opérations
 * d'ajout, retrait et recherche. Sert ici de hotte pour contenir des jeux de société.
 * Chaque sac peut être initialisé avec une capacité définie ou par défaut.
 * 
 * @author ?
 */
public class Sac {

    /** Capacité initiale par défaut du sac si non précisée (entier) */
    public static final int CAPACITE_INITIALE_DEFAUT = 10;

    /** Capacité initiale par défaut du sac si utilisée comme double */
    public static final double CAPACITE_INITIALE_DEFAUT_DOUBLE = 10;

    /** Taille actuelle du sac, c'est-à-dire le nombre d'éléments présents */
    private int taille;

    /** Tableau contenant les objets stockés dans le sac */
    private Object[] elements;

    /**
     * Constructeur par défaut : initialise le sac avec la capacité par défaut
     */
    public Sac() {
        // TODO Initialiser le tableau avec la capacité par défaut et taille à 0
    }

    /**
     * Constructeur avec capacité définie
     * @param capacite capacité maximale du sac
     * @throws IllegalArgumentException si capacité négative ou nulle
     */
    public Sac(int capacite) {
        // TODO Initialiser le tableau avec la capacité spécifiée et taille à 0
    }

    /**
     * Ajoute un objet dans le sac s'il reste de la place
     * @param aAjouter objet à ajouter dans le sac
     * @return true si l'ajout a été effectué, false sinon
     */
    public boolean ajouter(Object aAjouter) {
        // TODO Ajouter l'objet dans le tableau s'il reste de la place
        return false; // bouchon
    }

    /**
     * Retire un objet du sac s'il est présent
     * @param aEnlever objet à retirer
     * @return true si l'objet était présent et a été retiré, false sinon
     */
    public boolean retirer(Object aEnlever) {
        // TODO Chercher l'objet et le retirer en maintenant l'ordre
        return false; // bouchon
    }

    /**
     * Donne le nombre d'objets actuellement dans le sac
     * @return le nombre d'éléments
     */
    public int cardinal() {
        // TODO Retourner la valeur de taille
        return 0; // bouchon
    }

    /**
     * Vérifie si un objet est présent dans le sac
     * @param aChercher objet à rechercher
     * @return true si présent, false sinon
     */
    public boolean isPresent(Object aChercher) {
        // TODO Parcourir le tableau jusqu’à trouver l’objet ou atteindre la fin
        return false; // bouchon
    }

    /**
     * Retourne une description textuelle du sac et de son contenu
     * @return chaîne décrivant les objets dans le sac
     */
    @Override
    public String toString() {
        // TODO Construire une chaîne avec tous les objets ajoutés
        return "Sac avec " + taille + " éléments"; // bouchon simplifié
    }
}
