package com.mycalculator.app;

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
}
