public class Exponential extends Series {
    public Exponential(double b1, double q, int n) {
        super(b1, q, n);
    }

    @Override
    public double get(int j) {
        return first * Math.pow(delta, j - 1);
    }

    @Override
    public double getSum() {
        if (Math.abs(delta - 1) <= 10e-5)
            return super.getSum();

        return (get(n) * delta - first) / (delta - 1);
    }
}
