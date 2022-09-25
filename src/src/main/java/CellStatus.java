public class CellStatus {
    int index;
    int player;
    int priority;

    public CellStatus(int index, int player, int priority) {
        this.index = index;
        this.player = player;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "CellStatus{" +
            "index=" + index +
            ", player=" + player +
            ", priority=" + priority +
            '}';
    }
}
