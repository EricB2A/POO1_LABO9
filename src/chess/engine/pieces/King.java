package chess.engine.pieces;

import chess.PieceType;
import chess.engine.Board;
import chess.engine.Move;
import chess.engine.Piece;
import chess.engine.Player;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    public King(Player owner) {
        super(PieceType.KING, owner);
    }

    @Override
    public List<Move> getMoves(int x, int y) {
        List<Move> moves = new ArrayList<>();
        Board board = this.getOwner().getBoard();
        
        Move.addMove(x, y, x+1, y+1, moves, board);
        Move.addMove(x, y, x+1, 0, moves, board);
        Move.addMove(x, y, x+1, y-1, moves, board);

        Move.addMove(x, y, x, y+1, moves, board);
        Move.addMove(x, y, x, y-1, moves, board);
        Move.addMove(x, y, x+1, y, moves, board);
        Move.addMove(x, y, x-1, y, moves, board);

        Move.addMove(x, y, x-1, y+1, moves, board);
        Move.addMove(x, y, x-1, y, moves, board);
        Move.addMove(x, y, x-1, y-1, moves, board);

        return moves;
    }
}
