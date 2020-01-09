package chess.engine;

import java.util.List;

public class Move {
    private int toX, toY;
    private SpecialMove specialMove;

    public Move(int toX, int toY){
        this.toX = toX;
        this.toY = toY;
    }

    public Move(int toX, int toY, SpecialMove specialMove){
        this(toX, toY);
        this.specialMove = specialMove;
    }

    public boolean equals(int x, int y){
        return toX == x && toY == y;
    }

    public static boolean inBound(int x, int y, int dimension){
        return (x >= 0 && x < dimension) && (y >= 0 && y < dimension);
    }

    // Critères de mouvement : Déplacement a lieu sur cellule vide. Peut aller sur cellule contenant pièce adverse (en la mangeant),
    // mais pas sur cellule avec pièce alliée.
    public static void addMove(int toX, int toY, Piece originalPiece,List<Move> moves, ChessBoard chessBoard){
        addMove(toX, toY, originalPiece, moves, chessBoard, null);
    }

    public static void addMove(int toX, int toY, Piece originalPiece, List<Move> moves, ChessBoard chessBoard, SpecialMove specialMove){
        if(Move.inBound(toX, toY, chessBoard.getDimension())) {
            Piece piece = chessBoard.getCellAt(toX, toY);
            if (piece == null || piece.getColor() != originalPiece.getColor()) {
                moves.add(new Move(toX, toY, specialMove));
            }
        }
    }

    public static void addMoves(int fromX, int fromY, int deltaX, int deltaY, List<Move> moves, ChessBoard chessBoard){
        int toX = fromX + deltaX;
        int toY = fromY + deltaY;
        Piece originalPiece = chessBoard.getCellAt(fromX, fromY);

        while(inBound(toX, toY, chessBoard.getDimension())){
            Piece piece = chessBoard.getCellAt(toX, toY);
            if(piece == null){
                moves.add(new Move(toX, toY));
            }else if(piece.getColor() != originalPiece.getColor()){
                // Oh wow, une pièce adverse.
                moves.add(new Move(toX, toY));
                break; // On ne peut aller plus loin, quittons la boucle !
            }else{
                break;
            }
            toX += deltaX;
            toY += deltaY;
        }
    }

    public SpecialMove getSpecialMove(){
        return specialMove;
    }
    public int getToX(){return toX;};
    public int getToY(){return toY;};
}
