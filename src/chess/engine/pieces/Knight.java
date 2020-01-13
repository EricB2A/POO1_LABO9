package chess.engine.pieces;

import chess.ChessView;
import chess.PieceType;
import chess.engine.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/* ---------------------------
Laboratoire 	: 09
Fichier 		: engine/pieces/Knight.java
Auteur(s) 	    : Eric Bousbaa, Ilias Goujgali
Date			: 14.01.2020

But 			: Implémentation de la pièce Cavalier.

Remarque(s) 	: implémente l'interface ChessView.UserChoice car il fait partie des choix de promotion du pion

Compilateur	    : javac 11.0.4
--------------------------- */
public class Knight extends Piece implements ChessView.UserChoice {

    /**
     * Constructeur de la pièce.
     * @param color Couleur de la pièce.
     * @param chessBoard Echiquier.
     */
    public Knight(PieceColor color, ChessBoard chessBoard) {
        super(PieceType.KNIGHT, color, chessBoard);
    }

    /**
     * Retourne la liste de mouvement que possède la pièce à une position donnée.
     * cf. classe Move.
     */
    @Override
    public List<Move> getMoves(Point pos, boolean virtual) {
        List<Move> moves = new ArrayList<>();
        int x = pos.x, y = pos.y;
        // Déplacements en L.
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
