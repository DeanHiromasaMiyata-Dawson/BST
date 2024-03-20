import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Your implementation of a BST.
 *
 * @author Dean Miyata-Dawson
 * @version 1.0
 * @userid ddawson42
 * @GTID 903833148
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class BST<T extends Comparable<? super T>> {
	/*
	 * Do not add new instance variables or modify existing ones.
	 */
	private BSTNode<T> root;
	private int size;

	/**
	 * Constructs a new BST.
	 * <p>
	 * This constructor should initialize an empty BST.
	 * <p>
	 * Since instance variables are initialized to their default values, there
	 * is no need to do anything for this constructor.
	 */
	public BST() {
		// DO NOT IMPLEMENT THIS CONSTRUCTOR!
	}

	/**
	 * Constructs a new BST.
	 *
	 * This constructor should initialize the BST with the data in the
	 * Collection. The data should be added in the same order it is in the
	 * Collection.
	 *
	 * Hint: Not all Collections are indexable like Lists, so a regular for loop
	 * will not work here. However, all Collections are Iterable, so what type
	 * of loop would work?
	 *
	 * @param data the data to add
	 * @throws java.lang.IllegalArgumentException if data or any element in data
	 *                                            is null
	 */
	public BST(Collection<T> data) {
		if (data == null || data.contains(null)) {
			throw new IllegalArgumentException("Error: data is empty.");
		}

		size = 0;

		for (T temp : data) {
			add(temp);
		}
	}

	/**
	 * Adds the data to the tree.
	 *
	 * This must be done recursively.
	 *
	 * The data becomes a leaf in the tree.
	 *
	 * Traverse the tree to find the appropriate location. If the data is
	 * already in the tree, then nothing should be done (the duplicate
	 * shouldn't get added, and size should not be incremented).
	 *
	 * Must be O(log n) for best and average cases and O(n) for worst case.
	 *
	 * @param data the data to add
	 * @throws java.lang.IllegalArgumentException if data is null
	 */
	public void add(T data) {
		if (data == null) {
			throw new IllegalArgumentException("Error: data is empty.");
		}

		if (root == null) {
			root = new BSTNode<>(data);
			size++;
		} else {
			addHelper(data, root);
		}
	}

	/**
	 * helper method for add
	 * @param data data user wants to add
	 * @param node current node
	 */
	private void addHelper(T data, BSTNode<T> node) {
		if (data.compareTo(node.getData()) < 0) {
			if (node.getLeft() == null) {
				node.setLeft(new BSTNode<>(data));
				size++;
			} else {
				addHelper(data, node.getLeft());
			}
		} else if (data.compareTo(node.getData()) > 0) {
			if (node.getRight() == null) {
				node.setRight(new BSTNode<>(data));
				size++;
			} else {
				addHelper(data, node.getRight());
			}
		}
	}

	/**
	 * Removes and returns the data from the tree matching the given parameter.
	 *
	 * This must be done recursively.
	 *
	 * There are 3 cases to consider:
	 * 1: The node containing the data is a leaf (no children). In this case,
	 * simply remove it.
	 * 2: The node containing the data has one child. In this case, simply
	 * replace it with its child.
	 * 3: The node containing the data has 2 children. Use the predecessor to
	 * replace the data. You MUST use recursion to find and remove the
	 * predecessor (you will likely need an additional helper method to
	 * handle this case efficiently).
	 *
	 * Do not return the same data that was passed in. Return the data that
	 * was stored in the tree.
	 *
	 * Hint: Should you use value equality or reference equality?
	 *
	 * Must be O(log n) for best and average cases and O(n) for worst case.
	 *
	 * @param data the data to remove
	 * @return the data that was removed
	 * @throws java.lang.IllegalArgumentException if data is null
	 * @throws java.util.NoSuchElementException   if the data is not in the tree
	 */
	public T remove(T data) {
		if (data == null) {
			throw new IllegalArgumentException("Error: data is empty.");
		}
		BSTNode<T> removed = new BSTNode<>(null);
		root = removeHelper(data, root, removed);
		return removed.getData();
	}

	/**
	 * helper method for remove
	 * @param data data user wants to remove
	 * @param node current node
	 * @param removed node that got removed
	 * @return node that got removed
	 */
	private BSTNode<T> removeHelper(T data, BSTNode<T> node, BSTNode<T> removed) {
		if (node == null) {
			throw new NoSuchElementException("Error: data is not in this tree.");
		}
		if (data.compareTo(node.getData()) < 0) {
			node.setLeft(removeHelper(data, node.getLeft(), removed));
		} else if (data.compareTo(node.getData()) > 0) {
			node.setRight(removeHelper(data, node.getRight(), removed));
		} else {
			removed.setData(node.getData());
			size--;
			if (node.getLeft() == null) {
				return node.getRight();
			} else if (node.getRight() == null) {
				return node.getLeft();
			} else {
				BSTNode<T> child = new BSTNode<>(null);
				node.setLeft(predecessorHelper(node.getLeft(), child));
				node.setData(child.getData());
			}
		}
		return node;
	}

	/**
	 * finds the predecessor the node that is about to get removed
	 * @param node current node, also the node that is going to get removed
	 * @param child predecessor node
	 * @return predecessor node
	 */
	private BSTNode<T> predecessorHelper(BSTNode<T> node, BSTNode<T> child) {
		if (node.getRight() == null) {
			child.setData(node.getData());
			return node.getLeft();
		}
		node.setRight(predecessorHelper(node.getRight(), child));
		return node;
	}

	/**
	 * Returns the data from the tree matching the given parameter.
	 *
	 * This must be done recursively.
	 *
	 * Do not return the same data that was passed in. Return the data that
	 * was stored in the tree.
	 *
	 * Hint: Should you use value equality or reference equality?
	 *
	 * Must be O(log n) for best and average cases and O(n) for worst case.
	 *
	 * @param data the data to search for
	 * @return the data in the tree equal to the parameter
	 * @throws java.lang.IllegalArgumentException if data is null
	 * @throws java.util.NoSuchElementException   if the data is not in the tree
	 */
	public T get(T data) {
		if (data == null) {
			throw new IllegalArgumentException("Error: data is empty.");
		}
		return getHelper(data, root);
	}

	/**
	 * helper method for get
	 * @param data data user wants
	 * @param node current node
	 * @return data user wants
	 */
	private T getHelper(T data, BSTNode<T> node) {
		if (node == null) {
			throw new NoSuchElementException("Error: data is not in this tree.");
		}

		if (data.equals(node.getData())) {
			return node.getData();
		} else if (data.compareTo(node.getData()) < 0) {
			return getHelper(data, node.getLeft());
		} else if (data.compareTo(node.getData()) > 0) {
			return getHelper(data, node.getRight());
		}
		return null;
	}

	/**
	 * Returns whether or not data matching the given parameter is contained
	 * within the tree.
	 *
	 * This must be done recursively.
	 *
	 * Hint: Should you use value equality or reference equality?
	 *
	 * Must be O(log n) for best and average cases and O(n) for worst case.
	 *
	 * @param data the data to search for
	 * @return true if the parameter is contained within the tree, false
	 * otherwise
	 * @throws java.lang.IllegalArgumentException if data is null
	 */
	public boolean contains(T data) {
		if (data == null) {
			throw new IllegalArgumentException("Error: data is empty.");
		}

		return get(data) != null;
	}

	/**
	 * Generate a pre-order traversal of the tree.
	 *
	 * This must be done recursively.
	 *
	 * Must be O(n).
	 *
	 * @return the preorder traversal of the tree
	 */
	public List<T> preorder() {
		List<T> list = new ArrayList<>();
		preorderHelper(list, root);
		return list;
	}

	/**
	 * helper method for preorder
	 * @param list BST in preorder
	 * @param node current node
	 */
	private void preorderHelper(List<T> list, BSTNode<T> node) {
		if (node == null) {
			return;
		} else {
			list.add(node.getData());
			preorderHelper(list, node.getLeft());
			preorderHelper(list, node.getRight());
		}
	}

	/**
	 * Generate an in-order traversal of the tree.
	 *
	 * This must be done recursively.
	 *
	 * Must be O(n).
	 *
	 * @return the inorder traversal of the tree
	 */
	public List<T> inorder() {
		List<T> list = new ArrayList<>();
		inorderHelper(list, root);
		return list;
	}

	/**
	 * helper method for inorder
	 * @param list BST in inorder
	 * @param node current node
	 */
	private void inorderHelper(List<T> list, BSTNode<T> node) {
		if (node != null) {
			inorderHelper(list, node.getLeft());
			list.add(node.getData());
			inorderHelper(list, node.getRight());
		}
	}

	/**
	 * Generate a post-order traversal of the tree.
	 *
	 * This must be done recursively.
	 *
	 * Must be O(n).
	 *
	 * @return the postorder traversal of the tree
	 */
	public List<T> postorder() {
		List<T> list = new ArrayList<>();
		postorderHelper(list, root);
		return list;
	}

	/**
	 * helper method for postorder
	 * @param list BST in postorder
	 * @param node current node
	 */
	private void postorderHelper(List<T> list, BSTNode<T> node) {
		if (node != null) {
			postorderHelper(list, node.getLeft());
			postorderHelper(list, node.getRight());
			list.add(node.getData());
		}
	}

	/**
	 * Generate a level-order traversal of the tree.
	 *
	 * This does not need to be done recursively.
	 *
	 * Hint: You will need to use a queue of nodes. Think about what initial
	 * node you should add to the queue and what loop / loop conditions you
	 * should use.
	 *
	 * Must be O(n).
	 *
	 * @return the level order traversal of the tree
	 */
	public List<T> levelorder() {
		Queue<BSTNode<T>> queue = new LinkedList<>();
		List<T> list = new ArrayList<>();

		queue.add(root);

		while (!(queue.isEmpty())) {
			BSTNode<T> temp = queue.poll();
			list.add(temp.getData());

			if (temp.getLeft() != null) {
				queue.add(temp.getLeft());
			}

			if (temp.getRight() != null) {
				queue.add(temp.getRight());
			}
		}
		return list;
	}

	/**
	 * Returns the height of the root of the tree.
	 *
	 * This must be done recursively.
	 *
	 * A node's height is defined as max(left.height, right.height) + 1. A
	 * leaf node has a height of 0 and a null child has a height of -1.
	 *
	 * Must be O(n).
	 *
	 * @return the height of the root of the tree, -1 if the tree is empty
	 */
	public int height() {
		return heightHelper(root);
	}

	/**
	 * helper method for height
	 * @param node current node
	 * @return height of BST
	 */
	private int heightHelper(BSTNode<T> node) {
		if (node == null) {
			return -1;
		}

		return Math.max(heightHelper(node.getLeft()), heightHelper(node.getRight())) + 1;
	}

	/**
	 * Clears the tree.
	 *
	 * Clears all data and resets the size.
	 *
	 * Must be O(1).
	 */
	public void clear() {
		root = null;
		size = 0;
	}

	/**
	 * Generates a list of the max data per level from the top to the bottom
	 * of the tree. (Another way to think about this is to get the right most
	 * data per level from top to bottom.)
	 *
	 * This must be done recursively.
	 *
	 * This list should contain the last node of each level.
	 *
	 * If the tree is empty, an empty list should be returned.
	 *
	 * Ex:
	 * Given the following BST composed of Integers
	 *      2
	 *    /   \
	 *   1     4
	 *  /     / \
	 * 0     3   5
	 * getMaxDataPerLevel() should return the list [2, 4, 5] - 2 is the max
	 * data of level 0, 4 is the max data of level 1, and 5 is the max data of
	 * level 2
	 *
	 * Ex:
	 * Given the following BST composed of Integers
	 *               50
	 *           /        \
	 *         25         75
	 *       /    \
	 *      12    37
	 *     /  \    \
	 *   11   15   40
	 *  /
	 * 10
	 * getMaxDataPerLevel() should return the list [50, 75, 37, 40, 10] - 50 is
	 * the max data of level 0, 75 is the max data of level 1, 37 is the
	 * max data of level 2, etc.
	 *
	 * Must be O(n).
	 *
	 * @return the list containing the max data of each level
	 */

	public List<T> getMaxDataPerLevel() {
		List<T> list = new ArrayList<>();
		int maxLevel = 0;
		int level = 0;

		list.add(root.getData());
		while (level < height() - 1) {
			list.add(getMaxDataPerLevelHelper(root, maxLevel, level));
		}

		return list;
	}

	/**
	 * helper method for getMaxDataPerLevel
	 * @param list final list of max data from each level
	 * @param node current node
	 * @param maxLevel levels recorded in the list so far
	 * @param level current depth
	 */
	private T getMaxDataPerLevelHelper(BSTNode<T> node, int maxLevel, int level) {
		if (node == null) {
			return null;
		}

		if (maxLevel < level) {
			return node.getData();
			maxLevel = level;
		}
		getMaxDataPerLevelHelper(node.getRight(), maxLevel, level + 1);
		getMaxDataPerLevelHelper(node.getLeft(), maxLevel, level + 1);

		return null;
	}

	/**
	 * Returns the root of the tree.
	 *
	 * For grading purposes only. You shouldn't need to use this method since
	 * you have direct access to the variable.
	 *
	 * @return the root of the tree
	 */
	public BSTNode<T> getRoot() {
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
