package chess.engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.*;

import java.util.ArrayList;
import java.util.List;


public class Pawn extends Piece implements SpecialFirstMove {
    private boolean hasMoved = false;
    private int deltaPlayer = getSide() == Side.TOP ? 1 : -1;

    public Pawn(PieceColor color, ChessBoard chessBoard) {
        super(PieceType.PAWN, color, chessBoard);
    }

    @Override
    public List<Move> getMoves(int x, int y) {
        List<Move> moves = new ArrayList<Move>();
        ChessBoard chessBoard = this.getChessBoard();
        SpecialMove specialMove = canBePromoted(y + deltaPlayer) ? SpecialMove.PAWN_PROMOTION : null;
        // avance 1 case avec possible promotion
        if(chessBoard.isCellEmpty(x, y + deltaPlayer) ){
            // avance de 2 cases
            if(!hasMoved && chessBoard.isCellEmpty(x, y+ 2 * deltaPlayer)){
                moves.add(new Move(x, y + 2 * deltaPlayer, SpecialMove.PAWN_FAST_MOVE));
            }
            Move.addMove(x, y + deltaPlayer, this, moves, chessBoard, specialMove);
        }
        if (canAttack(x + 1, y + deltaPlayer)) {
            Move.addMove(x + 1, y + deltaPlayer, this, moves, chessBoard, specialMove);
        }
        if (canAttack(x - 1, y + deltaPlayer)) {
            Move.addMove(x - 1, y + deltaPlayer, this, moves, chessBoard, specialMove);
        }

        // prise en passant: on regarde si le dernier mouvement correspond à un déplacement de 2 (d'un pion évidemment)
        // si c'est le cas on vérifie que la destination se trouvait à gauche ou à droite de cette piece (this) si c'est
        // le cas on ajoute la diago correspondante
        Move lastMove = chessBoard.getLastMove();
        if (lastMove != null && lastMove.getSpecialMove() == SpecialMove.PAWN_FAST_MOVE) {
            if (lastMove.getToY() == y) {

                //on vérifie que la case destination est libre car sinon on pourrait manger 2 pièces d'un coup celle
                // sur la case où on va + en passant
                // diagonale droite
                if (lastMove.getToX() == x + 1 && chessBoard.isCellEmpty(x + 1, y + deltaPlayer)) {
                    Move.addMove(x + 1, y + deltaPlayer, this, moves, chessBoard, SpecialMove.PAWN_EN_PASSANT);
                }
                // diagonale gauche (else if car impossible que le dernier mouvement soit et gauche et à droite)
                else if (lastMove.getToX() == x - 1 && chessBoard.isCellEmpty(x - 1, y + deltaPlayer)) {
                    Move.addMove(x - 1, y + deltaPlayer, this, moves, chessBoard, SpecialMove.PAWN_EN_PASSANT);
                }
            }
        }
        return moves;
    }

    @Override
    public boolean hasAlreadyMoved() {
        return !hasMoved;
    }

    public void hasMoved() {
        this.hasMoved = true;
    }

    private boolean canBePromoted(int y) {
        ChessBoard chessBoard =  this.getChessBoard();
        return y == 0 || y == chessBoard.getDimension() - 1;
    }

    private boolean canAttack(int toX, int toY) {
        ChessBoard chessBoard = this.getChessBoard();
        return Move.inBound(toX, toY, chessBoard.getDimension()) && !chessBoard.isCellEmpty(toX, toY) && chessBoard.getCellAt(toX, toY).getColor() != getColor();
    }
}
