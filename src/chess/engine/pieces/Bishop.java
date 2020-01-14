package chess.engine.pieces;

import chess.ChessView;
import chess.PieceType;
import chess.engine.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/* ---------------------------
Laboratoire 	: 09
Fichier 		: engine/pieces/Bishop.java
Auteur(s) 	    : Eric Bousbaa, Ilias Goujgali
Date			: 14.01.2020

But 			: Implémentation de la pièce Fou.

Remarque(s) 	: - Implémente l'interface ChessView.UserChoice car le Fou fait parti des choix de promotion du Pion.

Compilateur	    : javac 11.0.4
--------------------------- */
public class Bishop extends Piece implements ChessView.UserChoice {
    /**
     * Constructeur de la pièce.
     * @param color Couleur de la pièce.
     * @param chessBoard Echiquier.
     */
    public Bishop(PieceColor color, ChessBoard chessBoard) {
        super(PieceType.BISHOP, color, chessBoard);
    }

    /**
     * Retourne la liste de mouvement que possède la pièce à une position donnée.
     * cf. classe Move.
     */
    @Override
    public List<Move> getMoves(Point pos, boolean virtual) {
        List<Move> moves = new ArrayList<>();

        //Diagonale droite supérieure.
        Move.addMoves(pos, new Point(1,1), this, moves, virtual);
        // Diagonale droite inférieure.
        Move.addMoves(pos, new Point(1, -1), this, moves, virtual);
        // Diagonale gauche suppérieure.
        Move.addMoves(pos, new Point(-1, 1), this, moves, virtual);
        // Diagonale gauche inférieure.
        Move.addMoves(pos, new Point(-1, -1), this, moves, virtual);

        return moves;
    }
    
    @Override
    public String textValue() {
        return "Fou";
    }
}
