package airline.utils;

import java.util.Locale;

/** Shared, null-safe case-insensitive text matching for dashboard searches. */
public final class SearchUtils {
    private SearchUtils() {
    }

    public static boolean contains(String value, String query) {
        return normalize(value).contains(normalize(query));
    }

    public static String normalize(String value) {
        return value == null ? "" : value.toLowerCase(Locale.ROOT);
    }
}
