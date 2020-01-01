package chess.engine;

import chess.ChessController;
import chess.ChessView;
import chess.PlayerColor;
import chess.engine.pieces.*;

public class Board implements ChessController {

    private int N_COTE = 8;
    private ChessView view;
    private Playable board[][];
    private Player turn;
    private Player player1;
    private Player player2;

    //TODO: très C++ comme code.. à voir si c'est une solution viable.
    private enum side {
        TOP(0), // Première rangée. On commence l'indexation à 0 car nous ne sommes pas de animaux.
        //TODO: trouver un moyen pour pouvoir utiliser "N_COTE - 1".
        BOTTOM(7); // Dernière rangée.

        side(int i) {
            this.position = i;
        }
        public final int position;
    }

    @Override
    public void start(ChessView view) {
        if(view == null){
            throw new RuntimeException("Wtf is this view.");
        }
        view.startView();
        this.view = view;
    }

    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {
        if(isCellEmpty(fromX, fromY)){
            return false;
        }

        if(isCellEmpty(toX, toY)){
            Piece toMove = (Piece) board[fromX][fromY];
            if(isItsTurn(toMove)){
                removePieceAt(fromX, fromY);
                placePieceAt(toMove, toX, toY);
                endTurn();
            }
        }

        System.out.println("turn sis "+ turn);
        return true;
    }

    private boolean isItsTurn(Piece piece){
        return piece.getOwner() == turn;
    }

    private void endTurn(){
        turn = turn == player1 ? player2 : player1;
        System.out.println("Ending turn..");
        System.out.println("Turn is : " + turn);
    }
    
    private void removePieceAt(int posX, int posY){
        if(isCellEmpty(posX, posY)){
            throw new RuntimeException("Piece is introuvable");
        }
        board[posX][posY] = null;
        view.removePiece(posX, posY);
    }

    private void placePieceAt(Piece piece, int posX, int posY){
        board[posX][posY] = piece;
        view.putPiece(piece.getType(), piece.getOwner().getColor(), posX, posY);
    }

    private boolean isCellEmpty(int posX, int posY){
        return board[posX][posY] == null;
    }

    @Override
    public void newGame() {
        board = new Playable[N_COTE][N_COTE];
        Player player1 = new Player(PlayerColor.BLACK);
        Player player2 = new Player(PlayerColor.WHITE);
        setUpTeam(player1, side.TOP);
        setUpTeam(player2, side.BOTTOM);

        for(int i = 0; i < N_COTE; ++i){
            for(int j = 0; j < N_COTE; ++j){
                if(board[i][j] != null){
                    Piece piece = (Piece) board[i][j];
                    view.putPiece(piece.getType(), piece.getOwner().getColor(), i, j);
                }
            }
        }

        this.player1 = player1;
        this.player2 = player2;
        this.turn = player2; // Le blanc commence.
    }

    private void setUpTeam(Player player, side side){
        // Nous permet de décaler les pions d'une rangée vers le centre de l'échiquier.
        int deltaPlayer = side == side.TOP ? 1 : -1;

        // Pawn
        for(int i = 0; i < N_COTE; ++i){
            board[i][side.position + deltaPlayer] = new Pawn(player);
        }

        // Rook
        board[0][side.position] = new Rook(player);
        board[N_COTE - 1][side.position] = new Rook(player);

        // Knight
        board[1][side.position] = new Knight(player);
        board[N_COTE - 2][side.position] = new Knight(player);

        // Bishop
        board[2][side.position] = new Bishop(player);
        board[N_COTE - 3][side.position] = new Bishop(player);

        // King
        board[3][side.position] = new King(player);

        // Queen
        board[4][side.position] = new Queen(player);
    }
}
