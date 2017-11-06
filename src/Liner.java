public class Liner extends Series {
    private double d;
    private double a1;

    public Liner(double a1, double d, int n) {
        super(n);
        this.d = d;
        this.a1 = a1;
    }

    @Override
    public double get(int j) {
        return a1 + (j - 1) * d;
    }
}
