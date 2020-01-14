package chess.engine;

import java.awt.Point;
import java.util.List;

/* ---------------------------
Laboratoire 	: 09
Fichier 		: engine/Move.java
Auteur(s) 	    : Eric Bousbaa, Ilias Goujgali
Date			: 14.01.2020

But 			: La classe représente un mouvement s'effectuant sur l'échiquier, partant d'une coordonnée
                  (pointant une cellule) à une autre.

Remarque(s) 	: -

Compilateur	 : javac 11.0.4
--------------------------- */
public class Move {
    private Point from, to;
    private SpecialMove specialMove;

    /**
     * Constructeur avec position initiale et position destination.
     * @param from Point (x,y) ciblant la cellule initial.
     * @param to Point (x,y) ciblant la cellule de destination.
     */
    public Move(Point from, Point to){
        this.from = new Point(from);
        this.to = new Point(to);
    }

    /**
     * Identique que constructeur précédent, mais représentant un mouvement ayant un
     * comportement spécial.
     * La liste de mouvements spéciaux supportés est présente dans l'énum SpecialMove.
     * @param from Point (x,y) ciblant la cellule initial.
     * @param to Point (x,y) ciblant la cellule de destination.
     * @param specialMove Type décrivant le type de mouvement spécial.
     */
    public Move(Point from, Point to, SpecialMove specialMove){
        this(from, to);
        this.specialMove = specialMove;
    }

    /**
     * Compare la destination d'un mouvement avec un point.
     * @param pos Point à comparer.
     * @return Vrai s'il s'agit de la même cellule pointée,
     *         faux dans le cas contraire.
     */
    public boolean equals(Point pos){
            return to.equals(pos);
    }

    /**
     * Est-ce que le point donné est compris dans les dimensions données.
     * @param pos Point (x,y).
     * @param dimension Dimensions carrés.
     * @return Vrai si point à l'intérieur, faux si point à l'extérieur.
     */
    public static boolean inBound(Point pos, int dimension){
        return (pos.x >= 0 && pos.x < dimension) && (pos.y >= 0 && pos.y < dimension);
    }

    /**
     * Ajoute mouvement simple (càd non spécial).
     * cf. addMove (l'autre).
     */
    public static void addMove(Point from, Point to, Piece originalPiece,List<Move> moves, boolean virtual){
        addMove(from, to, originalPiece, moves, null, virtual);
    }

    /**
     * Ajoute un mouvement légal à la liste de mouvements donnés. Un mouvement part d'un point from pour atteindre un point to.
     * Un mouvement est considéré légal s'il respecte les points suivants :
     *  - Si sa destination est dans les dimensions du damier.
     *  - Si la cellule destination est vide ou contient une pièce ennemie.
     *  - Si le mouvement ne vas pas mettre en échec le roi de l'équipe de la pièce bougée.
     *  Notons cependant que le dernier test peut-être omis dans le cas où l'argument virtual est à vrai.
     *  Dans ce cas, un mouvement est légal même si ce dernier met en échec le roi ou non.
     * @param from Point duquel part le mouvement.
     * @param to Point destination du mouvement.
     * @param originalPiece Pièce effectuant le mouvement.
     * @param moves Liste de mouvements sur laquelle va s'ajouter le nouveau mouvement si ce dernier est légal.
     * @param specialMove Stocke dans le mouvement s'il s'agit d'un mouvement spécial ou non.
     * @param virtual Si vrai, la fonction ne vérifie pas si le mouvement va mettre en échec le roi (de la même équipe que la pièce
     *                à déplacer). Si faux, le mouvement est uniquement légal si le roi n'est pas en échec.
     */
    public static void addMove(Point from, Point to, Piece originalPiece, List<Move> moves, SpecialMove specialMove, boolean virtual){
        ChessBoard chessBoard = originalPiece.getChessBoard();
        if(Move.inBound(to, ChessBoard.getDimension())) {
            Piece piece = chessBoard.getCellAt(to);
            if (piece == null || piece.getColor() != originalPiece.getColor()) {
                add(originalPiece, new Move(from, to, specialMove), moves, virtual);
            }
        }
    }

    /**
     * Ajoute le mouvement donnée à la liste de mouvements, si ce dernier ne va pas mettre en échec le roi de la pièce
     * à déplacer. Ce test peut être omis si l'argument virtual est à vrai.
     * Cette méthode ne vérifie pas si la cellule cible est vide, si elle contient une pièce ennemie ou amie
     * ni si cette dernière est dans les dimensions de l'échiquier.
     * @param piece Pièce à déplacer.
     * @param move Mouvement effectué sur la pièce.
     * @param moves Liste de mouvements sur laquelle va possiblement s'ajouter le mouvement.
     * @param virtual cf addMove.
     */
    public static void add(Piece piece, Move move, List<Move> moves, boolean virtual){
        if(!virtual){
            if(!(piece.willBeCheck(move))){
                moves.add(move);
            }
        }else{
            moves.add(move);
        }
    }

    /**
     * Ajoute une liste de mouvements dans une direction donnée.
     * Si on rencontre une pièce ennemie, il est possible d'aller sur la pièce ennemie (en la capturant) mais pas derrière.
     * Si on rencontre une pièce amie, les mouvements dans cette direction s'arrêtent devant.
     * @param from Point duquel part le mouvement.
     * @param delta Axe de déplacement dans les axes, valeurs attendues : -1, 0, 1.
     *              Exemple (1,-1) nous donne la diagonale inférieure droite.
     *              Ou (1,0) nous donne la ligne horizontale doite.
     * @param originalPiece Pièce à déplacer.
     * @param moves Liste de mouvements sur laquelle vont possiblement s'ajouter des mouvements.
     * @param virtual cf addMove.
     */
    public static void addMoves(Point from, Point delta, Piece originalPiece, List<Move> moves, boolean virtual){
        ChessBoard chessBoard = originalPiece.getChessBoard();
        Point to = new Point(from.x + delta.x, from.y + delta.y);

        while(inBound(to, ChessBoard.getDimension())){
            Piece piece = chessBoard.getCellAt(to);
            if(piece == null){
                add(originalPiece, new Move(from, to), moves, virtual);
            }else if(piece.getColor() != originalPiece.getColor()){
                // Oh wow, une pièce adverse.
                add(originalPiece, new Move(from, to), moves, virtual);
                break; // On ne peut aller plus loin, quittons la boucle !
            }else{
                break;
            }
            to.x += delta.x;
            to.y += delta.y;
        }
    }

    /**
     * @return Retourne le type de mouvement spécial du mouvement.
     * S'il s'agit d'un mouvement simple, renvoi un référence null.
     */
    public SpecialMove getSpecialMove(){
        return specialMove;
    }

    /**
     * @return Retourne la position de départ d'un mouvement.
     */
    public Point getFrom(){
        return from;
    }

    /**
     * @return Retourne la position de destination d'un mouvement.
     */
    public Point getTo(){
        return to;
    }
}
