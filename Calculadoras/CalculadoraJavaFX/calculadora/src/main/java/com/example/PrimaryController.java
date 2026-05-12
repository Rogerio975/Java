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
    private void initialize() {
        display.setText("0");
    }

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
            if (!operator.isEmpty()) {
                computeResult();
            } else {
                firstOperand = Double.parseDouble(currentNumber);
            }
        }
        operator = ((Button) event.getSource()).getText();
        startNewNumber = true;
        currentNumber = "";
    }

    @FXML
    private void handleEquals(ActionEvent event) {
        if (operator.isEmpty() || currentNumber.isEmpty()) {
            return;
        }
        computeResult();
    }

    @FXML
    private void handleClear(ActionEvent event) {
        resetCalculatorState();
        display.setText("0");
    }

    private void computeResult() {
        try {
            double secondOperand = Double.parseDouble(currentNumber);
            double result;

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
                        display.setText("Erro: Divisão por zero");
                        resetCalculatorState();
                        return;
                    }
                    result = firstOperand / secondOperand;
                    break;
                default:
                    return;
            }

            String output = formatResult(result);
            display.setText(output);
            firstOperand = result;
            currentNumber = output;
            operator = "";
            startNewNumber = true;
        } catch (NumberFormatException e) {
            display.setText("Erro");
            resetCalculatorState();
        }
    }

    private String formatResult(double value) {
        if (value == (long) value) {
            return String.format("%d", (long) value);
        }
        return String.valueOf(value);
    }

    private void resetCalculatorState() {
        currentNumber = "";
        firstOperand = 0;
        operator = "";
        startNewNumber = true;
    }
}
