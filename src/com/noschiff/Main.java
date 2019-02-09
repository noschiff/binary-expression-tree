package com.noschiff;

/**
 * Driver class to test the trees
 * @author noschiff
 */
public class Main {

    public static void main(String[] args) {
        Tree tree = new Tree(() -> {
            Node root = new Node(Operator.add);
            root.leftChild = new Node(3.5);
            root.rightChild = new Node(Operator.div);
            root.rightChild.rightChild = new Node(2);
            root.rightChild.leftChild = new Node(8);
            return root;
        });
        System.out.println(tree);

        Tree tree2 = new Tree(() -> {
            Node root = new Node(Operator.add);
            root.leftChild = new Node(Operator.exp);
            root.leftChild.leftChild = new Node(5);
            root.leftChild.rightChild = new Node(2);
            root.rightChild = new Node(Operator.mult);
            root.rightChild.rightChild = new Node(2);
            root.rightChild.leftChild = new Node(5);
            return root;
        });
        System.out.println(tree2);

    }
}
