package com.noschiff;

import java.util.ArrayList;
import java.util.Objects;

public abstract class Value<T> extends Element<T> {

    public Value(T value) {
        super(value);
    }

    public static Value parseValue(String input){
        if (input.equals(" ")) {
            return null;
        }
        try {
            return new Number(Double.parseDouble(input));
        } catch (NumberFormatException e) {
            try {
                return new Operation(Operator.valueOfChar(input.toCharArray()[0]));
            } catch (NullPointerException n) {
                System.out.println("what happened here");
            }
        }
        return null;

    }
    public static Value[] parseValues(String[] inputs) {
        ArrayList<Value> values = new ArrayList<>();
        for (String input : inputs) {
            values.add(parseValue(input));
        }
        values.removeIf(Objects::isNull);
        return (Value[]) values.toArray();
    }

    public static Value[] parseExpression(String expression) {
        ArrayList<Value> values = new ArrayList<>();
        char[] input = expression.toCharArray();
        //starts at left and moves to the right
        for (int i = 0; i < input.length; i++) {
            //character is an operator
            if (Operator.isOperator(input[i])) {
                values.add(new Operation(Operator.valueOfChar(input[i])));
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

                values.add(new Number(Double.parseDouble(tempNum.toString())));
            }
        }
        return values.toArray(new Value[0]);
    }

}
