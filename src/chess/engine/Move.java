package chess.engine;

import java.util.ArrayList;
import java.util.List;

public class Move {
    private int fromX, fromY, toX, toY;

    public Move(int fromX, int fromY, int toX, int toY){
        this.fromX = fromX; //TODO: from nécessaire ?
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
    }

    public boolean equals(int x, int y){
        return toX == x && toY == y;
    }

    public static boolean inBound(int x, int y, int dimension){
        return (x >= 0 && x < dimension) && (y > 0 && y < dimension);
    }

    // Critères de mouvement : Déplacement a lieu sur cellule vide. Peut aller sur cellule contenant pièce adverse (en la mangeant),
    // mais pas sur cellule avec pièce alliée.
    public static void addMove(int fromX, int fromY, int toX, int toY, List<Move> moves, Board board){
        Playable chessBoard[][] = board.getBoard();
        if(Move.inBound(toX, toY, board.getDimension())){
            Piece piece = (Piece) board.getBoard()[toX][toY];
            if(piece == null || piece.getOwner().getColor() != ((Piece) chessBoard[fromX][fromY]).getOwner().getColor()){
                moves.add(new Move(fromX, fromY, toX, toY));
            }
        }
    }

    public static void addMoves(int fromX, int fromY, int deltaX, int deltaY, List<Move> moves, Board board){
        Playable chessBoard[][] = board.getBoard();
        int toX = fromX + deltaX;
        int toY = fromY + deltaY;
        while(inBound(toX, toY, board.getDimension())){
            Piece piece = (Piece) chessBoard[toX][toY];
            if(piece == null){
                moves.add(new Move(fromX, fromY, toX, toY));
            }else if(piece.getOwner().getColor() != ((Piece) chessBoard[fromX][fromY]).getOwner().getColor()){
                // Oh wow, une pièce adverse.
                moves.add(new Move(fromX, fromY, toX, toY));
                break; // On ne peut aller plus loin, quittons la boucle !
            }else{
                break;
            }
            toX += deltaX;
            toY += deltaY;
        }
    }
}
