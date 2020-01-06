package chess.engine.pieces;

import chess.PieceType;
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
        Playable[][] board = getOwner().getBoard().getBoard();
        List<Move> moves = new ArrayList<Move>();

        if(getOwner().getSide() == Side.BOTTOM){
            // Avancer.
            if(firstMove){
                if(board[x][y-2] == null){
                    moves.add(new Move(x, y, x, y-2));
                }
            }
            if(board[x][y-1] == null){
                moves.add(new Move(x, y, x, y-1));
            }

        }else{ // TOP
            // Avancer.
            if(firstMove){
                if(board[x][y+2] == null){
                    moves.add(new Move(x, y, x, y+2));
                }
            }
            if(board[x][y+1] == null){
                moves.add(new Move(x, y, x, y+1));
            }

        }

        return moves;
    }
}
