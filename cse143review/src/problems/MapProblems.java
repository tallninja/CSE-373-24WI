package problems;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * See the spec on the website for example behavior.
 */
public class MapProblems {

    /**
     * Returns true if any string appears at least 3 times in the given list; false otherwise.
     */
    public static boolean contains3(List<String> list) {
        if (list == null || list.isEmpty()) {
            return false;  // Return false for empty or null input list
        }

        Map<String, Integer> countMap = new HashMap<>();

        for (String str : list) {
            countMap.put(str, countMap.getOrDefault(str, 0) + 1);
            if (countMap.get(str) >= 3) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns a map containing the intersection of the two input maps.
     * A key-value pair exists in the output iff the same key-value pair exists in both input maps.
     */
    public static Map<String, Integer> intersect(Map<String, Integer> m1, Map<String, Integer> m2) {
        Map<String, Integer> result = new HashMap<>();

        for (Map.Entry<String, Integer> entry : m1.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();

            if (m2.containsKey(key) && m2.get(key).equals(value)) {
                result.put(key, value);
            }
        }

        return result;
    }
}
