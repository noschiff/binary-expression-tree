package com.noschiff;

import java.util.ArrayList;
import java.util.Stack;

/**
 * A binary tree that represents a mathematical expression
 *
 * @author noschiff
 */
public class Tree {
    //The root node of the tree
    private Node root;

    /**
     * Constructor that takes a single Node to be the root
     *
     * @param root - root of the tree
     */
    @Deprecated
    public Tree(Node root) {
        this.root = root;
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
     * Constructor that takes a mathematical expression and initializes the tree
     *
     * @param expression - math expression stored in String
     * @param form       - the form that the expression is in
     */
    public Tree(String expression, Form form) {
        this.root = initFromExpr(expression, form);
    }

    /**
     * Creates the root Node with all children initialized from
     * a mathematical expression in one of three forms
     *
     * @param expression - math expression stored in String
     * @param form       - the form that the expression is in
     * @return - the root node of the tree
     */
    private Node initFromExpr(String expression, Form form) {
        char[] input = expression.toCharArray();
        Stack<Node> stack = new Stack<>();

        //read input characters based off of form inputted
        switch (form) {
            case postfix:
                //starts at left and moves to the right
                for (int i = 0; i < input.length; i++) {
                    //character is an operator
                    if (Operator.isOperator(input[i])) {
                        //make a new node with the operator
                        Node root = new Node(Operator.valueOfChar(input[i]));

                        //take the top nodes in stack and assign it to be the children
                        root.rightChild = stack.pop();
                        root.leftChild = stack.pop();

                        //put the Node into top of stack
                        stack.push(root);
                    }
                    //character is a number
                    else if (input[i] != ' ') {
                        //String to hold the number in case it is multiple characters long
                        StringBuilder tempNum = new StringBuilder(Character.toString(input[i]));
                        //fill the string with the entire number
                        while (i != input.length - 1 && input[i + 1] != ' ' && !Operator.isOperator(input[i + 1])) {
                            tempNum.append(input[i + 1]);
                            i++;
                        }

                        //add a new Node with value of tempNum
                        stack.push(new Node(Double.parseDouble(tempNum.toString())));
                    }
                }
                break;
            case prefix:
                for (int i = input.length - 1; i >= 0; i--) {
                    //character is an operator
                    if (Operator.isOperator(input[i])) {
                        //make a new node with the operator
                        Node root = new Node(Operator.valueOfChar(input[i]));

                        //take the top nodes in stack and assign it to be the children
                        root.leftChild = stack.pop();
                        root.rightChild = stack.pop();

                        //put the Node into top of stack
                        stack.push(root);
                    }
                    //character is a number
                    else if (input[i] != ' ') {
                        //String to hold the number incase it is multiple characters long
                        StringBuilder tempNum = new StringBuilder(Character.toString(input[i]));
                        //fill the string with the entire number
                        while (i != 0 && input[i - 1] != ' ' && !Operator.isOperator(input[i - 1])) {
                            tempNum.append(input[i - 1]);
                            i--;
                        }

                        //add a new Node with value of tempNum
                        stack.push(new Node(Double.parseDouble(tempNum.reverse().toString())));

                    }
                }
                break;
            case infix:
                //convert infix to postfix

                //Shunting-yard algorithm
                //https://en.wikipedia.org/wiki/Shunting-yard_algorithm#The_algorithm_in_detail

                //store expression in elements to build at the end
                ArrayList<String> elements = new ArrayList<>();
                //holds the operators as well as braces
                Stack<Character> operatorStack = new Stack<>();

                //go through every character of input
                for (int i = 0; i < input.length; i++) {
                    //character is an operator
                    if (Operator.isOperator(input[i])) {
                        //store operator to avoid calling method multiple times
                        Operator operator = Operator.valueOfChar(input[i]);

                        //while ((there is an operator at the top of the operator stack with greater precedence)
                        //or (the operator at the top of the operator stack has equal precedence and is left associative))
                        //and (the character at the top of the operator stack is an operator)
                            while ((!operatorStack.isEmpty() && Operator.isOperator(operatorStack.peek()) &&
                                    ((!operator.greaterPrecedenceThan(Operator.valueOfChar(operatorStack.peek())))
                                            || (Operator.valueOfChar(input[i]).equalPrecedence(Operator.valueOfChar(operatorStack.peek())) && Operator.valueOfChar(operatorStack.peek()).getAssociativity() == Associativity.LEFT)))) {
                                //pop operators from the operator stack onto the output list
                                elements.add(String.valueOf(operatorStack.pop()));
                            }
                        operatorStack.push(input[i]);
                    }
                    //character is left brace
                    else if (input[i] == '(') {
                        operatorStack.push(input[i]);
                    }
                    //character is right brace
                    else if (input[i] == ')') {
                        while (operatorStack.peek() != '(') {
                            elements.add(String.valueOf(operatorStack.pop()));
                        }
                        operatorStack.pop();
                    }
                    //character is a number
                    else if (input[i] != ' ') {
                        //String to hold the number incase it is multiple characters long
                        StringBuilder tempNum = new StringBuilder(Character.toString(input[i]));
                        //fill the string with the entire number
                        while (i != input.length - 1 && input[i + 1] != ' ' && input[i + 1] != ')' && input[i + 1] != '(' && !Operator.isOperator(input[i + 1])) {
                            tempNum.append(input[i + 1]);
                            i++;
                        }
                        elements.add(tempNum.toString());
                    }
                }
                while (!operatorStack.isEmpty()) {
                    if (operatorStack.peek() == ')' || operatorStack.peek() == '(') {
                        operatorStack.pop();
                    } else {
                        elements.add(String.valueOf(operatorStack.pop()));
                    }
                }
                //convert element list into String expression
                StringBuilder postfix = new StringBuilder();
                for (int i = 0; i < elements.size(); i++) {
                    postfix.append(elements.get(i));
                    if (i != elements.size() - 1) {
                        postfix.append(' ');
                    }
                }
                //the root node is initialized from postfix form
                return initFromExpr(postfix.toString(), Form.postfix);
        }
        //the only Node in the stack left is the root of the new tree
        return stack.pop();
    }

    /**
     * Evaluates the tree starting from the root
     *
     * @return - evaluation of the entire tree
     */
    public double evaluate() {
        return evaluate(root);
    }

    /**
     * Evaluates the tree starting from specified Node
     *
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

    public String getForm(Form form) {
        switch (form) {
            case postfix:
                return postfix(root);
            case prefix:
                return prefix(root);
            case infix:
                return infix(root);
        }
        return null;
    }

    private String infix(Node node) {
        if (node.getValue().getType() == Type.operator) {
            return '(' + infix(node.leftChild) + ' ' + node.getValue().getOperator().toString() + ' ' + infix(node.rightChild) + ')';
        }
        return node.getValue().getValue() + "";
    }

    private String prefix(Node node) {
        if (node.getValue().getType() == Type.operator) {
            return node.getValue().getOperator().toString() + ' ' + prefix(node.leftChild) + ' ' + prefix(node.rightChild);
        }
        return node.getValue().getValue() + "";
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
                ", infix=" + infix(root) +
                ", prefix=" + prefix(root) +
                ", postfix=" + postfix(root) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tree tree = (Tree) o;

        return root != null ? root.equals(tree.root) : tree.root == null;
    }
}
