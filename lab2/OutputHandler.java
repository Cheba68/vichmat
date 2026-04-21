import java.io.FileWriter;
import java.io.IOException;

public class OutputHandler {

    public static void writeToFile(String filename, Result result) throws IOException {

        FileWriter writer = new FileWriter(filename);

        writer.write("РЕЗУЛЬТАТ:\n");
        writer.write("Корень: " + result.root + "\n");
        writer.write("f(x): " + result.value + "\n");
        writer.write("Итерации: " + result.iterations + "\n");

        writer.close();
    }
}