package com.noschiff;

public abstract class Value<T> extends Element<T> {

    public Value(T value) {
        super(value);
    }

    public static Value parseValue(String input) {
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
}
