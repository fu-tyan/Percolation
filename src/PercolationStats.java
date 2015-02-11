public class PercolationStats {

    private int[] times;

    public PercolationStats(int N, int T) {
        if(N <= 0 || T <= 0) throw new IllegalArgumentException("Argumetn exception");
        times = new int[T];
        for(int k = 0; k < T; k++) {
            StdOut.println("try = " + k);
            Percolation model = new Percolation(N);
            int count = 0;
            int i;
            int j;
            while(!model.percolates()) {
                i = StdRandom.uniform(1, N + 1);
                j = StdRandom.uniform(1, N + 1);
                if(!model.isOpen(i, j)) {
                    model.open(i, j);
                    count++;
                }
            }
            times[k] = count;
        }
    }

    public double mean() {
        int sum = 0;
        for(int k = 0; k < times.length; k++) {
            sum = sum + times[k];
        }
        return sum / times.length;
    }
    public double stddev() {
        if(times.length == 1) {
            return Double.NaN;
        }
        double mean = mean();
        double sum = 0;
        for(int k = 0; k < times.length; k++) {
            sum = sum + Math.pow((times[k] - mean), 2);
        }
        return Math.sqrt(sum / (times.length - 1));
    }
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(times.length);
    }
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(times.length);
    }

    public static void main(String[] args) {
        int N = StdIn.readInt();
        int T = StdIn.readInt();
        PercolationStats sis = new PercolationStats(N, T);
        StdOut.println("mean = " + sis.mean());
        StdOut.println("stddev = " + sis.stddev());
        StdOut.println("95% confidence interval = " + sis.confidenceLo() + ", " + sis.confidenceHi());
    }
}
