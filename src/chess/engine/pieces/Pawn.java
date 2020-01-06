package chess.engine.pieces;

import chess.PieceType;
import chess.engine.Piece;
import chess.engine.Playable;
import chess.engine.Player;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    private boolean firstMove = false;

    public Pawn(Player owner) {
        super(PieceType.PAWN, owner);
    }

    @Override
    public List<Move> getMoves(int x, int y) {
        Playable[][] board = getOwner().getBoard().getBoard();
        List<Move> moves = new ArrayList<Move>();

        if(getOwner().getSide() == Side.BOTTOM){

        }else{ // TOP

        }

        return moves;
    }
}
