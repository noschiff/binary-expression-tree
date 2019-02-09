package com.noschiff;

import java.util.Objects;
import java.util.Stack;

/**
 * A binary tree that represents a mathematical expression
 *
 * @author noschiff
 */
public class Tree {
    //The root node of the tree
    Node root;

    @Deprecated
    public Tree(Node root) {
        this.root = root;
    }

    /**
     * Constructor that takes a postfix expression and initializes the tree
     *
     * @param expression - math expression in postfix form
     */
    public Tree(String expression, Form form) {
        char[] input = expression.toCharArray();
        Stack<Node> stack = new Stack<>();

        switch (form) {
            case Postfix:
                for (int i = 0; i < input.length; i++) {
                    if (Operator.isOperator(input[i])) {
                        Node root = new Node(Operator.charToOperator(input[i]));
                        root.rightChild = stack.pop();
                        root.leftChild = stack.pop();
                        stack.push(root);
                    } else if (input[i] != ' ') {
                        String tempNum = Character.toString(input[i]);
                        while (input[i + 1] != ' ' && i != input.length - 1) {
                            tempNum += input[i + 1];
                            i++;
                        }
                        stack.push(new Node(Double.parseDouble(tempNum)));
                    }
                }
                break;
            case Prefix:
                //TODO
                break;
            case Infix:
                //TODO
                break;
        }

        this.root = stack.pop();
    }

    /**
     * Constructor to make the tree and initialize it
     *
     * @param initTree - an interface that holds a method to initialize the tree
     */
    public Tree(InitTree initTree) {
        this.root = initTree.initTree();
    }

    /**
     * Evaluates the expression starting from the root
     *
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tree tree = (Tree) o;
        return Objects.equals(root, tree.root);
    }

}
