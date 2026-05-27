package com.myapps.bavariamunich.util;

import com.myapps.bavariamunich.config.AppConsts;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ListUtil {
    private ListUtil() {
    }

    public static List<String> splitToList(String str) {
        return splitToList(str, AppConsts.DEFAULT_SEPARATOR);
    }

    public static List<String> splitToList(String input, String separator) {
        if (input == null || input.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.stream(input.split(Pattern.quote(separator)))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        Collections::unmodifiableList
                ));
    }

}
