package algorithms.Bot;

import java.util.Comparator;

class pairComparable implements Comparator<Pair> {

    @Override
    public int compare(Pair o1, Pair o2) {
        if (o1.getX() > o2.getX()) {
            return 1;
        } else if (o1.getX() < o2.getX()) {
            return -1;
        } else {
            return 0;
        }
    }
}

public class Pair {

    private int x;
    private int y;

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
