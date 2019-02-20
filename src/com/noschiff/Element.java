package com.noschiff;

import java.util.ArrayList;
import java.util.List;

/**
 * Highest level class that represents a part of an expression
 * Purpose: boxes the value in an object
 * Note: is abstract; only elements that are a child classes are allowed
 *
 * @param <T> - the type of value that is held in the object
 * @author noschiff
 */
public abstract class Element<T> {

    private T value;

    public Element(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static Element[] parseExpression(String expression) {

        ArrayList<Element> values = new ArrayList<>();
        char[] input = expression.toCharArray();
        //starts at left and moves to the right
        for (int i = 0; i < input.length; i++) {
            //character is an operator
            if (Operator.isOperator(input[i]) && ((i == (input.length - 1)) || (input[i + 1] == ' '))) {
                values.add(new Operation(Operator.parseOperator(input[i])));
            } else if (EBracket.isBracket(input[i])) {
                values.add(new Bracket(EBracket.parseBracket(input[i])));
            }
            //character is a number
            else if (input[i] != ' ') {
                //String to hold the number in case it is multiple characters long
                StringBuilder tempNum = new StringBuilder(Character.toString(input[i]));
                //fill the string with the entire number
                while (i != input.length - 1 && input[i + 1] != ' ' && !Operator.isOperator(input[i + 1]) && !EBracket.isBracket((input[i + 1]))) {
                    tempNum.append(input[i + 1]);
                    i++;
                }

                values.add(new Number(Double.parseDouble(tempNum.toString())));
            }
        }
        return values.toArray(new Element[0]);
    }

    public static Element[] parseTree(Node root, Form output) {
        return parseNode(root, output).toArray(new Element[0]);
    }

    private static List<Element> parseNode(Node node, Form output) {
        if (node.getValue() instanceof Operation) {
            List<List<Element>> elements = new ArrayList<>();

            switch (output) {
                case infix:
                    elements.add(List.of(new Bracket(EBracket.LEFT)));
                    elements.add(parseNode(node.leftChild, Form.infix));
                    elements.add(List.of(node.getValue()));
                    elements.add(parseNode(node.rightChild, Form.infix));
                    elements.add(List.of(new Bracket(EBracket.RIGHT)));
                    break;
                case prefix:
                    elements.add(List.of(node.getValue()));
                    elements.add(parseNode(node.leftChild, Form.prefix));
                    elements.add(parseNode(node.rightChild, Form.prefix));
                    break;
                case postfix:
                    elements.add(parseNode(node.leftChild, Form.postfix));
                    elements.add(parseNode(node.rightChild, Form.postfix));
                    elements.add(List.of(node.getValue()));
                    break;
            }

            return Util.flattenList(elements);

        } else {
            return List.of(node.getValue());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Element<?> element = (Element<?>) o;

        return value != null ? value.equals(element.value) : element.value == null;
    }
}
