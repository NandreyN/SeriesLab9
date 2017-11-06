import java.io.*;
import java.util.*;

public abstract class Series {
    int n;

    Series(int n) {
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
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++){
            sb.append(String.valueOf(get(i)));
            sb.append(" , ");
        }
        return sb.toString();
    }

    public void saveToFile(String fileName) throws IOException {
        File file = new File(fileName);
        boolean success = true;
        if (!file.exists())
            success = file.createNewFile();

        if (!success)
            throw new FileNotFoundException("File not found. Cannot create for  some reasons");

        BufferedWriter br = new BufferedWriter(new FileWriter(fileName));
        for (int i = 1; i <= n; i++) {
            br.write(String.valueOf(get(i)) + "\t");
        }
        br.close();
    }
}
