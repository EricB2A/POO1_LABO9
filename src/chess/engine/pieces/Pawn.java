package chess.engine.pieces;

import chess.PieceType;
import chess.engine.*;

import java.util.ArrayList;
import java.util.List;
/*TODO: factoriser la promotion(3x copier/coller)
* */
public class Pawn extends Piece {
    private boolean hasMoved = false;
    private int deltaPlayer = getOwner().getSide() == Side.TOP ? 1 : -1;
    public Pawn(Player owner) {
        super(PieceType.PAWN, owner);
    }

    @Override
    public List<Move> getMoves(int x, int y) {

        List<Move> moves = new ArrayList<Move>();
        Board board = getOwner().getBoard();

        // Avancer 2 coups
        if(!hasMoved){
            // on vérife que la case est libre en prenant soit de vérifier que aucune piece ne se situe en elle-même
            // et sa case de destination
            if(board.isCellFree(x, y +2 * deltaPlayer ) && board.isCellFree(x, y +  deltaPlayer)){
                moves.add(new Move(x, y, x, y + 2 * deltaPlayer, SpecialMove.PAWN_FAST_MOVE));
            }
        }

        // avance un coup
        if(board.isCellFree(x, y +  deltaPlayer)){
            if(canBePromoted(y)){
                moves.add(new Move(x, y, x, y +  deltaPlayer, SpecialMove.PAWN_PROMOTION));

            }else{
                moves.add(new Move(x, y, x, y +  deltaPlayer));

            }
        }

        // attaque droite & gauche
        if(! board.isCellFree(x + 1 , y + deltaPlayer) && ((Piece) board.getPiece(x + 1 , y + deltaPlayer)).getOwner() != getOwner()){
            if(canBePromoted(y)) {
                moves.add(new Move(x,y,x + 1, y + deltaPlayer,SpecialMove.PAWN_PROMOTION));

            }else{
                moves.add(new Move(x,y,x + 1, y + deltaPlayer));
            }

        }

        if(! board.isCellFree(x - 1 , y + deltaPlayer) && ((Piece) board.getPiece(x - 1 , y + deltaPlayer)).getOwner() != getOwner()){

            if(canBePromoted(y)) {
                moves.add(new Move(x,y, x-1, y + deltaPlayer,SpecialMove.PAWN_PROMOTION));

            }else{
                moves.add(new Move(x,y, x-1, y + deltaPlayer));

            }

        }

        // prise en passant: on regarde si le dernier mouvement correspond à un déplacement de 2 (d'un pion évidemment)
        // si c'est le cas on vérifie que la destination se trouvait à gauche ou à droite de cette piece (this) si c'est
        // le cas on ajoute la diago correspondante
        Move lastMove = board.getLastMove();
        if(lastMove != null && lastMove.getSpecialMove() == SpecialMove.PAWN_FAST_MOVE){
            if(lastMove.getToY() == y)
            {
                // on ajoute la diagonale droite
                if(lastMove.getToX() == x + 1 && board.isCellFree(x + 1, y + deltaPlayer)  ) {

                    moves.add(new Move(x, y,x + 1, y + deltaPlayer, SpecialMove.PAWN_EN_PASSANT));
                }
                // diagonale gauche
                else if(lastMove.getToX() == x - 1 && board.isCellFree(x - 1, y + deltaPlayer)){
                    moves.add(new Move(x, y,x - 1, y + deltaPlayer, SpecialMove.PAWN_EN_PASSANT));
                }
            }
        }
        return moves;
    }
    public void hasMoved() {
        this.hasMoved = true;
    }

    private boolean canBePromoted(int y){
        Board board = getOwner().getBoard();
        return y + deltaPlayer == 0 || y + deltaPlayer == board.getDimension() - 1;
    }
}
