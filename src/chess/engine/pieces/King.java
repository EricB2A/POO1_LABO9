package chess.engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/* ---------------------------
Laboratoire 	: 09
Fichier 		: engine/pieces/King.java
Auteur(s) 	    : Eric Bousbaa, Ilias Goujgali
Date			: 14.01.2020

But 			: Implémentation de la pièce Roi.

Remarque(s) 	: - Les Rois implémentent l'interface SpecialFirstMove afin d'effectuer
                    l'opération de long et court Castle.

Compilateur	    : javac 11.0.4
--------------------------- */
public class King extends Piece implements SpecialFirstMove {
    private boolean hasMoved = false;

    /**
     * Constructeur de la pièce.
     * @param color Couleur de la pièce.
     * @param chessBoard Echiquier.
     */
    public King(PieceColor color, ChessBoard chessBoard) {
        super(PieceType.KING, color, chessBoard);
    }

    /**
     * Retourne la liste de mouvement que possède la pièce à une position donnée.
     * cf. classe Move.
     */
    @Override
    public List<Move> getMoves(Point pos, boolean virtual) {
        List<Move> moves = new ArrayList<>();
        ChessBoard chessBoard = this.getChessBoard();
        int x = pos.x, y = pos.y;

        // Supérieur droite
        Move.addMove(pos, new Point(x + 1, y + 1), this, moves, virtual);
        // Droite
        Move.addMove(pos, new Point(x + 1, y), this, moves, virtual);
        // Inférieur droite
        Move.addMove(pos, new Point(x + 1, y - 1), this, moves, virtual);

        // Supérieur gauche
        Move.addMove(pos, new Point(x - 1, y + 1), this, moves, virtual);
        // Gauche
        Move.addMove(pos, new Point(x - 1, y), this, moves, virtual);
        // Inférieur gauche
        Move.addMove(pos, new Point(x - 1, y - 1), this, moves, virtual);

        // Supérieur
        Move.addMove(pos, new Point(x , y + 1), this, moves, virtual);
        // Inférieur
        Move.addMove(pos, new Point(x , y - 1), this, moves, virtual);

        PlayerColor opponentColor = PieceColor.getOpponentColor(getColor());

        // Gestion du castle.
        if (!virtual && !hasMoved && !chessBoard.isUnderAttack(pos, opponentColor)) {
            Rook leftRook = (Rook) chessBoard.getCellAt(new Point(x + 3, y));
            Rook rightRook = (Rook) chessBoard.getCellAt(new Point(x - 4, y));

            if (leftRook != null && leftRook.hasNotMoved() && chessBoard.isCellEmpty(new Point(x - 1, y)) && chessBoard.isCellEmpty(new Point(x - 2, y))
                    && chessBoard.isCellEmpty(new Point(x - 3, y)) && !chessBoard.isUnderAttack(new Point(pos.x - 1, pos.y), opponentColor)) {
                Move move = new Move(pos, new Point(x - 2, y), SpecialMove.KING_LONG_CASTLED);
                Move._add(this, move, moves, false);

            }
            if (rightRook != null && rightRook.hasNotMoved() && chessBoard.isCellEmpty(new Point(x + 1, y))
                    && chessBoard.isCellEmpty(new Point(x + 2, y)) && !chessBoard.isUnderAttack(new Point(pos.x + 1, pos.y), opponentColor)) {

                Move move = new Move(pos, new Point(x + 2, y), SpecialMove.KING_SHORT_CASTLED);
                Move._add(this, move, moves, false);
            }
        }

        return moves;
    }

    /**
     * cf. classe SpecialFirstMove.
     */
    public boolean hasNotMoved() {
        return !hasMoved;
    }

    /**
     * cf. classe SpecialFirstMove.
     */
    public void hasMoved() {
        this.hasMoved = true;
    }
}
