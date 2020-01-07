package chess.engine.pieces;

import chess.ChessView;
import chess.PieceType;
import chess.engine.Board;
import chess.engine.Move;
import chess.engine.Piece;
import chess.engine.Player;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece implements ChessView.UserChoice {
    public Knight(Player owner) {
        super(PieceType.KNIGHT, owner);
    }

    @Override
    public List<Move> getMoves(int x, int y) {
        Board board = this.getOwner().getBoard();
        List<Move> moves = new ArrayList<Move>();

        Move.addMove(x, y, x + 1, y + 2, moves, board);
        Move.addMove(x, y, x - 1, y + 2, moves, board);
        Move.addMove(x, y, x + 1, y - 2, moves, board);
        Move.addMove(x, y, x - 1, y - 2, moves, board);

        Move.addMove(x, y, x + 2, y + 1, moves, board);
        Move.addMove(x, y, x - 2, y + 1, moves, board);
        Move.addMove(x, y, x + 2, y - 1, moves, board);
        Move.addMove(x, y, x - 2, y - 1, moves, board);

        return moves;
    }

    @Override
    public String textValue() {
        return "Cavalier";
    }
}
