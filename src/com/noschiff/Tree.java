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
     * Constructor that takes a mathematical expression as a String and initializes the tree
     *
     * @param expression - math expression stored in String
     * @param form       - the form that the expression is in
     */
    public Tree(String expression, Form form) {
        this.root = initFromExpr(expression, form);
    }

    /**
     * Constructor that takes a mathematical expression as a Value array and initializes the tree
     *
     * @param expression - math expression stored in Value array
     * @param form       - the form that the expression is in
     */
    public Tree(Element[] expression, Form form) {
        this.root = initFromExpr(expression, form);
    }

    private Node initFromExpr(String expression, Form form) {
        return initFromExpr(Element.parseExpression(expression), form);
    }

    /**
     * Creates the root Node with all children initialized from
     * a mathematical expression in one of three forms
     *
     * @param inputs - math expression stored in Value array
     * @param form   - the form that the expression is in
     * @return - the root node of the tree
     */
    private Node initFromExpr(Element[] inputs, Form form) {
        Stack<Node> stack = new Stack<>();

        //read input characters based off of form inputted
        switch (form) {
            case postfix:
                //starts at left and moves to the right
                for (Element input : inputs) {
                    if (input instanceof Value) {
                        if (input instanceof Operation) {
                            //make a new node with the operator
                            Node root = new Node((Value) input);

                            //take the top nodes in stack and assign it to be the children
                            root.rightChild = stack.pop();
                            root.leftChild = stack.pop();

                            //put the Node into top of stack
                            stack.push(root);
                        } else if (input instanceof Number) {
                            stack.push(new Node((Value) input));
                        }
                    }
                }
                break;
            case prefix:
                for (int i = inputs.length - 1; i >= 0; i--) {
                    if (inputs[i] instanceof Value) {
                        Value input = (Value) inputs[i];
                        if (input instanceof Operation) {
                            //make a new node with the operator
                            Node root = new Node(input);

                            //take the top nodes in stack and assign it to be the children
                            root.leftChild = stack.pop();
                            root.rightChild = stack.pop();

                            //put the Node into top of stack
                            stack.push(root);
                        } else if (input instanceof Number) {
                            stack.push(new Node(input));
                        }
                    }
                }
                break;
            case infix:
                //convert infix to postfix

                //Shunting-yard algorithm
                //https://en.wikipedia.org/wiki/Shunting-yard_algorithm#The_algorithm_in_detail

                //store expression in elements to build at the end
                ArrayList<Element> elements = new ArrayList<>();
                //holds the operators as well as braces
                Stack<Element> operatorStack = new Stack<>();

                //go through every input
                for (Element input : inputs) {
                    //character is an operator
                    if (input instanceof Operation) {
                        Operator operator = ((Operation) input).getValue();

                        while (!operatorStack.isEmpty() && operatorStack.peek() instanceof Operation) {
                            Operator nextOperator = ((Operation) operatorStack.peek()).getValue();
                            if (!operator.greaterPrecedenceThan(nextOperator)
                                    || (nextOperator.getAssociativity() == Associativity.LEFT
                                    && nextOperator.equalPrecedence(operator))) {
                                elements.add(operatorStack.pop());
                            } else {
                                break;
                            }
                        }
                        operatorStack.push(input);
                    }
                    //character is a brace
                    else if (input instanceof Bracket) {
                        if (input.getValue() == EBracket.LEFT) {
                            operatorStack.push(input);
                        } else if (input.getValue() == EBracket.RIGHT) {
                            while (operatorStack.peek().getValue() != EBracket.LEFT) {
                                elements.add(operatorStack.pop());
                            }
                        }
                    }
                    //character is a number
                    else if (input instanceof Number) {
                        elements.add(input);
                    }
                }
                while (!operatorStack.isEmpty()) {
                    if (operatorStack.peek() instanceof Bracket) {
                        operatorStack.pop();
                    } else {
                        elements.add(operatorStack.pop());
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
        if (node.getValue() instanceof Operation) {
            //Different operations for each possible operator
            switch (((Operation) node.getValue()).getValue()) {
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
        if (node.getValue() instanceof Number) {
            return ((Number) node.getValue()).getValue();
        }
        return 0;
    }

    /**
     * Converts the tree to a mathematical expression, stored in a String
     *
     * @param form - the form the expression will be output in
     * @return - the mathematical expression stored in a String
     */
    public String getForm(Form form) {
        //parses the tree starting from the root into an Element array
        //form is passed into parseTree() to specify the form of the returned array
        //converts the array into a String using arrayToString()
        
        return Element.arrayToString(Element.parseTree(root, form));
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
                ", infix=" + getForm(Form.infix) +
                ", prefix=" + getForm(Form.prefix) +
                ", postfix=" + getForm(Form.postfix) +
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
