package chess.engine.pieces;

import chess.PieceType;
import chess.engine.*;

import java.util.ArrayList;
import java.util.List;

/*TODO: factoriser la promotion(3x copier/coller)
 * */
public class Pawn extends Piece implements SpecialFirstMove {
    private boolean hasMoved = false;
    private int deltaPlayer = getOwner().getSide() == Side.TOP ? 1 : -1;
    private Board board;

    public Pawn(Player owner, ChessBoard chessBoard) {
        super(PieceType.PAWN, owner, chessBoard);
    }

    @Override
    public List<Move> getMoves(int x, int y) {

        List<Move> moves = new ArrayList<Move>();

        SpecialMove specialMove = canBePromoted(y + deltaPlayer) ? SpecialMove.PAWN_PROMOTION : null;
        // avance 1 case avec possible promotion
        if(board.isCellFree(x, y + deltaPlayer) ){
            // avance de 2 cases
            if(!hasMoved && board.isCellFree(x, y+ 2 * deltaPlayer)){
                moves.add(new Move(x, y, x, y + 2 * deltaPlayer, SpecialMove.PAWN_FAST_MOVE));
            }
            Move.addMove(x, y, x, y + deltaPlayer, moves, board, specialMove);
        }
        if (canAttack(x + 1, y + deltaPlayer)) {
            Move.addMove(x, y, x + 1, y + deltaPlayer, moves, board, specialMove);
        }
        if (canAttack(x - 1, y + deltaPlayer)) {
            Move.addMove(x, y, x - 1, y + deltaPlayer, moves, board, specialMove);
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
                if (lastMove.getToX() == x + 1 && board.isCellFree(x + 1, y + deltaPlayer)) {
                    Move.addMove(x, y, x + 1, y + deltaPlayer, moves, board, SpecialMove.PAWN_EN_PASSANT);
                }
                // diagonale gauche (else if car impossible que le dernier mouvement soit et gauche et à droite)
                else if (lastMove.getToX() == x - 1 && board.isCellFree(x - 1, y + deltaPlayer)) {
                    Move.addMove(x, y, x - 1, y + deltaPlayer, moves, board, SpecialMove.PAWN_EN_PASSANT);
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
        Board board = getOwner().getBoard();
        return y == 0 || y == board.getDimension() - 1;
    }

    private boolean canAttack(int toX, int toY) {
        return Move.inBound(toX, toY, board.getDimension()) && !board.isCellFree(toX, toY) && board.getPiece(toX, toY).getOwner() != getOwner();
    }
}
