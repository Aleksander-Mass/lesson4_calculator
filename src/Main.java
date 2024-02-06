// lesson4_test_ calculator

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите арифметическое выражение:");
        String input = scanner.nextLine();

        try {
            String result = calc(input);
            System.out.println("Результат: " + result);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public static String calc(String input) throws Exception {
        String[] tokens = input.split("\\s");
        if (tokens.length != 3) {
            throw new Exception("Неверный формат математической операции");
        }

        String operand1 = tokens[0];
        String operator = tokens[1];
        String operand2 = tokens[2];

        boolean isRoman = operand1.matches("[IVXLC]+") && operand2.matches("[IVXLC]+");

        int num1 = getNumber(operand1, isRoman);
        int num2 = getNumber(operand2, isRoman);

        // Проверка на соответствие диапазону [1, 10]
        if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
            throw new Exception("Введенные числа должны быть в диапазоне от 1 до 10 включительно");
        }

        int result;
        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                if (num2 == 0) {
                    throw new Exception("Деление на ноль");
                }
                result = num1 / num2;
                break;
            default:
                throw new Exception("Недопустимая арифметическая операция: " + operator);
        }

        return isRoman ? RomanConverter.intToRoman(result) : String.valueOf(result);
    }

    private static int getNumber(String operand, boolean isRoman) throws Exception {
        int num;
        try {
            num = isRoman ? RomanConverter.romanToInt(operand) : Integer.parseInt(operand);
        } catch (NumberFormatException e) {
            throw new Exception("Недопустимый формат числа: " + operand);
        }

        return num;
    }
}

class RomanConverter {
    private static final Map<Character, Integer> ROMAN_NUMERALS = new HashMap<>();

    static {
        ROMAN_NUMERALS.put('I', 1);
        ROMAN_NUMERALS.put('V', 5);
        ROMAN_NUMERALS.put('X', 10);
        ROMAN_NUMERALS.put('L', 50);
        ROMAN_NUMERALS.put('C', 100);
    }

    public static int romanToInt(String roman) {
        int result = 0;
        int prevValue = 0;

        for (int i = roman.length() - 1; i >= 0; i--) {
            int currentValue = ROMAN_NUMERALS.get(roman.charAt(i));

            if (currentValue < prevValue) {
                result -= currentValue;
            } else {
                result += currentValue;
            }

            prevValue = currentValue;
        }

        return result;
    }

    public static String intToRoman(int num) throws Exception {
        if (num <= 0 || num > 3999) {
            throw new Exception("Число для конвертации в римскую систему должно быть в пределах от 1 до 3999");
        }

        StringBuilder roman = new StringBuilder();
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] numerals = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        for (int i = 0; i < values.length; i++) {
            while (num >= values[i]) {
                num -= values[i];
                roman.append(numerals[i]);
            }
        }

        return roman.toString();
    }
}