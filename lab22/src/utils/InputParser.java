package utils;

public class InputParser {

    public static double parse(String text) {

        text = text.replace(',', '.');

        return Double.parseDouble(text);
    }
}