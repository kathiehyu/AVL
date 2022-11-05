import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.HashSet;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * Your implementation of an AVL.
 *
 * @author Kathie Huynh
 * @version 1.0
 * @userid Kathie Huynh
 * @GTID 903669289
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class AVL<T extends Comparable<? super T>> {

    static class AVLPrinter {

        /**
         * Initiates the avl printing
         * @param <T> comparable generic
         * @param root the root of the avl to be printed
         */
        public static <T extends Comparable<? super T>> void printNode(AVLNode<T> root) {
            int maxLevel = AVLPrinter.maxLevel(root);
            printNodeInternal(Collections.singletonList(root), 1, maxLevel);
        }

        /**
         *
         * @param <T> comparable generic
         * @param nodes list containing the nodes to traverse
         * @param level the current level of the avl
         * @param maxLevel the last level of the avl
         */
        private static <T extends Comparable<? super T>> void printNodeInternal(List<AVLNode<T>> nodes,
                                                                                int level, int maxLevel) {
            if (nodes.isEmpty() || AVLPrinter.isAllElementsNull(nodes)) {
                return;
            }

            int floor = maxLevel - level + 1;
            int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
            int firstSpaces = (int) Math.pow(2, (floor)) - 1;
            int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

            AVLPrinter.printWhitespaces(firstSpaces);

            List<AVLNode<T>> newNodes = new ArrayList<AVLNode<T>>();
            for (AVLNode<T> node : nodes) {
                int used = 0;
                if (node != null) {
                    System.out.print(node.getData());
                    used += node.getData().toString().length() - 1;
                    newNodes.add(node.getLeft());
                    newNodes.add(node.getRight());
                } else {
                    newNodes.add(null);
                    newNodes.add(null);
                    System.out.print(" ");
                }

                AVLPrinter.printWhitespaces(betweenSpaces - used);
            }
            System.out.println("");

            for (int i = 1; i <= endgeLines; i++) {
                for (int j = 0; j < nodes.size(); j++) {
                    AVLPrinter.printWhitespaces(firstSpaces - i);
                    if (nodes.get(j) == null) {
                        AVLPrinter.printWhitespaces(endgeLines + endgeLines + i + 1);
                        continue;
                    }

                    if (nodes.get(j).getLeft() != null) {
                        System.out.print("/");
                    } else {
                        AVLPrinter.printWhitespaces(1);
                    }

                    AVLPrinter.printWhitespaces(i + i - 1);

                    if (nodes.get(j).getRight() != null) {
                        System.out.print("\\");
                    } else {
                        AVLPrinter.printWhitespaces(1);
                    }

                    AVLPrinter.printWhitespaces(endgeLines + endgeLines - i);
                }

                System.out.println("");
            }

            printNodeInternal(newNodes, level + 1, maxLevel);
        }

        private static <T extends Comparable<? super T>> int maxLevel(AVLNode<T> node) {
            if (node == null) {
                return 0;
            }

            return Math.max(AVLPrinter.maxLevel(node.getLeft()), AVLPrinter.maxLevel(node.getRight())) + 1;
        }

        private static void printWhitespaces(int count) {
            for (int i = 0; i < count; i++) {
                System.out.print(" ");
            }
        }
        /**
         *
         * @param <T> comparable generic
         * @param list the list to search for non-null elements
         * @return whether or not the list is all null
         */
        private static <T> boolean isAllElementsNull(List<T> list) {
            for (Object object : list) {
                if (object != null) {
                    return false;
                }
            }

            return true;
        }
    }

    public static void main(String[] args) {
        ArrayList<Integer> set = new ArrayList<>();
        set.add(10);
        set.add(12);
        set.add(18);
        AVL<Integer> one = new AVL<>(set);
        AVLPrinter.printNode(one.root);
        System.out.println(one.root.getBalanceFactor());
        set.add(8);
        System.out.println("ADDED 8");
        one = new AVL<>(set);
        AVLPrinter.printNode(one.root);
        set.add(5);
        one = new AVL<>(set);
        AVLPrinter.printNode(one.root);
        set.add(7);
        one = new AVL<>(set);
        AVLPrinter.printNode(one.root);
        AVL<Integer> two = new AVL<>();
        Integer[] desired = { 1, 2, 3, 7, 8, 9, 11, 14, 15, 17, 18, 20, 21, 22, 23, 24};
        for (int i : desired) {
            two.add(i);
        }
        System.out.println(two.size);
        AVLPrinter.printNode(two.root);
        // System.out.println(two.get(12));
        // System.out.println(two.remove(4));
        // AVLPrinter.printNode(two.root);
        // System.out.println(two.get(18));
        // System.out.println(two.successor(23));

        AVL<Integer> tree = new AVL<>();
        tree.add(76);
        tree.add(34);
        tree.add(90);
        tree.add(40);
        tree.add(81);
        AVLPrinter.printNode(two.root);
        System.out.println(tree.successor(81));

    }

    // Do not add new instance variables or modify existing ones.
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot create AVL with null"
                + " Collection");
        }
        for (T item : data) {
            add(item);
        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     * 
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data");
        }
        if (root == null) {
            root = new AVLNode<>(data);
            size++;
            return;
        }
        root = addR(root, data);
    }

    /**
     * Recursively adds data to an AVL.
     * @param current the current node in the method call
     * @param data the data to add
     * @return the root of the subtree of the current call
     */
    private AVLNode<T> addR(AVLNode<T> current, T data) {
        if (current == null) {
            size++;
            AVLNode<T> newNode = new AVLNode<>(data);
            newNode.setHeight(0);
            newNode.setBalanceFactor(0);
            return newNode;
        }
        if (data.compareTo(current.getData()) < 0) {
            current.setLeft(addR(current.getLeft(), data));
        } else if (data.compareTo(current.getData()) > 0) {
            current.setRight(addR(current.getRight(), data));
        }
        updateHBF(current);
        if (current.getBalanceFactor() > 1) {
            if (current.getLeft().getBalanceFactor() > 0) {
                return rotateRight(current);
            } else {
                current.setLeft(rotateLeft(current.getLeft()));
                AVLNode<T> temp = rotateRight(current);
                return temp;
            }
            
        } else if (current.getBalanceFactor() < -1) {
            if (current.getRight().getBalanceFactor() > 0) {
                current.setRight(rotateRight(current.getRight()));
                return rotateLeft(current);
            } else {
                return rotateLeft(current);
            }
        }
        return current;
    }

    /**
     * rotates the subtree to the right.
     * @param a the node at which to rotate
     * @return the new root of the rotated subtree
     */
    private AVLNode<T> rotateRight(AVLNode<T> a) {
        AVLNode<T> b = a.getLeft();
        a.setLeft(b.getRight());
        b.setRight(a);
        updateHBF(a);
        updateHBF(b);
        return b;
    }

    /**
     * rotates the subtree to the left.
     * @param a the node at which to rotate
     * @return the new root of the rotated subtree
     */
    private AVLNode<T> rotateLeft(AVLNode<T> a) {
        AVLNode<T> b = a.getRight();
        a.setRight(b.getLeft());
        b.setLeft(a);
        updateHBF(a);
        updateHBF(b);
        return b;
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data, NOT successor. As a reminder, rotations can occur
     * after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove null data");
        }
        if (root == null) {
            throw new NoSuchElementException("Cannot find data");
        }

        AVLNode<T> dummy = new AVLNode<>(null);
        root = removeR(root, data, dummy);
        size--;
        return dummy.getData();
    }

    /**
     * Recursively removes the node and stores data in dummy node.
     * 
     * @param current the current node
     * @param data the data of the node to remove
     * @param dummy the node to store removed data
     * @return the current node
     */
    private AVLNode<T> removeR(AVLNode<T> current, T data, AVLNode<T> dummy) {
        if (current == null) {
            throw new NoSuchElementException("Cannot find data");
        } else if (current.getData().compareTo(data) > 0) {
            current.setLeft(removeR(current.getLeft(), data, dummy));
        } else if (current.getData().compareTo(data) < 0) {
            current.setRight(removeR(current.getRight(), data, dummy));
        } else {
            dummy.setData(current.getData());
            if (current.getLeft() == null && current.getRight() == null) {
                return null;
            } else if (current.getLeft() == null) {
                return current.getRight();
            } else if (current.getRight() == null) {
                return current.getLeft();
            } else {
                AVLNode<T> preDummy = new AVLNode<>(null);
                current.setLeft(preR(current.getLeft(), preDummy));
                current.setData(preDummy.getData());
            }
        }
        updateHBF(current);
        if (current.getBalanceFactor() > 1) {
            if (current.getLeft().getBalanceFactor() > 0) {
                return rotateRight(current);
            } else {
                current.setLeft(rotateLeft(current.getLeft()));
                return rotateRight(current);
            }
            
        } else if (current.getBalanceFactor() < -1) {
            if (current.getRight().getBalanceFactor() > 0) {
                current.setRight(rotateRight(current.getRight()));
                return rotateLeft(current);
            } else {
                return rotateLeft(current);
            }
        }
        return current;
    }

    /**
     * updates the height and balance factor of a node.
     * @param current the node to update
     */
    private void updateHBF(AVLNode<T> current) {
        current.setHeight(Math.max(
            (current.getLeft() == null ? -1
                : current.getLeft().getHeight()),
            (current.getRight() == null ? -1
                : current.getRight().getHeight())) + 1);
        current.setBalanceFactor(
            (current.getLeft() == null ? -1 : current.getLeft().getHeight())
            - (current.getRight() == null ? -1
                : current.getRight().getHeight()));
    }

    /**
     * recursively finds the predecessor and removes it.
     * 
     * @param current the current node
     * @param dummy the node to store the successor's data
     * @return the successor's right child
     */
    private AVLNode<T> preR(AVLNode<T> current, AVLNode<T> dummy) {
        if (current.getRight() == null) {
            dummy.setData(current.getData());
            return current.getLeft();
        } else {
            current.setRight(sucR(current.getRight(), dummy));
        }
        return current; // doesnt reach ?
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data");
        }
        AVLNode<T> target = getNode(root, data, new AVLNode<>(null));
        if (target == null) {
            throw new NoSuchElementException("Cannot find data " + data);
        } else {
            return target.getData();
        }
    }

    /**
     * Returns the node with data matching the given data recursively.
     * @param current the current node
     * @param data the data to search for
     * @param lastLeft the last node traversed left from
     * @return the node with data that matches the given data
     */
    private AVLNode<T> getNode(AVLNode<T> current, T data, AVLNode<T> lastLeft) {
        if (current == null) {
            return null;
        } else if (data.compareTo(current.getData()) == 0) {
            return current;
        } else if (data.compareTo(current.getData()) < 0) {
            lastLeft.setData(current.getData());
            return getNode(current.getLeft(), data, lastLeft);
        } else {
            return getNode(current.getRight(), data, lastLeft);
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot search for null data");
        }
        return getNode(root, data, new AVLNode<>(null)) != null;
    }

    /**
     * Returns the height of the root of the tree.
     * 
     * Should be O(1). 
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return root.getHeight();
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the data in the deepest node. If there is more than one node
     * with the same deepest depth, return the rightmost (i.e. largest) node with 
     * the deepest depth. 
     * 
     * Should be recursive. 
     *
     * Must run in O(log n) for all cases.
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      3
     *        \
     *         1
     * Max Deepest Node:
     * 1 because it is the deepest node
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      4
     *        \    /
     *         1  3
     * Max Deepest Node:
     * 3 because it is the maximum deepest node (1 has the same depth but 3 > 1)
     *
     * @return the data in the maximum deepest node or null if the tree is empty
     */
    public T maxDeepestNode() {
        return recurseDeepest(root).getData();
    }

    /**
     * recursively finds the deepest node in the AVL.
     * @param current the current node of the method call
     * @return the node with the largest data and deepest depth
     */
    private AVLNode<T> recurseDeepest(AVLNode<T> current) {
        if (current.getHeight() == 0) {
            return current;
        } else if (current.getBalanceFactor() > 0) {
            return recurseDeepest(current.getLeft());
        } else if (current.getBalanceFactor() < 0) {
            return recurseDeepest(current.getRight());
        } else {
            return recurseDeepest(current.getLeft());
        }
    }

    /**
     * In BSTs, you learned about the concept of the successor: the
     * smallest data that is larger than the current data. However, you only
     * saw it in the context of the 2-child remove case.
     *
     * This method should retrieve (but not remove) the successor of the data
     * passed in. There are 2 cases to consider:
     * 1: The right subtree is non-empty. In this case, the successor is the
     * leftmost node of the right subtree.
     * 2: The right subtree is empty. In this case, the successor is the lowest
     * ancestor of the node whose left subtree contains the data. 
     * 
     * The second case means the successor node will be one of the node(s) we 
     * traversed left from to find data. Since the successor is the SMALLEST element 
     * greater than data, the successor node is the lowest/last node 
     * we traversed left from on the path to the data node.
     *
     * This should NOT be used in the remove method.
     * 
     * Should be recursive. 
     *
     * Ex:
     * Given the following AVL composed of Integers
     *                    76
     *                  /    \
     *                34      90
     *                  \    /
     *                  40  81
     * successor(76) should return 81
     * successor(81) should return 90
     * successor(40) should return 76
     *
     * @param data the data to find the successor of
     * @return the successor of data. If there is no larger data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T successor(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot find successor of null");
        }
        AVLNode<T> lastLeft = new AVLNode<>(null);
        AVLNode<T> toFindSuc = getNode(root, data, lastLeft);
        if (toFindSuc == null) {
            throw new NoSuchElementException("Cannot find data");
        }
        if (toFindSuc.getRight() != null) {
            AVLNode<T> dummy = new AVLNode<>(null);
            sucR(toFindSuc.getRight(), dummy);
            return dummy.getData();
        } else {
            return lastLeft.getData();
        }
    }

    /**
     * recursively finds the successor and removes it.
     * 
     * @param current the current node
     * @param dummy the node to store the successor's data
     * @return the successor's right child
     */
    private AVLNode<T> sucR(AVLNode<T> current, AVLNode<T> dummy) {
        if (current.getLeft() == null) {
            dummy.setData(current.getData());
            return current.getRight();
        } else {
            current.setLeft(sucR(current.getLeft(), dummy));
        }
        return current; // doesnt reach ?
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
