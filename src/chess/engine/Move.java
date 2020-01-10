package chess.engine;

import java.awt.Point;
import java.util.List;

public class Move {
    private Point from, to;
    private SpecialMove specialMove;

    public Move(Point from, Point to){
        this.from = new Point(from);
        this.to = new Point(to);
    }

    public Move(Point from, Point to, SpecialMove specialMove){
        this(from, to);
        this.specialMove = specialMove;
    }

    public boolean equals(Point pos){
            return to.equals(pos);
    }

    public static boolean inBound(Point pos, int dimension){
        return (pos.x >= 0 && pos.x < dimension) && (pos.y >= 0 && pos.y < dimension);
    }

    // Critères de mouvement : Déplacement a lieu sur cellule vide. Peut aller sur cellule contenant pièce adverse (en la mangeant),
    // mais pas sur cellule avec pièce alliée.
    public static void addMove(Point from, Point to, Piece originalPiece,List<Move> moves, ChessBoard chessBoard){
        addMove(from, to, originalPiece, moves, chessBoard, null);
    }

    public static void addMove(Point from, Point to, Piece originalPiece, List<Move> moves, ChessBoard chessBoard, SpecialMove specialMove){
        if(Move.inBound(to, chessBoard.getDimension())) {
            Piece piece = chessBoard.getCellAt(to);
            if (piece == null || piece.getColor() != originalPiece.getColor()) {
                moves.add(new Move(from, to, specialMove));
            }
        }
    }

    public static void addMoves(Point from, Point delta, List<Move> moves, ChessBoard chessBoard){
        Point to = new Point(from.x + delta.x, from.y + delta.y);
        Piece originalPiece = chessBoard.getCellAt(from);

        while(inBound(to, chessBoard.getDimension())){
            Piece piece = chessBoard.getCellAt(to);
            if(piece == null){
                moves.add(new Move(from, to));
            }else if(piece.getColor() != originalPiece.getColor()){
                // Oh wow, une pièce adverse.
                moves.add(new Move(from, to));
                break; // On ne peut aller plus loin, quittons la boucle !
            }else{
                break;
            }
            to.x += delta.x;
            to.y += delta.y;
        }
    }

    public SpecialMove getSpecialMove(){
        return specialMove;
    }
    public Point getFrom(){
        return from;
    }
    public Point getTo(){
        return to;
    }
}
