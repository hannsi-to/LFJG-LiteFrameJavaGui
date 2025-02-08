package me.hannsi.lfjg.utils.math;

import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Utility class for various string operations.
 */
public class StringUtil {

    /**
     * Gets the first N characters of a string.
     *
     * @param str the input string
     * @param n the number of characters to get
     * @return the first N characters of the string, or an empty string if the input is null or invalid
     */
    public static String getFirstNCharacters(String str, int n) {
        if (str == null || n <= 0 || n > str.length()) {
            return "";
        }
        return str.substring(0, n);
    }

    /**
     * Links a list of strings with a specified space.
     *
     * @param list the array of strings to link
     * @param space the space to add between each string
     * @return the linked string
     */
    public static String linkList(String[] list, String space) {
        String result = "";

        for (String str : list) {
            result = addLastChar(result, str + space);
        }

        return result;
    }

    /**
     * Strips the specified key from the start and end of a string.
     *
     * @param str the input string
     * @param key the key to strip
     * @return the stripped string
     */
    public static String strip(String str, String key) {
        if (str.startsWith(key) && str.endsWith(key)) {
            return str.substring(key.length(), str.length() - key.length());
        }
        return str;
    }

    /**
     * Gets a StringSelection object for the specified string.
     *
     * @param str the input string
     * @return the StringSelection object
     */
    public static StringSelection getStringSelection(String str) {
        return new StringSelection(str);
    }

    /**
     * Converts a string to a char array.
     *
     * @param string the input string
     * @return the char array
     */
    public static char[] getChars(String string) {
        return string.toCharArray();
    }

    /**
     * Splits a string by newlines and returns the result as a list.
     *
     * @param str the input string
     * @return the list of split strings
     */
    public static List<String> splitlnAsList(String str) {
        return Arrays.asList(splitln(str));
    }

    /**
     * Splits a string by newlines.
     *
     * @param str the input string
     * @return the array of split strings
     */
    public static String[] splitln(String str) {
        return split(str, "\n");
    }

    /**
     * Splits a string by a specified regex and returns the result as a list.
     *
     * @param str the input string
     * @param regex the regex to split by
     * @return the list of split strings
     */
    public static List<String> splitAsList(String str, String regex) {
        return Arrays.asList(split(str, regex));
    }

    /**
     * Splits a string by a specified regex.
     *
     * @param str the input string
     * @param regex the regex to split by
     * @return the array of split strings
     */
    public static String[] split(String str, String regex) {
        return str.split(regex);
    }

    /**
     * Extracts a substring between two delimiters.
     *
     * @param input the input string
     * @param startDelimiter the start delimiter
     * @param endDelimiter the end delimiter
     * @return the extracted substring, or an empty string if delimiters are not found
     */
    public static String extractString(String input, String startDelimiter, String endDelimiter) {
        int startIndex = input.indexOf(startDelimiter);
        int endIndex = input.indexOf(endDelimiter, startIndex + startDelimiter.length());

        if (startIndex == -1 || endIndex == -1) {
            return "";
        }

        return input.substring(startIndex + startDelimiter.length(), endIndex);
    }

    /**
     * Removes the last character from a string.
     *
     * @param str the input string
     * @return the string without the last character
     */
    public static String removeLastChar(String str) {
        String output = "";
        if (str != null && !str.isEmpty()) {
            output = str.substring(0, str.length() - 1);
        }
        return output;
    }

    /**
     * Adds a specified string to the end of each string in an array and returns the result as a list.
     *
     * @param strings the array of strings
     * @param addStr the string to add
     * @return the list of modified strings
     */
    public static List<String> addEndCharInList(String[] strings, String addStr) {
        List<String> list = new ArrayList<>();

        for (String string : strings) {
            list.add(string + addStr);
        }

        return list;
    }

    /**
     * Adds a specified character to the end of each string in an array and returns the result as a list.
     *
     * @param strings the array of strings
     * @param addStr the character to add
     * @return the list of modified strings
     */
    public static List<String> addEndCharInList(String[] strings, char addStr) {
        return addEndCharInList(strings, String.valueOf(addStr));
    }

    /**
     * Adds a specified string to the start of each string in an array and returns the result as a list.
     *
     * @param strings the array of strings
     * @param addStr the string to add
     * @return the list of modified strings
     */
    public static List<String> addStartCharInList(String[] strings, String addStr) {
        List<String> list = new ArrayList<>();

        for (String string : strings) {
            list.add(addStr + string);
        }

        return list;
    }

    /**
     * Adds a specified character to the start of each string in an array and returns the result as a list.
     *
     * @param strings the array of strings
     * @param addStr the character to add
     * @return the list of modified strings
     */
    public static List<String> addStartCharInList(String[] strings, char addStr) {
        return addStartCharInList(strings, String.valueOf(addStr));
    }

    /**
     * Adds a specified string to the end of a string.
     *
     * @param str the input string
     * @param addStr the string to add
     * @return the modified string
     */
    public static String addLastChar(String str, String addStr) {
        return str + addStr;
    }

    /**
     * Adds a specified character to the end of a string.
     *
     * @param str the input string
     * @param addStr the character to add
     * @return the modified string
     */
    public static String addLastChar(String str, char addStr) {
        return str + addStr;
    }

    /**
     * Adds a specified string to the start of a string.
     *
     * @param str the input string
     * @param addStr the string to add
     * @return the modified string
     */
    public static String addInsertChar(String str, String addStr) {
        return addStr + str;
    }

    /**
     * Adds a specified character to the start of a string.
     *
     * @param str the input string
     * @param addStr the character to add
     * @return the modified string
     */
    public static String addInsertChar(String str, char addStr) {
        return addStr + str;
    }

    /**
     * Fills a string with a specified text if it is empty.
     *
     * @param str the input string
     * @param fillText the text to fill
     * @return the filled string
     */
    public static String fillIfEmpty(String str, String fillText) {
        if (str.isEmpty()) {
            str = fillText;
        }

        return str;
    }

    /**
     * Fills a string with a specified integer if it is empty.
     *
     * @param str the input string
     * @param fillText the integer to fill
     * @return the filled string
     */
    public static String fillIfEmpty(String str, int fillText) {
        return fillIfEmpty(str, Integer.toString(fillText));
    }

    /**
     * Fills a string with a specified float if it is empty.
     *
     * @param str the input string
     * @param fillText the float to fill
     * @return the filled string
     */
    public static String fillIfEmpty(String str, float fillText) {
        return fillIfEmpty(str, Float.toString(fillText));
    }

    /**
     * Fills a string with a specified double if it is empty.
     *
     * @param str the input string
     * @param fillText the double to fill
     * @return the filled string
     */
    public static String fillIfEmpty(String str, Double fillText) {
        return fillIfEmpty(str, Double.toString(fillText));
    }

    /**
     * Converts a char array to a string.
     *
     * @param chars the char array
     * @return the resulting string
     */
    public static String getStringFromChars(char[] chars) {
        String str = null;
        for (char ch : chars) {
            str = addLastChar(str, ch);
        }

        return str;
    }

    /**
     * Converts a char to a string.
     *
     * @param ch the char
     * @return the resulting string
     */
    public static String getStringFromChar(char ch) {
        return Character.toString(ch);
    }

    /**
     * Gets a random character from a string.
     *
     * @param input the input string
     * @return the random character
     */
    public static char getRandomCharacter(String input) {
        Random random = new Random();
        int index = random.nextInt(input.length());
        return input.charAt(index);
    }
}