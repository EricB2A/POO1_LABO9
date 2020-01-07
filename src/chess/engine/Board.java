package chess.engine;

import chess.ChessController;
import chess.ChessView;
import chess.PlayerColor;
import chess.engine.pieces.*;

public class Board implements ChessController {

    public int getDimension() {
        return N_COTE;
    }

    private int N_COTE = 8;
    private ChessView view;

    public Playable[][] getBoard() {
        return board;
    }

    private Playable board[][];
    private Player turn; //NOTE: on peut faire mieux. A voir.
    private Player player1;
    private Player player2;

    @Override
    public void start(ChessView view) {
        if(view == null){
            throw new RuntimeException("Wtf is this view.");
        }
        view.startView();
        this.view = view;
    }

    @Override
    //TODO: Parfois le déplacement ne fonctionne pas. Sombre, très sombre.
    public boolean move(int fromX, int fromY, int toX, int toY) {
        if(isCellEmpty(fromX, fromY)){
            return false;
        }

        Piece toMove = (Piece) board[fromX][fromY];
        if(isItsTurn(toMove)){
            for (Move move : toMove.getMoves(fromX, fromY)){
                if(move.equals(toX, toY)){
                    removePieceAt(fromX, fromY);
                    placePieceAt(toMove, toX, toY);
                    endTurn();
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isItsTurn(Piece piece){
        return piece.getOwner() == turn;
    }

    private void endTurn(){
        turn = turn == player1 ? player2 : player1;
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
        if(piece.getClass() == Pawn.class){
            ((Pawn) piece).hasMoved();
        }
    }

    private boolean isCellEmpty(int posX, int posY){
        return board[posX][posY] == null;
    }

    @Override
    public void newGame() {
        board = new Playable[N_COTE][N_COTE];
        Player player1 = new Player(PlayerColor.WHITE, Side.TOP, this);
        Player player2 = new Player(PlayerColor.BLACK, Side.BOTTOM, this);
        setUpTeam(player1);
        setUpTeam(player2);

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
        this.turn = player1; // Le blanc commence.
    }

    private void setUpTeam(Player player){

        // Par soucis de lisibilité.
        Side side = player.getSide();

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
    public boolean isCellFree(int x, int y ) {
        return board[x][y] == null;
    }
}
