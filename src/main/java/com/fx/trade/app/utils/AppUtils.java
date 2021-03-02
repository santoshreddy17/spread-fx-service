package com.fx.trade.app.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.*;

/**
 * Application Utils.
 */
public final class AppUtils {

    private AppUtils() {

    }

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String APPLICATION_JSON = "application/json";

    /**
     * Split Query Parameters.
     * @param query query
     * @return map of query parameters.
     */
    public static Map<String, List<String>> splitQueryParameters(String query) {
        if (query == null || "".equals(query)) {
            return Collections.emptyMap();
        }

        return Pattern.compile("&").splitAsStream(query)
                .map(s -> Arrays.copyOf(s.split("="), 2))
                .collect(groupingBy(s -> decode(s[0]), mapping(s -> decode(s[1]), toList())));

    }

    /**
     * Decode.
     * @param encoded encoded string
     * @return decoded string.
     */
    private static String decode(final String encoded) {
        try {
            return encoded == null ? null : URLDecoder.decode(encoded, "UTF-8");
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 is a required encoding", e);
        }
    }

    /**
     * Check if string is null or empty.
     * @param string
     * @return
     */
    public static boolean notNullOrEmpty(String string) {
        return string!=null  && !string.isEmpty();
    }
}
