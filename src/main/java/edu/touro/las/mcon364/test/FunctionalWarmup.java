package edu.touro.las.mcon364.test;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FunctionalWarmup {

    /**
     * Problem 1
     * Return a Supplier that gives the current month number (1-12).
     */
    public static Supplier<Integer> currentMonthSupplier() {

        Supplier<Integer> currentMonth = () -> LocalDate.now().getMonthValue();
        return currentMonth;

    }

    /**
     * Problem 2
     * Return a Predicate that is true only when the input string
     * has more than 5 characters.
     */
    public static Predicate<String> longerThanFive() {

        Predicate<String> isLongerThanFive = str -> str.length() > 5;
        return isLongerThanFive;
    }

    /**
     * Problem 3
     * Return a Predicate that checks whether a number is both:
     * - positive
     * - even
     *
     * Prefer chaining smaller predicates.
     */
    public static Predicate<Integer> positiveAndEven() {
        Predicate<Integer> isPositive = str -> str > 0;
        Predicate<Integer> isEven = str -> str % 2 == 0;
        Predicate<Integer> combined =  isPositive.and(isEven);
        return combined;
    }

    /**
     * Problem 4
     * Return a Function that counts words in a string.
     *
     * Notes:
     * - Trim first.
     * - Blank strings should return 0.
     * - Words are separated by one or more spaces (use can use regex "\\s+")
     *
     */
    public static Function<String, Integer> wordCounter() {
        Function<String, String> trim = str -> str.trim();
        Predicate<String> isBlank = str -> str.length() == 0;
        Function<String, Integer> split = str -> str.split("\\s").length;
        Function<String, Integer> countWordsCombined = trim.andThen(split);
        return countWordsCombined;

    }

    /**
     * Problem 5
     * Process the input labels as follows:
     * - remove blank strings
     * - trim whitespace
     * - convert to uppercase
     * - return the final list in the same relative order
     *
     * Example:
     * ["  math ", "", " java", "  "] -> ["MATH", "JAVA"]
     */
    public static List<String> cleanLabels(List<String> labels) {
        Predicate<String> isBlank = str -> str.length() == 0;
        Function<String, String> trim = str -> str.trim();
        Function<String, String> toUpperCase = str -> str.toUpperCase();

        for (String label : labels) {
            if (isBlank.test(label)) {
                labels.remove(label);
            }
            String trimmed = trim.apply(label);
            toUpperCase.apply(trimmed);

        }
        return labels;
    }
}
