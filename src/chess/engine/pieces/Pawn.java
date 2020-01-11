package chess.engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;


public class Pawn extends Piece implements SpecialFirstMove {
    private boolean hasMoved = false;
    private int deltaPlayer = getSide() == Side.BOTTOM ? 1 : -1;

    public Pawn(PieceColor color, ChessBoard chessBoard) {
        super(PieceType.PAWN, color, chessBoard);
    }

    @Override
    public List<Move> getMoves(Point pos, boolean virtual) {
        List<Move> moves = new ArrayList<Move>();
        ChessBoard chessBoard = this.getChessBoard();
        SpecialMove specialMove = canBePromoted(pos.y + deltaPlayer) ? SpecialMove.PAWN_PROMOTION : null;
        int x = pos.x, y = pos.y;

        // avance 1 case avec possible promotion
        if(chessBoard.isCellEmpty(new Point(x, y + deltaPlayer)) ){
            // avance de 2 cases
            if(!hasMoved && chessBoard.isCellEmpty(new Point(x, y+ 2 * deltaPlayer))){
                moves.add(new Move(pos, new Point(x, y + 2 * deltaPlayer), SpecialMove.PAWN_FAST_MOVE));
            }
            Move.addMove(pos, new Point(x, y + deltaPlayer), this, moves, specialMove, virtual);
        }
        if (canAttack(new Point(x + 1, y + deltaPlayer))) {
            Move.addMove(pos, new Point(x + 1, y + deltaPlayer), this, moves, specialMove, virtual);
        }
        if (canAttack(new Point(x - 1, y + deltaPlayer))) {
            Move.addMove(pos, new Point(x - 1, y + deltaPlayer), this, moves, specialMove, virtual);
        }

        // prise en passant: on regarde si le dernier mouvement correspond à un déplacement de 2 (d'un pion évidemment)
        // si c'est le cas on vérifie que la destination se trouvait à gauche ou à droite de cette piece (this) si c'est
        // le cas on ajoute la diago correspondante
        Move lastMove = chessBoard.getLastMove();
        if (lastMove != null && lastMove.getSpecialMove() == SpecialMove.PAWN_FAST_MOVE) {
            if (lastMove.getTo().y == y) {

                //on vérifie que la case destination est libre car sinon on pourrait manger 2 pièces d'un coup celle
                // sur la case où on va + en passant
                // diagonale droite
                if (lastMove.getTo().x == x + 1 && chessBoard.isCellEmpty(new Point(x + 1, y + deltaPlayer))) {
                    Move.addMove(pos, new Point(x + 1, y + deltaPlayer), this, moves, SpecialMove.PAWN_EN_PASSANT, virtual);
                }
                // diagonale gauche (else if car impossible que le dernier mouvement soit et gauche et à droite)
                else if (lastMove.getTo().x == x - 1 && chessBoard.isCellEmpty(new Point(x - 1, y + deltaPlayer))) {
                    Move.addMove(pos, new Point(x - 1, y + deltaPlayer), this, moves, SpecialMove.PAWN_EN_PASSANT, virtual);
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

    private boolean canAttack(Point to) {
        ChessBoard chessBoard = this.getChessBoard();
        return Move.inBound(to, chessBoard.getDimension()) && !chessBoard.isCellEmpty(to) && chessBoard.getCellAt(to).getColor() != getColor();
    }
}
