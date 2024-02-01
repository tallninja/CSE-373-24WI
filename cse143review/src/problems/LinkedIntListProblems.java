package problems;

import datastructures.LinkedIntList;
// Checkstyle will complain that this is an unused import until you use it in your code.

/**
 * See the spec on the website for example behavior.
 *
 * REMEMBER THE FOLLOWING RESTRICTIONS:
 * - do not call any methods on the `LinkedIntList` objects.
 * - do not construct new `ListNode` objects for `reverse3` or `firstToLast`
 *      (though you may have as many `ListNode` variables as you like).
 * - do not construct any external data structures such as arrays, queues, lists, etc.
 * - do not mutate the `data` field of any node; instead, change the list only by modifying
 *      links between nodes.
 */

public class LinkedIntListProblems {

    /**
     * Reverses the 3 elements in the `LinkedIntList` (assume there are exactly 3 elements).
     */
    public static void reverse3(LinkedIntList list) {
        if (list != null && list.front != null && list.front.next != null && list.front.next.next != null) {
            // Store references to the three nodes
            LinkedIntList.ListNode first = list.front;
            LinkedIntList.ListNode second = first.next;
            LinkedIntList.ListNode third = second.next;

            // Update links to reverse the order
            list.front = third;
            second.next = first;
            first.next = third.next;  // Update the link to the third node

            // Adjust the link from the third node
            third.next = second;
        }
    }

    /**
     * Moves the first element of the input list to the back of the list.
     */
    public static void firstToLast(LinkedIntList list) {
        if (list != null && list.front != null && list.front.next != null) {
            // Store references to the first and second nodes
            LinkedIntList.ListNode first = list.front;
            LinkedIntList.ListNode second = first.next;

            // Update links to move the first node to the back
            list.front = second;
            while (second.next != null) {
                second = second.next;
            }
            second.next = first;
            first.next = null;  // Set the original first's next to null to avoid cycles
        }
    }

    /**
     * Returns a list consisting of the integers of a followed by the integers
     * of n. Does not modify items of A or B.
     */
    public static LinkedIntList concatenate(LinkedIntList a, LinkedIntList b) {
        LinkedIntList result = new LinkedIntList();

        // Copy elements from list a to the result
        if (a != null && a.front != null) {
            result.front = copyList(a.front);
        }

        // Copy elements from list b to the result
        if (b != null && b.front != null) {
            if (result.front == null) {
                result.front = copyList(b.front);
            } else {
                LinkedIntList.ListNode currentResult = result.front;
                while (currentResult.next != null) {
                    currentResult = currentResult.next;
                }

                currentResult.next = copyList(b.front);
            }
        }

        return result;
    }

    private static LinkedIntList.ListNode copyList(LinkedIntList.ListNode node) {
        LinkedIntList.ListNode newHead = null;
        LinkedIntList.ListNode tail = null;

        while (node != null) {
            LinkedIntList.ListNode newNode = new LinkedIntList.ListNode(node.data);
            if (newHead == null) {
                newHead = newNode;
                tail = newNode;
            } else {
                tail.next = newNode;
                tail = newNode;
            }

            node = node.next;
        }

        return newHead;
    }
}
