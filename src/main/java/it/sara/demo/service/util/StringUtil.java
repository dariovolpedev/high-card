package it.sara.demo.service.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtil {

    public boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public boolean containsIgnoreCase(String s, String part) {
        return s != null && part != null && s.toLowerCase().contains(part.toLowerCase());
    }

    public boolean equalsIgnoreCase(String a, String b) {
        return a != null && a.equalsIgnoreCase(b);
    }

}
