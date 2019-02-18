package com.noschiff;

/**
 * Driver class to test the trees
 *
 * @author noschiff
 */
public class Main {

    public static void main(String[] args) {

        //hard code using lambda (InitTree interface)
        Tree lambdaTree = new Tree(() -> {
            Node root = new Node(new Operation(Operator.add));

            root.leftChild = new Node(new Operation(Operator.sub));
            root.leftChild.leftChild = new Node(new Operation(Operator.exp));
            root.leftChild.leftChild.leftChild = new Node(new Number(9.0));
            root.leftChild.leftChild.rightChild = new Node(new Number(0.5));
            root.leftChild.rightChild = new Node(new Number(2.0));

            root.rightChild = new Node(new Operation(Operator.mult));
            root.rightChild.leftChild = new Node(new Number(5.0));
            root.rightChild.rightChild = new Node(new Number(-4.0));

            return root;
        });
        System.out.println(lambdaTree);

        //build using string
        Tree post = new Tree("9.0 0.5 ^ 2.0 - 5.0 -4.0 × +", Form.postfix);
        Tree pre = new Tree("+ - ^ 9.0 0.5 2.0 × 5.0 -4.0", Form.prefix);
        Tree infix = new Tree("(((9.0 ^ 0.5) - 2.0) + (5.0 × -4.0))", Form.infix);
        System.out.println(post);
        System.out.println(pre);
        System.out.println(infix);

        Element[] inputArr = Element.parseExpression("+ - ^ 9.0 0.5 2.0 × 5.0 -4.0");
        Tree elementArr = new Tree(inputArr, Form.prefix);
        System.out.println(elementArr);

        //checks if the trees are equal
        System.out.println(post.equals(pre) && pre.equals(infix) && infix.equals(elementArr) && elementArr.equals(lambdaTree));
    }

    private static String convert(String expression, Form from, Form to) {
        return new Tree(expression, from).getForm(to);
    }
}
