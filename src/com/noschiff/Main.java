package com.noschiff;

import java.util.Arrays;
import java.util.Stack;

/**
 * Driver class to test the trees
 * @author noschiff
 */
public class Main {

    public static void main(String[] args) {
//        //hard code using lambda (InitTree interface)
//        Tree lambdaTree = new Tree(() -> {
//            Node root = new Node(Operator.add);
//            root.leftChild = new Node(Operator.exp);
//            root.leftChild.leftChild = new Node(5);
//            root.leftChild.rightChild = new Node(2);
//            root.rightChild = new Node(Operator.mult);
//            root.rightChild.rightChild = new Node(2);
//            root.rightChild.leftChild = new Node(5);
//            return root;
//        });
//        System.out.println(lambdaTree);
//
//        //build using postfix string
//        Tree pre = new Tree("+ ^ 3.0 2.0 × 5.0 4.0", Form.prefix);
//        Tree post = new Tree("3.0 2.0 ^ 5.0 4.0 × +", Form.postfix);
//        System.out.println(post);
//        System.out.println(pre);
//        System.out.println(post.equals(pre));
//
//        System.out.println(preToPost("+/ 34.5 2 9"));
//        System.out.println(postToPre("34.5 2 / 9.0 +"));
//    }
//
//    private static String preToPost(String pre) {
//        return new Tree(pre, Form.prefix).postfix();
//    }
//    private static String postToPre(String post) {
//        return new Tree(post, Form.postfix).prefix();

        String test = "3 + (4 / 5)";
        char[] input = test.toCharArray();
        StringBuilder postfix = new StringBuilder();
        Stack<Character> operatorStack = new Stack<>();

        for (int i = 0; i < input.length; i++) {
            //character is an operator
            if (Operator.isOperator(input[i])) {
                try {
                    while ((operatorStack.peek() != '(') &&
                            ((!Operator.charToOperator(input[i]).greaterPrescedenceThan(Operator.charToOperator(operatorStack.peek())))
                                    || (Operator.charToOperator(input[i]).equalPrescedence(Operator.charToOperator(operatorStack.peek())) && Operator.charToOperator(operatorStack.peek()).leftAssociative()))) {
                        postfix.append(operatorStack.pop());
                    }
                } catch (Exception e) {}
                operatorStack.push(input[i]);
            } else if (input[i] == '(') {
                operatorStack.push(input[i]);
            } else if (input[i] == ')') {
                while (operatorStack.peek() != '(') {
                    postfix.append(operatorStack.pop());
                }
                operatorStack.push(input[i]);
            } else {
                postfix.append(input[i]);
            }
        }
        while (!operatorStack.isEmpty()) {
            if (operatorStack.peek() == ')' || operatorStack.peek() == '(') {
                operatorStack.pop();
            } else {
                postfix.append(operatorStack.pop());
            }
        }
        System.out.println(test);
        System.out.println(postfix);
        //initFromExpr(postfix, Form.postfix);
    }
}
