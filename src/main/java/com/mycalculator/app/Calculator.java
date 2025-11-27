package com.mycalculator.app;

import java.util.Random;
import java.util.Scanner;

public class Calculator {

    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public double divide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("No se puede dividir por cero.");
        }
        return (double) a / b;
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
            correctAnswer = add(operand1, operand2);
        } else {
            operator = '-';
            correctAnswer = subtract(operand1, operand2);
        }
        return new Mission(operand1, operand2, operator, correctAnswer);
    }

    public static void main(String[] args) {
        System.out.println("¡Bienvenido a las Misiones de la Calculadora Java!");
        Scanner scanner = new Scanner(System.in);
        Calculator myCalculator = new Calculator();
        int score = 0;
        int totalMissions = 3;

        for (int i = 0; i < totalMissions; i++) {
            Mission currentMission = myCalculator.generateMission();
            System.out.println("
Misión " + (i + 1) + ": " + currentMission.toString());
            System.out.print("Tu respuesta: ");

            try {
                int userAnswer = scanner.nextInt();
                if (userAnswer == currentMission.correctAnswer) {
                    System.out.println("¡Correcto!");
                    score++;
                } else {
                    System.out.println("Incorrecto. La respuesta correcta era: " + currentMission.correctAnswer);
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Entrada no válida. Por favor, introduce un número.");
                scanner.next();
                i--;
            }
        }

        System.out.println("
--- Fin de las Misiones ---");
        System.out.println("Tu puntuación final: " + score + "/" + totalMissions);
        scanner.close();
    }
}
