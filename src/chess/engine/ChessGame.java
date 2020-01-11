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
        Point from = new Point(fromX, fromY);
        Point to = new Point(toX ,toY);
        if(chessBoard.isCellEmpty(from)){
            return false;
        }
        Piece toMove = chessBoard.getCellAt(from);

        if(isItsTurn(toMove)){
            for (Move move : toMove.getMoves(from, false)){
                if(move.equals(to)){
                    removePieceAt(from);
                    placePieceAt(toMove, to);

                    if(move.getSpecialMove() != null) {
                        switch (move.getSpecialMove()) {
                            case PAWN_EN_PASSANT:
                                // todo redondant voir plus bas => faire fonction ? library class Utils ?
                                int deltaPlayer = toMove.getSide() == Side.BOTTOM ? 1 : -1;
                                removePieceAt(new Point(toX, toY - deltaPlayer));
                                break;

                            case PAWN_PROMOTION:
                                PieceColor pc = new PieceColor(toMove.getColor(), toMove.getSide());
                                ChessView.UserChoice promoPiece = view.askUser("Vous êtes promu, soldat !", "Quel grade souhaitez-vous avoir ?",
                                        new Queen(pc, chessBoard), new Bishop(pc, chessBoard), new Rook(pc, chessBoard), new Knight(pc, chessBoard));
                                if (promoPiece != null) {
                                    removePieceAt(to);
                                    placePieceAt((Piece) promoPiece, to);
                                }
                                break;
                            // TODO voir si factorisable
                            case KING_LONG_CASTLED:
                                Rook rRook = (Rook) chessBoard.getCellAt(new Point(toX + 2, toY));
                                removePieceAt(new Point(toX + 2, toY));
                                placePieceAt(rRook, new Point(toX - 1, toY));
                                break;

                            case KING_SHORT_CASTLED:
                                Rook lRook = (Rook) chessBoard.getCellAt(new Point(toX - 1, toY));
                                removePieceAt(new Point(toX - 1, toY));
                                placePieceAt(lRook, new Point(toX + 1, toY));
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

    private void removePieceAt(Point pos){
        if(!chessBoard.removePieceAt(pos)){
            throw new RuntimeException("Piece is introuvable");
        }
        view.removePiece(pos.x, pos.y);
    }

    private void placePieceAt(Piece piece, Point pos){
        chessBoard.placePieceAt(piece, pos);
        // On marque la pièce comme bougée si nécessaire
        if (SpecialFirstMove.class.isAssignableFrom(piece.getClass())) {
            SpecialFirstMove spePiece = (SpecialFirstMove) piece;
            spePiece.hasMoved();
        }
        view.putPiece(piece.getType(), piece.getColor(), pos.x, pos.y);
    }

    @Override
    public void newGame() {
        int nCote = 8;
        ChessBoard chessBoard = new ChessBoard(nCote);

        Player player1 = new Player(PlayerColor.WHITE);
        Player player2 = new Player(PlayerColor.BLACK);

        chessBoard.setUpTeam(player1, Side.BOTTOM);
        chessBoard.setUpTeam(player2, Side.TOP);

        for(int x = 0; x < nCote; ++x){
            for(int y = 0; y < nCote; ++y){
                Point pos = new Point(x, y);
                if(!chessBoard.isCellEmpty(pos)){
                    Piece piece = chessBoard.getCellAt(pos);
                    view.putPiece(piece.getType(), piece.getColor(), x, y);
                }
            }
        }

        this.player1 = player1;
        this.player2 = player2;
        this.chessBoard = chessBoard;
        this.turn = player1; // Le blanc commence.
    }
}