package com.mycalculator.app;

import java.util.Random;

public class Calculator {

    private static boolean nativeLoaded = false;

    static {
        try {
            System.loadLibrary("calculator_native");
            nativeLoaded = true;
            System.out.println("[INFO] Motor C++ cargado exitosamente. Modo Turbo activado.");
        } catch (UnsatisfiedLinkError e) {
            System.out.println("[WARN] Librería C++ no encontrada. Usando motor Java estándar.");
        }
    }

    private native double addNative(double a, double b);
    private native double subtractNative(double a, double b);
    private native double multiplyNative(double a, double b);
    private native double divideNative(double a, double b);

    public double add(double a, double b) {
        return nativeLoaded ? addNative(a, b) : (a + b);
    }

    public double subtract(double a, double b) {
        return nativeLoaded ? subtractNative(a, b) : (a - b);
    }

    public double multiply(double a, double b) {
        return nativeLoaded ? multiplyNative(a, b) : (a * b);
    }

    public double divide(double a, double b) {
        if (!nativeLoaded && b == 0) {
            throw new IllegalArgumentException("No se puede dividir por cero.");
        }
        return nativeLoaded ? divideNative(a, b) : (a / b);
    }

    static class Mission {
        int operand1;
        int operand2;
        char operator;
        int correctAnswer;

        public Mission(int operand1, int operand2, char operator, int correctAnswer) {
            this.operand1 = operand1;
            this.operand2 = operand2;
            this.operator = operator;
            this.correctAnswer = correctAnswer;
        }

        @Override
        public String toString() {
            return String.format("¿Cuánto es %d %c %d?", operand1, operator, operand2);
        }
    }

    public Mission generateMission() {
        Random random = new Random();
        int operand1 = random.nextInt(10) + 1;
        int operand2 = random.nextInt(10) + 1;
        char operator;
        int correctAnswer;

        if (random.nextBoolean()) {
            operator = '+';
            correctAnswer = (int) add(operand1, operand2);
        } else {
            operator = '-';
            correctAnswer = (int) subtract(operand1, operand2);
        }
        return new Mission(operand1, operand2, operator, correctAnswer);
    }
}
