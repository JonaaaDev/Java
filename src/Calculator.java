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

    public static void main(String[] args) {
        System.out.println("¡Bienvenido a la Calculadora Java!");
        
        Calculator myCalculator = new Calculator();

        // Ejemplos de uso (puedes borrar estos luego):
        System.out.println("Suma de 5 y 3: " + myCalculator.add(5, 3));         // Debería imprimir 8
        System.out.println("Resta de 10 y 4: " + myCalculator.subtract(10, 4));  // Debería imprimir 6
        System.out.println("Multiplicación de 6 y 7: " + myCalculator.multiply(6, 7)); // Debería imprimir 42
        System.out.println("División de 10 y 2: " + myCalculator.divide(10, 2));   // Debería imprimir 5.0
        
        try {
            System.out.println("División por cero: " + myCalculator.divide(10, 0));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage()); // Debería imprimir "No se puede dividir por cero."
        }
    }
}