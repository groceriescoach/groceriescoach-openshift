package com.groceriescoach.core.com.groceriescoach.core.utils;

public class StringUtils extends org.apache.commons.lang3.StringUtils {


    public static String trimToEmpty(final String str) {
        return str == null ? EMPTY : str.replaceAll("(^\\h*)|(\\h*$)", "").trim();
    }

    public static String removeCurrencySymbols(final String str) {
        return str == null ? EMPTY : trimToEmpty(str.replaceAll("\\$", "").replaceAll(",", ""));
    }

    public static String removeThousandSeparators(final String str) {
        return str == null ? EMPTY : trimToEmpty(str.replaceAll(",", ""));
    }

    public static String replaceEncodedCharacters(final String str) {
        if (isNotBlank(str)) {
            return str.replaceAll("&nbsp;", " ").replaceAll("&amp;", "&");
        } else {
            return str;
        }
    }

    public static String removeHtml(final String str) {
        if (isNotBlank(str)) {
            return replaceEncodedCharacters(str).replaceAll("<br>", " ");
        } else {
            return str;
        }
    }

    public static String replaceAll(String originalString, String[] searchStrings, String replacement) {
        for (String searchString : searchStrings) {
            originalString = originalString.replaceAll(searchString, replacement);
        }
        return originalString;
    }

    public static String removeAll(String originalString, String[] searchStrings) {
        for (String searchString : searchStrings) {
            originalString = originalString.replaceAll(searchString, "");
        }
        return originalString;
    }

}
