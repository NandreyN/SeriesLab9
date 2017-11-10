public class Liner extends Series {

    public Liner(double a1, double d, int n) {
        super(a1,d,n);
    }

    @Override
    public double get(int j) {
        return first + (j - 1) * delta;
    }
}
