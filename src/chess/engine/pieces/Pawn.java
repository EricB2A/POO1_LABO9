package chess.engine.pieces;

import chess.PieceType;
import chess.engine.*;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    private boolean hasMoved;

    public Pawn(Player owner) {
        super(PieceType.PAWN, owner);
        this.hasMoved = false;
    }

    @Override
    public List<Move> getMoves(int x, int y) {

        Board board = getOwner().getBoard();
        List<Move> moves = new ArrayList<Move>();
        int deltaPlayer = getOwner().getSide() == Side.TOP ? 1 : -1;

        // Avancer.
        if(!hasMoved){
            if(board.isCellFree(x, y +2 * deltaPlayer )){
                moves.add(new Move(x, y, x, y + 2 * deltaPlayer));
            }
        }
        if(board.isCellFree(x, y +  deltaPlayer)){
            moves.add(new Move(x, y, x, y +  deltaPlayer));
        }

        return moves;
    }

    public boolean hasMoved(){
        return hasMoved;
    }
}
