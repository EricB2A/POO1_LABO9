package chess.engine;

/* ---------------------------
Laboratoire 	: 09
Fichier 		: engine/SpecialMove.java
Auteur(s) 	    : Eric Bousbaa, Ilias Goujgali
Date			: 14.01.2020

But 			: Représente un mouvement spécial que peut effectuer une pièce.

Remarque(s) 	: -

Compilateur	 : javac 11.0.4
--------------------------- */
public enum SpecialMove {
    PAWN_FAST_MOVE,
    PAWN_EN_PASSANT,
    PAWN_PROMOTION,
    KING_SHORT_CASTLED,
    KING_LONG_CASTLED
}
