package chess.engine;

import chess.PieceType;
import chess.PlayerColor;

public abstract class Piece implements Playable {
    //NOTE: Ce serait peut-être plus malin de changer l'ordre de lisibilité,
    //      vu que a l'emplacement des pièces dans le Board et pas dans la pièce en elle-même.. à voir!
    protected Player owner;
    private PieceType type;
    protected Board board;

    public Piece(PieceType type, Player owner, Board board){
        if(owner == null){
            throw new RuntimeException("The joueur is nul !");
        }
        if(type == null){
            throw new RuntimeException("Piece type is vide.");
        }
        if(board == null)
        {
            throw new RuntimeException("Board invalide");
        }
        this.type = type;
        this.owner = owner;
        this.board = board;
    }

    public Player getOwner() {
        return owner;
    }


    public PieceType getType() {
        return type;
    }

    public boolean belongsToPlayer(Player player){
        return owner.getColor() == player.getColor();
    }
}
