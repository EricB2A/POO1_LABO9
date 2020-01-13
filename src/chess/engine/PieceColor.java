package chess.engine;

import chess.PlayerColor;

/* ---------------------------
Laboratoire 	: 09
Fichier 		: engine/PieceColor.java
Auteur(s) 	    : Eric Bousbaa, Ilias Goujgali
Date			: 14.01.2020

But 			: Couleur d'une pièce. On associe un côté à une couleur.

Remarque(s) 	: -

Compilateur	 : javac 11.0.4
--------------------------- */
public class PieceColor {
    private PlayerColor color;
    private Side side;

    /**
     * Constructeur de couleur.
     * @param playerColor Couleur du joueur associé à la pièce
     * @param side Côté à laquelle la couleur est associé.
     * @throws RuntimeException Si playerColor ou side sont des références null.
     */
    public PieceColor(PlayerColor playerColor, Side side) {
        if(playerColor == null){
            throw new RuntimeException("The nean is not une couleur");
        }
        if(side == null){
            throw new RuntimeException("We need a side pour player");
        }

        this.color = playerColor;
        this.side = side;
    }

    /**
     * @return Couleur du joueur.
     */
    public PlayerColor getColor() {
        return color;
    }

    /**
     * @return Côté appartenant à la couleur.
     */
    public Side getSide() {
        return side;
    }
    public static PlayerColor getOpponentColor(PlayerColor playerColor){
        return playerColor == PlayerColor.WHITE ? PlayerColor.BLACK : PlayerColor.WHITE;
    }

}
