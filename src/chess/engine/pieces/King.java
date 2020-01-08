package chess.engine.pieces;

import chess.PieceType;
import chess.engine.*;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece implements SpecialFirstMove {
    private boolean hasMoved = false;
    
    public King(Player owner, ChessBoard chessBoard) {
        super(PieceType.KING, owner, chessBoard);
    }

    @Override
    public List<Move> getMoves(int x, int y) {
        List<Move> moves = new ArrayList<>();
        ChessBoard chessBoard = this.getChessBoard();

        Move.addMove(x, y, x + 1, y + 1, moves, chessBoard);
        Move.addMove(x, y, x + 1, 0, moves, chessBoard);
        Move.addMove(x, y, x + 1, y - 1, moves, chessBoard);

        Move.addMove(x, y, x - 1, y + 1, moves, chessBoard);
        Move.addMove(x, y, x - 1, 0, moves, chessBoard);
        Move.addMove(x, y, x - 1, y - 1, moves, chessBoard);

        Move.addMove(x, y, x , y + 1, moves, chessBoard);
        Move.addMove(x, y, x , y - 1, moves, chessBoard);



        // gestion du castle
        if (!hasMoved) {
            Rook rRook = (Rook) chessBoard.getCellAt(x + 4, y),
                    lRook = (Rook) chessBoard.getCellAt(x - 3, y);

            if (rRook != null && rRook.hasAlreadyMoved() && chessBoard.isCellEmpty(x + 1, y) && chessBoard.isCellEmpty(x + 2, y)
                    && chessBoard.isCellEmpty(x + 3, y)) {
                System.out.println("Roi: grand castled");
                moves.add(new Move(x + 2, y, SpecialMove.KING_LONG_CASTLED));
            }
            if (lRook != null && lRook.hasAlreadyMoved() && chessBoard.isCellEmpty(x - 1, y) && chessBoard.isCellEmpty(x - 2, y)) {
                System.out.println("Roi: petit castled");
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
