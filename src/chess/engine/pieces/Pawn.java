package chess.engine.pieces;

import chess.PieceType;
import chess.engine.Board;
import chess.engine.Piece;
import chess.engine.Playable;
import chess.engine.Player;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    private boolean firstMove ;

    public Pawn(Player owner) {
        super(PieceType.PAWN, owner);
        this.firstMove = true;
    }

    @Override
    public List<Move> getMoves(int x, int y) {
        Board board = getOwner().getBoard();
        List<Move> moves = new ArrayList<Move>();
        int deltaPlayer = getOwner().getSide() == Side.TOP ? 1 : -1;

        // Avancer.
        if(firstMove){
            if(board.isCellFree(x, y +2 * deltaPlayer )){
                moves.add(new Move(x, y, x, y + 2 * deltaPlayer));
            }
        }
        if(board.isCellFree(x, y +  deltaPlayer)){
            moves.add(new Move(x, y, x, y +  deltaPlayer));
        }
        return moves;
    }
}
