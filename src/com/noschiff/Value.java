package com.noschiff;

/**
 * Holds the value of the node; either an operand OR double
 * @author noschiff
 */
public class Value {

    //What type of value is stored
    private Type type;
    //Numeric value
    private double value;
    //Type of operator
    private Operator operator;

    /**
     * Constructor initializes the value to hold a double and sets the type
     * @param value - numeric value to be stored
     */
    public Value(double value) {
        this.value = value;
        this.type = Type.value;
    }

    /**
     * Constructor initialzes the value to hold an operator and sets the type
     * @param operator - operator to be stored
     */
    public Value(Operator operator) {
        this.operator = operator;
        this.type = Type.operator;
    }

    public double getValue() {
        return value;
    }

    public Type getType() {
        return type;
    }

    public Operator getOperator() {
        return operator;
    }
}
