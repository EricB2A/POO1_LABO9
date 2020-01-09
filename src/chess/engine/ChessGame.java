package chess.engine;

import chess.ChessController;
import chess.ChessView;
import chess.PlayerColor;
import chess.engine.pieces.*;

import java.awt.*;

public class ChessGame implements ChessController {

    private ChessView view;
    private ChessBoard chessBoard;

    private Player turn;
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
        if(chessBoard.isCellEmpty(fromX, fromY)){
            return false;
        }
        Piece toMove = chessBoard.getCellAt(fromX, fromY);

        if(isItsTurn(toMove)){
            for (Move move : toMove.getMoves(fromX, fromY)){
                if(move.equals(toX, toY)){
                    removePieceAt(fromX, fromY);
                    placePieceAt(toMove, toX, toY);

                    if(move.getSpecialMove() != null) {
                        switch (move.getSpecialMove()) {
                            case PAWN_EN_PASSANT:
                                // todo redondant voir plus bas => faire fonction ? library class Utils ?
                                int deltaPlayer = toMove.getSide() == Side.TOP ? 1 : -1;
                                removePieceAt(toX, toY - deltaPlayer);
                                break;

                            case PAWN_PROMOTION:
                                PieceColor pc = new PieceColor(toMove.getColor(), toMove.getSide());
                                ChessView.UserChoice promoPiece = view.askUser("Vous êtes promu, soldat !", "Quel grade souhaitez-vous avoir ?",
                                        new Queen(pc, chessBoard), new Bishop(pc, chessBoard), new Rook(pc, chessBoard), new Knight(pc, chessBoard));
                                if (promoPiece != null) {
                                    removePieceAt(toX, toY);
                                    placePieceAt((Piece) promoPiece, toX, toY);
                                }
                                break;
                            // TODO voir si factorisable
                            case KING_LONG_CASTLED:
                                Rook rRook = (Rook) chessBoard.getCellAt(toX + 2, toY);
                                removePieceAt(toX + 2, toY);
                                placePieceAt(rRook, toX - 1, toY);
                                break;

                            case KING_SHORT_CASTLED:
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
        return piece.getColor() == turn.getColor();
    }

    private void endTurn(){
        Color color = turn == player1 ? Color.BLACK : Color.WHITE;
        view.setCurrentPlayerColor(color);
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
        view.putPiece(piece.getType(), piece.getColor(), posX, posY);
    }

    @Override
    public void newGame() {
        int nCote = 8;
        ChessBoard chessBoard = new ChessBoard(nCote);

        Player player1 = new Player(PlayerColor.WHITE);
        Player player2 = new Player(PlayerColor.BLACK);

        chessBoard.setUpTeam(player1, Side.TOP);
        chessBoard.setUpTeam(player2, Side.BOTTOM);

        for(int i = 0; i < nCote; ++i){
            for(int j = 0; j < nCote; ++j){
                if(!chessBoard.isCellEmpty(i, j)){
                    Piece piece = chessBoard.getCellAt(i, j);
                    view.putPiece(piece.getType(), piece.getColor(), i, j);
                }
            }
        }

        this.player1 = player1;
        this.player2 = player2;
        this.chessBoard = chessBoard;
        this.turn = player1; // Le blanc commence.
    }
}