package com.mycalculator.app;

import java.util.Random;
import java.util.Scanner;
import javax.swing.SwingUtilities;

public class Calculator {

    public double add(double a, double b) {
        return a + b;
    }

    public double subtract(double a, double b) {
        return a - b;
    }

    public double multiply(double a, double b) {
        return a * b;
    }

    public double divide(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("No se puede dividir por cero.");
        }
        return a / b;
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalculatorGUI());
    }
}
