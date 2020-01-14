package chess.engine;

import chess.PlayerColor;
import chess.engine.pieces.*;

import java.awt.Point;

/* ---------------------------
Laboratoire 	: 09
Fichier 		: engine/ChessBoard.java
Auteur(s) 	    : Eric Bousbaa, Ilias Goujgali
Date			: 14.01.2020

But 			: Classe représentant l'échiquier sur lequel se déroule une partie. L'échiquier est une grille carrée de N_COTE
                  cases de côté, dont chaque cellule peut contenir une pièce.

Remarque(s) 	: - La cellule située tout en bas à gauche de l'échiquier est accessible via le point (0,0).

Compilateur	    : javac 11.0.4
--------------------------- */
public class ChessBoard {
    private static final int N_COTE = 8;
    private Piece[][] board;
    private Move lastMove;
    private ChessGame chessGame;
    private Point whiteKing;
    private Point blackKing;
    private final static String CHECK_TEXT_MESSAGE = "Echec";

    /**
     * Constructeur de l'échiquier.
     * @param chessGame référence sur l'instance d'une partie.
     * @throws RuntimeException Est lancé quand on construit l'object sans chessGame
     */
    protected ChessBoard(ChessGame chessGame){
        if(chessGame == null){
            throw new RuntimeException("We need une partie to play.");
        }
        this.board = new Piece[N_COTE][N_COTE];
        this.chessGame = chessGame;
    }

    /**
     * @param playerColor Couleur de l'équipe pouvant être en échec.
     * @return Vrai si l'équipe de la couleur donnée est en échec,
     *          faux dans le cas contraire.
     */
    public boolean isCheck(PlayerColor playerColor){
        if(playerColor == PlayerColor.WHITE){
            return isUnderAttack(whiteKing, PlayerColor.BLACK);
        }else{
            return isUnderAttack(blackKing, PlayerColor.WHITE);
        }
    }

    /**
     * Est-ce que la pièce donnée peut être attaquée par l'équipe adverse ?
     * @param piece Position de la pièce attaquée.
     * @param opponentColor Couleur de l'équipe adverse (celle qui attaque pièce).
     * @return Vrai si la pièce peut être attaquée, faux dans le cas contraire.
     */
    public boolean isUnderAttack(Point piece, PlayerColor opponentColor){
        for(int y = 0; y < N_COTE; ++y){
            for(int x = 0; x < N_COTE; ++x){
                Piece possibleOpponent = board[y][x];
                // Est-ce qu'il s'agit d'un adversaire ?
                if(possibleOpponent != null && possibleOpponent.getColor() == opponentColor){
                    // Oh je vois.. mais est-ce qu'il peut attaquer pièce ?
                    for(Move move : possibleOpponent.getMoves(new Point(x, y), true)){
                        if(move.equals(piece)){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Enlève la pièce d'une cellule donnée de l'échiquier.
     * La cellule va contenir une référence vide (null).
     * @param pos Emplacement (x,y) la cellule à vider.
     * @return Vrai si une pièce a été enlevée de la cellule,
     *          faux si la cellule était vide.
     */
    public boolean removePieceAt(Point pos){
        if(board[pos.y][pos.x] == null){
            return false;
        }
        board[pos.y][pos.x] = null;
        return true;
    }

    /**
     * Place la pièce sur l'échiquier. Ecrase le contenu de la cellule.
     * NOTE: La fonction peut également envoyer une message d'affichage à l'instance de jeu
     *       (chessGame) si un joueur est en échec suite aux nouvelles disposition des pièces.
     *       de la pièce.
     * @param piece Pièce placer.
     * @param pos Emplacement (x,y) de la cellule cible.
     */
    public void placePieceAt(Piece piece, Point pos){
        board[pos.y][pos.x] = piece;

        // Gardons en référence la position du Roi.
        if(piece.getClass() == King.class){
            if(piece.getColor() == PlayerColor.WHITE){
                whiteKing = new Point(pos);
            }else{
                blackKing = new Point(pos);
            }
        }

        // Est-ce que le nouveau positionnement des pièces met le roi adverse (celui qui n'a pas joué) en échec ?
        if(isCheck(piece.getColor() == PlayerColor.WHITE ? PlayerColor.BLACK : PlayerColor.WHITE)){
            chessGame.displayMessage(CHECK_TEXT_MESSAGE);
        }else{
            chessGame.displayMessage("");
        }
    }

    /**
     * Est-ce que la cellule ne contient aucune pièce (référence null) ?
     * @param pos Coordonnées de la cellule cible (x,y).
     * @return Vrai si la cellule contient une pièce, faux dans le
     * cas contraire.
     */
    public boolean isCellEmpty(Point pos){
        if(Move.inBound(pos, N_COTE)){
            return board[pos.y][pos.x] == null;
        }
        return false;
    }

    /**
     * @return Retourne les dimensions de l'échiquier (un côté).
     *         Pour rappel, l'échiquier est une grille carrée de N_COTE * N_COTE cellules.
     */
    public static int getDimension(){
        return N_COTE;
    }

    /**
     * Retourne le contenu d'une cellule cible.
     * @param pos Position de la cellule.
     * @return Contenu de la cellule. Peut contenir une pièce ou une référence null.
     */
    public Piece getCellAt(Point pos){
        return board[pos.y][pos.x];
    }

    /**
     * @return Retourne le dernier mouvement effectué sur l'échiquier.
     */
    public Move getLastMove(){
        return lastMove;
    }

    /**
     * Stocke le dernier mouvement donné.
     * @param move Mouvement à stocker.
     * NOTE: Si vous vous demandez pourquoi ne pas stocker une Point (une coordonnée)
     *       à la place (ce qui nous permettrait de ne pas avoir de méthode set), il faut
     *       savoir que les mouvements nous permettent de savoir si un coup spécial a été
     *       effectué, ce qui peut s'avérer utile.
     *       cf. classe Move pour les mouvements spéciaux.
     */
    protected void setLastMove(Move move){
        this.lastMove = move;
    }

    /**
     * Configure l'échiquier avec une configuration standard de jeu d'échec.
     * @param player
     * @param side
     */
    protected void setUpTeam(Player player, Side side){
        PieceColor pieceColor = new PieceColor(player.getColor(), side);

        // Nous permet de décaler les pions d'une rangée vers le centre de l'échiquier.
        int deltaPlayer = side == Side.BOTTOM ? 1 : -1;

        // Pion
        for(int i = 0; i < N_COTE; ++i){
            board[side.position + deltaPlayer][i] = new Pawn(pieceColor, this);
        }

        // Tour
        board[side.position][0] = new Rook(pieceColor, this);
        board[side.position][N_COTE - 1] = new Rook(pieceColor, this);

        // Chevalier
        board[side.position][1] = new Knight(pieceColor, this);
        board[side.position][N_COTE - 2] = new Knight(pieceColor, this);

        // Fou
        board[side.position][2] = new Bishop(pieceColor, this);
        board[side.position][N_COTE - 3] = new Bishop(pieceColor, this);

        // Roi
        board[side.position][4] = new King(pieceColor, this);

        // Gardons une référence sur l'emplacement initiale des Rois.
        if(player.getColor() == PlayerColor.WHITE){
            whiteKing = new Point(4, side.position);
        }else{
            blackKing = new Point(4, side.position);
        }
        
        // Reine
        board[side.position][3] = new Queen(pieceColor, this);
    }
}
