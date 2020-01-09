package chess.engine;

public enum Side {
    TOP(0), // Première rangée. On commence l'indexation à 0 car nous ne sommes pas de animaux.
    //TODO: trouver un moyen pour pouvoir utiliser "N_COTE - 1".
    BOTTOM(7); // Dernière rangée.

    Side(int i) {
        this.position = i;
    }
    public int position;
}