package com.mycalculator.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorGUI extends JFrame implements ActionListener {

    private JTextField displayField;
    private JButton[] numberButtons;
    private JButton addButton, subButton, mulButton, divButton;
    private JButton decButton, equButton, clrButton, delButton;

    private double num1 = 0, num2 = 0, result = 0;
    private char operator = ' '; // Inicializado vacío
    private boolean hasDecimal = false;
    private boolean startNewNumber = true; // Control para saber cuándo limpiar pantalla al escribir el segundo número

    private Calculator calculatorLogic;

    public CalculatorGUI() {
        calculatorLogic = new Calculator();

        setTitle("Calculadora Pro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(320, 450); // Ligeramente más grande
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        displayField = new JTextField();
        displayField.setFont(new Font("Consolas", Font.BOLD, 32));
        displayField.setEditable(false);
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        displayField.setBackground(Color.WHITE);
        displayField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(displayField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(buttonPanel, BorderLayout.CENTER);

        numberButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].setFont(new Font("Arial", Font.BOLD, 20));
            numberButtons[i].setFocusable(false);
            numberButtons[i].addActionListener(this);
        }

        // Inicializar botones de operación
        addButton = createOperatorButton("+");
        subButton = createOperatorButton("-");
        mulButton = createOperatorButton("*");
        divButton = createOperatorButton("/");
        decButton = createOperatorButton(".");
        equButton = createOperatorButton("=");
        clrButton = createOperatorButton("C");
        clrButton.setForeground(Color.RED);
        delButton = createOperatorButton("DEL"); // Nuevo botón Delete
        delButton.setForeground(Color.BLUE);

        // Fila 1
        buttonPanel.add(clrButton);
        buttonPanel.add(delButton);
        buttonPanel.add(new JPanel()); // Espacio vacío decorativo
        buttonPanel.add(divButton);

        // Fila 2
        buttonPanel.add(numberButtons[7]);
        buttonPanel.add(numberButtons[8]);
        buttonPanel.add(numberButtons[9]);
        buttonPanel.add(mulButton);

        // Fila 3
        buttonPanel.add(numberButtons[4]);
        buttonPanel.add(numberButtons[5]);
        buttonPanel.add(numberButtons[6]);
        buttonPanel.add(subButton);

        // Fila 4
        buttonPanel.add(numberButtons[1]);
        buttonPanel.add(numberButtons[2]);
        buttonPanel.add(numberButtons[3]);
        buttonPanel.add(addButton);

        // Fila 5
        buttonPanel.add(decButton);
        buttonPanel.add(numberButtons[0]);
        buttonPanel.add(equButton);
        buttonPanel.add(new JPanel()); // Relleno para mantener grid

        setVisible(true);
    }

    private JButton createOperatorButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        btn.setFocusable(false);
        btn.addActionListener(this);
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        // 1. Manejo de números
        if ((command.charAt(0) >= '0' && command.charAt(0) <= '9')) {
            if (startNewNumber) {
                displayField.setText(command);
                startNewNumber = false;
            } else {
                displayField.setText(displayField.getText() + command);
            }
            return;
        }

        // 2. Manejo de punto decimal
        if (".".equals(command)) {
            if (startNewNumber) {
                displayField.setText("0.");
                startNewNumber = false;
                hasDecimal = true;
            } else if (!hasDecimal) {
                displayField.setText(displayField.getText() + ".");
                hasDecimal = true;
            }
            return;
        }

        // 3. Manejo de Borrado (Clear y Delete)
        if ("C".equals(command)) {
            clearAll();
            return;
        }

        if ("DEL".equals(command)) {
            String currentText = displayField.getText();
            if (currentText.length() > 0) {
                displayField.setText(currentText.substring(0, currentText.length() - 1));
                // Si borramos el punto, permitir ponerlo de nuevo
                if (!currentText.contains(".")) {
                    hasDecimal = false;
                }
            }
            return;
        }

        // 4. Manejo de Operadores (+, -, *, /)
        if (isOperator(command.charAt(0)) && !"=".equals(command)) {
            if (displayField.getText().isEmpty()) return; // Protección contra vacíos
            
            // Si ya teníamos una operación pendiente, calculamos el parcial (encadenamiento)
            if (operator != ' ' && !startNewNumber) {
                calculate();
            }

            try {
                num1 = Double.parseDouble(displayField.getText());
                operator = command.charAt(0);
                startNewNumber = true; // El próximo número limpiará la pantalla
                hasDecimal = false;
            } catch (NumberFormatException ex) {
                displayField.setText("Error");
            }
            return;
        }

        // 5. Manejo de Igual (=)
        if ("=".equals(command)) {
            if (operator == ' ' || displayField.getText().isEmpty()) return;
            calculate();
            operator = ' '; // Reseteamos operador
            startNewNumber = true; // Listo para nuevo cálculo
        }
    }

    private void calculate() {
        try {
            num2 = Double.parseDouble(displayField.getText());

            switch (operator) {
                case '+': result = calculatorLogic.add(num1, num2); break;
                case '-': result = calculatorLogic.subtract(num1, num2); break;
                case '*': result = calculatorLogic.multiply(num1, num2); break;
                case '/': result = calculatorLogic.divide(num1, num2); break;
            }

            // Formatear resultado para quitar .0 si es entero
            if (result % 1 == 0) {
                displayField.setText(String.valueOf((int) result));
            } else {
                displayField.setText(String.valueOf(result));
            }
            
            num1 = result; // Permitir seguir operando sobre el resultado
            
        } catch (IllegalArgumentException ex) {
            displayField.setText("Error: Div 0");
        } catch (Exception ex) {
            displayField.setText("Error");
        }
    }

    private void clearAll() {
        displayField.setText("");
        num1 = 0;
        num2 = 0;
        result = 0;
        operator = ' ';
        hasDecimal = false;
        startNewNumber = true;
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    public static void main(String[] args) {
        // Uso de LookAndFeel del sistema para que se vea nativo
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) { }

        SwingUtilities.invokeLater(() -> new CalculatorGUI());
    }
}
