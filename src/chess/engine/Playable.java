package chess.engine;

import java.util.List;

public interface Playable {

    //NOTE: Il faut bien évidamment partir que x & y sont des données valides.
    //      Càd dans les dimensions de la board.
    List<Move> getMoves(int x, int y);

    String toString();
}
