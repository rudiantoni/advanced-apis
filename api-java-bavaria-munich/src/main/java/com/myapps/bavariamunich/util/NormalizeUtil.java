package com.myapps.bavariamunich.util;

import java.util.Locale;

public class NormalizeUtil {

    private NormalizeUtil() {
    }

    public static String normalizeEmail(String email) {
        if (email == null) return null;
        return email.trim().toLowerCase(Locale.ROOT);
    }

}
