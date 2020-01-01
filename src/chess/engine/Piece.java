package chess.engine;

import chess.PieceType;

public abstract class Piece implements Playable {
    //NOTE: Ce serait peut-être plus malin de changer l'ordre de lisibilité,
    //      vu que a l'emplacement des pièces dans le Board et pas dans la pièce en elle-même.. à voir!
    private Player owner;
    private PieceType type;

    public Piece(PieceType type, Player owner){
        if(owner == null){
            throw new RuntimeException("The joueur is nul !");
        }
        if(type == null){
            throw new RuntimeException("Piece type is vide.");
        }
        this.type = type;
        this.owner = owner;

    }


    public Player getOwner() {
        return owner;
    }

    public PieceType getType() {
        return type;
    }
}
