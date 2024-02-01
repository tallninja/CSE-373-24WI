package problems;

import datastructures.IntTree;
// Checkstyle will complain that this is an unused import until you use it in your code.

/**
 * See the spec on the website for tips and example behavior.
 *
 * Also note: you may want to use private helper methods to help you solve these problems.
 * YOU MUST MAKE THE PRIVATE HELPER METHODS STATIC, or else your code will not compile.
 * This happens for reasons that aren't the focus of this assignment and are mostly skimmed over in
 * 142 and 143. If you want to know more, you can ask on the discussion board or at office hours.
 *
 * REMEMBER THE FOLLOWING RESTRICTIONS:
 * - do not call any methods on the `IntTree` objects
 * - do not construct new `IntTreeNode` objects (though you may have as many `IntTreeNode` variables
 *      as you like).
 * - do not construct any external data structures such as arrays, queues, lists, etc.
 * - do not mutate the `data` field of any node; instead, change the tree only by modifying
 *      links between nodes.
 */

public class IntTreeProblems {

    /**
     * Computes and returns the sum of the values multiplied by their depths in the given tree.
     * (The root node is treated as having depth 1.)
     */
    public static int depthSum(IntTree tree) {
        return depthSumHelper(tree.overallRoot, 1);
    }

    private static int depthSumHelper(IntTree.IntTreeNode node, int depth) {
        if (node == null) {
            return 0;
        }

        return depth * node.data +
            depthSumHelper(node.left, depth + 1) +
            depthSumHelper(node.right, depth + 1);
    }

    /**
     * Removes all leaf nodes from the given tree.
     */
    public static void removeLeaves(IntTree tree) {
        tree.overallRoot = removeLeavesHelper(tree.overallRoot);
    }

    private static IntTree.IntTreeNode removeLeavesHelper(IntTree.IntTreeNode node) {
        if (node == null) {
            return null;
        }

        // If the node is a leaf, return null to remove it
        if (node.left == null && node.right == null) {
            return null;
        }

        // Recursively remove leaves from left and right subtrees
        node.left = removeLeavesHelper(node.left);
        node.right = removeLeavesHelper(node.right);

        return node;
    }

    /**
     * Removes from the given BST all values less than `min` or greater than `max`.
     * (The resulting tree is still a BST.)
     */
    public static void trim(IntTree tree, int min, int max) {
        tree.overallRoot = trimHelper(tree.overallRoot, min, max);
    }

    private static IntTree.IntTreeNode trimHelper(IntTree.IntTreeNode node, int min, int max) {
        if (node == null) {
            return null;
        }

        // If the node's data is within the range, recursively trim left and right subtrees
        if (node.data >= min && node.data <= max) {
            node.left = trimHelper(node.left, min, max);
            node.right = trimHelper(node.right, min, max);
            return node;
        }

        // If the node's data is less than the minimum, trim right subtree
        if (node.data < min) {
            return trimHelper(node.right, min, max);
        }

        // If the node's data is greater than the maximum, trim left subtree
        return trimHelper(node.left, min, max);
    }
}
