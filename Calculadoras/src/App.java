import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter first number: ");
            double num1 = scanner.nextDouble();

            System.out.print("Enter operator (+, -, *, /): ");
            char operator = scanner.next().charAt(0);

            System.out.print("Enter second number: ");
            double num2 = scanner.nextDouble();

            double result;

            try {
                result = switch (operator) {
                    case '+' -> num1 + num2;
                    case '-' -> num1 - num2;
                    case '*' -> num1 * num2;
                    case '/' -> {
                        if (num2 != 0) {
                            yield num1 / num2;
                        } else {
                            System.out.println("Error: Division by zero");
                            throw new ArithmeticException("Division by zero");
                        }
                    }
                    default -> throw new IllegalArgumentException("Invalid operator");
                };
            } catch (ArithmeticException e) {
                return;
            }

            System.out.println("Result: " + result);
        }
    }
}
