package problems;

/**
 * See the spec on the website for example behavior.
 *
 * REMEMBER THE FOLLOWING RESTRICTIONS:
 * - Do not add any additional imports
 * - Do not create new `int[]` objects for `toString` or `rotateRight`
 */
public class ArrayProblems {

    /**
     * Returns a `String` representation of the input array.
     * Always starts with '[' and ends with ']'; elements are separated by ',' and a space.
     */
    public static String toString(int[] array) {
        if (array == null) {
            return "null";
        }

        StringBuilder result = new StringBuilder("[");
        int n = array.length;

        for (int i = 0; i < n; i++) {
            result.append(array[i]);
            if (i < n - 1) {
                result.append(", ");
            }
        }

        result.append("]");
        return result.toString();
    }

    /**
     * Returns a new array containing the input array's elements in reversed order.
     * Does not modify the input array.
     */
    public static int[] reverse(int[] array) {
        if (array == null) {
            return null;  // Return null for null input array
        }

        int n = array.length;
        int[] reversedArray = new int[n];

        for (int i = 0; i < n; i++) {
            reversedArray[i] = array[n - 1 - i];
        }

        return reversedArray;
    }

    /**
     * Rotates the values in the array to the right.
     */
    public static void rotateRight(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }

        int lastElement = array[array.length - 1];

        for (int i = array.length - 1; i > 0; i--) {
            array[i] = array[i - 1];
        }

        array[0] = lastElement;
    }
}
