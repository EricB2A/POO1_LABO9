package chess.engine;

import chess.ChessController;
import chess.ChessView;
import chess.PlayerColor;
import chess.engine.pieces.*;

public class ChessGame implements ChessController {

    private ChessView view;
    private ChessBoard chessBoard;

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
    public boolean move(int fromX, int fromY, int toX, int toY) {
        System.out.println("Moving...");
        if(chessBoard.isCellEmpty(fromX, fromY)){
            return false;
        }
        Piece toMove = chessBoard.getCellAt(fromX, fromY);
        System.out.println("Piece to move : " + toMove);

        if(isItsTurn(toMove)){
            for (Move move : toMove.getMoves(fromX, fromY)){
                if(move.equals(toX, toY)){
                    removePieceAt(fromX, fromY);
                    placePieceAt(toMove, toX, toY);

                    if(move.getSpecialMove() != null) {
                        switch (move.getSpecialMove()) {
                            case PAWN_EN_PASSANT:
                                // todo redondant voir plus bas => faire fonction ? library class Utils ?
                                int deltaPlayer = turn.getSide() == Side.TOP ? 1 : -1;
                                removePieceAt(toX, toY - deltaPlayer);
                                break;

                            case PAWN_PROMOTION:
                                System.out.println("Pion: promotion possible");
                                ChessView.UserChoice promoPiece = view.askUser("Vous êtes promu, soldat !", "Quel grade souhaitez-vous avoir ?",
                                        new Queen(turn, chessBoard), new Bishop(turn, chessBoard), new Rook(turn, chessBoard), new Knight(turn, chessBoard));
                                if (promoPiece != null) {
                                    System.out.println("Pion: soldat promu");
                                    removePieceAt(toX, toY);
                                    placePieceAt((Piece) promoPiece, toX, toY);
                                }
                                break;
                            // TODO voir si factorisable
                            case KING_LONG_CASTLED:
                                System.out.println("bouge tour");

                                Rook rRook = (Rook) chessBoard.getCellAt(toX + 2, toY);
                                removePieceAt(toX + 2, toY);
                                placePieceAt(rRook, toX - 1, toY);
                                break;

                            case KING_SHORT_CASTLED:
                                System.out.println("bouge tour");
                                Rook lRook = (Rook) chessBoard.getCellAt(toX - 1, toY);
                                removePieceAt(toX - 1, toY);
                                placePieceAt(lRook, toX + 1, toY);
                                break;

                        }
                    }

                    chessBoard.setLastMove(move);
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
        if(!chessBoard.removePieceAt(posX, posY)){
            throw new RuntimeException("Piece is introuvable");
        }
        view.removePiece(posX, posY);
    }

    private void placePieceAt(Piece piece, int posX, int posY){
        chessBoard.placePieceAt(piece, posX, posY);
        // On marque la pièce comme bougée si nécessaire
        if (SpecialFirstMove.class.isAssignableFrom(piece.getClass())) {
            SpecialFirstMove spePiece = (SpecialFirstMove) piece;
            spePiece.hasMoved();
        }
        view.putPiece(piece.getType(), piece.getOwner().getColor(), posX, posY);
    }

    @Override
    public void newGame() {
        int nCote = 8;
        ChessBoard chessBoard = new ChessBoard(nCote);

        Player player1 = new Player(PlayerColor.WHITE, Side.TOP, this);
        Player player2 = new Player(PlayerColor.BLACK, Side.BOTTOM, this);

        chessBoard.setUpTeam(player1);
        chessBoard.setUpTeam(player2);

        for(int i = 0; i < nCote; ++i){
            for(int j = 0; j < nCote; ++j){
                if(!chessBoard.isCellEmpty(i, j)){
                    Piece piece = chessBoard.getCellAt(i, j);
                    view.putPiece(piece.getType(), piece.getOwner().getColor(), i, j);
                }
            }
        }

        this.player1 = player1;
        this.player2 = player2;
        this.chessBoard = chessBoard;
        this.turn = player1; // Le blanc commence.
    }
}