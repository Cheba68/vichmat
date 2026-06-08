package utils;

import java.io.File;
import java.util.Scanner;

public class FileInputReader {

    public static double[] read(String filename)
            throws Exception {

        Scanner scanner =
                new Scanner(new File(filename));

        double[] data =
                new double[3];

        for (int i = 0; i < 3; i++) {

            data[i] =
                    InputParser.parse(
                            scanner.nextLine()
                    );
        }

        scanner.close();

        return data;
    }
}