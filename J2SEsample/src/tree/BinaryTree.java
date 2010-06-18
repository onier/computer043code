/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tree;

public class BinaryTree {
    // the root node

    private Node root = null;

    // get the root node of the tree
    public Node getRoot() {
        return root;
    }

    public BinaryTree() {
        root = new Node(1);
        root.setLeft(2);
        root.setRight(3);
        root.left.setLeft(4);
        root.left.setRight(5);
         root.right.setLeft(6);
        root.right.setRight(7);
    }
    // add a new node to the tree

    public Node addNode(Comparable content) {
        if (root == null) {
            root = new Node(content);
            return root;
        }
        return addTo(root, content);
    }

    // add a new node to a subtree rooted at n
    private Node addTo(Node n, Comparable c) {
        if (c.compareTo(n.getContent()) < 0) {
            Node l = n.getLeft();
            if (l == null) {
                return n.setLeft(c);
            } else {
                return addTo(l, c);
            }
        } else {
            Node r = n.getRight();
            if (r == null) {
                return n.setRight(c);
            } else {
                return addTo(r, c);
            }
        }
    }

    /**
     * A class representing one node of the tree.
     */
    public class Node {

        private Comparable content = null;
        private Node left = null;
        private Node right = null;

        public Node(Comparable c) {
            this.content = c;
        }

        public Comparable getContent() {
            return content;
        }

        public Node getLeft() {
            return left;
        }

        public Node setLeft(Comparable content) {
            left = new Node(content);
            return left;
        }

        public Node getRight() {
            return right;
        }

        public Node setRight(Comparable content) {
            right = new Node(content);
            return right;
        }
    }
}

