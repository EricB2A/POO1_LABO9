package chess.engine.pieces;

import chess.PieceType;
import chess.engine.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


/* ---------------------------
Laboratoire 	: 09
Fichier 		: engine/pieces/Pawn.java
Auteur(s) 	    : Eric Bousbaa, Ilias Goujgali
Date			: 14.01.2020

But 			: Implémentation de la pièce Pion.

Remarque(s) 	: - Le Pion implémente l'interface SpecialFirstMove afin d'effectuer le déplacement rapide de la pièce
                    ainsi que la promotion.

Compilateur	    : javac 11.0.4
--------------------------- */
public class Pawn extends Piece implements SpecialFirstMove {
    private boolean hasMoved = false;
    private int deltaPlayer = getSide() == Side.BOTTOM ? 1 : -1;

    /**
     * Constructeur de la pièce.
     * @param color Couleur de la pièce.
     * @param chessBoard Echiquier.
     */
    public Pawn(PieceColor color, ChessBoard chessBoard) {
        super(PieceType.PAWN, color, chessBoard);
    }

    /**
     * Retourne la liste de mouvement que possède la pièce à une position donnée.
     * cf. classe Move.
     */
    @Override
    public List<Move> getMoves(Point pos, boolean virtual) {
        List<Move> moves = new ArrayList<>();
        ChessBoard chessBoard = this.getChessBoard();
        SpecialMove specialMove = canBePromoted(pos.y + deltaPlayer) ? SpecialMove.PAWN_PROMOTION : null;
        int x = pos.x, y = pos.y;

        // Avance 1 case avec possible promotion.
        if(chessBoard.isCellEmpty(new Point(x, y + deltaPlayer)) ){
            // Avance de 2 cases.
            Move fastMove = new Move(pos, new Point(x, y + 2 * deltaPlayer), specialMove.PAWN_FAST_MOVE);
            if(!hasMoved && chessBoard.isCellEmpty(fastMove.getTo())){
                Move.add(this, fastMove, moves, virtual);
                //moves.add(fastMove);
            }

            Move.addMove(pos, new Point(x, y + deltaPlayer), this, moves, specialMove, virtual);
        }
        // Attaque droite.
        Move sideAttack1 = new Move(pos, new Point(x + 1, y + deltaPlayer));
        if (canAttack(sideAttack1.getTo())) {
            Move.addMove(pos, sideAttack1.getTo(), this, moves, specialMove, virtual);
        }
        // Attaque gauche.
        Move sideAttack2 = new Move(pos, new Point(x - 1, y + deltaPlayer));
        if (canAttack(sideAttack2.getTo())) {
            Move.addMove(pos, sideAttack2.getTo(), this, moves, specialMove, virtual);
        }

        // prise en passant: on regarde si le dernier mouvement correspond à un déplacement de 2 (d'un pion évidemment)
        // si c'est le cas on vérifie que la destination se trouvait à gauche ou à droite de cette piece (this) si c'est
        // le cas on ajoute la diago correspondante
        Move lastMove = chessBoard.getLastMove();
        if (lastMove != null && lastMove.getSpecialMove() == SpecialMove.PAWN_FAST_MOVE) {
            if (lastMove.getTo().y == y) {

                //on vérifie que la case destination est libre car sinon on pourrait manger 2 pièces d'un coup celle
                // sur la case où on va + en passant
                // diagonale droite
                if (lastMove.getTo().x == x + 1 && chessBoard.isCellEmpty(new Point(x + 1, y + deltaPlayer))) {
                    Move.addMove(pos, new Point(x + 1, y + deltaPlayer), this, moves, SpecialMove.PAWN_EN_PASSANT, virtual);
                }
                // diagonale gauche (else if car impossible que le dernier mouvement soit et gauche et à droite)
                else if (lastMove.getTo().x == x - 1 && chessBoard.isCellEmpty(new Point(x - 1, y + deltaPlayer))) {
                    Move.addMove(pos, new Point(x - 1, y + deltaPlayer), this, moves, SpecialMove.PAWN_EN_PASSANT, virtual);
                }
            }
        }
        return moves;
    }

    /**
     * cf. classe SpecialFirstMove.
     */
    @Override
    public boolean hasNotMoved() {
        return !hasMoved;
    }

    /**
     * cf. classe SpecialFirstMove.
     */
    public void hasMoved() {
        this.hasMoved = true;
    }

    /**
     * Est-ce que la pièce peut être promue ?
     * @param y Position ordonnée (y) de la pièce.
     * @return Vrai si peut être promue, faux dans le cas contraire.
     */
    private boolean canBePromoted(int y) {
        ChessBoard chessBoard =  this.getChessBoard();
        return y == 0 || y == chessBoard.getDimension() - 1;
    }

    /**
     * Est-ce que la pièce peut attaquer un autre pièce ?
     * @param to Position de la pièce.
     * @return Vrai si la pièce peut attaquer, faux dans le cas contraire.
     */
    private boolean canAttack(Point to) {
        ChessBoard chessBoard = this.getChessBoard();
        return Move.inBound(to, chessBoard.getDimension()) && !chessBoard.isCellEmpty(to) && chessBoard.getCellAt(to).getColor() != getColor();
    }
}
