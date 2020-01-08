package chess.engine;

import chess.PieceType;

import java.util.List;

public abstract class Piece {
    //NOTE: Ce serait peut-être plus malin de changer l'ordre de lisibilité,
    //      vu que a l'emplacement des pièces dans le Board et pas dans la pièce en elle-même.. à voir!
    private Player owner;
    private PieceType type;
    private ChessBoard chessBoard;

    public Piece(PieceType type, Player owner, ChessBoard chessBoard) {
        if (owner == null) {
            throw new RuntimeException("The joueur is nul !");
        }
        if (type == null) {
            throw new RuntimeException("Piece type is vide.");
        }
        if(chessBoard == null){
            throw new RuntimeException("Board is nul.");
        }
        this.type = type;
        this.owner = owner;
        this.chessBoard = chessBoard;
    }

    public abstract List<Move> getMoves(int x, int y);

    //TODO: à supprimer. on assigne un couleur à la pièce à la place.
    public Player getOwner() {
        return owner;
    }

    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    public PieceType getType() {
        return type;
    }



}
