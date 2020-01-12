package chess.engine;

import chess.ChessController;
import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import chess.engine.pieces.*;

import java.awt.Point;
import java.awt.Color;

public class ChessGame implements ChessController {

    private ChessView view;
    private ChessBoard chessBoard;

    private Player turn;
    private Player player1;
    private Player player2;

    private Point whiteKing = new Point(3, 0);
    private Point blackKing = new Point(3,7);

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
        boolean hasPlayed = false;

        if(isItsTurn(toMove)){
            for (Move move : toMove.getMoves(from)){
                if(move.equals(to) && !isValidMove(from, to, toMove, move, turn) ){
                    movePiece(from, to, toMove, move);
                    chessBoard.setLastMove(move);
                    endTurn();
                    hasPlayed = true;
                }

            }
            if(isCheck(turn)){
                view.displayMessage("ECHEC TCHOIN !");
            }else{
                // autrement ne s'actualise par correctement....
                view.displayMessage("");
            }
        }
        return hasPlayed;
    }

    private void rollback(Point from, Point to, Piece toMove, Move move){
        // voir quand promotion car il ne faut pas replacer le pion promu mais le pion déchu :P

        removePieceAt(to);
        placePieceAt(toMove, from);

        if(toMove.getType() == PieceType.KING){
            if(toMove.getColor() == PlayerColor.WHITE){
                whiteKing.setLocation(from);
            }else{
                blackKing.setLocation(from);
            }
        }

        PlayerColor color = toMove.getColor() == PlayerColor.BLACK ? PlayerColor.WHITE: PlayerColor.BLACK;
        PlayerColor opponentColor = color == PlayerColor.BLACK ? PlayerColor.WHITE: PlayerColor.BLACK;

        Side side = toMove.getSide();
        Side opponentSide = side == Side.TOP ? Side.BOTTOM : Side.TOP;

        if(move.getSpecialMove() != null) {
            switch (move.getSpecialMove()) {
                case PAWN_EN_PASSANT:
                    // on remet le pion capturé
                    placePieceAt(new Pawn(new PieceColor(opponentColor, opponentSide), chessBoard), to);
                    break;

                case PAWN_PROMOTION:
                    // on retrograde le pion
                    removePieceAt(to);
                    placePieceAt(new Pawn(new PieceColor(color, side), chessBoard), from);
                    break;
                case KING_LONG_CASTLED:
                    // todo replacer tour
                    break;

                case KING_SHORT_CASTLED:
                    // todo replacer tour
                    break;
            }
        }
    }
    private void movePiece(Point from, Point to, Piece toMove, Move move){

        removePieceAt(from);
        placePieceAt(toMove, to);
        if(toMove.getType() == PieceType.KING){
            if(toMove.getColor() == PlayerColor.WHITE){
                whiteKing.setLocation(to);
            }else{
                blackKing.setLocation(to);
            }
        }

        if(move.getSpecialMove() != null) {
            switch (move.getSpecialMove()) {
                case PAWN_EN_PASSANT:
                    // todo redondant voir plus bas => faire fonction ? library class Utils ?
                    int deltaPlayer = toMove.getSide() == Side.TOP ? 1 : -1;
                    removePieceAt(new Point(to.x, to.y - deltaPlayer));
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
                    Rook rRook = (Rook) chessBoard.getCellAt(new Point(to.x + 2, to.y));
                    removePieceAt(new Point(to.x + 2, to.y));
                    placePieceAt(rRook, new Point(to.x - 1, to.y));
                    break;

                case KING_SHORT_CASTLED:
                    Rook lRook = (Rook) chessBoard.getCellAt(new Point(to.x - 1, to.y));
                    removePieceAt(new Point(to.x - 1, to.y));
                    placePieceAt(lRook, new Point(to.x + 1, to.y));
                    break;
            }
        }
    }

    private Player getOpponent(Player player){
        return player == player1 ? player2:player1;
    }
    // todo nom entre isCheck et isValidMove ?
    private boolean isCheck(Player playerToCheck){

        Player opponent = getOpponent(playerToCheck);
        Point kingPosition = opponent.getColor() == PlayerColor.WHITE ? blackKing: whiteKing;

        for(int i = 0 ; i < chessBoard.getDimension(); ++i){
            for(int j = 0 ; j < chessBoard.getDimension();++j){
                Piece piece =  chessBoard.getCellAt(i,j);
                if(piece!= null && piece.getColor() == opponent.getColor()){

                    for(Move possibleMove : piece.getMoves(new Point(i, j))){
                        // todo : faut-il vraiment vérifier que le possibleMove ne mette pas en echec de celui qui le fait ?
                        if(possibleMove.getTo().equals(kingPosition)){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    private boolean isValidMove(Point from, Point to, Piece toMove, Move move, Player playerToCheck) {
        /*
        * TODO: à vérifier :
        *  - qu'il soit impossible de manger un roi dans TOUS les cas mais que les messages echecs soit bien présent
        *  - qu'il n'y ait pas d'effet de bord avec le rollback
        *  - qu'il n'y ait rien d'inutile
        * */
        Piece attackedPiece = chessBoard.getCellAt(move.getTo());

        // On fait le mouvement, on vérifie si ça met le roi en echec puis on revient en arrière
        movePiece(from, to, toMove, move);
        boolean isCheck = isCheck(playerToCheck);
        rollback(from, to, toMove, move);

        // Si c'est une attaque (différente de prise en passant - pour prise en passant voir rollback )
        // on replace la piece prise
        if(attackedPiece != null){
            placePieceAt(attackedPiece, to);
        }

        return isCheck;
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

        chessBoard.setUpTeam(player1, Side.TOP);
        chessBoard.setUpTeam(player2, Side.BOTTOM);

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