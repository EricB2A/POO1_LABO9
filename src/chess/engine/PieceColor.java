package chess.engine;

import chess.PlayerColor;

public class PieceColor {
    private PlayerColor color;
    private Side side;

    public PieceColor(PlayerColor playerColor, Side side) {
        if(playerColor == null){
            throw new RuntimeException("The nean is not une couleur");
        }
        if(side == null){
            throw new RuntimeException("We need a side pour player");
        }

        this.color = playerColor;
        this.side = side;
    }

    public PlayerColor getColor() {
        return color;
    }

    public Side getSide() {
        return side;
    }
    public static PlayerColor getOpponentColor(PlayerColor playerColor){
        return playerColor == PlayerColor.WHITE ? PlayerColor.BLACK : PlayerColor.BLACK;
    }

}
