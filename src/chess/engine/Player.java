package chess.engine;

import chess.PlayerColor;

/* ---------------------------
Laboratoire 	: 09
Fichier 		: engine/Player.java
Auteur(s) 	    : Eric Bousbaa, Ilias Goujgali
Date			: 14.01.2020

But 			: Classe représentant un joueur, jouant une partie sur l'échiquier.

Remarque(s) 	: -

Compilateur	 : javac 11.0.4
--------------------------- */
public class Player {
    private PlayerColor color;

    /**
     * Constructeur de joueur.
     * @param color Couleur du joueur.
     * @throws RuntimeException Si la couleur est une référence null.
     */
    public Player(PlayerColor color){
        if(color == null){
            throw new RuntimeException("We need a color !");
        }
        this.color = color;
    }

    /**
     * @return Retourne la couleur du joueur.
     */
    public PlayerColor getColor() {
        return color;
    }
}
