package com.burca;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    static String NUMBER_REGEX = "\\d+";
    static String OPERATORS_REGEX = "[+\\-*/]+";

    public static void main(String[] args) {
        Math calculator = new Math();

        System.out.println("Digite a expressão (ex: 2+2): ");

        Scanner scanner = new Scanner(System.in);
        String expression = scanner.next();

        System.out.printf("Resultado:\n%s = %.2f", expression, calculator.calculate(expression));
    }

    public static class Math {
        private List<Double> getNumbers(String expression) {
            List<Double> numbersList = new ArrayList<>();

            Matcher matcher = Pattern.compile(NUMBER_REGEX).matcher(expression);
            while (matcher.find()) {
                numbersList.add(Double.parseDouble(matcher.group()));
            }

            return numbersList;
        }

        private List<String> getOperators(String expression) {
            List<String> operatorsList = new ArrayList<>();

            Matcher matcher = Pattern.compile(OPERATORS_REGEX).matcher(expression);
            while (matcher.find()) {
                operatorsList.add(matcher.group());
            }

            return operatorsList;
        }

        public Double calculate(String expression) {
            List<Double> numbers = getNumbers(expression);
            List<String> operators = getOperators(expression);

            return doMath(numbers, operators);
        }

        private Double doMath(List<Double> numbers, List<String> operators) {
            double total = 0;
            int operatorIndex = 0;

            for (int i = 0; i < numbers.size() - 1; i++) {
                if (total == 0) {
                    double firstNumber = numbers.get(i);
                    double secondNumber = numbers.get(i + 1);
                    String operator = operators.get(i);

                    total = evaluateOperation(firstNumber, operator, secondNumber);
                } else {
                    double nextNumber = numbers.get(i + 1);
                    String operator = operators.get(operatorIndex);

                    total = evaluateOperation(total, operator, nextNumber);
                }
                operatorIndex++;
            }

            return total;
        }

        private double evaluateOperation(double firstNumber, String operator, double secondNumber) {
            return switch (operator) {
                case "+" -> firstNumber + secondNumber;
                case "-" -> firstNumber - secondNumber;
                case "*" -> firstNumber * secondNumber;
                case "/" -> firstNumber / secondNumber;
                default -> 0;
            };
        }
    }
}