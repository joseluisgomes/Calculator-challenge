package com.example.demo;

/**
 * Possible math operations
 *
 * @author José Gomes
 */
public enum MathOperation {
    SUM("sum") {
        @Override
        public double apply(double x, double y) { return x + y; }
    },
    SUBTRACTION("sub") {
        @Override
        public double apply(double x, double y) { return x - y; }
    },
    MULTIPLICATION("mul") {
        @Override
        public double apply(double x, double y) { return x * y; }
    },
    DIVISION("div") {
        @Override
        public double apply(double x, double y) { return x / y; }
    };

    private final String operation;

    MathOperation(String operation) {
        this.operation = operation;
    }

    public String getOperation() { return operation; }

    public abstract double apply(double x, double y);
}
