package com.mycalculator.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorGUI extends JFrame implements ActionListener {

    private JTextField displayField;
    private JButton[] numberButtons;
    private JButton[] operatorButtons;
    private JButton addButton, subButton, mulButton, divButton;
    private JButton decButton, equButton, clrButton;

    private double num1 = 0, num2 = 0, result = 0;
    private char operator;
    private boolean hasDecimal = false;

    private Calculator calculatorLogic;

    public CalculatorGUI() {
        calculatorLogic = new Calculator();

        setTitle("Calculadora Java");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        displayField = new JTextField();
        displayField.setFont(new Font("Arial", Font.BOLD, 30));
        displayField.setEditable(false);
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        add(displayField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 10, 10));
        add(buttonPanel, BorderLayout.CENTER);

        numberButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].setFont(new Font("Arial", Font.BOLD, 20));
            numberButtons[i].addActionListener(this);
        }

        addButton = new JButton("+");
        subButton = new JButton("-");
        mulButton = new JButton("*");
        divButton = new JButton("/");
        decButton = new JButton(".");
        equButton = new JButton("=");
        clrButton = new JButton("C");

        operatorButtons = new JButton[]{addButton, subButton, mulButton, divButton, decButton, equButton, clrButton};
        for (JButton button : operatorButtons) {
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.addActionListener(this);
        }

        buttonPanel.add(clrButton);
        buttonPanel.add(new JPanel());
        buttonPanel.add(new JPanel());
        buttonPanel.add(divButton);

        buttonPanel.add(numberButtons[7]);
        buttonPanel.add(numberButtons[8]);
        buttonPanel.add(numberButtons[9]);
        buttonPanel.add(mulButton);

        buttonPanel.add(numberButtons[4]);
        buttonPanel.add(numberButtons[5]);
        buttonPanel.add(numberButtons[6]);
        buttonPanel.add(subButton);

        buttonPanel.add(numberButtons[1]);
        buttonPanel.add(numberButtons[2]);
        buttonPanel.add(numberButtons[3]);
        buttonPanel.add(addButton);

        buttonPanel.add(decButton);
        buttonPanel.add(numberButtons[0]);
        buttonPanel.add(equButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ("C".equals(command)) {
            displayField.setText("");
            num1 = 0;
            num2 = 0;
            result = 0;
            operator = ' ';
            hasDecimal = false;
        } else if ("=".equals(command)) {
            num2 = Double.parseDouble(displayField.getText());

            switch (operator) {
                case '+':
                    result = calculatorLogic.add(num1, num2);
                    break;
                case '-':
                    result = calculatorLogic.subtract(num1, num2);
                    break;
                case '*':
                    result = calculatorLogic.multiply(num1, num2);
                    break;
                case '/':
                    try {
                        result = calculatorLogic.divide(num1, num2);
                    } catch (IllegalArgumentException ex) {
                        displayField.setText("Error: " + ex.getMessage());
                        return;
                    }
                    break;
            }
            displayField.setText(String.valueOf(result));
            num1 = result;
            hasDecimal = (result % 1 != 0);
        } else if (isOperator(command.charAt(0)) && !".".equals(command)) {
            num1 = Double.parseDouble(displayField.getText());
            operator = command.charAt(0);
            displayField.setText("");
            hasDecimal = false;
        } else if (".".equals(command)) {
            if (!hasDecimal) {
                displayField.setText(displayField.getText() + ".");
                hasDecimal = true;
            }
        } else {
            displayField.setText(displayField.getText() + command);
        }
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalculatorGUI());
    }
}
