import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * My implementation of various pattern matching algorithms.
 *
 * @author Ian Andrew McLeod
 * @version 1.0
 *
 */
public class PatternMatching {

    /**
     * Brute force pattern matching algorithm to find all matches.
     *
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> bruteForce(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("The pattern you entered is null or of length 0.");
        }
        if (text == null || comparator == null) {
            throw new IllegalArgumentException("The text or comparator you entered is null.");
        }
        List<Integer> myList = new ArrayList<>();
        for (int t = 0; t <= (text.length() - pattern.length()); t++) {
            int i = 0;
            while (i <= pattern.length() - 1) {
                if (comparator.compare(pattern.charAt(i), text.charAt(i + t)) == 0) {
                    if (i < pattern.length() - 1) {
                        i++;
                        continue;
                    } else {
                        myList.add(t);
                        break;
                    }
                } else {
                    break;
                }
            }
        }

        return myList;

    }

    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     *
     *
     * @param pattern    a pattern you're building a failure table for
     * @param comparator you MUST use this for checking character equality
     * @return integer array holding your failure table
     * @throws java.lang.IllegalArgumentException if the pattern or comparator
     *                                            is null
     */
    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {

        if (pattern == null || comparator == null) {
            throw new IllegalArgumentException("The pattern or comparator is null. Please enter existing parameters.");
        }

        int[] myArray = new int[pattern.length()];
        if (pattern.length() == 0) {
            return myArray;
        }
        myArray[0] = 0;
        int i = 0;
        int j = 1;
        while (j < pattern.length()) {

            if (comparator.compare(pattern.charAt(i), pattern.charAt(j)) == 0) {
                i++;
                myArray[j] = i;
                j++;
            } else if (i == 0) {
                myArray[j] = 0;
                j++;
            } else {
                i = myArray[i - 1];
            }
        }

        return myArray;

    }


    /**
     * Knuth-Morris-Pratt (KMP) algorithm that relies on the failure table (also
     * called failure function). Works better with small alphabets.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text,
                                    CharacterComparator comparator) {

        if (pattern == null || comparator == null) {
            throw new IllegalArgumentException("The pattern or comparator is null. Please enter existing parameters.");
        }
        if (text == null || pattern.length() == 0) {
            throw new IllegalArgumentException("The text is null or the length of the given pattern is 0."
                    + " Please enter existing text with a pattern of atleast 1.");
        }

        List<Integer> myList = new ArrayList<>();
        if (pattern.length() > text.length()) {
            return myList;
        }
        int[] f = buildFailureTable(pattern, comparator);
        int i = 0;
        int j = 0;
        while (i <= (text.length() - pattern.length())) {
            while (j < pattern.length() && comparator.compare(text.charAt(i + j), pattern.charAt(j)) == 0) {
                j++;
            }

            if (j == 0) {
                i++;
            } else {
                if (j == pattern.length()) {
                    myList.add(i);
                }
                int nextAlignment = f[j - 1];
                i = i + j - nextAlignment;
                j = nextAlignment;
            }
        }

        return myList;

    }

    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     *
     * If the pattern is empty, return an empty map.
     *
     * @param pattern a pattern you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     * to their last occurrence in the pattern
     * @throws java.lang.IllegalArgumentException if the pattern is null
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("The pattern is null. Please enter an existing pattern.");
        }

        int m = pattern.length();
        HashMap<Character, Integer> last = new HashMap<>();
        for (int i = 0; i <= (m - 1); i++) {
            last.put(pattern.charAt(i), i);
        }
        return last;

    }

    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     *
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {

        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("The pattern is null or of length 0. Please enter a valid pattern.");
        }

        if (text == null || comparator == null) {
            throw new IllegalArgumentException("The text or comparator you entered is null.");
        }

        List<Integer> myList = new ArrayList<>();
        Map<Character, Integer> last = buildLastTable(pattern);
        int i = 0;
        while (i <= text.length() - pattern.length()) {
            int j = pattern.length() - 1;
            while (j >= 0 && comparator.compare(text.charAt(i + j), pattern.charAt(j)) == 0) {
                j--;
            }
            if (j == -1) {
                myList.add(i);
                i++;
            } else {
                int shift;
                if (last.get(text.charAt(i + j)) == null) {
                    shift = -1;
                } else {
                    shift = last.get(text.charAt(i + j));
                }
                if (shift < j) {
                    i = i + j - shift;
                } else {
                    i++;
                }
            }
        }

        return myList;

    }
}
