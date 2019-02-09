package com.noschiff;

/**
 * Driver class to test the trees
 * @author noschiff
 */
public class Main {

    public static void main(String[] args) {
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

        //build using postfix string
        Tree pre = new Tree("+ ^ 3.0 2.0 × 5.0 4.0", Form.prefix);
        Tree post = new Tree("3.0 2.0 ^ 5.0 4.0 × +", Form.postfix);
        System.out.println(post);
        System.out.println(pre);
        System.out.println(post.equals(pre));

        System.out.println(preToPost("+/ 34.5 2 9"));
        System.out.println(postToPre("34.5 2 / 9.0 +"));
    }

    private static String preToPost(String pre) {
        return new Tree(pre, Form.prefix).postfix();
    }
    private static String postToPre(String post) {
        return new Tree(post, Form.postfix).prefix();
    }
}
