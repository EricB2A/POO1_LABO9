package chess.engine;

import chess.PieceType;
import chess.PlayerColor;

import java.awt.Point;
import java.util.List;

public abstract class Piece  {
    private PieceType type;
    private PieceColor color;
    private ChessBoard chessBoard;

    public Piece(PieceType type, PieceColor color, ChessBoard chessBoard) {
        if (type == null) {
            throw new RuntimeException("Piece type is vide.");
        }
        if (color == null){
            throw new RuntimeException("A piece need une couleur.");
        }
        if(chessBoard == null){
            throw new RuntimeException("Board is nul.");
        }
        this.type = type;
        this.color = color;
        this.chessBoard = chessBoard;
    }

    public abstract List<Move> getMoves(Point pos, boolean virtual);

    public boolean willBeCheck(Move move){

        // Ok, essayons d'applique le move
        chessBoard.placePieceAt(this, move.getTo());

        // Est-ce que mon roi va prendre cher ?
        boolean isKingCheck = chessBoard.isCheck(color.getColor());

        // Rollback
        chessBoard.placePieceAt(this, move.getFrom());
        System.out.println("IS BE CHECK : " + isKingCheck);

        return isKingCheck;
    }

    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    public PlayerColor getColor(){
        return color.getColor();
    }

    public PieceType getType() {
        return type;
    }

    public Side getSide() {
        return color.getSide();
    }

}
