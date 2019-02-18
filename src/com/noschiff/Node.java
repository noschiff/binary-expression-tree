package com.noschiff;

/**
 * Single node that can be apart of a tree
 *
 * @author noschiff
 */
public class Node {
    Node leftChild, rightChild;
    private Value value;

    public Node(Value value) {
        this.value = value;
    }

    public Value getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (leftChild != null ? !leftChild.equals(node.leftChild) : node.leftChild != null) return false;
        if (rightChild != null ? !rightChild.equals(node.rightChild) : node.rightChild != null) return false;
        return value != null ? value.equals(node.value) : node.value == null;
    }

}
