package chess.engine;

import chess.ChessController;
import chess.ChessView;
import chess.PlayerColor;
import chess.engine.pieces.*;

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

    private Player player1 = new Player(PlayerColor.BLACK);
    private Player player2 = new Player(PlayerColor.WHITE);
    private Player playingPlayer = player2;

    @Override
    public void start(ChessView view) {
        if(view == null){
            throw new RuntimeException("Wtf is this view.");
        }
        view.startView();
        this.view = view;
    }
    public boolean isCellFree(int x, int y)
    {
        return board[x][y] == null;
    }

    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {


        Playable selectedPiece = board[fromX][fromY];
        Playable destinationCell = board[toX][toY];

        if(selectedPiece == null){
            System.out.println("Pas de pièces sur cette case");
            return false;
        }
        // on vérifie que la pièce appartient au joueur
        if (!selectedPiece.belongsToPlayer(playingPlayer)) {
            System.out.println("La pièces ne vous appartient pas");
            return false ;
        }

        if(destinationCell != null && destinationCell.belongsToPlayer(playingPlayer)){
            System.out.println("Mauvaise cible");
            return false;
        }

        // on vérifie que le mouvement est valide
        if(!selectedPiece.checkMove(fromX, fromY, toX, toY)){
            System.out.println("Mouvement invalide" );
            return false;
        }
        if(destinationCell != null)
        {
            System.out.println("SUUUUUS");
            view.removePiece(toX, toY);
        }

        view.removePiece(fromX,fromY);
        view.putPiece(((Piece) selectedPiece).getType(), ((Piece) selectedPiece).getOwner().getColor(),toX, toY);

        board[toX][toY] = board[fromX][fromY];
        board[fromX][fromY] = null;
        playingPlayer = player1 == playingPlayer ? player2 : player1;

        return true;
    }

    @Override
    public void newGame() {
        System.out.println("Starting new game..");
        board = new Playable[N_COTE][N_COTE];

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
    public Playable getPiece(int x, int y) {
        return board[x][y];
    }
    private void setUpTeam(Player player, side side){

        // Nous permet de décaler les pions d'une rangée vers le centre de l'échiquier.
        int deltaPlayer = side == side.TOP ? 1 : -1;
        // Pawn
        for(int i = 0; i < N_COTE; ++i){
            board[i][side.position + deltaPlayer] = new Pawn(player, this);
        }

        // Rook
        board[0][side.position] = new Rook(player, this);
        board[N_COTE - 1][side.position] = new Rook(player, this);

        // Knight
        board[1][side.position] = new Knight(player, this);
        board[N_COTE - 2][side.position] = new Knight(player, this);

        // Bishop
        board[2][side.position] = new Bishop(player, this);
        board[N_COTE - 3][side.position] = new Bishop(player, this);

        // King
        board[3][side.position] = new King(player, this);

        // Queen
        board[4][side.position] = new Queen(player, this);
    }
}
