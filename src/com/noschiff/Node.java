package com.noschiff;

/**
 * Single node that can be apart of a tree
 * @author noschiff
 */
public class Node {
    Node leftChild, rightChild;
    private Value value;

    public Node(Value value) {
        this.value = value;
    }

    public Node(double value) {
        this.value = new Value(value);
    }

    public Node(Operator operator) {
        this.value = new Value(operator);
    }

    public Node(Node leftChild, Node rightChild, Value value) {
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.value = value;
    }

    public Value getValue() {
        return value;
    }

}
