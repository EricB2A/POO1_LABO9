package chess.engine.pieces;

import chess.PieceType;
import chess.engine.Board;
import chess.engine.Move;
import chess.engine.Piece;
import chess.engine.Player;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece implements SpecialFirstMove {
    private boolean hasMoved = false;

    public King(Player owner) {
        super(PieceType.KING, owner);
    }

    @Override
    public List<Move> getMoves(int x, int y) {
        List<Move> moves = new ArrayList<>();
        Board board = this.getOwner().getBoard();

        Move.addMove(x, y, x + 1, y + 1, moves, board);
        Move.addMove(x, y, x + 1, 0, moves, board);
        Move.addMove(x, y, x + 1, y - 1, moves, board);

        Move.addMove(x, y, x - 1, y + 1, moves, board);
        Move.addMove(x, y, x - 1, 0, moves, board);
        Move.addMove(x, y, x - 1, y - 1, moves, board);

        Move.addMove(x, y, x , y + 1, moves, board);
        Move.addMove(x, y, x , y - 1, moves, board);



        // gestion du castle
        if (!hasMoved) {
            Rook rRook = (Rook) board.getPiece(x + 4, y),
                    lRook = (Rook) board.getPiece(x - 3, y);

            if (rRook != null && rRook.hasAlreadyMoved() && board.isCellFree(x + 1, y) && board.isCellFree(x + 2, y)
                    && board.isCellFree(x + 3, y)) {
                System.out.println("Roi: grand castled");
                moves.add(new Move(x, y, x + 2, y, SpecialMove.KING_LONG_CASTLED));
            }
            if (lRook != null && lRook.hasAlreadyMoved() && board.isCellFree(x - 1, y) && board.isCellFree(x - 2, y)) {
                System.out.println("Roi: petit castled");
                moves.add(new Move(x, y, x - 2, y, SpecialMove.KING_SHORT_CASTLED));
            }
        }


        return moves;
    }

    public boolean hasAlreadyMoved() {
        return !hasMoved;
    }

    public void hasMoved() {
        this.hasMoved = true;
    }
}
