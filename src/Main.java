import java.util.Scanner;
import java.util.TreeMap;


public class Main {
    public static void main(String[] args) {
        Converter converter = new Converter();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите выражение: ");
        String input = scanner.nextLine();

        String[] tokens = input.split("\\s*[-+*/]\\s*");

        if (tokens.length != 2) {
            throw new IllegalArgumentException("Некорректное количество чисел или операторов");
        }

        String operator = input.replaceAll("\\s*[0-9IVXLCDM]+\\s*", "");

        if (!operator.matches("[-+*/]")) {
            throw new IllegalArgumentException("Некорректный оператор");
        }

        boolean isRoman1 = converter.isRoman(tokens[0]);
        boolean isRoman2 = converter.isRoman(tokens[1]);

        if (isRoman1 != isRoman2) {
            throw new IllegalArgumentException("Числа должны быть в одном формате (или оба арабские, или оба римские)");
        }

        int a, b;

        if (isRoman1) {
            a = converter.romanToInt(tokens[0]);
            b = converter.romanToInt(tokens[1]);
        } else {
            a = Integer.parseInt(tokens[0]);
            b = Integer.parseInt(tokens[1]);
        }

        int result;
        switch (operator) {
            case "+" -> result = a + b;
            case "-" -> result = a - b;
            case "*" -> result = a * b;
            default -> {
                if (b == 0) {
                    throw new ArithmeticException("Деление на ноль");
                }
                result = a / b;
            }
        }

        if (isRoman1) {
            if (result < 0) {
                throw new IllegalArgumentException("Римские цифры не могут быть меньше 0");
            }
            System.out.println(converter.intToRoman(result));
        } else {
            System.out.println(result);
        }
    }
}
class Converter {
    TreeMap<Character, Integer> romanKeyMap = new TreeMap<>();
    TreeMap<Integer, String> arabianKeyMap = new TreeMap<>();

    public Converter() {
        romanKeyMap.put('I', 1);
        romanKeyMap.put('V', 5);
        romanKeyMap.put('X', 10);
        romanKeyMap.put('L', 50);
        romanKeyMap.put('C', 100);
        romanKeyMap.put('D', 500);
        romanKeyMap.put('M', 1000);

        arabianKeyMap.put(1000, "M");
        arabianKeyMap.put(900, "CM");
        arabianKeyMap.put(500, "D");
        arabianKeyMap.put(400, "CD");
        arabianKeyMap.put(100, "C");
        arabianKeyMap.put(90, "XC");
        arabianKeyMap.put(50, "L");
        arabianKeyMap.put(40, "XL");
        arabianKeyMap.put(10, "X");
        arabianKeyMap.put(9, "IX");
        arabianKeyMap.put(5, "V");
        arabianKeyMap.put(4, "IV");
        arabianKeyMap.put(1, "I");

    }


    public boolean isRoman(String number){
        return romanKeyMap.containsKey(number.charAt(0));
    }


    public String intToRoman(int number) {
        StringBuilder roman = new StringBuilder();
        int arabianKey;
        do {
            arabianKey = arabianKeyMap.floorKey(number);
            roman.append(arabianKeyMap.get(arabianKey));
            number -= arabianKey;
        } while (number != 0);
        return roman.toString();


    }

    public int romanToInt(String s) {
        int end = s.length() - 1;
        char[] arr = s.toCharArray();
        int arabian;
        int result = romanKeyMap.get(arr[end]);
        for (int i = end - 1; i >= 0; i--) {
            arabian = romanKeyMap.get(arr[i]);

            if (arabian < romanKeyMap.get(arr[i + 1])) {
                result -= arabian;
            } else {
                result += arabian;
            }


        }
        return result;

    }
}