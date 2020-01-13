package chess.engine.pieces;

import chess.ChessView;
import chess.PieceType;
import chess.engine.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/* ---------------------------
Laboratoire 	: 09
Fichier 		: engine/pieces/Queen.java
Auteur(s) 	    : Eric Bousbaa, Ilias Goujgali
Date			: 14.01.2020

But 			: Implémentation de la pièce Reine.

Remarque(s) 	: - La Reine implémente l'interface ChessView.UserChoice car elle fait parti des choix de promotion du Pion.

Compilateur	    : javac 11.0.4
--------------------------- */

public class Queen extends Piece implements ChessView.UserChoice {
    /**
     * Constructeur de la pièce.
     * @param color Couleur de la pièce.
     * @param chessBoard Echiquier.
     */
    public Queen(PieceColor color, ChessBoard chessBoard) {
        super(PieceType.QUEEN, color, chessBoard);
    }

    /**
     * Retourne la liste de mouvements que possède la pièce à une position donnée.
     * cf. classe Move.
     */
    public List<Move> getMoves(Point pos, boolean virtual) {
        List<Move> moves = new ArrayList<>();

        // Diagonale supérieur droite
        Move.addMoves(pos, new Point(1, 1), this, moves, virtual);
        // Diagonale droite inférieur.
        Move.addMoves(pos, new Point(1, -1), this, moves, virtual);
        // Diagonale gauche supérieur.
        Move.addMoves(pos, new Point(-1, 1), this, moves, virtual);
        // Diagonale gauche inférieur.
        Move.addMoves(pos, new Point(-1, -1), this, moves, virtual);
        // Ligne haut.
        Move.addMoves(pos, new Point(0, 1), this, moves, virtual);
        // Ligne bas.
        Move.addMoves(pos, new Point(0, -1), this, moves, virtual);
        // Ligne droite.
        Move.addMoves(pos, new Point(1, 0), this, moves, virtual);
        // Ligne gauche.
        Move.addMoves(pos, new Point(-1, 0), this, moves, virtual);

        return moves;
    }

    @Override
    public String textValue() {
        return "Reine";
    }
}
