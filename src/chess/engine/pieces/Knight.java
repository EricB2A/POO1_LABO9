package chess.engine.pieces;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import chess.engine.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece implements ChessView.UserChoice {
    public Knight(PieceColor color, ChessBoard chessBoard) {
        super(PieceType.KNIGHT, color, chessBoard);
    }

    @Override
    public List<Move> getMoves(Point pos, boolean virtual) {
        List<Move> moves = new ArrayList<>();
        ChessBoard chessBoard = this.getChessBoard();
        int x = pos.x, y = pos.y;

        Move.addMove(pos, new Point(x + 1, y + 2), this, moves, virtual);
        Move.addMove(pos, new Point(x - 1, y + 2), this, moves, virtual);
        Move.addMove(pos, new Point(x + 1, y - 2), this, moves, virtual);
        Move.addMove(pos, new Point(x - 1, y - 2), this, moves, virtual);

        Move.addMove(pos, new Point(x + 2, y + 1), this, moves, virtual);
        Move.addMove(pos, new Point(x - 2, y + 1), this, moves, virtual);
        Move.addMove(pos, new Point(x + 2, y - 1), this, moves, virtual);
        Move.addMove(pos, new Point(x - 2, y - 1), this, moves, virtual);

        return moves;
    }

    @Override
    public String textValue() {
        return "Cavalier";
    }
}
