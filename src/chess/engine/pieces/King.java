package chess.engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.*;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece implements SpecialFirstMove {
    private boolean hasMoved = false;
    
    public King(PieceColor color, ChessBoard chessBoard) {
        super(PieceType.KING, color, chessBoard);
    }

    @Override
    public List<Move> getMoves(int x, int y) {
        List<Move> moves = new ArrayList<>();
        ChessBoard chessBoard = this.getChessBoard();

        Move.addMove(x + 1, y + 1, this, moves, chessBoard);
        Move.addMove( x + 1, y, this, moves, chessBoard);
        Move.addMove( x + 1, y - 1, this, moves, chessBoard);

        Move.addMove(x - 1, y + 1, this, moves, chessBoard);
        Move.addMove(x - 1, y, this, moves, chessBoard);
        Move.addMove(x - 1, y - 1, this, moves, chessBoard);

        Move.addMove(x , y + 1, this, moves, chessBoard);
        Move.addMove(x , y - 1, this, moves, chessBoard);

        // Gestion du castle.
        if (!hasMoved) {
            Rook rightRook = (Rook) chessBoard.getCellAt(x + 4, y);
            Rook leftRook = (Rook) chessBoard.getCellAt(x - 3, y);

            if (rightRook != null && rightRook.hasAlreadyMoved() && chessBoard.isCellEmpty(x + 1, y) && chessBoard.isCellEmpty(x + 2, y)
                    && chessBoard.isCellEmpty(x + 3, y)) {
                moves.add(new Move(x + 2, y, SpecialMove.KING_LONG_CASTLED));
            }
            if (leftRook != null && leftRook.hasAlreadyMoved() && chessBoard.isCellEmpty(x - 1, y) && chessBoard.isCellEmpty(x - 2, y)) {
                moves.add(new Move(x - 2, y, SpecialMove.KING_SHORT_CASTLED));
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
