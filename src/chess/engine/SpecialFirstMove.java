package chess.engine;

/* ---------------------------
Laboratoire 	: 09
Fichier 		: engine/SpecialFirstMove.java
Auteur(s) 	    : Eric Bousbaa, Ilias Goujgali
Date			: 14.01.2020

But 			: Interface que peuvent implémenter certaines pièces,
                   dont on souhaite savoir si elles sont déjà été déplacés
                   dans le cours d'une partie (Exemple : Roi avant un Caslte).

Remarque(s) 	: - 

Compilateur	 : javac 11.0.4
--------------------------- */
public interface SpecialFirstMove {
    /**
     * @return Vrai si la pièce n'a pas encore été déplacé dans la partie.
     *         Faux si la pièce a été déplacée.
     */
    boolean hasNotMoved();

    /**
     * Permet d'informer que la pièce a été déplacée. Suite à l'appel de cette
     * méthode, hasNotMoved() renvoie Faux.
     */
    void hasMoved();
}
