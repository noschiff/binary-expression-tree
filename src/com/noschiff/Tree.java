package com.noschiff;

/**
 * A binary tree that represents a mathematical expression
 * @author noschiff
 */
public class Tree {
    //The root node of the tree
    private Node root;

    @Deprecated
    public Tree(Node root) {
        this.root = root;
    }

    /**
     * Constructor to make the tree and initialize it
     * @param initTree - an interface that holds a method to initialize the tree
     */
    public Tree(InitTree initTree) {
        this.root = initTree.initTree();
    }

    /**
     * Evaluates the expression starting from the root
     * @return - evaluation of the entire tree
     */
    public double evaluate() {
        return evaluate(root);
    }

    /**
     * @param node - the node to act on
     * @return - evaluation of the subtree
     */
    private double evaluate(Node node) {
        //If the node is an operator, operate on its children
        if (node.getValue().getType() == Type.operator) {
            //Different operations for each possible operator
            switch (node.getValue().getOperator()) {
                case add:
                    return evaluate(node.leftChild) + evaluate(node.rightChild);
                case sub:
                    return evaluate(node.leftChild) - evaluate(node.rightChild);
                case mult:
                    return evaluate(node.leftChild) * evaluate(node.rightChild);
                case div:
                    return evaluate(node.leftChild) / evaluate(node.rightChild);
                case exp:
                    return Math.pow(evaluate(node.leftChild), evaluate(node.rightChild));
            }
        }
        //If the node is a numeric value, return it
        return node.getValue().getValue();
    }

    public String infix() {
        return infix(root);
    }

    private String infix(Node node) {
        if (node.getValue().getType() == Type.operator) {
            return '(' + infix(node.leftChild) + ' ' + node.getValue().getOperator().toString() + ' ' + infix(node.rightChild) + ')';
        }
        return node.getValue().getValue() + "";
    }

    public String prefix() {
        return prefix(root);
    }

    private String prefix(Node node) {
        if (node.getValue().getType() == Type.operator) {
            return node.getValue().getOperator().toString() + ' ' + prefix(node.leftChild) + ' ' + prefix(node.rightChild);
        }
        return node.getValue().getValue() + "";
    }

    public String postfix() {
        return postfix(root);
    }

    private String postfix(Node node) {
        if (node.getValue().getType() == Type.operator) {
            return postfix(node.leftChild) + ' ' + postfix(node.rightChild) + ' ' + node.getValue().getOperator().toString();
        }
        return node.getValue().getValue() + "";
    }

    /**
     * @return - the tree's values formatted in a String
     * value - evaluated value of the tree
     * infix, prefix, postfix - different representations of the expression
     */
    @Override
    public String toString() {
        return "Tree{" +
                "value=" + evaluate() +
                ", infix=" + infix() +
                ", prefix=" + prefix() +
                ", postfix=" + postfix() +
                '}';
    }
}
