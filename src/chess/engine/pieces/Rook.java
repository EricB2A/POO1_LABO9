package chess.engine.pieces;

import chess.ChessView;
import chess.PieceType;
import chess.engine.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/* ---------------------------
Laboratoire 	: 09
Fichier 		: engine/pieces/Rook.java
Auteur(s) 	    : Eric Bousbaa, Ilias Goujgali
Date			: 14.01.2020

But 			: Implémentation de la pièce Tour.

Remarque(s) 	: - Implémente l'interface ChessView.UserChoice car la Tour fait parti des choix de promotion du Pion
                  - La tour implémente l'interface SpecialFirstMove afin d'effectuer
                    l'opération de long et court Castle.

Compilateur	    : javac 11.0.4
--------------------------- */
public class Rook extends Piece implements ChessView.UserChoice, SpecialFirstMove {
    private boolean hasMoved = false;

    /**
     * Constructeur de la pièce.
     * @param color Couleur de la pièce.
     * @param chessBoard Echiquier.
     */
    public Rook(PieceColor color, ChessBoard chessBoard) {
        super(PieceType.ROOK, color, chessBoard);
    }

    /**
     * Retourne la liste de mouvement que possède la pièce à une position donnée.
     * cf. classe Move.
     */
    @Override
    public List<Move> getMoves(Point pos, boolean virtual) {
        List<Move> moves = new ArrayList<>();

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
        return "Tour";
    }

    /**
     * cf. classe SpecialFirstMove.
     */
    public void hasMoved() {
        this.hasMoved = true;
    }


    /**
     * cf. classe SpecialFirstMove.
     */
    public boolean hasNotMoved(){
        return !hasMoved;
    }
}
