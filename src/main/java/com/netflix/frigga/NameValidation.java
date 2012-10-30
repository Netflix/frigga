/**
 * Copyright 2012 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.netflix.frigga;

import java.util.regex.Pattern;

/**
 * Contains static validation methods for checking if a name conforms to Asgard naming standards.
 */
public class NameValidation {

    private static final Pattern NAME_HYPHEN_CHARS_PATTERN =
            Pattern.compile("^[" + NameConstants.NAME_HYPHEN_CHARS + "]+");
    private static final Pattern NAME_CHARS_PATTERN = Pattern.compile("^[" + NameConstants.NAME_CHARS + "]+");
    private static final Pattern PUSH_FORMAT_PATTERN = Pattern.compile(".*?" + NameConstants.PUSH_FORMAT);
    private static final Pattern LABELED_VARIABLE_PATTERN =
            Pattern.compile("^(.*?-)?" + NameConstants.LABELED_VARIABLE + ".*?$");

    private NameValidation() { }

    /**
     * Validates if provided value is non-null and non-empty.
     *
     * @param value the string to validate
     * @param variableName name of the variable to include in error messages
     * @return the value parameter if valid, throws an exception otherwise
     */
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
     * Validates a name of a cloud object. The name can contain letters, numbers, dots, and underscores.
     *
     * @param name the string to validate
     * @return true if the name is valid
     */
    public static boolean checkName(String name) {
        return checkMatch(name, NAME_CHARS_PATTERN);
    }

    /**
     * The detail part of an auto scaling group name can include letters, numbers, dots, underscores, and hyphens.
     * Restricting the ASG name this way allows safer assumptions in other code about ASG names, like a promise of no
     * spaces, hash marks, percent signs, or dollar signs.
     *
     * @param detail the detail string to validate
     * @return true if the detail is valid
     */
    public static boolean checkDetail(String detail) {
        return checkMatch(detail, NAME_HYPHEN_CHARS_PATTERN);
    }

    /**
     * Determines whether a name ends with the reserved format -v000 where 0 represents any digit, or starts with the
     * reserved format z0 where z is any letter, or contains a hyphen-separated token that starts with the z0 format.
     *
     * @param name to inspect
     * @return true if the name ends with the reserved format
     */
    public static Boolean usesReservedFormat(String name) {
        return checkMatch(name, PUSH_FORMAT_PATTERN) || checkMatch(name, LABELED_VARIABLE_PATTERN);
    }

    private static boolean checkMatch(String input, Pattern pattern) {
        return input != null && pattern.matcher(input).matches();
    }

}
