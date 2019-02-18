package com.noschiff;

import java.util.ArrayList;

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
                values.add(new Operation(Operator.valueOfChar(input[i])));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Element<?> element = (Element<?>) o;

        return value != null ? value.equals(element.value) : element.value == null;
    }
}
