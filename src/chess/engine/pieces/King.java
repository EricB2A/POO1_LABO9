package chess.engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class King extends Piece implements SpecialFirstMove {
    private boolean hasMoved = false;
    
    public King(PieceColor color, ChessBoard chessBoard) {
        super(PieceType.KING, color, chessBoard);
    }

    @Override
    public List<Move> getMoves(Point pos, boolean virtual) {
        List<Move> moves = new ArrayList<>();
        ChessBoard chessBoard = this.getChessBoard();
        int x = pos.x, y = pos.y;

        Move.addMove(pos, new Point(x + 1, y + 1), this, moves, virtual);
        Move.addMove(pos, new Point(x + 1, y), this, moves, virtual);
        Move.addMove(pos, new Point(x + 1, y - 1), this, moves, virtual);

        Move.addMove(pos, new Point(x - 1, y + 1), this, moves, virtual);
        Move.addMove(pos, new Point(x - 1, y), this, moves, virtual);
        Move.addMove(pos, new Point(x - 1, y - 1), this, moves, virtual);

        Move.addMove(pos, new Point(x , y + 1), this, moves, virtual);
        Move.addMove(pos, new Point(x , y - 1), this, moves, virtual);

        //todo méthode static est-ce utilisé quelque part d'autre ?
        PlayerColor opponentColor = PieceColor.getOpponentColor(getColor());

        // Gestion du castle.
        if (!virtual && !hasMoved && !chessBoard.isUnderAttack(pos, opponentColor)) {
            Rook leftRook = (Rook) chessBoard.getCellAt(new Point(x + 3, y));
            Rook rightRook = (Rook) chessBoard.getCellAt(new Point(x - 4, y));

            if (leftRook != null && leftRook.hasAlreadyMoved() && chessBoard.isCellEmpty(new Point(x - 1, y)) && chessBoard.isCellEmpty(new Point(x - 2, y))
                    && chessBoard.isCellEmpty(new Point(x - 3, y)) && !chessBoard.isUnderAttack(new Point(pos.x - 1, pos.y), opponentColor)) {
                Move move = new Move(pos, new Point(x - 2, y), SpecialMove.KING_LONG_CASTLED);
                Move._add(this, move, moves, false);

            }
            if (rightRook != null && rightRook.hasAlreadyMoved() && chessBoard.isCellEmpty(new Point(x + 1, y))
                    && chessBoard.isCellEmpty(new Point(x + 2, y)) && !chessBoard.isUnderAttack(new Point(pos.x + 1, pos.y), opponentColor)) {

                Move move = new Move(pos, new Point(x + 2, y), SpecialMove.KING_SHORT_CASTLED);
                Move._add(this, move, moves, false);
                //_add(Piece piece, Move move, List<Move> moves, boolean virtual, SpecialMove specialMove)
                //moves.add();
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
