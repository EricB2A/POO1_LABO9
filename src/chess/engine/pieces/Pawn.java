package chess.engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.Board;
import chess.engine.Piece;
import chess.engine.Playable;
import chess.engine.Player;

public class Pawn extends Piece {
    private boolean neverMove = true;

    public Pawn(Player owner, Board board) {
        super(PieceType.PAWN, owner, board);
    }

    @Override
    public boolean checkMove(int fromX, int fromY, int toX, int toY) {

        int absDeltaX = Math.abs(toX - fromX),
                deltaY = toY - fromY,
                absDeltaY = Math.abs(deltaY);
        Playable victim = board.getPiece(toX, toY);


        // les blancs décrémentent
        // les noirs incrémentent
        if (owner.getColor() == PlayerColor.BLACK && deltaY < 0 || owner.getColor() == PlayerColor.WHITE && deltaY > 0) {
            System.out.println("Pion: En avant soldat !");
            return false;
        }
        // On vérifie que s'il y a un mouvement latéral il s'agit d'une attaque
        if (absDeltaX != 0 && absDeltaX != absDeltaY || absDeltaX == 0 && absDeltaY > 0
                && !board.isCellFree(toX, toY)) {
            System.out.println("Pion: Déplacement impossible");
            return false;
        }
        // on vérifie s'il y a une piece sur la case et si elle appartient au joueur
        if (absDeltaX == 1  && (victim == null || victim.belongsToPlayer(owner))) {

            System.out.println("Pion: attaque imposible Sir !");
            return false;
        }

        //Si le pion se déplace de 2 on vérifie qu'il s'agisse de son premier mouvement et qu'il n'aille que en avant.
        if (absDeltaY > 2 || absDeltaY == 2 && ( absDeltaX != 0 || !neverMove)) {
            System.out.println("Pion: Pas si vite soldtat !");
            return false;
        }

        neverMove = false;
        return true;
    }
}
