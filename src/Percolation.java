public class Percolation {

    private int side;
    private int last;
    QuickFindUF model;

    private int checkArgument(int i, int j) {
        if(i <= 0 || i > side || j <= 0 || j > side) throw new IndexOutOfBoundsException("Out of bound exception");
        return (i - 1) * side + j;
    }

    public Percolation(int N) {
        if(N <= 0) throw new IllegalArgumentException("Illegal argument exception");
        side = N;
        last = N * N + 1;
        model = new QuickFindUF(last + 1);
    }

    public void open(int i, int j) {
        int index = checkArgument(i, j);
        if(i == 1) {
            model.union(index, 0);
        }
        if(i == side) {
            model.union(last, index);
        }
        if(i > 1 && isOpen(i - 1, j)) {
            model.union(index, index - side);
        }
        if(i < side && isOpen(i + 1, j)) {
            model.union(index + side, index);
        }
        if(j > 1 && isOpen(i, j - 1)) {
            if(model.find(index) > model.find(index - 1)) {
                model.union(index, index - 1);
            } else {
                model.union(index - 1, index);
            }
        }
        if(j < side && isOpen(i, j + 1)) {
            if(model.find(index) > model.find(index + 1)) {
                model.union(index, index + 1);
            } else {
                model.union(index + 1, index);
            }
        }
        if (!isOpen(i, j)) {
            model.union(index, ???);
        }
    }

    public boolean isOpen(int i, int j) {
        int index = checkArgument(i, j);
        if(model.find(index) == index) {
            return false;
        }
        return true;
    }

    public boolean isFull(int i, int j) {
        int index = checkArgument(i, j);
        if(model.find(index) == 0) {
            return true;
        }
        return false;
    }

    public boolean percolates() {
        if(model.find(last) == 0) {
            return true;
        }
        return false;
    }

    public int checkCell(int i, int j) {
        return model.find(checkArgument(i,j));
    }

    public static void main(String[] args) {
        int N = StdIn.readInt();
        Percolation sis = new Percolation(N);
        while (!sis.percolates()) {
            int i = StdIn.readInt();
            int j = StdIn.readInt();
            if (sis.isOpen(i, j)) continue;
            sis.open(i, j);
            StdOut.println(sis.checkCell(i, j));
        }
        StdOut.println("Bingo");
    }
}
