package chess.engine;

import chess.PieceType;
import chess.PlayerColor;

import java.awt.Point;
import java.util.List;

/* ---------------------------
Laboratoire 	: 09
Fichier 		: engine/Piece.java
Auteur(s) 	    : Eric Bousbaa, Ilias Goujgali
Date			: 14.01.2020

But 			: Classe abstraire représentant une pièce, qui est présente sur l'échiquier.

Remarque(s) 	: -

Compilateur	 : javac 11.0.4
--------------------------- */
public abstract class Piece  {
    private PieceType type;
    private PieceColor color;
    private ChessBoard chessBoard;

    /**
     * Constructeur de pièce.
     * @param type Type de la pièce (Exemple : Fou).
     * @param color Couleur de la pièce et ainsi du joueur possédant la pièce.
     * @param chessBoard Echiquier.
     * @throws RuntimeException Si le type, la couleur ou le chessboard est une référence null.
     */
    public Piece(PieceType type, PieceColor color, ChessBoard chessBoard) {
        if (type == null) {
            throw new RuntimeException("Piece type is vide.");
        }
        if (color == null){
            throw new RuntimeException("A piece need une couleur.");
        }
        if(chessBoard == null){
            throw new RuntimeException("Board is nul.");
        }
        this.type = type;
        this.color = color;
        this.chessBoard = chessBoard;
    }

    /**
     * Récupère la liste de mouvements légaux d'une pièce à partir d'une position.
     * @param pos Position de la pièce sur l'échiquier.
     * @param virtual Faux : vérifie si le roi de l'équipe de la pièce va être en échec.
     *                Vrai : ne vérifie pas l'échec.
     * @return Liste de mouvements légaux que possède la pièce.
     * NOTE: Chaque pièce enfant réimplémenant cette méthode possède sa propre liste de règles définissant les mouvement
     *       légaux. Il se peut que l'éditeur souligne en gris une "duplication de code". Il s'agit d'unc hoix de notre
     *       part de laisser cette "petite" duplication plutôt que de tenter de factoriser avec une méthode telle que
     *       "getDiagonals" ou "getLine" qui n'aurait pas (à notre goût) de valeur ajoutée.
     */
    public abstract List<Move> getMoves(Point pos, boolean virtual);

    /**
     * Est-ce que le mouvement donné va mettre en échec le roi de l'équie de la pièce ?
     * @param move Mouvement à tester
     * @return Vrai si le roi sera en échec. Faux dans le cas contraire.
     */
    public boolean willBeCheck(Move move){
        // Ok, essayons d'appliquer le move.
        Piece previousPiece = chessBoard.getCellAt(move.getTo());
        chessBoard.removePieceAt(move.getFrom());
        chessBoard.placePieceAt(this, move.getTo());

        // Est-ce que mon roi va prendre cher ?
        boolean isKingCheck = chessBoard.isCheck(color.getColor());

        // Rollback du move.
        chessBoard.removePieceAt(move.getTo());
        chessBoard.placePieceAt(this, move.getFrom());
        if(previousPiece != null){
            chessBoard.placePieceAt(previousPiece, move.getTo());
        }
        return isKingCheck;
    }

    /**
     * @return Retourne l'échiquier.
     */
    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    /**
     * @return Retourne la couleur de la pièce.
     */
    public PlayerColor getColor(){
        return color.getColor();
    }

    /**
     * @return Retourne le type de la pièce (Exemple : fou).
     */
    public PieceType getType() {
        return type;
    }

    /**
     * @return Retourne le côté de la pièce (BAS ou HAUT).
     */
    public Side getSide() {
        return color.getSide();
    }

}
