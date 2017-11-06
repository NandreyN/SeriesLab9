public class Exponential extends Series {
    private double q;
    private double b1;

    public Exponential(double b1, double q, int n) {
        super(n);
        this.q = q;
        this.b1 = b1;
    }

    @Override
    public double get(int j) {
        return b1 * Math.pow(q, j - 1);
    }
}
