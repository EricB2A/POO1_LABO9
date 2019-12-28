package chess.engine;

import chess.ChessController;
import chess.ChessView;
import chess.PlayerColor;
import chess.engine.pieces.*;

import java.awt.*;

public class Board implements ChessController {

    private int N_COTE = 8;
    private ChessView view;

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
    Playable board[][];

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
        return false;
    }

    @Override
    public void newGame() {
        System.out.println("Starting new game..");
        board = new Playable[N_COTE][N_COTE];
        Player player1 = new Player(PlayerColor.BLACK);
        Player player2 = new Player(PlayerColor.WHITE);
        System.out.println("Setting up team 1..");
        setUpTeam(player1, side.TOP);
        System.out.println("Done.");
        System.out.println("Setting up team 2..");
        setUpTeam(player2, side.BOTTOM);
        System.out.println("Done.");

        for(int i = 0; i < N_COTE; ++i){
            for(int j = 0; j < N_COTE; ++j){
                System.out.printf("X : %s, Y : %s.", i, j);
                if(board[i][j] != null){
                    System.out.printf("Has a pièece !");
                    Piece piece = (Piece) board[i][j]; // c'est plus malin que ça en a l'air.
                    view.putPiece(piece.getType(), piece.getOwner().getColor(), i, j);
                }
                System.out.printf("\n");
            }
        }
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
