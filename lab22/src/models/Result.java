package models;

import java.util.List;

public class Result {

    private final double root;
    private final double functionValue;
    private final int iterations;
    private final List<IterationData> iterationsTable;

    public Result(double root,
                  double functionValue,
                  int iterations,
                  List<IterationData> iterationsTable) {

        this.root = root;
        this.functionValue = functionValue;
        this.iterations = iterations;
        this.iterationsTable = iterationsTable;
    }

    public double getRoot() {
        return root;
    }

    public double getFunctionValue() {
        return functionValue;
    }

    public int getIterations() {
        return iterations;
    }

    public List<IterationData> getIterationsTable() {
        return iterationsTable;
    }
}