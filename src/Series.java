import java.io.*;

public abstract class Series {
    // add first and d here , remove from children
    double first, delta;
    int n;
    final static int SCALE = 100;

    Series(double first, double delta, int n) {
        this.first = first;
        this.delta = delta;
        this.n = n;
    }

    public abstract double get(int j);

    public double getSum() {
        double sum = 0.;
        for (int i = 1; i <= n; i++)
            sum += get(i);
        return sum;
    }

    public String toString() {
        if (n <= 0)
            return "Empty here";

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n - 1; i++) {
            sb.append(String.valueOf(get(i)));
            sb.append(" , ");
        }
        sb.append(String.valueOf(get(n)));
        sb.append("; \tSum = ").append(getSum());
        return sb.toString();
    }

    public void saveToFile(String fileName) throws IOException {
        BufferedWriter br = new BufferedWriter(new FileWriter(fileName));
        br.write(this.toString());
        br.close();
    }
}
