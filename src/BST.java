import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Implementation of a BST.
 */
public class BST<T extends Comparable<? super T>> {
	private BSTNode<T> root;
	private int size;

	/**
	 * Constructs a new BST.
	 * <p>
	 * Initializes an empty BST.
	 * <p>
	 * Since instance variables are initialized to their default values, there
	 * is no need to do anything for this constructor.
	 */
	public BST() {
		
	}

	/**
	 * Constructs a new BST.
	 *
	 * Initializes the BST with the data in the Collection. 
  	 * Data is added in the same order it is in the Collection.
	 *
	 * @param data the data to add
	 * @throws java.lang.IllegalArgumentException if data or any element in data is null
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
	 * Adds the data to the tree recursively.
	 *
	 * The data becomes a leaf in the tree.
	 *
	 * Traverses the tree to find the appropriate location. 
  	 * If the data isalready in the tree, then nothing should be done
	 * Duplicate does not get added, and size is not incremented.
	 *
	 * O(log n) for best and average cases and O(n) for worst case.
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
  	 *
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
	 * Recursively removes and returns the data from the tree matching the given parameter 
	 *
	 * 3 cases:
	 * 1: The node containing the data is a leaf (no children). In this case,
	 * simply remove it.
	 * 2: The node containing the data has one child. In this case, simply
	 * replace it with its child.
	 * 3: The node containing the data has 2 children. Use the predecessor to
	 * replace the data. You MUST use recursion to find and remove the
	 * predecessor (you will likely need an additional helper method to
	 * handle this case efficiently).
	 *
	 * O(log n) for best and average cases and O(n) for worst case.
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
  	 *
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
  	 *
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
	 * Recursively Returns the data from the tree matching the given parameter.
	 * O(log n) for best and average cases and O(n) for worst case.
	 *
	 * @param data the data to search for
	 * @return the data in the tree equal to the parameter
	 * @throws java.lang.IllegalArgumentException if data is null
	 * @throws java.util.NoSuchElementException if the data is not in the tree
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
	 * Recursively returns whether or not data matching the given parameter is contained
	 * within the tree.
	 *
	 * O(log n) for best and average cases and O(n) for worst case.
	 *
	 * @param data the data to search for
	 * @return true if the parameter is contained within the tree, false otherwise
	 * @throws java.lang.IllegalArgumentException if data is null
	 */
	public boolean contains(T data) {
		if (data == null) {
			throw new IllegalArgumentException("Error: data is empty.");
		}

		return get(data) != null;
	}

	/**
	 * Recursively generates a pre-order traversal of the tree.
	 *
	 * O(n).
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
	 * Recursively generates an in-order traversal of the tree.
	 *
	 * O(n).
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
	 * Recursively generates a post-order traversal of the tree.
	 *
	 * O(n).
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
	 * O(n).
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
	 * Recursively returns the height of the root of the tree.
	 *
	 * A node's height is defined as max(left.height, right.height) + 1. A
	 * leaf node has a height of 0 and a null child has a height of -1.
	 *
	 * O(n).
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
	 * Returns the root of the tree.
	 *
	 * @return the root of the tree
	 */
	public BSTNode<T> getRoot() {
		return root;
	}

	/**
	 * Returns the size of the tree.
	 *
	 * @return the size of the tree
	 */
	public int size() {
		return size;
	}
}
