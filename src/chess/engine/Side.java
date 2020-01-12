package chess.engine;

public enum Side {
    TOP(ChessBoard.getDimension() - 1),
    BOTTOM(0); // Première rangée. On commence l'indexation à 0 car nous ne sommes pas des animaux.

    Side(int i) {
        this.position = i;
    }
    public int position;
}