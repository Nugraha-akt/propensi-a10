package apap.propensi.mantra.service;

import java.util.Arrays;

public class StringTitleCase {

    public static String toTitleCase(String input) {
        if (input == null) {
            return null;
        }
        if (input.length() == 0) {
            return input;
        }
        StringBuilder sb = new StringBuilder();
        Arrays.stream(input.split("\\s+"))
                .forEach(s -> sb.append(Character.toTitleCase(s.charAt(0)))
                        .append(s.substring(1))
                        .append(" "));
        return sb.toString().trim();
    }
}
