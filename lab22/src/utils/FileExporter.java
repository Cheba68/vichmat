package utils;

import models.IterationData;
import models.Result;

import java.io.FileWriter;
import java.io.IOException;

public class FileExporter {

    public static void export(Result result,
                              String filename)
            throws IOException {

        FileWriter writer =
                new FileWriter(filename);

        writer.write(
                "РЕЗУЛЬТАТ РЕШЕНИЯ\n\n"
        );

        writer.write(
                "Корень: "
                        + result.getRoot()
                        + "\n"
        );

        writer.write(
                "f(x): "
                        + result.getFunctionValue()
                        + "\n"
        );

        writer.write(
                "Количество итераций: "
                        + result.getIterations()
                        + "\n\n"
        );

        writer.write(
                "ТАБЛИЦА ИТЕРАЦИЙ\n\n"
        );

        writer.write(
                "Шаг\tX\tf(x)\tОшибка\n"
        );

        for (IterationData row
                : result.getIterationsTable()) {

            writer.write(
                    row.getStep()
                            + "\t"
                            + row.getX()
                            + "\t"
                            + row.getFx()
                            + "\t"
                            + row.getError()
                            + "\n"
            );
        }

        writer.close();
    }
}