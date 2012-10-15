package com.netflix.frigga;

import java.util.regex.Pattern;

public class NameValidation implements NameConstants {

    private static final Pattern NAME_HYPHEN_CHARS_PATTERN = Pattern.compile("^[" + NAME_HYPHEN_CHARS + "]+");
    private static final Pattern NAME_CHARS_PATTERN = Pattern.compile("^[" + NAME_CHARS + "]+");

    public static String notEmpty(String value, String variableName) {
        if (value == null) {
            throw new NullPointerException("ERROR: Trying to use String with null " + variableName);
        }
        if (value.isEmpty()) {
            throw new IllegalArgumentException("ERROR: Illegal empty string for " + variableName);
        }
        return value;
    }

    /**
     * Validates a name of a cloud object according to the rules in http://go/CloudModel
     * The name can contain letters, numbers, dots, and underscores.
     *
     * @param name the string to validate
     * @return true if the name is valid
     */
    public static boolean checkName(String name) {
        return name != null && NAME_CHARS_PATTERN.matcher(name).matches();
    }

    /**
     * The detail part of an auto scaling group name can include letters, numbers, dots, underscores, and hyphens.
     * Restricting the ASG name this way allows safer assumptions in other code about ASG names, like a promise of no
     * spaces, hash marks, percent signs, or dollar signs.
     *
     * @param detail the detail string to validate
     * @return true if the detail is valid
     */
    public static Boolean checkDetail(String detail) {
        return detail != null && NAME_HYPHEN_CHARS_PATTERN.matcher(detail).matches();
    }

}
