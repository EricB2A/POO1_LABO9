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
        return (x >= 0 && x < dimension && y > 0 && y < dimension);
    }

    public static void getLine(int x, int y, int deltaX, int deltaY, List<Move> moves, Board board){
        Playable chessBoard[][] = board.getBoard();
        int xx = x + deltaX;
        int yy = y + deltaY;
        while(inBound(xx, yy, board.getDimension())){
            Piece piece = (Piece) chessBoard[xx][yy];
            if(piece == null){
                moves.add(new Move(x, y, xx, yy));
            }else if(piece.getOwner().getColor() != ((Piece) chessBoard[x][y]).getOwner().getColor()){
                moves.add(new Move(x, y, xx, yy));
                break; // On ne peut aller plus loin.
            }else{
                break;
            }
            xx += deltaX;
            yy += deltaY;
        }
    }
}
