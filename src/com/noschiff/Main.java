package com.noschiff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/**
 * Driver class to test the trees
 *
 * @author noschiff
 */
public class Main {

    public static void main(String[] args) {
/*
        //hard code using lambda (InitTree interface)
        Tree lambdaTree = new Tree(() -> {
            Node root = new Node(Operator.add);
            root.leftChild = new Node(Operator.exp);
            root.leftChild.leftChild = new Node(5);
            root.leftChild.rightChild = new Node(2);
            root.rightChild = new Node(Operator.mult);
            root.rightChild.rightChild = new Node(2);
            root.rightChild.leftChild = new Node(5);
            return root;
        });
        System.out.println(lambdaTree);
*/
        //build using postfix string
        Tree pre = new Tree("+ - ^ 9.0 0.5 2.0 × 5.0 4.0", Form.prefix);
        Tree post = new Tree("9.0 0.5 ^ 2.0 - 5.0 4.0 × +", Form.postfix);
        System.out.println(post);
        System.out.println(pre);
        Tree infix = new Tree("(((9.0 ^ 0.5) - 2.0) + (5.0 × 4.0))", Form.infix);
        System.out.println(infix);

        System.out.println(pre.equals(infix));

        String expr = "((3.0 ^ 2.0) + (5.0 × 4.0))";
        System.out.println(expr + " from infix to postfix: " + convert(expr, Form.infix, Form.postfix));
    }

    private static String convert(String expression, Form from, Form to) {
        return new Tree(expression, from).getForm(to);
    }
}
