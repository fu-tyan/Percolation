public class Percolation {

    private int side;
    private int last;
    private WeightedQuickUnionUF model;
    private boolean[][] model2d;
    private boolean percolated;

    public Percolation(int N) {
        percolated = false;
        if (N <= 0) throw new IllegalArgumentException("Illegal argument exception");
        side = N;
        last = N * N + 1;
        model = new WeightedQuickUnionUF(last + 1);
        model2d = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                model2d[i][j] = false;
            }
        }
    }

    private int checkArgument(int i, int j) {
        if (i <= 0 || i > side || j <= 0 || j > side) throw new IndexOutOfBoundsException("Out of bound exception");
        return (i - 1) * side + j;
    }

    public void open(int i, int j) {
        int index = checkArgument(i, j);
        if (i == 1) {
            model.union(index, 0);
        }
        if (i > 1 && isOpen(i - 1, j)) {
            model.union(index, index - side);
        }
        if (i < side && isOpen(i + 1, j)) {
            model.union(index + side, index);
        }
        if (j > 1 && isOpen(i, j - 1)) {
            if (model.find(index) > model.find(index - 1)) {
                model.union(index, index - 1);
            } else {
                model.union(index - 1, index);
            }
        }
        if (j < side && isOpen(i, j + 1)) {
            if (model.find(index) > model.find(index + 1)) {
                model.union(index, index + 1);
            } else {
                model.union(index + 1, index);
            }
        }
        if (i == side) {
            if (model.connected(0, index)) {
               percolated = true;
            }
        }
        model2d[i - 1][j - 1] = true;
    }

    public boolean isOpen(int i, int j) {
        checkArgument(i, j);
        return model2d[i - 1][j - 1];
    }

    public boolean isFull(int i, int j) {
        int index = checkArgument(i, j);
        if (model.connected(index, 0)) {
            return true;
        }
        return false;
    }

    public boolean percolates() {
        return percolated;
    }
}
