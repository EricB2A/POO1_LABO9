package chess.engine;

/* ---------------------------
Laboratoire 	: 09
Fichier 		: engine/Side.java
Auteur(s) 	    : Eric Bousbaa, Ilias Goujgali
Date			: 14.01.2020

But 			: Côté auquel est assigné une équipe.

Remarque(s) 	: -

Compilateur	 : javac 11.0.4
--------------------------- */
public enum Side {
    //RAPPEL: Dans notre échiquier, la cellule (0,0) est en bas à gauche de l'échiquier.
    
    TOP(ChessBoard.getDimension() - 1), // Dernière rangée
    BOTTOM(0); // Première rangée. On commence l'indexation à 0 car nous ne sommes pas des animaux.

    Side(int i) {
        this.position = i;
    }
    public int position;
}