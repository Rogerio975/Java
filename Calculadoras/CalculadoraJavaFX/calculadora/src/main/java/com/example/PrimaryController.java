package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class PrimaryController {

    @FXML
    private TextField display;

    private String currentNumber = "";
    private double firstOperand = 0;
    private String operator = "";
    private boolean startNewNumber = true;

    @FXML
    private void handleNumber(ActionEvent event) {
        if (startNewNumber) {
            display.setText("");
            currentNumber = "";
            startNewNumber = false;
        }
        String digit = ((Button) event.getSource()).getText();
        currentNumber += digit;
        display.setText(currentNumber);
    }

    @FXML
    private void handleDecimal(ActionEvent event) {
        if (startNewNumber) {
            display.setText("0.");
            currentNumber = "0.";
            startNewNumber = false;
        } else if (!currentNumber.contains(".")) {
            currentNumber += ".";
            display.setText(currentNumber);
        }
    }

    @FXML
    private void handleOperator(ActionEvent event) {
        if (!currentNumber.isEmpty()) {
            firstOperand = Double.parseDouble(currentNumber);
        }
        operator = ((Button) event.getSource()).getText();
        startNewNumber = true;
        currentNumber = ""; // Clear current number for next input
    }

    @FXML
    private void handleEquals(ActionEvent event) {
        if (operator.isEmpty() || currentNumber.isEmpty()) {
            return; // Nothing to calculate or incomplete operation
        }

        double secondOperand = Double.parseDouble(currentNumber);
        double result = 0;

        try {
            switch (operator) {
                case "+":
                    result = firstOperand + secondOperand;
                    break;
                case "-":
                    result = firstOperand - secondOperand;
                    break;
                case "*":
                    result = firstOperand * secondOperand;
                    break;
                case "/":
                    if (secondOperand == 0) {
                        display.setText("Error: Div by zero");
                        resetCalculatorState();
                        return;
                    }
                    result = firstOperand / secondOperand;
                    break;
                // Adicione mais operadores aqui para a calculadora científica
            }
            display.setText(String.valueOf(result));
            firstOperand = result; // Permite encadear operações
            operator = "";
            currentNumber = String.valueOf(result); // Define o número atual como o resultado para futuras operações
            startNewNumber = true; // A próxima entrada de número deve limpar o display
        } catch (NumberFormatException e) {
            display.setText("Error");
            resetCalculatorState();
        }
    }

    @FXML
    private void handleClear(ActionEvent event) {
        resetCalculatorState();
        display.setText("0");
    }

    private void resetCalculatorState() {
        currentNumber = "";
        firstOperand = 0;
        operator = "";
        startNewNumber = true;
    }

    // Placeholder para funções científicas - a ser expandido
    @FXML
    private void handleScientificFunction(ActionEvent event) {
        // Implementação para funções científicas como sin, cos, tan, log, etc.
        // Isso envolverá analisar o número atual, aplicar a função e atualizar o display.
        // Exemplo: Math.sin(Math.toRadians(Double.parseDouble(currentNumber)))
        display.setText("Função Científica (TODO)"); // Placeholder
    }
}
