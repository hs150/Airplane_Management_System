package airline.utils;

import java.util.Collection;

/** Generates the next numeric identifier while preserving the application's existing ID format. */
public final class IdentifierGenerator {
    private IdentifierGenerator() {
    }

    public static String next(String prefix, Collection<String> identifiers, int firstNumber, int width) {
        int nextNumber = identifiers.stream()
                .filter(identifier -> identifier != null)
                .mapToInt(IdentifierGenerator::numericPart)
                .max()
                .orElse(firstNumber - 1) + 1;
        return prefix + String.format("%0" + width + "d", nextNumber);
    }

    private static int numericPart(String identifier) {
        String digits = identifier.replaceAll("\\D", "");
        return digits.isEmpty() ? 0 : Integer.parseInt(digits);
    }
}
