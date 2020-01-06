package chess.engine.pieces;

import chess.PieceType;
import chess.engine.Board;
import chess.engine.Piece;
import chess.engine.Playable;
import chess.engine.Player;

import java.lang.management.PlatformLoggingMXBean;

public class King extends Piece {
    public King(Player owner, Board board) {
        super(PieceType.KING, owner, board);
    }

    @Override
    public boolean checkMove(int fromX, int fromY, int toX, int toY) {
        int absDeltaX = Math.abs(fromX - toX),
                absDeltaY = Math.abs(fromY - toY);
        Playable victim = board.getPiece(toX, toY);
        if(absDeltaX > 1 || absDeltaY > 1)
        {
            System.out.println("Roi: tout doux mon Seigneur ne vous épuisez pas");
            return false;
        }
        if(victim != null && victim.belongsToPlayer(owner)){
            System.out.println("Roi: vous attaquez vos troupes mon Seigneur");
            return false;
        }
        return true;
    }
}
