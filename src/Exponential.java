public class Exponential extends Series {
    public Exponential(double b1, double q, int n) {
        super(b1, q, n);
    }

    @Override
    public double get(int j) {
        return first * Math.pow(delta, j - 1);
    }
}
